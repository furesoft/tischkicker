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

    int width = 50;
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
            treppchenPlatz3.setBounds(posDritter.x - getTeamsWidth(treppchenPlatz3)/2 , posDritter.y,width,height);
        }

        treppchenPlatz1.setBounds(posErster.x - getTeamsWidth(treppchenPlatz1) / 2, posErster.y, width, height);
        treppchenPlatz2.setBounds(posZweiter.x - getTeamsWidth(treppchenPlatz2) / 2, posZweiter.y, width, height);
    }

    //TODO
    // Bug bei längeren Namen, werden dann einfach nur 3 Punkte angezeigt

    public int getTeamsWidth(JLabel jLabel)
    {
        FontMetrics metrics = this.getGraphics().getFontMetrics(jLabel.getFont());

        return SwingUtilities.computeStringWidth(metrics, jLabel.getText());
    }

}
