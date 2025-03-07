package com.example.cartservice.exception;

/**
 * The ResourceNotFoundException class is a custom exception that is thrown when
 * a requested resource is not found in the application. This class extends
 * RuntimeException, allowing it to be used as an unchecked exception.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}