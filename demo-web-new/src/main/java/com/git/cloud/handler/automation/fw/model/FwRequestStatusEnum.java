package com.git.cloud.handler.automation.fw.model;
/**
 * 防火墙状态
 * @author Administrator
 *
 */
public enum FwRequestStatusEnum {
	/**
	 * 已开通		
	 */
	OPENED("已开通"),
	/**
	 * 开通中
	 */
	OPENING("开通中"),
	/**
	 * 已回收
	 */
	CLOSED("已回收"),
	/**
	 * 回收中
	 */
	CLOSING("回收中"),
	/**
	 * 已作废
	 */
	DELETE("已作废");
	private final String value;

    private FwRequestStatusEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static FwRequestStatusEnum fromString(String value ) {
		if (value != null) {
			for (FwRequestStatusEnum c : FwRequestStatusEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
