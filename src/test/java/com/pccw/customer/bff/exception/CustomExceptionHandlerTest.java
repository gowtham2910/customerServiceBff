package com.pccw.customer.bff.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pccw.customer.bff.constants.Constants;


/**
 * This class is used to test CustomeExceptionHandler 
 * 
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

	@Autowired
	CustomExceptionHandler customExceptionHandler;

	@Test
	void handleInternalServerErrorExceptionTest() throws Exception {
		CustomException ex = new CustomException("No Records", "NodataFound");
		Map<String, String> res = new HashMap<>();
		res.put(Constants.MESSAGE, ex.getMessage());
		res.put(Constants.DETAILS, ex.getDetails());
		ResponseEntity<Object> result = customExceptionHandler.handleInternalServerErrorException(ex);
		assertEquals(result.getBody(), res);
	}
	@Test
	void handleBizCompServicTesteException() {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);
		Map<String, String> res = new HashMap<>();
		res.put(Constants.MESSAGE, bizcompServiceException.getMessage());
		res.put(Constants.DETAILS, bizcompServiceException.getDetails());
		ResponseEntity<Object> result = customExceptionHandler.handleBizCompServiceException(bizcompServiceException);
		assertEquals(result.getBody(), res);
	}
	@Test
	void handleParameterMissingExceptionTest() {
		ParameterMissingException parameterMissingException = new ParameterMissingException(",'/' missing",
				"Missing '/' parameter", HttpStatus.NOT_FOUND);
		Map<String, String> res = new HashMap<>();
		res.put(Constants.MESSAGE, parameterMissingException.getMessage());
		res.put(Constants.DETAILS, parameterMissingException.getDetails());
		ResponseEntity<Object> result = customExceptionHandler
				.handleParameterMissingException(parameterMissingException);
		assertEquals(result.getBody(), res);
	}
	@Test
	void bizcompServiceExceptionTestCases() {
		RestUrlFormationException restUrlFormationException = new RestUrlFormationException("Unexpected URL",
				"The url localhost:/ is not found: connection refused", HttpStatus.NOT_FOUND);
		Map<String, String> res = new HashMap<>();
		res.put(Constants.MESSAGE, restUrlFormationException.getMessage());
		res.put(Constants.DETAILS, restUrlFormationException.getDetails());
		ResponseEntity<Object> result = customExceptionHandler
				.handleRestUrlFormationException(restUrlFormationException);
		assertEquals(result.getBody(), res);
	}
	

}
