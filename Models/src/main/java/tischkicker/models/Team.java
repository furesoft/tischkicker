package tischkicker.models;

import java.util.ArrayList;


class Team extends BasisKlasse {
    private String name;
    private ArrayList<Integer> spielerID = new ArrayList<>();
    private int gesamttore;
    private int gegentore;
    private boolean aufgegeben;

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