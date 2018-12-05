package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class CmMacVo extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String macAddr;
	private String serialNo;
	private String tempIP;
	private String profile;
	private String istState;
	private String instanceId ;
	private String wfModelId ;
	

	public CmMacVo() {

	}

	public CmMacVo(String id, String seatId, String macAddr, String serialNo,
			String tempIP, String profile, String istState ,String instanceId ,String wfModelId) {
		this.id = id;
		this.macAddr = macAddr;
		this.serialNo = serialNo;
		this.tempIP = tempIP;
		this.profile = profile;
		this.istState = istState;
		this.instanceId = instanceId ;
		this.wfModelId = wfModelId ;
	}

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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getIstState() {
		return istState;
	}

	public void setIstState(String istState) {
		this.istState = istState;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getWfModelId() {
		return wfModelId;
	}

	public void setWfModelId(String wfModelId) {
		this.wfModelId = wfModelId;
	}
	

}
