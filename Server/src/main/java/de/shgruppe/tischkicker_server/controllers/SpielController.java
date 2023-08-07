package de.shgruppe.tischkicker_server.controllers;

import de.shgruppe.tischkicker_server.logic.SpielManager;
import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tischkicker.models.Spiel;
import tischkicker.models.Tor;

import java.io.IOException;
import java.util.List;

@RestController
public class SpielController {

    @Autowired
    SpielRepository repository;

    @GetMapping("/spiele")
    public List<Spiel> alleSpieleHolen() {
        return repository.findAll();
    }

    @GetMapping("/spiele/{id}")
    public Spiel betimmtesSpieleHolen(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @PostMapping("/spiel/start/{id}")
    public void spielStarten(@PathVariable int id){
        SpielManager.Instance.spielStarten(repository.getReferenceById(id));
    }

    @PostMapping("/spiel/aufgeben/{id}")
    public void spielAufgeben(@PathVariable int id){
        SpielManager.Instance.reset();
    }

    @PostMapping("/spiel/increment/{seite}")
    public void spielstandIncrementieren(@PathVariable Tor.Seite seite) throws Exception {
        SpielManager.Instance.increment(seite);
    }

    @PostMapping("/spiel/decrement/{seite}")
    public void spielstandDecrementieren(@PathVariable Tor.Seite seite) throws Exception {
        SpielManager.Instance.decrement(seite);
    }

    @PostMapping("/spiel/seitenwechsel")
    public void seitenwechsel() throws IOException {
        SpielManager.Instance.seitenWechsel();
    }
}
