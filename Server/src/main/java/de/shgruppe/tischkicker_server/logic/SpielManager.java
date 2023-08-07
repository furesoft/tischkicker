package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tischkicker.models.Spiel;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Tor;

public class SpielManager {
    public static SpielManager Instance = new SpielManager();
    private final SpielErgebnis ergebnis = new SpielErgebnis();

    private final SpielPhase runde = new SpielPhase();
    private boolean tauscheTeams = false;
    public boolean spielVorbei = false;

    public int anzahltoreBisGewonnen = 10;
    public boolean beachteSeitenwechsel = false;

    @Autowired
    SpielRepository spielRepository;

    private SpielManager() {

    }

    public void reset() {
        ergebnis.toreTeam2 = 0;
        ergebnis.toreTeam1 = 0;
        tauscheTeams = false;
        spielVorbei = false;
    }

    public void spielStarten(Spiel spiel) {
        ergebnis.spiel = spiel;

        reset();
    }

    public void empfangeTor(Tor tor) {
        if (spielVorbei) {
            return;
        }

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

        triggerSpielMode();
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
            Instance.runde.empfangeEndergebnis(ergebnis);
            spielRepository.save(ergebnis.spiel);
        }
    }

    public void seitenWechsel() {
        tauscheTeams = !tauscheTeams;
    }

    public SpielErgebnis getErgebnis() {
        return ergebnis;
    }
}
