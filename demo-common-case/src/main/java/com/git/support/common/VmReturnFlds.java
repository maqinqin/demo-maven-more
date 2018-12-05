package com.git.support.common;

/**
 * @author liufei.co
 * 
 */
public class VmReturnFlds {
	// datacenter list名称
	public final static String DATACENTER_LIST_NAME = "DATACENTER_LIST_NAME";
	// datacenter名
	public final static String DATACENTER_NAME = "DATACENTER_NAME";
	// CDP list名称
	public final static String CDP_LIST_NAME = "CDP_LIST_NAME";
	// CDP名
	public final static String CDP_NAME = "CDP_NAME";
	// ****集群信息开始****
	// 集群 list名称
	public final static String CLUSTER_LIST_NAME = "CLUSTER_LIST_NAME";
	// 集群名称
	public final static String CLUSTER_NAME = "CLUSTER_NAME";
	// drs设置开关
	public final static String DRS_CONF_ENABLED = "DRS_CONF_ENABLED";
	// drs设置策略级别
	public final static String DRS_CONF_VM_BEHAVIOR = "DRS_CONF_VM_BEHAVIOR";
	// drs迁移阈值
	public final static String DRS_VMOTION_RATE = "DRS_VMOTION_RATE";
	// 电源管理设置开关
	public final static String DPM_CONF_ENABLED = "DPM_CONF_ENABLED";
	// 电源管理设置策略级别
	public final static String DPM_BEHAVIOR = "DPM_BEHAVIOR";
	// dpm阈值
	public final static String DPM_HOST_POWER_ACTION_RATE = "DPM_HOST_POWER_ACTION_RATE";
	// HA设置开关
	public final static String DAS_CONF_ENABLED = "DAS_CONF_ENABLED";
	// 主机监控状态
	public final static String DAS_HOST_MONITORING = "DAS_HOST_MONITORING";
	// 接入监控开关
	public final static String DAS_ADMISSION_CONTROL_ENABLED = "DAS_ADMISSION_CONTROL_ENABLED";
	// 接入控制级别
	public final static String DAS_FAIL_OVER_LEVEL = "DAS_FAIL_OVER_LEVEL";
	// 接入控制策略
	public final static String DAS_ADMISSION_CONTROL_POLICY_TYPE = "DAS_ADMISSION_CONTROL_POLICY_TYPE";
	// 集群允许的主机故障数目
	public final static String DAS_FAIL_OVER_LEVEL_POLICY = "DAS_FAIL_OVER_LEVEL_POLICY";
	// 故障切换空间容量保留的群集cpu百分比
	public final static String DAS_CPU_FAILOVER_RESOURCES_PERCENT = "DAS_CPU_FAILOVER_RESOURCES_PERCENT";
	// 故障切换空间容量保留的群集内存百分比
	public final static String DAS_MEMORY_FAILOVER_RESOURCES_PERCENT = "DAS_MEMORY_FAILOVER_RESOURCES_PERCENT";
	// 虚机重启优先级
	public final static String DAS_VMSETTINGS_RESTART_PRIORITY = "DAS_VMSETTINGS_RESTART_PRIORITY";
	// 虚拟机集群监控开关
	public final static String DAS_MONITORING_CLUSTER_SETTING_FLAG = "DAS_MONITORING_CLUSTER_SETTING_FLAG";
	// 虚拟机监控开关
	public final static String DAS_MONITORING_ENABLED = "DAS_MONITORING_ENABLED";
	// 虚拟机监控策略
	public final static String DAS_VM_MONITORING = "DAS_VM_MONITORING";
	// 主机隔离响应策略
	public final static String DAS_VMSETTINGS_ISOLATION_RESPONSE = "DAS_VMSETTINGS_ISOLATION_RESPONSE";
	// 监控数据存储信号策略
	public final static String HB_DATASTORE_CANDIDATE_POLICY = "HB_DATASTORE_CANDIDATE_POLICY";
	// 指定监测数据存储信号
	public final static String HB_DS_ARRAY = "HB_DS_ARRAY";
	// ***集群信息结束***

	// ***主机信息开始***
	// 主机列表名
	public final static String HOST_LIST_NAME = "HOST_LIST_NAME";
	// 主机名
	public final static String HOST_NAME = "HOST_NAME";
	// cpu核数
	public final static String HOST_CPU_CORE_NUM = "HOST_CPU_CORE_NUM";
	// 内存大小
	public final static String HOST_MEMORY_SIZE = "HOST_MEMORY_SIZE";
	// 已用cpu数
	public final static String HOST_USED_CPU = "HOST_USED_CPU";
	// 已用内存数
	public final static String HOST_USED_MEMORY_SIZE = "HOST_USED_MEMORY_SIZE";
	// 电源状态
	public final static String HOST_POWER_STATE = "HOST_POWER_STATE";
	// ntp server
	public final static String HOST_NTP_SERVER = "HOST_NTP_SERVER";
	// ***主机信息结束***

	// ***路由信息***
	// 路由列表名
	public final static String ROUTE_LIST_NAME = "ROUTE_LIST_NAME";
	// 路由目的地址
	public final static String ROUTE_NETWORK = "ROUTE_NETWORK";
	// 路由网关
	public final static String ROUTE_GATEWAY = "ROUTE_GATEWAY";

	// ***网卡信息***
	// 网卡列表名
	public final static String NIC_LIST_NAME = "NIC_LIST_NAME";
	// MAC地址
	public final static String NIC_MAC_ADDRESS = "NIC_MAC_ADDRESS";
	// 端口组
	public final static String NIC_PORTGROUP = "NIC_PORTGROUP";

	// ***IP信息***
	// IP列表名
	public final static String IP_LIST_NAME = "IP_LIST_NAME";
	// IP地址
	public final static String IP_ADDRESS = "IP_ADDRESS";
	// 掩码
	public final static String IP_MASK = "IP_MASK";
	// 网关
	public final static String IP_GATEWAY = "IP_GATEWAY";

	// ***datastore信息***
	// datastore列表名
	public final static String DATASTORE_LIST_NAME = "DATASTORE_LIST_NAME";
	// datastore名称
	public final static String DATASTORE_NAME = "DATASTORE_NAME";
	// datastore类型
	public final static String DATASTORE_TYPE = "DATASTORE_TYPE";
	// NAS存储 ip
	public final static String DATASTORE_REMOTE_IP = "DATASTORE_REMOTE_IP";
	// NAS存储路径
	public final static String DATASTORE_REMOTE_PATH = "DATASTORE_REMOTE_PATH";
	// datastore容量大小
	public final static String DATASTORE_SIZE = "DATASTORE_SIZE";
	// 剩余空间
	public final static String DATASTORE_FREE_SIZE = "DATASTORE_FREE_SIZE";
	// ***虚机信息***
	// 虚机列表名
	public final static String VM_LIST_NAME = "VM_LIST_NAME";
	// 虚机名
	public final static String VM_NAME = "VM_NAME";
	// cpu核数
	public final static String VM_CPU_CORE_NUM = "VM_CPU_CORE_NUM";
	// 内存大小
	public final static String VM_MEMORY_SIZE = "VM_MEMORY_SIZE";
	// 虚拟磁盘大小
	public final static String VM_DISK_SIZE = "VM_DISK_SIZE";
	// 虚机所在datastore
	public final static String VM_DATASTORE_NAME = "VM_DATASTORE_NAME";
	// 虚机电源状态
	public final static String VM_POWER_STATE = "VM_POWER_STATE";
	public static final String HOST_SN = "HOST_SN";
	public static final String DEVICE_TYPE = "DEVICE_TYPE";
	public static final String OS_NAME = "OS_NAME";
	public static final String MANUFACTURER = "MANUFACTURER";

	// 虚拟机CPU使用
	public static final String VM_CPU_USAGE = "VM_CPU_USAGE";
	// 虚拟机memory使用
	public static final String VM_MEMORY_USAGE = "VM_CPU_USAGE";
	
	//虚拟机使用情况
	public static final String VM_USAGE = "VM_USAGE";

}
