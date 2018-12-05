package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-17
 */
public class CloudServiceFlowRefPo extends BaseBO implements Serializable {

	// Fields

	private String serviceFlowId;
	private String flowId;
	private String serviceId;
	private String operModelType;
	private String isActive;
	private String templateId;

	// Constructors

	/** default constructor */
	public CloudServiceFlowRefPo() {
	}

	/** minimal constructor */
	public CloudServiceFlowRefPo(String serviceFlowId) {
		this.serviceFlowId = serviceFlowId;
	}

	/** full constructor */
	public CloudServiceFlowRefPo(String serviceFlowId, String flowId,
			String serviceId, String operModelType, String isActive) {
		this.serviceFlowId = serviceFlowId;
		this.flowId = flowId;
		this.serviceId = serviceId;
		this.operModelType = operModelType;
		this.isActive = isActive;
	}

	// Property accessors

	public String getServiceFlowId() {
		return this.serviceFlowId;
	}

	public void setServiceFlowId(String serviceFlowId) {
		this.serviceFlowId = serviceFlowId;
	}

	public String getFlowId() {
		return this.flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getOperModelType() {
		return this.operModelType;
	}

	public void setOperModelType(String operModelType) {
		this.operModelType = operModelType;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
