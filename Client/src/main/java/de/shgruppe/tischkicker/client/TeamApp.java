package de.shgruppe.tischkicker.client;

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
    private JTextField playerField = new JTextField(15);// Textfeld für Spielername
    private JTextField teamNameField = new JTextField(15);// Textfeld für Teamname
    private JButton saveButtonTeams = new JButton("Speichern");// Button zum Speichern der bearbeiteten Teamnamen
    private JButton saveButtonPlayers = new JButton("Speichern");// Button zum Speichern der bearbeiteten Spielernamen
    private JButton addPlayerButton;// Button zum Hinzufügen von Spielern
    private JButton addTeamButton;// Button zum Hinzufügen von Teams
    private JButton addConfigButton;// Button zum Öffnen der Konfigurationsansicht
    private JButton startButton;// Button zum Starten des Spiels

    private DataButton deletePlayerButton;
    private JFrame fenster;
    private Team team;
    JPanel reihe;
    public static List<Team> teams = new ArrayList<>();// Liste aller Teams
    public List<JTextField> teamNameFields = new ArrayList<>();// Liste der Textfelder für Teamnamen
    public List<JTextField> playerNameFields = new ArrayList<>();// Liste der Textfelder für Spielernamen
    private JTextArea outputTextArea = new JTextArea(6, 21);// Textfeld für Ausgaben
    private JPanel panel = new JPanel();// Hauptpanel für die GUI
    private JPanel spielerPanel;
    private List<String> nameSpielerallerTeams;


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
        setSize(600, 500);
        Color backgroundColor = new Color(40, 44, 52);
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        tempPlayers = new ArrayList<>();
        initRows();

        addPlayerButton = new JButton("Spieler hinzufügen");
        addPlayerButton.addActionListener(new ActionListener() {
            // Aufruf der Methode zum Hinzufügen von Spielern
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });

        addTeamButton = new JButton("Team hinzufügen");

        addTeamButton.setEnabled(false);
        addTeamButton.addActionListener(new ActionListener() {
            // Aufruf der Methode zum Hinzufügen von Teams
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
        addConfigButton.setEnabled(false);
        addConfigButton.addActionListener(new ActionListener() {
            // Aufruf der Methode zur Konfiguration von Spielern und Teams
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
        panel.add(addTeamButton);
        panel.add(startButton);
        panel.add(addConfigButton);
        panel.add(new JScrollPane(outputTextArea));

        initActionListener();
    }

    private void addPlayer() {
        String player = playerField.getText();
        if (!player.isEmpty()) {
            // Hinzufügen des Spielers zur temporären Liste
            tempPlayers.add(player);
            outputTextArea.append("Spieler '" + player + "' wurde hinzugefügt.\n");
            playerField.setText("");
            addTeamButton.setEnabled(true);
        } else {
            outputTextArea.append("Spielername erforderlich!\n");
        }
    }

    private void addTeam() {
        String teamName = teamNameField.getText();
        if (!teamName.isEmpty() && !tempPlayers.isEmpty()) {
            team = new Team(tempPlayers, teamName);// Erstellen eines neuen Teams
            Client.sendTeamsToServer(team);// Senden des Teams an den Server (Annahme)
            //TODO sobald Client Server verbindung aufgebaut ist teams.add(team entfernen)
            teams.add(team);
            outputTextArea.append("Team '" + teamName + "' wurde hinzugefügt.\n");
            tempPlayers = new ArrayList<>();// Zurücksetzen der temporären Spielerliste
            playerField.setText("");
            teamNameField.setText("");
            addConfigButton.setEnabled(true);

        } else {
            outputTextArea.append("Mindestens ein Spieler und ein Teamname erforderlich!\n");
        }
        addTeamButton.setEnabled(false);
    }



    // Methode zur Konfiguration von Spieler- und Teamnamen
    public void config() {
        fenster = new JFrame();
        fenster.setTitle("Bearbeiten");
        fenster.setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();
        // Reiter für "Spielername ändern"
        spielerPanel = new JPanel();
        spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.PAGE_AXIS));

        // Sammeln aller Spieler aller Teams in einer Liste
        nameSpielerallerTeams =
                teams.stream().flatMap(team -> team.players.stream()).collect(Collectors.toList());

        int playerIndex = 0;
                // Teamnamen hinzufügen
        for (Team team : teams) {
            JLabel teamLabel = new JLabel("Team " + team.getName());

            spielerPanel.add(teamLabel);

            // Spielernamen hinzufügen
            // Hinzufügen der Textfelder für die Spielernamen und Lösch-Buttons
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String spielerName = nameSpielerallerTeams.get(playerIndex);
                JTextField spielerNameField = new JTextField(spielerName);

                spielerNameField.setColumns(20);

                deletePlayerButton = new DataButton("Löschen");
                deletePlayerButton.setData(spielerName);

                deletePlayerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DataButton sender = (DataButton) e.getSource();

                        deletePlayerByID(sender);
                    }
                });

                reihe = new JPanel();
                reihe.add(spielerNameField);
                reihe.add(deletePlayerButton);

                spielerPanel.add(reihe);
                playerNameFields.add(spielerNameField);
                playerIndex++;
            }
        }

        spielerPanel.add(saveButtonPlayers);
        tabbedPane.addTab("Spielername ändern", spielerPanel);

        JPanel teamPanel = new JPanel();
        // Hinzufügen der Textfelder für die Teamnamen und Lösch-Buttons
        for (int i = 0; i < teams.size(); i++) {
            JTextField teamNameField = new JTextField(15);
            Team team1 = teams.get(i);

            DataButton deleteTeamButton = new DataButton("Löschen");
            deleteTeamButton.setData(playerIndex);

            //Zu jedem Button wird eine eindeutiger int mitgeschickt der dann für die Löschung der Teams zuständig ist
            deleteTeamButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DataButton sender = (DataButton) e.getSource();

                    Team team2 = (Team) sender.getData();
                   deleteTeams(team2);
                }
            });

            teamPanel.add(teamNameField);
            teamNameField.setText(team1.getName());
            teamNameFields.add(teamNameField);
            teamPanel.add(deleteTeamButton);

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


    private void deletePlayerByID(DataButton btn) {
        String playerNameToBeRemoved = (String)btn.getData();

        for (int j = 0; j < teams.size(); j++) {
            Team team = teams.get(j);
            List <String> playerNamesTeam1 = team.getPlayerNames();
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String playerName = playerNamesTeam1.get(i);
                if (playerName.equals(playerNameToBeRemoved)) {

                    if (team.players.size() > 1) {
                        // Spielerpanel löschen
                        spielerPanel.remove(btn.getParent());
                        spielerPanel.invalidate();

                        team.players.remove(playerName);
                        Client.sendTeamsToServer(team);

                        // Entferne das Panel des Spielers aus dem spielerPanel
                        spielerPanel.repaint();
                        //spielerPanel.revalidate();
                        return;
                    } else {
                        System.out.println("Zu wenig Spieler im Team!!");
                    }
                }

            }

        }


    }
    //TODO Löschen der teams
    private void deleteTeams(Team team){

        Client.deleteTeam(team);

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
