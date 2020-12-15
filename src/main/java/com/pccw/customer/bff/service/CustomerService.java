package com.pccw.customer.bff.service;

import java.util.List;
import java.util.Map;

import com.pccw.customer.bff.dto.CustomerDto;

/**
 * @author 40003450
 *
 */
public interface CustomerService {

	/**
	 * List or find Customer objects.
	 * 
	 * @return List<Customer>
	 */
	public List<CustomerDto> listOfCustomers(Map<String, Object> pathParams,
			Map<String, Object> queryParams);

	/**
	 * Customer Creates a Customer.
	 * 
	 * @param customer
	 * @return Customer
	 */
	public CustomerDto createCustomer(CustomerDto customer);

	/**
	 * Retrieves a Customer by ID.
	 * 
	 * @param id
	 * @param fields
	 * @return Customer
	 */
	public CustomerDto retrieveCustomerById(Map<String, Object> pathParams,
			Map<String, Object> queryParams);

	/**
	 * Updates partially a Customer.
	 * 
	 * @param id
	 * @param customer
	 * @return Customer
	 */
	public CustomerDto updateCustomer(Map<String, Object> pathParams,
			CustomerDto customer);

	/**
	 * Deletes a Customer.
	 * 
	 * @param id
	 */
	public void deleteCustomer(Map<String, Object> pathParams);

}
