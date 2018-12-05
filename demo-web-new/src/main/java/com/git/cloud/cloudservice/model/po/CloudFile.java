package com.git.cloud.cloudservice.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;

public class CloudFile extends BaseBO implements java.io.Serializable{

	//云服务定义表
	private CloudServicePo cloudServiceList;
	
	//镜像表
	private CloudImage cloudImage;
	
	//云服务属性表
	private List<CloudServiceAttrPo> cloudServiceAttrList;
	
	//云服务属性的选项表 （下拉框）
	private List<CloudServiceAttrSelPo> cloudServiceAttrSelList;
	
	//云服务属性流程关系表（操作模型）
	private List<CloudServiceFlowRefPo> cloudServiceFlowRefList;
	
	//密码表
	private List<CmPasswordPo> cmPasswordList;
	
	private List<CloudFileTemplate> cloudFileTemplateList;
	

	


	public CloudServicePo getCloudServiceList() {
		return cloudServiceList;
	}





	public void setCloudServiceList(CloudServicePo cloudServiceList) {
		this.cloudServiceList = cloudServiceList;
	}





	public CloudImage getCloudImage() {
		return cloudImage;
	}





	public void setCloudImage(CloudImage cloudImage) {
		this.cloudImage = cloudImage;
	}





	public List<CloudServiceAttrPo> getCloudServiceAttrList() {
		return cloudServiceAttrList;
	}





	public void setCloudServiceAttrList(List<CloudServiceAttrPo> cloudServiceAttrList) {
		this.cloudServiceAttrList = cloudServiceAttrList;
	}





	public List<CloudServiceAttrSelPo> getCloudServiceAttrSelList() {
		return cloudServiceAttrSelList;
	}





	public void setCloudServiceAttrSelList(List<CloudServiceAttrSelPo> cloudServiceAttrSelList) {
		this.cloudServiceAttrSelList = cloudServiceAttrSelList;
	}





	public List<CloudServiceFlowRefPo> getCloudServiceFlowRefList() {
		return cloudServiceFlowRefList;
	}





	public void setCloudServiceFlowRefList(List<CloudServiceFlowRefPo> cloudServiceFlowRefList) {
		this.cloudServiceFlowRefList = cloudServiceFlowRefList;
	}





	public List<CmPasswordPo> getCmPasswordList() {
		return cmPasswordList;
	}





	public void setCmPasswordList(List<CmPasswordPo> cmPasswordList) {
		this.cmPasswordList = cmPasswordList;
	}





	public List<CloudFileTemplate> getCloudFileTemplateList() {
		return cloudFileTemplateList;
	}





	public void setCloudFileTemplateList(List<CloudFileTemplate> cloudFileTemplateList) {
		this.cloudFileTemplateList = cloudFileTemplateList;
	}





	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
