package de.shgruppe.tischkicker_server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Spiel;

@RestController
public class SpielController {

    @GetMapping("/spiele")
    public Spiel alleSpieleHolen() {

        return null;
    }

    @GetMapping("/spiele{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {

        return null;
    }


}
