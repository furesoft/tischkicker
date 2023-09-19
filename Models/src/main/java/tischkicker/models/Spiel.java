package tischkicker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "spiel")
public class Spiel {
    @Column(name = "datum")
    private Date spieldatum;

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getSpiele() {
        return spiele;
    }

    public void setSpiele(String spiele) {
        this.spiele = spiele;
    }

    @Column(name = "teams")
    private String teams;

    @Column(name = "TORET1")
    private int toreteam1;
    @Column(name = "TORET2")
    private int toreteam2;
    @Column(name = "qualifikation")
    private int qualifikation;
    @Column(name = "spiele")
    private String spiele = "-1,-1";

    @Column(name = "GewinnerID")
    private int gewinnerID;
    @Transient
    private String[] teamNames;
    @Transient
    private int[] teamIDs;
    @Transient
    private int[] spieleIDs;

    @Column(name="TurnierID")
    private int turnierID;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int spielID;

    public Spiel(Date spieldatum, String teams, int toreteam1, int toreteam2, int qualifikation, int ID, String spiele,int gewinnerID) {
        this.spieldatum = spieldatum;
        this.teams = teams;
        this.toreteam1 = toreteam1;
        this.toreteam2 = toreteam2;
        this.qualifikation = qualifikation;
        this.spielID = ID;
        this.spiele = spiele;
        this.gewinnerID =gewinnerID;
    }

    public Spiel() {

    }
    public int getTurnierID() {
        return turnierID;
    }

    public void setTurnierID(int turnierID) {
        this.turnierID = turnierID;
    }



    public int getSpielID() {
        return spielID;
    }

    public Date getSpieldatum() {
        return spieldatum;
    }

    public void setSpieldatum(Date spieldatum) {
        this.spieldatum = spieldatum;
    }


    public String[] getTeamNames() {
        if (teamNames == null)
        {
            return new String[]{
                    "",""
            };
        }
        return teamNames;
    }

    public void setSpieleIDs(int spieleIDs1, int spieleID2){
        List<String> strings = Arrays.stream(new int[]{spieleIDs1, spieleID2}).boxed().map(id -> Integer.toString(id))
                .collect(Collectors.toList());
        this.spiele = String.join(",", strings);
        this.spieleIDs = new int[]{spieleIDs1, spieleID2};
    }

    @JsonIgnore
    public int[] getSpieleIDs(){
        if (spieleIDs == null) {
            return Arrays.stream(spiele.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        return spieleIDs;
    }


    public void setTeams(int teamID1, int teamID2) {
        List<String> strings = Arrays.stream(new int[]{teamID1, teamID2}).boxed().map(id -> Integer.toString(id))
                                     .collect(Collectors.toList());
        this.teams = String.join(",", strings);
        this.teamIDs = new int[]{teamID1, teamID2};
    }


    public int getGewinner() {
        return gewinnerID;
    }

    public void setGewinner(int gewinnerID) {
        this.gewinnerID = gewinnerID;
    }


    public int getQualifikation() {
        return qualifikation;
    }

    public void setQualifikation(int qualifikation) {
        this.qualifikation = qualifikation;
    }

    public int getToreteam1() {
        return toreteam1;
    }

    public void setToreteam1(int toreteam1) {
        this.toreteam1 = toreteam1;
    }

    public int getToreteam2() {
        return toreteam2;
    }

    public void setToreteam2(int toreteam2) {
        this.toreteam2 = toreteam2;
    }

    public void setTeamNames(String team1, String team2) {
        teamNames = new String[]{team1, team2};
    }

    @JsonIgnore
    public int[] getTeamIDs() {
        if (teamIDs == null && teams != null) {
            return Arrays.stream(teams.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        return teamIDs;
    }
}