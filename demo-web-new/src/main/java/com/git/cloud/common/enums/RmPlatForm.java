package com.git.cloud.common.enums;

public enum RmPlatForm {
	X86("X"),		/**< x86 */
	POWER("P"),		/**< power */
	HP("H"),	    /**< 惠普 */
	OPENSTACKX86("O"),
	POWERVC("PV")
	;
    
    private final String value;

    private RmPlatForm(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RmPlatForm fromString(String value ) {
		if (value != null) {
			for (RmPlatForm c : RmPlatForm.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }
}
