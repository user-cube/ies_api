package com.ies.smartroom.api.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class SocketClient {

    private WebSocketClient client;

    public SocketClient(){
        client = new ReactorNettyWebSocketClient();
    }

    public void send(String message){
        System.out.println("Send");
        URI url = null;
        try {
            url = new URI("ws://localhost:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.execute(url, session->
                session.send(
                        Mono.just(session.textMessage(message))
                )
        );
    }
}
