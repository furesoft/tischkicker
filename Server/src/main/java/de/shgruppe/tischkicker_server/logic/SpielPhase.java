package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

@Component
public class SpielPhase {

    Spiel naechstesSpiel;

    @Autowired
    SpielRepository spielRepository;

    /***
     * Diese Methode generiert die Liste der nächsten Spielphase sukzessiv basierend auf den aktuellen Gewinner des zuletzt gespielten Spiel.
     * Diese Methode sollte nur am Ende des Spiels ausgeführt werden.
     * -1 ist ein Platzhalter dafür, dass noch kein weiteres Team zugeteilt wurde.
     *
     * @param ergebnis Das Spielergebnis des zuletzt gespielten Spiels
     */
    public Spiel empfangeEndergebnis(SpielErgebnis ergebnis) {
        Team gewinnerTeam = ermittleGewinnerTeam(ergebnis);

        if (naechstesSpiel != null && naechstesSpiel.getTeamIDs()[1] != -1) {
            naechstesSpiel = null;
        }

        if (naechstesSpiel == null) {
            naechstesSpiel = new Spiel();

            naechstesSpiel.setTeams(gewinnerTeam.getId(), -1); // -1 wird später durch ein anderes Team ersetzt
            naechstesSpiel.setTeamNames(gewinnerTeam.getName(), null);

            naechstesSpiel = spielRepository.saveAndFlush(naechstesSpiel);

            return naechstesSpiel;
        }

        naechstesSpiel.setTeams(naechstesSpiel.getTeamIDs()[0], gewinnerTeam.getId());
        naechstesSpiel.setTeamNames(naechstesSpiel.getTeamNames()[0], gewinnerTeam.getName());

        naechstesSpiel = spielRepository.saveAndFlush(naechstesSpiel);

        return naechstesSpiel;
    }


    private Team ermittleGewinnerTeam(SpielErgebnis ergebnis) {
        if (ergebnis.teams[0].isAufgegeben()) {
            return ergebnis.teams[1];
        }
        if (ergebnis.teams[1].isAufgegeben()){
            return ergebnis.teams[0];
        }
        if (ergebnis.toreTeam1 > ergebnis.toreTeam2) {
            return ergebnis.teams[0];
        }

        return ergebnis.teams[1];
    }
}
