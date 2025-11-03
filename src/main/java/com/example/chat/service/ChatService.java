package com.example.chat.service;

import com.example.chat.model.Message;

import reactor.core.publisher.Flux;

/**
 * Service interface for chat operations.
 * Provides methods to publish messages and stream them reactively.
 */
public interface ChatService {

    /**
     * Publishes a message to the chat.
     *
     * @param message the message to publish
     */
    void publish(Message message);

    /**
     * Returns a Flux of messages for streaming.
     *
     * @return a Flux of Message entities
     */
    Flux<Message> messages();
}
