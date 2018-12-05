package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmHostResPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1590536931134868288L;
    private String id;
    private String platformType;
    private String serviceType;
    private String secureAreaType;
    private String secureLayer;
	
    
    
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPlatformType() {
		return platformType;
	}



	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}



	public String getServiceType() {
		return serviceType;
	}



	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}



	public String getSecureAreaType() {
		return secureAreaType;
	}



	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}



	public String getSecureLayer() {
		return secureLayer;
	}



	public void setSecureLayer(String secureLayer) {
		this.secureLayer = secureLayer;
	}



	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
