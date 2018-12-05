package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmUseRelPo.java 
 * @Package 	com.git.cloud.resmgt.common.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmUseRelPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;	
	private String useRelId;
	private String useRelCode;
	private String useId;
	private String platformId;
	private String virtualTypeId;
	private String hostTypeId;
	private String isActive;
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmUseRelPo() {
	}



	/** full constructor */
	public RmUseRelPo(String useRelId, String useRelCode, String useId,
			String platformId, String virtualTypeId, String hostTypeId,
			String isActive
//			, String createUser, Timestamp createTime,String updateUser, Timestamp updateTime
			) {
		super();
		this.useRelId = useRelId;
		this.useRelCode = useRelCode;
		this.useId = useId;
		this.platformId = platformId;
		this.virtualTypeId = virtualTypeId;
		this.hostTypeId = hostTypeId;
		this.isActive = isActive;
//		this.createUser = createUser;
//		this.createTime = createTime;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}
	

	// Property accessors



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

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


}
