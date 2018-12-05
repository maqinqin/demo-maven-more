package com.git.cloud.vmrc.model.vo;

/**
* @ClassName: vmrcVo  
* @Description: vmware remote console vo
* @author WangJingxin
* @date 2016年6月24日 下午3:47:52  
*
 */
public class VmrcVo implements java.io.Serializable {

	/**  
	* @Fields serialVersionUID
	*/  
	private static final long serialVersionUID = -455037644121162214L;
	
	private String vmId;
	private int modeMask;
	private int msgMode;
	private String advancedConfig;
	private String connectHost;
	private String connectThumbprint;
	private boolean connectAllowSslErrors;
	private String connectTicket;
	private String connectUsername;
	private String connectPassword;
	private String connectVmid;
	private String connectDatacenter;
	private String connectVmpath;
	private String devName;
	
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getAdvancedConfig() {
		return advancedConfig;
	}
	public void setAdvancedConfig(String advancedConfig) {
		this.advancedConfig = advancedConfig;
	}
	public String getConnectHost() {
		return connectHost;
	}
	public void setConnectHost(String connectHost) {
		this.connectHost = connectHost;
	}
	public String getConnectThumbprint() {
		return connectThumbprint;
	}
	public void setConnectThumbprint(String connectThumbprint) {
		this.connectThumbprint = connectThumbprint;
	}
	public String getConnectTicket() {
		return connectTicket;
	}
	public void setConnectTicket(String connectTicket) {
		this.connectTicket = connectTicket;
	}
	public String getConnectUsername() {
		return connectUsername;
	}
	public void setConnectUsername(String connectUsername) {
		this.connectUsername = connectUsername;
	}
	public String getConnectPassword() {
		return connectPassword;
	}
	public void setConnectPassword(String connectPassword) {
		this.connectPassword = connectPassword;
	}
	public String getConnectVmid() {
		return connectVmid;
	}
	public void setConnectVmid(String connectVmid) {
		this.connectVmid = connectVmid;
	}
	public String getConnectDatacenter() {
		return connectDatacenter;
	}
	public void setConnectDatacenter(String connectDatacenter) {
		this.connectDatacenter = connectDatacenter;
	}
	public String getConnectVmpath() {
		return connectVmpath;
	}
	public void setConnectVmpath(String connectVmpath) {
		this.connectVmpath = connectVmpath;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public boolean isConnectAllowSslErrors() {
		return connectAllowSslErrors;
	}
	public void setConnectAllowSslErrors(boolean connectAllowSslErrors) {
		this.connectAllowSslErrors = connectAllowSslErrors;
	}
	public int getModeMask() {
		return modeMask;
	}
	public void setModeMask(int modeMask) {
		this.modeMask = modeMask;
	}
	public int getMsgMode() {
		return msgMode;
	}
	public void setMsgMode(int msgMode) {
		this.msgMode = msgMode;
	}

	
}
