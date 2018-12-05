package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwUsePo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		make
 * @date 		2015-3-6下午14:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwUseRelPo extends BaseBO implements java.io.Serializable{

	// Fields
	private static final long serialVersionUID = 11L;
	private String useRelId;//IP用途关联Id
	private String useRelCode;//IP用途编码
	private String useId;//IP用途Id
	private String platformId;//平台Id
	private String virtualTypeId;//虚机类型Id
	private String hostTypeId;//主机类型Id
	private String isActive;//是否激活

	// Constructors

	/** default constructor */
	public RmNwUseRelPo() {
	}
	
	/** minimal constructor */
	public RmNwUseRelPo(String useRelId) {
		this.useRelId = useRelId;
	}
	
	/** full constructor */
	public RmNwUseRelPo(String useRelId, 
			String useRelCode,
			String useId, 
			String platformId, 
			String virtualTypeId,
			String hostTypeId, 
			String isActive) {
		super();
		this.useRelId = useRelId;
		this.useRelCode = useRelCode;
		this.useId = useId;
		this.platformId = platformId;
		this.virtualTypeId = virtualTypeId;
		this.hostTypeId = hostTypeId;
		this.isActive = isActive;
	}

	public String getUseRelId() {
		return useRelId;
	}

	public void setUseRelId(String useRelId) {
		this.useRelId = useRelId;
	}

	public String getUseRelCode() {
		return useRelCode;
	}

	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}

	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
