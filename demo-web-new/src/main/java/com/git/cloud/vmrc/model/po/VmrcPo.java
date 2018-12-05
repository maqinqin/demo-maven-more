package com.git.cloud.vmrc.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
* @ClassName: VmrcPo  
* @Description: vmware remote console po
* @author WangJingxin
* @date 2016年6月27日 下午2:19:43  
*
 */
public class VmrcPo extends BaseBO {
	
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



	public boolean isConnectAllowSslErrors() {
		return connectAllowSslErrors;
	}



	public void setConnectAllowSslErrors(boolean connectAllowSslErrors) {
		this.connectAllowSslErrors = connectAllowSslErrors;
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



	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
