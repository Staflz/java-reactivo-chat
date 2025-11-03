package com.example.chat.config;

/**
 * Constants used throughout the chat application.
 * Contains route paths, error messages, and configuration keys.
 */
public final class ChatConstants {

    // Route paths
    public static final String CHAT_BASE_PATH = "/chat";
    public static final String SEND_MESSAGE_PATH = "/send";
    public static final String STREAM_MESSAGES_PATH = "/stream";

    // Error messages
    public static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
    public static final String REQUEST_BINDING_FAILED_MESSAGE = "Request binding failed";
    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred";

    // Configuration properties
    public static final String CHAT_MAX_BUFFER_SIZE_KEY = "chat.max-buffer-size";
    public static final int DEFAULT_MAX_BUFFER_SIZE = 1000;

    private ChatConstants() {
        // Utility class
    }
}