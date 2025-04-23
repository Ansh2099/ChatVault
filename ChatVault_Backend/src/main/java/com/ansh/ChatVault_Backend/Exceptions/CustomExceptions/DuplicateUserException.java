package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
