package com.git.cloud.iaas.openstack.model;

import com.git.cloud.iaas.enums.ResultEnum;

public class TenantException  extends RuntimeException implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7873168678431686624L;
	
	private Integer code;
	public TenantException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}
	public TenantException(ResultEnum resultEnum, Throwable cause) {
		super(resultEnum.getMessage(),cause);
		this.code = resultEnum.getCode();
	}

	public TenantException(ResultEnum resultEnum, String message) {
		super(message);
		this.code = resultEnum.getCode();
	}
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	

}
