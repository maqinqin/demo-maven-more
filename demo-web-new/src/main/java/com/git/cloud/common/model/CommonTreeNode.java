
package com.git.cloud.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用的树形节点实体类，封装树形节点数据。
 */
public class CommonTreeNode implements java.io.Serializable {

	private static final long serialVersionUID = -884949903235149230L;
	private String id;// 节点ID
	private String upId;// 上级节点ID
	private String pId;// 上级节点ID
	private String icon;// 节点图标
	private String iconSkin;
	private String iconOpen;//展开时图标
	private String iconClose;//关闭时图标
	private String name;// 名称
	private String title;
	private String level;// 节点层次
	private boolean isParent = false;// 是否是父节点（true：表示有子节点，false：表示没有子节点）
	private boolean checked = false;// 当前节点是否选中
	private boolean open = true;// 是否展开
	private String click;// 节点点击调用方法
	private Boolean nocheck;//是否存在勾选框
	private String bizType;//业务类型，用来区分不同的节点业务功能
	private boolean isFirstExpand;//是否是第一次展开加号，第一次展开要和数据库交互，之后就不用了

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public boolean isFirstExpand() {
		return isFirstExpand;
	}

	public void setFirstExpand(boolean isFirstExpand) {
		this.isFirstExpand = isFirstExpand;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/** 子节点集合 */
	private List<CommonTreeNode> children = null;
	/** 节点参数 */
	private Map<String, String> params = new HashMap<String, String>();
	/** 第一级节点的父节点id */
	public static String ROOT_ID = "0";

	/**
	 * 添加子节点
	 * 
	 * @param childNode
	 */
	public void addChildNode(CommonTreeNode childNode) {

		if (this.children == null)
			this.children = new ArrayList<CommonTreeNode>();

		this.children.add(childNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return this.pId;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
		this.pId = upId;
	}

	public List<CommonTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<CommonTreeNode> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 * the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the isParent
	 */
	public boolean getIsParent() {
		return isParent;
	}

	/**
	 * @param isParent
	 * the isParent to set
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;

	}
	
	public boolean getParent() {
		return isParent;
	}

	/**
	 * @return the click
	 */
	public String getClick() {
		return click;
	}

	/**
	 * @param click
	 * the click to set
	 */
	public void setClick(String click) {
		this.click = click;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 * the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the nocheck
	 */
	public Boolean getNocheck() {
		return nocheck;
	}

	/**
	 * @param nocheck the nocheck to set
	 */
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
}
