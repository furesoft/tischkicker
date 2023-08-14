package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Spieler;
import tischkicker.models.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielerRepository spielerRepository;

    @GetMapping("/teams")
    public List<Team> alleTeamsHolen() {
        return teamRepository.findAll();
    }

    @GetMapping("/teams/{id}")
    public Team bestimtesTeamsHolen(@PathVariable int id) {
        return teamRepository.getReferenceById(id);
    }

    @PostMapping("/teams")
    public Team teamAnlegen(@RequestBody Team team) {
        addSpielerToDb(team);
        team.setID(teamRepository.findAll().size());

        teamRepository.saveAndFlush(team);
        return team;
    }

    private void addSpielerToDb(Team team) {
        List ids = new ArrayList();
        for (String name : team.getPlayers()) {
            Spieler s = new Spieler();
            s.setName(name);

            spielerRepository.saveAndFlush(s);

            s.setID((int)spielerRepository.count());

            ids.add(s.getID());
        }

        team.spieler = String.join(",", ids);
    }

    private void findAndSetSpieler(Team team) {
        int[] ids = Arrays.stream(team.spieler.split(",")).mapToInt(Integer::parseInt).toArray();

        String[] spieler = (String[]) Arrays.stream(ids).mapToObj(id -> spielerRepository.getReferenceById(id))
                                            .map(s -> s.getName()).toArray();

        team.setPlayers(spieler);
    }

    @DeleteMapping("/teams/{id}")
    public void teamLoeschen(int id) {
        teamRepository.deleteById(id);
    }

}
