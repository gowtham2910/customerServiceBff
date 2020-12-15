package com.pccw.customer.bff;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * SpringBoot application Test class
 * 
 * @author 40006154
 *
 */
@SpringBootTest(classes = CustomerServiceBffApplication.class)
@ActiveProfiles("devlocal")
class CustomerServiceBffApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		assertThat(this.context).isNotNull();
	}

	@Test
	void contextLoadsNew() {
		CustomerServiceBffApplication.main(new String[] {});
		assertThat(this.context).isNotNull();
	}

}