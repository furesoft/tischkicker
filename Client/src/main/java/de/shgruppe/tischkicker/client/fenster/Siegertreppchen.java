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
        int x = 15;
        int y = 15;
        int width = 800;
        int height = 750;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();

        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getClassLoader().getResourceAsStream("podium.png").readAllBytes()));
        jlb.setSize(getWidth(), getHeight());

        add(jlb);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        treppchenPlatz2.setBounds(x, y, width, height);
        treppchenPlatz2.setForeground(Color.WHITE);
        add(treppchenPlatz2);

        treppchenPlatz1.setLocation(getWidth() / 2 - 50, getHeight() - 50);
        treppchenPlatz1.setForeground(Color.WHITE);
        add(treppchenPlatz1);

        treppchenPlatz3.setLocation(this.getWidth() / 2 - 50, this.getHeight() - 150);
        treppchenPlatz3.setForeground(Color.WHITE);
        add(treppchenPlatz3);

        setLocationRelativeTo(null);

        this.setResizable(false);
        this.setLocation(0, 0);
        this.pack();
    }

    public void setTeams(List<Team> teams) {
        treppchenPlatz1.setText(teams.get(0).getName());
        treppchenPlatz2.setText(teams.get(1).getName());
        treppchenPlatz3.setText(teams.get(2).getName());
    }

}
