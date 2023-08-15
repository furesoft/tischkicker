package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TurnierManager {
    SpielPhase spielPhase;

    public static TurnierManager Instance = new TurnierManager();
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielRepository spielRepository;

    public List<Spiel> turnierStarten(){
        List<Spiel> spiele = generiereSpiele();
        spielRepository.saveAllAndFlush(spiele);
        return spiele;
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
                spiele[i/2] = spiel;
            }
        }
        return Arrays.asList(spiele);
    }
}
