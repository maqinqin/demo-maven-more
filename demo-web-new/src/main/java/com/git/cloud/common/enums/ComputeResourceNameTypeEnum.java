package com.git.cloud.common.enums;

public enum ComputeResourceNameTypeEnum {
	VMNAME("VM_NAME"), HOSTNAME("HOST_NAME");
	private final String value;

	private ComputeResourceNameTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static ComputeResourceNameTypeEnum fromString(String value) {
		if (value != null) {
			for (ComputeResourceNameTypeEnum c : ComputeResourceNameTypeEnum
					.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
