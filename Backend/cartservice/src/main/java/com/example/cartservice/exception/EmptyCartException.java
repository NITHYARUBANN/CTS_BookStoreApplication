package com.example.cartservice.exception;

/**
 * Exception thrown when an operation is attempted on an empty cart.
 */
public class EmptyCartException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new EmptyCartException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public EmptyCartException(String message) {
		super(message);
	}
}