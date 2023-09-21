package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tischkicker.models.Team;

public class Statistics {
    @Autowired
    TeamRepository teamRepository;

    public void incrementTeamTore(Team team, int tore, int gegenTore) {
        team.setGegentore(team.getGegentore() + gegenTore);
        team.setGesamttore(team.getGesamttore() + tore);

        teamRepository.saveAndFlush(team);
    }
}
