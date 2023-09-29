package de.shgruppe.tischkicker.client.ui;

import tischkicker.models.Spiel;

import javax.swing.*;
import java.awt.*;

public class Spielfeld {
    public JLabel background;
    public Spiel spiel;
    public JLabel team1;
    public JLabel team2;
    public JLabel toreTeam1;
    public JLabel toreTeam2;

    boolean besterVerlierer = false;
    JPanel frame;

    /**
     * Erstellt ein neues Spielfeld.
     *
     * @param frame  gibt das JPanel an, in dem das Spielfeld erscheinen soll.
     * @param x      gibt die X-Koordinate eines Spielfeldes an.
     * @param y      gibt die Y-Koordinate eines Spielfeldes an.
     * @param width  gibt die Breite eines Spielfelds an.
     * @param height gibt die Höhe eines Spielfelds an.
     */
    public Spielfeld(JPanel frame, int x, int y, int width, int height) {
        this.frame = frame;

        background = new JLabel(" ");

        team1 = new JLabel(" ");
        toreTeam1 = new JLabel(" ");

        team2 = new JLabel(" ");
        toreTeam2 = new JLabel(" ");

        background.setBounds(x, y, width, height);
        // Macht den Hintergrund undurchsichtig.
        background.setOpaque(true);
        // Setzt die Hintergrundfarbe des Spielfeld-Hintergrunds.
        background.setBackground(Colors.SPIELFELD_NORMAL);

        // Setzt die Position, Größe und Hintergrundfarbe des ersten Teams.
        team1.setBounds(x + 5, y + 5, width - 10, ((height - 10) / 2));
        team1.setOpaque(true);
        team1.setBackground(new Color(209, 210, 209));
        toreTeam1.setBounds(x + width - 30, y + 5, 25, ((height - 10) / 2));
        toreTeam1.setOpaque(true);
        toreTeam1.setBackground(Colors.SpielfeldName);

        // Setzt die Position, Größe und Hintergrundfarbe des zweiten Teams.
        team2.setBounds(x + 5, (int) (y + 5 + (height * 0.9) / 2), width - 10, ((height - 10) / 2));
        team2.setOpaque(true);
        team2.setBackground(new Color(201, 197, 195));
        toreTeam2.setBounds(x + width - 30, (int) (y + 5 + (height * 0.9) / 2), 25, ((height - 10) / 2));
        toreTeam2.setOpaque(true);
        toreTeam2.setBackground(Colors.SpielfeldName);

        // Fügt die Anzeigen für die Tore der beiden Teams und die Teamnamen dem JPanel hinzu.
        frame.add(toreTeam1);
        frame.add(toreTeam2);
        frame.add(team1);
        frame.add(team2);
        frame.add(background);
    }

    /**
     * Diese Methode ändert die Hintergrundfarbe Gewinnerteams auf Grün
     *
     * @param id id des Gewinnerteams
     */
    public void setGewinner(int id) {
        if (spiel.getTeamIDs()[0] == id) {
            team1.setBackground(Colors.GEWINNER);
        }
        else {
            team2.setBackground(Colors.GEWINNER);
        }

        frame.repaint();
    }

    public void setTeamnames(Spiel spiel) {
        setTeams(spiel);
    }

    /**
     * Aktualisiert die Anzeige der Teamnamen in der GUI basierend auf den Informationen aus dem Spiel-Objekt.
     *
     * @param spiel Ein Spiel-Objekt, das die benötigten Informationen über die Teams enthält.
     */
    public void setTeams(Spiel spiel) {
        String teamName;
        if (spiel.getTeamNames() == null) {
            teamName = "";
        }
        else {
            teamName = spiel.getTeamNames()[1];
            if (teamName == null) {
                teamName = "";
            }
        }

        team1.setText(spiel.getTeamNames()[0]);
        toolTip(team1, team1.getText());

        if (besterVerlierer) {
            team2.setText(" * " + teamName);
        }
        else {
            team2.setText(spiel.getTeamNames()[1]);
            toolTip(team2, team2.getText());
        }
    }

    public void aktualisiereTeamNamen(String[] teamNames) {
        var teamname1 = teamNames[0];
        var teamname2 = teamNames[1];

        team1.setText(teamname1);
        team2.setText(teamname2);
    }

    public void toolTip(JLabel label, String text) {
        if (label == null) {
            return;
        }
        if (text == null) {
            return;
        }
        if (text.length() > 15) {
            label.setToolTipText(text);
        }

    }

    /**
     * Verschiebt verschiedene GUI-Komponenten vertikal basierend auf dem angegebenen y-Wert.
     *
     * @param y Der vertikale Versatzwert.
     */
    public void setY(int y) {
        background.setBounds(background.getX(), y, background.getWidth(), background.getHeight());

        team1.setBounds(team1.getX(), y + 5, team1.getWidth(), team1.getHeight());
        team2.setBounds(team2.getX(), (int) (y + 5 + (background.getHeight() * 0.9) / 2), team2.getWidth(), team2.getHeight());

        toreTeam1.setBounds(toreTeam1.getX(), y + 5, toreTeam1.getWidth(), toreTeam1.getHeight());
        toreTeam2.setBounds(toreTeam2.getX(), (int) (y + 5 + (background.getHeight() * 0.9) / 2), toreTeam2.getWidth(), toreTeam2.getHeight());
    }

    public void besterVerlierer() {
        besterVerlierer = true;
        team2.setText(" *");
    }

    /**
     * Gibt zurück, ob es sich um den besten Verlierer handelt.
     *
     * @return {@code true}, wenn es sich um den besten Verlierer handelt, ansonsten {@code false}.
     */
    public boolean isBesterVerlierer() {
        return besterVerlierer;
    }
}
