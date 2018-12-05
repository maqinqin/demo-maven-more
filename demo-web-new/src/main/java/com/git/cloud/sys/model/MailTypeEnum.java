package com.git.cloud.sys.model;

public enum MailTypeEnum {
	/**
	 * 服务申请结束邮件
	 */
	MAIL_REQUEST_FINISH("MAIL_REQUEST_FINISH");
	
    private final String value;
	
	private MailTypeEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static MailTypeEnum fromString(String value ) {
		if (value != null) {
			for (MailTypeEnum c : MailTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
	}
}
