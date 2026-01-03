package com.wecode.bookit.exceptions;

public class RoomNotFound extends RuntimeException {
    public RoomNotFound(String message) {
        super(message);
    }
}
