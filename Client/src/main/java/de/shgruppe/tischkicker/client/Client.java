package de.shgruppe.tischkicker.client;
import com.google.gson.Gson;
import tischkicker.models.Spiel;

import javax.swing.*;
import java.net.URISyntaxException;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client {
    public static List<Spiel> spiele;

    private static final String URL = "http://localhost:8080";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static Team sendTeamsToServer(Team team) {
        try {
            String jsonData = gson.toJson(team);

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/teams")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();

                return gson.fromJson(responseBody, Team.class);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpRequest.Builder createRequest(String ressource) {
        return HttpRequest.newBuilder()
                .uri(URI.create(URL + ressource))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public enum Modus {
        increment, decrement,
    }

    public static void spielstandAnpassen(int id, Modus modus) throws IOException, InterruptedException {
        HttpRequest request = createRequest("/spiel/" + modus + "/" + id)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            System.out.println(modus + " ist für " + id + " schiefgegangen.");
        }
    }


    public static void aufgeben(int id) throws IOException, InterruptedException {
        HttpRequest request = createRequest("/spiel/aufgeben" + id)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            System.out.println("Aufgeben ist für " + id + " schiefgegangen.");
        }
    }


    public static void deleteTeam(Team team) {
        try {
            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/teams/" + team.ID)
                    .DELETE()
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                Team teams = gson.fromJson(responseBody, Team.class);
                TeamApp.teams.add(teams);
                System.out.println(responseBody);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static Spiel[] startTurnier() {
        try {
            HttpRequest request = createRequest("/turnier")
                    .GET()
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = Client.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:"+responseBody);

                return Client.gson.fromJson(responseBody, Spiel[].class);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Team[] getTeams() {
        try {
            HttpRequest request = createRequest("/teams")
                    .GET()
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = Client.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                return Client.gson.fromJson(responseBody, Team[].class);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void spielStarten (Spiel spiel)
    {
        try {
            String jsonData = gson.toJson(spiel);

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/spiel/start/"+spiel.getSpielID())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void spielAufgeben (Spiel spiel)
    {
        try {
        String jsonData = gson.toJson(spiel);

        // HTTP-POST-Anfrage erstellen
        HttpRequest request = createRequest("/spiel/aufgeben/"+spiel.getSpielID())
                .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                .build();

        // Die Anfrage an die API senden und die Antwort erhalten
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            // Die JSON-Antwort verarbeiten
            String responseBody = response.body();
        } else {
            System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
        }
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}


    public static Spiel[] getSpieleFromServer() {

        try {
            HttpRequest request = createRequest("/spiele")

                    .GET()
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten

                String responseBody = response.body();
                System.out.println("API-Antwort:");
                System.out.println(responseBody);

                return gson.fromJson(responseBody, Spiel[].class);

            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AktuellerSpielstand spielstandAnzeige = new AktuellerSpielstand(500,500);

    public static void main(String[] args) {
        try {
            URI serverURI = new URI("ws://localhost:8080/live");
            Websocket client = new Websocket(serverURI);
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
                public void run() {
                new TeamApp().setVisible(true);
            }
        });


        //in Bearbeitung
        //Siegertreppchen sieger = new Siegertreppchen(f, 1400, 900, 150, 100, 28);
    }

    public static void turnierbaumGenerieren(Spiel[] spiele) {
        Team[] teams = getTeams();

        if (teams != null) {
            TurnierBaum t = new TurnierBaum();
            t.tunierbaumErstellen(teams.length);
            for (int i = 0 ; i < spiele.length ; i++)
            {
                t.spielfeldFuellen(spiele[i],0,i);
            }
        } else {
            // TODO Fehlermeldung ausgeben, falls keine Teams vorhanden sind (null), zum Beispiel mit Exception werfen
        }
    }
}

