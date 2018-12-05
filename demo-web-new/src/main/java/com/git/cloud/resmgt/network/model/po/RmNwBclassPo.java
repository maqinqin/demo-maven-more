package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwBclassPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwBclassPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String bclassId;
	private String bclassName;
	private String subnetmask;
	private String resPoolId;
	private String scope;
	private String isActive;
	private String remark;
	private String datacenterId;
//	private String createUser;
//	private Timestamp createTime;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmNwBclassPo() {
	}

	/** full constructor */
	public RmNwBclassPo(String bclassId, String bclassName, String subnetmask,
			String resPoolId, String scope, String isActive, String remark
//			,String createUser, Timestamp createTime, String updateUser,Timestamp updateTime
			) {
		super();
		this.bclassId = bclassId;
		this.bclassName = bclassName;
		this.subnetmask = subnetmask;
		this.resPoolId = resPoolId;
		this.scope = scope;
		this.isActive = isActive;
		this.remark = remark;
//		this.createUser = createUser;
//		this.createTime = createTime;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}

	// Property accessors

	public String getBclassId() {
		return bclassId;
	}

	public void setBclassId(String bclassId) {
		this.bclassId = bclassId;
	}

	public String getBclassName() {
		return bclassName;
	}

	public void setBclassName(String bclassName) {
		this.bclassName = bclassName;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

}
