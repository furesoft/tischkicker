package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.shgruppe.tischkicker.client.Client.getSpieleFromServer;
import static de.shgruppe.tischkicker.client.Client.sendTeamsToServer;
import static de.shgruppe.tischkicker.client.Team.counter;

public class TeamApp extends JFrame {

    private List<String> tempPlayers;
    private JTextField playerField = new JTextField(15);
    private JTextField teamNameField = new JTextField(15);
    private JButton saveButton = new JButton("Speichern");
    private JButton addPlayerButton;
    private JButton addButton;
    private JButton addConfigButton;
    private JButton startButton;
    private JFrame fenster;
    private Team team;

    public static List<Team> teams = new ArrayList<>();
    public List<JTextField> teamNameFields = new ArrayList<>();
    private JTextArea outputTextArea = new JTextArea(6, 21);
    private JPanel panel = new JPanel();

    private void initRows() {
        outputTextArea.setEditable(false);

        panel.setLayout(new GridLayout(9, 2, 11, 11));
        add(panel);

        JLabel label = new JLabel("Spieler:");
        label.setForeground(new Color(0, 0, 0));
        panel.add(label);
        panel.add(playerField);

        JLabel teamname = new JLabel("Teamname:");
        teamname.setForeground(new Color(0, 0, 0));
        panel.add(teamname);
        panel.add(teamNameField);

        teamNameField.setText("Erst alle Spieler hinzufügen");
    }

    public TeamApp() {
        setTitle("Team App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        Color backgroundColor = new Color(108, 107, 107, 255);
        panel.setBackground(backgroundColor);

        tempPlayers = new ArrayList<>();
        initRows();

        addPlayerButton = new JButton("Spieler hinzufügen");
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });

        addButton = new JButton("Team hinzufügen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeam();
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        addConfigButton = new JButton("Bearbeiten");
        addConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config();
            }
        });

        panel.add(addPlayerButton);
        panel.add(addButton);
        panel.add(startButton);
        panel.add(addConfigButton);

        panel.add(new JScrollPane(outputTextArea));
    }

    private void addPlayer() {
        String player = playerField.getText();
        if (!player.isEmpty()) {
            tempPlayers.add(player);
            outputTextArea.append("Spieler '" + player + "' wurde hinzugefügt.\n");
            playerField.setText("");
            scrollToBottom();
        } else {
            outputTextArea.append("Spielername erforderlich!\n");
        }
    }

    private void addTeam() {
        String teamName = teamNameField.getText();
        if (!teamName.isEmpty() && !tempPlayers.isEmpty()) {
            team = new Team(tempPlayers, teamName);
            teams.add(team);

            outputTextArea.append("Team '" + teamName + "' wurde hinzugefügt.\n");
            sendTeamsToServer();
            tempPlayers = new ArrayList<>();
            playerField.setText("");
            teamNameField.setText("");
        } else {
            outputTextArea.append("Mindestens ein Spieler und ein Teamname erforderlich!\n");
        }
    }

    public void config() {
        fenster = new JFrame();
        fenster.setTitle("Bearbeiten");
        fenster.setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Reiter für "Spielername ändern"
        JPanel spielerPanel = new JPanel();
        spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.PAGE_AXIS));

        int anzahlSpielerallerTeams = teams.stream().mapToInt(team -> team.players.size()).sum();
        List<String> nameSpielerallerTeams =
                teams.stream().flatMap(team -> team.players.stream()).collect(Collectors.toList());

        int playerIndex = 0;
        for (Team team : teams) {
            // teams hinzufügen
            JLabel teamLabel = new JLabel("Team " + team.getTeamName());
            spielerPanel.add(teamLabel);

            // Spielernamen hinzufügen
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String spielerName = nameSpielerallerTeams.get(playerIndex);
                JTextArea spielerNameArea = new JTextArea(spielerName);
                spielerNameArea.setLineWrap(true);
                spielerNameArea.setWrapStyleWord(true);
                spielerPanel.add(spielerNameArea);
                playerIndex++;
            }
        }
        tabbedPane.addTab("Spielername ändern", spielerPanel);

        // Reiter für "Teamname ändern"
        JPanel teamPanel = new JPanel();
        for (int i = 0; i < counter; i++) {
            JTextField teamNameField = new JTextField(15);
            teamPanel.add(teamNameField);

            Team team1 = teams.get(i);
            teamNameField.setText(team1.getTeamName());

            teamNameFields.add(teamNameField);
        }
        initActionListener();
        tabbedPane.addTab("Teamname ändern", teamPanel);
        teamPanel.add(saveButton);
        fenster.add(tabbedPane);
        fenster.setVisible(true);
        scrollToBottom();
    }

    private void updateTeamNames() {
        for (int i = 0; i < teams.size(); i++) {
            Team value = teams.get(i);
            JTextField teamNameField = teamNameFields.get(i);

            String newTeamName = teamNameField.getText();
            value.setTeamName(newTeamName);
        }
    }

    public void initActionListener() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeamNames();
                teamNameFields.clear();
                outputTextArea.append("Teamnamen gespeichert!\n");
            }
        });
    }

    private void scrollToBottom() {
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }

    private void startGame() {
        if (team.getNumTeams() < 2) {
            outputTextArea.append("Mindestens zwei Teams erforderlich!\n");
            getSpieleFromServer();
        } else {
            team.startGame();
            for (int i = 0; i < teams.size(); i++) {
                System.out.println(teams.get(i).teamName);
            }
            outputTextArea.append("Spiel gestartet!\n");
        }
    }
}
