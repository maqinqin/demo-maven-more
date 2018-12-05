package com.git.cloud.resmgt.common.model;

public enum DeviceTypeEnum {

	DEVICE_TYPE_STORAGE("STORAGE"),//存储
	DEVICE_TYPE_SERVER("SERVER")//服务器
	;
    
    private final String value;

    private DeviceTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static DeviceTypeEnum fromString(String value ) {
		if (value != null) {
			for (DeviceTypeEnum c : DeviceTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}

}
