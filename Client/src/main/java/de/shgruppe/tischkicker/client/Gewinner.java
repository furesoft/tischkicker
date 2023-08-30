package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;

public class Gewinner {
    JFrame frame = new JFrame();
    JLabel gewinner = new JLabel();

    public Gewinner() {
        gewinner.setFont(new Font("Arial", 0, 70));

        frame.add(gewinner);

        frame.setSize(1000, 500);
    }

    public void show(String teamName) {
        if(teamName.length()>20){
            gewinner.setText("<html>" + teamName + " hat gewonnen!!!<br></html>");
            //frame.setSize(SwingUtilities.computeStringWidth(frame.getGraphics().getFontMetrics(gewinner.getFont()), gewinner.getText()),
            //        500);
        }else{
            gewinner.setText(teamName + " hat gewonnen!!!");
            //frame.setSize(SwingUtilities.computeStringWidth(frame.getGraphics().getFontMetrics(gewinner.getFont()), gewinner.getText()),
            //        500);
        }

        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }
}
