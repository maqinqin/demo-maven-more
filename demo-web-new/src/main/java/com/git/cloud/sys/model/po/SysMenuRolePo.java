package com.git.cloud.sys.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class SysMenuRolePo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 4138103720486708187L;
	private String roleId;
	private String menuId;
	
	public SysMenuRolePo(){
	}
	public SysMenuRolePo(String roleId,String menuId){
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String getBizId() {
		return null;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
