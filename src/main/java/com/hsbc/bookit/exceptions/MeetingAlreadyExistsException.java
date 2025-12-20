package com.hsbc.bookit.exceptions;

public class MeetingAlreadyExistsException extends RuntimeException{
    public MeetingAlreadyExistsException(String message) {
        super(message);
    }
}
