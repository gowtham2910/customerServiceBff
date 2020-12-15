package com.pccw.customer.bff.exception;

import org.springframework.http.HttpStatus;

/**
 * This class is used to handle errors from BizComp Service
 * @author 40003450
 *
 */
public class BizCompServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String details;
	private final HttpStatus httpStatus;

	/**
	 * This is parameterized constructor of BizCompServiceException class
	 * 
	 * @param message
	 * @param details
	 */
	public BizCompServiceException(String message, String details, HttpStatus httpStatus) {
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
