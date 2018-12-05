package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class VirtualRouterPo extends BaseBO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String virtualRouterId;
	private String virtualRouterName;
	private String externalNetworkId;
	private String vpcId;
	private String isActive;
	private String remark;
	private String snatStatus;
	private String projectId;
	private String iaasUuid;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSnatStatus() {
		return snatStatus;
	}



	public void setSnatStatus(String snatStatus) {
		this.snatStatus = snatStatus;
	}



	public String getVirtualRouterId() {
		return virtualRouterId;
	}



	public void setVirtualRouterId(String virtualRouterId) {
		this.virtualRouterId = virtualRouterId;
	}



	public String getVirtualRouterName() {
		return virtualRouterName;
	}



	public void setVirtualRouterName(String virtualRouterName) {
		this.virtualRouterName = virtualRouterName;
	}



	public String getExternalNetworkId() {
		return externalNetworkId;
	}



	public void setExternalNetworkId(String externalNetworkId) {
		this.externalNetworkId = externalNetworkId;
	}



	public String getVpcId() {
		return vpcId;
	}



	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
	}



	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
