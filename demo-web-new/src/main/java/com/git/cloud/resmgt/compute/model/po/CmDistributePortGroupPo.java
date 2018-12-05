package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmDistributePortGroupPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String portGroupId;
	private String switchId;
	private String portGroupName;
	private String vlanId;
	private String isActive;
	private String datacenterId;
	
	
	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}



	public String getPortGroupId() {
		return portGroupId;
	}



	public void setPortGroupId(String portGroupId) {
		this.portGroupId = portGroupId;
	}



	public String getSwitchId() {
		return switchId;
	}



	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}



	public String getPortGroupName() {
		return portGroupName;
	}



	public void setPortGroupName(String portGroupName) {
		this.portGroupName = portGroupName;
	}



	public String getVlanId() {
		return vlanId;
	}



	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}



	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}



	@Override
	public String getBizId() {
		return null;
	}


}
