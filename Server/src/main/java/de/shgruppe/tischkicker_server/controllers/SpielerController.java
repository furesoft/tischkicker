package de.shgruppe.tischkicker_server.controllers;

import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spieler;

@RestController
class SpielerController {
    @GetMapping("/spieler")
    public Spieler allespielerHolen() {

        return null;
    }

    @GetMapping("/spieler/{id}")
    public Spieler einzelnenSpielerHolen(@PathVariable int id) {

        return null;
    }

    @PostMapping("/spieler")
    public Spieler spielerAnlegen(@RequestBody Spieler spieler) {

        return null;
    }
}

