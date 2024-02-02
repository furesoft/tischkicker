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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SpielPhase {

    int naechstSpielePhase;
    int turnierID;
    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamNameGetter teamNameGetter;

    /***
     * Diese Methode generiert die Liste der nächsten Spielphase sukzessiv basierend auf den aktuellen Gewinner des zuletzt gespielten Spiel.
     * Diese Methode sollte nur am Ende des Spiels ausgeführt werden.
     * -1 ist ein Platzhalter dafür, dass noch kein weiteres Team zugeteilt wurde.
     *
     * @param ergebnis Das Spielergebnis des zuletzt gespielten Spiels
     */
    public Spiel empfangeEndergebnis(SpielErgebnis ergebnis) throws TurnierBeendetException, IOException {
        Team gewinnerTeam = getGewinnerTeam(ergebnis);

        // wir bekommen das ergebnis mit der SpielID.
        // gewinnerErmittlung funktioniert.
        // TODO zurückgeben nächstes Spiel, damit Client aktualisiert.
        // Spiel muss nicht neu angelegt werden.

        // finde spiele mit spiele, das diese id beinhaltet.
        int spielIdBeendetesSpiel = ergebnis.spiel.getSpielID();
        Optional<Spiel> maybeNaechstesSpiel = findeSpieleMitNachfolgerVonSpiel(spielIdBeendetesSpiel, ergebnis.spiel.getTurnierID());

        if (!maybeNaechstesSpiel.isPresent()) {
            System.out.println("Spiel hat kein Nachfolger. Turnier ist beendet");
            throw new TurnierBeendetException();
        }

        turnierID = ergebnis.spiel.getTurnierID();
        naechstSpielePhase = ergebnis.spiel.getQualifikation();
        List<Spiel> alleSpiele = spielRepository.findAll();

        List<Spiel> spieleDerPhase = alleSpiele.stream().filter(s -> s.getQualifikation() == naechstSpielePhase)
                                               .filter(t -> t.getTurnierID() == turnierID).collect(Collectors.toList());

        List<Spiel> spieleVorbei = spieleDerPhase.stream().filter(s -> s.getGewinnerID() > 0).collect(Collectors.toList());

        if (spieleVorbei.size() == spieleDerPhase.size() - 1) {
            spieleDerPhase.removeAll(spieleVorbei);
            Spiel uebriegesSpiel = spieleDerPhase.get(0);

            for (int i = 0; i < spieleVorbei.size(); i++) {
                int tore1 = spieleVorbei.get(i).getToreteam1();
                int tore2 = spieleVorbei.get(i).getToreteam2();

                int teamID1 = uebriegesSpiel.getTeamIDs()[0];
                int teamID2 = uebriegesSpiel.getTeamIDs()[1];

                if (teamID1 == -2 || teamID2 == -2) {
                    int verliererTeamID;
                    if (tore1 > tore2) {
                        verliererTeamID = spieleVorbei.get(i).getTeamIDs()[1];
                    }
                    else {
                        verliererTeamID = spieleVorbei.get(i).getTeamIDs()[0];
                    }

                    boolean teamAufgegeben1 = hatTeamAufgegeben(spieleVorbei.get(i).getTeamIDs()[1]);
                    boolean teamAufgegeben2 = hatTeamAufgegeben(spieleVorbei.get(i).getTeamIDs()[0]);

                    if (!teamAufgegeben1 && !teamAufgegeben2) {
                        if (teamID1 == -1) {
                            teamID1 = verliererTeamID;
                            uebriegesSpiel.setTeams(teamID1, uebriegesSpiel.getTeamIDs()[1]);
                        }
                        else if (teamID2 == -2) {
                            teamID2 = verliererTeamID;
                            uebriegesSpiel.setTeams(uebriegesSpiel.getTeamIDs()[0], teamID2);
                        }

                        uebriegesSpiel = spielRepository.saveAndFlush(uebriegesSpiel);

                        String t1Name = teamNameGetter.getTeamName(teamID1);
                        String t2Name = teamNameGetter.getTeamName(teamID2);

                        uebriegesSpiel.setTeamNames(t1Name, t2Name);

                        break;
                    }
                }

                if (i == spieleVorbei.size()-1 && (teamID1 == -2 || teamID2 == -2))
                {
                    Spiel nachFolgeSpiel = findeSpieleMitNachfolgerVonSpiel(uebriegesSpiel.getSpielID(), uebriegesSpiel.getTurnierID()).get();
                    Optional<Team> uebrigesTeam = teamRepository.findById(uebriegesSpiel.getTeamIDs()[0]);
                    if(uebrigesTeam.isPresent())
                    {
                        teamNachfolgespielSetzen(nachFolgeSpiel, uebrigesTeam.get());
                    }
                    break;
                }
            }
        }

        //wenn Nachfolger, dann Nachfolgespiel aktualisieren.
        Spiel naechstesSpiel = maybeNaechstesSpiel.get();
        if(naechstesSpiel.getTeamIDs()[0]==-2 || naechstesSpiel.getTeamIDs()[1]==-2) {
            Spiel sonderFallCheck = (sonderfallCheck(naechstesSpiel, alleSpiele, gewinnerTeam));
            if (sonderFallCheck != null) {
                return sonderFallCheck;
            }
        }
        teamNachfolgespielSetzen(naechstesSpiel, gewinnerTeam);

        return naechstesSpiel;
    }
    public Spiel sonderfallCheck (Spiel naechstesSpiel, List<Spiel> alleSpiele, Team gewinnerTeam)
    {
        List<Spiel> naechsteSpielePhase = alleSpiele.stream().filter(s -> s.getQualifikation() == naechstesSpiel.getQualifikation())
                .filter(t -> t.getTurnierID() == naechstesSpiel.getTurnierID()).collect(Collectors.toList());

        List<Spiel> naechsteSpieleVorbeiPhase = naechsteSpielePhase.stream().filter(t -> t.getGewinnerID() > 0).collect(Collectors.toList());
        boolean spielCheckSonderfall = (naechstesSpiel.getTeamIDs()[0]==-2 || naechstesSpiel.getTeamIDs()[1]==-2)
                && (naechstesSpiel.getTeamIDs()[0]==-1 || naechstesSpiel.getTeamIDs()[1]==-1);
        if(naechsteSpielePhase.size()-1==naechsteSpieleVorbeiPhase.size() && spielCheckSonderfall)
        {
            //teamNachfolgespielSetzen(naechstesSpiel, gewinnerTeam);
            Spiel uebernaechstesSpiel = findeSpieleMitNachfolgerVonSpiel(naechstesSpiel.getSpielID(),naechstesSpiel.getTurnierID()).get();
            teamNachfolgespielSetzen(uebernaechstesSpiel,gewinnerTeam);
            return uebernaechstesSpiel;
        }
        return null;
    }
    public void teamNachfolgespielSetzen(Spiel nachfolgeSpiel, Team gewinner)
    {
        int team1 = nachfolgeSpiel.getTeamIDs()[0];
        int team2 = nachfolgeSpiel.getTeamIDs()[1];

        // -1 == noch nicht gespielt, -2 == bester-verlierer
        if (team1 < 0) {
            //setze Gewinner als team1
            team1 = gewinner.getId();
        }
        else {
            //setze Gewiiner Team2
            team2 = gewinner.getId();
        }

        nachfolgeSpiel.setTeams(team1, team2);

        nachfolgeSpiel = spielRepository.saveAndFlush(nachfolgeSpiel);

        String team1Name = teamNameGetter.getTeamName(team1);
        String team2Name = teamNameGetter.getTeamName(team2);

        nachfolgeSpiel.setTeamNames(team1Name, team2Name);
    }

    private Optional<Spiel> findeSpieleMitNachfolgerVonSpiel(int spielIdBeendetesSpiel, int turnierID) {
        List<Spiel> alleSpiele = spielRepository.findAll();

        //finde das Spiel, das die ID als Vorgänger beinhaltet.
        return alleSpiele.stream().filter(s -> s.getTurnierID() == turnierID).filter(spiel -> {
            int[] ids = spiel.getSpieleIDs();
            for (int i = 0; i < ids.length; i++) {
                int id = ids[i];

                if (id == spielIdBeendetesSpiel) {
                    return true;
                }
            }

            return false;
        }).findFirst();
    }


    private Team getGewinnerTeam(SpielErgebnis ergebnis) {
        if (ergebnis.teams[0].isAufgegeben()) {
            return ergebnis.teams[1];
        }
        if (ergebnis.teams[1].isAufgegeben()) {
            return ergebnis.teams[0];
        }
        if (ergebnis.toreTeam1 > ergebnis.toreTeam2) {
            return ergebnis.teams[0];
        }

        return ergebnis.teams[1];
    }


    private boolean hatTeamAufgegeben(int teamID) {
        Optional<Team> a = teamRepository.findById(teamID);

        return a.get().isAufgegeben();
    }

}
