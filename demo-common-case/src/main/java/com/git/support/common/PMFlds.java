package com.git.support.common;

/**
 * 物理机安装相关参数
 * @version 1.0 2013-12-11
 * @see
 */
public final class PMFlds {
	
	public static final String OS_TYPE = "OS_TYPE";
	
	public static final String HOST_NAME = "HOST_NAME";
	
	public static final String DEVICE_ID = "DEVICE_ID";
	
	public static final String MGMT_IP = "MGMT_IP";
	
	public static final String GATEWAY = "GATEWAY";
	
	public static final String IP_MASK = "IP_MASK";
	
	public static final String SN_CODE = "SN_CODE";
	
	public static final String MAC_ADDR = "MAC_ADDR";
	
	public static final String NIC_MACS = "NIC_MACS";
	
	public static final String BOOTOS_IP = "BOOTOS_IP";
	
	public static final String VM_MANAGER_TYPE = "VM_MANAGER_TYPE"; //虚拟机管理机类型，取值为VCENTER，HMC
	public static final String VM_MANAGER_IP = "VM_MANAGER_IP";
	public static final String OP_TYPE = "OP_TYPE";
	public static final String VM_IP = "VM_IP";
	public static final String VM_NAME = "VM_NAME";
	public static final String USER_NAME = "USER_NAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String VMLIST = "VMLIST";
	
	
	public static final String[] VM_OP_FIELDS = {VM_MANAGER_TYPE, VM_MANAGER_IP, 
		OP_TYPE, VM_IP, VM_NAME};

	public static final Object VM_HOST_NAME = "VM_HOST_NAME";
	
	
	
	/**
	 * 与com.ccb.iomp.cloud.core.netip.common.enums.NwAddrType同。
	 * 网络地址类型的定义      
	 * 定义了资源池地址分配所用到的网络地址类型编码
	 * */
	public static final class NwAddrType {
		public static final String PM_PROD = "HP";		/**< 物理机生产地址 */
		public static final String PM_MGMT = "HM";		/**< 物理机管理地址 */
		public static final String PM_VMOTION = "HV";	/**< 物理机管理-vMontion地址 */
		public static final String PM_ILO = "HL";		/**< 物理机管理-ILO地址 */
		public static final String PM_FSP1 = "HF1";		/**< 物理机管理-FSP1地址 */
		public static final String PM_FSP2 = "HF2";		/**< 物理机管理-FSP2地址 */
		public static final String NAS_PROD = "NP";		/**< NAS存储生产地址 */
		public static final String NAS_MGMT = "NM";		/**< NAS存储管理地址 */    
	}

	
	public static final class Linux{
		public static final  String ROOT_PWD ="ROOT_PWD";
	}
	
	public static final class Esxi{
		public static final String VMK0_IP = "VMK0_IP";// 对应vss0pg1
		public static final String VMK1_IP = "VMK1_IP";// 对应vss0pg2
		public static final String VMK2_IP = "VMK2_IP";// 对应vss1pg1
		public static final String DEF_GATEWAY = "DEF_GATEWAY";
		public static final String MAP_GATEWAY = "MAP_GATEWAY";
		public static final String LOGS_IP = "LOGS_IP";
		public static final String VSS0PG1 = "VSS0PG1"; // pg名称
		public static final String VSS0PG1VLAN = "VSS0PG1VLAN";// pg vlan id
		public static final String VSS0PG2 = "VSS0PG2";
		public static final String VSS0PG2VLAN = "VSS0PG2VLAN";
		public static final String VSS1PG1 = "VSS1PG1";
		public static final String VSS1PG1VLAN = "VSS1PG1VLAN";
		public static final String VS0_VMMGNT_VLAN_RANGE = "VS0_VMMGNT_VLAN_RANGE";
		public static final String VS1_PRODUCT_VLAN_RANGE = "VS1_PRODUCT_VLAN_RANGE";
	}
	
	public static void main(String args[]){
		System.out.println(PMFlds.Esxi.VMK1_IP);
	}
		
		

}
