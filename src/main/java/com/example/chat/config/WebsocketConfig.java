package com.example.chat.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.example.chat.handler.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebsocketConfig {

    private final ChatWebSocketHandler chatWebSocketHandler;

    @Bean
    public HandlerMapping webSocketMapping() {
        return new SimpleUrlHandlerMapping(Map.of("/ws/chat", chatWebSocketHandler), -1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}


