package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 辅助服务器表，存放ntp，dns等服务器信息
 * @ClassName:RmGaneralServerPo
 * @Description:TODO
 * @author zhuzhaoyong
 * @date 2014-10-14 下午3:14:10
 *
 *
 */
public class RmGeneralServerPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 2193678511459352906L;
	private String id;
	private String serverName;
	private String type;
	private String serverIp;
	private String subMask;
	private String gateway;
	private String isActive;
	private String datacenterId;
	private String userName;
	private String pwd;

	// Constructors

	/** default constructor */
	public RmGeneralServerPo(){
	}

	/** minimal constructor */
	public RmGeneralServerPo(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getSubMask() {
		return subMask;
	}

	public void setSubMask(String subMask) {
		this.subMask = subMask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
