package com.git.support.common;

/**
 * @author liufei
 * @version 1.0 2013-5-7
 * @see
 */
public class VmGlobalConstants {
	// 虚机类型
	public static final String VM_TYPE_KVM = "KVM";

	public static final String VM_TYPE_XEN = "xen";

	/**
	 * 操作失败重复执行次数
	 */
	public static final int RE_EXECUTE_COUNT = 2;

	/**
	 * 虚机类型 1-windows 2-linux
	 */
	public static final String VM_TYPE_WIN = "1";

	public static final String VM_TYPE_LINUX = "2";

	/**
	 * 接口类型
	 */
	public static final String INTERFACE_TYPE_VCENTER = "1";

	public static final String INTERFACE_TYPE_ESXI = "2";

	/**
	 * vCenter根节点folder名称
	 */
	public static final String VC_ROOT_FOLDER_NAME = "host";

	/**
	 * vCenter下资源类型 Folder|Datacenter|Cluster
	 */
	public static final String VC_RESOURCE_TYPE_FOLDER = "Folder";

	public static final String VC_RESOURCE_TYPE_DATACENTER = "Datacenter";

	public static final String VC_RESOURCE_TYPE_CLUSTER = "ClusterComputeResource";

	/**
	 * ESXi主机SSL证书指纹
	 */
	// public static final String HOST_SSL_THUMBPRINT =
	// "BE:68:1E:DC:3E:D8:4F:B2:66:55:DF:68:E3:DA:A7:B3:29:CC:4C:15";//"1B:78:64:B6:67:05:B8:A3:FF:D5:ED:15:A6:64:70:59:3E:17:2D:86";
	public static final String HOST_SSL_THUMBPRINT = "46:30:7D:0F:05:E4:17:B1:F6:27:B2:0E:D4:FD:51:E5:2E:91:CE:2A";
	/**
	 * cluster的das接入控制策略类型
	 */
	// 设置群集允许的主机故障数目策略
	public static final String DAS_AC_POLICY_TYPE_HOST_COUNT = "1";
	// 设置作为故障切换空间容量保留的群集资源百分比策略
	public static final String DAS_AC_POLICY_TYPE_RESOURCE_PERCENT = "2";
	/**
	 * cluster主机隔离响应
	 */
	// 保持
	public static final String DAS_VM_ISOLATION_RESPONSE_NONE = "NONE";
	// 关闭电源
	public static final String DAS_VM_ISOLATION_RESPONSE_POWEROFF = "POWER_OFF";
	// 关机
	public static final String DAS_VM_ISOLATION_RESPONSE_SHUTDOWN = "SHUTDOWN";
	/**
	 * cluster虚机重新启动优先级
	 */
	// 禁用
	public static final String DAS_VM_RESTART_PRIORITY_DISABLED = "1";
	// 低
	public static final String DAS_VM_RESTART_PRIORITY_LOW = "2";
	// 中
	public static final String DAS_VM_RESTART_PRIORITY_MEDIUM = "3";
	// 高
	public static final String DAS_VM_RESTART_PRIORITY_HIGH = "4";

	/**
	 * cluster VM监控状态
	 */
	// 禁用
	public static final String DAS_VM_MONITORING_STATE_DISABLED = "1";
	// 仅虚机监控
	public static final String DAS_VM_MONITORING_STATE_VM_ONLY = "2";
	// 虚机和应用监控
	public static final String DAS_VM_MONITORING_STATE_VM_AND_APP = "3";

	// 监控数据存储信号策略
	public static final String DAS_HB_DS_POLICY_ALL_FEASIBLE = "ALL_FEASIBLE_DS";
	public static final String DAS_HB_DS_POLICY_ALL_FEASIBLE_WITH_USER_PREFERENCE = "ALL_FEASIBLE_DS_WITH_USER_PREFERENCE";
	public static final String DAS_HB_DS_POLICY_USER_SELECTED = "USER_SELECTED_DS";

	/**
	 * cluster dpm管理策略
	 */
	public static final String DPM_BEHAVIOR_AUTOMATED = "AUTOMATED";
	public static final String DPM_BEHAVIOR_MANUAL = "MANUAL";

	/**
	 * cluster drs管理策略
	 */
	public static final String DRS_BEHAVIOR_FULLY_AUTOMATED = "FULLY_AUTOMATED";
	public static final String DRS_BEHAVIOR_PARTIALLY_AUTOMATED = "PARTIALLY_AUTOMATED";
	public static final String DRS_BEHAVIOR_MANUAL = "MANUAL";

	/**
	 * 管理流量操作类型
	 */
	public static final String CONF_MANAGEMENT_ADD = "CONF_MANAGEMENT_ADD";
	public static final String CONF_MANAGEMENT_REMOVE = "CONF_MANAGEMENT_REMOVE";

	/**
	 * 上传到windows服务器脚本路径
	 */
	public static final String GUEST_WIN_SCRIPT_PATH = "c:\\backupfile\\csaction\\bin\\"; // "c:\\test\\";
	/**
	 * 执行虚机脚本分隔符
	 */
	public static final String SPLIT_FLAG_REG = "\\$\\$";

	/**
	 * 执行虚机脚本分隔符
	 */
	public static final String SPLIT_FLAG = "$$";

	/**
	 * 回收资源类型
	 */
	public static final String DESTORY_TYPE_VM = "DESTORY_TYPE_VM";

	/**
	 * 虚机电源操作类型
	 */
	public static final String VM_POWERON = "poweron";
	public static final String VM_POWEROFF = "poweroff";
	public static final String VM_RESET = "reset";
	public static final String VM_SUSPEND = "suspend";
	public static final String VM_REBOOT = "reboot";
	public static final String VM_SHUTDOWN = "shutdown";
	public static final String VM_STANDBY = "standby";
	public static final String VM_STATE = "state";
	public static final String VM_RESUME = "resume";

	/**
	 * 虚机状态
	 */
	public static final String VM_STATE_POWERON = "poweron";
	public static final String VM_STATE_POWEROFF = "poweroff";
	public static final String VM_STATE_RESET = "reset";
	public static final String VM_STATE_SUSPEND = "suspend";
	public static final String VM_STATE_PAUSED = "paused";
	public static final String VM_STATE_REBOOT = "reboot";
	public static final String VM_STATE_SHUTDOWN = "shutdown";
	public static final String VM_STATE_STANDBY = "standby";
	public static final String VM_STATE_STATE = "state";
	public static final String VM_STATE_RESUME = "resume";

	/**
	 * 接口操作类型,包括新增、修改
	 */
	public static final String INTERFACE_OPER_TYPE_ADD = "INTERFACE_OPER_TYPE_ADD";
	public static final String INTERFACE_OPER_TYPE_UPDATE = "INTERFACE_OPER_TYPE_UPDATE";

	/**
	 * datastore类型
	 */
	public static final String DATASTORE_TYPE_NAS = "DATASTORE_TYPE_NAS";
	public static final String DATASTORE_TYPE_VMFS = "DATASTORE_TYPE_VMFS";

	/**
	 * host电源状态类型
	 */
	public static final String HOST_POWER_STATE_POWERED_ON = "POWERED_ON";
	public static final String HOST_POWER_STATE_POWERED_OFF = "POWERED_OFF";
	public static final String HOST_POWER_STATE_STAND_BY = "STAND_BY";
	public static final String HOST_POWER_STATE_UNKNOWN = "UNKNOWN";

	/**
	 * 虚机电源状态类型
	 */
	public static final String VM_POWER_STATE_POWERED_ON = "POWERED_ON";
	public static final String VM_POWER_STATE_POWERED_OFF = "POWERED_OFF";
	public static final String VM_POWER_STATE_SUSPENDED = "SUSPENDED";

	public static final String VMOTION_HIGH_PRIORITY = "VMOTION_HIGH_PRIORITY";

	public static final String VMOTION_TYPE_RELOCATE = "VMOTION_TYPE_RELOCATE";

	public static final String VMOTION_DEFAULT_PRIORITY = "VMOTION_DEFAULT_PRIORITY";

	public static final String VMOTION_LOW_PRIORITY = "VMOTION_LOW_PRIORITY";

	public static final String VMOTION_TYPE_MIGRATE = "VMOTION_TYPE_MIGRATE";
	/**
	 * 虚机快照操作类型（snapshot）
	 */
	public static final String SNAPSHOT_LIST = "LIST";
	public static final String SNAPSHOT_CREATE = "CREATE";
	public static final String SNAPSHOT_REMOVE = "REMOVE";
	public static final String SNAPSHOT_REVERT = "REVERT";
	public static final String SNAPSHOT_REMOVEALL = "REMOVEALL";

}
