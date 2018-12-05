package com.git.support.common;
/**
 * 虚机消息队列间传递参数key值
 * <p>
 * @author liufei
 * @version 1.0 2013-5-3
 * @see
 */
public final class VMFlds {
	public  final static String URL = "URL";
	public  final static String USERNAME = "USERNAME";
	public  final static String PASSWORD = "PASSWORD";
	public  final static String VCENTER_URL = "VCENTER_URL";
	public  final static String VCENTER_USERNAME = "VCENTER_USERNAME";
	public  final static String VCENTER_PASSWORD = "VCENTER_PASSWORD";
	public  final static String ESXI_URL = "ESXI_URL";
	public  final static String ESXI_IP = "ESXI_IP";
	public  final static String ESXI_USERNAME = "ESXI_USERNAME";
	public  final static String ESXI_PASSWORD = "ESXI_PASSWORD";
	//ESXi主机名
	public  final static String HOST_NAME = "HOST_NAME";
	//虚机操作系统用户名
	public  final static String GUEST_USER_NAME = "GUEST_USER_NAME";
	public  final static String GUEST_PASSWORD = "GUEST_PASSWORD";
	//创建虚机名称
	public  final static String VAPP_NAME = "VAPP_NAME";
	public  final static String OVF_URL = "OVF_URL";
	public  final static String TEMPLATE_NAME = "TEMPLATE_NAME";
	public  final static String DATASTORE_NAME = "DATASTORE_NAME";
	public  final static String CPU_VALUE = "CPU_VALUE";
	public  final static String MAX_CPU_VALUE = "MAX_CPU_VALUE";
	public  final static String CPU_CORE_VALUE = "CPU_CORE_VALUE";
	public  final static String MEMORY_VALUE = "MEMORY_VALUE";
	public  final static String MAX_MEMORY_VALUE = "MAX_MEMORY_VALUE";
	public  final static String ADD_DISK_NAME = "ADD_DISK_NAME";
	public  final static String ADD_DISK_SIZE = "ADD_DISK_SIZE";
	public  final static String ADD_DISK_MODE = "ADD_DISK_MODE";
	//嵌套增加网卡对象
	public  final static String ADD_NIC1_OBJ = "ADD_NIC1_OBJ";
	public  final static String ADD_NIC2_OBJ = "ADD_NIC2_OBJ";
	public  final static String ADD_NIC_TYPE_NAME = "ADD_NIC_TYPE_NAME";
	public  final static String ADD_NIC_PORTGROUP = "ADD_NIC_PORTGROUP";
	//虚机操作系统类型(windows或Linux)
	public  final static String VM_TYPE = "VM_TYPE";
	//虚机操作系统主机名
	public  final static String VM_HOST_NAME = "VM_HOST_NAME";
	public  final static String NIC_DOMAIN_NAME = "NIC_DOMAIN_NAME";
	public  final static String NIC_IP = "NIC_IP";
	public  final static String NIC_MASK = "NIC_MASK";
	public  final static String NIC_GATEWAY = "NIC_GATEWAY";
	public  final static String NIC_DNS = "NIC_DNS";
	public  final static String VLAN_ID = "VLAN_ID";
	//网卡路由信息
	public  final static String ROUTE_IPS = "ROUTE_IPS";
	public  final static String ROUTE_GWS = "ROUTE_GWS";
	public  final static String ROUTE_MASKS = "ROUTE_MASKS";
	
	//window网络连接的名称
	public  final static String V_CONNECTION_NAME = "V_CONNECTION_NAME";
	
	//虚机执行脚本信息
	//执行脚本
	public final static String EXECUTE_SCRIPT = "EXECUTE_SCRIPT";
	//脚本list名
	public final static String SCRIPT_LIST_NAME = "SCRIPT_LIST_NAME";
	//脚本参数list名
	public final static String SCRIPT_ARGS_LIST_NAME = "SCRIPT_ARGS_LIST_NAME";
	//脚本服务器路径
	public final static String SCRIPT_SERVER_PATH = "SCRIPT_SERVER_PATH";
	//脚本下发到适配器服务器路径
	public final static String ADAPTER_SCRIPT_PATH = "ADAPTER_SCRIPT_PATH";
	
	//创建cluster、folder、host时指定的父节点
	public  final static String PARENT_TYPE = "PARENT_TYPE";
	public  final static String PARENT_NAME = "PARENT_NAME";
	public  final static String CLUSTER_NAME = "CLUSTER_NAME";
	//创建folder名称
	public  final static String FOLDER_NAME = "FOLDER_NAME";
	//是否开启HA
	public  final static String DAS_CONFIG_ENABLED = "DAS_CONFIG_ENABLED";
	//主机监控开关
	public  final static String DAS_HOST_MONITORING = "DAS_HOST_MONITORING";
	//接入控制开关
	public  final static String DAS_ADMISSION_CONTROL_ENABLED = "DAS_ADMISSION_CONTROL_ENABLED";
	//接入控制级别
	public  final static String DAS_FAILOVER_LEVEL = "DAS_FAILOVER_LEVEL";
	//接入控制策略类型
	public  final static String DAS_ADMISSION_CONTROL_POLICY_TYPE = "DAS_ADMISSION_CONTROL_POLICY_TYPE";
	//群集允许的主机故障数目
	public  final static String DAS_FAILOVER_LEVEL_POLICY = "DAS_FAILOVER_LEVEL_POLICY";
	//故障切换空间容量保留的群集cpu百分比
	public  final static String DAS_CPU_FAILOVER_RESOURCES_PERCENT = "DAS_CPU_FAILOVER_RESOURCES_PERCENT";
	//故障切换空间容量保留的群集内存百分比
	public  final static String DAS_MEMORY_FAILOVER_RESOURCES_PERCENT = "DAS_MEMORY_FAILOVER_RESOURCES_PERCENT";
	//主机隔离响应策略
	public  final static String DAS_VM_SETTINGS_ISOLATION_RESPONSE = "DAS_VM_SETTINGS_ISOLATION_RESPONSE";
	//虚机重新启动优先级
	public  final static String DAS_VM_SETTINGS_RESTART_PRIORITY = "DAS_VM_SETTINGS_RESTART_PRIORITY";
	//虚拟机监控状态		
	public  final static String DAS_MONITORING_CLUSTER_SETTING_FLAG = "DAS_MONITORING_CLUSTER_SETTING_FLAG";
	public  final static String DAS_MONITORING_ENABLED = "DAS_MONITORING_ENABLED";
	//虚拟机监控策略
	public  final static String DAS_VM_MONITORING = "DAS_VM_MONITORING";
	//虚机故障间隔时间
	public  final static String DAS_MONITORING_FAILURE_INTERVAL = "DAS_MONITORING_FAILURE_INTERVAL";
	//最大重置次数
	public  final static String DAS_MONITORING_MAX_FAILURES = "DAS_MONITORING_MAX_FAILURES";
	//最短正常运行时间
	public  final static String DAS_MONITORING_MIN_UP_TIME = "DAS_MONITORING_MIN_UP_TIME";
	//电源管理设置开关
	public  final static String DPM_CONFIG_ENABLED = "DPM_CONFIG_ENABLED";
	//电源管理设置策略级别
	public  final static String DPM_BEHAVIOR = "DPM_BEHAVIOR";
	//电源管理阈值
	public final static String  DPM_HOST_POWER_ACTION_RATE = "DPM_HOST_POWER_ACTION_RATE";
	//drs设置开关
	public  final static String DRS_CONFIG_ENABLED = "DRS_CONFIG_ENABLED";
	//drs设置策略级别
	public  final static String DRS_CONFIG_VM_BEHAVIOR = "DRS_CONFIG_VM_BEHAVIOR";
	//drs迁移阈值
	public final static String DRS_VMOTION_RATE= "DRS_VMOTION_RATE";
	/**
	 * 集群下创建host配置参数
	 */
	public  final static String HOST_MANAGEMENT_IP = "HOST_MANAGEMENT_IP";
	public  final static String HOST_USERNAME = "HOST_USERNAME";
	public  final static String HOST_PASSWORD = "HOST_PASSWORD";
	//设为true时，如果当前主机已被其他vCenter管理,则原来vCenter丢失对该主机的连接
	public  final static String HOST_FORCE = "HOST_FORCE";
	public  final static String LICENSE_KEY =  "LICENSE_KEY";
	
	/**
	 * 集群下配置存储检测信号
	 */
	public  final static String HBDS_ARRAY = "HBDS_ARRAY";
	public  final static String HBDS_CANDIDATE_POLICY = "HBDS_CANDIDATE_POLICY";
	
	/**
	 * 端口组管理流量开关操作类型
	 */
	public final static String CONF_MANAGEMENT_TYPE = "CONF_MANAGEMENT_TYPE";
	
	public final static String VMK_ID = "VMK_ID";
	
	//返回执行结果key
	public final static String EXECUTE_RESULT = "EXECUTE_RESULT";
	
	//回收资源名称
	public final static String DESTORY_RESOURCE_NAME = "DESTORY_RESOURCE_NAME";
	//回收资源类型
	public final static String DESTORY_TYPE = "DESTORY_TYPE";
	//批量调用回收资源记录
	public final static String DESTORY_RESOURCE_RECS = "DESTORY_RESOURCE_RECS";
	
	//虚机电源管理类型
	public final static String VM_POWER_OPER_TYPE = "VM_POWER_OPER_TYPE";
	//挂载Nas记录List
	public final static String NAS_ADD_RECS = "NAS_ADD_RECS";
	//Nas存储ip
	public final static String NAS_HOST_REMOTE_IP = "NAS_HOST_REMOTE_IP";
	//Nas存储服务端路径
	public final static String NAS_REMOTE_PATH = "NAS_REMOTE_PATH";
	//挂载后Nas在主机上显示名称
	public final static String NAS_LOCAL_PATH = "NAS_LOCAL_PATH";
	//接口操作类型
	public final static String INTERFACE_OPER_TYPE = "INTERFACE_OPER_TYPE";
	
	/**
	 * 重命名接口参数
	 */
	public final static String RESOURCE_NAME = "RESOURCE_NAME";
	public final static String RESOURCE_NEW_NAME = "RESOURCE_NEW_NAME";
	
	/**
	 * 创建用户接口参数
	 */
	//新建用户名
	public final static String NEW_USER_NAME = "NEW_USER_NAME";
	//新建用户密码
	public final static String NEW_PASSWORD = "NEW_PASSWORD";
	//角色id
	public final static String ROLE_ID = "ROLE_ID";
	public static final String VMOTION_PRIORITY = "VMOTION_PRIORITY";
	public static final String VMOTION_TYPE = "VMOTION_TYPE";
	public static final String VMOTION_POWER_STATE = "VMOTION_POWER_STATE";
	public static final String RECONFIG_TYPE_CPU = "RECONFIG_TYPE_CPU";
	public static final String RECONFIG_TYPE_MEMORY = "RECONFIG_TYPE_MEMORY";
	public static final String RECONFIG_TYPE = "RECONFIG_TYPE";
	public static final String CPU_ALTER_TYPE = "CPU_ALTER_TYPE";
	public static final String MEMORY_ALTER_TYPE = "MEMORY_ALTER_TYPE";
	public static final String CPU_VALUE_CHANGED = "CPU_VALUE_CHANGED";
	public static final String MEMORY_VALUE_CHANGED = "MEMORY_VALUE_CHANGED";
	public static final String VMOTION_SOURCE_HOST = "VMOTION_SOURCE_HOST";
	public static final String VMOTION_TARGET_DATASTORE = "VMOTION_TARGET_DATASTORE";
	public static final String VMOTION_TARGET_HOST = "VMOTION_TARGET_HOST";
	public static final String IF_MOVE_THIS_VM = "IF_MOVE_THIS_VM";
	public static final String IF_NEED_MOVE_VM = "IF_NEED_MOVE_VM";
	
	//虚机文件允许扩展大小(kb)
	public static final String VM_VOL_CAPACITY_SIZE = "VM_VOL_CAPACITY_SIZE";
	//创建虚机镜像大小(kb)
	public static final String VM_VOL_ALLOCATION_SIZE = "VM_VOL_ALLOCATION_SIZE";
	//虚机镜像名称
	public static final String VM_IMAGE_NAME = "VM_IMAGE_NAME";
	
	//数据中心名称
	public static final String DATACENTER_NAME = "DATACENTER_NAME";
	
	//数据中心名称
	public static final String VM_SNAPSHOT_NAME = "VM_SNAPSHOT_NAME";

}
