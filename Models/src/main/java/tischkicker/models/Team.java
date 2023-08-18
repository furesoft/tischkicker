package tischkicker.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Team {
    private String name;

    public String spieler;

    @Transient
    private String[] players;

    private int gesamttore;
    private int gegentore;
    private boolean aufgegeben;

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
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

    public int[] getspielerIDs() {
        String [] ids = spieler.split(",");

        return Arrays.stream(ids).mapToInt(Integer::parseInt).toArray();
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