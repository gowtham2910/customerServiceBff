package com.pccw.customer.bff.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.pccw.customer.bff.constants.Constants;
import com.pccw.customer.bff.dto.CustomerDto;
import com.pccw.customer.bff.exception.BizCompServiceException;
import com.pccw.customer.bff.exception.RestUrlFormationException;
import com.pccw.customer.bff.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is the client service implementation class which
 * interacts with bizComp service to return the expected result
 * @author 40003450 
 */

@Slf4j
@Service
public class CustomerRestServiceImpl implements CustomerService {

    @Autowired
    Environment env;

    @Autowired
    RestTemplate restTemplate;

    /**
     * List or find Customer objects.
     *
     * @param id
     * @param fields
     * @return Customer
     */
    @Override
    public List<CustomerDto> listOfCustomers(Map<String, Object> pathParams, Map<String, Object> queryParams) {
        List<CustomerDto> customerList = new ArrayList<>();
        try {
            log.info("List Of Customers in BFF");
            HttpEntity<List<CustomerDto>> customerEntity = new HttpEntity<>(httpHeaderConfig());
            ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(
                    getURI(pathParams, queryParams), HttpMethod.GET, customerEntity,
                    new ParameterizedTypeReference<List<CustomerDto>>() {
                    });
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                customerList = response.getBody();
            }
        } catch (BizCompServiceException ce) {
            log.error(ce.getMessage(), ce);
            throw ce;
        }
        return customerList;
    }

    /**
     * Creates a Customer.
     *
     * @param cust
     * @return Customer
     */
    @Override
    public CustomerDto createCustomer(CustomerDto cust) {
        CustomerDto customer = null;
        try {
            log.info("Create Customer in BFF");
            HttpEntity<CustomerDto> customerEntity = new HttpEntity<>(cust);
            messageConverter(restTemplate);
            ResponseEntity<CustomerDto> customerRes = restTemplate.exchange(getURI(null, null),
                    HttpMethod.POST, customerEntity, CustomerDto.class);
            if (customerRes.getStatusCode() == HttpStatus.OK && customerRes.getBody() != null) {
                customer = customerRes.getBody();
            }
        } catch (BizCompServiceException ce) {
            log.error(ce.getMessage(), ce);
            throw ce;
        }
        return customer;
    }

    /**
     * Retrieves a Customer by ID.
     *
     * @param id
     * @param fields
     * @return Customer
     */
    @Override
    public CustomerDto retrieveCustomerById(Map<String, Object> pathParams,
                                                                    Map<String, Object> queryParams) {
        CustomerDto customer = null;
        try {
            log.info("Retrieve Customer By Id in BFF");
            HttpEntity<CustomerDto> customerEntity = new HttpEntity<>(httpHeaderConfig());
            ResponseEntity<CustomerDto> response = restTemplate.exchange(getURI(pathParams, queryParams),
                    HttpMethod.GET, customerEntity, CustomerDto.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                customer = response.getBody();
            }
        } catch (BizCompServiceException ce) {
            log.error(ce.getMessage(), ce);
            throw ce;
        }
        return customer;
    }

    /**
     * Updates partially a Customer.
     *
     * @param id
     * @param cust
     * @return Customer
     */
    @Override
    public CustomerDto updateCustomer(Map<String, Object> pathParams,
                                                              CustomerDto cust) {
        CustomerDto customer = null;
        try {
            log.info("Update Customer in BFF");
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpEntity<CustomerDto> customerEntity = new HttpEntity<>(cust, httpHeaderConfig());
            messageConverter(restTemplate);
            ResponseEntity<CustomerDto> customerRes = restTemplate.exchange(getURI(pathParams, null),
                    HttpMethod.PATCH, customerEntity, CustomerDto.class);
            if (customerRes.getStatusCode() == HttpStatus.OK && customerRes.getBody() != null) {
                customer = customerRes.getBody();
            }
        } catch (BizCompServiceException ce) {
            log.error(ce.getMessage(), ce);
            throw ce;
        }
        return customer;
    }

    /**
     * Deletes a Customer.
     *
     * @param id
     */
    @Override
    public void deleteCustomer(Map<String, Object> pathParams) {
        try {
            log.info("Delete Customer in BFF");
            messageConverter(restTemplate);
            restTemplate.delete(getURI(pathParams, null));
        } catch (BizCompServiceException ce) {
            log.error(ce.getMessage(), ce);
            throw ce;
        }
    }

    // TODO - To move below methods to Utility class

    /**
     * @param pathparams
     * @param queryParams
     * @return
     */
    private String getURI(Map<String, Object> pathparams, Map<String, Object> queryParams) {
    	UriComponentsBuilder builder = null;
    	try {
        StringBuilder url = new StringBuilder(env.getProperty(Constants.CUSTOMER_BIZCOMP_URL) + ":"
                + env.getProperty(Constants.CUSTOMER_BIZCOMP_PORT) + "/" + Constants.CUSTOMER_SERVICE);
        if (null != pathparams && !pathparams.isEmpty()) {
            for (Map.Entry<String, Object> entry : pathparams.entrySet()) {
                url.append("/" + entry.getValue());
            }
        }
        builder = UriComponentsBuilder.fromHttpUrl(url.toString());
        if (null != queryParams && !queryParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
    	}catch (RestUrlFormationException se) {
    		log.error(se.getMessage(), se);
            throw se;
    	}
        return builder.toUriString();
    }

    private HttpHeaders httpHeaderConfig() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return requestHeaders;
    }

    private void messageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM,
                MediaType.APPLICATION_FORM_URLENCODED));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

}
