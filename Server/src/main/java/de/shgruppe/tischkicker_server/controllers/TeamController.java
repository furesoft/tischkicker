package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Team;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamRepository repository;

    @GetMapping("/teams")
    public List<Team> alleTeamsHolen() {
        return repository.findAll();
    }

    @GetMapping("/teams/{id}")
    public Team bestimtesTeamsHolen(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @PostMapping("/teams")
    public void teamAnlegen(@RequestBody Team team) {
        repository.save(team);
    }

}
