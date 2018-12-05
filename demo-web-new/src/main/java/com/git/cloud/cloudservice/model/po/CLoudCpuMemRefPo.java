/**
 * @Title:CLoudCpuMemRefPo.java
 * @Package:com.git.cloud.cloudservice.model.po
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-11-17 下午3:26:16
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:CLoudCpuMemRefPo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-11-17 下午3:26:16
 *
 *
 */
public class CLoudCpuMemRefPo  extends BaseBO implements Serializable{
	
	private String cpuMemRefId;
	private String paramName;
	private String IsActive;
	private String paramType;
	private String parentId;
	private Integer paramValue;

	


	public CLoudCpuMemRefPo() {
		super();
	}


	public CLoudCpuMemRefPo(String cpuMemRefId, String paramName,
			String isActive, String paramType, String parentId,
			Integer paramValue) {
		super();
		this.cpuMemRefId = cpuMemRefId;
		this.paramName = paramName;
		IsActive = isActive;
		this.paramType = paramType;
		this.parentId = parentId;
		this.paramValue = paramValue;
	}


	/**
	 * @return the cpuMemRefId
	 */
	public String getCpuMemRefId() {
		return cpuMemRefId;
	}


	/**
	 * @param cpuMemRefId the cpuMemRefId to set
	 */
	public void setCpuMemRefId(String cpuMemRefId) {
		this.cpuMemRefId = cpuMemRefId;
	}


	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}


	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return IsActive;
	}


	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}


	/**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}


	/**
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}


	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the paramValue
	 */
	public Integer getParamValue() {
		return paramValue;
	}


	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(Integer paramValue) {
		this.paramValue = paramValue;
	}


	@Override
	public String getBizId() {
		return null;
	}

}
