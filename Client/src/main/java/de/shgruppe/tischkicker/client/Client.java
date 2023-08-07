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
    public static List <Spiel> spiele;
    private static final String URL = "http://localhost:8080";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static void sendTeamsToServer() {

        try {

            String jsonData = gson.toJson(TeamApp.teams);


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
                System.out.println("API-Antwort:");
                System.out.println(responseBody);
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static HttpRequest.Builder createRequest(String ressource) {
        return HttpRequest.newBuilder()
                .uri(URI.create(URL + ressource))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public static void getSpieleFromServer() {

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

                // Spiele-Array parsen und in die Liste setzen

               spiele = gson.fromJson(responseBody, List.class);

            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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
    }
    }

