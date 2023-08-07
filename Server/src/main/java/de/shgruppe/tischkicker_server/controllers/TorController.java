package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.SocketHandler;
import de.shgruppe.tischkicker_server.logic.SpielManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.SpielErgebnis;
import tischkicker.models.Tor;

import java.io.IOException;

@RestController
public class TorController {

    @PostMapping ("/tor")
    public void torgeschossen(@RequestBody Tor tor) throws IOException {
        SpielManager.Instance.empfangeTor(tor);

        SpielErgebnis ergebnis = SpielManager.Instance.getErgebnis();
        SocketHandler.broadcast(ergebnis);
    }
}
