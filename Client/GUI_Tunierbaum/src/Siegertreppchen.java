import javax.swing.*;
import java.awt.*;


public class Siegertreppchen {

    JLabel treppchenPlatz1;
    JLabel erster;
    JLabel treppchenPlatz2;
    JLabel zweiter;
    JLabel treppchenPlatz3;
    JLabel dritter;

    public Siegertreppchen(JFrame frame, int x, int y, int width, int height, int unterschied /* sollte ca einem viertel der Höhe entsprechen */){
        // schön sieht aus: width: 150, height: 100, unterschied: 28
        treppchenPlatz2 = new JLabel("  #2");
        treppchenPlatz2.setBounds(x,y,width,height);
        treppchenPlatz2.setOpaque(true);
        treppchenPlatz2.setBackground(new Color(201, 197, 195));
        frame.add(treppchenPlatz2);

        treppchenPlatz1 = new JLabel("  #1");
        treppchenPlatz1.setBounds(x+width, (y-unterschied), width, (height+unterschied));
        treppchenPlatz1.setOpaque(true);
        treppchenPlatz1.setBackground(new Color(149, 157, 158));
        frame.add(treppchenPlatz1);

        treppchenPlatz3 = new JLabel("  #3");
        treppchenPlatz3.setBounds((x+(2*width)), (y+unterschied), width, (height-unterschied));
        treppchenPlatz3.setOpaque(true);
        treppchenPlatz3.setBackground(new Color(209, 210, 209));
        frame.add(treppchenPlatz3);
    }

}
