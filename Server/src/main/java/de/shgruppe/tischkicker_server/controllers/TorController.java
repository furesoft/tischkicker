package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.logic.SpielManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Tor;

@RestController
public class TorController {

    @PostMapping ("/tor")
    public void torgeschossen(@RequestBody Tor tor) throws Exception {
        SpielManager.Instance.empfangeTor(tor);
    }
}
