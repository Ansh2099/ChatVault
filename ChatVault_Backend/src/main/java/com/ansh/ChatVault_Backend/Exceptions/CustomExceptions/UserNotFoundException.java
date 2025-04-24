package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){super(message);}
}
