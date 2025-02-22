package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        repository.saveAndFlush(spieler);
    }

    @PutMapping("/spieler/{id}")
    public ResponseEntity<String> spielerNamenAendern(@PathVariable int id, @RequestBody String name) {
        Optional<Spieler> optionalSpieler = repository.findById(id);

        if (optionalSpieler.isPresent()) {
            Spieler spieler = optionalSpieler.get();
            spieler.setName(name);

            repository.saveAndFlush(spieler);

            return ResponseEntity.ok("Spielername wurde erfolgreich aktualisiert.");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/spieler/{id}")
    public void spielerLoeschen(@PathVariable int id) {
        repository.deleteById(id);
    }
}

