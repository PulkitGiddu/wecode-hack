package com.wecode.bookit.exceptions;

public class NotEnoughCreditsException extends RuntimeException{
    public NotEnoughCreditsException(String message) {
        super(message);
    }
}
