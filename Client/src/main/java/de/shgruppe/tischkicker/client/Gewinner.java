package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;

public class Gewinner {
    JFrame frame = new JFrame();
    JLabel gewinner = new JLabel();

    public Gewinner() {
        gewinner.setFont(new Font("Arial", 0, 35));

        frame.add(gewinner);

        frame.setSize(350, 250);
    }

    public void show(String teamName) {
        gewinner.setText(teamName + " hat gewonnen!!!");

        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }
}
