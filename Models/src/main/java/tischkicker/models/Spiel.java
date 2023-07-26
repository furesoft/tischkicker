package tischkicker.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "spiel")
public class Spiel {
    public Spiel(Date spieldatum, String[] teams, int toreteam1, int toreteam2, int qualifikation, int ID) {
        this.spieldatum = spieldatum;
        this.teams = teams;
        this.toreteam1 = toreteam1;
        this.toreteam2 = toreteam2;
        this.qualifikation = qualifikation;
        this.spielID = ID;
    }

    @Column(name = "datum")
    private Date spieldatum;

    @Column(name = "teams")
    private String[] teams = new String[2];

    @Column(name = "TORET1")
    private int toreteam1;

    @Column(name = "TORET2")
    private int toreteam2;

    @Column(name = "qualifikation")
    private int qualifikation;

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int spielID;


    public int getSpielID() {
        return spielID;
    }

    public Date getSpieldatum() {
        return spieldatum;
    }

    public void setSpieldatum(Date spieldatum) {
        this.spieldatum = spieldatum;
    }

    public String[] getTeams() {
        return teams;
    }

    public void setTeams(String[] teams) {
        this.teams = teams;
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
}