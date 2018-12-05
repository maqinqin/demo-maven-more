package com.git.support.common;

public final class NAOpration {
	private final static int  BASE              =     4000;
	public final static int  TODO              =  BASE + 1;
	public final static int CREATE_AND_CONF_POOL = BASE + 2;
	public final static int CREATE_AND_CONF_MONITOR = BASE + 3;
	public final static int CREATE_AND_CONF_PERSISTENCE_PROFILE =  BASE + 4;
	public final static int CREATE_AND_CONF_FASTL4_PROFILE = BASE + 5;
	public final static int CREATE_AND_CONF_SNAT_POOL = BASE + 6;
	public final static int CREATE_AND_CONF_VS = BASE + 7;
	public final static int CREATE_AND_CONF_FTP_PROFILE = BASE + 8;
	public final static int CREATE_AND_CONF_UDP_PROFILE = BASE + 9;
	public final static int CREATE_AND_CONF_TCP_PROFILE = BASE + 10;
	public final static int CREATE_AND_CONF_HTTP_PROFILE = BASE + 11;
	//定义MEMBER一键式启停的方法
	public final static int GET_POOL_MEMBER_INFO = BASE + 12;
	public final static int CHANGE_MEMBER_STATUS = BASE + 13;
	//vs数据采集
	public final static int QUERY_VS_INFO = BASE + 14;
	//设备纳管
	public final static int DEVICE_SUPERISE = BASE + 15; 
	//获取vlan信息
	public final static int QUERY_VLAN_INFO = BASE + 16;
	//获取设备数据采集信息
	public final static int GET_DEVICE_CONF_INFO = BASE + 17;
}
