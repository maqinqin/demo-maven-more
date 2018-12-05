package com.git.cloud.handler.automation.se.common;

import java.util.Map;

import com.google.common.collect.Maps;

public class StorageConstants {

	// 数据中心英文名
	public static final String DATACENTER_ENAME="DATACENTER_ENAME";
	
	public static final String PROJECTABBR = "PROJECTABBR";
	
	public static final String SERVERFUNCTION = "SERVERFUNCTION";
	
	public static final String DESCRIBE = "DESCRIBE";
	
	//主机类型虚拟机
	public static final String DEV_TYPE_VM = "VM";
	public static final String DEV_TYPE_HM = "HM";
	
	public static final String CLOUD_SERVICE_TYPE = "CLOUD_SERVICE_TYPE";
	public static final String CLOUD_SERVICE_TYPE_VM_INSTALL = "VM_INSTALL";
	public static final String CLOUD_SERVICE_TYPE_STORAGE_INSTALL = "STORAGE_INSTALL";
	public static final String DB_NAME = "DB_NAME";
	public static final String RAC_NAS_PR = "rachb";
	
	// 请求存储资源adapter类型
	public static final String RESOURCE_TYPE_STORAGE= "RESOURCE_TYPE_STORAGE";
	public static final String RESOURCE_TYPE_STORAGE_NETAPP= "NETAPP";
	// 请求交换机资源adapter类型
	public static final String RESOURCE_TYPE_SWITCH = "RESOURCE_TYPE_SWITCH";
	// 数据使用类型
	public static final String DATA_APP_TYPE = "DATA_APP_TYPE";
	public static final String DATA_APP_TYPE_DB = "DB";
	public static final String DATA_APP_TYPE_FILE = "FILE";
	
	// 存储类型
	public static final String STORAGE_APPLY_TYPE_SAN = "SAN";
	public static final String STORAGE_APPLY_TYPE_NAS = "NAS";
	
	// 存储应用类型
	public static final String STORAGE_APP_TYPE_CODE = "STORAGE_APP_TYPE_CODE";
	public static final String STORAGE_APP_TYPE_FILE = "FILE";
	public static final String STORAGE_APP_TYPE_VMWARE = "VMWARE";
	// 资源池类型
	public static final String RES_POOL_LEVEL = "RES_POOL_LEVEL";
	public static final String RES_POOL_LEVEL_PLATINUM = "PLATINUM";
	public static final String RES_POOL_LEVEL_GLOD = "GLOD";
	public static final String RES_POOL_LEVEL_SILVER = "SILVER";
	public static final String RES_POOL_LEVEL_COPPER = "COPPER";
	public static final String IS_MORRIOR_STORAGE = "IS_MORRIOR_STORAGE";
	public static final String IS_NAS_HEARTBEAT = "IS_NAS_HEARTBEAT";
	// 服务级别对应存储数量
	public static final int PLATINUM_STORAGE_SIZE = 2;
	public static final int GLOD_STORAGE_SIZE = 2;
	public static final int SILVER_STORAGE_SIZE = 1;
	public static final int COPPER_STORAGE_SIZE = 1;

	// 资源池服务级别规则参数
	public static final String AVAILABILITY_TYPE = "AVAILABILITY_TYPE";
	public static final String PERFORMANCE_TYPE = "PERFORMANCE_TYPE";
	public static final String RESPONSE_TIME = "RESPONSE_TIME";
	public static final String IOPS = "IOPS";
	public static final String MBPS = "MBPS";
	public static final String SAN_SERVICE_LEVEL = "SAN_SERVICE_LEVEL";
	public static final String NAS_SERVICE_LEVEL = "NAS_SERVICE_LEVEL";

	public static final String LEVLE_ADVANCED = "ADVANCED";
	public static final String LEVLE_HIGH = "HIGH";
	public static final String LEVLE_MIDDLE = "MIDDLE";
	public static final String LEVLE_LOW = "LOW";

	// 集群类型参数
	public static final String CLUSTER_TYPE ="CLUSTER_TYPE";
	public static final String CLUSTER_TYPE_RAC = "RAC";
	public static final String CLUSTER_TYPE_HA = "HA";
	public static final String CLUSTER_TYPE_SINGLE = "SINGLE";

	public static final String HEARTBEAT_COUNT = "_HEARTBEAT_COUNT";

	// 心跳盘名称
	public static final String RAC_HEARTBEAT_NAME = "OracleSys";
	public static final String HA_HEARTBEAT_NAME = "HAHeartBeat";

	//存储可用率
	public static final String AVAILABLE_RATE_THRESHOLD = "AVAILABLE_RATE_THRESHOLD";
	public static final String NAS_AVAILABLE_RATE_THRESHOLD = "NAS_AVAILABLE_RATE_THRESHOLD";
	// 分盘参数
	public static final String NAME = "NAME";
	public static final String COUNT = "COUNT";
	public static final String SIZE = "SIZE";

	// 管理机相关参数
	public static final String SMISISERVERIP = "SMISISERVER_IP";
	public static final String USERNAME = "USER_NAME";
	public static final String USERPASSWD = "USER_PASSWD";
	public static final String NAMESPACE = "NAME_SPACE";
	// 存储基本信息基本参数
	public static final String OS_TYPE ="OS_TYPE";
	public static final String OS_NAME ="OS_NAME";
	public static final String OS_NAME_AIX = "AIX";
	public static final String OS_NAME_HP_UX = "HP";
	public static final String OS_NAME_LINUX = "LINUX";
	public static final String OS_VERSION ="OS_VERSION";
	public static final String OS_VERSION_HU_UX_1111 = "11.11";
	public static final String OS_VERSION_HU_UX_1123 = "11.23";
	public static final String OS_VERSION_HU_UX_1131 = "11.31";
	
	public static final String DATA_TYPE ="DATA_TYPE";
	public static final String HBA_WWN = "HBA_WWN";
	public static final int HBA_WWN_FOUR = 4;
	public static final int HBA_WWN_TWO = 2;
	public static final String SUID_USABLE = "SUID_USABLE";
	public static final String SU_ID = "SU_ID";
	public static final String SN="SN";
	public static final String IS_PRIOR = "IS_PRIOR";
	public static final String STORAGE_MGR = "STORAGE_MGR";
	public static final String LUN_SELECTED = "LUN_SELECTED";
	public static final String STORAGE_INFO = "STORAGE_INFO";
	public static final String STORAGE_DEV_ID = "STORAGE_DEV_ID";
	public static final String STORAGE_DEV_SN = "STORAGE_DEV_SN";
	public static final String STORAGE_MODEL = "STORAGE_MODEL";
	public static final String STORAGE_MODEL_CODE = "STORAGE_MODEL_CODE";
	public static final String STORAGE_MODEL_VMAX = "VMAX";
	public static final String STORAGE_MODEL_VSP = "VSP";
	public static final String STORAGE_MODEL_FAS = "FAS";
	public static final String STORAGE_MODEL_NETAPP_FAS = "NATAPP FAS";
	
	public static final String STORAGE_DEV_TYPE_EMC = "EMC";
	public static final String STORAGE_DEV_TYPE_HITACHI = "HDS";
	public static final String STORAGE_DEV_TYPE_NETAPP = "NETAPP";
	public static final String STORAGE_DEV_TYPE = "STORAGE_DEV_TYPE";
	
	public static final String STORAGE_TYPE_CODE_NETAPP_FAS = "10";
	public static final String STORAGE_TYPE_CODE_EMC_VMAX="VMAX";
	public static final String STORAGE_TYPE_CODE_HDS_VSP="VSP";
	
	public static final String STORAGE_TYPE = "STORAGE_TYPE";
	public static final String STORAGE_SN_KEY = "STORAGE_SN";
	public static final String STORAGE_SN_MAP = "STORAGE_SN_MAP";
	public static final String STORAGEONE = "STORAGE_SN1";
	public static final String STORAGETWO = "STORAGE_SN2";
	public static final String UNIT_INFO = "UNIT_INFO";
	public static final String STORAGE_POOL_TYPE = "POOL_TYPE";
	
	public static final String LUN_STATUS_OCCYPY = "1";//占用标志
	public static final String LUN_STATUS_USED = "2";//使用表示
	
	public static final String STORAGEMGR_ID = "STORAGEMGR_ID";
	public static final String STORAGE_RESULT = "STORAGE_RESULT";
	
	//选择存储端口组
	public static final String SELECTED_PORT_GROUP = "SELECTED_PORT_GROUP";
	public static final String PORT_GROUP = "PORT_GROUP";
	public static final String FA_WWN = "FA_WWN";
	public static final String FA_WWNONE = "FA_WWN1";
	public static final String FA_WWNTWO = "FA_WWN2";
	public static final String GROUP_ID = "GROUP_ID";
	
	//获取zone信息
	public static final String FABRIC_NAME = "FABRIC_NAME";
	public static final String SERVICE_IP = "SERVICE_IP";
	public static final String SERVICE_PORT = "SERVICE_PORT";
	public static final String SWITCH_TYPE = "SWITCH_TYPE";
	public static final String SWITCH_TYPE_CISCO = "CISCO";
	public static final String SWITCH_CISCO_POSTFIX_ORIGINAL="-1";
	public static final String SWITCH_CISCO_POSTFIX_FINAL="-Final";
	public static final String SWITCH_BROCADE_FID_POSTFIX_ORIGINAL="-fid-Original";
	public static final String SWITCH_BROCADE_FID_POSTFIX_FINAL="-fid-Final";
	public static final String SWITCH_BROCADE_ALL_POSTFIX_ORIGINAL="-all-Original";
	public static final String SWITCH_BROCADE_ALL_POSTFIX_FINAL="-all-Final";
	public static final String SWITCH_ORIGINAL = "-1";
	public static final String SWITCH_FINAL = "-Final";
	public static final String SWITCH_TYPE_BROCADE = "BROCADE";
	public static final String FABRIC_INFO = "FABRIC_INFO";

	public static final String VF_NAME = "VF_NAME";
	
	public static final String FABRIC1 = "FABRIC1";
	public static final String FABRIC2 = "FABRIC2";
	
	public static final String FABRIC_FINISHED = "FABRIC_FINISHED";
	
	public static final String CORE_SWITCH = "CORE_SWITCH";
	
	public static final String CISCO_ORIGINAL_FILENAME = "original";
	public static final String CISCO_FINAL_FILENAME = "final";
	
	public static final String VIEW_INFO1 = "VIEW_INFO1";
	public static final String VIEW_INFO2 = "VIEW_INFO2";
	public static final String VIEW_NAME = "VIEW_NAME";
	public static final String IG_NAME = "IG_NAME";
	public static final String PG_NAME = "PG_NAME";
	public static final String SG_NAME = "SG_NAME";
	public static final String IG_MEMBER = "IG_MEMBER";
	public static final String PG_MEMBER = "PG_MEMBER";
	public static final String SG_MEMBER = "SG_MEMBER";
	
		
	public static final String VSP_LOGIN = "VSP_LOGIN";
	public static final String VSP_LOGOUT = "VSP_LOGOUT";
	public static final String VSP_LOCK = "VSP_LOCK";
	public static final String VSP_UNLOCK = "VSP_UNLOCK";
	public static final String VSP_SYSTEM_MACHINE_CODE = "8";
	public static final String VSP_SERIES_NUMBER = "VSP_SERIES_NUMBER";
	
	public static final String MACHINE_CODE_EMC_SMIS = "1";
	public static final String MACHINE_CODE_EMC_SUPER = "3";
	public static final String MACHINE_CODE_CISCO_SMIS = "2";
	public static final String MACHINE_CODE_BROCADE_SMIS = "4";
	public static final String MACHINE_CODE_NETAPP_SMIS = "5";
	public static final String MACHINE_CODE_HDS_SMIS = "6";
	public static final String MACHINE_CODE_HDS_SUPER = "7";
	public static final String MACHINE_CODE_HDS_SYSTEM = "8";
	public static final boolean SMIS_TRUE = true;
	public static final boolean SMIS_FALSE = false;
	
	public static final String VSP_HOSTGROUP_GID="VSP_HOSTGROUP_GID";
	public static final String VSP_HOSTGROUP_CONFIG = "VSP_HOSTGROUP_CONFIG";
	public static final String VSP_HOSTGROUP_SCAN = "VSP_HOSTGROUP_SCAN";
	
	public static final String STORAGEONE_IG_FLAG_FINISHED = "STORAGEONE_IG_FLAG_FINISHED";
	public static final String STORAGEONE_IG_FLAG_CHECK_FINISHED = "STORAGEONE_IG_FLAG_CHECK_FINISHED";
	public static final String STORAGETWO_IG_FLAG_FINISHED = "STORAGETWO_IG_FLAG_FINISHED";
	public static final String STORAGETWO_IG_FLAG_CHECK_FINISHED = "STORAGETWO_IG_FLAG_CHECK_FINISHED";
	
	public static final String STORAGE_BACKUP_FILE1 = "STORAGE_BACKUP_FILE1";
	public static final String STORAGE_BACKUP_FILE2 = "STORAGE_BACKUP_FILE2";
	
	public static final String CHECK_IG_NAME_VMAX = "CHECK_IG_NAME_VMAX";
	public static final String CHECK_PG_NAME_VMAX = "CHECK_PG_NAME_VMAX";
	public static final String CHECK_SG_NAME_VMAX = "CHECK_SG_NAME_VMAX";
	public static final String FA_PORT_FREFIX_VMAX = "FA_PORT_FREFIX_VMAX";
	
	public static final String IG_FLAG_WWN_KEY_VMAX = "IG_FLAG_WWN_KEY_VMAX";
	public static final String IG_FLAG_ENABLE_KEY_VMAX = "IG_FLAG_ENABLE_KEY_VMAX";
	public static final String IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_C = "IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_C";
	public static final String IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_SC3 = "IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_SC3";
	public static final String IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_SPC2 = "IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_SPC2";
	public static final String IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_OS2007 = "IG_FLAG_ENABLE_PARAMETER_KEY_VMAX_AIX_OS2007";
	public static final String BackupBasePath = "BackupBasePath";
	
	public static final String STORAGE_ASSING_INFO = "STORAGE_ASSING_INFO";
	public static final String EMC_HDISK_POWER = "EMC_hdiskpower";
	public static final String HDISKPOWER_NUMBER="hdiskpower_number";
	public static final String HDS_HDISK_POWER = "HDS_hdisk";	
	public static final String HDISK_NUMBER="hdisk_number";

	
	public static final String RAC_NAS_HEARTBEAT_VOL_PR = "/vol/";
	
	public static final String RAC_NAS_SN = "RAC_NAS_SN";
	public static final String RAC_NAS_VOL_POOL = "RAC_NAS_VOL_POOL";
	public static final String RAC_NAS_MODEL = "RAC_NAS_MODEL";
	public static final String HEARTBEAT_VOLUME_NAME = "HEARTBEAT_VOLUME_NAME";
	public static final String NAS_HEARTBEAT_MOUNTPOINT = "VOL_MOUNT_POINT";
	
	public static final String CREATE_HEARTBEAT_VOL_INFO = "CREATE_HEARTBEAT_VOL_INFO";
	public static final String CONFIG_HEARTBEAT_VOL_INFO = "CONFIG_HEARTBEAT_VOL_INFO";
	public static final String CREATE_HEARTBEAT_QTREE_NAME = "CREATE_HEARTBEAT_QTREE_NAME";
	public static final String CREATE_QTREE_NAME = "CREATE_QTREE_NAME";
	
	public static final String EXPORTFS_TIME = "EXPORTFS_TIME";
		
	public static final String NAS_SN = "NAS_SN";
	public static final String NAS_VOL_POOL = "NAS_VOL_POOL";
	public static final String NAS_MODEL = "NAS_MODEL";
	public static final String IS_VOL_SHARED = "IS_VOL_SHARED";
	public static final String VOL_SHARE = "VOL_SHARE";
	
	//NAS共享类型
	public static final String NAS_SHARE_TYPE = "NAS_SHARE_TYPE";
	public static final String NAS_SHARE_TYPE_CIFS = "CIFS";
	public static final String NAS_SHARE_TYPE_NFS = "NFS";
	
	//NAS交易类型
	public static final String NAS_TRADE_TYPE="TRADE_TYPE";
	public static final String NAS_TRADE_TYPE_OLAP="OLAP";
	public static final String NAS_TRADE_TYPE_OLTP="OLTP";
	
	//NAS分配类型
	public static final String ASSIGN_TYPE_VOLUME = "VOLUME";
	public static final String ASSIGN_TYPE_QTREE = "QTREE";
	public static final String ASSIGN_TYPE_ZONE = "ZONE";
	public static final String ASSIGN_TYPE_VIEW = "VIEW";
	public static final String ASSIGN_TYPE_LUN = "LUN";
	public static final String ASSIGN_TYPE_IG = "IG";
	public static final String ASSIGN_TYPE_PG = "PG";
	public static final String ASSIGN_TYPE_SG = "SG";
	
	//NAS设备生产IP地址类型
	public static final String IP_ADDR_TYPE_CODE_NP = "NP";
	//NAS设备管理IP地址类型
	public static final String IP_ADDR_TYPE_CODE_NM = "NM";
	
	//IP表存储设备类型编码
	public static final String IP_STORAGE_TYPE = "4";
	
	public static final String QTREE_MODE = "4/2/1";
	
	public static final Map<String,Map<String,String>> dev_ip_type = Maps.newHashMap();
	
	public static final Map<String,String> aix_hm_ip_type = Maps.newHashMap();
	public static final Map<String,String> aix_vm_ip_type = Maps.newHashMap();

	public static final Map<String,String> hp_hm_ip_type = Maps.newHashMap();
	//public static final Map<String,String> hp_vm_ip_type = Maps.newHashMap();

	public static final Map<String,String> linux_hm_ip_type = Maps.newHashMap();
	public static final Map<String,String> linux_vm_ip_type = Maps.newHashMap();
	
	public static final Map<String,String> storage_ip_type = Maps.newHashMap();
	
	public static final Map<String,String> os_ms_type = Maps.newHashMap();
	public static final Map<String,String> dev_hv_type = Maps.newHashMap();
	
	public static final Map<String,String> lun_type = Maps.newHashMap();
	
	public static final String SSH_SUCCESS="0";
	static{
		os_ms_type.put("AIX", "2");
		os_ms_type.put("LINUX", "1");
		os_ms_type.put("HP", "3");
		os_ms_type.put("STORAGE", "4");
		
		dev_hv_type.put("VM", "V");
		dev_hv_type.put("HM", "H");
		
		aix_hm_ip_type.put("P", "PHP");
		aix_hm_ip_type.put("M", "PHM");
		dev_ip_type.put("AIX_HM", aix_hm_ip_type);
		aix_vm_ip_type.put("P", "PVP");
		aix_vm_ip_type.put("M", "PVM");
		dev_ip_type.put("AIX_VM", aix_vm_ip_type);
		
		hp_hm_ip_type.put("P", "HHP");
		hp_hm_ip_type.put("M", "HHM");
		dev_ip_type.put("HP_HM", hp_hm_ip_type);
		dev_ip_type.put("HP_VM", hp_hm_ip_type);
		
		linux_hm_ip_type.put("P", "XHP");
		linux_hm_ip_type.put("M", "XHM");
		dev_ip_type.put("LINUX_HM", linux_hm_ip_type);
		
		linux_vm_ip_type.put("P", "XVP");
		linux_vm_ip_type.put("M", "XVM");
		dev_ip_type.put("LINUX_VM", linux_vm_ip_type);
		
		storage_ip_type.put("P", "SNP");
		storage_ip_type.put("M", "SNM");
		dev_ip_type.put("STORAGE_IP_TYPE", storage_ip_type);
		
		lun_type.put("Data", "DATADISK");
		lun_type.put("Arch", "ARCHDISK");
		lun_type.put("OracleSys", "SYSDISK");
		lun_type.put("Heartbeat", "SYSDISK");
		lun_type.put("HeartBeat", "SYSDISK");
		lun_type.put("RACHeartBeat", "SYSDISK");
		lun_type.put("DataM", "DATADISKM");
		lun_type.put("ArchM", "ARCHDISKM");
		lun_type.put("OracleSysM", "SYSDISKM");
		lun_type.put("HeartbeatM", "SYSDISKM");
		lun_type.put("HeartBeatM", "SYSDISKM");
		lun_type.put("RACHeartBeatM", "SYSDISKM");
	}
	
	/*
	 * add by liyongjie 2014-05-15
	 */
	/*
	 * SSH
	 */
	public static final String SSH_ECHO_INFO = "ECHO_INFO";
	public static final String SSH_ERROR_INFO = "ERROR_INFO";
	public static final String SSH_EXIT_CODE = "EXIT_CODE";
	
	/*
	 * check vsp lun
	 */
	public static final String CHECK_LUN_VSP = "CHECK_LUN_VSP";
	public static final String CHECK_LUN_VSP_LIST = "CHECK_LUN_VSP_LIST";
	public static final String CHECK_LUN_SERIAL_VSP = "Serial";
	public static final String CHECK_LUN_PORTS_VSP = "PORTs";
	
	/*
	 * check vsp view
	 */
	public static final String CHECK_VIEW_VSP = "CHECK_VIEW_VSP";
	public static final String CHECK_IG_VSP = "CHECK_IG_VSP";
	public static final String CHECK_SG_VSP = "CHECK_SG_VSP";
	public static final String CHECK_HBA_LOGIN_VSP = "CHECK_HBA_LOGIN_VSP";
	
	//add by liufei
	public static final String POOL_NAME = "POOL_NAME";
	public static final String VOL_INFO = "VOL_INFO";
	public static final String VOL_NAME = "VOL_NAME";
	public static final String VOL_SIZE = "VOL_SIZE";
	public static final String CREATE_VOL_INFO = "CREATE_VOL_INFO";
	public static final String QTREES = "QTREES";
	public static final String QTREE_NAME = "QTREE_NAME";
	public static final String CONFIG_VOL_INFO = "CONFIG_VOL_INFO";
	public static final String EXPORTFS_INFO = "EXPORTFS_INFO";
	public static final String ROOT_HOSTS = "ROOT_HOSTS";
	public static final String HOST_IP = "HOST_IP";
	public static final String PATH_NAME = "PATH_NAME";
	
	//节点结束标志
	public static final String DB_CHOOSE_STORAGE_FINISHED = "DB_CHOOSE_STORAGE_FINISHED";
	public static final String DB_SELECT_LUN_FINISHED = "DB_SELECT_LUN_FINISHED";
	public static final String DB_CHOOSE_PORTGROUP_FINISHED = "DB_CHOOSE_PORTGROUP_FINISHED";
	public static final String DB_GENERATE_ZONE_FINISHED = "DB_GENERATE_ZONE_FINISHED";
	public static final String DB_CRETAE_ZONE_FIRST_FINISHED = "DB_CRETAE_ZONE_FIRST_FINISHED";
	public static final String DB_CRETAE_ZONE_SECOND_FINISHED = "DB_CRETAE_ZONE_SECOND_FINISHED";
	public static final String DB_CREATE_ZONE_FIRST_CHECK_FINISHED = "DB_CREATE_ZONE_FIRST_CHECK_FINISHED";
	public static final String DB_CREATE_ZONE_SECOND_CHECK_FINISHED = "DB_CREATE_ZONE_SECOND_CHECK_FINISHED";
	public static final String DB_GENERATE_VIEW_NAME_FINISHED = "DB_GENERATE_VIEW_NAME_FINISHED";
	public static final String STORAGEONE_FINISHED = "STORAGEONE_FINISHED";
	public static final String STORAGEONE_CHECK_FINISHED = "STORAGEONE_CHECK_FINISHED";
	public static final String STORAGETWO_FINISHED = "STORAGETWO_FINISHED";
	public static final String STORAGETWO_CHECK_FINISHED = "STORAGETWO_CHECK_FINISHED";
	public static final String RAC_NAS_CHOOSE_FINISHED = "RAC_NAS_CHOOSE_FINISHED";
	public static final String CREATE_HEARTBEAT_VOL_FINISHED = "CREATE_HEARTBEAT_VOL_FINISHED";
	public static final String NAS_CHOOSE_FINISHED = "NAS_CHOOSE_FINISHED";
	public static final String CREATE_VOL_FINISHED = "CREATE_VOL_FINISHED";
	public static final String CONFIG_RAC_HEARTBEAT_VOL_FINISHED = "CONFIG_RAC_HEARTBEAT_VOL_FINISHED";
	public static final String CONFIG_VOL_FINISHED = "CONFIG_VOL_FINISHED";
	public static final String ASSIGN_LUN_CONFIRM_FINISHED = "ASSIGN_LUN_CONFIRM_FINISHED";
	//public static final 
	
	//NAS设备可用生产IP
	public static final String NAS_NPS = "NAS_NPS";
	//分配nas设备使用的生产IP
	public static final String NAS_NP_USED ="NAS_NP_USED";
	//分配nas设备使用的生产IP
	public static final String NAS_VIP ="NAS_VIP";
	//NAS分盘全路径127.0.0.1:/vol/volName/qtreeName
	public static final String NAS_PATH = "NAS_PATH";
	//NAS分盘路径 不带ip
	public static final String NAS_FILESYSTEM = "NAS_FILESYSTEM";
	//主机挂载路径
	public static final String HOST_MOUNT_PATH = "VOL_MOUNT_POINT";
}
