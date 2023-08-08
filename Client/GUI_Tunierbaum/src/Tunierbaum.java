
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tunierbaum{

    ArrayList<ArrayList<Spielfeld>> reihen = new ArrayList<>();
    ArrayList<Spielfeld> spielfeldList = new ArrayList<>();
    ArrayList<Verbindungslinie> linienListe = new ArrayList<>();

    public void tunierbaumErstellen(JFrame frame, double anzahlTeams){

        int spielfelderAnzahl = (int)Math.round(anzahlTeams/2);
        int teamAnzahl = (int) anzahlTeams;
        double spielfelderAnzahlDouble;

        int y = 100; // Y-Koordinate für ein Spielfeld
        int x = 100; // X-Koordinate für ein Spielfeld

        // Zurücksetzen der Listen, bevor neue Reihen hinzugefügt werden
        reihen.clear();
        spielfeldList.clear();

        do {
            spielfeldListeFuellen(frame, x, y, spielfelderAnzahl);
            reihen.add(new ArrayList<>(spielfeldList));

            if((teamAnzahl % 2) != 0 && teamAnzahl > 2){
                reihen.get(reihen.size()-1).get(spielfeldList.size()-1).besterVerlierer();
            }

            spielfeldList.clear(); // Zurücksetzen der spielfeldList für die nächste Reihe
            x += 200;
            y = 100;
            teamAnzahl = Math.round((float) teamAnzahl /2);
            spielfelderAnzahlDouble = (double) spielfelderAnzahl / 2;
            spielfelderAnzahl = (int) Math.round(spielfelderAnzahlDouble);
        } while (spielfelderAnzahlDouble > 0.5);
        for(int reihe = 0; reihe < reihen.size(); reihe++){
            for(int spielfeld = 0; spielfeld < reihen.get(reihe).size();spielfeld++){
               if(reihe > 0){
                   if(spielfeld*2+1 < reihen.get(reihe-1).size()){
                       int newY = 0;
                       newY = ((reihen.get(reihe-1).get(spielfeld*2).background.getY()
                               + (((reihen.get(reihe-1).get(spielfeld*2+1).background.getY())+reihen.get(reihe-1).get(spielfeld*2+1).background.getHeight())))/2)
                               - reihen.get(reihe).get(spielfeld).background.getHeight()/2;
                       reihen.get(reihe).get(spielfeld).setY(newY);
                       linienListe.add(new Verbindungslinie(frame, reihen.get(reihe-1).get(spielfeld*2),reihen.get(reihe-1).get(spielfeld*2+1), reihen.get(reihe).get(spielfeld),3));
                   }else if(reihen.get(reihe).get(spielfeld).isBesterVerlierer()){
                       reihen.get(reihe).get(spielfeld).setY(reihen.get(reihe).get(spielfeld-1).background.getY()+
                               reihen.get(reihe).get(spielfeld-1).background.getHeight()+25);
                       linienListe.add(new Verbindungslinie(frame,reihen.get(reihe-1).get(reihen.get(reihe-1).size()-1), reihen.get(reihe).get(spielfeld), 3));
                       //reihen.get(reihe-1).get(reihen.get(reihe-1).size()-1).aktuellesSpiel();
                       //reihen.get(reihe).get(spielfeld).aktuellesSpiel();
                   }
               }
            }
        }

    }
    public void spielfeldListeFuellen(JFrame frame, int x, int y, int anzhalSpiele){
        for(int i = 0; i < anzhalSpiele; i++){
            spielfeldList.add(new Spielfeld(frame, x, y, 150, 100));
            y += 125;
        }
    }
    public void spielfeldFuellen(Spiel spiel, int reihe, int spielfeld){
        reihen.get(reihe).get(spielfeld).setTeams(spiel);
    }
}

