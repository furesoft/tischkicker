package de.shgruppe.tischkicker_server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tischkicker.models.Tor;

import javax.annotation.processing.Messager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {

     static ArrayList<WebSocketSession> sessions = new ArrayList();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        for(WebSocketSession webSocketSession : sessions) {
            //Map value = new ().fromJson(message.getPayload(), Map.class);
            webSocketSession.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }


    public  static void broadcast(Object message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for(WebSocketSession webSocketSession : sessions) {
            //Map value = new ().fromJson(message.getPayload(), Map.class);
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }

    }


}
