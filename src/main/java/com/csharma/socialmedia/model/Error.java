package com.csharma.socialmedia.model;

public class Error {

    private int code;
    private String responseStatus;
    private String message;


    public Error(final int code, final String responseStatus, final String message) {
        this.code = code;
        this.responseStatus = responseStatus;
        this.message = message;
    }

    @SuppressWarnings("unused")
    public Error() {
    }

    public int getCode() {
        return code;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public String getMessage() {
        return message;
    }

}
