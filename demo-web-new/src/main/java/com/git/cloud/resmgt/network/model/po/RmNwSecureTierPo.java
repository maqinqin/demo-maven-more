package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwSecureTierPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwSecureTierPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String secureTierId;
	private String secureTierName;
	private String secureAreaId;
	private String isActive;
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmNwSecureTierPo() {
	}

	/** full constructor */

	public RmNwSecureTierPo(String secureTierId, String secureTierName,
		String secureAreaId, String isActive
//		, String createUser, Timestamp createTime,String updateUser, Timestamp updateTime
			) {
	super();
	this.secureTierId = secureTierId;
	this.secureTierName = secureTierName;
	this.secureAreaId = secureAreaId;
	this.isActive = isActive;
//	this.createUser = createUser;
//	this.createTime = createTime;
//	this.updateUser = updateUser;
//	this.updateTime = updateTime;
}

	// Property accessors

	public String getSecureTierId() {
		return secureTierId;
	}

	public void setSecureTierId(String secureTierId) {
		this.secureTierId = secureTierId;
	}

	public String getSecureTierName() {
		return secureTierName;
	}

	public void setSecureTierName(String secureTierName) {
		this.secureTierName = secureTierName;
	}

	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
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
