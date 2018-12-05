package com.git.cloud.resmgt.common.model;

public enum VmDistriTypeEnum {

	VM_DISTRI_TYPE_SINGLE("SINGLE"),//单机
	VM_DISTRI_TYPE_CLUSTER("CLUSTER"),//集群
	VM_DISTRI_TYPE_MIX("MIX"),//混合型
	;
    
    private final String value;

    private VmDistriTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static VmDistriTypeEnum fromString(String value ) {
		if (value != null) {
			for (VmDistriTypeEnum c : VmDistriTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}

}
