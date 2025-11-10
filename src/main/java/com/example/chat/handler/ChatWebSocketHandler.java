package com.example.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.example.chat.dto.MessageRequest;
import com.example.chat.mapper.MessageMapper;
import com.example.chat.service.ChatService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        log.info("New WebSocket connection established: {}", session.getId());
        
        return session.send(
            session.receive()
                .doOnNext(message -> log.debug("Received WebSocket message: {}", message.getPayloadAsText()))
                .map(WebSocketMessage::getPayloadAsText)
                .map(this::handleIncomingMessage)
                .doOnNext(outgoingMessage -> {
                    // Extract sender and content from the message and publish via reactive service
                    if (outgoingMessage != null && outgoingMessage.has("sender") && outgoingMessage.has("content")) {
                        MessageRequest request = new MessageRequest(
                            outgoingMessage.get("sender").asText(),
                            outgoingMessage.get("content").asText()
                        );
                        chatService.publish(MessageMapper.toEntity(request));
                    }
                })
                .map(message -> {
                    if (message != null) {
                        try {
                            // Echo the message back to the sender
                            return session.textMessage(objectMapper.writeValueAsString(message));
                        } catch (Exception e) {
                            log.error("Error serializing message", e);
                            return session.textMessage("{\"error\":\"Message serialization failed\"}");
                        }
                    } else {
                        return session.textMessage("{\"error\":\"Invalid message format\"}");
                    }
                })
        ).doOnTerminate(() -> log.info("WebSocket connection closed: {}", session.getId()))
         .doOnError(error -> log.error("WebSocket error for session {}: {}", session.getId(), error.getMessage()));
    }

    /**
     * Handles incoming WebSocket messages.
     *
     * @param message the incoming WebSocket message payload as String
     * @return the processed message as JsonNode
     */
    private JsonNode handleIncomingMessage(String messagePayload) {
        try {
            // Parse the incoming message
            JsonNode jsonNode = objectMapper.readTree(messagePayload);
            
            // Validate required fields
            if (!jsonNode.has("sender") || !jsonNode.has("content")) {
                log.warn("Invalid message format: missing sender or content");
                return createErrorResponse("Invalid message format");
            }
            
            log.debug("Processed message from sender: {}", jsonNode.get("sender").asText());
            return jsonNode;
            
        } catch (Exception e) {
            log.error("Error parsing JSON message", e);
            return createErrorResponse("Invalid JSON format");
        }
    }

    /**
     * Creates an error response JsonNode.
     *
     * @param errorMessage the error message
     * @return the error response as JsonNode
     */
    private JsonNode createErrorResponse(String errorMessage) {
        try {
            String errorJson = String.format("{\"error\":\"%s\"}", errorMessage);
            return objectMapper.readTree(errorJson);
        } catch (Exception ex) {
            log.error("Error creating error response", ex);
            // Return a simple hardcoded error object
            return objectMapper.createObjectNode().put("error", "Internal error");
        }
    }
}


