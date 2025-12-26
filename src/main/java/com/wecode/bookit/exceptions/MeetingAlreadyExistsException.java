package com.wecode.bookit.exceptions;

public class MeetingAlreadyExistsException extends RuntimeException{
    public MeetingAlreadyExistsException(String message) {
        super(message);
    }
}
