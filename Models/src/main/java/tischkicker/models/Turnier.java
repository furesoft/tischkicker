package tischkicker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Turnier {
    @Column(name = "startdatum")
    private String startdatum;

    @Column(name = "enddatum")
    private String enddatum;


    @Column(name = "spiele")
    private String spiele;

    @Column(name = "gespielt")
    private boolean gespielt;
    @Transient
    private int[] spieleIDs;

    @Column(name = "teams")
    private String teams;

    @Transient
    private int[] teamsIDs;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "MAXIMAL_TORE_BIS_GEWONNEN")
    private int maximalToreBisGewonnen;
    @Column(name = "Turniername")
    private String turnierName;

    public int getMaximalToreBisGewonnen() {
        return maximalToreBisGewonnen;
    }

    public void setMaximalToreBisGewonnen(int maximalToreBisGewonnen) {
        this.maximalToreBisGewonnen = maximalToreBisGewonnen;
    }

    public boolean isGespielt() {
        return gespielt;
    }

    public void setGespielt(boolean gespielt) {
        this.gespielt = gespielt;
    }

    public String getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(String startdatum) {
        this.startdatum = startdatum;
    }

    public String getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(String enddatum) {
        this.enddatum = enddatum;
    }

    public String getSpiele() {
        return spiele;
    }

    public void setSpiele(String spiele) {
        this.spiele = spiele;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public int[] getSpieleIDs() {
        if (spieleIDs == null) {
            return Arrays.stream(spiele.split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return spieleIDs;
    }

    public void setSpieleIDs(int[] spiele) {
        List<String> strings = Arrays.stream(spiele).boxed().map(id -> Integer.toString(id))
                                     .collect(Collectors.toList());
        this.spiele = String.join(",", strings);
        this.spieleIDs = spiele;
    }

    @JsonIgnore
    public int[] getTeamsIDs() {
        if (teamsIDs == null) {
            return Arrays.stream(teams.split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return teamsIDs;
    }

    public void setTeamsIDs(int[] teams) {
        List<String> strings = Arrays.stream(teams).boxed().map(id -> Integer.toString(id))
                                     .collect(Collectors.toList());
        this.teams = String.join(",", strings);
        this.teamsIDs = teams;
    }

    @Override
    public String toString() {
        if (turnierName != null) {
            return turnierName;
        }

        if (startdatum.equals(enddatum)) {
            return startdatum + " : " + id;
        }

        return startdatum + " - " + enddatum + " : " + id;
    }

    public String getTurnierName() {
        return turnierName;
    }

    public void setTurnierName(String turnierName) {
        this.turnierName = turnierName;
    }


}