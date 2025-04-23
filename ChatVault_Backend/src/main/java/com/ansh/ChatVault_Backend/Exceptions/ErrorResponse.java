package com.ansh.ChatVault_Backend.Exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    int status;
    String message;
    LocalDateTime time;

    public ErrorResponse(int status, String message, LocalDateTime time) {
        this.status = status;
        this.message = message;
        this.time = time;
    }
}
