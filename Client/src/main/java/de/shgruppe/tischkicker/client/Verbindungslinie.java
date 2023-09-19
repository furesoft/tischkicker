package de.shgruppe.tischkicker.client;

import de.shgruppe.tischkicker.client.ui.Colors;
import de.shgruppe.tischkicker.client.ui.Spielfeld;

import javax.swing.*;

public class Verbindungslinie {

    public Spielfeld quellSpielFeld1, quellSpielfeld2, ankunftsSpielfeld;

    /**
     * Erstellt eine Verbindungslinie zwischen 3 Spielfeldern
     *
     * @param frame         gibt den JFrame an, indem die Linie erscheinen soll
     * @param spielfeld1    spielfeld 1 wird zu berechnung der Koordinaten, Breite, Höhe benötigt
     * @param spielfeld2    spielfeld 2 wird zu berechnung der Koordinaten, Breite, Höhe benötigt
     * @param spielfeld3    spielfeld 3 wird zu berechnung der Koordinaten, Breite, Höhe benötigt
     * @param linienStaerke gibt die Linienstärke an
     */
    public Verbindungslinie(JPanel frame, Spielfeld spielfeld1, Spielfeld spielfeld2, Spielfeld spielfeld3, int linienStaerke) {
        verbindungslinie2(frame, spielfeld1, spielfeld3, linienStaerke);
        verbindungslinie1(frame, spielfeld2, spielfeld3, linienStaerke);

        quellSpielFeld1 = spielfeld1;
        quellSpielfeld2 = spielfeld2;
        ankunftsSpielfeld = spielfeld3;
    }

    /**
     * Erstellt eine Verbindungslinie zwischen 2 Spielfeldern
     *
     * @param frame         gibt den JFrame an, indem die Linie erscheinen soll
     * @param spielfeld1    spielfeld 1 wird zu berechnung der Koordinaten, Breite, Höhe benötigt
     * @param spielfeld2    spielfeld 2 wird zu berechnung der Koordinaten, Breite, Höhe benötigt
     * @param linienStaerke gibt die Linienstärke an
     */
    public Verbindungslinie(JPanel frame, Spielfeld spielfeld1, Spielfeld spielfeld2, int linienStaerke) {
        spielfeld2.setY(spielfeld2.background.getY() + 20);
        if (spielfeld1.background.getY() < spielfeld2.background.getY()) {
            verbindungslinie2(frame, spielfeld1, spielfeld2, linienStaerke);
        }
        else {
            ankunftsSpielfeld = spielfeld2;
            quellSpielFeld1 = spielfeld1;
            verbindungslinie1(frame, spielfeld1, spielfeld2, linienStaerke);
        }
    }

    public void verbindungslinie2(JPanel frame, Spielfeld spielfeld1, Spielfeld spielfeld3, int linienStaerke) {
        // die oberste linie ist immer Linie 3, Linie 2 ist immer in der Mitte und Linie 1 immer unten
        //  ---------- <- Linie 3
        //	         |
        //	         |
        //	         |
        //	         |
        //	         | <- Linie 2
        //Linie 1 -> ----------

        // Linie 1
        JLabel linie1 = new JLabel(" ");
        linie1.setBounds(spielfeld3.background.getX() - (spielfeld3.background.getX() - (spielfeld1.background.getX() + spielfeld1.background.getWidth())) / 2, spielfeld3.background.getY() + (spielfeld3.background.getHeight() / 2), (spielfeld3.background.getX() - (spielfeld1.background.getX() + spielfeld1.background.getWidth())) / 2, linienStaerke);
        linie1.setOpaque(true);
        linie1.setBackground(Colors.VERBINDUNGSLINIE);

        // Linie 2
        JLabel linie2 = new JLabel(" ");
        linie2.setBounds(spielfeld1.background.getX() + spielfeld1.background.getWidth() + (spielfeld3.background.getX() - (spielfeld1.background.getX() + spielfeld1.background.getWidth())) / 2, spielfeld1.background.getY() + (spielfeld1.background.getHeight() / 2), linienStaerke, (spielfeld3.background.getY() + (spielfeld3.background.getHeight()) / 2) - (spielfeld1.background.getY() + (spielfeld1.background.getHeight()) / 2));
        linie2.setOpaque(true);
        linie2.setBackground(Colors.VERBINDUNGSLINIE);

        // Linie 3
        JLabel linie3 = new JLabel(" ");
        linie3.setBounds(spielfeld1.background.getX() + spielfeld1.background.getWidth(), spielfeld1.background.getY() + (spielfeld1.background.getHeight()) / 2, (spielfeld3.background.getX() - (spielfeld1.background.getX() + spielfeld1.background.getWidth())) / 2, linienStaerke);
        linie3.setOpaque(true);
        linie3.setBackground(Colors.VERBINDUNGSLINIE);

        // Füge die Linien zum Panel hinzu
        frame.add(linie1);
        frame.add(linie2);
        frame.add(linie3);
    }

    public void verbindungslinie1(JPanel frame, Spielfeld spielfeld2, Spielfeld spielfeld3, int linienStaerke) {
        // die oberste linie ist immer Linie 3, Linie 2 ist immer in der Mitte und Linie 1 immer unten
        //	       ------ <- Linie 3
        //	       |
        //	       |
        //	       |
        //	       |
        //	       | <- Linie 2
        //---------- <- Linie 1

        // Linie 1
        JLabel linie1 = new JLabel(" ");
        linie1.setBounds((spielfeld2.background.getX() + spielfeld2.background.getWidth()), (spielfeld2.background.getY() + (spielfeld2.background.getHeight() / 2)), (spielfeld3.background.getX() - (spielfeld2.background.getX() + spielfeld2.background.getWidth())) / 2, linienStaerke);
        linie1.setOpaque(true);
        linie1.setBackground(Colors.VERBINDUNGSLINIE);

        // Linie 2
        JLabel linie2 = new JLabel(" ");
        linie2.setBounds(spielfeld3.background.getX() - (spielfeld3.background.getX() - (spielfeld2.background.getX() + spielfeld2.background.getWidth())) / 2, ((spielfeld3.background.getY()) + (spielfeld3.background.getHeight() / 2)), linienStaerke, (((spielfeld2.background.getY()) + ((spielfeld2.background.getHeight()) / 2)) - ((spielfeld3.background.getY()) + ((spielfeld3.background.getHeight()) / 2))) + linienStaerke);
        linie2.setOpaque(true);
        linie2.setBackground(Colors.VERBINDUNGSLINIE);

        // Linie 3
        JLabel linie3 = new JLabel(" ");
        linie3.setBounds(spielfeld3.background.getX() - (spielfeld3.background.getX() - (spielfeld2.background.getX() + spielfeld2.background.getWidth())) / 2, ((spielfeld3.background.getY()) + (spielfeld3.background.getHeight() / 2)), (spielfeld3.background.getX()) - (spielfeld3.background.getX() - (spielfeld3.background.getX() - (spielfeld2.background.getX() + spielfeld2.background.getWidth())) / 2), linienStaerke);
        linie3.setOpaque(true);
        linie3.setBackground(Colors.VERBINDUNGSLINIE);

        // Füge die Linien zum Panel hinzu
        frame.add(linie1);
        frame.add(linie2);
        frame.add(linie3);
    }
}


