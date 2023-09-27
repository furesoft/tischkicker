package de.shgruppe.tischkicker.client.ui;

import tischkicker.models.Team;

import javax.swing.*;
import java.awt.*;


public class Siegertreppchen extends Frame {

    JLabel treppchenPlatz1;
    JLabel erster;
    JLabel treppchenPlatz2;
    JLabel zweiter;
    JLabel treppchenPlatz3;
    JLabel dritter;

    public Siegertreppchen() {
        int x = 15;
        int y = 15;
        int width = 150;
        int height = 100;
        int unterschied = 28;

        treppchenPlatz2 = new JLabel("  #2");
        treppchenPlatz2.setBounds(x, y, width, height);
        treppchenPlatz2.setOpaque(true);
        treppchenPlatz2.setBackground(new Color(201, 197, 195));
        add(treppchenPlatz2);

        treppchenPlatz1 = new JLabel("  #1");
        treppchenPlatz1.setBounds(x + width, (y - unterschied), width, (height + unterschied));
        treppchenPlatz1.setOpaque(true);
        treppchenPlatz1.setBackground(new Color(149, 157, 158));
        add(treppchenPlatz1);

        treppchenPlatz3 = new JLabel("  #3");
        treppchenPlatz3.setBounds((x + (2 * width)), (y + unterschied), width, (height - unterschied));
        treppchenPlatz3.setOpaque(true);
        treppchenPlatz3.setBackground(new Color(209, 210, 209));
        add(treppchenPlatz3);
    }

    public void setTeams(Team[] teams) {
        treppchenPlatz1.setText(teams[0].getName());
        treppchenPlatz2.setText(teams[1].getName());
        treppchenPlatz3.setText(teams[2].getName());
    }

}
