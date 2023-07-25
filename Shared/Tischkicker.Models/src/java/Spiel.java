class Spiel extends Basisklasse {



    private Date spieldatum ;

    private String[] teams = new String[];

    private int plazierung ;

   private int toreteam1 ;
   private int toreteam2 ;

   private int qualifikation ;

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

   public int getPlazierung() {
      return plazierung;
   }

   public void setPlazierung(int plazierung) {
      this.plazierung = plazierung;
   }

   public int getTore() {
      return tore;
   }

   public void setTore(int tore) {
      this.tore = tore;
   }

   public int getQualifikation() {
      return qualifikation;
   }

   public void setQualifikation(int qualifikation) {
      this.qualifikation = qualifikation;
   }
}