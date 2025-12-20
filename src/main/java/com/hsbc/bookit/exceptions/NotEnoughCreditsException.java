package com.hsbc.bookit.exceptions;

public class NotEnoughCreditsException extends RuntimeException{
    public NotEnoughCreditsException(String message) {
        super(message);
    }
}
