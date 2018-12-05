package com.git.cloud.resmgt.common.model.po;

public class OpenstackResPo {
	
	private String datcenterId;			//数据中心ID
	private String datacenterCname;		//数据中心name
	private String vmSmId;				//虚机服务器id
	private String serverName;			//虚机服务器name
	private String id;					//存储卷id
	private String volumeType;			//存储卷name
	
	public OpenstackResPo(String datcenterId, String datacenterCname, String vmSmId, String serverName, String id,
			String volumeType) {
		super();
		this.datcenterId = datcenterId;
		this.datacenterCname = datacenterCname;
		this.vmSmId = vmSmId;
		this.serverName = serverName;
		this.id = id;
		this.volumeType = volumeType;
	}
	public String getDatcenterId() {
		return datcenterId;
	}
	public void setDatcenterId(String datcenterId) {
		this.datcenterId = datcenterId;
	}
	public String getDatacenterCname() {
		return datacenterCname;
	}
	public void setDatacenterCname(String datacenterCname) {
		this.datacenterCname = datacenterCname;
	}
	public String getVmSmId() {
		return vmSmId;
	}
	public void setVmSmId(String vmSmId) {
		this.vmSmId = vmSmId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getId() {
		return id;
	}
	public void setVolumeId(String id) {
		this.id = id;
	}
	public String getVolumeType() {
		return volumeType;
	}
	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

}
