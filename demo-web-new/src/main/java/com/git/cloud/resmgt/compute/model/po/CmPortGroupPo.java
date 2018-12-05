package com.git.cloud.resmgt.compute.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.network.model.vo.RmNwIpAddressVo;

public class CmPortGroupPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	switchId;	//交换机id
	private String	portGroupId;	//端口组id
	private String	networkTag;	//网络标签
	private Integer	vlanId;	//vlanid
	private String isActive;
	private String hostId ;
	private String vmNum;
	private String dvcSwitchHostId;
	private String stdSwitchHostId;
	private List<RmNwIpAddressVo> rmNwIpAddressVoList;
	
	

	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}


	public List<RmNwIpAddressVo> getRmNwIpAddressVoList() {
		return rmNwIpAddressVoList;
	}


	public void setRmNwIpAddressVoList(List<RmNwIpAddressVo> rmNwIpAddressVoList) {
		this.rmNwIpAddressVoList = rmNwIpAddressVoList;
	}


	public String getSwitchId() {
		return switchId;
	}


	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}


	public String getPortGroupId() {
		return portGroupId;
	}


	public void setPortGroupId(String portGroupId) {
		this.portGroupId = portGroupId;
	}


	public String getNetworkTag() {
		return networkTag;
	}


	public void setNetworkTag(String networkTag) {
		this.networkTag = networkTag;
	}


	public Integer getVlanId() {
		return vlanId;
	}


	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
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


	public String getVmNum() {
		return vmNum;
	}


	public void setVmNum(String vmNum) {
		this.vmNum = vmNum;
	}


	public String getDvcSwitchHostId() {
		return dvcSwitchHostId;
	}


	public void setDvcSwitchHostId(String dvcSwitchHostId) {
		this.dvcSwitchHostId = dvcSwitchHostId;
	}


	public String getStdSwitchHostId() {
		return stdSwitchHostId;
	}


	public void setStdSwitchHostId(String stdSwitchHostId) {
		this.stdSwitchHostId = stdSwitchHostId;
	}
	
}
