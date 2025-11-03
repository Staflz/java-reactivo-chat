package com.example.chat.mapper;

import java.time.LocalDateTime;

import com.example.chat.dto.MessageRequest;
import com.example.chat.dto.MessageResponse;
import com.example.chat.model.Message;

/**
 * Mapper class for converting between Message entities and DTOs.
 * Provides static methods for bidirectional conversions.
 */
public class MessageMapper {

    /**
     * Converts a MessageRequest to a Message entity.
     * Sets the current timestamp.
     *
     * @param request the incoming message request
     * @return the created Message entity
     */
    public static Message toEntity(MessageRequest request) {
        return Message.builder()
                .sender(request.sender())
                .content(request.content())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Converts a Message entity to a MessageResponse.
     *
     * @param message the message entity
     * @return the message response DTO
     */
    public static MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getSender(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}