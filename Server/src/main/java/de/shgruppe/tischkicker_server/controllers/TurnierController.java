package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Turnier;

import java.util.Date;
import java.util.List;

@RestController
public class TurnierController {

    @Autowired
    TurnierRepository repository;

    @GetMapping("/turniere")
    public List<Turnier> alleTuniereHolen() {
        return repository.findAll();
    }

    @GetMapping("/turniere/{id}")
    public Turnier einzelnesTurnierHolen(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @GetMapping("/turniere/{datum}")
    public Turnier turnierbyDatum(@PathVariable Date datum) {
       // return repository.findByDate(datum);
        //ToDo: Nicht implementiert
        return null;
    }

    @PostMapping("/turniere")
    public void turnierAnlegen(@RequestBody Turnier turnier) {
        repository.save(turnier);
    }
}
