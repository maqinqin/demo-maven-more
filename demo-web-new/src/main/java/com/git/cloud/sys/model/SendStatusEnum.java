package com.git.cloud.sys.model;

public enum SendStatusEnum {
	/**
	 * 0未发送
	 */
	SEND_STATUS_UNSEND("0"),  
	/**
	 * 1发送成功
	 */
	SEND_STATUS_SENDSUCCESS("1"),
	/**
	 * 2发送失败
	 */
	SEND_STATUS_SENDFAIL("2"); 
	
    private final String value;
	
	private SendStatusEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static SendStatusEnum fromString(String value ) {
		if (value != null) {
			for (SendStatusEnum c : SendStatusEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
