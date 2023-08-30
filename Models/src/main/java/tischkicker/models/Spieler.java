package tischkicker.models;

import javax.persistence.*;

@Entity
public class Spieler {
    private String name;
    private String abteilung;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.replace("\"", "");
    }

    public String getAbteilung() {
        return this.abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }
}