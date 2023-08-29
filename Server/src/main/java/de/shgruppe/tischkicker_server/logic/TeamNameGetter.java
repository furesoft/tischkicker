package de.shgruppe.tischkicker_server.logic;

import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamNameGetter
{
    @Autowired
    private TeamRepository teamRepository;

    public String getTeamName(int teamId){
        var maybeTeam = teamRepository.findById(teamId);
        if(maybeTeam.isPresent()){
            return maybeTeam.get().getName();
        }
        //steht noch nicht fest.
        else if(teamId == -1){
            return "";
        }
        else if(teamId == -2){
            return "Verlierer...";
        }

        //unbekannter Status....
        return "";
    }
}
