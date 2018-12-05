package com.git.cloud.resmgt.common.model;

public enum DeviceManufacturerEnum {

	DEVICE_MANUFACTURER_IBM("IBM"), 
	DEVICE_MANUFACTURER_HP("HP"), 
	DEVICE_MANUFACTURER_EMC("EMC"), 
	DEVICE_MANUFACTURER_HDS("HDS"), 
	DEVICE_MANUFACTURER_NETAPP("NETAPP"), 
	;
	private final String value;
    

    private DeviceManufacturerEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    

    public static DeviceManufacturerEnum fromString(String value ) {
		if (value != null) {
			for (DeviceManufacturerEnum c : DeviceManufacturerEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
