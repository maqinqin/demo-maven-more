package com.git.cloud.request.model;

/**
 * @ClassName:SrTypeCodeEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-9 下午3:48:20
 */
public enum SrTypeCodeEnum {

	SR_TYPE_FOLDER("F"),
	SR_TYPE_LEAF("L");
	
	private final String value;
	
	private SrTypeCodeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
