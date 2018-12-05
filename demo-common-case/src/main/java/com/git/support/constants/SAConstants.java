package com.git.support.constants;

public class SAConstants {
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String DEFAULT = "UTF-8";
	
	public static final String SERVER_IP = "SERVER_IP";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_PASSWORD = "USER_PASSWORD";
	public static final String SERVER_NAME = "SERVER_NAME";
	public static final String EXEC_SHELL = "EXEC_SHELL";
	public static final String CHARSET = "CHARSET";
	public static final String IS_TOARRAY = "IS_TOARRAY";
	public static final String IS_KEY = "IS_KEY";
	public static final String IS_CHMOD = "IS_CHMOD";
	public static final String EXEC_FLAG = "EXEC_FLAG";
	public static final String LOCALPRIKEY_URL = "LOCALPRIKEY_URL";
	

	public static final String ECHO_INFO = "ECHO_INFO";
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final String EXIT_CODE = "EXIT_CODE";
	public static final String SSH_SA_RESULT = "SSH_SA_RESULT";
	
	public static final String DEPENDENT = "dependent";
	public static final String INDEPENDENT = "independent";
	
	public static final String CMD_LIST = "CMD_LIST";
	
	public static final String OUT_PARAM_LIST = "OUT_PARAM_LIST";
	
	//打包脚本所需常量
	public static final String PACKAGE_TAR_SHELL_MAP = "PACKAGE_TAR_DEL_MAP";
	
	//虚拟机相关的常量 add by mengzx 20130527
	public static final String V_USER_NAME = "V_USER_NAME";//用户名称(应用用户)
	public static final String V_USER_PASSWD = "V_USER_PASSWD";//用户密码
	public static final String NIC_TYPE_NAME_PROD = "NIC_TYPE_NAME_PROD";//生产网卡类型
	public static final String NIC_PORTGROUP_PROD = "NIC_PORTGROUP_PROD";//生产网卡端口组
	public static final String NIC_IP_PROD = "NIC_IP_PROD";//生产IP
	public static final String NIC_MASK_PROD = "NIC_MASK_PROD";//生产子网掩码
	public static final String NIC_GATEWAY_PROD = "NIC_GATEWAY_PROD";//生产网关
	public static final String NIC_TYPE_NAME_MGMT = "NIC_TYPE_NAME_MGMT";//管理网卡类型
	public static final String NIC_PORTGROUP_MGMT = "NIC_PORTGROUP_MGMT";//管理网卡端口组
	public static final String NIC_IP_MGMT = "NIC_IP_MGMT";//管理IP
	public static final String NIC_MASK_MGMT = "NIC_MASK_MGMT";//管理子网掩码
	public static final String NIC_GATEWAY_MGMT = "NIC_GATEWAY_MGMT";//管理网关
	
	public static final String V_DNS_SERVER1 = "V_DNS_SERVER1";//DNS地址
	public static final String V_DNS_SERVER2 = "V_DNS_SERVER2";//DNS地址
	public static final String V_NTP_SERVER1 = "V_NTP_SERVER1";//NTP地址
	public static final String V_NTP_SERVER2 = "V_NTP_SERVER2";//NTP地址
	public static final String V_NTP_SERVER3 = "V_NTP_SERVER3";//NTP地址
	public static final String V_GROUP_ID = "V_GROUP_ID";//用户组Id
	public static final String V_GROUP_NAME = "V_GROUP_NAME";//用户组名称
	public static final String V_USER_ID = "V_USER_ID";//用户Id
	public static final String V_USER_DIR = "V_USER_DIR";//用户home目录
	public static final String V_PING_COUNT = "V_PING_COUNT";//验证连通性时ping的次数
	public static final String V_LV_SIZE = "V_LV_SIZE";//lv大小
	public static final String V_LV_NAME = "V_LV_NAME";//lv名称
	public static final String V_VG_NAME = "V_VG_NAME";//vg名称
	public static final String V_LV_MNT = "V_LV_MNT";//
	
	//路由配置
	public static final String V_INTERFACE = "V_INTERFACE";//设备名
	public static final String V_ORDER = "V_ORDER";//路由序号
	public static final String V_ADDRESS = "V_ADDRESS";//ip地址
	public static final String V_NETMASK = "V_NETMASK";//子网掩码
	public static final String V_GATEWAY = "V_GATEWAY";//网关
	
	public static final String V_CHG_USER_NAME = "V_CHG_USER_NAME";//修改用户密码的用户名称
	public static final String V_CHG_USER_PASSWD = "V_CHG_USER_PASSWD";//新的密码
	
	//下发weblogic domain相关常量
	public static final String DOMAIN_TAR = "DOMAIN_TAR";
	public static final String NODEMANAGER_TAR = "NODEMANAGER_TAR";
	public static final String TARGET_DIR = "TARGET_DIR";
	public static final String TARGET_SERVER_IP = "TARGET_SERVER_IP";
	public static final String TARGET_USER_NAME = "TARGET_USER_NAME";
	public static final String TARGET_USER_PWD = "TARGET_USER_PWD";
	public static final String PACKAGE_DISTR_PATH = "PACKAGE_DISTR_PATH";
	
	//虚拟机相关的常量 end
	
	//物理机相关常量 begin
	public static final int OS_LINIX = 1;
	public static final int OS_WINDOWS = 2;
	public static final int OS_ESXi = 3;
	public static final String DEVDO_LIST = "DEVDO_LIST";
	//物理机相关常量 end
	
	//puppet服务器IP地址
	public static final String PUPPET_SERVER_IP = "PUPPET_SERVER_IP";
	//yum源IP地址
	public static final String YUM_SOURCE_IP = "YUM_SOURCE_IP";
	
	public static final String DEST_PATH = "DEST_PATH";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String FILE_CONTENT = "FILE_CONTENT";
	
	/**
	 * 更新Automation服务器 /etc/hosts文件脚本
	 * */
	public static final String AUTOMATION_HOSTS_SHELL = "./GIT_HOSTS_SET.sh";
}
