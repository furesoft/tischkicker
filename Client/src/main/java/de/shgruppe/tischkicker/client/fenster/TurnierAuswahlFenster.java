package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;

public class TurnierAuswahlFenster extends JFrame {


    public TurnierAuswahlFenster() {

        Turnier[] alleTurniere = API.getTurniere();

        this.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setSize(400, 400);
        panel.setBackground(new java.awt.Color(40, 44, 52));
        this.add(panel);
        panel.setVisible(true);

        JButton turnierErstellenButton = new JButton("+");
        turnierErstellenButton.setSize(50, 50);
        turnierErstellenButton.addActionListener(e -> {
            API.erstelleTurnier();
            new TeamsInitialisierenFenster().setVisible(true);
        });


        turnierErstellenButton.setBounds(300, 100, 50, 50);
        panel.add(turnierErstellenButton);
        turnierErstellenButton.setVisible(true);


        JButton turnierButton = new JButton("Turnier anzeigen ");
        turnierButton.setSize(50, 50);
        turnierButton.setBackground(new java.awt.Color(165, 171, 179));
        turnierButton.setBounds(100, 200, 150, 50);
        panel.add(turnierButton);
        turnierButton.setVisible(true);


        JComboBox turniereComboBox = new JComboBox(alleTurniere);

        panel.add(turniereComboBox);
        turniereComboBox.setBackground(Color.WHITE);
        turniereComboBox.setBounds(100, 100, 150, 50);
        turniereComboBox.setVisible(true);
    }

}