package com.git.cloud.policy.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmVmRulesPo.java 
 * @Package 	com.git.cloud.policy.model.po 
 * @author 		yangzhenhai
 * @date 		2014-9-11 下午4:29:58
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmRulesPo  extends BaseBO  implements java.io.Serializable{
	// Fields

	private String rulesId;//主键
	private String sortObject;//排序对象
	private String sortType;//排序方式
	private String isActive;//是否有效

	// Constructors

	/** default constructor */
	public RmVmRulesPo() {
	}

	/** full constructor */
	public RmVmRulesPo(String rulesId, String sortObject, String sortType,
			String isActive) {
		this.rulesId = rulesId;
		this.sortObject = sortObject;
		this.sortType = sortType;
		this.isActive = isActive;
	}

	// Property accessors

	public String getRulesId() {
		return this.rulesId;
	}

	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}

	public String getSortObject() {
		return this.sortObject;
	}

	public void setSortObject(String sortObject) {
		this.sortObject = sortObject;
	}

	public String getSortType() {
		return this.sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getIsActive() {
		return this.isActive;
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
