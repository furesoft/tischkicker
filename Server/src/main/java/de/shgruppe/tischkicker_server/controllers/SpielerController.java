package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spieler;

import java.util.List;

@RestController
class SpielerController {
    @Autowired
    SpielerRepository repository;

    @GetMapping("/spieler")
    public List<Spieler> allespielerHolen() {
        return repository.findAll();
    }

    @GetMapping("/spieler/{id}")
    public Spieler einzelnenSpielerHolen(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @PostMapping("/spieler")
    public void spielerAnlegen(@RequestBody Spieler spieler) {
        repository.save(spieler);
    }
}

