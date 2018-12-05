package com.git.cloud.common.enums;

public enum RmHostType {
	PHYSICAL("H"),  /**< 物理机 */
	VIRTUAL("V")    /**< 虚拟机 */
	;
    
    private final String value;

    private RmHostType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RmHostType fromString(String value ) {
		if (value != null) {
			for (RmHostType c : RmHostType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }
}
