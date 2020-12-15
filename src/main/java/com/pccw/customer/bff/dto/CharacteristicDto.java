package com.pccw.customer.bff.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Characteristic DTO class
 * @author 40003450
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicDto implements Serializable {
		private static final long serialVersionUID = 1L;
	
		private String name = null;
		private String valueType = null;
		private String value = null;
		private String baseType = null;
		private String schemaLocation = null;  
		private String type = null;
}
