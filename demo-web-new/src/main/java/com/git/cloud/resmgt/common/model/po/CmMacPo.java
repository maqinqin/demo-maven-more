package com.git.cloud.resmgt.common.model.po;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 基于 Mac 地址保存相关的裸机信息，如机器序列号，临时IP地址等
 * 
 * @author Shiguang
 *
 */
@XmlType(name="cmMacPo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CmMacPo extends BaseBO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7199382678040433087L;
	private String id;
	private String macAddr;
	private String serialNo;
	private String tempIP;
	private String adminIP;
	private String istState;
	private String profile ;
	private String instanceId ;
	private String wfInstanceId ;
	private String wfModelId ;
	

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTempIP() {
		return tempIP;
	}

	public void setTempIP(String tempIP) {
		this.tempIP = tempIP;
	}

	public String getAdminIP() {
		return adminIP;
	}

	public void setAdminIP(String adminIP) {
		this.adminIP = adminIP;
	}

	public String getIstState() {
		return istState;
	}

	public void setIstState(String istState) {
		this.istState = istState;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getWfInstanceId() {
		return wfInstanceId;
	}

	public void setWfInstanceId(String wfInstanceId) {
		this.wfInstanceId = wfInstanceId;
	}

	public String getWfModelId() {
		return wfModelId;
	}

	public void setWfModelId(String wfModelId) {
		this.wfModelId = wfModelId;
	}

}
