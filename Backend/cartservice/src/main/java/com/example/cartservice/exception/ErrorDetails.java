package com.example.cartservice.exception;

import java.util.Date;

import lombok.Data;

/**
 * The ErrorDetails class is a simple POJO (Plain Old Java Object) that
 * encapsulates error information to be returned in API responses. This class
 * includes details such as the time stamp of the error, a message describing
 * the error, and additional details about the error.
 */
@Data
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}