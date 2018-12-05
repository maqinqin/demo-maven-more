package com.git.cloud.shiro.model;

/**
 * @ClassName:SrStatusCodeEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-9 下午4:28:01
 */
public enum CertificateCheckEnum {

	NOT_FOUNT_CERTIFICATE("1"),
	TIME_LIMIT_CERTIFICATE("2");
	
	private final String value;
	
	private CertificateCheckEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
