package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.util.List;
import java.util.Optional;

@Component
public class SpielPhase {



    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamNameGetter teamNameGetter;

    /***
     * Diese Methode generiert die Liste der nächsten Spielphase sukzessiv basierend auf den aktuellen Gewinner des zuletzt gespielten Spiel.
     * Diese Methode sollte nur am Ende des Spiels ausgeführt werden.
     * -1 ist ein Platzhalter dafür, dass noch kein weiteres Team zugeteilt wurde.
     *
     * @param ergebnis Das Spielergebnis des zuletzt gespielten Spiels
     */
    public Spiel empfangeEndergebnis(SpielErgebnis ergebnis) throws KeinSpielVerfuegbarWeilTurnierBeendetException {
        Team gewinnerTeam = ermittleGewinnerTeam(ergebnis);

        // wir bekommen das ergebnis mit der SpielID.
        // gewinnerErmittlung funktioniert.
        // TODO zurückgeben nächstes Spiel, damit Client aktualisiert.
        // Spiel muss nicht neu angelegt werden.

        // finde spiele mit spiele, das diese id beinhaltet.
        int spielIdBeendetesSpiel = ergebnis.spiel.getSpielID();
        Optional<Spiel> maybeNaechstesSpiel = findeSpieleMitNachfolgerVonSpiel(spielIdBeendetesSpiel);

        if(!maybeNaechstesSpiel.isPresent()){
            System.out.println("Spiel hat kein Nachfolger. Turnier ist beendet");
            throw new KeinSpielVerfuegbarWeilTurnierBeendetException();
        }

        //wenn Nachfolger, dann Nachfolgespiel aktualisieren.
        Spiel naechstesSpiel = maybeNaechstesSpiel.get();

        int team1 = naechstesSpiel.getTeamIDs()[0];
        int team2 = naechstesSpiel.getTeamIDs()[1];

        // -1 == noch nicht gespielt, -2 == bester-verlierer
        if(team1 < 0){
            //setze Gewinner als team1
            team1 = gewinnerTeam.getId();
        }
        else {
            //setze Gewiiner Team2
            team2 = gewinnerTeam.getId();
        }

        naechstesSpiel.setTeams(team1, team2);

        naechstesSpiel = spielRepository.saveAndFlush(naechstesSpiel);

        String team1Name = teamNameGetter.getTeamName(team1);
        String team2Name = teamNameGetter.getTeamName(team1);

        naechstesSpiel.setTeamNames(team1Name, team2Name);

        return naechstesSpiel;
    }

    private Optional<Spiel> findeSpieleMitNachfolgerVonSpiel(int spielIdBeendetesSpiel) {

        List<Spiel> alleSpiele = spielRepository.findAll();

        //finde das Spiel, das die ID als Vorgänger beinhaltet.
        return alleSpiele.stream()
                .filter(spiel -> {
                    int[] ids = spiel.getSpieleIDs();
                    for(int i=0; i< ids.length; i++){
                        int id=ids[i];
                        if(id == spielIdBeendetesSpiel){
                            return true;
                        }
                    }
                    return false;
                })
                .findFirst();

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
