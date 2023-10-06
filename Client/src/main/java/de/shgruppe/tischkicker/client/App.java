package de.shgruppe.tischkicker.client;

import de.shgruppe.tischkicker.client.fenster.AktuellerSpielstand;
import de.shgruppe.tischkicker.client.fenster.TurnierAuswahlFenster;
import de.shgruppe.tischkicker.client.fenster.TurnierBaum;
import de.shgruppe.tischkicker.client.websockets.WebsocketConnection;

import javax.swing.*;

public class App {
    public static AktuellerSpielstand spielstandAnzeige = new AktuellerSpielstand(750, 750);
    // alte Größe war 500 * 500
    public static TurnierBaum turnierbaum = new TurnierBaum();


    public static void main(String[] args) {
        try {
            WebsocketConnection client = new WebsocketConnection();
            client.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            turnierbaum.frame.setVisible(false);
            new TurnierAuswahlFenster().setVisible(true);
        });


    }
}
