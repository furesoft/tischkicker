package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.logic.SpielManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Tor;

@RestController
public class TorController {
    @Autowired
    SpielManager spielManager;

    @PostMapping("/tor")
    public void torgeschossen(@RequestBody Tor tor) throws Exception {
        spielManager.empfangeTor(tor);
    }
}
