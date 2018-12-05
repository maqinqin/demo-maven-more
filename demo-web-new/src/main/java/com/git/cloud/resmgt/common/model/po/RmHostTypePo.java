package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmHostTypePo.java 
 * @Package 	com.git.cloud.resmgt.common.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmHostTypePo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String hostTypeId;
	private String hostTypeCode;
	private String hostTypeName;
	private String isActive;
//	以下继承自basebo
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmHostTypePo() {
	}


	/** full constructor */

	public RmHostTypePo(String hostTypeId, String hostTypeCode,
			String hostTypeName, String platformId, String virtualTypeId,
			String isActive
//			, String createUser, Timestamp createTime,String updateUser, Timestamp updateTime
			) {
		super();
		this.hostTypeId = hostTypeId;
		this.hostTypeCode = hostTypeCode;
		this.hostTypeName = hostTypeName;
		this.isActive = isActive;
//		this.createUser = createUser;
//		this.createTime = createTime;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}

	// Property accessors


	public String getHostTypeId() {
		return hostTypeId;
	}


	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}


	public String getHostTypeCode() {
		return hostTypeCode;
	}


	public void setHostTypeCode(String hostTypeCode) {
		this.hostTypeCode = hostTypeCode;
	}


	public String getHostTypeName() {
		return hostTypeName;
	}


	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
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
