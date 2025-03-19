package com.ansh.ChatVault_Backend.Mappers;

import com.ansh.ChatVault_Backend.Constants.MessageType;
import com.ansh.ChatVault_Backend.Model.Message;
import com.ansh.ChatVault_Backend.Responses.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        MessageResponse.MessageResponseBuilder builder = MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                .media(message.getMediaFilePath());

        // Set default dimensions for images
        if (message.getType() == MessageType.IMAGE) {
            builder.mediaWidth(800)
                   .mediaHeight(800);
        }

        return builder.build();
    }
}
