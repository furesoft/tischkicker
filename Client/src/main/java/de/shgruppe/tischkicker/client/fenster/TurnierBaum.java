package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.App;
import de.shgruppe.tischkicker.client.Verbindungslinie;
import de.shgruppe.tischkicker.client.ui.Spielfeld;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TurnierBaum {

    static ArrayList<ArrayList<Spielfeld>> reihen = new ArrayList<>();
    public JFrame frame = new JFrame();
    Spielfeld selectedSpielfeld;
    Spielfeld aktuellesSpiel;
    JLabel hinweis = new JLabel();
    ArrayList<Spielfeld> spielfeldList = new ArrayList<>();
    ArrayList<Spielfeld> alleSpielfelder = new ArrayList<>();
    ArrayList<Verbindungslinie> linienListe = new ArrayList<>();
    JButton starteSpiel = new JButton("Spiel starten");
    JPanel panel;


    public TurnierBaum() {

        panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


        starteSpiel.setBounds(50, 20, 150, 50);


        panel.add(starteSpiel);
        hinweis.setBounds(300, 20, 250, 50);
        hinweis.setText("* = Zufälliger Verlierer dieser Spiel-Phase");

        panel.add(hinweis);


        frame.setSize(1920, 1080);
        int anzahlTeams = API.getTeams().length;
        //                   x=                  y=Fertig
        setpenelsize(anzahlTeams * 150 / 2, anzahlTeams * 100);

        // panel.setBackground(Color.GREEN);
        panel.setLayout(null);


        frame.add(scrollPane);


        starteSpiel.addActionListener(e -> {
            //TODO Probieren, ob aktuelles Spiel startbar ist.

            App.spielstandAnzeige.show();
            App.spielstandAnzeige.aktualisiereDaten(aktuellesSpiel.spiel);

            API.spielStarten(aktuellesSpiel.spiel);

        });

    }

    public int tunierbaumErstellen(double anzahlTeams, List<Spiel> spiele) {


        int spielfelderAnzahl = (int) Math.round(anzahlTeams / 2);
        int teamAnzahl = (int) anzahlTeams;
        double spielfelderAnzahlDouble; // Zwischenspeicher für die Berechnungen und den Abbruch der Do-While Schleife

        int y = 100; // Y-Koordinate für ein Spielfeld
        int x = 100; // X-Koordinate für ein Spielfeld

        // Zurücksetzen der Listen, bevor neue Reihen hinzugefügt werden
        reihen.clear();
        spielfeldList.clear();

        int beginIndexSpiele = 0;
        do {
            starteSpiel.setVisible(false);
            spielfeldListeFuellen(x, y, spielfelderAnzahl, beginIndexSpiele, spiele);
            beginIndexSpiele += spielfelderAnzahl;
            reihen.add(new ArrayList<>(spielfeldList));

            if (teamAnzahl % 2 != 0 && teamAnzahl > 2) {
                reihen.get(reihen.size() - 1).get(spielfeldList.size() - 1).besterVerlierer();
            }

            spielfeldList.clear(); // Zurücksetzen der spielfeldList für die nächste Reihe
            x += 200;

            teamAnzahl = Math.round((float) teamAnzahl / 2);
            spielfelderAnzahlDouble = (double) spielfelderAnzahl / 2;
            spielfelderAnzahl = (int) Math.round(spielfelderAnzahlDouble);

        } while (spielfelderAnzahlDouble > 0.5);

        //panel.setPreferredSize();

        for (int reihe = 0; reihe < reihen.size(); reihe++) {
            for (int spalte = 0; spalte < reihen.get(reihe).size(); spalte++) {
                if (reihe > 0) {
                    if (spalte * 2 + 1 < reihen.get(reihe - 1).size()) {
                        ArrayList<Spielfeld> tmpSpielfelder = reihen.get(reihe - 1);
                        int index = spalte * 2;
                        Spielfeld nSpielfeld = tmpSpielfelder.get(index + 1);

                        int newY = (tmpSpielfelder.get(index).background.getY() + nSpielfeld.background.getY() + nSpielfeld.background.getHeight()) / 2 - reihen.get(reihe)
                                                                                                                                                                .get(spalte).background.getHeight() / 2;

                        reihen.get(reihe).get(spalte).setY(newY);

                        linienListe.add(new Verbindungslinie(panel, tmpSpielfelder.get(index), tmpSpielfelder.get(index + 1), reihen.get(reihe)
                                                                                                                                    .get(spalte), 3));
                    }
                    else if (reihen.get(reihe).get(spalte).isBesterVerlierer()) {
                        ArrayList<Spielfeld> tmpSpielfelder = reihen.get(reihe);
                        Spielfeld spielfeld = tmpSpielfelder.get(spalte - 1);

                        tmpSpielfelder.get(spalte)
                                      .setY(spielfeld.background.getY() + spielfeld.background.getHeight() + 25);

                        linienListe.add(new Verbindungslinie(panel, reihen.get(reihe - 1).get(reihen.get(reihe - 1)
                                                                                                    .size() - 1), tmpSpielfelder.get(spalte), 3));
                    }
                }
            }
        }
        return teamAnzahl;
    }

    public Spielfeld getNaechstesSpielfeld() {
        for (Verbindungslinie linie : linienListe) {
            if (linie.quellSpielFeld1 == aktuellesSpiel || linie.quellSpielfeld2 == aktuellesSpiel) {
                return linie.ankunftsSpielfeld;
            }
        }

        return null;
    }

    public void setpenelsize(int x, int y) {
        // panel größe wird Festgelegt

        panel.setMinimumSize(new Dimension(x, y));
        panel.setMaximumSize(new Dimension(x, y));
        panel.setPreferredSize(new Dimension(x, y));
        panel.repaint();
    }

    /**
     * Füllt die Liste spielfeldList mit Spielfeldern
     *
     * @param x            X-Koordinate für die Spielfelder einer Reihe
     * @param y            Y-Koordinate für das erste Spielfeld einer Reihe
     * @param anzahlReihen Anzahl der Reihen
     */
    public void spielfeldListeFuellen(int x, int y, int anzahlReihen, int beginIndexSpiele, List<Spiel> spiele) {

        for (int i = 0; i < anzahlReihen; i++) {
            Spielfeld spielfeld = new Spielfeld(panel, x, y, 150, 100);
            spielfeld.spiel = spiele.get(beginIndexSpiele + i);
            spielfeldList.add(spielfeld);
            alleSpielfelder.add(spielfeld);

            y += 125;
        }

        for (Spielfeld spielfeld : spielfeldList) {
            spielfeld.background.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    spielfeldClicked(spielfeld);
                }
            });
        }
    }

    /*
     * Blockiert das aktuell angeklickte Spielfeld
     */
    public void lock() {
        selectedSpielfeld.background.setBackground(selectedSpielfeld.normal);

        aktuellesSpiel = null;
        selectedSpielfeld = null;
        starteSpiel.setVisible(false);
    }


    /**
     * Ermöglicht es auf ein Spielfeld im Turnierbaum zuzugreifen, um es zu befüllen. Siehe OneNote Doku
     *
     * @param spiel     Spiel, das in dem Spielfeld erscheinen soll
     * @param reihe     Reihe im Turnierbaum
     * @param spielfeld Spielfeld im Turnierbaum
     */
    public void spielfeldFuellen(Spiel spiel, int reihe, int spielfeld) {
        reihen.get(reihe).get(spielfeld).setTeamnames(spiel);

    }

    public void spielfeldClicked(Spielfeld spielfeld) {
        aktuellesSpiel = spielfeld;

        spielfeld.spiel = API.getSpiel(spielfeld.spiel.getSpielID());
        int[] teamids = spielfeld.spiel.getTeamIDs();


        if (teamids[0] < 0 || teamids[1] < 0 || spielfeld.spiel.getGewinner() > 0) {
            return;
        }

        if (selectedSpielfeld != null) {
            selectedSpielfeld.background.setBackground(spielfeld.normal);
        }
        else {
            spielfeld.background.setBackground(spielfeld.normal);
        }

        if (selectedSpielfeld == spielfeld) {
            selectedSpielfeld.background.setBackground(spielfeld.normal);
            starteSpiel.setVisible(false);
            selectedSpielfeld = null;

        }
        else {
            selectedSpielfeld = spielfeld;
            starteSpiel.setVisible(true);
            spielfeld.background.setBackground(spielfeld.selected);
        }

        frame.repaint();
        frame.revalidate();
    }

    public void ergebnisUebertragen(SpielErgebnis spielergebnis) {
        if (spielergebnis.spiel == null) {
            return;
        }

        for (Spielfeld feld : alleSpielfelder) {
            if (feld.spiel.getSpielID() == spielergebnis.spiel.getSpielID()) {
                feld.spiel = spielergebnis.spiel;
                feld.toreTeam1.setText(String.valueOf(spielergebnis.toreTeam1));
                feld.toreTeam2.setText(String.valueOf(spielergebnis.toreTeam2));

                break;
            }
        }
    }

    // wird aufgerufen um bei einem bereits laufenden Turnier den Turnierbaum richtig zu laden
    public void ergebnisAmAnfang(Spiel[] spiels) {
        // alle Spiele des Turniers
        for (int i = 0; i < spiels.length; i++) {
            // alle Spielfelder des Turnierbaums
            for (int h = 0; h < alleSpielfelder.size(); h++) {
                if (alleSpielfelder.get(h).spiel == null && i == h) {
                    alleSpielfelder.get(h).spiel = spiels[i];
                }
                if (alleSpielfelder.get(h).spiel == null) {
                    continue;
                }
                if (spiels[i].getSpielID() == alleSpielfelder.get(h).spiel.getSpielID()) {
                    alleSpielfelder.get(h).setTeamnames(spiels[i]);
                    if (spiels[i].getGewinner() > 0)
                    {
                        alleSpielfelder.get(h).toreTeam1.setText(String.valueOf((spiels[i].getToreteam1())));
                        alleSpielfelder.get(h).toreTeam2.setText(String.valueOf(spiels[i].getToreteam2()));
                        alleSpielfelder.get(h).setGewinner(spiels[i].getGewinner());
                    }
                    /*
                    if (spiels[i].getToreteam1() > 0 || spiels[i].getToreteam2() > 0) {
                        alleSpielfelder.get(h).toreTeam1.setText(String.valueOf((spiels[i].getToreteam1())));
                        alleSpielfelder.get(h).toreTeam2.setText(String.valueOf(spiels[i].getToreteam2()));
                        if (spiels[i].getToreteam1() == 10) {
                            alleSpielfelder.get(h).setGewinner(alleSpielfelder.get(h).spiel.getTeamIDs()[0]);
                        }
                        else if (spiels[i].getToreteam2() == 10) {
                            alleSpielfelder.get(h).setGewinner(alleSpielfelder.get(h).spiel.getTeamIDs()[1]);
                        }
                        else if (API.getTeam(spiels[i].getTeamIDs()[1]).isAufgegeben()) {
                            alleSpielfelder.get(h).setGewinner(alleSpielfelder.get(h).spiel.getTeamIDs()[0]);
                        }
                        else if (API.getTeam(spiels[i].getTeamIDs()[0]).isAufgegeben()) {
                            alleSpielfelder.get(h).setGewinner(alleSpielfelder.get(h).spiel.getTeamIDs()[1]);
                        }
                    }

                     */
                }
            }
        }
    }

    public void feldInitialisieren(Spiel spiel, Team team1) {
        for (int i = 0; i < alleSpielfelder.size(); i++) {
            if (alleSpielfelder.get(i).spiel.getSpielID() == spiel.getSpielID()) {

                int teamID2 = spiel.getTeamIDs()[1];
                String team1Name = String.valueOf(team1.getName());
                if (teamID2 == -1 || teamID2 == -2) {
                    alleSpielfelder.get(i).team1.setText(team1Name);
                }
                else {
                    alleSpielfelder.get(i).team2.setText(team1Name);
                }

                break;
            }
        }
    }

    public void setGewinner(Team gewinner, Spiel spiel) {
        Spielfeld feld = getSpielfeld(spiel.getSpielID());

        feld.setGewinner(gewinner.getId());
    }

    private Spielfeld getSpielfeld(int spielID) {
        for (Spielfeld feld : alleSpielfelder) {
            if (feld.spiel.getSpielID() == spielID) {
                return feld;
            }
        }

        return null;
    }

    public void updateTeamnames(List<Spiel> alleSpieleMitTeamnamen) {
        for (Spiel spiel : alleSpieleMitTeamnamen) {
            for (Spielfeld spielfeld : alleSpielfelder) {
                if (spielfeld.spiel.getSpielID() == spiel.getSpielID()) {
                    spielfeld.aktualisiereTeamNamen(spiel.getTeamNames());
                }
            }
        }

    }
}

