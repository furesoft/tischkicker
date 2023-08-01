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

        setTitle("Team App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        // Create TextFields for players
        playerFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            playerFields[i] = new JTextField(15);
        }

        // Create TextField for team name
        teamNameField = new JTextField(15);
        outputTextArea = new JTextArea(6, 21);
        outputTextArea.setEditable(false);
        // Create Buttons
        addButton = new JButton("Team hinzufügen");
        startButton = new JButton("Start");
        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputTextArea));



        // Füge das JTextArea-Element zur GUI hinzu

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
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        add(panel);


        // Create JPanel and set layout



        // Add labels and TextFields for players
        for (int i = 0; i < 4; i++) {
            panel.add(new JLabel("Spieler " + (i + 1) + ":"));
            panel.add(playerFields[i]);
        }

        // Add label and TextField for team name
        panel.add(new JLabel("Teamname:"));
        panel.add(teamNameField);
        panel.add(addButton);
        panel.add(startButton);
        panel.add(outputPanel);
        // Add Buttons

        // Add panel to frame

        // Create TeamManager instance

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TeamApp().setVisible(true);
            }
        });
    }
}
