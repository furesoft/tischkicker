package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;

public class TurnierAuswahlFenster extends JFrame {


    public TurnierAuswahlFenster() {


        Turnier[] alleTurniere = API.getTurniere();

        this.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600,400);
        panel.setBackground(new java.awt.Color(40, 44, 52));
        this.add(panel);
        panel.setVisible(true);

        JButton turnierErstellenButton = new JButton("Neues Turnier");

        turnierErstellenButton.setBackground(new java.awt.Color(165, 171, 179));

        turnierErstellenButton.addActionListener(e -> {
            API.erstelleTurnier();
            new TeamsInitialisierenFenster().setVisible(true);

        });



        turnierErstellenButton.setBounds(300, 100, 150, 50);
        panel.add(turnierErstellenButton);
        turnierErstellenButton.setVisible(true);



        JComboBox turniereComboBox = new JComboBox(alleTurniere);

        panel.add(turniereComboBox);
        turniereComboBox.setBackground(Color.WHITE);
        turniereComboBox.setForeground(Color.WHITE);
        turniereComboBox.setBounds(50, 100, 200, 50);
        turniereComboBox.setVisible(true);

        JButton turnierButton = new JButton("Turnier anzeigen ");

        turnierButton.setBackground(new java.awt.Color(165, 171, 179));
        turnierButton.setBounds(50, 200, 150, 50);
        panel.add(turnierButton);
        turnierButton.setVisible(true);



    }

}

