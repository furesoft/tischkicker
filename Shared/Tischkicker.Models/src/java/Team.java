class Team extends Basisklasse {
    private String name;
    private ArrayList <int> spielerID = new ArrayList <int> ();
    private int gesamttore;
    private int gegentore;
    private boolean aufgegeben;
}

    public String getName() {
        return name;
    }

    public ArrayList<int> getSpielerID() {
        return spielerID;
    }

    public void setSpielerID(ArrayList <int> spielerID) {
        this.spielerID = spielerID;
    }

    public void setName(String name) {
        this.name = name;
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