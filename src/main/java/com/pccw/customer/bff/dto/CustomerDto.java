package com.pccw.customer.bff.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer DTO class
 * 
 * @author 40003450
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String customer_id = null;
		private String href = null;
		private String name = null;
		private String status = null;
		private String statusReason = null;
		private TimePeriod validFor = null;
		private String baseType = null;
		private String schemaLocation = null;
		private String type = null;
		private List<ContactMediumDto> contactMedium = null;
		private List<RelatedPartyDto> relatedParty = null;
		private List<CharacteristicDto> characteristic = null;
		private List<AccountRefDto> accountRef = null;
		private List<PaymentMethodRefDto> paymentMethodRef = null;
		private List<CreditProfileDto> creditProfile = null;
		private List<AgreementRefDto> agreementRef = null;
		
}