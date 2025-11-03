package com.example.chat.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.config.ChatConstants;
import com.example.chat.dto.MessageRequest;
import com.example.chat.dto.MessageResponse;
import com.example.chat.mapper.MessageMapper;
import com.example.chat.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * REST Controller for handling chat operations.
 * Provides endpoints for sending messages and streaming chat messages.
 */
@RestController
@RequestMapping(ChatConstants.CHAT_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    /**
     * Sends a new message to the chat.
     *
     * @param request the message request containing sender and content
     */
    @PostMapping(ChatConstants.SEND_MESSAGE_PATH)
    public void send(@Valid @RequestBody MessageRequest request) {
        log.info("Received message from sender: {}", request.sender());
        chatService.publish(MessageMapper.toEntity(request));
    }

    /**
     * Streams chat messages in real-time using Server-Sent Events.
     *
     * @return a Flux of message responses
     */
    @GetMapping(value = ChatConstants.STREAM_MESSAGES_PATH, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageResponse> stream() {
        log.info("Client connected to message stream");
        return chatService.messages()
                .map(MessageMapper::toResponse);
    }

    /**
     * Health check endpoint for testing connectivity.
     * Useful for Postman demonstrations.
     *
     * @return a simple health status message
     */
    @GetMapping("/health")
    public String health() {
        return "Chat application is running and reactive!";
    }
}
