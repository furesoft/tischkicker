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
        int teamfaktor = 0;
        List<Team> teams = teamRepository.findAll();
        if (teams.size()%2 != 0)
        {
            teamfaktor = 1;
        }

        Spiel[] spiele = new Spiel[teams.size()/2+teamfaktor];
        Collections.shuffle(teams);
        for (int i = 0 ; i < teams.size() ; i++)
        {
            if (i%2==0 && i<teams.size()-2)
            {
                Spiel spiel = new Spiel();
                spiel.setTeams(teams.get(i).getID(),teams.get(i+1).getID());
                spiel.setTeamNames(teams.get(i).getName(), teams.get(i + 1).getName());
                spiele[i/2] = spiel;
            }
            if (teams.size()%2 != 0 && i== teams.size()-1)
            {
                Spiel spielUngerade = new Spiel();
                spielUngerade.setTeams(teams.get(i).getID(),-1);
                spielUngerade.setTeamNames(teams.get(i).getName(),null);
                spiele[i/2] = spielUngerade;
            }
        }
        // TODO Fall abdecken für ungerade Anzahl an Teams
        return Arrays.asList(spiele);
    }
}
