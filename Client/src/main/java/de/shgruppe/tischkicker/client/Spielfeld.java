package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Spielfeld {
    JLabel background;
    JLabel team1;
    JLabel team2;
    JLabel toreTeam1;
    JLabel toreTeam2;
    boolean besterVerlierer = false;
    Spiel spiel;

    public Spielfeld(JFrame frame, int x, int y, int width, int height){
        background = new JLabel(" ");
        team1 = new JLabel(" ");
        team2 = new JLabel(" ");
        toreTeam1 = new JLabel(" ");
        toreTeam2 = new JLabel(" ");

        background.setBounds(x,y,width,height);
        background.setOpaque(true);
        background.setBackground(new Color(149, 157, 158));

        team1.setBounds(x+5, y+5, width-10, ((height-10)/2));
        team1.setOpaque(true);
        team1.setBackground(new Color(209, 210, 209));
        toreTeam1.setBounds(x+width-30, y+5, 25, ((height-10)/2));
        toreTeam1.setOpaque(true);
        toreTeam1.setBackground(Color.WHITE);

        team2.setBounds(x+5, (int)(y+5+(height *0.9)/2),width-10, ((height-10)/2));
        team2.setOpaque(true);
        team2.setBackground(new Color(201, 197, 195));
        toreTeam2.setBounds(x+width-30,  (int)(y+5+(height *0.9)/2), 25, ((height-10)/2));
        toreTeam2.setOpaque(true);
        toreTeam2.setBackground(Color.WHITE);

        frame.add(toreTeam1);
        frame.add(toreTeam2);
        frame.add(team1);
        frame.add(team2);
        frame.add(background);
    }

    public Spiel aktuellesSpiel(){
        background.setBackground(Color.RED);
        return spiel;
    }
    public void gewinnerTeam1(){
        team1.setBackground(Color.GREEN);
    }
    public void gewinnerTeam2(){
        team2.setBackground(Color.GREEN);
    }
    public void setTeams(Spiel spiel){
        this.spiel = spiel;
        team1.setText(spiel.getTeam1().getName());
        if(besterVerlierer){
            team2.setText(" * " + spiel.getTeam2().getName());
        }else{
            team2.setText(spiel.getTeam2().getName());

        }
       // toreTeam1.setText(String.valueOf(spiel.getTeam1().getTore()));
        //toreTeam2.setText(String.valueOf(spiel.getTeam2().getTore()));
    }
    public void setY(int y){
        background.setBounds(background.getX(),y,background.getWidth(),background.getHeight());
        team1.setBounds(team1.getX(), y+5, team1.getWidth(),team1.getHeight());
        team2.setBounds(team2.getX(), (int)(y+5+(background.getHeight() *0.9)/2), team2.getWidth(),team2.getHeight());
        toreTeam1.setBounds(toreTeam1.getX(),y+5, toreTeam1.getWidth(),toreTeam1.getHeight());
        toreTeam2.setBounds(toreTeam2.getX(), (int)(y+5+(background.getHeight() *0.9)/2), toreTeam2.getWidth(),toreTeam2.getHeight());
    }
    public void besterVerlierer(){
        besterVerlierer = true;
        team2.setText(" *");
    }

    public boolean isBesterVerlierer() {
        return besterVerlierer;
    }
}
