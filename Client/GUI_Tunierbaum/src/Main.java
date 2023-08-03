import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame f=new JFrame();

        Team t1 = new Team("FC Bayern", 1);
        Team t2 = new Team("ST. Pauli", 7);
        Spiel[] spiele = new Spiel[8];
        spiele[0] = new Spiel(0,t1, t2);
        spiele[1]= new Spiel(1,t1, t2);
        spiele[2] = new Spiel(2,t1, t2);
        spiele[3] = new Spiel(3,t1, t2);
        spiele[4] = new Spiel(0,t1, t2);
        spiele[5]= new Spiel(1,t1, t2);
        spiele[6] = new Spiel(2,t1, t2);
        spiele[7] = new Spiel(3,t1, t2);


        Spielfeld sf = new Spielfeld(f, 450,450,150,100 ,null);
        sf.aktuellesSpiel();
        sf.gewinner();
        sf.setTeams(spiele[1]);
        //sf.spielfeldAusblenden();


        Tunierbaum t = new Tunierbaum();
        t.tunierbaumErstellen(f, spiele);
        //t.tunierbaumAusblenden(false);
        t.tunierbaumAusblenden(true);


        Siegertreppchen sieger = new Siegertreppchen(f, 1400, 900, 150, 100, 28);

        /*
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true);
        f.setVisible(true);
        */
        f.setSize(1920,1080);
        f.setLayout(null);
        f.setVisible(true);


    }
}