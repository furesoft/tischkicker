package de.shgruppe.tischkicker.client;

import tischkicker.models.SpielErgebnis;

import javax.swing.*;
import java.awt.*;

public class AktuellerSpielstand {

    JLabel team1Name;
    JLabel team1Tore;
    JLabel team2Name;
    JLabel team2Tore;
    JButton toreTeam1Erhoehen;
    JButton toreTeam1Verringern;
    JButton toreTeam2Erhoehen;
    JButton toreTeam2Verringern;
    JButton manuellerSeitenwechsel;
    JLabel seitenwechsel;

    JFrame frame = new JFrame();

    public AktuellerSpielstand(int width, int height) {
        seitenwechsel = new JLabel("SEITENWECHSEL");
        seitenwechsel.setBounds(0, 0, width, (int) (height * 0.2));
        seitenwechsel.setOpaque(true);

        team1Name = new JLabel();
        team1Name.setBounds(0, seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team1Name.setOpaque(true);

        team2Name = new JLabel();
        team2Name.setBounds(team1Name.getWidth(), seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team2Name.setOpaque(true);

        toreTeam1Erhoehen = new JButton("+");
        toreTeam1Erhoehen.setBounds(0, team1Name.getY() + team1Name.getHeight(), (int) (width * 0.1), (int) (height * 0.25));
        toreTeam1Erhoehen.setOpaque(true);

        toreTeam1Verringern = new JButton("-");
        toreTeam1Verringern.setBounds(0, toreTeam1Erhoehen.getY() + toreTeam1Erhoehen.getHeight(), (int) (width * 0.1), (int) (height * 0.25));
        toreTeam1Verringern.setOpaque(true);

        team1Tore = new JLabel();
        team1Tore.setBounds(toreTeam1Erhoehen.getWidth(), toreTeam1Erhoehen.getY(), (int) (width * 0.4), (int) (height * 0.5));
        team1Tore.setOpaque(true);

        team2Tore = new JLabel();
        team2Tore.setBounds(team1Tore.getX() + team1Tore.getWidth(), team1Tore.getY(), team1Tore.getWidth(), team1Tore.getHeight());
        team2Tore.setOpaque(true);

        toreTeam2Erhoehen = new JButton("+");
        toreTeam2Erhoehen.setBounds(width - toreTeam1Erhoehen.getWidth() - 10, toreTeam1Erhoehen.getY(), toreTeam1Erhoehen.getWidth(), toreTeam1Erhoehen.getHeight());
        toreTeam2Erhoehen.setOpaque(true);

        toreTeam2Verringern = new JButton("-");
        toreTeam2Verringern.setBounds(width - toreTeam1Verringern.getWidth() - 10, toreTeam1Verringern.getY(), toreTeam1Verringern.getWidth(),
                toreTeam1Verringern.getHeight());
        toreTeam2Verringern.setOpaque(true);


        frame.add(seitenwechsel);
        frame.add(team1Name);
        frame.add(team2Name);
        frame.add(toreTeam1Erhoehen);
        frame.add(toreTeam2Erhoehen);
        frame.add(toreTeam1Verringern);
        frame.add(toreTeam2Verringern);
        frame.add(team1Tore);
        frame.add(team2Tore);


        frame.setSize(width + 6, height + 37);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void aktualisiereDaten(SpielErgebnis ergebnis) {
        team1Tore.setText(Integer.toString(ergebnis.toreTeam1));
        team2Tore.setText(Integer.toString(ergebnis.toreTeam2));

        String[] teamNames = ergebnis.spiel.getTeamNames();

        team1Name.setText(teamNames[0]);
        team1Name.setText(teamNames[1]);
    }

    public void show() {
        frame.setVisible(true);
    }
}
