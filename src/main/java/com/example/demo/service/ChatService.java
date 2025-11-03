package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service

public class ChatService {
    private final Sinks.Many<Message> sink;
    private final Flux<Message> flux;

    public ChatService() {
        // Multicast: varios subscriptores; onBackpressureBuffer para evitar drop cuando hay sobrecarga
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = sink.asFlux();
    }

    public void publish(Message message) {
        sink.tryEmitNext(message);
    }

    public Flux<Message> messages() {
        return flux;
    }
}
