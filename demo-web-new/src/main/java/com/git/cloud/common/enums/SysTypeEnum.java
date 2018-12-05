package com.git.cloud.common.enums;

public enum SysTypeEnum {
	OPENSTACK("OPENSTACK"), // openstack系统
	VMWARE("VMWARE");// VMWARE系统
    
    private final String value;

    private SysTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static SysTypeEnum fromString(String value ) {
		if (value != null) {
			for (SysTypeEnum c : SysTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }
}
