package com.example.chat.exception;

/**
 * Data Transfer Object for error responses.
 * Contains error details returned to clients.
 */
public record ErrorResponse(
    int status,
    String message,
    String details
) {}