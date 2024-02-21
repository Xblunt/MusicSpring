package dev.MusicSpring.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.*;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketService  implements  ApplicationListener<ApplicationEvent> {

@Override
public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof SessionConnectedEvent) {
        System.out.println("WebSocket соединение установлено");
        } else if (event instanceof SessionDisconnectEvent) {
        System.out.println("WebSocket соединение закрыто");
        }
        }

}
