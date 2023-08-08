package de.shgruppe.tischkicker_server.logic;

import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.util.ArrayList;
import java.util.Collections;

public class Baumerfassung {

    //Testweise erstelltes Team-Array
    ArrayList<Team> teamManager = new ArrayList<Team>();

    //Booleans zur erkennung ob gerade/ungerade.
    boolean teamanzahlGerade = false;

    public Baumerfassung() {

    }

    private void erhalteTeams() {
        //Schnittstelle zum Erhalten der Teams
        //TeamManager.getTeamManagers();
        //          TO BE CONTINUED

        //Für TESTS:
        Team Haus = new Team();
        Team Vasilidavid = new Team();
        Team ManchesterUnited = new Team();
        Team DKMM = new Team();

        teamManager.add(Haus);
        teamManager.add(Vasilidavid);
        teamManager.add(ManchesterUnited);
        teamManager.add(DKMM);
        teamanzahlVerarbeitung();
    }

    private void teamanzahlVerarbeitung() {
        teamanzahlGerade = teamManager.size() % 2 == 0;

        generiereDurchgang();
    }

    public ArrayList generiereIDList() {
        //Erstellen einer Liste mit den ID´s der Teams für die Spiellisten
        ArrayList<Integer> idlist = new ArrayList<Integer>();
        for (int i = 0; i < teamManager.size(); i++) {
            idlist.add(teamManager.get(i).getID());
        }

        return idlist;
    }

    public ArrayList generiereSpielList() {
        ArrayList<Spiel> spiellist = new ArrayList<Spiel>();
        for (int i = 0; i < teamManager.size(); i++) {
        }

        return spiellist;
    }

    public int generiereDurchgang() {
        //Spiel-Array mithilfe von ID-Array mit Teams befüllen
        //Wenn teamanzahlUngerade = true --> die letze ID abnehmen und in zwischenvariable für
        //        // nächsten Durchgang speichern

        ArrayList<Spiel> spiele = generiereSpielList();
        ArrayList<Integer> ids = generiereIDList();

        Spiel test = new Spiel(ids.get(0) + "," + ids.get(1));

        //ToDo: implementiere durchgang
        return 0;
    }

    public ArrayList teamsVerschiebenRandom(ArrayList<Integer> idlist) {
        //random verschieben --> Arraysortrandom mit ID-Array und dann wieder einfügen über generiereDurchgang
        Collections.shuffle(idlist);
        //funktioniert das? :D
        return idlist;
    }

    public ArrayList teamsVerschiebenEinzeln(ArrayList<Integer> idlist, int id1, int id2) {
        //zwischenvariable und erfassung der zu den teams passenden id´s im Spiel-Array.teamid1 und 2 über for-schleife
        int listenstelleid1 = 0;
        int listenstelleid2 = 0;
        for (int i = 0; i < idlist.size(); i++) {
            if (idlist.get(i) == id1) {
                listenstelleid1 = i;
            }
            if (idlist.get(i) == id2) {
                listenstelleid2 = i;
            }

            idlist.set(listenstelleid1, id2);
            idlist.set(listenstelleid2, id1);
        }

        return idlist;
    }

}