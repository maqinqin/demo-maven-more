package com.git.cloud.common.model;

/**
 * 
 * @author Administrator
 *
 */
public enum QuotaCodeEnum {

	CPU("cpu"),
	MEM("mem"),
	STORAGE("storage"),
	IP("ip"),
	VLB("vlb"),
	VM("vm"),
	NETWORK("network"),
	GROUP("group");
	
	private final String value;
	
	private QuotaCodeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
