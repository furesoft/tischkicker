package de.shgruppe.tischkicker.client.websockets;

import com.google.gson.Gson;
import de.shgruppe.tischkicker.client.API;
import de.shgruppe.tischkicker.client.App;
import de.shgruppe.tischkicker.client.fenster.Siegertreppchen;
import de.shgruppe.tischkicker.client.ui.Spielfeld;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import tischkicker.messages.*;
import tischkicker.models.Spiel;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;


class Websocket extends WebSocketClient {
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

            App.spielstandAnzeige.aktualisiereDaten(spielergebnis);

            System.out.printf(String.valueOf(Thread.currentThread().getId()));

            //Falls Quickspiel
            if(spielergebnis.spiel.getSpielID()!=1) {
                App.turnierbaum.ergebnisUebertragen(spielergebnis);
            }
        }

        else if (deserializedMessage.type == MessageType.SiegerTreppchen) {
            SiegerTreppchenMessage treppchenMessage = gson.fromJson(message, SiegerTreppchenMessage.class);

            Siegertreppchen treppchen = null;
            try {
                treppchen = new Siegertreppchen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            treppchen.setTeams(treppchenMessage.teams);
            treppchen.setVisible(true);
        }


        else if (deserializedMessage.type == MessageType.SpielBeendet) {
            SpielBeendetMessage spielergebnis = gson.fromJson(message, SpielBeendetMessage.class);
            App.spielstandAnzeige.hide();

            Spielfeld f = App.turnierbaum.getNaechstesSpielfeld();
            if (f != null) {
                f.spiel = spielergebnis.getNeuesSpiel();
                App.turnierbaum.feldInitialisieren(f.spiel, spielergebnis.getGewinner());
            }

            //Für den Fall QuickPlay
            if(spielergebnis.getSpiel().getSpielID()!=1) {
                holeAlleSpieleVomServerUndAktualisiereTeamnamenDerGUI();

                App.turnierbaum.setGewinner(spielergebnis.getGewinner(), spielergebnis.getSpiel());
                App.turnierbaum.block();
            }
        }
    }

    private void holeAlleSpieleVomServerUndAktualisiereTeamnamenDerGUI() {
        List<Spiel> alleSpieleMitTeamnamen = Arrays.asList(API.getSpieleFromServer());

        App.turnierbaum.updateTeamnames(alleSpieleMitTeamnamen);
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
