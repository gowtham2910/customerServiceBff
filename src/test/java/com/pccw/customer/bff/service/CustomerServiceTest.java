package com.pccw.customer.bff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.customer.bff.dto.CustomerDto;
import com.pccw.customer.bff.exception.BizCompServiceException;
import com.pccw.customer.bff.serviceimpl.CustomerRestServiceImpl;

/**
 * This class is the service implementation Test class
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CustomerRestServiceImpl customerService;

	@Mock
	Environment mockEnvironment;

	@BeforeEach
	public void setUp() {
		when(mockEnvironment.getProperty("customer.bizcomp.url")).thenReturn("http://localhost");
		when(mockEnvironment.getProperty("customer.bizcomp.port")).thenReturn("8083");
	}

	@Autowired
	private ResourceLoader resourceLoader;
	ObjectMapper obj = new ObjectMapper();

	String getFile(String location) throws Exception {
		Resource resource = resourceLoader.getResource(location);
		InputStream input = resource.getInputStream();
		byte[] bdata = FileCopyUtils.copyToByteArray(input);
		String data = new String(bdata, StandardCharsets.UTF_8);
		return data;
	}

	/**
	 * Test Case For service implementation for Customer list
	 *
	 * 
	 */
	@Test
	void getAllCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {

		List<CustomerDto> customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<CustomerDto>>() {
				});

		ResponseEntity<List<CustomerDto>> resp = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.any(ParameterizedTypeReference.class))).thenReturn(resp);

		Map<String, Object> map = new HashMap<>();
		map.put("fields", "field");
		map.put("offset", 1);
		map.put("limit", 2);
		List<CustomerDto> result = customerService.listOfCustomers(null, map);
		assertNotNull(HttpStatus.OK);
		assertEquals(2, result.size());
	}

	/**
	 * Test Case For exception in service implementation for list of
	 * Customer
	 *
	 * 
	 */
	@Test
	void getAllCustomerExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.any(ParameterizedTypeReference.class))).thenThrow(bizcompServiceException);
		try {
			customerService.listOfCustomers(null, null);
		} catch (BizCompServiceException bz) {
			assertEquals(bz, bizcompServiceException);
		}
	}

	/**
	 * Test Case For service implementation for create customer
	 *
	 * 
	 */
	@Test
	void saveCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {
		CustomerDto customer = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<CustomerDto>() {
				});

		ResponseEntity<CustomerDto> resp = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenReturn(resp);

		CustomerDto result = customerService.createCustomer(customer);
		assertNotNull(HttpStatus.OK);
		assertEquals(customer, result);
	}

	/**
	 * Test Case For exception in service implementation for create
	 * Customer
	 *
	 * 
	 */
	@Test
	void saveCustomerExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenThrow(bizcompServiceException);
		try {
			customerService.createCustomer(null);
		} catch (BizCompServiceException bz) {
			assertEquals(bz, bizcompServiceException);
		}
	}

	/**
	 * Test Case For service implementation for retrieve Customer by ID
	 *
	 * 
	 */
	@Test
	void retrieveCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {

		CustomerDto customer = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<CustomerDto>() {
				});

		ResponseEntity<CustomerDto> resp = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenReturn(resp);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("customer_id", "3");
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("fields", "field");
		CustomerDto result = customerService.retrieveCustomerById(pathParams, queryParams);
		assertNotNull(HttpStatus.OK);
		assertEquals(customer, result);
	}

	/**
	 * Test Case For exception in service implementation for retrieve
	 * Customer
	 *
	 * 
	 */
	@Test
	void retrieveCustomerExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenThrow(bizcompServiceException);
		try {
			customerService.retrieveCustomerById(null, null);
		} catch (BizCompServiceException bz) {
			assertEquals(bz, bizcompServiceException);
		}
	}

	/**
	 * Test Case For service implementation for update Customer
	 *
	 * 
	 */
	@Test
	void updateCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {

		CustomerDto customer = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<CustomerDto>() {
				});

		ResponseEntity<CustomerDto> resp = new ResponseEntity<>(customer, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenReturn(resp);
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("customer_id", "3");
		CustomerDto result = customerService.updateCustomer(pathParams, customer);
		assertNotNull(HttpStatus.OK);
		assertEquals(customer, result);
	}

	/**
	 * Test Case For exception in service implementation for update
	 * Customer
	 *
	 * 
	 */
	@Test
	void updateCustomerExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<CustomerDto>>any())).thenThrow(bizcompServiceException);
		try {
			customerService.updateCustomer(null, null);
		} catch (BizCompServiceException bz) {
			assertEquals(bz, bizcompServiceException);
		}
	}

	/**
	 * Test Case For service implementation for delete Customer
	 *
	 * 
	 */
	@Test
	void deleteCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {
		CustomerDto customer = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<CustomerDto>() {
				});

		ResponseEntity<CustomerDto> resp = new ResponseEntity<>(customer, HttpStatus.OK);

		Mockito.doNothing().when(restTemplate).delete(Mockito.anyString());

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("customer_id", "3");
		customerService.deleteCustomer(pathParams);
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case For exception in service implementation for retrieve
	 * Customer
	 *
	 * 
	 */
	@Test
	void deleteCustomerExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {
		BizCompServiceException bizcompServiceException = new BizCompServiceException("Bad-Request",
				"Bad request is found", HttpStatus.BAD_REQUEST);

		Mockito.doThrow(bizcompServiceException).when(restTemplate).delete(Mockito.anyString());
		try {
			customerService.deleteCustomer(null);
		} catch (BizCompServiceException bz) {
			assertEquals(bz, bizcompServiceException);
		}
	}
}