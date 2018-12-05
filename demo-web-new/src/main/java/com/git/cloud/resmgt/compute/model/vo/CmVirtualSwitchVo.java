package com.git.cloud.resmgt.compute.model.vo;

public class CmVirtualSwitchVo {
	
	private String	switchId;	//交换机id
	private String	hostId;		//物理机id
	private String	switchName; //交换机名称
	private String	portGroupId;//端口组id
	private String	networkTag;	//网络标签
	private Integer	vlanId;		//vlanid
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
	
	
}
