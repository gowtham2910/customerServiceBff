package com.pccw.customer.bff.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RelatedParty entity class
 * @author 40003450
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelatedPartyDto implements Serializable {
	
		private static final long serialVersionUID = 1L;
			
		private String relatedParty_id = null;
		private String href = null;  
		private String name = null;
		private String role = null;
		private String baseType = null;
		private String schemaLocation = null;  
		private String type = null;
		private String referredType = null; 
}

