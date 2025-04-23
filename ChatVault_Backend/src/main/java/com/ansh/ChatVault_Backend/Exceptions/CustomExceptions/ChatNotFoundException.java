package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String message) {
        super(message);
    }
}
