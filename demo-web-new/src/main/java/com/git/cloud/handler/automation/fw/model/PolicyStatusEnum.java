package com.git.cloud.handler.automation.fw.model;

public enum PolicyStatusEnum {

	INVALID("0"),
	VALID("1"),
	DELETE("2");
	
	private final String value;
	
	private PolicyStatusEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
