package com.git.cloud.handler.automation.se.vo;

import java.util.HashMap;
import java.util.List;

public class SshExecShellVo {
	private String ip; 
	private String user;
	private String pwd; 
	private Boolean isKey; 
	private String keyFileUrl; 
	private String keyPWD;
	private String cmd;
	private List<String> cmdList;
	private HashMap<String, Object> cmdMap;
	
	public SshExecShellVo() {
		super();
		this.ip = null;
		this.user = null;
		this.pwd = null;
		this.isKey = false;
		this.keyFileUrl = null;
		this.keyPWD = null;
		this.cmd = null;
		this.cmdList = null;
	}
	
	public SshExecShellVo(String ip, String user, String pwd) {
		super();
		this.ip = ip;
		this.user = user;
		this.pwd = pwd;
		this.isKey = false;
		this.keyFileUrl = null;
		this.keyPWD = null;
		this.cmd = null;
		this.cmdList = null;
	}

	public SshExecShellVo(String ip, String user, String pwd, Boolean isKey,
			String keyFileUrl, String keyPWD) {
		super();
		this.ip = ip;
		this.user = user;
		this.pwd = pwd;
		this.isKey = isKey;
		this.keyFileUrl = keyFileUrl;
		this.keyPWD = keyPWD;
		this.cmd = null;
		this.cmdList = null;
	}

	public SshExecShellVo(String ip, String user, String pwd, Boolean isKey,
			String keyFileUrl, String keyPWD, String cmd) {
		super();
		this.ip = ip;
		this.user = user;
		this.pwd = pwd;
		this.isKey = isKey;
		this.keyFileUrl = keyFileUrl;
		this.keyPWD = keyPWD;
		this.cmd = cmd;
		this.cmdList = null;
	}

	public SshExecShellVo(String ip, String user, String pwd, Boolean isKey,
			String keyFileUrl, String keyPWD, List<String> cmdList) {
		super();
		this.ip = ip;
		this.user = user;
		this.pwd = pwd;
		this.isKey = isKey;
		this.keyFileUrl = keyFileUrl;
		this.keyPWD = keyPWD;
		this.cmd = null;
		this.cmdList = cmdList;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Boolean getIsKey() {
		return isKey;
	}
	public void setIsKey(Boolean isKey) {
		this.isKey = isKey;
	}
	public String getKeyFileUrl() {
		return keyFileUrl;
	}
	public void setKeyFileUrl(String keyFileUrl) {
		this.keyFileUrl = keyFileUrl;
	}
	public String getKeyPWD() {
		return keyPWD;
	}
	public void setKeyPWD(String keyPWD) {
		this.keyPWD = keyPWD;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public List<String> getCmdList() {
		return cmdList;
	}
	public void setCmdList(List<String> cmdList) {
		this.cmdList = cmdList;
	}

	public HashMap<String, Object> getCmdMap() {
		return cmdMap;
	}

	public void setCmdMap(HashMap<String, Object> cmdMap) {
		this.cmdMap = cmdMap;
	}
}
