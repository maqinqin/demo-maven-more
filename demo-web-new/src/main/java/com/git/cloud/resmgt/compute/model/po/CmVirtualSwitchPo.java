package com.git.cloud.resmgt.compute.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

public class CmVirtualSwitchPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String	switchId;	//交换机id
	private String	hostId;		//物理机id
	private String	switchName; //交换机名称
	private String 	isActive;
	private List<CmPortGroupPo>	cmPortGroupPoList;	//端口组实体类
	
	public String getSwitchId() {
		return switchId;
	}


	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}


	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}


	public String getSwitchName() {
		return switchName;
	}


	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}


	public List<CmPortGroupPo> getCmPortGroupPoList() {
		return cmPortGroupPoList;
	}


	public void setCmPortGroupPoList(List<CmPortGroupPo> cmPortGroupPoList) {
		this.cmPortGroupPoList = cmPortGroupPoList;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
