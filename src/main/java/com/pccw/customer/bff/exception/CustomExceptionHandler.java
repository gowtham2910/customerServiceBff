package com.pccw.customer.bff.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pccw.customer.bff.constants.Constants;

/**
 * This is Custom Exceptions handler class
 * 
 * @author 40003450
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method returns the exception message and details
	 * 
	 * @param ex
	 * @return res
	 */
	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> handleInternalServerErrorException(CustomException ex) {
		Map<String, String> res = new HashMap<>();
		CustomException exceptionResponse = new CustomException(ex.getMessage(), ex.getDetails());
		res.put(Constants.MESSAGE, exceptionResponse.getMessage());
		res.put(Constants.DETAILS, exceptionResponse.getDetails());
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method returns the BizCompService message and details
	 * 
	 * @param ex
	 * @return res
	 */
	@ExceptionHandler(value = BizCompServiceException.class)
	public ResponseEntity<Object> handleBizCompServiceException(BizCompServiceException ex) {
		Map<String, String> res = new HashMap<>();
		BizCompServiceException exceptionResponse = new BizCompServiceException(ex.getMessage(), ex.getDetails(), HttpStatus.INTERNAL_SERVER_ERROR);
		res.put(Constants.MESSAGE, exceptionResponse.getMessage());
		res.put(Constants.DETAILS, exceptionResponse.getDetails());		
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method returns the parameter missing exception message and details
	 * 
	 * @param ex
	 * @return res
	 */
	@ExceptionHandler(value = ParameterMissingException.class)
	public ResponseEntity<Object> handleParameterMissingException(ParameterMissingException ex) {
		Map<String, String> res = new HashMap<>();
		ParameterMissingException exceptionResponse = new ParameterMissingException(ex.getMessage(), ex.getDetails(), HttpStatus.INTERNAL_SERVER_ERROR);
		res.put(Constants.MESSAGE, exceptionResponse.getMessage());
		res.put(Constants.DETAILS, exceptionResponse.getDetails());		
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method returns the Rest URL Formation exception message and details
	 * 
	 * @param ex
	 * @return res
	 */
	@ExceptionHandler(value = RestUrlFormationException.class)
	public ResponseEntity<Object> handleRestUrlFormationException(RestUrlFormationException ex) {
		Map<String, String> res = new HashMap<>();
		RestUrlFormationException exceptionResponse = new RestUrlFormationException(ex.getMessage(), ex.getDetails(), HttpStatus.INTERNAL_SERVER_ERROR);
		res.put(Constants.MESSAGE, exceptionResponse.getMessage());
		res.put(Constants.DETAILS, exceptionResponse.getDetails());		
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
