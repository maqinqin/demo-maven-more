
package com.git.cloud.resmgt.common;

public class CloudClusterConstants {
	//ESXI物理机的用户名
	public static final String ESXI_ROOT_USERNAME = "root";
	/**监控敏感度*/
	public static final String DAS_MONITORING_FAILURE_INTERVAL_LOW = "604800"; 
	public static final String DAS_MONITORING_MAX_FAILURES_LOW = "10800"; 
	public static final String DAS_MONITORING_MIN_UP_TIME_LOW = "120"; 
	
	public static final String DAS_MONITORING_FAILURE_INTERVAL_MIDDLE = "86400"; 
	public static final String DAS_MONITORING_MAX_FAILURES_MIDDLE = "10800"; 
	public static final String DAS_MONITORING_MIN_UP_TIME_MIDDLE = "60"; 
	
	public static final String DAS_MONITORING_FAILURE_INTERVAL_HIGH = "3600"; 
	public static final String DAS_MONITORING_MAX_FAILURES_HIGH = "10800"; 
	public static final String DAS_MONITORING_MIN_UP_TIME_HIGH = "30"; 

	public static final String VM_SENSITIVE_LOW = "1"; 
	public static final String VM_SENSITIVE_MIDDLE = "2"; 
	public static final String VM_SENSITIVE_HIGH = "3"; 
	
	public static final String DAS_ADMISSION_CONTROL_POLICY_TYPE_NUM = "1"; 
	public static final String DAS_ADMISSION_CONTROL_POLICY_TYPE_PERCENT= "2"; 
	
	public static final String VCENTER_USERNAME = "root";
	public static final String VCENTER_URL_HTTPS = "https://"; 
	public static final String VCENTER_URL_SDK = "/sdk"; 
	
	public static final boolean DPM_BEHAVIOR_CLOSE_1 = false; 
	public static final boolean DPM_BEHAVIOR_OPEN = true;
	
	public static final String IS_DRS_YES = "1"; 
	public static final String IS_DRS_NO = "0";
	
	public static final boolean IS_DRS_CLOSE = false; 
	public static final boolean IS_DRS_OPEN = true;
	
	/**
	 * cluster dpm管理策略
	 */
	public static final String DPM_BEHAVIOR_AUTOMATED = "1";
	public static final String DPM_BEHAVIOR_MANUAL = "2";
	public static final String DPM_BEHAVIOR_CLOSE = "3";
	
	/**
	 * cluster dpm管理策略
	 */
	public static final String DAS_VM_MONITORING_CLOSE = "1";
	public static final String DPM_BEHAVIOR_MANUAL_VM = "2";
	public static final String DPM_BESHAVIOR_CLOSE_APPLY = "3";
	
	
	public static final String DAS_CONFIG_ENABLED_CLOSE="3";
	
	public static final boolean DAS_CONFIG_ENABLED_CLOSE_FALSE = false; 
	public static final boolean DAS_CONFIG_ENABLED_CLOSE_TRUE = true;
	
	public static final String ADMISSION_CONTROL_ENABLED_OPEN = "1";
	public static final String ADMISSION_CONTROL_ENABLED_CLOSE = "2";
	
	public static final boolean ADMISSION_CONTROL_ENABLED_OPEN_TRUE = true; 
	public static final boolean ADMISSION_CONTROL_ENABLED_CLOSE_FALSE = false;
	
	
	public static final String DAS_CONFIG_ENABLED_OPEN="1";
	
/** 默认值**/
	
	//接入控制开关
	//public  final static String IS_ADMISSION_CONTROL = "1";
	//接入控制级别
	//public  final static String DAS_FAILOVER_LEVEL = "1";
	//接入控制策略类型
	//public  final static String ADMISSION_POLICY_CODE = "1";
	//群集允许的主机故障数目
	//public  final static String HOST_BAD_NUM = "1";
	//故障切换空间容量保留的群集cpu百分比
	//public  final static String KEEP_RES_CPU = "25";
	//故障切换空间容量保留的群集内存百分比
	//public  final static String KEEP_RES_MEM = "25";
	//主机隔离响应策略
	//public  final static String HOST_RESPOND_CODE = "1";
	//虚机重新启动优先级
	//public  final static String VM_RESTART_PRIORITY_CODE = "3";
	
	//虚拟机监控状态		
	public  final static boolean DAS_MONITORING_CLUSTER_SETTING_FLAG = false;
	public  final static boolean VM_MONITOR_STATUS = false;
	//虚拟机监控策略
	//public  final static String VM_MONITOR_STATUS_CODE = "1";
	//虚机故障间隔时间
	public  final static String DAS_MONITORING_FAILURE_INTERVAL = "3600";
	//最大重置次数
	public  final static String DAS_MONITORING_MAX_FAILURES = "10800";
	//最短正常运行时间
	public  final static String DAS_MONITORING_MIN_UP_TIME = "30";
	//电源管理设置开关
	//public  final static String DPM_CONFIG_ENABLED = "1";
	//电源管理设置策略级别
	//public  final static String DPM_MANAGE_CODE = "1";
	//电源管理阈值
	//public final static String  DPM_THRESHOLD = "3";
	//drs设置开关
	public  final static boolean IS_DRS = false;
	//drs设置策略级别
	//public  final static String DRS_LEVEL_CODE = "1";
	//drs迁移阈值
	//public final static String DRS_MIGRATION_THRESHOLD= "3";
	//主机监控状态
	//public static final String IS_HOST_MONITOR="1";
	
	//敏感度
	//public static final String MONITOR_SENSITIVE_CODE="1";
	
	public static final String VM_RES_CLASS = "VM";
	public static final String VM_RES_TYPE = "VMWARE";
	public static final String VMK0 = "vmk0";
	public static final String HOST_POWER_A="A";
	public static final String HOST_POWER_B="B";
	
	
	public final static String DEVICE_MODEL_X3550 = "X3550";//
	public final static String DEVICE_MODEL_X3650 = "X3650";//
	public final static String DEVICE_MODEL_X3850 = "X3850";//
	public final static String DEVICE_MODEL_POWER750 = "P750";//
	public final static String DEVICE_MODEL_POWER780 = "P780";//
	public final static String DEVICE_MODEL_RX2800 = "RX2800";//HP服务器-机架
	public final static String DEVICE_MODEL_RX9900 = "RX9900";//HP服务器-刀箱
	public final static String DEVICE_MODEL_SUPERDOME2 = "SUPERDOME2";//HP服务器-刀箱
	public final static String DEVICE_MODEL_POWER720 = "P720";//
	public final static String DEVICE_MODEL_POWER740 = "P740";//
	public final static String DEVICE_MODEL_C7000 = "C7000";//HP 刀箱型号
	
	public final static String INDEX_NO = "";
	public final static String HOST_ID = "";
	public final static String DATASTORE_ID = "";
	
	public final static String SUCCESS_KEY = "success";
	public final static String ERROR_KEY = "error";
	
	public final static String COBBLER_SYSTEMADD = "cobbler_systemadd";
	public final static String COBBLER_FORWARD = "cobbler_forward";
}
