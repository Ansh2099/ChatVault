package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class UnauthorizedAccess extends RuntimeException {
    public UnauthorizedAccess(String message) {
        super(message);
    }
}
