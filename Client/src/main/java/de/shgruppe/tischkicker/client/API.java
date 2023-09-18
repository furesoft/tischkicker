package de.shgruppe.tischkicker.client;

import com.google.gson.Gson;
import de.shgruppe.tischkicker.client.fenster.TeamsInitialisierenFenster;
import tischkicker.models.Spiel;
import tischkicker.models.Spieler;
import tischkicker.models.Turnier;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class API {
    private static final String URL = "http://localhost:8080";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static void spielerAnpassen(int spielerId, String neuerName) {
        try {
            String jsonData = gson.toJson(neuerName);

            // HTTP-PUT-Anfrage erstellen
            HttpRequest request = createRequest("/spieler/" + spielerId).PUT(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                                                                        .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                System.out.println("Spielername erfolgreich aktualisiert.");
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void teamNamenAendern(int teamID, String neuerName) {
        try {
            String jsonData = gson.toJson(neuerName);

            // HTTP-PUT-Anfrage erstellen
            HttpRequest request = createRequest("/teams/" + teamID).PUT(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                                                                   .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                System.out.println("Spielername erfolgreich aktualisiert.");
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Team sendTeamsToServer(Team team) {
        try {
            String jsonData = gson.toJson(team);

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/teams").POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                                                         .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();

                return gson.fromJson(responseBody, Team.class);
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpRequest.Builder createRequest(String ressource) {
        return HttpRequest.newBuilder().uri(URI.create(URL + ressource)).header("Content-Type", "application/json")
                          .header("Accept", "application/json");
    }

    public static void spielstandAnpassen(int id, Modus modus) throws IOException, InterruptedException {
        HttpRequest request = createRequest("/spiel/" + modus + "/" + id).POST(HttpRequest.BodyPublishers.ofString(""))
                                                                         .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println(modus + " ist für " + id + " schiefgegangen.");
        }
    }

    public static void seitenwechsel() throws IOException, InterruptedException {
        HttpRequest request = createRequest("/spiel/seitenwechsel").POST(HttpRequest.BodyPublishers.ofString(""))
                                                                   .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Seitenwechsel ist schief gegangen");
        }
    }

    public static void aufgeben(int id) throws IOException, InterruptedException {
        HttpRequest request = createRequest("/spiel/aufgeben/" + id).POST(HttpRequest.BodyPublishers.ofString(""))
                                                                    .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Aufgeben ist für " + id + " schiefgegangen.");
        }
    }

    public static void deleteTeam(int id) {
        try {
            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/teams/" + id).DELETE().build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                Team teams = gson.fromJson(responseBody, Team.class);
                TeamsInitialisierenFenster.teams.add(teams);
                System.out.println(responseBody);
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void deleteSpieler(int spielerID) {
        try {
            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/spieler/" + spielerID).DELETE().build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                Team teams = gson.fromJson(responseBody, Team.class);
                TeamsInitialisierenFenster.teams.add(teams);
                System.out.println(responseBody);
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static Turnier erstelleTurnier() {
        return getResource("/turniererstellen", Turnier.class);
    }

    public static Spiel[] startTurnier(int id) {
        return getResource("/turnier"+id, Spiel[].class);
    }

    public static Spieler[] getSpieler() {
        return getResource("/spieler", Spieler[].class);
    }

    public static tischkicker.models.Team getTeam(int id) {
        return getResource("/teams/" + id, tischkicker.models.Team.class);
    }

    public static Team[] getTeams() {
        return getResource("/teams", Team[].class);
    }

    public static Spiel getSpiel(int spieleID) {
        return getResource("/spiele/" + spieleID, Spiel.class);
    }

    public static Turnier getTurnier(int turnierID) {
        return getResource("/turniere" + turnierID, Turnier.class);
    }

    public static Turnier[] getTurniere() {
        return getResource("/turniere", Turnier[].class);
    }

    public static <T> T getResource(String path, Class<T> clazz) {

        try {
            HttpRequest request = createRequest(path).GET().build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = API.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
                System.out.println("API-Antwort:");
                return API.gson.fromJson(responseBody, clazz);
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void spielStarten(Spiel spiel) {
        try {
            String jsonData = gson.toJson(spiel);

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/spiel/start/" + spiel.getSpielID()).POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                                                                                     .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void spielAufgeben(Spiel spiel) {
        try {
            String jsonData = gson.toJson(spiel);

            // HTTP-POST-Anfrage erstellen
            HttpRequest request = createRequest("/spiel/aufgeben/" + spiel.getSpielID()).POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                                                                                        .build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten
                String responseBody = response.body();
            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Spiel[] getSpieleFromServer() {

        try {
            HttpRequest request = createRequest("/spiele")

                    .GET().build();

            // Die Anfrage an die API senden und die Antwort erhalten
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Die JSON-Antwort verarbeiten

                String responseBody = response.body();
                System.out.println("API-Antwort:");
                System.out.println(responseBody);

                return gson.fromJson(responseBody, Spiel[].class);

            }
            else {
                System.out.println("Fehler bei der API-Anfrage. Response Code: " + statusCode + " " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void turnierbaumGenerieren(Spiel[] spiele) {
        Team[] teams = getTeams();
        if (teams != null) {
            double anzahlTeams = teams.length;
            int spielfelderAnzahl = (int) Math.round(anzahlTeams / 2);
            App.turnierbaum.tunierbaumErstellen(teams.length, Arrays.asList(spiele));

            for (int i = 0; i < spielfelderAnzahl; i++) {
                // Problem, falls Turnier gestartet wird, obwohl schon Spiele vorhanden sind
                if (spiele[i].getQualifikation() == 1) {
                    App.turnierbaum.spielfeldFuellen(spiele[i], 0, i);
                }
            }

            App.turnierbaum.frame.setVisible(true);
        }
        else {
            // TODO Fehlermeldung ausgeben, falls keine Teams vorhanden sind (null), zum Beispiel mit Exception werfen
        }
    }

    public enum Modus {
        increment, decrement,
    }
}

