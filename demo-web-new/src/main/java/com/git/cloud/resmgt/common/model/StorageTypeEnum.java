package com.git.cloud.resmgt.common.model;

public enum StorageTypeEnum {

	STORAGE_TYPE_SAN("SAN"),//SAN存储
	STORAGE_TYPE_NAS("NAS"),//NAS存储
	;

    private final String value;
    
    private StorageTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public static StorageTypeEnum fromString(String value ) {
		if (value != null) {
			for (StorageTypeEnum c : StorageTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
