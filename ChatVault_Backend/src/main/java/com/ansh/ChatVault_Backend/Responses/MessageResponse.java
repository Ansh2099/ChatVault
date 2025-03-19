package com.ansh.ChatVault_Backend.Responses;

import com.ansh.ChatVault_Backend.Constants.MessageState;
import com.ansh.ChatVault_Backend.Constants.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private Long id;
    private String content;
    private MessageType type;
    private MessageState state;
    private String senderId;
    private String receiverId;
    private LocalDateTime createdAt;
    private String media;
    private Integer mediaWidth;
    private Integer mediaHeight;
}
