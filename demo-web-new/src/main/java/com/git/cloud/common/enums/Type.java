package com.git.cloud.common.enums;

public enum Type {
	/**< 提示 */
	TIP("TIP"),
	/**< 警报 */
	WARNING("WARNING"),
	/**< 异常 */
	ERROR("ERROR")
	;
    
    private final String value;

    private Type(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static Type fromString(String value ) {
		if (value != null) {
			for (Type c : Type.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
