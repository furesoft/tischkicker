package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;

public class AktuellerSpielstand {

    JLabel team1Name;
    JLabel team1Tore;
    JLabel team2Name;
    JLabel team2Tore;

    public AktuellerSpielstand(JFrame frame){
        team1Name = new JLabel("FC Bayern");
        team1Tore = new JLabel("3");
        team2Name = new JLabel("BVB");
        team2Tore = new JLabel("1");

        team1Name.setBounds(0,0,960,360);
        team1Name.setOpaque(true);
        team1Name.setBackground(new Color(209, 210, 209));
        team1Tore.setBounds(0,360,960,720);
        team1Tore.setOpaque(true);
        team1Tore.setBackground(Color.WHITE);

        team2Name.setBounds(960,0,960,360);
        team2Name.setOpaque(true);
        team2Name.setBackground(new Color(201, 197, 195));
        team2Tore.setBounds(960,360,960 , 720);
        team2Tore.setOpaque(true);
        team2Tore.setBackground(Color.WHITE);

        frame.add(team1Name);
        frame.add(team1Tore);
        frame.add(team2Name);
        frame.add(team2Tore);
    }


}
