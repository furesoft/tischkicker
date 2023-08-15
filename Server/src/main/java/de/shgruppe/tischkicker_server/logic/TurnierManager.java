package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class TurnierManager {
    SpielPhase spielPhase;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielRepository spielRepository;

    public List<Spiel> turnierStarten(){
        List<Spiel> spiele = generiereSpiele();
        return spielRepository.saveAllAndFlush(spiele);
    }
    private List<Spiel> generiereSpiele()
    {
        List<Team> teams = teamRepository.findAll();
        Spiel[] spiele = new Spiel[teams.size()/2];
        Collections.shuffle(teams);
        for (int i = 0 ; i < teams.size() ; i++)
        {
            if (i%2==0)
            {
                Spiel spiel = new Spiel();
                spiel.setTeams(teams.get(i).getID(),teams.get(i+1).getID());
                spiel.setTeamNames(teams.get(i).getName(), teams.get(i + 1).getName());
                spiele[i/2] = spiel;
            }
        }
        // TODO Fall abdecken für ungerade Anzahl an Teams
        return Arrays.asList(spiele);
    }
}
