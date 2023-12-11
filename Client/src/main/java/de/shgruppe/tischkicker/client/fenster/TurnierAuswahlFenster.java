package de.shgruppe.tischkicker.client.fenster;

import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.App;
import de.shgruppe.tischkicker.client.ui.Colors;
import de.shgruppe.tischkicker.client.ui.DataButton;
import tischkicker.models.Spiel;
import tischkicker.models.Turnier;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.shgruppe.tischkicker.client.API.getSpieleFromServer;
import static de.shgruppe.tischkicker.client.API.turnierbaumGenerieren;

public class TurnierAuswahlFenster extends JFrame {

    public static Turnier aktuellesTurnier;
    static JComboBox turniereComboBox = new JComboBox();

    static ArrayList<Turnier> alleTurniere;

    public TurnierAuswahlFenster() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        alleTurniere = new ArrayList<>(List.of(API.getTurniere()));

        this.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 400);
        panel.setBackground(Colors.BACKGROUND);
        this.add(panel);
        panel.setVisible(true);

        DataButton turnierErstellenButton = new DataButton("Neues Turnier", true);
        turnierErstellenButton.setForeground(Colors.BUTTON_SCHRIFT);

        turnierErstellenButton.setBackground(Colors.BUTTON_BACKGROUND);

        turnierErstellenButton.addActionListener(e -> {
            App.turnierbaum.resetTurnierBaum();
            Turnier turnier = API.erstelleTurnier();
            aktuellesTurnier = turnier;

            //aktuellesTurnier = API.erstelleTurnier();
            new TeamsInitialisierenFenster().setVisible(true);
        });


        DataButton turnierStartenButton = new DataButton("Turnier anzeigen", true);
        turnierStartenButton.setSize(50, 50);
        turnierStartenButton.setBackground(Colors.BUTTON_BACKGROUND);
        turnierStartenButton.setForeground(Colors.BUTTON_SCHRIFT);
        turnierStartenButton.setBounds(50, 200, 200, 50);
        panel.add(turnierStartenButton);
        turnierStartenButton.setVisible(true);

        turnierErstellenButton.setBounds(300, 100, 150, 50);
        panel.add(turnierErstellenButton);
        turnierErstellenButton.setVisible(true);

        DataButton quickPlayButton = new DataButton("Schnellspiel", true);
        quickPlayButton.setBounds(300,200,150,50);
        quickPlayButton.setBackground(Colors.BUTTON_BACKGROUND);
        quickPlayButton.setForeground(Colors.BUTTON_SCHRIFT);
        panel.add(quickPlayButton);
        quickPlayButton.setVisible(true);

        panel.add(turniereComboBox);

        turniereComboBox.setBackground(Color.WHITE);
        turniereComboBox.setForeground(Color.BLACK);
        turniereComboBox.setBounds(50, 100, 200, 50);
        for (Turnier value : alleTurniere) {
            turniereComboBox.addItem(value);
        }
        turniereComboBox.setVisible(true);

        turniereComboBox.addActionListener(e -> {
            Turnier selectedTurnier = (Turnier) turniereComboBox.getSelectedItem();

            setButtonText(selectedTurnier, turnierStartenButton);
        });

        if(!alleTurniere.isEmpty()) {
            setButtonText(alleTurniere.get(0), turnierStartenButton);
        }

        quickPlayButton.addActionListener(e -> {
            App.spielstandAnzeige.aktualisiereDaten(API.getQuickSpiel());
            App.spielstandAnzeige.show();
        });

        turnierStartenButton.addActionListener(e -> {
            if (turniereComboBox.getSelectedIndex() != -1) {
                App.turnierbaum.resetTurnierBaum();
                Turnier turnier = ((Turnier) turniereComboBox.getSelectedItem());

                aktuellesTurnier = alleTurniere.stream().filter(t -> t.getId() == turnier.getId()).findFirst().get();

                var turnierSpiele = Arrays.stream(getSpieleFromServer()).filter(s -> s.getTurnierID() == turnier.getId())
                        .collect(Collectors.toList());

                Spiel[] spiele = API.startTurnier(turnier.getId());
                turnierbaumGenerieren(spiele);

                if (!turnierSpiele.isEmpty()) {
                    App.turnierbaum.ladeSpieleAmAnfang(spiele);
                }
            }
        });

        setLocationRelativeTo(null);
    }

    private static List<Spiel> getSpieleVonTurnier(Turnier turnier) {
        return Arrays.stream(getSpieleFromServer()).filter(s -> s.getTurnierID() == turnier.getId())
                     .collect(Collectors.toList());
    }

    private static void setButtonText(Turnier selectedTurnier, DataButton turnierStartenButton) {
        if (selectedTurnier.isGespielt()) {
            turnierStartenButton.setText("Turnier anzeigen");
        }
        else {
            List<Spiel> turnierSpiele = getSpieleVonTurnier(selectedTurnier);
            boolean hatTurniergestartetesSpiel = turnierSpiele.stream()
                                                              .filter(s -> s.getQualifikation() == 1 && s.getGewinnerID() > 0)
                                                              .findAny().isPresent();

            if (hatTurniergestartetesSpiel) {
                turnierStartenButton.setText("Turnier fortsetzen");
            }
            else {
                turnierStartenButton.setText("Turnier starten");
            }
        }
    }
}