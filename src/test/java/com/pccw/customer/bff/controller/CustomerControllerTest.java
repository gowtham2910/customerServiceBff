package com.pccw.customer.bff.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.customer.bff.dto.CustomerDto;
import com.pccw.customer.bff.exception.CustomException;
import com.pccw.customer.bff.serviceimpl.CustomerRestServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	CustomerRestServiceImpl customerService;

	@BeforeEach
	public void setUp() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Autowired
	CustomerBffController customerController;
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
	 * Test Case For getting all Customer list
	 *
	 * @param fields
	 * @param offset
	 * @param limit
	 */

	@Test
	void getAllCustomerTest() throws Exception {

		List<CustomerDto> customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<CustomerDto>>() {
				});
		when(customerService.listOfCustomers(Mockito.any(), Mockito.any())).thenReturn(customer);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/customer?fields=fields&offset=0&limit=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
				.andExpect(content().json(getFile("classpath:checkAllData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case For throwing exception with null values for list of Customer
	 * 
	 *
	 * @param fields
	 * @param offset=null
	 * @param limit
	 */

	@Test
	void getAllCustomerNullOffsetExceptionTest() throws Exception {

		List<CustomerDto> customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<CustomerDto>>() {
				});
		when(customerService.listOfCustomers(Mockito.any(), Mockito.any())).thenReturn(customer);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/customer?fields=fields&limit=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		assertNotNull(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Test Case For throwing exception with null values for list of Customer
	 *
	 *
	 * @param fields
	 * @param offset
	 * @param limit  =null
	 */

	@Test
	void getAllCustomerNullLimitExceptionTest() throws Exception {

		List<CustomerDto> customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<CustomerDto>>() {
				});
		when(customerService.listOfCustomers(Mockito.any(), Mockito.any())).thenReturn(customer);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/customer?fields=fields&offset=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}

	/**
	 * Test Case For throwing custom exception for list of Customer
	 *
	 * 
	 */
	@Test
	void getAllCustomerExceptionTest1() throws Exception {

		CustomException customException = new CustomException("ParameterMissing", "Few aparameters are missing");

		try {
			when(customerService.listOfCustomers(Mockito.any(), Mockito.any())).thenThrow(customException);
			customerController.listofCustomers(null, null, null);

			// .andExpect(MockMvcResultMatchers.status().isInternalServerError());
		} catch (CustomException ex) {
			assertEquals(ex, customException);
		}

	}

	/**
	 * Test Case For creating new Customer
	 *
	 * @return Customer
	 */

	@Test
	void saveCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {

		CustomerDto customer = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<CustomerDto>() {
				});
		when(customerService.createCustomer(Mockito.any(CustomerDto.class)))
				.thenReturn(customer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
				.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:createData.json"))
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print())
				.andExpect(content().json(getFile("classpath:createCheckData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case For throwing custom exception for list of  Customer
	 *
	 * 
	 */
	@Test
	void saveCustomerExceptionTest1() throws Exception {

		CustomException customException = new CustomException("ParameterMissing", "Few aparameters are missing");
		try {
			when(customerService.createCustomer(Mockito.any())).thenThrow(customException);
			customerController.createCustomer(null);
			// .andExpect(MockMvcResultMatchers.status().isInternalServerError());
		} catch (CustomException ex) {
			assertEquals(ex, customException);
		}

	}

	/**
	 * Test Case Retrieving a Customer by ID.
	 *
	 * @param id
	 * @param fields
	 * @return Customer
	 */
	@Test
	void retrieveByIdTest() throws JsonMappingException, JsonProcessingException, Exception {
		CustomerDto[] customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<CustomerDto[]>() {
				});
		when(customerService.retrieveCustomerById(Mockito.any(), Mockito.any())).thenReturn(customer[1]);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/customer/2?fields=fields")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
				.andExpect(content().json(getFile("classpath:retrieveCheckData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case Retrieving a Customer by ID.
	 *
	 * @param id
	 * @param fields=null
	 * @return Customer
	 */

	@Test
	void retrieveByIdTest1() throws JsonMappingException, JsonProcessingException, Exception {
		CustomerDto[] customer = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<CustomerDto[]>() {
				});
		when(customerService.retrieveCustomerById(Mockito.any(), Mockito.any())).thenReturn(customer[1]);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/customer/2")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
				.andExpect(content().json(getFile("classpath:retrieveCheckData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case For throwing custom exception for retrieve of Customer
	 * 
	 *
	 * 
	 */
	@Test
	void retrieveCustomerExceptionTest1() throws Exception {

		CustomException customException = new CustomException("ParameterMissing", "Few aparameters are missing");
		try {
			when(customerService.retrieveCustomerById(Mockito.any(), Mockito.any()))
					.thenThrow(customException);
			customerController.retrieveCustomerById(null, null);
			// .andExpect(MockMvcResultMatchers.status().isInternalServerError());
		} catch (CustomException ex) {
			assertEquals(ex, customException);
		}

	}

	/**
	 * Test Case Updating partially a Customer.
	 *
	 * @param id
	 * @param customer
	 * @return Customer
	 */
	@Test
	void updateCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {
		CustomerDto customer = obj.readValue(getFile("classpath:updateData.json"),
				new TypeReference<CustomerDto>() {
				});
		when(customerService.updateCustomer(Mockito.any(), Mockito.any(CustomerDto.class)))
				.thenReturn(customer);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/customer/1");
		this.mockMvc
				.perform(builder.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:updateData.json"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect((ResultMatcher) jsonPath("$.customer_id", is("1"))).andDo(print())
				.andExpect(content().json(getFile("classpath:UpdateCheckData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case For throwing custom exception for update Customer
	 *
	 * 
	 */
	@Test
	void updateCustomerExceptionTest1() throws Exception {

		CustomException customException = new CustomException("ParameterMissing", "Few aparameters are missing");
		try {
			when(customerService.updateCustomer(Mockito.any(), Mockito.any())).thenThrow(customException);
			customerController.updateCustomer(null, null);
			// .andExpect(MockMvcResultMatchers.status().isInternalServerError());
		} catch (CustomException ex) {
			assertEquals(ex, customException);
		}

	}

	/**
	 * Test case for Deleting a Customer.
	 *
	 * @param id
	 */
	@Test
	void deleteCustomerTest() throws JsonMappingException, JsonProcessingException, Exception {

		Mockito.doNothing().when(customerService).deleteCustomer(Mockito.any());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/customer/1");
		this.mockMvc
				.perform(builder.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:updateData.json"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	/**
	 * Test Case For throwing custom exception for delete Customer
	 *
	 * 
	 */
	@Test
	void deleteCustomerExceptionTest1() throws Exception {

		CustomException customException = new CustomException("ParameterMissing", "Few aparameters are missing");
		try {
			Mockito.doThrow(customException).when(customerService).deleteCustomer(Mockito.any());
			customerController.deleteCustomer(null);
			// .andExpect(MockMvcResultMatchers.status().isInternalServerError());
		} catch (CustomException ex) {
			assertEquals(ex, customException);
		}

	}

}