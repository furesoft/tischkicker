package tischkicker.databasemodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Turnier {
    private Date startdatum;
    private Date enddatum;
    private ArrayList<Integer> spieleID = new ArrayList<>();
    private ArrayList<Integer> teamsID = new ArrayList<>();
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public int getID() {
        return ID;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
        this.enddatum = enddatum;
    }

    public ArrayList<Integer> getSpieleID() {
        return spieleID;
    }

    public void setSpieleID(ArrayList<Integer> spieleID) {
        this.spieleID = spieleID;
    }

    public ArrayList<Integer> getTeamsID() {
        return teamsID;
    }

    public void setTeamsID(ArrayList<Integer> teamsID) {
        this.teamsID = teamsID;
    }
}