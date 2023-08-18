package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.messages.SpielBeendetMessage;
import tischkicker.models.Spiel;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import java.io.IOException;
import java.util.Arrays;

class SpielHolder {
    public int teamID;

    public int tore;

}

@Component
public class SpielManager {

    private SpielErgebnis ergebnis = new SpielErgebnis();

    private boolean tauscheTeams = false;
    public boolean spielVorbei = false;

    public int anzahltoreBisGewonnen = 10;

    private SpielHolder rot = new SpielHolder();
    private SpielHolder weiss = new SpielHolder();

    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TurnierManager turnierManager;

    private SpielManager() {

    }

    public void reset() {
        ergebnis = new SpielErgebnis();
        rot = new SpielHolder();
        weiss = new SpielHolder();

        tauscheTeams = false;
        spielVorbei = false;
    }

    public void spielStarten(Spiel spiel) {
        reset();

        ergebnis.spiel = spiel;
        ergebnis.teams = getTeamsForSpiel(spiel);

        rot.teamID = spiel.getTeamIDs()[0];
        weiss.teamID = spiel.getTeamIDs()[1];

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
        return getInfo(seite).teamID;
    }

    private SpielHolder getInfo(Tor.Seite seite) {
        return seite == Tor.Seite.ROT ? rot : weiss;
    }

    private SpielHolder getInfoByID(int id) {
        if (rot.teamID == id) {
            return rot;
        }

        return weiss;
    }

    /***
     * Wenn Seitenwechsel aktiviert ist, wird bei der hälfte des maximums der Spiele um zu gewinnen ein Seitenwechsel durchgeführt.
     * Wenn diese erreicht ist, ist das Spiel vorbei und wird in die Datenbank gespeichert.
     */
    private void triggerSpielMode() throws IOException {
        int maxTore = Math.max(ergebnis.toreTeam1, ergebnis.toreTeam2); // die größte Anzahl Tore der Teams holen, da diese relevant für den weiteren Schritt ist

        if (maxTore == anzahltoreBisGewonnen) {
            turnierManager.spielPhase.empfangeEndergebnis(ergebnis);
            SpielBeendetMessage msg = new SpielBeendetMessage();
            msg.setGewinner(getGewinner(ergebnis.spiel));
            msg.setSpiel(ergebnis.spiel);

            SocketHandler.broadcast(msg);

            reset();

            spielVorbei = true;
        }
    }

    private Team getGewinner(Spiel spiel) {
        Team team = ergebnis.teams[1];

        if (ergebnis.toreTeam1 > ergebnis.toreTeam2) {
            team = ergebnis.teams[0];
            team.setId(spiel.getTeamIDs()[0]);
            return team;
        }

        team.setId(spiel.getTeamIDs()[1]);

        return team;
    }

    public void seitenWechsel() throws IOException {
        SpielHolder tmp = rot;
        rot = weiss;
        weiss = tmp;

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
        ergebnis.toreTeam1 = rot.tore;
        ergebnis.toreTeam2 = weiss.tore;
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

        SpielBeendetMessage msg = new SpielBeendetMessage();
        msg.setGewinner(getGewinnerWennAufgegeben(id));
        msg.setSpiel(ergebnis.spiel);

        SocketHandler.broadcast(msg);

        reset();
    }

    private Team getGewinnerWennAufgegeben(int id) {
        if(ergebnis.spiel.getTeamIDs()[0] == id) {
            return ergebnis.teams[1];
        }

        return ergebnis.teams[0];
    }
}
