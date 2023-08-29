package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.messages.SpielBeendetMessage;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;

class SpielHolder {
    public int teamID;

    public int tore;

}

@Component
public class SpielManager {

    public boolean spielVorbei = false;
    public int anzahltoreBisGewonnen = 10;
    @Autowired
    SpielRepository spielRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TurnierManager turnierManager;
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

    public void spielStarten(Spiel spiel) {
        reset();

        ergebnis.spiel = spiel;
        ergebnis.teams = getTeamsForSpiel(spiel);

        team1.teamID = spiel.getTeamIDs()[0];
        team2.teamID = spiel.getTeamIDs()[1];

        ergebnis.seiteTeam2 = Tor.Seite.ROT;
        ergebnis.seiteTeam1 = Tor.Seite.WEISS;
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
        if(this.ergebnis.seiteTeam1 == seite) {
            return team1.teamID;
        }
        else{
            return team2.teamID;
        }

    }

    private SpielHolder getInfoByID(int id) {
        if (team1.teamID == id) {
            return team1;
        }

        return team2;
    }

    //TODO: Automatischer Seitenwechsel????
    /***
     * Wenn Seitenwechsel aktiviert ist, wird bei der hälfte des maximums der Spiele um zu gewinnen ein Seitenwechsel durchgeführt.
     * Wenn diese erreicht ist, ist das Spiel vorbei und wird in die Datenbank gespeichert.
     */
    private void triggerSpielMode() throws IOException {
        int maxTore = Math.max(ergebnis.toreTeam1, ergebnis.toreTeam2); // die größte Anzahl Tore der Teams holen, da diese relevant für den weiteren Schritt ist

            // Abfrage, ob die Gewinnbedingungen erfüllt wurden
        if (maxTore == anzahltoreBisGewonnen || ergebnis.teams[0].isAufgegeben() || ergebnis.teams[1].isAufgegeben()) {
            ergebnis.spiel.setToreteam1(team1.tore);
            ergebnis.spiel.setToreteam2(team2.tore);

            ergebnis.spiel.setSpielvorbei(true);

            spielRepository.saveAndFlush(ergebnis.spiel);


            Spiel neuesSpiel = null;
            try {
                neuesSpiel = turnierManager.spielPhase.empfangeEndergebnis(ergebnis);
            } catch (KeinSpielVerfuegbarWeilTurnierBeendetException e) {
                //TODO was sende ich an den Client, wewnn das Spiel vorbei ist.
            }
            SpielBeendetMessage msg = new SpielBeendetMessage();
            msg.setGewinner(getGewinner(ergebnis.spiel));
            msg.setSpiel(ergebnis.spiel);
            msg.setNeuesSpiel(neuesSpiel);

            SocketHandler.broadcast(msg);

            if(!ergebnis.teams[0].isAufgegeben() && !ergebnis.teams[1].isAufgegeben()){
                reset();
            }

            spielVorbei = true;
        }
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

        if (ergebnis.toreTeam1 < ergebnis.toreTeam2 && !ergebnis.teams[0].isAufgegeben() && !ergebnis.teams[1].isAufgegeben()){
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
