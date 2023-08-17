package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.errorhandling.Hilfsmethoden;
import de.shgruppe.tischkicker_server.logic.TurnierManager;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spiel;
import tischkicker.models.Turnier;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TurnierController {

    @Autowired
    TurnierRepository repository;

    @Autowired
    TurnierManager turnierManager;


    @GetMapping("/turniere/{id}")
    public Turnier einzelnesTurnierHolen(@PathVariable int id) {
        Optional <Turnier> turnier = repository.findById(id);
        return Hilfsmethoden.optionalCheck(turnier,id);
    }

    @GetMapping("/turniere/{datum}")
    public Turnier turnierbyDatum(@PathVariable Date datum) {
       // return repository.findByDate(datum);
        //ToDo: Nicht implementiert
        return null;
    }

    @GetMapping("/turnier")
    public List<Spiel> turnierStarten() {
        return turnierManager.turnierStarten();
    }
}
