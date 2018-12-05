package com.git.cloud.common.enums;

public enum RmVolumeTypeEnum {
	
	EXTERNAL_DISK("exterDisk"),		// 外挂磁盘
	INTERNAL_DISK("interDisk") 		//内置磁盘
	;
    
    private final String value;

    private RmVolumeTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RmVolumeTypeEnum fromString(String value ) {
		if (value != null) {
			for (RmVolumeTypeEnum c : RmVolumeTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }

}
