package com.example.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the chat application.
 * Binds properties from application.properties with prefix "chat".
 */
@Component
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {
    private int maxBufferSize = ChatConstants.DEFAULT_MAX_BUFFER_SIZE;

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }
}