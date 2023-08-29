package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.errorhandling.Hilfsmethoden;
import de.shgruppe.tischkicker_server.logic.SpielManager;
import de.shgruppe.tischkicker_server.logic.TeamNameGetter;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class SpielController {
    @Autowired
    SpielManager spielManager;

    @Autowired
    SpielRepository spielRepository;

    @Autowired
    TeamNameGetter teamNameGetter;

    @GetMapping("/spiele")
    public List<Spiel> alleSpieleHolen() {
        return spielRepository.findAll();
    }

    @GetMapping("/spiele/{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {
        Optional<Spiel> spiel = spielRepository.findById(id);

        Spiel spiel1 = Hilfsmethoden.optionalCheck(spiel, id);

        int[] teamIDs = spiel1.getTeamIDs();
        String teamname1 = teamNameGetter.getTeamName(teamIDs[0]);
        String teamname2 = teamNameGetter.getTeamName(teamIDs[1]);

        spiel1.setTeamNames(teamname1, teamname2);

        return spiel1;
    }

    @PostMapping("/spiel/start/{id}")
    public void spielStarten(@PathVariable int id) {
        Optional<Spiel> spiel = spielRepository.findById(id);

        Spiel spiel1 = Hilfsmethoden.optionalCheck(spiel, id);
        spielManager.spielStarten(spiel1);
    }

    @PostMapping("/spiel/aufgeben/{id}")
    public void spielAufgeben(@PathVariable int id) throws IOException {
        spielManager.aufgeben(id);
    }

    @PostMapping("/spiel/increment/{teamID}")
    public void spielstandIncrementieren(@PathVariable int teamID) throws Exception {
        spielManager.increment(teamID);
    }

    @PostMapping("/spiel/decrement/{teamID}")
    public void spielstandDecrementieren(@PathVariable int teamID) throws Exception {
        spielManager.decrement(teamID);
    }

    @PostMapping("/spiel/seitenwechsel")
    public void seitenwechsel() throws IOException {
        spielManager.seitenWechsel();
    }
}
