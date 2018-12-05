package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class CloudFileTemplate extends BaseBO implements Serializable{

	//流程设计器表内容
	private String templateId;
	
	private String templateName;
	
	private String typeId;
	
	private Date createDate;
	
	private String jsonData;
	
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
