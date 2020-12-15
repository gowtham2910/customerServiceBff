package com.pccw.customer.bff.exception;

import org.springframework.http.HttpStatus;

/**
 * This class is used to handle when there are parameters missing from client
 * @author 40003450
 *
 */
public class ParameterMissingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String details;
	private final HttpStatus httpStatus;

	/**
	 * This is parameterized constructor of ParameterMissingFromClientException class
	 * 
	 * @param message
	 * @param details
	 */
	public ParameterMissingException(String message, String details, HttpStatus httpStatus) {
		super(message);
		this.details = details;
		this.httpStatus = httpStatus;
	}

	/**
	 * This method returns the exception details
	 * 
	 * @return details
	 */
	public String getDetails() {
		return details;
	}
	
	/**
	 * This method returns the exception HttpStatus
	 * 
	 * @return details
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
	