package com.pccw.customer.bff.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MediumCharacteristic DTO class
 * @author 40003450
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediumCharacteristicDto implements Serializable {
	  private static final long serialVersionUID = 1L;
	  
		private String city = null;
		private String contactType = null;
		private String country = null;
		private String emailAddress = null;
		private String faxNumber = null;
		private String phoneNumber = null;
		private String postCode = null;
		private String socialNetworkId = null;
		private String stateOrProvince = null;
		private String street1 = null;
		private String street2 = null;
		private String baseType = null;
		private String schemaLocation = null;  
		private String type = null;
}
