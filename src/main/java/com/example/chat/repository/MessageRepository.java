package com.example.chat.repository;

import java.util.List;

import com.example.chat.model.Message;

/**
 * Repository interface for message operations.
 * Provides in-memory storage for messages.
 */
public interface MessageRepository {

    /**
     * Saves a message to the repository.
     *
     * @param message the message to save
     */
    void save(Message message);

    /**
     * Retrieves all messages from the repository.
     *
     * @return a list of all messages
     */
    List<Message> findAll();

    /**
     * Retrieves the last N messages from the repository.
     *
     * @param limit the maximum number of messages to retrieve
     * @return a list of the most recent messages
     */
    List<Message> findLast(int limit);
}