package com.pccw.customer.bff.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * AnyDto DTO class
 * 
 * @author 40003450
 *
 */
public class AnyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Any {\n");

		sb.append("}");
		return sb.toString();
	}
}
