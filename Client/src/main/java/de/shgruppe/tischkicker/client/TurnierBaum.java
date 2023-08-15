package de.shgruppe.tischkicker.client;

import tischkicker.models.Spiel;

import javax.swing.*;
import java.util.ArrayList;

public class TurnierBaum {

    static ArrayList<ArrayList<Spielfeld>> reihen = new ArrayList<>();
    ArrayList<Spielfeld> spielfeldList = new ArrayList<>();
    ArrayList<Verbindungslinie> linienListe = new ArrayList<>();

    public void tunierbaumErstellen(JFrame frame, double anzahlTeams){

        int spielfelderAnzahl = (int)Math.round(anzahlTeams/2);
        int teamAnzahl = (int) anzahlTeams;
        double spielfelderAnzahlDouble;

        int y = 100; // Y-Koordinate für ein Spielfeld
        int x = 100; // X-Koordinate für ein Spielfeld

        // Zurücksetzen der Listen, bevor neue Reihen hinzugefügt werden
        reihen.clear();
        spielfeldList.clear();

        do {
            spielfeldListeFuellen(frame, x, y, spielfelderAnzahl);
            reihen.add(new ArrayList<>(spielfeldList));

            if(teamAnzahl % 2 != 0 && teamAnzahl > 2){
                reihen.get(reihen.size()-1).get(spielfeldList.size()-1).besterVerlierer();
            }

            spielfeldList.clear(); // Zurücksetzen der spielfeldList für die nächste Reihe
            x += 200;

            teamAnzahl = Math.round((float) teamAnzahl /2);
            spielfelderAnzahlDouble = (double) spielfelderAnzahl / 2;
            spielfelderAnzahl = (int) Math.round(spielfelderAnzahlDouble);

        } while (spielfelderAnzahlDouble > 0.5);

        for(int reihe = 0; reihe < reihen.size(); reihe++){
            for(int spalte = 0; spalte < reihen.get(reihe).size(); spalte++){
               if(reihe > 0) {
                   if(spalte * 2 + 1 < reihen.get(reihe - 1).size()) {
                       ArrayList<Spielfeld> tmpSpielfelder = reihen.get(reihe - 1);
                       int index = spalte * 2;
                       Spielfeld nSpielfeld = tmpSpielfelder.get(index + 1);

                       int newY = (tmpSpielfelder.get(index).background.getY() + nSpielfeld.background.getY() + nSpielfeld.background.getHeight()) / 2
                               - reihen.get(reihe).get(spalte).background.getHeight() / 2;

                       reihen.get(reihe).get(spalte).setY(newY);

                       linienListe.add(new Verbindungslinie(frame, tmpSpielfelder.get(index),
                               tmpSpielfelder.get(index + 1), reihen.get(reihe).get(spalte),3));
                   }
                   else if(reihen.get(reihe).get(spalte).isBesterVerlierer()){
                       ArrayList<Spielfeld> tmpSpielfelder = reihen.get(reihe);
                       Spielfeld spielfeld = tmpSpielfelder.get(spalte - 1);

                       tmpSpielfelder.get(spalte).setY(spielfeld.background.getY() + spielfeld.background.getHeight() + 25);

                       linienListe.add(new Verbindungslinie(frame, reihen.get(reihe - 1).get(reihen.get(reihe - 1).size() - 1),
                               tmpSpielfelder.get(spalte), 3));
                   }
               }
            }
        }
    }


    public void spielfeldListeFuellen(JFrame frame, int x, int y, int anzhalSpiele){
        for(int i = 0; i < anzhalSpiele; i++){
            spielfeldList.add(new Spielfeld(frame, x, y, 150, 100));
            y += 125;
        }
    }
    public static void spielfeldFuellen(Spiel spiel, int reihe, int spielfeld){
        reihen.get(reihe).get(spielfeld).setTeams(spiel);
    }
}

