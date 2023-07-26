package de.shgruppe.tischkicker_server.controllers;

import org.springframework.web.bind.annotation.*;
import tischkicker.models.Turnier;

import java.util.Date;


@RestController
public class TurnierController {

    @GetMapping("/tuniere")
    public Turnier alleTuniereHolen() {

        return null;
    }

    @GetMapping("/tuniere/{id}")
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
