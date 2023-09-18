package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.App;
import tischkicker.models.Spiel;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

import static de.shgruppe.tischkicker.client.API.getSpieleFromServer;
import static de.shgruppe.tischkicker.client.API.turnierbaumGenerieren;

public class TurnierAuswahlFenster extends JFrame {


    static Turnier aktuellesTurnier;
    public TurnierAuswahlFenster() {

        Turnier[] alleTurniere = API.getTurniere();

        this.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setSize(400, 400);
        panel.setBackground(new java.awt.Color(40, 44, 52));
        this.add(panel);
        panel.setVisible(true);

        JComboBox turniereComboBox = new JComboBox(alleTurniere);
        panel.add(turniereComboBox);
        turniereComboBox.setBackground(Color.WHITE);
        turniereComboBox.setBounds(100, 100, 150, 50);
        turniereComboBox.setVisible(true);

        JButton turnierErstellenButton = new JButton("+");
        turnierErstellenButton.setSize(50, 50);
        turnierErstellenButton.addActionListener(e -> {
            aktuellesTurnier = API.erstelleTurnier();
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

        turnierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = ((Turnier)turniereComboBox.getSelectedItem())
                        .getID();
                var SpieleZuTurnier = Arrays.stream(getSpieleFromServer())
                        .filter(s -> s.getTurnierID() == id).collect(Collectors.toList());

                Spiel[] spiele = API.startTurnier(id);
                turnierbaumGenerieren(spiele);

                if (SpieleZuTurnier.size() != 0) {
                    App.turnierbaum.ergebnisAmAnfang(spiele);
                }
            }
        });


    }

}