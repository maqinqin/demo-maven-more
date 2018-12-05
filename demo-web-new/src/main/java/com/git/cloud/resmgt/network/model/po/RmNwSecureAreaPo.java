package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwSecureAreaPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwSecureAreaPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;	
	private String secureAreaId;
	private String secureAreaName;
	private String isActive;
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public RmNwSecureAreaPo() {
	}


	/** full constructor */

	public RmNwSecureAreaPo(String secureAreaId, String secureAreaName,
		String isActive
//		, String createUser, Timestamp createTime,String updateUser, Timestamp updateTime
			) {
	super();
	this.secureAreaId = secureAreaId;
	this.secureAreaName = secureAreaName;
	this.isActive = isActive;
//	this.createUser = createUser;
//	this.createTime = createTime;
//	this.updateUser = updateUser;
//	this.updateTime = updateTime;
}

	// Property accessors

	public String getSecureAreaId() {
		return secureAreaId;
	}


	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}


	public String getSecureAreaName() {
		return secureAreaName;
	}


	public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
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
