package com.git.cloud.request.model;

/**
 * @ClassName:SrStatusCodeEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-9 下午4:28:01
 */
public enum SrStatusCodeEnum {

	REQUEST_WAIT_SUBMIT("REQUEST_WAIT_SUBMIT"),
	REQUEST_WAIT_APPROVE("REQUEST_WAIT_APPROVE"),
	REQUEST_APPROVING("REQUEST_APPROVING"),
	REQUEST_DELETE("REQUEST_DELETE"),
	REQUEST_WAIT_ASSIGN("REQUEST_WAIT_ASSIGN"),
	REQUEST_ASSIGN_SUCCESS("REQUEST_ASSIGN_SUCCESS"),
	REQUEST_ASSIGN_FAILURE("REQUEST_ASSIGN_FAILURE"),
	REQUEST_WAIT_OPERATE("REQUEST_WAIT_OPERATE"),
	REQUEST_OPERATING("REQUEST_OPERATING"),
	REQUEST_WAIT_CLOSE("REQUEST_WAIT_CLOSE"),
	REQUEST_CLOSED("REQUEST_CLOSED");
	
	private final String value;
	
	private SrStatusCodeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
