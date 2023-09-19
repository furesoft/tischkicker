package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.App;
import de.shgruppe.tischkicker.client.ui.Colors;
import tischkicker.models.Spiel;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static de.shgruppe.tischkicker.client.API.getSpieleFromServer;
import static de.shgruppe.tischkicker.client.API.turnierbaumGenerieren;

public class TurnierAuswahlFenster extends JFrame {


    public static Turnier aktuellesTurnier;

    public TurnierAuswahlFenster() {


        Turnier[] alleTurniere = API.getTurniere();

        this.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 400);
        panel.setBackground(Colors.BACKGROUND);
        this.add(panel);
        panel.setVisible(true);

        JButton turnierErstellenButton = new JButton("Neues Turnier");

        turnierErstellenButton.setBackground(new java.awt.Color(165, 171, 179));

        turnierErstellenButton.addActionListener(e -> {
            aktuellesTurnier = API.erstelleTurnier();
            new TeamsInitialisierenFenster().setVisible(true);

        });


        JButton turnierButton = new JButton("Turnier anzeigen");
        turnierButton.setSize(50, 50);
        turnierButton.setBackground(new Color(165, 171, 179));
        turnierButton.setBounds(50, 200, 200, 50);
        panel.add(turnierButton);
        turnierButton.setVisible(true);

        turnierErstellenButton.setBounds(300, 100, 150, 50);
        panel.add(turnierErstellenButton);
        turnierErstellenButton.setVisible(true);


        JComboBox turniereComboBox = new JComboBox(alleTurniere);

        panel.add(turniereComboBox);

        turniereComboBox.setBackground(Color.WHITE);
        turniereComboBox.setForeground(Color.WHITE);
        turniereComboBox.setBounds(50, 100, 200, 50);
        turniereComboBox.setVisible(true);

        turniereComboBox.addActionListener(e -> {
            Turnier selectedTurnier = (Turnier) turniereComboBox.getSelectedItem();

            if (selectedTurnier.isGespielt()) {
                turnierButton.setText("Turnier anzeigen");
            }
            else {
                turnierButton.setText("Turnier starten/fortsetzen");
            }
        });


        turnierButton.addActionListener(e -> {
            int id = ((Turnier) turniereComboBox.getSelectedItem()).getId();
            var SpieleZuTurnier = Arrays.stream(getSpieleFromServer()).filter(s -> s.getTurnierID() == id)
                                        .collect(Collectors.toList());

            Spiel[] spiele = API.startTurnier(id);
            turnierbaumGenerieren(spiele);

            if (SpieleZuTurnier.size() != 0) {
                App.turnierbaum.ladeSpieleAmAnfang(spiele);
            }
        });

    }
}