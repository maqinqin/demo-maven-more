package com.git.cloud.handler.automation.fw.model;

public enum ExecuteTypeEnum {
	/**
	 * 定时执行
	 */
	TIMING("TIMMING"),	
	/**
	 * 立即执行
	 */
	IMMEDIATE("IMMEDIATE"),	
	
	;
    
    private final String value;

    private ExecuteTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static ExecuteTypeEnum fromString(String value ) {
		if (value != null) {
			for (ExecuteTypeEnum c : ExecuteTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }

}
