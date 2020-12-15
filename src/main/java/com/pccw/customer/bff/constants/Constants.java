package com.pccw.customer.bff.constants;

/**
 * Class containing String constants used in the project
 * 
 * @author 40003450
 *
 */
public final class Constants {

	private Constants() {

	}

	public static final String CUSTOMER_SERVICE = "/customer";
	public static final String LISTOF_CUSTOMER_PARAMS = "/{fields}/{offset}/{limit}";
	public static final String RETRIEVE_CUSTOMER_PARAMS = "/{id}/{fields}";
	public static final String UPDATE_CUSTOMER_PARAMS = "/{id}/{customer}";
	public static final String CUSTOMER_ID_PARAM = "/{id}";
	public static final String MESSAGE = "message";
	public static final String DETAILS = "details";
	public static final String ACCESS_DENIED = "Access denied";
	public static final String MISSING_LIMIT = "Missing Limit";
	public static final String MISSING_LIMIT_MSG = "Please provide the limit for the given offset";
	public static final String MISSING_OFFSET = "Missing Offset";
	public static final String MISSING_OFFSET_MSG = "Please provide the offset for the given limit";
	public static final String CUSTOMER_BIZCOMP_URL = "customer.bizcomp.url";
	public static final String CUSTOMER_BIZCOMP_PORT = "customer.bizcomp.port";
}
