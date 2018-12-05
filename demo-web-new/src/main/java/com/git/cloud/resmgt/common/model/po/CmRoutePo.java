package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmRoutePo extends BaseBO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String datacenterId;

	private String name2;
	private String ip;
	private String mask;
	private  String gateway;
	private String remark;
	private String isActive;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	
	
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public CmRoutePo(){
		super();
	}
	public CmRoutePo(String id,String datacenterId,String name2,String ip,
			String mask,String gateway,String remark,String isActive){
		super();
		this.id=id;
		this.datacenterId=datacenterId;
	
		this.name2=name2;
		this.ip=ip;
		this.mask=mask;
		this.gateway=gateway;
		this.remark=remark;
		this.isActive=isActive;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
