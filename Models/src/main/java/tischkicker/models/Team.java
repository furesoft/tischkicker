package tischkicker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Team {
    public String spieler;
    public String name;
    @Transient
    private String[] players;
    private int gesamttore;
    private int gegentore;
    private boolean aufgegeben;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId()
    {
        return id;
    }
    public void setId (int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name.replace("\"","");
    }
    public String[] getPlayers()
    {
        return players;
    }
    public void setPlayers(String[] names)
    {
        players = names;
    }

    @JsonIgnore
    public int[] getspielerIDs() {

        String[] ids = spieler.split(",");

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