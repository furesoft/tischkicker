package de.shgruppe.tischkicker.client;

import tischkicker.models.Spielergebnis;

public class gesamtTore {
    Spielergebnis ergebnis;

    public void empfangeTor(Spielergebnis tor) {

        this.ergebnis = tor;

    }

    public Spielergebnis getGesamtAnzahlTore() {
        return ergebnis;
    }
}


