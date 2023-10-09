package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.messages.SiegerTreppchenMessage;
import tischkicker.messages.SpielBeendetMessage;
import tischkicker.messages.SpielErgebnis;
import tischkicker.messages.TurnierBeendetMessage;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Tor;
import tischkicker.models.Turnier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class SpielHolder {
    public int teamID;

    public int tore;
}

@Component
public class SpielManager {

    public boolean spielVorbei = false;
    public int anzahlToreBisGewonnen = 10;

    @Autowired
    SpielRepository spielRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TurnierManager turnierManager;

    @Autowired
    TurnierRepository turnierRepository;

    @Autowired
    Statistics stats;

    private SpielErgebnis ergebnis = new SpielErgebnis();
    private boolean tauscheTeams = false;
    private SpielHolder team1 = new SpielHolder();
    private SpielHolder team2 = new SpielHolder();

    private SpielManager() {

    }

    public void reset() {
        ergebnis = new SpielErgebnis();
        team1 = new SpielHolder();
        team2 = new SpielHolder();

        tauscheTeams = false;
        spielVorbei = false;
    }

    public void spielStarten(Spiel spiel) throws IOException {
        reset();

        ergebnis.spiel = spiel;
        ergebnis.teams = getTeamsForSpiel(spiel);

        team1.teamID = spiel.getTeamIDs()[0];
        team2.teamID = spiel.getTeamIDs()[1];

        ergebnis.seiteTeam2 = Tor.Seite.ROT;
        ergebnis.seiteTeam1 = Tor.Seite.WEISS;

        anzahlToreBisGewonnen = getTurnier(spiel.getTurnierID()).getMaximalToreBisGewonnen();

        if (anzahlToreBisGewonnen == 0) {
            anzahlToreBisGewonnen = 10;
        }

        SocketHandler.broadcast(ergebnis);
    }

    private Turnier getTurnier(int turnierID) {
        return turnierManager.turnierRepository.findById(turnierID).get();
    }

    private Team[] getTeamsForSpiel(Spiel spiel) {
        int[] teamIDs = spiel.getTeamIDs();

        Team team1 = teamRepository.findById(teamIDs[0]).get();
        Team team2 = teamRepository.findById(teamIDs[1]).get();

        return new Team[]{team1, team2};
    }

    /***
     * Erhöht die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param tor
     * @throws IOException
     */
    public void empfangeTor(Tor tor) throws Exception {
        if (spielVorbei) {
            return;
        }

        increment(getIdVonSeite(tor.seite));
    }

    private int getIdVonSeite(Tor.Seite seite) {
        if (this.ergebnis.seiteTeam1 == seite) {
            return team1.teamID;
        }
        else {
            return team2.teamID;
        }

    }

    private SpielHolder getInfoByID(int id) {
        if (team1.teamID == id) {
            return team1;
        }

        return team2;
    }

    private boolean hatSpielerGewonnen() {
        int maxTore = Math.max(ergebnis.toreTeam1, ergebnis.toreTeam2); // die größte Anzahl Tore der Teams holen, da diese relevant für den weiteren Schritt ist
        int aktuelleTordifferenz = Math.abs(ergebnis.spiel.getToreteam1() - ergebnis.spiel.getToreteam2()); // absolute tordifferenz

        return maxTore >= anzahlToreBisGewonnen && aktuelleTordifferenz >= TurnierManager.aktuellesTurnier.getTordifferenz() || ergebnis.teams[0].isAufgegeben() || ergebnis.teams[1].isAufgegeben();
    }

    private void triggerSpielMode() throws IOException {
        ergebnis.spiel.setToreteam1(team1.tore);
        ergebnis.spiel.setToreteam2(team2.tore);

        if (hatSpielerGewonnen()) {
            if (ergebnis.toreTeam1 >= anzahlToreBisGewonnen || ergebnis.teams[1].isAufgegeben()) {
                ergebnis.spiel.setGewinnerID(ergebnis.teams[0].getId());
            }
            if (ergebnis.toreTeam2 >= anzahlToreBisGewonnen || ergebnis.teams[0].isAufgegeben()) {
                ergebnis.spiel.setGewinnerID(ergebnis.teams[1].getId());
            }
            spielRepository.saveAndFlush(ergebnis.spiel);

            Spiel neuesSpiel = null;
            try {
                neuesSpiel = turnierManager.spielPhase.empfangeEndergebnis(ergebnis);
            } catch (TurnierBeendetException e) {
                Optional<Turnier> turnier = turnierRepository.findById(ergebnis.spiel.getTurnierID());

                turnier.get().setEnddatum(Hilfsmethoden.ermittleDatum());
                turnier.get().setGespielt(true);

                turnierRepository.saveAndFlush(turnier.get());

                TurnierBeendetMessage tmsg = new TurnierBeendetMessage();
                tmsg.setTurnier(turnier.get());

                SocketHandler.broadcast(tmsg);


                SiegerTreppchenMessage treppchenMessage = new SiegerTreppchenMessage();
                treppchenMessage.teams = getTreppchenTeams();

                SocketHandler.broadcast(treppchenMessage);


                stats.incrementTeamTore(ergebnis.teams[0], ergebnis.toreTeam1, ergebnis.toreTeam2);
                stats.incrementTeamTore(ergebnis.teams[1], ergebnis.toreTeam2, ergebnis.toreTeam1);
            }

            SpielBeendetMessage msg = new SpielBeendetMessage();
            msg.setGewinner(getGewinner(ergebnis.spiel));
            msg.setSpiel(ergebnis.spiel);
            msg.setNeuesSpiel(neuesSpiel);

            SocketHandler.broadcast(msg);


            if (!ergebnis.teams[0].isAufgegeben() && !ergebnis.teams[1].isAufgegeben()) {
                reset();
            }

            spielVorbei = true;
        }
    }

    private ArrayList<Team> getTreppchenTeams() {
        ArrayList<Team> teams = new ArrayList<>();

        Team erster = getGewinner(ergebnis.spiel);
        Team zweiter = Arrays.stream(ergebnis.teams).filter(t -> t.getId() != erster.getId()).findFirst().get();
        teams.add(erster);
        teams.add(zweiter);

        int vorheridePhasenID = ergebnis.spiel.getQualifikation() - 1;
        List<Spiel> spieleVorherigePhase = spielRepository.findAll().stream()
                                                          .filter(s -> s.getTurnierID() == ergebnis.spiel.getTurnierID())
                                                          .filter(s -> s.getQualifikation() == vorheridePhasenID)
                                                          .collect(Collectors.toList());
        if (spieleVorherigePhase.size() > 0) {
            ArrayList<Team> verliererTeams = getVerliererTeams(spieleVorherigePhase, erster, zweiter);
            Team dritter = getBestVerlierer(verliererTeams);
            teams.add(dritter);
        }
        else {
            teams.add(null);
        }

        return teams;
    }

    private Team getBestVerlierer(ArrayList<Team> verliererTeams) {
        Team besterVerlierer = null;

        for (Team team : verliererTeams) {
            if (besterVerlierer == null) {
                besterVerlierer = team;
                continue;
            }

            if (team.getGesamttore() > besterVerlierer.getGesamttore()) {
                besterVerlierer = team;
            }
        }

        return besterVerlierer;
    }

    private ArrayList<Team> getVerliererTeams(List<Spiel> spieleVorherigePhase, Team erster, Team zweiter) {
        ArrayList<Team> teams = new ArrayList<>();

        for (Spiel spiel : spieleVorherigePhase) {
            int verliereID = Arrays.stream(spiel.getTeamIDs()).filter(id -> id != spiel.getGewinnerID()).findFirst()
                                   .getAsInt();
            if (verliereID != zweiter.getId() && verliereID != erster.getId()) {
                teams.add(teamRepository.findById(verliereID).get());
            }
        }

        return teams;
    }

    private Team getGewinner(Spiel spiel) {
        Team team = ergebnis.teams[1];

        if (ergebnis.teams[0].isAufgegeben()) {
            team = ergebnis.teams[1];
            team.setId(spiel.getTeamIDs()[1]);
        }

        if (ergebnis.teams[1].isAufgegeben()) {
            team = ergebnis.teams[0];
            team.setId(spiel.getTeamIDs()[0]);
        }

        if (ergebnis.toreTeam1 > ergebnis.toreTeam2 && !ergebnis.teams[0].isAufgegeben() && !ergebnis.teams[1].isAufgegeben()) {
            team = ergebnis.teams[0];
            team.setId(spiel.getTeamIDs()[0]);
            return team;
        }

        if (ergebnis.toreTeam1 < ergebnis.toreTeam2 && !ergebnis.teams[0].isAufgegeben() && !ergebnis.teams[1].isAufgegeben()) {
            team = ergebnis.teams[1];
            team.setId(spiel.getTeamIDs()[1]);
        }

        return team;
    }

    public void seitenWechsel() throws IOException {
        Tor.Seite tmpSeite = ergebnis.seiteTeam1;
        ergebnis.seiteTeam1 = ergebnis.seiteTeam2;
        ergebnis.seiteTeam2 = tmpSeite;

        SocketHandler.broadcast(ergebnis);
    }

    public SpielErgebnis getErgebnis() {
        return ergebnis;
    }

    /***
     * Erhöht die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param teamID
     * @throws IOException
     */
    public void increment(int teamID) throws Exception {
        getInfoByID(teamID).tore++;
        setzeTorErgebnisse();

        SocketHandler.broadcast(ergebnis);

        triggerSpielMode();
    }

    private void setzeTorErgebnisse() {
        ergebnis.toreTeam1 = team1.tore;
        ergebnis.toreTeam2 = team2.tore;
    }

    /***
     * Verringert die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param teamID
     * @throws IOException
     */
    public void decrement(int teamID) throws Exception {
        if (!spielVorbei && ergebnis.spiel == null) {
            return;
        }

        SpielHolder team = getInfoByID(teamID);
        if (team.tore == 0) {
            return;
        }

        team.tore--;

        spielRepository.save(ergebnis.spiel);

        setzeTorErgebnisse();

        SocketHandler.broadcast(ergebnis);
    }

    public void aufgeben(int id) throws IOException {
        // Team als aufgegeben markieren und in Datenbank schreiben, sowie dem client mitteilen
        Team aufgegebenTeam = Arrays.stream(ergebnis.teams).filter(team -> team.getId() == id).findFirst().get();
        aufgegebenTeam.setAufgegeben(true);
        teamRepository.saveAndFlush(aufgegebenTeam);

        triggerSpielMode();

        /*SpielBeendetMessage msg = new SpielBeendetMessage();
        msg.setGewinner(getGewinnerWennAufgegeben(id));
        msg.setSpiel(ergebnis.spiel);

        SocketHandler.broadcast(msg);

        reset();*/
    }

    /*private Team getGewinnerWennAufgegeben(int id) {
        if (ergebnis.spiel.getTeamIDs()[0] == id) {
            return ergebnis.teams[1];
        }

        return ergebnis.teams[0];
    }*/
}
