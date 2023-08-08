package de.shgruppe.tischkicker.client;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import tischkicker.models.SpielErgebnis;


import java.net.URI;


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
        SpielErgebnis spielergebnis = gson.fromJson(message, SpielErgebnis.class);
        System.out.println("Received message: " + message);
        gesamtTore neu = new gesamtTore();

        neu.empfangeTor(spielergebnis);
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
