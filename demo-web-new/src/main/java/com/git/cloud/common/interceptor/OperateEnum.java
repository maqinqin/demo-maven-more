package com.git.cloud.common.interceptor;

/**
 * @ClassName:OperateEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2015-01-16 下午3:54:58
 */
public enum OperateEnum {

	INSERT("INSERT"),
	UPDATE("UPDATE"),
	DELETE("DELETE"),
	SPECIAL("SPECIAL");
	
	private final String value;
	
	private OperateEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
