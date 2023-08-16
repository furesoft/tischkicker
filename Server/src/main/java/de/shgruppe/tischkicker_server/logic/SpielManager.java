package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.models.Spiel;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import java.io.IOException;

class SpielHolder {
    public int teamID;

    public int tore;

}

@Component
public class SpielManager {

    private final SpielErgebnis ergebnis = new SpielErgebnis();

    private boolean tauscheTeams = false;
    public boolean spielVorbei = false;

    public int anzahltoreBisGewonnen = 10;
    public boolean beachteSeitenwechsel = false;

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
        ergebnis.toreTeam2 = 0;
        ergebnis.toreTeam1 = 0;
        ergebnis.spiel = null;
        tauscheTeams = false;
        spielVorbei = false;
    }

    public void spielStarten(Spiel spiel) {
        reset();

        ergebnis.spiel = spiel;
        ergebnis.teams = getTeamsForSpiel(spiel);

        rot.teamID = spiel.getTeamIDs()[0];
        weiss.teamID = spiel.getTeamIDs()[1];
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
        if(rot.teamID == id){
            return rot;
        }

        return weiss;
    }

    /***
     * Wenn Seitenwechsel aktiviert ist, wird bei der hälfte des maximums der Spiele um zu gewinnen ein Seitenwechsel durchgeführt.
     * Wenn diese erreicht ist, ist das Spiel vorbei und wird in die Datenbank gespeichert.
     */
    private void triggerSpielMode() {
        int maxTore = Math.max(ergebnis.toreTeam1, ergebnis.toreTeam2); // die größte Anzahl Tore der Teams holen, da diese relevant für den weiteren Schritt ist

        if (beachteSeitenwechsel) {
            if (maxTore == anzahltoreBisGewonnen / 2) {
                seitenWechsel();
            }
        }

        if (maxTore == anzahltoreBisGewonnen) {
            turnierManager.spielPhase.empfangeEndergebnis(ergebnis);
        }
    }

    public void seitenWechsel() {
       SpielHolder tmp = rot;
       rot = weiss;
       weiss = tmp;
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
        sicherstellungSpielGestartet();

        triggerSpielMode();

        getInfoByID(teamID).tore++;
        ergebnis.toreTeam1 = rot.tore;
        ergebnis.toreTeam2 = weiss.tore;

        SocketHandler.broadcast(ergebnis);
    }

    private void sicherstellungSpielGestartet() throws Exception {
        if (ergebnis.spiel == null) {
            throw new Exception("Es wurde kein Spiel gestartet");
        }
    }

    /***
     * Verringert die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param teamID
     * @throws IOException
     */
    public void decrement(int teamID) throws Exception {
        sicherstellungSpielGestartet();

        getInfoByID(teamID).tore--;

        spielRepository.save(ergebnis.spiel);
        SocketHandler.broadcast(ergebnis);
    }
}
