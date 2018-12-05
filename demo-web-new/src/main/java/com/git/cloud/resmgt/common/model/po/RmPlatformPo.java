package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmPlatformPo.java 
 * @Package 	com.git.cloud.resmgt.common.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmPlatformPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String platformId;
	private String platformCode;
	private String platformName;
	private String isActive;
//	以下从basebo继承
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmPlatformPo() {
	}

	/** full constructor */
	public RmPlatformPo(String platformId, String platformCode,
			String platformName, String isActive 
//			,String createUser,Timestamp createTime, String updateUser, Timestamp updateTime
			) {
		super();
		this.platformId = platformId;
		this.platformCode = platformCode;
		this.platformName = platformName;
		this.isActive = isActive;
//		this.createUser = createUser;
//		this.createTime = createTime;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}

	// Property accessors

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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
