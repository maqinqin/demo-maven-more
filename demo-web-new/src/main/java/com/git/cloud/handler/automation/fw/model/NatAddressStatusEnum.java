package com.git.cloud.handler.automation.fw.model;

public enum NatAddressStatusEnum {

	AVAILABLE("可用"),
	DNAT("DNAT"),
	SNAT("SNAT"),
	DNAT_SNAT("DNAT/SNAT");
	
	private final String value;
	
	private NatAddressStatusEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
