import javax.swing.*;
import java.awt.*;

public class Spielfeld {
    JLabel background;
    JLabel team1;
    JLabel team2;
    JLabel toreTeam1;
    JLabel toreTeam2;

    public Spielfeld(JFrame frame, int x, int y, int width, int height, Spiel spiel){
        background = new JLabel(" ");
        if(spiel!= null){
            team1 = new JLabel(spiel.getTeam1().getName());
            team2 = new JLabel(spiel.getTeam2().getName());
            toreTeam1 = new JLabel(String.valueOf(spiel.getTeam1().getTore()));
            toreTeam2 = new JLabel(String.valueOf(spiel.getTeam2().getTore()));
        }else{
            team1 = new JLabel(" ");
            team2 = new JLabel(" ");
            toreTeam1 = new JLabel(" ");
            toreTeam2 = new JLabel(" ");
        }

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

    public void aktuellesSpiel(){
        getBackground().setBackground(Color.RED);
    }
    public void gewinner(){
        getTeam1().setBackground(Color.GREEN);
    }
    public void setTeams(Spiel spiel){
        team1.setText(spiel.getTeam1().getName());
        team2.setText(spiel.getTeam2().getName());
        toreTeam1.setText(String.valueOf(spiel.getTeam1().getTore()));
        toreTeam2.setText(String.valueOf(spiel.getTeam2().getTore()));
    }
    public void toggleSpielfeldAnzeige(boolean anzeigenJaNein){
        background.setVisible(anzeigenJaNein);
        team1.setVisible(anzeigenJaNein);
        team2.setVisible(anzeigenJaNein);
        toreTeam1.setVisible(anzeigenJaNein);
        toreTeam2.setVisible(anzeigenJaNein);
    }

    public JLabel getBackground() {
        return background;
    }

    public JLabel getTeam1() {
        return team1;
    }

    public JLabel getTeam2() {
        return team2;
    }
}
