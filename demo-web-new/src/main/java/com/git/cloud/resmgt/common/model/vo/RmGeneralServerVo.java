/**
 * @Title:RmGeneralServerVo.java
 * @Package:com.git.cloud.resmgt.common.model.vo
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-6 下午05:14:17
 * @version V1.0
 */
package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;

/**
 * @ClassName:RmGeneralServerVo
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-6 下午05:14:17
 *
 *
 */
public class RmGeneralServerVo extends RmGeneralServerPo {

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = -4361060486419185953L;
	private String datacenterName;
	private String password;
	private String typeName;
	private String userName;
	private String serverIp;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDatacenterName() {
		return datacenterName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}

}
