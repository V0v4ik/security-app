package com.volodymyr.studying.exceptions;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {

    public NoSuchUserException(String message) {
        super(message);
    }
}
