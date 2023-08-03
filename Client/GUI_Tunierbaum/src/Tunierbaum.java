import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tunierbaum {

    ArrayList<Spielfeld> spielfeldList = new ArrayList<>();



    public void tunierbaumErstellen(JFrame frame, Spiel[] spiele){

        int anzahlSpielfelder = spiele.length;

        boolean endeZeichnen = false;

        int y = 100; // Y-Koordinate für ein Spielfeld
        int x = 100; // X-Koordinate für ein Spielfeld
        while (anzahlSpielfelder > 0.5){
            for(int i = 0; i < anzahlSpielfelder; i++)/* for (Spiel spiel : spiele) */{
                // diese Schleife ist für die Anzahl der zu zeichnenden Spielfelder verantwortlich
                spielfeldList.add(new Spielfeld(frame, x, y, 150, 100, spiele[i]));
                y += 125;
            }
            x += 200;
            y = 100;
            anzahlSpielfelder /=2;
        }
    }

    public void tunierbaumAusblenden(boolean anzeigenJaNein){
        for (Spielfeld spielfeld : spielfeldList) {
            spielfeld.toggleSpielfeldAnzeige(anzeigenJaNein);
        }
    }

}
