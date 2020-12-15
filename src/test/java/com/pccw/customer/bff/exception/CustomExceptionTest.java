package com.pccw.customer.bff.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * This class is used to test CustomException
 * 
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)

class CustomExceptionTest {

	@Test
	void customExceptionTestCases() throws Exception {
		CustomException customException = new CustomException("No Records", "Data not found");
		assertThat(customException.getMessage()).isEqualTo("No Records");
		assertThat(customException.getDetails()).isEqualTo("Data not found");
	}
}
