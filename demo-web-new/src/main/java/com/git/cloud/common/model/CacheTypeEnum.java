package com.git.cloud.common.model;

public enum CacheTypeEnum {
	
	SYSTEM_PARAMETER("systemParameter");
	
	private final String value;

	public String getValue() {
		return value;
	}
	private CacheTypeEnum(String value) {
		this.value = value;
	}
	
	public static CacheTypeEnum fromString(String value ) {
		if (value != null) {
			for (CacheTypeEnum cacheTypeEnum : CacheTypeEnum.values()) {
				if (value.equalsIgnoreCase(cacheTypeEnum.value)) {
					return cacheTypeEnum;
				}
			}
		}
		return null;
	}
}
