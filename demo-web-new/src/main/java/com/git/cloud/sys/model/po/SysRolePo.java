package com.git.cloud.sys.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
  * @ClassName: SysRolePo
  * @Description: TODO
  * @author guojianjun
  * @date 2014-12-17 下午2:59:11
  *
  */
public class SysRolePo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = -5908461469547837129L;
	private String roleId;
	private String roleName;
	private String remark;
	private String isActive;
	
	public SysRolePo(){
	}
	public SysRolePo(String roleId){
		this.roleId = roleId;
	}
	public SysRolePo(String roleId,String roleName,String remark,String isActive){
		this.roleId = roleId;
		this.roleName = roleName;
		this.remark = remark;
		this.isActive = isActive;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
