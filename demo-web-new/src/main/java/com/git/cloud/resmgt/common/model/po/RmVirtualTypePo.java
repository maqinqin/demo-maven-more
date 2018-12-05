package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmVirtualTypePo.java 
 * @Package 	com.git.cloud.resmgt.common.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVirtualTypePo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String virtualTypeId;
	private String virtualTypeCode;
	private String virtualTypeName;
	private String platformId;
	private String isActive;
//	以下继承自basebo
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmVirtualTypePo() {
	}

	/** full constructor */
  
	public RmVirtualTypePo(String virtualTypeId, String virtualTypeCode,
			String virtualTypeName, String platformId, String isActive
//			,String createUser, String createTime, String updateUser,String updateTime
			) {
		super();
		this.virtualTypeId = virtualTypeId;
		this.virtualTypeCode = virtualTypeCode;
		this.virtualTypeName = virtualTypeName;
		this.platformId = platformId;
		this.isActive = isActive;
//		this.createUser = createUser;
//		this.createTime = createTime;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}

	// Property accessors

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
	}

	public String getVirtualTypeName() {
		return virtualTypeName;
	}

	public void setVirtualTypeName(String virtualTypeName) {
		this.virtualTypeName = virtualTypeName;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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
