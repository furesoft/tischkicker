package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.errorhandling.Hilfsmethoden;
import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spieler;

import java.util.List;
import java.util.Optional;

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
        Optional<Spieler> spieler = repository.findById(id);
        return Hilfsmethoden.optionalCheck(spieler, id);
    }

    @PostMapping("/spieler")
    public void spielerAnlegen(@RequestBody Spieler spieler) {
        repository.save(spieler);
    }


}

