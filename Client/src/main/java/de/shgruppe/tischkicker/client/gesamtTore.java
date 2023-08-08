package de.shgruppe.tischkicker.client;

import tischkicker.models.SpielErgebnis;


public class gesamtTore {
    SpielErgebnis ergebnis;

    public void empfangeTor(SpielErgebnis tor) {

        this.ergebnis = tor;

    }

    public SpielErgebnis getSpielergebnis() {
        return ergebnis;
    }
}


