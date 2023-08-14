import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame f=new JFrame();


        Team t1 = new Team("FC Bayern",3);
        Team t2 = new Team("BVB",0);
        Spiel s1 = new Spiel(0, t1, t2);

        Spielfeld sf = new Spielfeld(f, 800,600,150,400);
        sf.aktuellesSpiel();
        sf.gewinnerTeam1();
        sf.setTeams(s1);


        //Spielfeld sf1 = new Spielfeld(f, 400, 400, 150, 100);
        //Spielfeld sf2 = new Spielfeld(f, 400, 525, 150, 100);
        //Spielfeld sf3 = new Spielfeld(f, 600, 450, 150, 100);
        //Verbindungslinie vl1 = new Verbindungslinie(f, sf1, sf2, sf3,5);


        Tunierbaum t = new Tunierbaum();
        t.tunierbaumErstellen(f, 9);
        //t.tunierbaumAusblenden(true);
       // t.spielfeldFuellen(s1,0,1);


        Siegertreppchen sieger = new Siegertreppchen(f, 1400, 900, 150, 100, 28);
        //AktuellerSpielstand a1 = new AktuellerSpielstand(f);

        AktuellerSpielstand a1 = new AktuellerSpielstand(800,400);

        f.setSize(1920,1080);
        f.setLayout(null);
        f.setVisible(true);

    }
}