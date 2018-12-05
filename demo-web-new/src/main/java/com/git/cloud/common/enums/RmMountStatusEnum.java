package com.git.cloud.common.enums;

public enum RmMountStatusEnum {
	
	UNMOUNT("unmount"),		// 未挂载：unmount
	MOUNT("mount") 			//已挂载：mount
	;
    
    private final String value;

    private RmMountStatusEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RmMountStatusEnum fromString(String value ) {
		if (value != null) {
			for (RmMountStatusEnum c : RmMountStatusEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }

}
