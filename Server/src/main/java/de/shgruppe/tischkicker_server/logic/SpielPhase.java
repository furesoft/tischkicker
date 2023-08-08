package de.shgruppe.tischkicker_server.logic;

import tischkicker.models.Spiel;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Team;

import java.util.ArrayList;
import java.util.List;

public class SpielPhase {
    public List<Spiel> aktuelleSpiele = new ArrayList<>();
    public List<Spiel> naechstenSpiele = new ArrayList<>();


    /***
     * Diese Methode generiert die Liste der nächsten Spielphase sukzessiv basierend auf den aktuellen Gewinner des zuletzt gespielten Spiel.
     * Diese Methode sollte nur am Ende des Spiels ausgeführt werden.
     * -1 ist ein Platzhalter dafür, dass noch kein weiteres Team zugeteilt wurde.
     *
     * @param ergebnis Das Spielergebnis des zuletzt gespielten Spiels
     */
    public void empfangeEndergebnis(SpielErgebnis ergebnis) {
        for (Spiel spiel : aktuelleSpiele) {
            if (spiel.getSpielID() == ergebnis.spiel.getSpielID()) {
                Team gewinnerTeam = ermittleGewinnerTeam(ergebnis);

                Spiel letztesSpiel = naechstenSpiele.get(naechstenSpiele.size() - 1);

                if (letztesSpiel.getTeamIDs()[1] == -1) {
                    letztesSpiel.setTeams(letztesSpiel.getTeamIDs()[0], gewinnerTeam.getID());
                }
                else {
                    Spiel naechstesSpiel = new Spiel();
                    naechstesSpiel.setTeams(gewinnerTeam.getID(), -1); // -1 wird später durch ein anderes Team ersetzt

                    naechstenSpiele.add(spiel);
                }
                break;
            }
        }
    }

    /***
     * Bereitet interne Liste vor für die nächste Spielphase
     * @return Gibt die Spiele der nächsten Spielphase zurück
     */
    public List<Spiel> naechstePhase() {
        List<Spiel> tmp = naechstenSpiele;

        aktuelleSpiele = naechstenSpiele;
        naechstenSpiele.clear();

        return tmp;
    }

    private Team ermittleGewinnerTeam(SpielErgebnis ergebnis) {
        if (ergebnis.toreTeam1 > ergebnis.toreTeam2) {
            return ergebnis.teams[0];
        }

        return ergebnis.teams[1];
    }
}
