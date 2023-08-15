package de.shgruppe.tischkicker_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

     static ArrayList<WebSocketSession> sessions = new ArrayList();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        for(WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }


    public  static void broadcast(Object message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        for(WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
