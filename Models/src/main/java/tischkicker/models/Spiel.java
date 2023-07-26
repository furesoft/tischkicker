package tischkicker.models;

import java.util.Date;

class Spiel extends BasisKlasse {
    private Date spieldatum;

    private String[] teams = new String[2];

    private int toreteam1;
    private int toreteam2;

    private int qualifikation;

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