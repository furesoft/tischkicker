package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;
public class Verbindungslinie {

    public Verbindungslinie(JFrame frame, Spielfeld spielfeld1, Spielfeld spielfeld2, Spielfeld spielfeld3, int linienStaerke){
            verbindungslinie2(frame, spielfeld1,spielfeld3,linienStaerke);
            verbindungslinie1(frame, spielfeld2, spielfeld3,linienStaerke);
    }

    public Verbindungslinie(JFrame frame, Spielfeld spielfeld1, Spielfeld spielfeld2, int linienStaerke){
        if(spielfeld1.background.getY() < spielfeld2.background.getY()){
            verbindungslinie2(frame, spielfeld1,spielfeld2,linienStaerke);
        }else{
            verbindungslinie1(frame, spielfeld1,spielfeld2,linienStaerke);
        }
    }

    public void verbindungslinie2(JFrame frame, Spielfeld spielfeld1, Spielfeld spielfeld3, int linienStaerke){
        // die oberste linie ist immer Linie 3, Linie 2 ist immer in der Mitte und Linie 1 immer unten
        //  ---------- <- Linie 3
        //	         |
        //	         |
        //	         |
        //	         |
        //	         | <- Linie 2
        //Linie 1 -> ----------

        JLabel linie1 = new JLabel(" ");
        linie1.setBounds(spielfeld3.background.getX()-(spielfeld3.background.getX()-(spielfeld1.background.getX()+spielfeld1.background.getWidth()))/2,
                spielfeld3.background.getY()+(spielfeld3.background.getHeight()/2),
                (spielfeld3.background.getX()-(spielfeld1.background.getX()+spielfeld1.background.getWidth()))/2,
                linienStaerke);
        linie1.setOpaque(true);
        linie1.setBackground(Color.BLACK);

        JLabel linie2 = new JLabel(" ");
        linie2.setBounds(spielfeld1.background.getX()+spielfeld1.background.getWidth()+
                        (spielfeld3.background.getX()-(spielfeld1.background.getX()+spielfeld1.background.getWidth()))/2,
                spielfeld1.background.getY() + (spielfeld1.background.getHeight()/2),
                linienStaerke,
                (spielfeld3.background.getY()+(spielfeld3.background.getHeight())/2)
                -(spielfeld1.background.getY()+(spielfeld1.background.getHeight())/2));
        linie2.setOpaque(true);
        linie2.setBackground(Color.BLACK);


        JLabel linie3 = new JLabel(" ");
        linie3.setBounds(spielfeld1.background.getX()+spielfeld1.background.getWidth(),
                spielfeld1.background.getY()+(spielfeld1.background.getHeight())/2,
                (spielfeld3.background.getX()-(spielfeld1.background.getX()+spielfeld1.background.getWidth()))/2,
                linienStaerke);
        linie3.setOpaque(true);
        linie3.setBackground(Color.BLACK);

        frame.add(linie1);
        frame.add(linie2);
        frame.add(linie3);
    }

    public void verbindungslinie1(JFrame frame, Spielfeld spielfeld2, Spielfeld spielfeld3, int linienStaerke){
        // die oberste linie ist immer Linie 3, Linie 2 ist immer in der Mitte und Linie 1 immer unten
        //	       ------ <- Linie 3
        //	       |
        //	       |
        //	       |
        //	       |
        //	       | <- Linie 2
        //---------- <- Linie 1

        System.out.println((spielfeld3.background.getX()-(spielfeld2.background.getX()+spielfeld2.background.getWidth()))/2);

        JLabel linie1 = new JLabel(" ");
        linie1.setBounds((spielfeld2.background.getX()+spielfeld2.background.getWidth()),
                (spielfeld2.background.getY()+(spielfeld2.background.getHeight()/2)),
                (spielfeld3.background.getX()-(spielfeld2.background.getX()+spielfeld2.background.getWidth()))/2,
                linienStaerke);
        linie1.setOpaque(true);
        linie1.setBackground(Color.BLACK);

        JLabel linie2 = new JLabel(" ");
        linie2.setBounds(spielfeld3.background.getX()-(spielfeld3.background.getX()-(spielfeld2.background.getX()+spielfeld2.background.getWidth()))/2,
                ((spielfeld3.background.getY())+(spielfeld3.background.getHeight()/2)),
                linienStaerke,
                (((spielfeld2.background.getY()) + ((spielfeld2.background.getHeight())/2))
                        - ((spielfeld3.background.getY()) + ((spielfeld3.background.getHeight())/2)))+ linienStaerke);

        linie2.setOpaque(true);
        linie2.setBackground(Color.BLACK);

        JLabel linie3 = new JLabel(" ");
        linie3.setBounds(spielfeld3.background.getX()-(spielfeld3.background.getX()-(spielfeld2.background.getX()+spielfeld2.background.getWidth()))/2,
                ((spielfeld3.background.getY())+(spielfeld3.background.getHeight()/2)),
                (spielfeld3.background.getX())-
                        (spielfeld3.background.getX()-(spielfeld3.background.getX()-(spielfeld2.background.getX()+spielfeld2.background.getWidth()))/2),
                linienStaerke);
        linie3.setOpaque(true);
        linie3.setBackground(Color.BLACK);

        frame.add(linie1);
        frame.add(linie2);
        frame.add(linie3);
    }
}


