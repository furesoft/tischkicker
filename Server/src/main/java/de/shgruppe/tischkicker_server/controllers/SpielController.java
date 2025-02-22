package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.logic.SpielManager;
import de.shgruppe.tischkicker_server.logic.TeamNameGetter;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Spiel;

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
        List<Spiel> alleSpiele = spielRepository.findAll();

        alleSpiele.stream().forEach(spiel -> {
            ergaenzeTeamnamen(spiel);
        });

        return alleSpiele;
    }

    @GetMapping("/spiele/{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {
        Optional<Spiel> spiel = spielRepository.findById(id);

        Spiel spiel1 = Hilfsmethoden.optionalCheck(spiel, id);

        ergaenzeTeamnamen(spiel1);

        return spiel1;
    }

    private void ergaenzeTeamnamen(Spiel spiel) {
        int[] teamIDs = spiel.getTeamIDs();
        String teamname1 = teamNameGetter.getTeamName(teamIDs[0]);
        String teamname2 = teamNameGetter.getTeamName(teamIDs[1]);

        spiel.setTeamNames(teamname1, teamname2);
    }

    @PostMapping("/spiel/start/{id}")
    public void spielStarten(@PathVariable int id) throws IOException {
        Optional<Spiel> spielAusDb = spielRepository.findById(id);

        Spiel spiel = Hilfsmethoden.optionalCheck(spielAusDb, id);
        spielManager.spielStarten(spiel);
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
