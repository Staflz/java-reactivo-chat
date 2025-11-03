package com.example.chat.repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.chat.model.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * In-memory implementation of MessageRepository.
 * Uses a thread-safe list to store messages.
 */
@Repository
@Slf4j
public class InMemoryMessageRepository implements MessageRepository {

    private final List<Message> messages = new CopyOnWriteArrayList<>();

    @Override
    public void save(Message message) {
        messages.add(message);
        log.debug("Message saved from sender: {}", message.getSender());
    }

    @Override
    public List<Message> findAll() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public List<Message> findLast(int limit) {
        int size = messages.size();
        int startIndex = Math.max(0, size - limit);
        return Collections.unmodifiableList(messages.subList(startIndex, size));
    }
}