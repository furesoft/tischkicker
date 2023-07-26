package de.shgruppe.tischkicker_server.controllers;

import org.springframework.web.bind.annotation.*;
import tischkicker.models.Team;

@RestController
public class TeamController {

    @GetMapping("/teams")
    public Team alleTeamsHolen() {


        return null;
    }

    @GetMapping("/teams/{id}")
    public Team bestimtesTeamsHolen(@PathVariable int Id) {


        return null;
    }

    @PostMapping("/teams")
    public Team teamAnlegen(@RequestBody Team team) {


        return team;
    }

}
