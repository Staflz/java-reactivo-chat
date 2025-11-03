package com.example.chat.dto;

/**
 * Data Transfer Object for incoming message requests.
 * Contains validation constraints for sender and content fields.
 */
public record MessageRequest(
    @jakarta.validation.constraints.NotBlank(message = "Sender cannot be blank")
    String sender,

    @jakarta.validation.constraints.NotBlank(message = "Content cannot be blank")
    String content
) {}