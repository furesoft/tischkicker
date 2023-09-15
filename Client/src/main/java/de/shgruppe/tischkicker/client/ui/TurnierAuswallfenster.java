package de.shgruppe.tischkicker.client.ui;
import de.shgruppe.tischkicker.client.Client;
import de.shgruppe.tischkicker.client.TeamApp;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnierAuswallfenster extends JFrame {



    public  TurnierAuswallfenster() {

        Turnier[] alleTurniere = Client.getTurniere();


        this.setSize(400,400);

        JPanel ebeene2 = new JPanel();
        ebeene2.setSize(400, 400);
        ebeene2.setBackground(new java.awt.Color(40, 44, 52));
        this.add(ebeene2);
        ebeene2.setVisible(true);

        JButton plussbuten = new JButton("+");
        plussbuten.setSize(50, 50);
        plussbuten.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.erstelleTurnier();
                new TeamApp().setVisible(true);
            }
          });


        plussbuten.setBounds(300, 100, 50, 50);
        ebeene2.add(plussbuten);
        plussbuten.setVisible(true);


        JButton turnierButton = new JButton("Turnier anzeigen ");
        turnierButton.setSize(50, 50);
        turnierButton.setBackground(new java.awt.Color(165, 171, 179));
        turnierButton.setBounds(100, 200, 150, 50);
        ebeene2.add(turnierButton);
        turnierButton.setVisible(true);


        JComboBox dropDownFuerDatum = new JComboBox(alleTurniere);

        ebeene2.add(dropDownFuerDatum);
        dropDownFuerDatum.setBackground(Color.WHITE);
        dropDownFuerDatum.setBounds(100, 100, 150, 50);
        dropDownFuerDatum.setVisible(true);

    }





}