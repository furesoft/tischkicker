package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.logic.SpielManager;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Tor;

import java.io.IOException;
import java.util.List;

@RestController
public class SpielController {
    @Autowired
    SpielManager spielManager;

    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/spiele")
    public List<Spiel> alleSpieleHolen() {
        return spielRepository.findAll();
    }

    @GetMapping("/spiele/{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {
        Spiel spiel = spielRepository.getReferenceById(id);
        int[] teamIDs = spiel.getTeamIDs();
        Team team1 = teamRepository.getReferenceById(teamIDs[0]);
        Team team2 = teamRepository.getReferenceById(teamIDs[2]);

        spiel.setTeamNames(team1.getName(), team2.getName());

        return spiel;
    }

    @PostMapping("/spiel/start/{id}")
    public void spielStarten(@PathVariable int id){
        spielManager.spielStarten(spielRepository.getReferenceById(id));
    }

    @PostMapping("/spiel/aufgeben/{id}")
    public void spielAufgeben(@PathVariable int id){
        spielManager.reset();
    }

    @PostMapping("/spiel/increment/{teamID}")
    public void spielstandIncrementieren(@PathVariable int teamID) throws Exception {
        spielManager.increment(teamID);
    }

    @PostMapping("/spiel/decrement/{seite}")
    public void spielstandDecrementieren(@PathVariable int teamID) throws Exception {
        spielManager.decrement(teamID);
    }

    @PostMapping("/spiel/seitenwechsel")
    public void seitenwechsel() throws IOException {
        spielManager.seitenWechsel();
    }
}
