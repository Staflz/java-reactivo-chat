package com.example.chat.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.chat.config.ChatProperties;
import com.example.chat.model.Message;
import com.example.chat.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Implementation of ChatService using reactive Sinks for message broadcasting.
 * Provides in-memory message publishing and streaming.
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final Sinks.Many<Message> sink;
    private final Flux<Message> flux;
    private final MessageRepository messageRepository;
    private final AtomicLong messageCounter = new AtomicLong(0);

    public ChatServiceImpl(ChatProperties chatProperties, MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        int maxBufferSize = chatProperties.getMaxBufferSize();
        // Multicast: varios subscriptores; onBackpressureBuffer para evitar drop cuando hay sobrecarga
        this.sink = Sinks.many().multicast().onBackpressureBuffer(maxBufferSize);
        this.flux = sink.asFlux();
        log.info("ChatService initialized with max buffer size: {}", maxBufferSize);
    }

    @Override
    public void publish(Message message) {
        messageRepository.save(message);
        messageCounter.incrementAndGet();

        Sinks.EmitResult result = sink.tryEmitNext(message);
        if (result.isFailure()) {
            log.error("Failed to emit message from sender: {}, reason: {}", message.getSender(), result);
        } else {
            log.debug("Message published from sender: {}", message.getSender());
        }
    }

    @Override
    public Flux<Message> messages() {
        return flux;
    }
}