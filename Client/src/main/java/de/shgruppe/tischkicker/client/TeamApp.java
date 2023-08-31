package de.shgruppe.tischkicker.client;


import de.shgruppe.tischkicker.client.ui.DataButton;
import tischkicker.models.Spiel;
import tischkicker.models.Spieler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.shgruppe.tischkicker.client.Client.*;

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
    private JFrame bearbeitenFenster;
    JPanel teamPanel;
    JPanel deleteButtonundSpielernamen;
    public static List<Team> teams = new ArrayList<>();// Liste aller Teams
    public List<JTextField> teamNameFields = new ArrayList<>();// Liste der Textfelder für Teamnamen
    public List<JTextField> playerNameFields = new ArrayList<>();// Liste der Textfelder für Spielernamen
    private JTextArea outputTextArea = new JTextArea(6, 21);// Textfeld für Ausgaben
    private JPanel hauptPanel = new JPanel();// Hauptpanel für die GUI
    List <DataButton> buttonNamen= new ArrayList<>();
    private JPanel spielerTab;
    private List<String> nameSpielerallerTeams;



    private void initRows() {
        outputTextArea.setEditable(false);
        hauptPanel.setLayout(new GridLayout(9, 2, 11, 11));
        add(hauptPanel);

        JLabel label = new JLabel("Spieler:");
        label.setForeground(new Color(0, 255, 255));
        hauptPanel.add(label);
        hauptPanel.add(playerField);

        JLabel teamname = new JLabel("Teamname:");
        teamname.setForeground(new Color(0, 255, 255));
        hauptPanel.add(teamname);
        hauptPanel.add(teamNameField);

        teamNameField.setText("Erst alle Spieler hinzufügen");
    }

    public TeamApp() {
        setTitle("Team App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        Color backgroundColor = new Color(40, 44, 52);
        hauptPanel.setBackground(backgroundColor);
        hauptPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
                if (getSpieleFromServer() != null)
                {

                    Spiel[] spiele = Client.startTurnier();
                    turnierbaumGenerieren(spiele);
                    turnierbaum.ergebnisAmAnfang(spiele);
                }
                else
                {
                    Spiel[] spiele = Client.startTurnier();
                    turnierbaumGenerieren(spiele);
                }

            }
        });


        addConfigButton = new JButton("Bearbeiten");
        addConfigButton.setEnabled(false);
        addConfigButton.addActionListener(new ActionListener() {
            // Aufruf der Methode zur Konfiguration von Spielern und Teams
            @Override
            public void actionPerformed(ActionEvent e) {

                config();
               // addConfigButton.setEnabled(false);
            }
        });

        saveButtonPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerNames();
                outputTextArea.append("Spielername gespeichert!\n");
            }
        });

        hauptPanel.add(addPlayerButton);
        hauptPanel.add(addTeamButton);
        hauptPanel.add(startButton);
        hauptPanel.add(addConfigButton);
        hauptPanel.add(new JScrollPane(outputTextArea));

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
            Team team = new Team(tempPlayers, teamName);// Erstellen eines neuen Teams
            teams.add(team);
            Client.sendTeamsToServer(team);// Senden des Teams an den Server (Annahme)
            //TODO sobald Client Server verbindung aufgebaut ist teams.add(team entfernen)

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

        playerNameFields.clear();
        buttonNamen.clear();
        addTeamButton.setEnabled(false);
        addPlayerButton.setEnabled(false);
        bearbeitenFenster = new JFrame();
        bearbeitenFenster.setTitle("Bearbeiten");
        bearbeitenFenster.setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Reiter für "Spielername ändern"
        spielerTab = new JPanel();
        spielerTab.setLayout(new BoxLayout(spielerTab, BoxLayout.PAGE_AXIS));

        // Sammeln aller Spieler aller Teams in einer Liste
        List <Spieler> spielers= List.of(Objects.requireNonNull(getSpieler()));

        int playerIndex = 0;
        // Teamnamen hinzufügen
        for (Team team : teams) {
            JLabel teamLabel = new JLabel("Team " + team.getName());

            spielerTab.add(teamLabel);

            // Spielernamen hinzufügen
            // Hinzufügen der Textfelder für die Spielernamen und Lösch-Buttons
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String spielerName = spielers.get(playerIndex).getName();
                JTextField spielerNameField = new JTextField(spielerName);

                spielerNameField.setColumns(20);

                deletePlayerButton = new DataButton("Löschen");
                deletePlayerButton.setData(spielerName);

                buttonNamen.add(deletePlayerButton);

                deletePlayerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DataButton sender = (DataButton) e.getSource();

                        deletePlayerByString(sender);
                        System.out.println(deletePlayerButton.getName());
                    }
                });

                deleteButtonundSpielernamen = new JPanel();
                deleteButtonundSpielernamen.add(spielerNameField);
                deleteButtonundSpielernamen.add(deletePlayerButton);

                spielerTab.add(deleteButtonundSpielernamen);
                playerNameFields.add(spielerNameField);
                playerIndex++;
            }
        }

        spielerTab.add(saveButtonPlayers);
        JScrollPane spielerTabScrollPane = new JScrollPane(spielerTab);
        tabbedPane.addTab("Spielername ändern", spielerTabScrollPane);

        teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.PAGE_AXIS));

        // Hinzufügen der Textfelder für die Teamnamen und Lösch-Buttons
        for (int i = 0; i < teams.size(); i++) {
            JTextField teamNameField = new JTextField(15);
            Team team1 = teams.get(i);

            //DataButton deleteTeamButton = new DataButton("Löschen");
            //deleteTeamButton.setData(team1.getName());

            //Zu jedem Button wird eine eindeutiger int mitgeschickt der dann für die Löschung der Teams zuständig ist
            /*deleteTeamButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DataButton sender = (DataButton) e.getSource();

                    deleteTeamByString(sender);
                }
            });*/
            teamNameField.setText(team1.getName());
            teamNameFields.add(teamNameField);
            JPanel deleteButtonundTeamnamen = new JPanel();
            deleteButtonundTeamnamen.add(teamNameField);
            //sadeleteButtonundTeamnamen.add(deleteTeamButton);

            teamPanel.add(deleteButtonundTeamnamen);




        }

        bearbeitenFenster.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Hier können Sie Ihre eigene Operation ausführen

                addTeamButton.setEnabled(true);
                addPlayerButton.setEnabled(true);

            }
        });

        JScrollPane teamTabScrollPane = new JScrollPane(teamPanel);
        tabbedPane.addTab("Teamname ändern", teamTabScrollPane);
        teamPanel.add(saveButtonTeams);

        bearbeitenFenster.add(tabbedPane);
        bearbeitenFenster.setVisible(true);




    }


    // Methode zum Aktualisieren von Spielernamen
    private void updatePlayerNames() {

        int playerIndex = 0;
        List <Spieler> spielers= List.of(Objects.requireNonNull(getSpieler()));
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);

            if(team == null) {
                continue;
            }

            for (int j = 0; j < team.getNumberOfPlayersPerTeam(); j++) {
                String newPlayerName = playerNameFields.get(playerIndex).getText();


                //buttonNamen
                if(!newPlayerName.equals(Objects.requireNonNull(spielers).get(playerIndex).getName())) {
                    Client.spielerAnpassen(spielers.get(playerIndex).getId(), newPlayerName);
                    for (int h = 0 ; h < buttonNamen.size() ; h++) {
                        if (!buttonNamen.get(playerIndex).getData().equals(newPlayerName)) {
                            buttonNamen.get(playerIndex).setData(newPlayerName);

                        }
                    }
                    System.out.println(spielers.get(playerIndex).getId());
                    team.setPlayerName(j, newPlayerName);

                    spielerTab.invalidate();
                    spielerTab.repaint();
                }




                playerIndex++;
            }
        }

    }

    // Methode zum Aktualisieren von Teamnamen
    private void updateTeamNames() {

        List <Team> teamsVonAPIAnfrage= List.of(Objects.requireNonNull(getTeams()));
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            String teamNameField = teamNameFields.get(i).getText();



            if(!teamNameField.equals(Objects.requireNonNull(teamsVonAPIAnfrage).get(i).getName())){
                Client.teamNamenAendern(teamsVonAPIAnfrage.get(i).getID(),teamNameField);
                team.setName(teamNameField);
                spielerTab.invalidate();
                spielerTab.repaint();

            }

        }

    }



    private void deletePlayerByString(DataButton btn) {
        int playerIndex = 0;
        String playerNameToBeRemoved = (String) btn.getData();
        List <Spieler> spielers= List.of(Objects.requireNonNull(getSpieler()));
        for (int j = 0; j < teams.size(); j++) {
            Team team = teams.get(j);
            List<String> playerNamesTeam1 = team.getPlayerNames();
            for (int i = 0; i < team.getNumberOfPlayersPerTeam(); i++) {
                String playerName = playerNamesTeam1.get(i);

                if (spielers.get(playerIndex).getName().equals(playerNameToBeRemoved)) {

                    if (team.players.size() > 1) {
                        // Spielerpanel löschen
                        spielerTab.remove(btn.getParent());
                        spielerTab.invalidate();
                        buttonNamen.remove(playerIndex);
                        team.players.remove(playerName);
                        deleteSpieler(spielers.get(playerIndex).getId());
                        playerNameFields.remove(playerIndex);
                        // Entferne das Panel des Spielers aus dem spielerPanel
                        spielerTab.repaint();
                        //spielerPanel.revalidate();

                        return;
                    } else {

                        System.out.println("Zu wenig Spieler im Team!!");
                    }
                }
                playerIndex++;

            }


        }


    }

    //TODO Löschen der teams
    private void deleteTeamByString(DataButton btn) {
        String teamNameToBeRemoved = (String) btn.getData();
        List<Spieler> spielers = List.of(Objects.requireNonNull(getSpieler()));
        for (int i = 0; i < teams.get(i).getNumberOfPlayersPerTeam(); i++) {
            for (int j = 0; j < spielers.size(); j++) {
                if (teamNameToBeRemoved.equals(spielers.get(i).getName())){
                    deleteSpieler(spielers.get(i).getId());

                }
            }

            deleteTeam(teams.get(i).getID());
            teamPanel.remove(btn.getParent());

        }

    }

    // Methode zum Initialisieren des ActionListeners zum Speichern von Teamnamen
    public void initActionListener() {
        saveButtonTeams.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeamNames();

                outputTextArea.append("Teamnamen gespeichert!\n");
            }
        });
    }


}