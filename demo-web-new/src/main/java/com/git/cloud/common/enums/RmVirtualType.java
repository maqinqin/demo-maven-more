package com.git.cloud.common.enums;

public enum RmVirtualType {
	
	POWERVM("PV"),
	VMWARE("VM"),
	KVM("KV"),
	XEN("XE"),
	OPENSTACKKVM("OK"),
	OPENSTACKIRONIC("OI")
	;
    
    private final String value;

    private RmVirtualType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RmVirtualType fromString(String value ) {
		if (value != null) {
			for (RmVirtualType c : RmVirtualType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }

}
