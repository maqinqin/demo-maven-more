package com.git.cloud.rest.model;

public enum EnvirmentSign {
	/**测试环境*/
	TEST("T"),
    /**生产环境*/
	PRODUCTION("P"),
	/**开发环境*/
	DEVELOPMENT("D"),
	;
    private final String value;

    private EnvirmentSign(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static EnvirmentSign fromString(String value ) {
		if (value != null) {
			for (EnvirmentSign c : EnvirmentSign.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }



}
