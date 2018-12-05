package com.git.cloud.common.model;

/**
 * 使用状态
 * @author liangshuang
 * @date 2014-9-23 下午3:03:53
 * @version v1.0
 *
 */
public enum UseStatusEnum {

	START("Y"),//启用
	STOP("N");//停用
	
	private final String value;

	public String getValue() {
		return value;
	}
	
	private UseStatusEnum(String value){
		this.value=value;
	}
	
}
