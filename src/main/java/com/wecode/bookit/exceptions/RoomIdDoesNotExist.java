package com.wecode.bookit.exceptions;

public class RoomIdDoesNotExist extends RuntimeException {
    public RoomIdDoesNotExist(String message) {
        super(message);
    }
}
