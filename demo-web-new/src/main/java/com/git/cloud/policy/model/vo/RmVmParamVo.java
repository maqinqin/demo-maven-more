package com.git.cloud.policy.model.vo;

import com.git.cloud.policy.model.po.RmVmParamPo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-18
 */
public class RmVmParamVo extends RmVmParamPo {

	private String objectTypeName;//参数对象类型
	private String paramTypeName;//参数类别
	private String objectName;//参数对象
	
	public RmVmParamVo(String objectTypeName, String paramTypeName,String objectName) {
		super();
		this.objectTypeName = objectTypeName;
		this.paramTypeName = paramTypeName;
		this.objectName = objectName;
	}
	
	public RmVmParamVo() {
		super();
	}

	public RmVmParamVo(String paramId, String objectType, String objectId,
			String paramType, String value, String isActive,String objectTypeName, String paramTypeName,String objectName) {
		super(paramId, objectType, objectId, paramType, value, isActive);
		this.objectTypeName = objectTypeName;
		this.paramTypeName = paramTypeName;
		this.objectName = objectName;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public String getParamTypeName() {
		return paramTypeName;
	}

	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}


}
