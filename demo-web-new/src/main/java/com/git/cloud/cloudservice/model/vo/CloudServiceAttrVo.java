/**
 * @Title:CloudServiceAttrVo.java
 * @Package:com.git.cloud.cloudservice.model.vo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-17 下午6:34:33
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.vo;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;

/**
 * @ClassName:CloudServiceAttrVo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-17 下午6:34:33
 *
 *
 */
public class CloudServiceAttrVo extends CloudServiceAttrPo {
	private String attrTypeName;
	private String attrClassName;
	private List<CloudServiceAttrSelPo> attrSelList;
	/**
	 * @return the attrTypeName
	 */
	public String getAttrTypeName() {
		return attrTypeName;
	}
	/**
	 * @param attrTypeName the attrTypeName to set
	 */
	public void setAttrTypeName(String attrTypeName) {
		this.attrTypeName = attrTypeName;
	}
	/**
	 * @return the attrClassName
	 */
	public String getAttrClassName() {
		return attrClassName;
	}
	/**
	 * @param attrClassName the attrClassName to set
	 */
	public void setAttrClassName(String attrClassName) {
		this.attrClassName = attrClassName;
	}
	public List<CloudServiceAttrSelPo> getAttrSelList() {
		return attrSelList;
	}
	public void setAttrSelList(List<CloudServiceAttrSelPo> attrSelList) {
		this.attrSelList = attrSelList;
	}
	
	
}
