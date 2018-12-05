/**
 * @Title:CloudServiceFlowRefVo.java
 * @Package:com.git.cloud.cloudservice.model.po
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-16 上午10:51:23
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.po;

/**
 * @ClassName:CloudServiceFlowRefVo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-16 上午10:51:23
 *
 *
 */
public class CloudServiceFlowRefVo extends CloudServiceFlowRefPo {

	private String flowName;
	private String operModelTypeName;

	
	/**
	 * @return the operModelTypeName
	 */
	public String getOperModelTypeName() {
		return operModelTypeName;
	}

	/**
	 * @param operModelTypeName the operModelTypeName to set
	 */
	public void setOperModelTypeName(String operModelTypeName) {
		this.operModelTypeName = operModelTypeName;
	}

	/**
	 * @return the flowName
	 */
	public String getFlowName() {
		return flowName;
	}

	/**
	 * @param flowName the flowName to set
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
}
