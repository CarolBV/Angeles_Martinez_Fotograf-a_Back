package com.fotografia.errors;

import org.springframework.http.HttpStatus;

public class GalleryException extends RuntimeException {
    private final HttpStatus status;

    public GalleryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}