package de.shgruppe.tischkicker.client;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Client {
    TeamManager zugriffAufTeams;
    public static void main(String[] args) {
        String apiUrl = "https://api.example.com/data"; // Hier die API-URL einsetzen

        try {


            // Gson-Objekt zum Serialisieren von JSON erstellen
            Gson gson = new Gson();
        //    String jsonData = gson.toJson(zugriffAufTeams.getTeamManager);

            // URL-Verbindung aufbauen
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // JSON-Daten senden
            OutputStream outputStream = connection.getOutputStream();
           // outputStream.write(jsonData.getBytes());
            outputStream.flush();

            // Die Antwort lesen
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Die JSON-Antwort verarbeiten
                System.out.println("API-Antwort:");
                System.out.println(response.toString());
            } else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

