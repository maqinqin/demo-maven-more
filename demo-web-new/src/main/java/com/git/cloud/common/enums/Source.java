package com.git.cloud.common.enums;

public enum Source {
	/**< 自动识别 */
	AUTORECOGNITION("AUTO"), 
	/**< 手动获取 */
	MANUALOPERATION("MANUAL"),
	/**< 从vc抓取*/
	GRABFROMVC("FROMVC")			
	;
    
    private final String value;

    private Source(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static Source fromString(String value ) {
		if (value != null) {
			for (Source c : Source.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
