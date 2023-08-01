package de.shgruppe.tischkicker.client;


import tischkicker.models.Spielergebnis;
import tischkicker.models.Tor;

public class gesamtTore {
    private int gesamtAnzahlToreRot = 0;
    private int gesamtAnzahlToreSchwarz = 0;

    public void empfangeTor(Spielergebnis tor) {

        gesamtAnzahlToreRot = tor.toreRot;
        gesamtAnzahlToreSchwarz = tor.toreSchwarz;


    }

    public int getGesamtAnzahlTore() {
        return gesamtAnzahlToreRot;
    }

    public int getGesamtAnzahlToreSchwarz() {
        return gesamtAnzahlToreSchwarz;
    }
}
