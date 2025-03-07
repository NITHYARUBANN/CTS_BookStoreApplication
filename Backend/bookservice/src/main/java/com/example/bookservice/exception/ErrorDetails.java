package com.example.bookservice.exception;

import java.util.Date;

/**
 * A class representing error details for exception handling.
 */
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	/**
	 * Constructs a new ErrorDetails object with the specified timestamp, message,
	 * and details.
	 *
	 * @param timestamp the timestamp of the error
	 * @param message   the error message
	 * @param details   additional details about the error
	 */
	public ErrorDetails(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	/**
	 * Gets the timestamp of the error.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp of the error.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the error message.
	 *
	 * @param message the new error message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets additional details about the error.
	 *
	 * @return the error details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets additional details about the error.
	 *
	 * @param details the new error details
	 */
	public void setDetails(String details) {
		this.details = details;
	}
}