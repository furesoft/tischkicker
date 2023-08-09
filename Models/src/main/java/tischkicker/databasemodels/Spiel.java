package tischkicker.databasemodels;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "spiel")
public class Spiel {
    @Column(name = "datum")
    private Date spieldatum;
    @Column(name = "teams")
    private String teams;
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
    public Spiel(Date spieldatum, String teams, int toreteam1, int toreteam2, int qualifikation, int ID) {
        this.spieldatum = spieldatum;
        this.teams = teams;
        this.toreteam1 = toreteam1;
        this.toreteam2 = toreteam2;
        this.qualifikation = qualifikation;
        this.spielID = ID;
    }

    public Spiel(String teams) {
        this.teams = teams;
    }

    public Spiel() {

    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
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