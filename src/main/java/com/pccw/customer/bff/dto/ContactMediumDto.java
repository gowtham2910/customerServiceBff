package com.pccw.customer.bff.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ContactMedium DTO class
 * 
 * @author 40003450
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactMediumDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contact_Medium_id = null;
	private String mediumType = null;
	private Boolean preferred = null;
	private TimePeriod validFor = null;
	private String baseType = null;
	private String schemaLocation = null;
	private String type = null;
	private MediumCharacteristicDto mediumCharacteristic = null;

}
