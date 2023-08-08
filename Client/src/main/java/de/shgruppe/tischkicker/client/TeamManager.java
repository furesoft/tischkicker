package de.shgruppe.tischkicker.client;
import java.util.ArrayList;
import java.util.List;

public class TeamManager {


    public String[] players;
    public String teamName;

    public static List<TeamManager> teamManagers=new ArrayList<>();

    public static int counter;
    public TeamManager(String[] players, String teamName){

        this.players  = players;
        this.teamName = teamName;
        counter+=1;
    }
    public int getNumTeams() {

        return counter;
    }

    public void startGame() {
    }

    public void setTeamManagers(TeamManager teamManager1) {

        teamManagers.add(teamManager1);
    }
    public static List<TeamManager> getTeamManagers() {

        return teamManagers;
    }

}
