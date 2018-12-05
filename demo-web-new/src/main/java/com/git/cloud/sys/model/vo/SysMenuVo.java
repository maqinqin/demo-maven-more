package com.git.cloud.sys.model.vo;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.sys.model.po.SysMenuPo;

/**
 * 系统菜单VO对象;
 * 
 * @ClassName: SysMenuVo
 * @Description:
 * @author: wangdy
 * @date: 2015-4-14 上午10:42:42
 * 
 */
public class SysMenuVo extends BaseBO {

	private static final long serialVersionUID = 1L;
	private String id;
	private String menuName;
	private String menuUrl;
	private String parentId;
	private String imageUrl;
	private List<SysMenuPo> childMenu; // 子菜单;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

	public List<SysMenuPo> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<SysMenuPo> childMenu) {
		this.childMenu = childMenu;
	}
}
