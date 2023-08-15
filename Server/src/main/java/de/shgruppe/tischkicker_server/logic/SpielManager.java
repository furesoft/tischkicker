package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tischkicker.models.Spiel;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import java.io.IOException;

public class SpielManager {
    public static SpielManager Instance = new SpielManager();
    private final SpielErgebnis ergebnis = new SpielErgebnis();

    private boolean tauscheTeams = false;
    public boolean spielVorbei = false;

    public int anzahltoreBisGewonnen = 10;
    public boolean beachteSeitenwechsel = false;

    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamRepository teamRepository;

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
    }

    private Team[] getTeamsForSpiel(Spiel spiel) {
        int[] teamIDs = spiel.getTeamIDs();

        Team team1 = teamRepository.getReferenceById(teamIDs[0]);
        Team team2 = teamRepository.getReferenceById(teamIDs[2]);

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

        increment(tor.seite);
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
            TurnierManager.Instance.spielPhase.empfangeEndergebnis(ergebnis);
        }
    }

    public void seitenWechsel() {
        tauscheTeams = !tauscheTeams;
    }

    public SpielErgebnis getErgebnis() {
        return ergebnis;
    }

    /***
     * Erhöht die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param seite
     * @throws IOException
     */
    public void increment(Tor.Seite seite) throws Exception {
        sicherstellungSpielGestartet();

        triggerSpielMode();

        if (seite == Tor.Seite.ROT) {
            if (tauscheTeams) {
                ergebnis.toreTeam2 += 1;
            }
            else {
                ergebnis.toreTeam1 += 1;
            }
        }
        else if (seite == Tor.Seite.WEISS) {
            if (tauscheTeams) {
                ergebnis.toreTeam1 += 1;
            }
            else {
                ergebnis.toreTeam2 += 1;
            }
        }

        spielRepository.save(ergebnis.spiel);
        SocketHandler.broadcast(ergebnis);
    }

    private void sicherstellungSpielGestartet() throws Exception {
        if (ergebnis.spiel == null) {
            throw new Exception("Es wurde kein Spiel gestartet");
        }
    }

    /***
     * Verringert die entsprechende Toranzahl und sendet den aktuellen Zwischenstand an die Clients.
     * @param seite
     * @throws IOException
     */
    public void decrement(Tor.Seite seite) throws Exception {
        sicherstellungSpielGestartet();

        if (seite == Tor.Seite.ROT) {
            if (tauscheTeams) {
                ergebnis.toreTeam2 -= 1;
            }
            else {
                ergebnis.toreTeam1 -= 1;
            }
        }
        else if (seite == Tor.Seite.WEISS) {
            if (tauscheTeams) {
                ergebnis.toreTeam1 -= 1;
            }
            else {
                ergebnis.toreTeam2 -= 1;
            }
        }

        spielRepository.save(ergebnis.spiel);
        SocketHandler.broadcast(ergebnis);
    }
}
