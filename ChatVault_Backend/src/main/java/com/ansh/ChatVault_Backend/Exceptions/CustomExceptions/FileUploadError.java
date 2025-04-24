package com.ansh.ChatVault_Backend.Exceptions.CustomExceptions;

public class FileUploadError extends RuntimeException {
    public FileUploadError(String message, Throwable e) {
        super(message,e);
    }
}
