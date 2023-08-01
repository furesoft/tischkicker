package de.shgruppe.tischkicker.client;
import com.google.gson.Gson;
import tischkicker.models.Spiel;

import javax.swing.*;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {
        String apiUrl = "http://localhost:8080/teams";
        String apiUrl2 = "http://localhost:8080/spiele";
        try {

            // Gson-Objekt zum Serialisieren von JSON erstellen
            Gson gson = new Gson();
            String jsonData = gson.toJson(TeamManager.getTeamManagers());

            // HTTP-Client erstellen
            HttpClient httpClient = HttpClient.newHttpClient();

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            //HTTP-GET-Anfrage erstellen
            HttpRequest requestSpieler = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl2))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build();



            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseSpieler = httpClient.send(requestSpieler, HttpResponse.BodyHandlers.ofString());


            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody2 = responseSpieler.body();
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                System.out.println(responseBody);

                Spiel[] spielArray = gson.fromJson(responseBody2, Spiel[].class);
                List<Spiel> spiele = Arrays.asList(spielArray);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            URI serverURI = new URI("http://localhost:8080/live");
            ClientManager client = new ClientManager(serverURI);
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
    }
    }

