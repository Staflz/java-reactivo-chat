package com.example.chat.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * Domain model representing a chat message.
 * Contains the sender, content, and timestamp of the message.
 */
@Data
@Builder
public class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
