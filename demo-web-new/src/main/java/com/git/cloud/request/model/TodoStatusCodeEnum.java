package com.git.cloud.request.model;

/**
 * @ClassName:TodoStatusCodeEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-13 下午3:54:58
 */
public enum TodoStatusCodeEnum {

	TODO_STATUS_WAIT_DEAL("0"),
	TODO_STATUS_DEALING("1"),
	TODO_STATUS_DEAL("2");
	
	private final String value;
	
	private TodoStatusCodeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
