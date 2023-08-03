package de.shgruppe.tischkicker_server.logic;

import tischkicker.models.SpielErgebnis;
import tischkicker.models.Tor;

public class SpielManager {
    public static SpielManager Instance = new SpielManager();
    private final SpielErgebnis ergebnis = new SpielErgebnis();
    private boolean tauscheTeams = false;

    private SpielManager() {

    }

    public void reset() {
        ergebnis.team1 = 0;
        ergebnis.team2 = 0;
        tauscheTeams = false;
    }

    public void empfangeTor(Tor tor) {
        if (tor.seite == Tor.Seite.ROT) {
            if (tauscheTeams) {
                ergebnis.team1 += 1;
            }
            else {
                ergebnis.team2 += 1;
            }
        }
        else if (tor.seite == Tor.Seite.WEISS) {
            if (tauscheTeams) {
                ergebnis.team2 += 1;
            }
            else {
                ergebnis.team1 += 1;
            }
        }
    }

    public void seitenWechsel() {
        tauscheTeams = !tauscheTeams;
    }

    public SpielErgebnis getErgebnis() {
        return ergebnis;
    }
}
