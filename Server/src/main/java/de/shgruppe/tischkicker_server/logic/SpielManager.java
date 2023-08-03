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
        ergebnis.toreTeam2 = 0;
        ergebnis.toreTeam1 = 0;
        tauscheTeams = false;
    }

    public void empfangeTor(Tor tor) {
        if (tor.seite == Tor.Seite.ROT) {
            if (tauscheTeams) {
                ergebnis.toreTeam2 += 1;
            }
            else {
                ergebnis.toreTeam1 += 1;
            }
        }
        else if (tor.seite == Tor.Seite.WEISS) {
            if (tauscheTeams) {
                ergebnis.toreTeam1 += 1;
            }
            else {
                ergebnis.toreTeam2 += 1;
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
