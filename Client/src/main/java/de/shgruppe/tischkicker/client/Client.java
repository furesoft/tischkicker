package de.shgruppe.tischkicker.client;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {
        String apiUrl = "http://localhost:8080/teams"; // Hier die API-URL einsetzen

        try {

            // Gson-Objekt zum Serialisieren von JSON erstellen
            Gson gson = new Gson();
            String jsonData = gson.toJson(TeamManager.getTeamManagers());

            // HTTP-Client erstellen
            HttpClient httpClient = HttpClient.newHttpClient();

            // HTTP-POST-Anfrage erstellen und konfigurieren
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Den HTTP-Statuscode der Antwort überprüfen
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


    }

