package tischkicker.models;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Team {
    private  String name;
    private ArrayList<Integer> spielerID = new ArrayList<>();
    private int gesamttore;
    private int gegentore;
    private boolean aufgegeben;

    public void setID(int ID) {
        this.ID = ID;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getSpielerID() {
        return spielerID;
    }

    public void setSpielerID(ArrayList<Integer> spielerID) {
        this.spielerID = spielerID;
    }

    public int getGesamttore() {
        return gesamttore;
    }

    public void setGesamttore(int gesamttore) {
        this.gesamttore = gesamttore;
    }

    public int getGegentore() {
        return gegentore;
    }

    public void setGegentore(int gegentore) {
        this.gegentore = gegentore;
    }

    public boolean isAufgegeben() {
        return aufgegeben;
    }

    public void setAufgegeben(boolean aufgegeben) {
        this.aufgegeben = aufgegeben;
    }
}