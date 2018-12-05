package com.git.cloud.resmgt.common.model;

public enum DatastoreTypeEnum {

	DATASTORE_TYPE_LOCAL_DISK("LOCAL_DISK"), // 本地磁盘
	DATASTORE_TYPE_NAS_DATASTORE("NAS_DATASTORE"), // 共享NAS
	;

    private final String value;
    

    private DatastoreTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    

    public static DatastoreTypeEnum fromString(String value ) {
		if (value != null) {
			for (DatastoreTypeEnum c : DatastoreTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
