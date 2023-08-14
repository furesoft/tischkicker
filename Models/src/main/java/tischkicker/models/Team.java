package tischkicker.models;

import javax.persistence.*;

@Entity
public class Team {
    private String name;

    public String spieler;

    @Transient
    private String[] players;

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

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] names) {
        players = names;
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