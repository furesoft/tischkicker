package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TurnierManager {
    @Autowired
    SpielPhase spielPhase;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TurnierRepository turnierRepository;

    static Turnier aktuellesTurnier;
    public static Map<Integer, List<Team>> alleTeams = new HashMap<>();

    public static void teamHinzufuegen (Team team) {
        if(alleTeams.containsKey(aktuellesTurnier.getID())) {
            alleTeams.get(aktuellesTurnier.getID()).add(team);
        }
        else {
            List<Team> teams = new ArrayList<>();
            teams.add(team);

            alleTeams.put(aktuellesTurnier.getID(), teams);
        }
    }

    public Turnier Turniererstellen() {
        aktuellesTurnier = turnierRepository.saveAndFlush(new Turnier());

        return aktuellesTurnier;
    }

    public List<Spiel> turnierStarten(int turnierID) {
        List<Spiel> spiele1 = spielRepository.findAll();
        spiele1 = spiele1.stream().filter(s -> s.getTurnierID() == turnierID).collect(Collectors.toList());
        aktuellesTurnier = turnierRepository.findById(turnierID).get();

        if (spiele1.size() == 0) {
            aktuellesTurnier.setStartdatum(Hilfsmethoden.ermittleDatum());
            aktuellesTurnier.setTeamsIDs(alleTeams.get(turnierID).stream().mapToInt(Team::getId).toArray());
            spiele1.addAll(generiereUndSpeicherSpiele(generiereSpielePhase1()));
            aktuellesTurnier.setSpieleIDs(spiele1.stream().mapToInt(Spiel::getSpielID).toArray());

            aktuellesTurnier = turnierRepository.saveAndFlush(aktuellesTurnier);

            for (int i = 0; i < spiele1.size(); i++) {
                spiele1.get(i).setTurnierID(aktuellesTurnier.getID());
            }

            return spiele1;
        }
        else {
            List<Team> teams = holeTeamsvonTurnier(aktuellesTurnier);

            for (int i = 0; i < spiele1.size(); i++) {
                String[] namen = new String[2];
                int[] teamids = spiele1.get(i).getTeamIDs();

                for (int h = 0; h < 2; h++) {
                    if (teamids[h] < 0) {
                        namen[h] = null;
                    }
                    else {
                        namen[h] = teams.get(teamids[h] - 1).getName();
                    }
                }

                spiele1.get(i).setTeamNames(namen[0], namen[1]);
            }
        }

        return spiele1;
    }

    public List<Team> holeTeamsvonTurnier(Turnier turnier) {
        List<Team> alleTeams = teamRepository.findAll();
        List<Integer> teamIds = Arrays.stream(turnier.getTeamsIDs()).boxed().collect(Collectors.toList());

        return alleTeams.stream().filter(t -> teamIds.contains(t.getId())).collect(Collectors.toList());
    }

    private List<Spiel> generiereSpielePhase1() {
        int teamfaktor = 0;
        List<Team> teams = holeTeamsvonTurnier(aktuellesTurnier);

        if (teams.size() % 2 != 0) {
            teamfaktor = 1;
        }

        Spiel[] spiele = new Spiel[teams.size() / 2 + teamfaktor];
        Collections.shuffle(teams);

        for (int i = 0; i < teams.size(); i++) {
            if (i % 2 == 0 && i < teams.size() - 1) {
                Spiel spiel = new Spiel();
                spiel.setTeams(teams.get(i).getId(), teams.get(i + 1).getId());
                spiel.setTeamNames(teams.get(i).getName(), teams.get(i + 1).getName());
                spiel.setQualifikation(1);
                spiel.setSpieleIDs(-1, -1);
                spiele[i / 2] = spiel;

            }
            else if (teams.size() % 2 != 0 && i == teams.size() - 1) {
                Spiel spielUngerade = new Spiel();
                spielUngerade.setTeams(teams.get(i).getId(), -2);
                spielUngerade.setTeamNames(teams.get(i).getName(), null);
                spielUngerade.setQualifikation(1); // Qualifikation = Phase
                spielUngerade.setSpieleIDs(-1, -1);
                spiele[i / 2] = spielUngerade;
            }
        }

        return Arrays.asList(spiele);
    }

    public List<Spiel> generiereUndSpeicherSpiele(List<Spiel> erstePhase) {
        List<Spiel> alleSpiele = new ArrayList<>();
        List<Spiel> gespeicherteSpielePhase1 = spielRepository.saveAllAndFlush(erstePhase);
        List<Spiel> aktuellePhase = gespeicherteSpielePhase1;
        alleSpiele.addAll(gespeicherteSpielePhase1);

        while (aktuellePhase.size() != 1) {
            aktuellePhase = generiereSpieleNaechstePhase(aktuellePhase);
            aktuellePhase = spielRepository.saveAllAndFlush(aktuellePhase);
            alleSpiele.addAll(aktuellePhase);
        }

        return alleSpiele;
    }

    private List<Spiel> generiereSpieleNaechstePhase(List<Spiel> aktuellePhase) {
        List<Spiel> naechstePhase = new ArrayList<>();

        for (int i = 0; i < aktuellePhase.size() - 1; i += 2) {
            Spiel ersterVorgaenger = aktuellePhase.get(i);
            Spiel zweiterVorgaenger = aktuellePhase.get(i + 1);

            Spiel spielNeu = new Spiel();

            spielNeu.setQualifikation(ersterVorgaenger.getQualifikation() + 1);
            spielNeu.setSpieleIDs(ersterVorgaenger.getSpielID(), zweiterVorgaenger.getSpielID());
            spielNeu.setTeams(-1, -1);

            naechstePhase.add(spielNeu);
        }

        boolean ungeradeSpielAnzahl = aktuellePhase.size() % 2 != 0;

        if (ungeradeSpielAnzahl) // falls ungerade Anzahl in Phase, ein weiteres Spiel generieren
        {
            Spiel vorgaenger = aktuellePhase.get(aktuellePhase.size() - 1);
            Spiel spielUngerade = new Spiel();

            spielUngerade.setSpieleIDs(vorgaenger.getSpielID(), -1); // -1 für kein Vorgänger Spiel
            spielUngerade.setQualifikation(vorgaenger.getQualifikation() + 1);
            spielUngerade.setTeams(-1, -2); // -2 für unbekannten Verlierer und -1 noch nicht bekannt für Team

            naechstePhase.add(spielUngerade);
        }

        return naechstePhase;
    }
}
