package com.lcsserver.dto;

public class LcsErrorResponse {
    private String message;

    public LcsErrorResponse() {
    }

    public LcsErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public LcsErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "LcsErrorResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
