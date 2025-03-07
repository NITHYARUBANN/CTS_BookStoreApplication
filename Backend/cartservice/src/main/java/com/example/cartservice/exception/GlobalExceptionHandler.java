package com.example.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions across the
 * application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles ResourceNotFoundException.
	 *
	 * @param ex the ResourceNotFoundException
	 * @return a ResponseEntity containing the error message and HTTP status
	 *         NOT_FOUND
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles validation exceptions.
	 *
	 * @param ex      the MethodArgumentNotValidException
	 * @param request the WebRequest
	 * @return a ResponseEntity containing the validation errors and HTTP status
	 *         BAD_REQUEST
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed", errors.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles constraint violation exceptions.
	 *
	 * @param ex      the ConstraintViolationException
	 * @param request the WebRequest
	 * @return a ResponseEntity containing the validation errors and HTTP status
	 *         BAD_REQUEST
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations()
				.forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed", errors.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles global exceptions.
	 *
	 * @param ex      the Exception
	 * @param request the WebRequest
	 * @return a ResponseEntity containing the error message and HTTP status
	 *         INTERNAL_SERVER_ERROR
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles EmptyCartException.
	 *
	 * @param ex the EmptyCartException
	 * @return a ResponseEntity containing the error message and HTTP status
	 *         BAD_REQUEST
	 */
	@ExceptionHandler(EmptyCartException.class)
	public ResponseEntity<Map<String, String>> handleEmptyCartException(EmptyCartException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles InsufficientStockException.
	 *
	 * @param ex the InsufficientStockException
	 * @return a ResponseEntity containing the error message and HTTP status
	 *         BAD_REQUEST
	 */
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<Map<String, String>> handleInsufficientStockException(InsufficientStockException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}

