package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class TurnierManager {
    @Autowired
    SpielPhase spielPhase;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SpielRepository spielRepository;

    public List<Spiel> turnierStarten() {
        List<Spiel> spiele1 = spielRepository.findAll();
        if (spiele1.size() == 0)
        {
            spiele1 = generiereSpiele();
            spiele1 = spielRepository.saveAllAndFlush(spiele1);
        }
        else
        {
            List<Team> teams = teamRepository.findAll();
            for (int i = 0 ; i < spiele1.size() ; i++)
            {
                String [] namen = new String[2];
                int [] teamids = spiele1.get(i).getTeamIDs();
                for (int h = 0 ; h < 2 ; h++)
                {
                   if (teamids[h] < 0)
                   {
                       namen[h] = null;
                   }
                   else
                   {
                       namen[h] = teams.get(teamids[h]-1).getName();
                   }
                }
                spiele1.get(i).setTeamNames(namen[0],namen[1]);
            }
        }

        return spiele1;
    }

    private List<Spiel> generiereSpiele() {
        int teamfaktor = 0;
        List<Team> teams = teamRepository.findAll();

        if (teams.size() % 2 != 0) {
            teamfaktor = 1;
        }


        Spiel[] spiele = new Spiel[teams.size() / 2 + teamfaktor];
        Collections.shuffle(teams);

        for (int i = 0; i < teams.size(); i++) {
            if (i % 2 == 0 && i < teams.size() - 1) {
                Spiel spiel = new Spiel();
                spiel.setTeams(teams.get(i).getId(), teams.get(i + 1).getId());
                spiel.setTeamNames(teams.get(i).getName(), teams.get(i + 1).getName());
                spiel.setSpieleIDs(-1,-1);
                spiele[i / 2] = spiel;
            }
            else if (teams.size() % 2 != 0 && i == teams.size() - 1) {
                Spiel spielUngerade = new Spiel();
                spielUngerade.setTeams(teams.get(i).getId(), -1);
                spielUngerade.setTeamNames(teams.get(i).getName(), null);
                spielUngerade.setSpieleIDs(-1,-1);
                spiele[i / 2] = spielUngerade;
            }
        }
        /*
        for(int h = 1 ; h < teams.size()/2+1 ; h++)
        {
            Spiel spielLeer = new Spiel();
            spielLeer.setTeams(-1,-1);
            spielLeer.setTeamNames(null,null);
            spielLeer.setSpieleIDs(h,h++);
        }

         */

        return Arrays.asList(spiele);
    }
}
