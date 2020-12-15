package com.pccw.customer.bff.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * This class is used to test ParameterMissingException
 * 
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)

class ParameterMissingExceptionTest {
	@Test
	void bizcompServiceExceptionTestCases() throws Exception {
		ParameterMissingException bizcompServiceException = new ParameterMissingException(",'/' missing",
				"Missing '/' parameter", HttpStatus.NOT_FOUND);
		assertThat(bizcompServiceException.getDetails()).isEqualTo("Missing '/' parameter");
		assertThat(bizcompServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
