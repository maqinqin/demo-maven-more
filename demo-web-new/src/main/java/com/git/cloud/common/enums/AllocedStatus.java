package com.git.cloud.common.enums;

public enum AllocedStatus {
	
	NOT_ALLOC("NA"),	// 空闲
	ALLOCTODEV("A2DV"),	// 分配
	HOLD("A2PH")		// 占位
	;
    
    private final String value;

    private AllocedStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static AllocedStatus fromString(String value ) {
		if (value != null) {
			for (AllocedStatus c : AllocedStatus.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }

}
