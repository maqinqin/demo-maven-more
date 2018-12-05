package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmHostResPoolPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3657155742087231181L;


	// Fields

	private String id;
	private String platformType;
	private String serviceType;
	private String secureAreaType;
	private String secureLayer;
	private String availableZoneId;
	private String hostTypeId;
	// Constructors

	/** default constructor */
	public RmHostResPoolPo() {
	}
	
	
	/** minimal constructor */
	public RmHostResPoolPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmHostResPoolPo(String id, String platformType, String serviceType,
			String secureAreaType, String secureLayer) {
		this.id = id;
		this.platformType = platformType;
		this.serviceType = serviceType;
		this.secureAreaType = secureAreaType;
		this.secureLayer = secureLayer;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformType() {
		return this.platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSecureAreaType() {
		return this.secureAreaType;
	}

	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}

	public String getSecureLayer() {
		return this.secureLayer;
	}

	public void setSecureLayer(String secureLayer) {
		this.secureLayer = secureLayer;
	}
	
	
	public String getAvailableZoneId() {
		return availableZoneId;
	}


	public void setAvailableZoneId(String availableZoneId) {
		this.availableZoneId = availableZoneId;
	}


	public String getHostTypeId() {
		return hostTypeId;
	}


	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


}
