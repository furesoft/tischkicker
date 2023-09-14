package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.errorhandling.Hilfsmethoden;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import  java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


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





    public List<Spiel> turnierStarten() {
        List<Spiel> spiele = spielRepository.findAll();
        if (spiele.size() == 0)
        {
            Turnier turnier = new Turnier();
             turnier.setStartdatum(Hilfsmethoden.ermittleDatum());

            spiele.addAll(generiereUndSpeicherSpiele(generiereSpielePhase1()));
            turnier.setSpieleIDs(spiele.stream().mapToInt(Spiel::getSpielID).toArray());
            turnier = turnierRepository.saveAndFlush(turnier);

            for (int i = 0 ; i < spiele.size() ; i++)
            {
                spiele.get(i).setTurnierID(turnier.getID());
            }

            spielRepository.saveAllAndFlush(spiele);

            return spiele;
        }
        else
        {
            List<Team> teams = teamRepository.findAll();
            for (int i = 0 ; i < spiele.size() ; i++)
            {
                String [] namen = new String[2];
                int [] teamids = spiele.get(i).getTeamIDs();
                for (int h = 0 ; h < 2 ; h++)
                {
                   if (teamids[h] < 0)
                   {
                       namen[h] = null;
                   }
                   else
                   {
                       namen[h] = teams.get(teamids[h]-1).getName();
                   }
                }
                spiele.get(i).setTeamNames(namen[0],namen[1]);
            }
        }

        return spiele;
    }

    private List<Spiel> generiereSpielePhase1() {
        int teamfaktor = 0;
        List<Team> teams = teamRepository.findAll();

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
                spiel.setSpieleIDs(-1,-1);
                spiele[i / 2] = spiel;

            }
            else if (teams.size() % 2 != 0 && i == teams.size() - 1) {
                Spiel spielUngerade = new Spiel();
                spielUngerade.setTeams(teams.get(i).getId(), -2);
                spielUngerade.setTeamNames(teams.get(i).getName(), null);
                spielUngerade.setQualifikation(1); // Qualifikation = Phase
                spielUngerade.setSpieleIDs(-1,-1);
                spiele[i / 2] = spielUngerade;
            }
        }

        return Arrays.asList(spiele);
    }
    public List<Spiel> generiereUndSpeicherSpiele (List<Spiel> erstePhase)
    {
        List<Spiel> alleSpiele = new ArrayList<>();
        List<Spiel> gespeicherteSpielePhase1 = spielRepository.saveAllAndFlush(erstePhase);
        List<Spiel> aktuellePhase = gespeicherteSpielePhase1;
        alleSpiele.addAll(gespeicherteSpielePhase1);
        while (aktuellePhase.size()!=1)
        {

            aktuellePhase = generiereSpieleNaechstePhase(aktuellePhase);
            aktuellePhase = spielRepository.saveAllAndFlush(aktuellePhase);
            alleSpiele.addAll(aktuellePhase);

        }

        return alleSpiele;
    }

    private List<Spiel> generiereSpieleNaechstePhase(List<Spiel> aktuellePhase) {
        List<Spiel> naechstePhase = new ArrayList<>();
        for (int i= 0 ; i < aktuellePhase.size()-1 ; i+=2)
        {
            Spiel Vorgaenger1 = aktuellePhase.get(i);
            Spiel Vorgaenger2 = aktuellePhase.get(i+1);
            Spiel spielNeu = new Spiel();
            spielNeu.setQualifikation(Vorgaenger1.getQualifikation()+1);
            spielNeu.setSpieleIDs(Vorgaenger1.getSpielID(),Vorgaenger2.getSpielID());
            spielNeu.setTeams(-1,-1);
            naechstePhase.add(spielNeu);
        }
        boolean ungeradeSpielAnzahl = aktuellePhase.size()%2 != 0;
        if (ungeradeSpielAnzahl) // falls ungerade Anzahl in Phase, ein weiteres Spiel generieren
        {
            Spiel Vorgaenger1 = aktuellePhase.get(aktuellePhase.size()-1);
            Spiel spielUngerade = new Spiel();
            spielUngerade.setSpieleIDs(Vorgaenger1.getSpielID(),-1); // -1 für kein Vorgänger Spiel
            spielUngerade.setQualifikation(Vorgaenger1.getQualifikation()+1);
            spielUngerade.setTeams(-1,-2); // -2 für unbekannten Verlierer und -1 noch nicht bekannt für Team
            naechstePhase.add(spielUngerade);
        }
        return naechstePhase;
    }




}
