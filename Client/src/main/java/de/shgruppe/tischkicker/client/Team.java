package de.shgruppe.tischkicker.client;

import java.util.List;

public class Team {


    public List<String> players;

    public int ID;
    public String name;
    public static int counter;
    public Team(List <String> players, String name){
        this.players  = players;
        this.name = name;

        counter+=1;
    }

    public static int getNumTeams() {
        return counter;
    }

    public void startGame() {
    }
    public  String getName() {
        return name;
    }


    public void setName(String newTeamName) {
        this.name =newTeamName;
    }
    public int getNumberOfPlayersPerTeam() {
        return players.size();
    }
    public void setPlayerName(int playerIndex, String newPlayerName) {
        if (playerIndex >= 0 && playerIndex < players.size()) {
            players.set(playerIndex, newPlayerName);
        } else {
            System.out.println("Ungültiger Spielerindex.");
        }
    }
    public List<String> getPlayerNames() {
        return players;
    }


}



