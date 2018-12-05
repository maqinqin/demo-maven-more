package com.git.support.constants;

/**
 * 自动化安装Weblogic所需的参数
 * @version 1.0 2013-5-27 
 * @see
 */
public class WeblogicConstants {
	
	public static final String APP_USER = "APP_USER";// 应用用户名
	public static final String WLS_HOME = "WLS_HOME";// weblogic安装目录
	public static final String DOMAIN_TEMPLATE_FILE = "DOMAIN_TEMPLATE_FILE";// Domain模板文件位置
	public static final String DOMAIN_NAME = "DOMAIN_NAME";// 目标Domain名称
	public static final String DOMAIN_DIR = "DOMAIN_DIR";// 目标Domain目录
	public static final String V_WEBLOGIC_GROUP_NAME = "V_WEBLOGIC_GROUP_NAME";// weblogic用户组名称
	
	
	public static final String ADMIN_PORT = "ADMIN_PORT";// 管理服务器监听端口
	public static final String ADMIN_ADDR = "ADMIN_ADDR";// // Admin Server所属Machine、IP及端口号，用冒号分隔，如Machine1:127.0.0.1:7001
	public static final String WLS_USERNAME = "WLS_USERNAME";// 目标Domain用户名
	public static final String WLS_PASSWORD = "WLS_PASSWORD";// 目标Domain密码
	public static final String NODEMANAGER_PORT = "NODEMANAGER_PORT";// nodeManager端口
	public static final String NODEMANAGER_ADDR = "NODEMANAGER_ADDR";// nodemanager监听地址
	public static final String LOGFILES_NUMBER = "LOGFILES_NUMBER";// 日志文件数量最大值
	public static final String ADMIN_MANAGE_ADDR = "ADMIN_MANAGE_ADDR";//Admin Server的带管IP地址
	public static final String AS_JVM_PARA = "AS_JVM_PARA";// ADMIN Server对应的JVM参数
	public static final String MS_JVM_PARA = "MS_JVM_PARA";// 被管Server对应的JVM参数
	public static final String START_OPTIONS = "START_OPTIONS";// 服务器启动参数
	public static final String MS_ADDRS = "MS_ADDRS";// 被管Server名称、所属Machine、IP及端口号
	public static final String MACHINE_ADDRS = "MACHINE_ADDRS";//Machine名称及nodemanager 、IP及端口号
	public static final String VM_SERVER_NAMES = "VM_SERVER_NAMES";// VM_SERVER_NAMES
	public static final String VM_SERVER_ADDRS = "VM_SERVER_ADDRS";// VM_SERVER_ADDRS
	public static final String VM_MS_NUM = "VM_VM_MS_NUM";// 每台虚拟机被管server数量

	
	

}
