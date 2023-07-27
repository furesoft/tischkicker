package de.shgruppe.tischkicker_server.controllers;
import de.shgruppe.tischkicker_server.SocketHandler;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Tor;

import java.io.IOException;


@RestController
public class TorController {

    @PostMapping ("/tor")
    public void torgeschossen(@RequestBody Tor body) throws IOException {
        SocketHandler.broadcast(body);
    }
}
