package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmSeatPo extends BaseBO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3738422943075825244L;
	private String id;
	private String seatCode;
	private String seatName;
	private String parentCode;
	private String isActive;
	private String description;
	private String datacenterId;
	private String uheight;
	private String hostId;
	private String deviceName;

	// Constructors

	/** default constructor */
	public CmSeatPo(){
	}

	/** minimal constructor */
	public CmSeatPo(String id){
		this.id = id;
	}

	/** full constructor */
	public CmSeatPo(String id, String seatCode, String seatName, String parentCode, String isActive, String description){
		this.id = id;
		this.seatCode = seatCode;
		this.seatName = seatName;
		this.parentCode = parentCode;
		this.isActive = isActive;
		this.description = description;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeatCode() {
		return this.seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getSeatName() {
		return this.seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUheight() {
		return uheight;
	}

	public void setUheight(String uheight) {
		this.uheight = uheight;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
