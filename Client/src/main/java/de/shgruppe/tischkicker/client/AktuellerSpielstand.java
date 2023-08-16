package de.shgruppe.tischkicker.client;

import de.shgruppe.tischkicker.client.ui.DataButton;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AktuellerSpielstand {

    JLabel team1Name;
    JLabel toreLbl;
    JLabel team2Name;

    DataButton toreTeam1Erhoehen;
    DataButton toreTeam1Verringern;
    DataButton toreTeam2Erhoehen;
    DataButton toreTeam2Verringern;
    JButton manuellerSeitenwechsel;
    JLabel seitenwechsel;

    JFrame frame = new JFrame();

    int team1ID;
    int team2ID;

    public AktuellerSpielstand(int width, int height) {
        seitenwechsel = new JLabel("SEITENWECHSEL");
        seitenwechsel.setBounds(0, 0, width, (int) (height * 0.2));
        seitenwechsel.setOpaque(true);

        team1Name = new JLabel("a");
        team1Name.setBounds(50, seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team1Name.setOpaque(true);

        team2Name = new JLabel("b");
        team2Name.setBounds(width - 150, seitenwechsel.getHeight(), width / 2, (int) (height * 0.3));
        team2Name.setOpaque(true);

        toreTeam1Erhoehen = new DataButton("+");
        toreTeam1Erhoehen.setBounds(0, team1Name.getY() + team1Name.getHeight(), (int) (width * 0.1), (int) (height * 0.25));
        toreTeam1Erhoehen.setOpaque(true);
        toreTeam1Erhoehen.addActionListener(AktuellerSpielstand::buttonClick);

        toreTeam1Verringern = new DataButton("-");
        toreTeam1Verringern.setBounds(0, toreTeam1Erhoehen.getY() + toreTeam1Erhoehen.getHeight(), (int) (width * 0.1), (int) (height * 0.25));
        toreTeam1Verringern.setOpaque(true);
        toreTeam1Verringern.addActionListener(AktuellerSpielstand::buttonClick);

        toreLbl = new JLabel();
        toreLbl.setOpaque(true);
        toreLbl.setFont(new Font("Arial", 0, 50));


        toreTeam2Erhoehen = new DataButton("+");
        toreTeam2Erhoehen.setBounds(width - toreTeam1Erhoehen.getWidth() - 10, toreTeam1Erhoehen.getY(),
                toreTeam1Erhoehen.getWidth(), toreTeam1Erhoehen.getHeight());
        toreTeam2Erhoehen.setOpaque(true);
        toreTeam2Erhoehen.addActionListener(AktuellerSpielstand::buttonClick);

        toreTeam2Verringern = new DataButton("-");
        toreTeam2Verringern.setBounds(width - toreTeam1Verringern.getWidth() - 10, toreTeam1Verringern.getY(),
                toreTeam1Verringern.getWidth(), toreTeam1Verringern.getHeight());
        toreTeam2Verringern.setOpaque(true);
        toreTeam2Verringern.addActionListener(AktuellerSpielstand::buttonClick);


        frame.add(seitenwechsel);
        frame.add(team1Name);
        frame.add(team2Name);
        frame.add(toreTeam1Erhoehen);
        frame.add(toreTeam2Erhoehen);
        frame.add(toreTeam1Verringern);
        frame.add(toreTeam2Verringern);
        frame.add(toreLbl);

        frame.setSize(width + 6, height + 37);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        setTore("0 : 0");
    }

    private int getToreWidth() {
        FontMetrics metrics = frame.getGraphics().getFontMetrics(toreLbl.getFont());

        return SwingUtilities.computeStringWidth(metrics, toreLbl.getText());
    }

    private void setTore(String text) {
        toreLbl.setText(text);

        int fontWidth = getToreWidth();
        toreLbl.setBounds(frame.getWidth() / 2 - fontWidth / 2, toreTeam1Erhoehen.getY(), fontWidth, (int) (frame.getHeight() * 0.5));
    }

    private static void buttonClick(ActionEvent e) {
        DataButton btn = (DataButton) e.getSource();

        Client.Modus modus = btn.getText() == "+" ? Client.Modus.INCREMENT : Client.Modus.DECREMENT;

        try {
            Client.spielstandAnpassen((int) btn.getData(), modus);
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void aktualisiereDaten(SpielErgebnis ergebnis) {
        setTore(ergebnis.toreTeam1 + " : " + ergebnis.toreTeam2);

        Team[] teamNames = ergebnis.teams;

        team1Name.setText("<html>" + teamNames[0].getName() + "</html>");
        team2Name.setText("<html>" + teamNames[1].getName() + "</html>");

        team1ID = ergebnis.teams[0].getID();
        team2ID = ergebnis.teams[1].getID();

        toreTeam1Erhoehen.setData(team1ID);
        toreTeam1Verringern.setData(team1ID);

        toreTeam2Erhoehen.setData(team2ID);
        toreTeam2Verringern.setData(team2ID);
    }

    public void show() {
        frame.setVisible(true);
    }
}
