package de.shgruppe.tischkicker.client;
import tischkicker.models.Spieler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team {
    public List<String> players;
    public String teamName;
    private int numberOfPlayersPerTeam;
    public static int counter;
    public Team(List <String> players, String teamName){
        this.players  = players;
        this.teamName = teamName;
        this.numberOfPlayersPerTeam = players.size();

        counter+=1;
    }

    public static int getNumTeams() {
        return counter;
    }

    public void startGame() {
    }
    public  String getName() {
        return teamName;
    }


    public void setTeamName(String newTeamName) {
        this.teamName=newTeamName;
    }
    public int getNumberOfPlayersPerTeam() {
        return numberOfPlayersPerTeam;
    }
    public void setPlayerName(int playerIndex, String newPlayerName) {
        if (playerIndex >= 0 && playerIndex < players.size()) {
            players.set(playerIndex, newPlayerName);
        } else {
            System.out.println("Ungültiger Spielerindex.");
        }
    }


}
