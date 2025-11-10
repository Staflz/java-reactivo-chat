package com.example.chat.component;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.example.chat.service.ChatService;
import com.example.chat.config.WebsocketConfig;
import com.example.chat.handler.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket service verification component.
 * Tests that all WebSocket-related beans are properly initialized.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketServiceChecker implements CommandLineRunner {

    private final ChatService chatService;
    private final WebsocketConfig websocketConfig;
    private final ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void run(String... args) {
        try {
            log.info("🔍 Checking WebSocket service initialization...");
            
            // Test 1: Verify ChatService
            var messageFlux = chatService.messages();
            if (messageFlux == null) {
                log.error("❌ ChatService.messages() returned null");
                return;
            }
            log.info("✅ ChatService is properly initialized");
            
            // Test 2: Verify WebSocketConfig
            if (websocketConfig == null) {
                log.error("❌ WebSocketConfig is null");
                return;
            }
            log.info("✅ WebSocketConfig is properly initialized");
            
            // Test 3: Verify ChatWebSocketHandler
            if (chatWebSocketHandler == null) {
                log.error("❌ ChatWebSocketHandler is null");
                return;
            }
            log.info("✅ ChatWebSocketHandler is properly initialized");
            
            log.info("🎉 All WebSocket services verified and ready!");
            
        } catch (Exception e) {
            log.error("❌ WebSocket service verification failed: {}", e.getMessage(), e);
        }
    }
}