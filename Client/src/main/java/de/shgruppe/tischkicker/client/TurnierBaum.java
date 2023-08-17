package de.shgruppe.tischkicker.client;

import tischkicker.models.Spiel;
import tischkicker.models.SpielErgebnis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TurnierBaum {

    static ArrayList<ArrayList<Spielfeld>> reihen = new ArrayList<>();
    JFrame frame = new JFrame();
    Spielfeld selectedSpielfeld;
    Spielfeld aktuellesSpiel;
    JLabel hinweis = new JLabel();
    ArrayList<Spielfeld> spielfeldList = new ArrayList<>();
    ArrayList<Spielfeld> alleSpielfelder = new ArrayList<>();
    ArrayList<Verbindungslinie> linienListe = new ArrayList<>();
    JButton starteSpiel = new JButton("Spiel starten");

    public TurnierBaum() {
        frame.setSize(1920, 1080);
        frame.setLayout(null);
        frame.setVisible(true);
        starteSpiel.setBounds(50,20,150,50);
        frame.add(starteSpiel);
        hinweis.setBounds(300,20,250,50);
        hinweis.setText("* = Bester Verlierer dieser Spiel-Phase");
        frame.add(hinweis);

        starteSpiel.addActionListener(e -> {
            Client.spielstandAnzeige.show();
            Client.spielstandAnzeige.aktualisiereDaten(aktuellesSpiel.spiel);

            Client.spielStarten(aktuellesSpiel.spiel);
        });
    }

    public void tunierbaumErstellen(double anzahlTeams){

        int spielfelderAnzahl = (int)Math.round(anzahlTeams/2);
        int teamAnzahl = (int) anzahlTeams;
        double spielfelderAnzahlDouble;

        int y = 100; // Y-Koordinate für ein Spielfeld
        int x = 100; // X-Koordinate für ein Spielfeld

        // Zurücksetzen der Listen, bevor neue Reihen hinzugefügt werden
        reihen.clear();
        spielfeldList.clear();

        do {
            starteSpiel.setVisible(false);
            spielfeldListeFuellen(x, y, spielfelderAnzahl);
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


    public void spielfeldListeFuellen(int x, int y, int anzahlReihen){
        for(int i = 0; i < anzahlReihen; i++){
            Spielfeld spielfeld = new Spielfeld(frame, x, y, 150, 100);
            spielfeldList.add(spielfeld);
            alleSpielfelder.add(spielfeld);

            y += 125;
        }
        for (Spielfeld spielfeld:spielfeldList) {
            spielfeld.background.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    spielfeldClicked(spielfeld);
                }
            });
        }

    }
    public void spielfeldFuellen(Spiel spiel, int reihe, int spielfeld){
        reihen.get(reihe).get(spielfeld).setTeams(spiel);
    }
    public void spielfeldClicked (Spielfeld spielfeld)
    {
        aktuellesSpiel = spielfeld;

        if (spielfeld.spiel == null || spielfeld.spiel.getTeamNames()[1] == null) {
            return;
        }

        if (selectedSpielfeld != null) {
            selectedSpielfeld.background.setBackground(spielfeld.normal);
        } else {
            spielfeld.background.setBackground(spielfeld.normal);
        }

        if (selectedSpielfeld == spielfeld) {
            selectedSpielfeld.background.setBackground(spielfeld.normal);
            starteSpiel.setVisible(false);
            selectedSpielfeld = null;

        } else {
            selectedSpielfeld = spielfeld;
            starteSpiel.setVisible(true);
            spielfeld.background.setBackground(spielfeld.selected);
        }

        frame.repaint();
        frame.revalidate();
    }

    public void ergebnisUebertragen(SpielErgebnis spielergebnis) {
        for (Spielfeld feld : alleSpielfelder) {
            if (feld.spiel.getSpielID() == spielergebnis.spiel.getSpielID()) {
                feld.spiel = spielergebnis.spiel;
                feld.toreTeam1.setText(String.valueOf(spielergebnis.toreTeam1));
                feld.toreTeam2.setText(String.valueOf(spielergebnis.toreTeam2));

                break;
            }
        }
    }
}

