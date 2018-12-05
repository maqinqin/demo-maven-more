package com.git.cloud.handler.automation;

//用于执行脚本的服务器定义
public class Server {
	public String serverIp;
	public String user;
	public String password;
	public String serverName;
	public String serverId;
	
	public Server() {
	}
	public Server(String serverIp, String user, String password) {
		this.serverIp = serverIp;
		this.user = user;
		this.password = password;
	}
	
	public void setServerIdAndName(String id, String name) {
		this.serverId = id;
		this.serverName = name;
	}
	@Override
	public String toString() {
		return "Server [serverIp=" + serverIp + ", user=" + user
				+ ", password=" + password + ", serverName=" + serverName
				+ ", serverId=" + serverId + "]";
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
}