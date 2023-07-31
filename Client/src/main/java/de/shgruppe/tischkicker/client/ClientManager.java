package de.shgruppe.tischkicker.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientManager extends WebSocketClient {
    public ClientManager(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WebSocket connection opened");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            URI serverURI = new URI("http://localhost:8080/live");
            ClientManager client = new ClientManager(serverURI);
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
