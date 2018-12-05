package com.git.cloud.workflow.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class TemplateTypePo extends BaseBO{

	private static final long serialVersionUID = 8444931307627214210L;
	
	private String typeId;
	private String typeName;
	private String parentId;
	private int	   typeLevel;
	private String desc;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getTypeLevel() {
		return typeLevel;
	}
	public void setTypeLevel(int typeLevel) {
		this.typeLevel = typeLevel;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
