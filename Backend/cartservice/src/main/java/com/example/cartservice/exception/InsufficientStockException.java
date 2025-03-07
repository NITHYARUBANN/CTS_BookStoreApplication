package com.example.cartservice.exception;

/**
 * Exception thrown when there is insufficient stock to fulfill a request.
 */
public class InsufficientStockException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new InsufficientStockException with the specified detail
	 * message.
	 *
	 * @param message the detail message
	 */
	public InsufficientStockException(String message) {
		super(message);
	}
}