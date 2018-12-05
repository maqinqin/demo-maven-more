package com.git.cloud.common.model;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public enum IsActiveEnum {

	YES("Y"),
	NO("N");
	
	private final String value;
	
	private IsActiveEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
