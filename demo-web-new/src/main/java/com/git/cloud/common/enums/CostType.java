package com.git.cloud.common.enums;

public enum CostType {
	/**< 规格 */
	FLAVOR("flavor"),
	/**< 磁盘 */
	DISK("disk"),
	/**< 浮动ip */
	FLOATIP("floatIp")
	;
    
    private final String value;

    private CostType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static CostType fromString(String value ) {
		if (value != null) {
			for (CostType c : CostType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
