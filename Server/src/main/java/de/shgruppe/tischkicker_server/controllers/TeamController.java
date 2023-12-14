package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.Hilfsmethoden;
import de.shgruppe.tischkicker_server.logic.TurnierManager;
import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spiel;
import tischkicker.models.Spieler;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielerRepository spielerRepository;


    @GetMapping("/teams")
    public List<Team> alleTeamsHolen() {
        List<Team> teams = teamRepository.findAll();
        for (int i = 0; i < teams.size() - 1; i++) {
            teamUmwandeln(teams.get(i).getId());
        }
        return teams;
    }


    @GetMapping("/teams/{id}")
    public Team bestimtesTeamsHolen(@PathVariable int id) {
        return teamUmwandeln(id);
    }

    public Team teamUmwandeln(int id) {
        Optional<Team> team = teamRepository.findById(id);
        Team team1 = Hilfsmethoden.optionalCheck(team, id);

        int[] ids = team1.getspielerIDs();
        String[] names = new String[ids.length];

        for (int i = 0; i < ids.length; i++) {
            Optional<Spieler> spieler = spielerRepository.findById(ids[i]);
            Spieler spieler1 = Hilfsmethoden.optionalCheck(spieler, ids[i]);
            names[i] = spieler1.getName();
        }

        team1.setPlayers(names);

        return team1;
    }

    @PostMapping("/teams")
    public Team teamAnlegen(@RequestBody Team team) {
        addSpielerToDb(team);

        team = teamRepository.saveAndFlush(team);
        TurnierManager.teamHinzufuegen(team);

        return team;
    }


    @PutMapping("/teams/{id}")
    public ResponseEntity<String> teamNamenAendern(@PathVariable int id, @RequestBody String name) {
        Optional<Team> optionalSpieler = teamRepository.findById(id);

        if (optionalSpieler.isPresent()) {
            Team teams = optionalSpieler.get();
            teams.setName(name);

            teamRepository.save(teams);

            return ResponseEntity.ok("Teamname wurde erfolgreich aktualisiert.");
        }

        return ResponseEntity.notFound().build();
    }

    private void addSpielerToDb(Team team) {
        List<String> ids = new ArrayList();

        for (String name : team.getPlayers()) {
            Spieler s = new Spieler();
            s.setName(name);

            s = spielerRepository.saveAndFlush(s);

            ids.add(Integer.toString(s.getId()));
        }

        team.spieler = String.join(",", ids);
    }

    @DeleteMapping("/teams/{id}")
    public void teamLoeschen(@PathVariable int id) {
        teamRepository.deleteById(id);
    }

}
