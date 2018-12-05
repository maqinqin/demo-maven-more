package com.git.cloud.resmgt.openstack.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwVfwPolicyVo extends BaseBO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vfwPolicyId;		/*虚拟防火墙策略ID*/
	private String projectId;		/*所属projectID*/
	private String vfwPolicyName;	/*虚拟防火墙策略名称*/
	private String remark;
	private String isActive;
	private String projectName;
	private String vfwName;
	private String iaasUuid;

     public String getIaasUuid() {
          return iaasUuid;
     }
     public void setIaasUuid(String iaasUuid) {
          this.iaasUuid = iaasUuid;
     }

	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVfwName() {
		return vfwName;
	}

	public void setVfwName(String vfwName) {
		this.vfwName = vfwName;
	}

	public String getVfwPolicyId() {
		return vfwPolicyId;
	}

	public void setVfwPolicyId(String vfwPolicyId) {
		this.vfwPolicyId = vfwPolicyId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVfwPolicyName() {
		return vfwPolicyName;
	}

	public void setVfwPolicyName(String vfwPolicyName) {
		this.vfwPolicyName = vfwPolicyName;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
