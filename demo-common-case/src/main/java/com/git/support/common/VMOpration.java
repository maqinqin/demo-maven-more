package com.git.support.common;

public final class VMOpration {
	private final static int BASE = 1000;

	// public final static int HOST_STAT = BASE + 1;
	// public final static int POWERON_HOST = BASE + 2;
	// public final static int POWEROFF_HOST = BASE + 3;
	// public final static int REBOOT_HOST = BASE + 4;
	// public final static int CREATE_VM = BASE + 5;
	// public final static int POWERON_VM = BASE + 6;
	// public final static int POWEROFF_VMT = BASE + 7;
	// public final static int REBOOT_VM = BASE + 8;

	public final static int CREATEANDCONF_VM = BASE + 1;
	public final static int CREATE_FOLDER = BASE + 2;
	public final static int CREATE_CLUSTER = BASE + 3;
	public final static int CREATE_HOST = BASE + 4;
	public final static int CONF_MANAGEMENT_TO_VMK = BASE + 5;
	public final static int ADD_HBDS = BASE + 6;
	public final static int UPLOAD_GUEST_FILE = BASE + 7;
	public final static int EXECUTE_GUEST_BAT = BASE + 8;
	public final static int DESTORY_RESOURCE = BASE + 9;
	public final static int VMPOWER_OPS = BASE + 10;
	public final static int ADD_NAS = BASE + 11;
	public final static int RESOURCE_EXIST = BASE + 12;
	public final static int DESTORY_RESOURCE_LIST = BASE + 13;
	public final static int QUERY_VC_INFO = BASE + 14;
	public final static int VMPOWER_OPS_ESXI = BASE + 15;
	public final static int QUERY_CLUSTER_INFO = BASE + 16;
	public final static int QUERY_HOST_INFO = BASE + 17;
	public final static int QUERY_VM_INFO = BASE + 18;
	public final static int RESOURCE_RENAME = BASE + 19;
	// Esxi创建用户
	public final static int CREATE_USER = BASE + 20;
	// Esxi修改用户密码
	public final static int MODIFY_USER_PASSWORD = BASE + 21;
	// 验证Esxi登录用户名密码
	public final static int PASSWORD_VALIDATE = BASE + 22;
	// 从OVF模板创建虚机
	public final static int CREATE_VM_FROM_OVF = BASE + 23;
	// 配置虚机
	public final static int CONFIG_VM = BASE + 24;
	public static final int RECONFIG_VM = BASE + 25;
	public static final int QUERY_POWER_STATE = BASE + 26;
	public static final int VMOTION = BASE + 27;
	// 创建数据中心
	public final static int CREATE_DATACENTER = BASE + 28;

	// 移除纳管主机
	public final static int DESTORY_HOST_RESOURCE = BASE + 29;
	// 虚拟机添加磁盘
	public final static int ADD_DISK = BASE + 30;

	// 获取虚拟机CPU和内存的使用情况
	public final static int VI_TOP = BASE + 31;

}