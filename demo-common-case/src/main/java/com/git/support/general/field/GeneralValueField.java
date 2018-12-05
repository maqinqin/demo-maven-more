package com.git.support.general.field;


/**
 * 通用值常量配置类
 * 
 * @author zhangbh
 * @version 创建时间：2015-12-29 下午3:44:59 类说明
 */
public class GeneralValueField {
	
	public static enum IPMI{
		/**IPMI v1.5*/
		IPMI_VERSION_V15("V15"),
		/**IPMI v2.0*/
		IPMI_VERSION_V20("V20");
		
		private final String value;

		private IPMI(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static IPMI fromString(String value) {
			if (value != null) {
				for (IPMI c : IPMI.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 电源操作管理
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum PowerOperation {
		/** 打开电源 */
		POWERON("poweron"),
		/** 关闭电源 */
		POWEROFF("poweroff"),
		/** 复位 */
		RESET("reset"),
		/** 暂停 */
		SUSPEND("suspend"),
		/** 重启 */
		REBOOT("reboot"),
		/** 关闭系统 */
		SHUTDOWN("shutdown"),
		/** 待机 */
		STANDBY("standby"),
		/** 查询状态 */
		STATE("state"),
		/** 唤起 */
		RESUME("resume"),
		/** 进入维护模式 **/
		ENTER_MAINTENCE_MODE("enter_maintence_mode"),
		/** 退出维护模式 **/
		EXIT_MAINTENCE_MODE("exit_maintence_mode");

		private final String value;

		private PowerOperation(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static PowerOperation fromString(String value) {
			if (value != null) {
				for (PowerOperation c : PowerOperation.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * 状态
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum State {
		/** 未知 */
		UNKNOWN("unknown"),
		/** 电源打开 */
		POWERON("poweron"),
		/** 电源关闭 */
		POWEROFF("poweroff"),
		/** 挂起 */
		PAUSED("paused");

		private final String value;

		private State(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static State fromString(String value) {
			if (value != null) {
				for (State c : State.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * 存储操作
	  * @ClassName: StorageOperation
	  * @author WangJingxin
	  * @date 2016年1月18日 上午11:16:18
	  *
	 */
	public static enum StorageOperation{
		/** 添加iSCSI存储 **/
		ADD_ISCSI("add_iscsi"),
		/** 删除iSCSI存储 **/
		REMOVE_ISCSI("remove_iscsi"),
		/** 用硬件FCoE添加光纤SAN存储 **/
		ADD_FCSAN_WITH_HARDWAREFCoE("add_fcsan_with_hardwarefcoe"),
		/** 用软件FCoE添加光纤SAN存储 **/
		ADD_FCSAN_WITH_SOFTWAREFCoE("add_fcsan_with_softwarefcoe"),
		/** 删除光纤SAN存储 **/
		REMOVE_FCSAN("remove_fcsan");
		private final String value;
		private StorageOperation(String value){
			this.value = value;
		}
		public String getValue(){
			return value;
		}
		public static StorageOperation fromString(String value) {
			if (value != null) {
				for (StorageOperation c : StorageOperation.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 快照操作类型
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum SnapshotOperation {
		/** 创建快照 */
		CREATE("create"),
		/** 快照列表 */
		LIST("list"),
		/** 快照恢复 */
		REVERT("revert"),
		/** 快照移除全部 */
		REMOVEALL("removeall"),
		/** 快照移除指定快照 */
		REMOVE("remove");

		private final String value;

		private SnapshotOperation(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static SnapshotOperation fromString(String value) {
			if (value != null) {
				for (SnapshotOperation c : SnapshotOperation.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * 
	* 硬件信息类型
	* @author Wang Jingxin
	* @date 2016-1-4 上午10:12:42 
	*
	 */
	public static enum HardwareInfoType{
		/**
		 * CPU
		 */
		CPU("CPU"),
		/**
		 * CPU温度
		 */
		CPU_TEMP("CPUTemperature"),
		/**
		 * 内存
		 */
		RAM("RAM"),
		/**
		 * 内存温度
		 */
		RAM_TEMP("RAMTemperature"),
		/**
		 * 风扇转速
		 */
		FAN("Fan"),
		/**
		 * 机箱信息
		 */
		CHASSIS("Chassis");
		
		private final String value;
		
		private HardwareInfoType(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static HardwareInfoType fromString(String value) {
			if (value != null) {
				for (HardwareInfoType c : HardwareInfoType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 虚拟机操作系统类型
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum OsType {
		/**
		 * windows操作系统
		 */
		WIN_VM_OS("Windows"),
		/**
		 * 基于Linux操作系统类型red hat
		 */
		LINUX_VM_OS_REDHAT("Redhat"),
		/**
		 * 基于Linux操作系统类型centos
		 */
		LINUX_VM_OS_CENTOS("Centos"),
		/**
		 * 基于Linux操作系统类型Ubuntu
		 */
		LINUX_VM_OS_UBUNTU("Ubuntu"),
		/**
		 * 基于Linux操作系统类型suse
		 */
		LINUX_VM_OS_SUSE("SUSE"),
		/**
		 * 基于Linux操作系统类型debian
		 */
		LINUX_VM_OS_DEBIAN("Debian"),
		/**
		 * 基于Linux操作系统类型asianux
		 */
		LINUX_VM_OS_ASIANUX("Asianux"),
		/**
		 * 基于Linux操作系统类型oracle
		 */
		LINUX_VM_OS_ORACLE("Oracle"),
		/**
		 * 基于Linux操作系统类型其他发行版本
		 */
		LINUX_VM_OS_OTHER("Other");

		private final String value;

		private OsType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static OsType fromString(String value) {
			if (value != null) {
				for (OsType c : OsType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 虚拟化类型
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum VMType {
		/** 虚拟化类型类型 KVM */
		KVM("KVM"),
		/** 虚拟化类型类型XEN */
		XEN("XEN"),
		/** 虚拟化类型类型 VMWare */
		VMWARE("VMWare");

		private final String value;

		private VMType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static VMType fromString(String value) {
			if (value != null) {
				for (VMType c : VMType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	* @ClassName: DriveType  
	* @Description: 存储器类型
	* @author WangJingxin
	* @date 2016年6月8日 下午2:43:35  
	*
	 */
	public static enum DriveType {
		/** 非SSD **/
		NON_SSD("non-ssd"),
		/** SSD **/
		SSD("ssd");
		private final String value;
		private DriveType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public static DriveType fromString(String value) {
			if (value != null) {
				for (DriveType c : DriveType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	* @ClassName: HardwareAccelerationSupportStatus  
	* @Description: 硬件加速类型
	* @author WangJingxin
	* @date 2016年6月8日 下午3:03:52  
	*
	 */
	public static enum HardwareAccelerationSupportStatus {
		/** 支持 **/
		SUPPORTED("vStorageSupported"), 
		/** 不支持 **/
		UNSUPPORTED("vStorageUnsupported"), 
		/** 未知 **/
		UNKNOWN("vStorageUnknown");
		private final String value;
		private HardwareAccelerationSupportStatus(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public static HardwareAccelerationSupportStatus fromString(String value) {
			if (value != null) {
				for (HardwareAccelerationSupportStatus c : HardwareAccelerationSupportStatus.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * VMware配置信息
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum Vmware {
		/** 接口类型,vcenter接口 */
		INTERFACE_TYPE_VCENTER("vcenter"),
		/** 接口类型，esxi接口 */
		INTERFACE_TYPE_ESXI("esxi"),
		/** vCenter根节点folder名称 */
		VC_ROOT_FOLDER_NAME("host"),
		/** vCenter下资源类型 Folder */
		VC_RESOURCE_TYPE_FOLDER("Folder"),
		/** vCenter下资源类型 data center */
		VC_RESOURCE_TYPE_DATACENTER("Datacenter"),
		/** vCenter下资源类型 Cluster */
		VC_RESOURCE_TYPE_CLUSTER("ClusterComputeResource"),
		/** ESXi主机SSL证书指纹 */
		HOST_SSL_THUMBPRINT("46:30:7D:0F:05:E4:17:B1:F6:27:B2:0E:D4:FD:51:E5:2E:91:CE:2A"),
		/** cluster的das接入控制策略类型 设置群集允许的主机故障数目策略 */
		DAS_AC_POLICY_TYPE_HOST_COUNT("1"),
		/** 设置作为故障切换空间容量保留的群集资源百分比策略 */
		DAS_AC_POLICY_TYPE_RESOURCE_PERCENT("2"),
		/** cluster主机隔离响应 保持 */
		DAS_VM_ISOLATION_RESPONSE_NONE("NONE"),
		/** cluster主机隔离响应 关闭电源 */
		DAS_VM_ISOLATION_RESPONSE_POWEROFF("POWER_OFF"),
		/** cluster主机隔离响应 关机 */
		DAS_VM_ISOLATION_RESPONSE_SHUTDOWN("SHUTDOWN"),
		/** cluster虚机重新启动优先级 禁用 */
		DAS_VM_RESTART_PRIORITY_DISABLED("1"),
		/** cluster虚机重新启动优先级 低 */
		DAS_VM_RESTART_PRIORITY_LOW("2"),
		/** cluster虚机重新启动优先级 中 */
		DAS_VM_RESTART_PRIORITY_MEDIUM("3"),
		/** cluster虚机重新启动优先级 高 */
		DAS_VM_RESTART_PRIORITY_HIGH("4"),
		/** cluster VM监控状态 禁用 */
		DAS_VM_MONITORING_STATE_DISABLED("1"),
		/** cluster VM监控状态 仅虚机监控 */
		DAS_VM_MONITORING_STATE_VM_ONLY("2"),
		/** cluster VM监控状态 虚机和应用监控 */
		DAS_VM_MONITORING_STATE_VM_AND_APP("3"),
		/** 监控数据存储信号策略 */
		DAS_HB_DS_POLICY_ALL_FEASIBLE("ALL_FEASIBLE_DS"),
		/** 监控数据存储信号策略 */
		DAS_HB_DS_POLICY_ALL_FEASIBLE_WITH_USER_PREFERENCE("ALL_FEASIBLE_DS_WITH_USER_PREFERENCE"),
		/** 监控数据存储信号策略 */
		DAS_HB_DS_POLICY_USER_SELECTED("USER_SELECTED_DS"),
		/** cluster dpm管理策略 */
		DPM_BEHAVIOR_AUTOMATED("AUTOMATED"),
		/** cluster dpm管理策略 */
		DPM_BEHAVIOR_MANUAL("MANUAL"),
		/** cluster drs管理策略 */
		DRS_BEHAVIOR_FULLY_AUTOMATED("FULLY_AUTOMATED"),
		/** cluster drs管理策略 */
		DRS_BEHAVIOR_PARTIALLY_AUTOMATED("PARTIALLY_AUTOMATED"),
		/** cluster drs管理策略 */
		DRS_BEHAVIOR_MANUAL("MANUAL"),
		/** 管理流量操作类型 */
		CONF_MANAGEMENT_ADD("CONF_MANAGEMENT_ADD"),
		/** 管理流量操作类型 */
		CONF_MANAGEMENT_REMOVE("CONF_MANAGEMENT_REMOVE");

		private final String value;

		private Vmware(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Vmware fromString(String value) {
			if (value != null) {
				for (Vmware c : Vmware.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 执行脚本常量配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static enum SSH {
		/** 上传到windows服务器脚本路径 */
		GUEST_WIN_SCRIPT_PATH("c:\\backupfile\\csaction\\bin\\"),
		/** 执行虚机脚本分隔符 */
		SPLIT_FLAG_REG("\\$\\$"),
		/** 执行虚机脚本分隔符 */
		SPLIT_FLAG("$$");

		private final String value;

		private SSH(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static SSH fromString(String value) {
			if (value != null) {
				for (SSH c : SSH.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	/**
	 * 存储类型
	  * @author WangJingxin
	  * @date 2016年2月18日 上午11:08:14
	  *
	 */
	public static enum StorageType{
		
		DIR("dir"),
		FS("fs"),
		NETFS("netfs"),
		DISK("disk"),
		ISCSI("iscsi"),
		LOGICAL("logical"),
		SCSI("scsi"),
		MPATH("mpath"),
		RBD("rbd"),
		SHEEPDOG("sheepdog"),
		GLUSTER("gluster"),
		ZFS("zfs");
		
		private final String value;

		private StorageType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static StorageType fromString(String value) {
			if (value != null) {
				for (StorageType c : StorageType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * 迁移类型
	  * @author WangJingxin
	  * @date 2016年4月12日 下午2:28:50
	  *
	 */
	public static enum MigrateType{
		/**
		 * 共享存储动态迁移
		 */
		LIVE_SHERED_STORAGE("LIVE_SHERED_STORAGE"),
		/**
		 * 非共享存储动态迁移
		 */
		LIVE_NON_SHERED_STORAGE("LIVE_NON_SHERED_STORAGE"),
		/**
		 * 离线迁移
		 */
		OFFLINE_SHERED_STORAGE("OFFLINE");
		
		private final String value;

		private MigrateType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static MigrateType fromString(String value) {
			if (value != null) {
				for (MigrateType c : MigrateType.values()) {
					if (value.equalsIgnoreCase(c.value)) {
						return c;
					}
				}
			}
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PowerOperation.POWEROFF.getValue();
		System.out.println(SnapshotOperation.REVERT.getValue());
		Vmware.INTERFACE_TYPE_VCENTER.getValue();
	}

}
