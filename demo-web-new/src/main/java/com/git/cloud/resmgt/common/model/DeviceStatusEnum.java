package com.git.cloud.resmgt.common.model;

public enum DeviceStatusEnum {

	DEVICE_STATUS_BUILDING("BUILDING"), 	// 构建中
	DEVICE_STATUS_CHANGING("CHANGING"), 	// 扩容中
	DEVICE_STATUS_RECYCLEING("RECYCLEING"), // 回收中
	DEVICE_STATUS_ONLINE("ONLINE"), 		// 已上线
	DEVICE_STATUS_OFFLINE("OFFLINE")		// 已下线
	;

	private final String value;

	private DeviceStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static DeviceStatusEnum fromString(String value) {
		if (value != null) {
			for (DeviceStatusEnum c : DeviceStatusEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}

}
