package com.csharma.socialmedia.exception;

public class ServiceException extends Exception {

    private final String message;

    public ServiceException(final String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
