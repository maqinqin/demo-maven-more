package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
/**
 * @Description 
 * @author 		 make
 * @version 	 v1.0  2015-3-6
 */
public class RmNwUseRelVo extends RmNwUseRelPo implements java.io.Serializable{
	private String platformName;
	private String virtualTypeName;
	private String hostTypeName;
	
	/** default constructor */
	public RmNwUseRelVo() {
	}

	/** minimal constructor */
	public RmNwUseRelVo(String useRelId) {
		super(useRelId);
	}
	
	/** full constructor */
	public RmNwUseRelVo(String useRelId, 
			String useRelCode,
			String useId, 
			String platformId, 
			String virtualTypeId,
			String hostTypeId, 
			String isActive) {
		super(useRelId, useRelCode, useId, platformId, virtualTypeId, hostTypeId, isActive);
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getVirtualTypeName() {
		return virtualTypeName;
	}

	public void setVirtualTypeName(String virtualTypeName) {
		this.virtualTypeName = virtualTypeName;
	}

	public String getHostTypeName() {
		return hostTypeName;
	}

	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
	}	
}
