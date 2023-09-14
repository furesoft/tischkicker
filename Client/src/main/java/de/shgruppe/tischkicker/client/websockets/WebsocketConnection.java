package de.shgruppe.tischkicker.client.websockets;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketConnection {
    private final Websocket client;
    int delay;

    public WebsocketConnection() throws URISyntaxException {
        client = new Websocket(new URI("ws://localhost:8080/live"));
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void connect() throws InterruptedException {
        while (!client.isOpen()) {
            try {
                client.connect();
            } catch (Exception ex) {
                Thread.sleep(delay);
            }
        }
    }
}
