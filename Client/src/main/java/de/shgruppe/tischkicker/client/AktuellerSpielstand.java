package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;

public class AktuellerSpielstand {

    JLabel team1Name;
    JLabel team1Tore;
    JLabel team2Name;
    JLabel team2Tore;
    JButton toreTeam1Erhoehen;
    JButton toreTeam1Verringern;
    JButton toreTeam2Erhoehen;
    JButton toreTeam2Verringern;
    JButton manuellerSeitenwechsel;
    JLabel seitenwechsel;

    public AktuellerSpielstand(int width, int height){
        JFrame frame = new JFrame();

        seitenwechsel = new JLabel("SEITENWECHSEL");
        seitenwechsel.setBounds(0,0,width,(int)(height*0.2));
        seitenwechsel.setOpaque(true);
        seitenwechsel.setBackground(Color.BLACK);

        team1Name = new JLabel("FCB");
        team1Name.setBounds(0, seitenwechsel.getHeight(),width/2, (int)(height*0.3));
        team1Name.setOpaque(true);
        team1Name.setBackground(Color.RED);

        team2Name = new JLabel("BVB");
        team2Name.setBounds(team1Name.getWidth(), seitenwechsel.getHeight(), width/2, (int)(height*0.3));
        team2Name.setOpaque(true);
        team2Name.setBackground(Color.YELLOW);

        toreTeam1Erhoehen = new JButton("+");
        toreTeam1Erhoehen.setBounds(0, team1Name.getY()+team1Name.getHeight(), (int)(width*0.1), (int)(height*0.25));
        toreTeam1Erhoehen.setOpaque(true);
        toreTeam1Erhoehen.setBackground(Color.BLUE);

        toreTeam1Verringern = new JButton("-");
        toreTeam1Verringern.setBounds(0, toreTeam1Erhoehen.getY()+toreTeam1Erhoehen.getHeight(), (int)(width*0.1), (int)(height*0.25));
        toreTeam1Verringern.setOpaque(true);
        toreTeam1Verringern.setBackground(Color.GREEN);

        team1Tore = new JLabel("0");
        team1Tore.setBounds(toreTeam1Erhoehen.getWidth(), toreTeam1Erhoehen.getY(), (int)(width*0.4), (int)(height*0.5));
        team1Tore.setOpaque(true);
        team1Tore.setBackground(Color.PINK);

        team2Tore = new JLabel("1");
        team2Tore.setBounds(team1Tore.getX()+team1Tore.getWidth(), team1Tore.getY(), team1Tore.getWidth(), team1Tore.getHeight());
        team2Tore.setOpaque(true);
        team2Tore.setBackground(Color.PINK);

        toreTeam2Erhoehen = new JButton("+");
        toreTeam2Erhoehen.setBounds(team2Tore.getX()+team2Tore.getWidth(), team2Tore.getY(), team1Tore.getWidth(), team1Tore.getHeight());
        toreTeam2Erhoehen.setOpaque(true);
        toreTeam2Erhoehen.setBackground(Color.BLUE);

        toreTeam2Verringern = new JButton("-");
        toreTeam2Verringern.setBounds(toreTeam2Erhoehen.getX(), toreTeam1Verringern.getY(), team1Tore.getWidth(), team1Tore.getHeight());
        toreTeam2Verringern.setOpaque(true);
        toreTeam2Verringern.setBackground(Color.GREEN);


        frame.add(seitenwechsel);
        frame.add(team1Name);
        frame.add(team2Name);
        frame.add(toreTeam1Erhoehen);
        frame.add(toreTeam2Erhoehen);
        frame.add(toreTeam1Verringern);
        frame.add(toreTeam2Verringern);
        frame.add(team1Tore);
        frame.add(team2Tore);


        frame.setSize(width+6,height+37);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }


}
