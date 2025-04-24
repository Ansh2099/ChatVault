package com.ansh.ChatVault_Backend.Service;

import com.ansh.ChatVault_Backend.Exceptions.CustomExceptions.UserNotFoundException;
import com.ansh.ChatVault_Backend.Mappers.ChatMapper;
import com.ansh.ChatVault_Backend.Model.Chat;
import com.ansh.ChatVault_Backend.Model.User;
import com.ansh.ChatVault_Backend.Repositories.ChatRepository;
import com.ansh.ChatVault_Backend.Repositories.UserRepository;
import com.ansh.ChatVault_Backend.Responses.ChatResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper mapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(c -> mapper.toChatResponse(c, userId))
                .toList();
    }

    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = chatRepository.findChatByReceiverAndSender(senderId, receiverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() ->  new UserNotFoundException("User with id " + senderId + " not found"));
        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() ->  new UserNotFoundException("User with id " + receiverId + " not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        chat.setCreatedDate(LocalDateTime.now());

        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }
}
