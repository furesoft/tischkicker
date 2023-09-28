package de.shgruppe.tischkicker.client.fenster;

import tischkicker.models.Team;

import javax.swing.*;
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
        int unterschied = 28;

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        treppchenPlatz2.setBounds(x, y, width, height);
        add(treppchenPlatz2);

        treppchenPlatz1.setBounds(x + width, (y - unterschied), width, (height + unterschied));
        add(treppchenPlatz1);

        treppchenPlatz3.setBounds((x + (2 * width)), (y + unterschied), width, (height - unterschied));
        add(treppchenPlatz3);

        setLocationRelativeTo(null);

        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getClassLoader().getResourceAsStream("podium.png").readAllBytes()));

        add(jlb);

        this.setResizable(false);
        this.pack();
    }

    public void setTeams(List<Team> teams) {
        treppchenPlatz1.setText(teams.get(0).getName());
        treppchenPlatz2.setText(teams.get(1).getName());
        treppchenPlatz3.setText(teams.get(2).getName());
    }

}
