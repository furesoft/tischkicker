class Turnier extends Basisklasse
{
    private Date startdatum;
    private Date enddatum;
    private ArrayList <int> spieleID = new ArrayList <int> ();
    private ArrayList <int> teamsID = new ArrayList <int> ();

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
        this.enddatum = enddatum;
    }

    public ArrayList<int> getSpieleID() {
        return spieleID;
    }

    public void setSpieleID(ArrayList<int> spieleID) {
        this.spieleID = spieleID;
    }

    public ArrayList<int> getTeamsID() {
        return teamsID;
    }

    public void setTeamsID(ArrayList<int> teamsID) {
        this.teamsID = teamsID;
    }
}