package com.git.cloud.request.model;

/**
 * @ClassName:TodoStatusCodeEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-13 下午3:54:58
 */
public enum DriveWfTypeEnum {

	DRIVE_WF_SUBMIT("0"),
	DRIVE_WF_AGREE("1"),
	DRIVE_WF_DISAGREE("2");
	
	private final String value;
	
	private DriveWfTypeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
