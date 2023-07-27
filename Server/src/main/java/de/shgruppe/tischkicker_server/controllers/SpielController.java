package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spiel;

import java.util.List;

@RestController
public class SpielController {

    @Autowired
    SpielRepository repository;

    @GetMapping("/spiele")
    public List<Spiel> alleSpieleHolen() {
        return repository.findAll();
    }

    @GetMapping("/spiele/{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

}
