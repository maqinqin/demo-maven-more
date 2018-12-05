package com.git.cloud.policy.model.vo;

import com.git.cloud.policy.model.po.RmVmRulesPo;

/**
 * 
 * @Description
 * @author yangzhenhai
 * @version v1.0 2014-9-18
 */
public class RmVmRulesVo extends RmVmRulesPo {

	private String sortObjectName;// 排序对象
	private String sortTypeName;// 排序方式
	

	public RmVmRulesVo() {
		super();
	}

	public RmVmRulesVo(String rulesId, String sortObject, String sortType,
			String isActive,String sortObjectName, String sortTypeName) {
		super(rulesId, sortObject, sortType, isActive);
		this.sortObjectName = sortObjectName;
		this.sortTypeName = sortTypeName;
	}

	public String getSortObjectName() {
		return sortObjectName;
	}

	public void setSortObjectName(String sortObjectName) {
		this.sortObjectName = sortObjectName;
	}

	public String getSortTypeName() {
		return sortTypeName;
	}

	public void setSortTypeName(String sortTypeName) {
		this.sortTypeName = sortTypeName;
	}

}
