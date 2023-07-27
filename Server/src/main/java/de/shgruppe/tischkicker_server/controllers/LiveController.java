package de.shgruppe.tischkicker_server.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tischkicker.models.Tor;

@Controller
public class LiveController {

    @MessageMapping("/tor")
    @SendTo("/topic/tor")

    public void torschuss(Tor tor) throws Exception {
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        System.out.println(tor.seite);
    }
}