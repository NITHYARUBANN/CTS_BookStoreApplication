package com.example.bookservice.exception;

/**
 * Exception thrown when a book is not found.
 */
public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new BookNotFoundException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public BookNotFoundException(String message) {
		super(message);
	}
}