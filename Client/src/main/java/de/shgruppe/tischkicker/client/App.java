package de.shgruppe.tischkicker.client;

import de.shgruppe.tischkicker.client.fenster.AktuellerSpielstand;
import de.shgruppe.tischkicker.client.fenster.Gewinner;
import de.shgruppe.tischkicker.client.fenster.TurnierAuswahlFenster;
import de.shgruppe.tischkicker.client.fenster.TurnierBaum;
import de.shgruppe.tischkicker.client.websockets.WebsocketConnection;

import javax.swing.*;

public class App {
    public static AktuellerSpielstand spielstandAnzeige = new AktuellerSpielstand(500, 500);
    public static TurnierBaum turnierbaum = new TurnierBaum();
    public static Gewinner gewinner = new Gewinner();

    public static void main(String[] args) {
        try {
            WebsocketConnection client = new WebsocketConnection();
            client.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                turnierbaum.frame.setVisible(false);
                new TurnierAuswahlFenster().setVisible(true);
            }
        });


        //in Bearbeitung
        //Siegertreppchen sieger = new Siegertreppchen(f, 1400, 900, 150, 100, 28);
    }
}
