package com.example.bookservice.exception;

/**
 * Exception thrown when an operation is restricted to admin users only.
 */
public class AdminOnlyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new AdminOnlyException with the specified detail message.
     *
     * @param message the detail message
     */
    public AdminOnlyException(String message) {
        super(message);
    }
}