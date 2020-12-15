package com.pccw.customer.bff.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pccw.customer.bff.constants.Constants;
import com.pccw.customer.bff.dto.CustomerDto;
import com.pccw.customer.bff.exception.CustomException;
import com.pccw.customer.bff.exception.ParameterMissingException;
import com.pccw.customer.bff.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is RestController class in Customer BFF
 *
 * @author 40003450
 */
@Slf4j
@RestController
public class CustomerBffController {

    @Autowired
    CustomerService customerService;

    /**
     * List or find CustomerService objects.
     *
     * @param fields
     * @param offset
     * @param limit
     * @return List<CustomerService>
     */
    @GetMapping(value = Constants.CUSTOMER_SERVICE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> listofCustomers(
            @RequestParam(value = "fields", required = false) String fields,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<CustomerDto> customer = new ArrayList<>();
        try {
            if (null != offset && null == limit) {
            	throw new ParameterMissingException(Constants.MISSING_LIMIT, Constants.MISSING_LIMIT_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (null != limit && null == offset) {
            	throw new ParameterMissingException(Constants.MISSING_OFFSET, Constants.MISSING_OFFSET_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, Object> queryParams = new HashMap<>();
            if (null != fields)
                queryParams.put("fields", fields);
            if (null != offset)
                queryParams.put("offset", offset);
            if (null != limit)
                queryParams.put("limit", limit);
            customer = customerService.listOfCustomers(null, queryParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Creates a CustomerService.
     *
     * @param cusomter
     * @return CustomerService
     */
    @PostMapping(value = Constants.CUSTOMER_SERVICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createCustomer(
            @RequestBody(required = true) CustomerDto customer) {
        CustomerDto customerRes = null;
        try {
            customerRes = customerService.createCustomer(customer);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(customerRes, HttpStatus.CREATED);
    }

    /**
     * Retrieves a CustomerService by ID.
     *
     * @param id
     * @param fields
     * @return CustomerService
     */
    @GetMapping(value = Constants.CUSTOMER_SERVICE + Constants.CUSTOMER_ID_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> retrieveCustomerById(
            @PathVariable(value = "id", required = true) String id,
            @RequestParam(value = "fields", required = false) String fields) {
        CustomerDto customerRes = null;
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            if (null != fields)
                queryParams.put("fields", fields);
            customerRes = customerService.retrieveCustomerById(pathParams, queryParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(customerRes, HttpStatus.OK);
    }

    /**
     * Updates partially a CustomerService.
     *
     * @param id
     * @param customer
     * @return CustomerService
     */
    @PatchMapping(value = Constants.CUSTOMER_SERVICE + Constants.CUSTOMER_ID_PARAM, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable(value = "id", required = true) String id,
            @RequestBody(required = true) CustomerDto customer) {
        CustomerDto customerRes = null;
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            customerRes = customerService.updateCustomer(pathParams, customer);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(customerRes, HttpStatus.OK);
    }

    /**
     * Deletes a CustomerService.
     *
     * @param id
     */
    @DeleteMapping(value = Constants.CUSTOMER_SERVICE + Constants.CUSTOMER_ID_PARAM)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(value = "id", required = true) String id) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            customerService.deleteCustomer(pathParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
    }

}
