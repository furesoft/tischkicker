package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.ui.Colors;
import tischkicker.models.Team;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class Siegertreppchen extends Frame {

    JLabel treppchenPlatz1 = new JLabel("", SwingConstants.CENTER);
    JLabel treppchenPlatz2 = new JLabel("", SwingConstants.CENTER);
    JLabel treppchenPlatz3 = new JLabel("", SwingConstants.CENTER);

    public Siegertreppchen() throws IOException {
        int x = 15;
        int y = 15;
        int width = 800;
        int height = 750;
        int unterschied = 28;

        setSize(width, height);

        treppchenPlatz2.setBounds(x, y, width, height);
        treppchenPlatz2.setOpaque(true);
        treppchenPlatz2.setBackground(Colors.TreppchenZweiter);
        add(treppchenPlatz2);

        treppchenPlatz1.setBounds(x + width, (y - unterschied), width, (height + unterschied));
        treppchenPlatz1.setOpaque(true);
        treppchenPlatz1.setBackground(Colors.TreppchenErster);
        add(treppchenPlatz1);

        treppchenPlatz3.setBounds((x + (2 * width)), (y + unterschied), width, (height - unterschied));
        treppchenPlatz3.setOpaque(true);
        treppchenPlatz3.setBackground(Colors.TreppchenDritter);
        add(treppchenPlatz3);

        setLocationRelativeTo(null);

        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getResourceAsStream("podium.png").readAllBytes()));

        add(jlb);

        this.setResizable(false);
        this.pack();
    }

    public void setTeams(List<Team> teams) {
        treppchenPlatz1.setText("   #1 " + teams.get(0).getName());
        treppchenPlatz2.setText("   #2 " + teams.get(1).getName());
        treppchenPlatz3.setText("   #3 " + teams.get(2).getName());
    }

}
