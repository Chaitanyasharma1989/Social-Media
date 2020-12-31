package com.csharma.socialmedia.exception;

public class ServiceException extends RuntimeException {

    private final String message;

    public ServiceException(final String message) {
        super();
        this.message = message;
    }
}
