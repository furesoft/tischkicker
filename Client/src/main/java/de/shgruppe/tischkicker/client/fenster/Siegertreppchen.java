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

    private Point posErster = new Point(640,620);
    private Point posZweiter = new Point(1090,700);
    private Point posDritter = new Point(190,800);

    int height = 50;
    int textsize = 50;

    public Siegertreppchen() throws IOException {

        // Bild ist 1305*900 groß

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getClassLoader().getResourceAsStream("podium.png").readAllBytes()));

        add(jlb);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        treppchenPlatz2.setForeground(Color.WHITE);
        treppchenPlatz2.setFont(new Font("Arial", 0,textsize));
        jlb.add(treppchenPlatz2);

        treppchenPlatz1.setForeground(Color.WHITE);
        treppchenPlatz1.setFont(new Font("Arial", 0,textsize));
        jlb.add(treppchenPlatz1);

        treppchenPlatz3.setForeground(Color.WHITE);
        treppchenPlatz3.setFont(new Font("Arial", 0,textsize));
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
            int platz3width = getTeamsWidth(treppchenPlatz3);
            treppchenPlatz3.setBounds(posDritter.x - platz3width/2 , posDritter.y,platz3width,height);
        }
        int platz1width = getTeamsWidth(treppchenPlatz1);
        treppchenPlatz1.setBounds(posErster.x - platz1width / 2, posErster.y, platz1width, height);
        int platz2width = getTeamsWidth(treppchenPlatz2);
        treppchenPlatz2.setBounds(posZweiter.x - platz2width / 2, posZweiter.y, platz2width, height);
    }


    public int getTeamsWidth(JLabel jLabel)
    {
        FontMetrics metrics = this.getGraphics().getFontMetrics(jLabel.getFont());

        return SwingUtilities.computeStringWidth(metrics, jLabel.getText());
    }

}
