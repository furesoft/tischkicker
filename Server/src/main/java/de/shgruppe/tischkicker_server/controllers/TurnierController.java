package de.shgruppe.tischkicker_server.controllers;

import org.springframework.web.bind.annotation.*;
import tischkicker.models.Turnier;

import java.util.Date;


@RestController
public class TurnierController {

    @GetMapping("/turniere")
    public Turnier alleTuniereHolen() {

        return null;
    }

    @GetMapping("/turniere/{id}")
    public Turnier einzelnesTurnierHolen(@PathVariable int id) {
        return null;
    }

    @GetMapping("/turniere/{datum}")
    public Turnier turnierAnlegen(@PathVariable Date datum) {
        return null;
    }

    @PostMapping("/turniere")
    public Turnier turnierAnlegen(@RequestBody Turnier turnier) {
        return turnier;
    }


}
