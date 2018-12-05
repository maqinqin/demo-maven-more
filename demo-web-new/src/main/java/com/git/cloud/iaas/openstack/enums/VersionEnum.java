package com.git.cloud.iaas.openstack.enums;


public enum VersionEnum {
	HW_VERSION_206("2.0.6"),
	HW_VSERSION_63("6.3");
	
	private final String value;
	
	private VersionEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static VersionEnum fromString(String value ) {
		if (value != null) {
			for (VersionEnum c : VersionEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
