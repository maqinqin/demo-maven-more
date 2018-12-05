package com.git.cloud.sys.model.po;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 组织机构
 * @ClassName: SysOrganizationPo
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysOrganizationPo extends BaseBO {
	private static final long serialVersionUID = 3277864114259230979L;
	private String id;
	private String orgId;
	private String orgName;
	private String remark;
	private SysOrganizationPo parent;
	private String isActive;

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public SysOrganizationPo getParent() {
		return parent;
	}

	public void setParent(SysOrganizationPo parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
