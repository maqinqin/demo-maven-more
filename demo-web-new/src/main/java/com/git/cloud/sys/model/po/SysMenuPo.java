package com.git.cloud.sys.model.po;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 菜单
 * @ClassName: SysMenuPo
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysMenuPo extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;
	private String menuName;
	private String menuCode;
	private String menuDesc;
	private String menuUrl;
	private String parentId;
	private SysMenuPo parent;
	private String orderId;
	private String resourceType;
	private String imageUrl;
	private String lableColor;
	private String isActive;
	private String menuId;
	private String roleId;
	private Boolean checked=false;
	
	@Override
	public String getBizId() {
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public SysMenuPo getParent() {
		return parent;
	}

	public void setParent(SysMenuPo parent) {
		this.parent = parent;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLableColor() {
		if(null==lableColor||lableColor.length()==0)
			lableColor = "0x000000";
		return lableColor;
	}

	public void setLableColor(String lableColor) {
		this.lableColor = lableColor;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	
}
