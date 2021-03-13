package com.antra.report.client.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws")
@Component
public class WebSocketController {
    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        WebSocketController.session = session;
    }

//    @OnMessage
//    public void onMessage(String message) throws IOException {}

    public void sendMessage(String message) throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose() {
    }

}
