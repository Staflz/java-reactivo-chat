package com.example.chat.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for outgoing message responses.
 * Contains the message details sent to clients.
 */
public record MessageResponse(
    String sender,
    String content,
    LocalDateTime timestamp
) {}