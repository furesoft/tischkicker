package de.shgruppe.tischkicker.client.fenster;

import tischkicker.models.Team;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class Siegertreppchen extends JFrame {

    JLabel treppchenPlatz1 = new JLabel("", SwingConstants.CENTER);
    JLabel treppchenPlatz2 = new JLabel("", SwingConstants.CENTER);
    JLabel treppchenPlatz3 = new JLabel("", SwingConstants.CENTER);

    public Siegertreppchen() throws IOException {

        // Bild ist 1305*900 groß

        int x = 1000;
        int y = 700;
        int width = 50;
        int height = 50;

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getClassLoader().getResourceAsStream("podium.png").readAllBytes()));

        add(jlb);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        treppchenPlatz2.setBounds(x,y, width, height);
        treppchenPlatz2.setForeground(Color.WHITE);
        jlb.add(treppchenPlatz2);

        treppchenPlatz1.setBounds(x-450,y-80,width,height);
        treppchenPlatz1.setForeground(Color.WHITE);
        jlb.add(treppchenPlatz1);

        treppchenPlatz3.setBounds(x-900,y+100,width,height);
        treppchenPlatz3.setForeground(Color.WHITE);
        jlb.add(treppchenPlatz3);

        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLocation(0, 0);
        this.pack();
    }

    public void setTeams(List<Team> teams) {
        treppchenPlatz1.setText(teams.get(0).getName());
        treppchenPlatz2.setText(teams.get(1).getName());
        if (teams.get(2) != null)
        {
            treppchenPlatz3.setText(teams.get(2).getName());
        }
    }
    public int getTeamsWidth(JLabel jLabel)
    {
        FontMetrics metrics = this.getGraphics().getFontMetrics(jLabel.getFont());

        return SwingUtilities.computeStringWidth(metrics, jLabel.getText());
    }

}
