package com.volodymyr.studying.exceptions;

public class UserAlreadyExistException extends IllegalArgumentException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
