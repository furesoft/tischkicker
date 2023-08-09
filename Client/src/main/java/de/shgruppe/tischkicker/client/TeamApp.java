package de.shgruppe.tischkicker.client;

import com.sun.xml.bind.v2.TODO;
import de.shgruppe.tischkicker.client.UI.DataButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamApp extends JFrame {
    private List<String> tempPlayers;
    private JTextField playerField = new JTextField(15);
    private JTextField teamNameField = new JTextField(15);
    private JButton saveButtonTeams = new JButton("Speichern");
    private JButton saveButtonPlayers = new JButton("Speichern");
    private JButton addPlayerButton;
    private JButton addButton;
    private JButton addConfigButton;
    private JButton startButton;
    private JFrame fenster;
    private Team team;

    JPanel playerPanel;
    public static List<Team> teams = new ArrayList<>();
    public List<JTextField> teamNameFields = new ArrayList<>();
    public List<JTextField> playerNameFields = new ArrayList<>();
    private JTextArea outputTextArea = new JTextArea(6, 21);
    private JPanel panel = new JPanel();

    private boolean fensterSchließen=false;

    private void initRows() {
        outputTextArea.setEditable(false);
        panel.setLayout(new GridLayout(9, 2, 11, 11));
        add(panel);

        JLabel label = new JLabel("Spieler:");
        label.setForeground(new Color(0, 255, 255));
        panel.add(label);
        panel.add(playerField);

        JLabel teamname = new JLabel("Teamname:");
        teamname.setForeground(new Color(0, 255, 255));
        panel.add(teamname);
        panel.add(teamNameField);

        teamNameField.setText("Erst alle Spieler hinzufügen");
    }

    public TeamApp() {
        setTitle("Team App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        Color backgroundColor = new Color(40, 44, 52);
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        saveButtonPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerNames();
                outputTextArea.append("Spielername gespeichert!\n");
            }
        });

        panel.add(addPlayerButton);
        panel.add(addButton);
        panel.add(startButton);
        panel.add(addConfigButton);
        panel.add(new JScrollPane(outputTextArea));

        initActionListener();
    }

    private void addPlayer() {
        String player = playerField.getText();
        if (!player.isEmpty()) {
            tempPlayers.add(player);
            outputTextArea.append("Spieler '" + player + "' wurde hinzugefügt.\n");
            playerField.setText("");
        } else {
            outputTextArea.append("Spielername erforderlich!\n");
        }
    }

    private void addTeam() {
        String teamName = teamNameField.getText();
        if (!teamName.isEmpty() && !tempPlayers.isEmpty()) {
            team = new Team(tempPlayers, teamName);
            Client.sendTeamsToServer(team);
            outputTextArea.append("Team '" + teamName + "' wurde hinzugefügt.\n");
            tempPlayers = new ArrayList<>();
            playerField.setText("");
            teamNameField.setText("");
        } else {
            outputTextArea.append("Mindestens ein Spieler und ein Teamname erforderlich!\n");
        }
    }
    // Methode zur Konfiguration von Spieler- und Teamnamen
    public void config() {
        fenster = new JFrame();
        fenster.setTitle("Bearbeiten");
        fenster.setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();
        // Reiter für "Spielername ändern"
        JPanel spielerPanel = new JPanel();
        spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.PAGE_AXIS));

        List<String> nameSpielerallerTeams =
                teams.stream().flatMap(team -> team.players.stream()).collect(Collectors.toList());

        int playerIndex = 0;
        // Teamnamen hinzufügen
        for (Team team : teams) {
            JLabel teamLabel = new JLabel("Team " + team.getName());
            spielerPanel.add(teamLabel);

            // Spielernamen hinzufügen
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String spielerName = nameSpielerallerTeams.get(playerIndex);
                JTextField spielerNameField = new JTextField(spielerName);

                spielerNameField.setColumns(20);

                DataButton deletePlayerButton = new DataButton("Löschen");
                deletePlayerButton.setData(playerIndex);

                deletePlayerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DataButton sender = (DataButton) e.getSource();
                        int id = (int) sender.getData();
                        deletePlayerByID(id);
                    }
                });
                playerPanel = new JPanel();
                playerPanel.add(spielerNameField);
                playerPanel.add(deletePlayerButton);
                spielerPanel.add(playerPanel);
                playerNameFields.add(spielerNameField);
                playerIndex++;
            }
        }

        spielerPanel.add(saveButtonPlayers);
        tabbedPane.addTab("Spielername ändern", spielerPanel);

        JPanel teamPanel = new JPanel();
        for (int i = 0; i < teams.size(); i++) {
            JTextField teamNameField = new JTextField(15);
            teamPanel.add(teamNameField);

            Team team1 = teams.get(i);
            teamNameField.setText(team1.getName());

            teamNameFields.add(teamNameField);
        }
        tabbedPane.addTab("Teamname ändern", teamPanel);
        teamPanel.add(saveButtonTeams);
        fenster.add(tabbedPane);
        fenster.setVisible(true);
        scrollToBottom();
    }
    // Methode zum Aktualisieren von Spielernamen
    private void updatePlayerNames() {
        int playerIndex = 0;

        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);

            for (int j = 0; j < team.getNumberOfPlayersPerTeam(); j++) {
                JTextField playerNameField = playerNameFields.get(playerIndex);
                String newPlayerName = playerNameField.getText();

                team.setPlayerName(j, newPlayerName);
                Client.sendTeamsToServer(team);

                playerIndex++;
            }
        }
    }
    // Methode zum Aktualisieren von Teamnamen
    private void updateTeamNames() {
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            JTextField teamNameField = teamNameFields.get(i);

            String newTeamName = teamNameField.getText();
            team.setTeamName(newTeamName);
        }
    }

    //TODO Methode zum löschen der Spieler
    private void deletePlayerByID(int playerIndex) {
        int removeNameFields=playerIndex;
        int index = 0;
        int counter=0;
        for (Team team : teams) {

            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {

                if (counter == team.getNumberOfPlayersPerTeam()){
                    playerIndex-=counter;
                    index-=counter;
                }
                counter +=1;
                if (index == playerIndex) {
                    System.out.println(index +" " + playerIndex+" "+i);
                    if (team.players.size() > 1) {
                        team.players.remove(index);
                        Client.sendTeamsToServer(team);

                        // Entferne das Panel des Spielers aus dem spielerPanel
                        playerNameFields.remove(removeNameFields);
                        playerPanel.remove(removeNameFields);

                        fenster.dispose();

                        playerPanel.repaint();
                        playerPanel.revalidate();
                        return;
                    } else {
                        System.out.println("Zu wenig Spieler im Team!!");
                    }

                }

                index++;
                //config();
            }

        }


    }
    //TODO Löschen der teams
    private void deleteTeams(){


    }

    // Methode zum Initialisieren des ActionListeners zum Speichern von Teamnamen
    public void initActionListener() {
        saveButtonTeams.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeamNames();
                teamNameFields.clear();
                outputTextArea.append("Teamnamen gespeichert!\n");
            }
        });
    }

    // Methode zum Scrollen von outputTextArea nach unten
    private void scrollToBottom() {
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }


    // Methode zum Starten des Spiels mit validierten Teams
    private void startGame() {
        if (team.getNumTeams() < 2) {
            outputTextArea.append("Mindestens zwei Teams erforderlich!\n");
        } else {
            team.startGame();
            Client.turnierbaumGenereieren();
            for (int i = 0; i < teams.size(); i++) {
                System.out.println(teams.get(i).teamName);
            }
            int anzahlSpielerallerTeams = teams.stream().mapToInt(team -> team.players.size()).sum();
            for (int i = 0; i < anzahlSpielerallerTeams; i++) {
                System.out.println(teams.get(i).players);
            }
            outputTextArea.append("Spiel gestartet!\n");
        }
    }
}
