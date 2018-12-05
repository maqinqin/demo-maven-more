package com.git.cloud.sys.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
  * @ClassName: SysRoleVo
  * @Description: TODO
  * @author guojianjun
  * @date 2014-12-17 下午2:58:53
  *
  */
public class SysRoleVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = -5908461469547837129L;
	private String roleId;
	private String roleName;
	private String remark;
	private String isActive;
	private String roleMenus;		//角色菜单关联数据
	
	public SysRoleVo(){
	}
	public SysRoleVo(String roleId){
		this.roleId = roleId;
	}
	public SysRoleVo(String roleId,String roleName,String remark,String isActive,String roleMenus){
		this.roleId = roleId;
		this.roleName = roleName;
		this.remark = remark;
		this.isActive = isActive;
		this.roleMenus = roleMenus;
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
	public String getRoleMenus() {
		return roleMenus;
	}
	public void setRoleMenus(String roleMenus) {
		this.roleMenus = roleMenus;
	}

}
