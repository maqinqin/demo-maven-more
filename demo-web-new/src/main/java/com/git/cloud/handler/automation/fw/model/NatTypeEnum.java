package com.git.cloud.handler.automation.fw.model;

public enum NatTypeEnum {

	DNAT("DNAT"),
	SNAT("SNAT");
	
	private final String value;
	
	private NatTypeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
