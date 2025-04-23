package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException(String message){
        super(message);
    }
}
