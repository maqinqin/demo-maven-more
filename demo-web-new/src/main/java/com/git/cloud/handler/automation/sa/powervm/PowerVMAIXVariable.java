package com.git.cloud.handler.automation.sa.powervm;

/**
 * 创建power aix 虚拟机 并安装oracle rac 需要的参数变量名称 
 * @author zhuzhaoyong
 *
 */
public class PowerVMAIXVariable {

	/**
	 *aix虚拟机参数
	 */
	public static final String SYSTEMNAME = "systemname";
	public static final String SPOT_NAME = "spot_name";
	public static final String IMAGE_NAME = "image_name";
	public static final String STORAGETYPE = "storagetype";
	public static final String NTPSERVER = "ntpserver";
	public static final String DESTNET_MASK_GATEWAY_INTERFACENAME = "desip_mask_gw_if_list";
	public static final String IP_MASK_INTERFACENAME = "ip_mask_if_list";
	public static final String HEART_IP = "heart_ip";
	public static final String HEART_MASK = "heart_mask";
	public static final String HEART_GATWAY = "heart_gatway";
	public static final String HEART_PVID = "heart_pvid";
	public static final String INTERFACE_1 = "interface_1";
	public static final String INTERFACE_0 = "interface_0";
	public static final String VIOC_PVID = "vioc_pvid";
	public static final String VIOC_GATEWAY = "vioc_gateway";
	public static final String VIOC_MASK = "vioc_mask";
	public static final String VIOC_IP = "vioc_ip";
	public static final String NIMSERVER_IP = "nimserver_ip";
	public static final String NIMSERVER_MASK = "nimserver_mask";
	public static final String NIMSERVER_GATEWAY = "nimserver_gateway";
	public static final String UNCAP_WEIGHT = "uncap_weight";
	public static final String PROFILENAME = "profilename";
	public static final String LPARNAME = "lparname";
	public static final String HOSTNAME = "hostname";
	public static final String SYSDISKM = "SYSDISKM";
	public static final String DATADISKM = "DATADISKM";
	public static final String ARCHDISKM = "ARCHDISKM";
	public static final String SYSDISK = "SYSDISK";
	public static final String DATADISK = "DATADISK";
	public static final String ARCHDISK = "ARCHDISK";
	public static final String STORAGE_RESULT = "STORAGE_RESULT";
	public static final String MAC_ADDR = "mac_addr";
	public static final String LPAR_ID = "lpar_id";
	public static final String CPU_QT = "cpu_qt";
	public static final String MEM_QT = "mem_qt";
	public static final String DISKS = "disks";
	
	public static final String PRODUCT_IP_1 = "product_ip_1";
	public static final String PRODUCT_IP_1_MASK = "product_ip_1_mask";
	public static final String PRODUCT_IP_1_GATEWAY = "product_ip_1_gateway";
	public static final String PRODUCT_IP_1_PVID = "product_ip_1_pvid";
	
	public static final String PRODUCT_IP_2 = "product_ip_2";
	public static final String PRODUCT_IP_2_MASK = "product_ip_2_mask";
	public static final String PRODUCT_IP_2_GATEWAY = "product_ip_2_gateway";
	public static final String PRODUCT_IP_2_PVID = "product_ip_2_pvid";
	
	public static final String PRODUCT_IP_3 = "product_ip_3";
	public static final String PRODUCT_IP_3_MASK = "product_ip_3_mask";
	public static final String PRODUCT_IP_3_GATEWAY = "product_ip_3_gateway";
	public static final String PRODUCT_IP_3_PVID = "product_ip_3_pvid";
	
	public static final String MIN_MEM = "min_mem";
	public static final String DESIRED_MEM = "desired_mem";
	public static final String MAX_MEM = "max_mem";
	public static final String DESIRED_PROC_UNITS = "desired_proc_units";
	
	public static final String REMOTE_SLOT_NUMBER_SCSI = "remote_slot_number_scsi";
	public static final String REMOTE_SLOT_NUMBER_FC_1 = "remote_slot_number_fc_1";
	public static final String REMOTE_SLOT_NUMBER_FC_2 = "remote_slot_number_fc_2";
	
	// 用于controlm脚本的服务ip
	public static final String SERVICE_IP_FOR_CONTROLM = "service_ip_for_controlm";

	// oracle rac 参数
	public static final String DB_BLOCK_SIZE = "DB_BLOCK_SIZE";
	public static final String DB_NAME = "DB_NAME";
	public static final String APP_NAME = "app_name";
	
	/**
	 * 配置文件中的配置项的key
	 */
	public static final String P_V_O_R_NIMSERVER_SCRIPT_PATH = "P_V_O_R.NIMSERVER_SCRIPT_PATH";
	public static final String P_V_O_R_LOCAL_ORACLE_CONF_TMP_PATH = "P_V_O_R.LOCAL_ORACLE_CONF_TMP_PATH";
	public static final String P_V_O_R_ORACLE_CONF_FILENAME = "P_V_O_R.ORACLE_CONF_FILENAME";
	public static final String P_V_O_R_ORACLE_CONF_PATH = "P_V_O_R.ORACLE_CONF_PATH";
	public static final String P_V_O_R_DB_BLOCK_SIZE = "P_V_O_R.DB_BLOCK_SIZE";
	public static final String P_V_O_R_UNCAP_FOR_ORACLE_RAC = "P_V_O_R.UNCAP_FOR_ORACLE_RAC";
	public static final String P_V_O_R_UNCAP_FOR_ONEAIX_AND_HA = "P_V_O_R.UNCAP_FOR_ONEAIX_AND_HA";
	public static final String P_V_O_R_STORAGETYPE = "P_V_O_R.STORAGETYPE";
	public static final String P_V_O_R_NOT_NULL_PARAMETERS_AIX_ORACLE_RAC = "P_V_O_R.NOT_NULL_PARAMETERS_AIX_ORACLE_RAC";
	public static final String P_V_O_R_NOT_NULL_PARAMETERS_AIX = "P_V_O_R.NOT_NULL_PARAMETERS_AIX";


	/**
	 * aix ha 常量
	 */
	public static final String RAC_PARAMETER_CONF = "rac_parameter.conf";
	/*P_V_O_R.NLS_CHARACTERSET=ALS32UTF8
			P_V_O_R.NLS_NCHAR_CHARACTERSET=AL16UTF16
			P_V_O_R.DISKMTYPE=NORMAL*/
	public static final String P_V_O_R_NLS_CHARACTERSET = "NLS_CHARACTERSET";
	public static final String P_V_O_R_NLS_NCHAR_CHARACTERSET = "NLS_NCHAR_CHARACTERSET";
	public static final String P_V_O_R_DISKMTYPE = "DISKMTYPE";
	public final static String P_V_H_VIOC_CONF_PATH = "P_V_H.VIOC_CONF_PATH";
	public final static String P_V_H_VIOC_CONF_NAME = "P_V_H.VIOC_CONF_NAME";
	
	/**
	 * 通用的配置信息
	 */
	public final static String NIM_USER_NAME = "root";
	public final static String HMC_USER_NAME = "hscroot";
	// hmc的操作用户
	public final static String HMC_USER_NAME_KEY = "HMC_USER_NAME_KEY";
//	public final static String IGNITE_USER_NAME = "root";
	public final static String IGNITE_USER_NAME_KEY = "IGNITE_USER_NAME_KEY";
	/**
	 * aix虚拟机用户名
	 */
	public static final String AIX_VM_USER = "root";

}
