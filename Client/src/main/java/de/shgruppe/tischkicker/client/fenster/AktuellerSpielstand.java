package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.ui.Colors;
import de.shgruppe.tischkicker.client.ui.DataButton;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AktuellerSpielstand {

    JLabel team1Name, toreLbl, team2Name;

    DataButton toreTeam1Erhoehen, toreTeam1Verringern, toreTeam2Erhoehen, toreTeam2Verringern;


    JPanel farbanzeigeTeam1 = new JPanel(), farbanzeigeTeam2 = new JPanel();

    JButton seitenwechsel;

    DataButton aufgebenTeam1Btn = new DataButton("X");
    DataButton aufgebenTeam2Btn = new DataButton("X");

    JFrame frame = new JFrame();

    int team1ID, team2ID;

    public AktuellerSpielstand(int width, int height) {
        seitenwechsel = new DataButton("<-->");
        seitenwechsel.setToolTipText("Seitenwechsel");
        seitenwechsel.setBounds(0, 0, width, (int) (height * 0.2));
        seitenwechsel.setOpaque(true);
        seitenwechsel.addActionListener(e -> {
            try {
                API.seitenwechsel();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        team1Name = new JLabel("a");
        team1Name.setBounds(50, seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team1Name.setOpaque(true);
        team1Name.setBackground(Colors.BACKGROUND);
        team1Name.setForeground(Colors.SCHRIFT);

        team2Name = new JLabel("b");
        team2Name.setBounds(width - 150, seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team2Name.setOpaque(true);
        team2Name.setBackground(Colors.BACKGROUND);
        team2Name.setForeground(Colors.SCHRIFT);

        farbanzeigeTeam1.setBounds(team1Name.getX() - 25, team1Name.getY() + 70, 15, 15);
        farbanzeigeTeam1.setBackground(Color.WHITE);

        farbanzeigeTeam2.setBounds(team2Name.getX() - 25, team2Name.getY() + 70, 15, 15);
        farbanzeigeTeam2.setBackground(Color.RED);

        toreTeam1Erhoehen = new DataButton("+");
        toreTeam1Erhoehen.setToolTipText("Spielstand erhöhen");
        toreTeam1Erhoehen.setBounds(0, team1Name.getY() + team1Name.getHeight(), (int) (width * 0.1), (int) (height * 0.25));
        toreTeam1Erhoehen.setOpaque(true);
        toreTeam1Erhoehen.addActionListener(AktuellerSpielstand::buttonClick);

        toreTeam1Verringern = new DataButton("-");
        toreTeam1Verringern.setToolTipText("Spielstand verringern");
        toreTeam1Verringern.setBounds(0, toreTeam1Erhoehen.getY() + toreTeam1Erhoehen.getHeight(), (int) (width * 0.1), (int) (height * 0.25) - 50);
        toreTeam1Verringern.setOpaque(true);
        toreTeam1Verringern.addActionListener(AktuellerSpielstand::buttonClick);

        toreLbl = new JLabel();
        toreLbl.setOpaque(true);
        toreLbl.setFont(new Font("Arial", 0, 50));
        toreLbl.setBackground(Colors.BACKGROUND);
        toreLbl.setForeground(Colors.SCHRIFT);

        toreTeam2Erhoehen = new DataButton("+");
        toreTeam2Erhoehen.setToolTipText("Spielstand erhöhen");
        toreTeam2Erhoehen.setBounds(width - toreTeam1Erhoehen.getWidth() - 10, toreTeam1Erhoehen.getY(), toreTeam1Erhoehen.getWidth(), toreTeam1Erhoehen.getHeight());
        toreTeam2Erhoehen.setOpaque(true);
        toreTeam2Erhoehen.addActionListener(AktuellerSpielstand::buttonClick);

        toreTeam2Verringern = new DataButton("-");
        toreTeam2Verringern.setToolTipText("Spielstand verringern");
        toreTeam2Verringern.setBounds(width - toreTeam1Verringern.getWidth() - 10, toreTeam1Verringern.getY(), toreTeam1Verringern.getWidth(), toreTeam1Verringern.getHeight());
        toreTeam2Verringern.setOpaque(true);
        toreTeam2Verringern.addActionListener(AktuellerSpielstand::buttonClick);

        aufgebenTeam1Btn.setToolTipText("Aufgeben");
        aufgebenTeam1Btn.setBounds(toreTeam1Verringern.getX(), height - 50, toreTeam1Erhoehen.getWidth(), 50);
        aufgebenTeam1Btn.setOpaque(true);

        aufgebenTeam2Btn.setToolTipText("Aufgeben");
        aufgebenTeam2Btn.setBounds(toreTeam2Verringern.getX(), height - 50, toreTeam2Erhoehen.getWidth(), 50);
        aufgebenTeam2Btn.setOpaque(true);

        JPanel contentPanel = new JPanel();

        contentPanel.add(seitenwechsel);
        contentPanel.add(team1Name);
        contentPanel.add(team2Name);
        contentPanel.add(toreTeam1Erhoehen);
        contentPanel.add(toreTeam2Erhoehen);
        contentPanel.add(toreTeam1Verringern);
        contentPanel.add(toreTeam2Verringern);
        contentPanel.add(toreLbl);

        contentPanel.add(farbanzeigeTeam1);
        contentPanel.add(farbanzeigeTeam2);

        contentPanel.add(aufgebenTeam1Btn);
        contentPanel.add(aufgebenTeam2Btn);

        contentPanel.setSize(width + 6, height + 37);
        contentPanel.setLayout(null);
        frame.setSize(contentPanel.getSize());
        frame.setLayout(null);
        frame.setResizable(false);

        aufgebenTeam1Btn.addActionListener(this::aufgebenClick);

        aufgebenTeam2Btn.addActionListener(this::aufgebenClick);

        contentPanel.setBackground(Colors.BACKGROUND);

        this.frame.add(contentPanel);
    }

    private static void buttonClick(ActionEvent e) {
        DataButton btn = (DataButton) e.getSource();

        API.Modus modus = btn.getText() == "+" ? API.Modus.increment : API.Modus.decrement;

        try {
            API.spielstandAnpassen((int) btn.getData(), modus);
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Color getTeamFarbe(Tor.Seite seite) {
        return seite == Tor.Seite.ROT ? Color.RED : Color.WHITE;
    }

    private int getToreWidth() {
        FontMetrics metrics = frame.getGraphics().getFontMetrics(toreLbl.getFont());

        return SwingUtilities.computeStringWidth(metrics, toreLbl.getText());
    }

    private void setTore(String text) {
        toreLbl.setText(text);

        int fontWidth = getToreWidth();

        toreLbl.setBounds(frame.getWidth() / 2 - fontWidth / 2, toreTeam1Erhoehen.getY() - 13, fontWidth, (int) (frame.getHeight() * 0.5));
    }

    public void aktualisiereDaten(SpielErgebnis ergebnis) {
        setTore(ergebnis.toreTeam1 + " : " + ergebnis.toreTeam2);

        Team[] teamNames = ergebnis.teams;

        team1Name.setText("<html>" + teamNames[0].getName() + "</html>");
        team2Name.setText("<html>" + teamNames[1].getName() + "</html>");

        if (ergebnis.spiel == null) {
            return;
        }

        int[] ids = ergebnis.spiel.getTeamIDs();
        team1ID = ids[0];
        team2ID = ids[1];

        toreTeam1Erhoehen.setData(team1ID);
        toreTeam1Verringern.setData(team1ID);
        aufgebenTeam1Btn.setData(team1ID);

        toreTeam2Erhoehen.setData(team2ID);
        toreTeam2Verringern.setData(team2ID);
        aufgebenTeam2Btn.setData(team2ID);

        farbanzeigeTeam1.setBackground(getTeamFarbe(ergebnis.seiteTeam1));
        farbanzeigeTeam2.setBackground(getTeamFarbe(ergebnis.seiteTeam2));

        frame.repaint();
    }

    private void aufgebenClick(ActionEvent e) {
        DataButton btn = (DataButton) e.getSource();

        try {
            API.aufgeben((int) btn.getData());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void aktualisiereDaten(Spiel spiel) {
        setTore("0 : 0");

        String[] teamNames = spiel.getTeamNames();
        int[] teamIds = spiel.getTeamIDs();

        team1Name.setText("<html>" + teamNames[0] + "</html>");
        team2Name.setText("<html>" + teamNames[1] + "</html>");

        team1ID = teamIds[0];
        team2ID = teamIds[1];

        toreTeam1Erhoehen.setData(team1ID);
        toreTeam1Verringern.setData(team1ID);

        toreTeam2Erhoehen.setData(team2ID);
        toreTeam2Verringern.setData(team2ID);
    }

    public void show() {
        frame.setVisible(true);

        setTore("0 : 0");
    }

    public void hide() {
        frame.setVisible(false);
    }
}
