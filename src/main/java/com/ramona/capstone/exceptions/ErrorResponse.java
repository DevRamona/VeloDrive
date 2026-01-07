package com.ramona.capstone.exceptions;

public record ErrorResponse(
        int status,
        String message,
        long timestamp
){}
