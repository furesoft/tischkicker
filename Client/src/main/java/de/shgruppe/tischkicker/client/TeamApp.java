package de.shgruppe.tischkicker.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TeamApp extends JFrame {

    private JTextField[] playerFields;
    private JTextField teamNameField;
    private JButton addButton;
    private JButton startButton;
    public TeamManager teams;
    private JTextArea outputTextArea;
    JPanel panel = new JPanel();
    public TeamApp() {
        panel.setBackground(Color.BLACK);
        setTitle("Team App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        Color color = new Color(46, 50, 52);
        panel.setBackground(color);

        playerFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            playerFields[i] = new JTextField(15);
        }


        teamNameField = new JTextField(15);
        outputTextArea = new JTextArea(6, 21);
        outputTextArea.setEditable(false);

        addButton = new JButton("Team hinzufügen");
        startButton = new JButton("Start");
        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputTextArea));





        // Set actions for Buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeam();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        panel.setLayout(new GridLayout(8, 2, 11, 11));
        add(panel);




        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel("Spieler " + (i + 1) + ":");
            label.setForeground(new Color(255, 215, 0));
            panel.add(label);
            panel.add(playerFields[i]);
        }

        // Add label and TextField/teams
        JLabel teamname = new JLabel("Teamname:");
        teamname.setForeground(new Color(255, 215, 0));
        panel.add(teamname);
        panel.add(teamNameField);
        panel.add(addButton);
        panel.add(startButton);
        panel.add(outputPanel);


    }

    private void addTeam() {
        String[] players = new String[4];
        boolean hasPlayer = false;
        for (int i = 0; i < 4; i++) {
            players[i] = playerFields[i].getText();
            if (!players[i].isEmpty()) {
                hasPlayer = true;
            }
        }

        String teamName = teamNameField.getText();
        if (hasPlayer && !teamName.isEmpty()) {
            teams = new TeamManager(players,teamName);
            teams.setTeamManagers(teams);

            outputTextArea.setText("Team '" + teamName + "' wurde hinzugefügt.");

            for (int i = 0; i < 4; i++) {
                playerFields[i].setText("");
            }
            teamNameField.setText("");
        } else {
            outputTextArea.setText("Mindestens ein Spieler und ein Teamname erforderlich!");
        }
    }

    private void startGame() {
        if (teams.getNumTeams() < 2) {
            outputTextArea.setText("Mindestens zwei Teams erforderlich!");

        }
    else{
        teams.startGame();
            outputTextArea.setText("Spiel gestartet!");}
    }


}
