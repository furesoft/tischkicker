package de.shgruppe.tischkicker_server.controllers;
import de.shgruppe.tischkicker_server.SocketHandler;
import org.springframework.web.bind.annotation.*;
import tischkicker.models.Tor;

import java.io.IOException;


@RestController
public class TorController {


    @PostMapping ("/tor")
    public void torgeschossen() throws IOException {
        Tor t1= new Tor();
        t1.seite = Tor.Seite.ROT;
        SocketHandler.broadcast(t1);
    }
}
