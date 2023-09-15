package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.logic.TurnierManager;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TurnierController {

    @Autowired
    TurnierRepository repository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TurnierManager turnierManager;


    @GetMapping("/turniere/{id}")
    public Turnier einzelnesTurnierHolen(@PathVariable int id) {
        Optional<Turnier> turnier = repository.findById(id);
        return Hilfsmethoden.optionalCheck(turnier, id);
    }

    @GetMapping("/turniere/{datum}")
    public Turnier turnierbyDatum(@PathVariable Date datum) {
        // return repository.findByDate(datum);
        //ToDo: Nicht implementiert
        return null;
    }

    @GetMapping("/turniere")
    public List<Turnier> alleTurniereHolen() {
        return repository.findAll();
    }

    @GetMapping("/turnier")
    public List<Spiel> turnierStarten() {
        return turnierManager.turnierStarten();
    }


    @GetMapping("/turnierteams/{id}")
    public List<Team> teamsVonTurnierHolen(@PathVariable int id) {
        Turnier turnier = Hilfsmethoden.optionalCheck(repository.findById(id), id);
        List<Team> alleTeams = teamRepository.findAll();
        List teamIds = List.of(turnier.getTeamsIDs());

        return alleTeams.stream().filter(t -> teamIds.contains(t.getId())).collect(Collectors.toList());
    }

    @GetMapping("/turniererstellen")
    public Turnier einzelnesTurnierErstellen() {
        return turnierManager.Turniererstellen();
    }
}
