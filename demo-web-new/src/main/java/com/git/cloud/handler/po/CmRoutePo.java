package com.git.cloud.handler.po;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 路由信息表，用于配置虚拟机的路由
 * @author zhuzhaoyong
 *
 */
public class CmRoutePo extends BaseBO {

	private static final long serialVersionUID = 1L;
	String id;
	String datacenterId;
	String name;
	String ip;
	String mask;
	String gateway;
	String remark;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
