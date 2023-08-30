package de.shgruppe.tischkicker.client;

import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import tischkicker.messages.Message;
import tischkicker.messages.MessageType;
import tischkicker.messages.SpielBeendetMessage;
import tischkicker.messages.SpielErgebnis;
import tischkicker.models.Spiel;
import tischkicker.models.Team;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


public class Websocket extends WebSocketClient {
    public Websocket(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        System.out.println("WebSocket connection opened");
    }

    @Override
    public void onMessage(String message) {
        Gson gson = new Gson();

        Message deserializedMessage = gson.fromJson(message, Message.class);

        if (deserializedMessage.type == MessageType.SpielErgebnis) {
            SpielErgebnis spielergebnis = gson.fromJson(message, SpielErgebnis.class);
            System.out.println("Received message: " + message);

            Client.spielstandAnzeige.aktualisiereDaten(spielergebnis);

            System.out.printf(String.valueOf(Thread.currentThread().getId()));
            Client.turnierbaum.ergebnisUebertragen(spielergebnis);
        }
        else if (deserializedMessage.type == MessageType.Phasenaenderung) {
            //ToDo: implementiere spiel in nächster phase anzeigen
        }
        else if (deserializedMessage.type == MessageType.SpielBeendet) {
            SpielBeendetMessage spielergebnis = gson.fromJson(message, SpielBeendetMessage.class);
            Client.spielstandAnzeige.hide();
            Spielfeld f = Client.turnierbaum.getNaechstesSpielfeld();
            if (f != null) {
                f.spiel = spielergebnis.getNeuesSpiel();
                Client.turnierbaum.feldInitialisieren(f.spiel, spielergebnis.getGewinner());
            }

            holeAlleSpieleVomServerUndAktualisiereTeamnamenDerGUI();

            Client.gewinner.show(spielergebnis.getGewinner().getName());
            Client.turnierbaum.setGewinner(spielergebnis.getGewinner(), spielergebnis.getSpiel());
            Client.turnierbaum.lock();

        }

    }

    private void holeAlleSpieleVomServerUndAktualisiereTeamnamenDerGUI() {
        // Alle Spiele vom Server holen
        List<Spiel> alleSpieleMitTeamnamen = Arrays.asList(Client.getSpieleFromServer());

        // Alle Teamnamen aktualisieren.
        Client.turnierbaum.updateTeamnames(alleSpieleMitTeamnamen);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

}
