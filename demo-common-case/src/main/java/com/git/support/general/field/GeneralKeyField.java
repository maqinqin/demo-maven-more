package com.git.support.general.field;

/**
 * 通用key字段常量配置类
 * 
 * @author zhangbh
 * @version 创建时间：2015-12-29 下午3:07:44 类说明
 */
public class GeneralKeyField {
	/**
	 * 基于VMware的虚拟化系统常用参数配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class VMware {
		
		
		public static final String CLONE_TO_VM_NAME = "CLONE_TO_VM_NAME";
		public static final String CLONE_FROM_VM_NAME = "CLONE_FROM_VM_NAME";
		/**
		 * 数据中心名称
		 */
		public static final String DATACENTER_NAME = "DATACENTER_NAME";
		/**
		 * vcenter常量，URL
		 */
		public static final String VCENTER_URL = "VCENTER_URL";
		/**
		 * vcenter的用户名称
		 */
		public static final String VCENTER_USERNAME = "VCENTER_USERNAME";
		/**
		 * vcenter的密码
		 */
		public static final String VCENTER_PASSWORD = "VCENTER_PASSWORD";

		/**
		 * esxi常量，URL
		 */
		public static final String ESXI_URL = "ESXI_URL";
		/**
		 * esxi的用户名
		 */
		public static final String ESXI_USERNAME = "ESXI_USERNAME";
		/**
		 * esxi的密码
		 */
		public static final String ESXI_PASSWORD = "ESXI_PASSWORD";
		/**
		 * esxi的主机名称
		 */
		public static final String ESXI_HOST_NAME = "ESXI_HOST_NAME";

		/**
		 * SSL 指纹
		 */
		public static final String ESXI_SSL_THUMBPRINT = "ESXI_SSL_THUMBPRINT";

		/**
		 * 虚拟机路径
		 */
		public static final String VM_PATH_NAME = "VM_PATH_NAME";

		/**
		 * 创建cluster、folder、host时指定的父节点类型
		 */
		public static final String PARENT_TYPE = "PARENT_TYPE";
		/**
		 * 创建cluster、folder、host时指定的父节点名称
		 */
		public static final String PARENT_NAME = "PARENT_NAME";
		/**
		 * 创建cluster名称
		 */
		public static final String CLUSTER_NAME = "CLUSTER_NAME";
		/**
		 * 创建folder名称
		 */
		public static final String FOLDER_NAME = "FOLDER_NAME";
		/**
		 * 是否开启HA
		 */
		public static final String DAS_CONFIG_ENABLED = "DAS_CONFIG_ENABLED";
		/**
		 * 主机监控开关
		 */
		public static final String DAS_HOST_MONITORING = "DAS_HOST_MONITORING";
		/**
		 * 接入控制开关
		 */
		public static final String DAS_ADMISSION_CONTROL_ENABLED = "DAS_ADMISSION_CONTROL_ENABLED";
		/**
		 * 接入控制级别
		 */
		public static final String DAS_FAILOVER_LEVEL = "DAS_FAILOVER_LEVEL";
		/**
		 * 接入控制策略类型
		 */
		public static final String DAS_ADMISSION_CONTROL_POLICY_TYPE = "DAS_ADMISSION_CONTROL_POLICY_TYPE";
		/**
		 * 群集允许的主机故障数目
		 */
		public static final String DAS_FAILOVER_LEVEL_POLICY = "DAS_FAILOVER_LEVEL_POLICY";
		/**
		 * 故障切换空间容量保留的群集cpu百分比
		 */
		public static final String DAS_CPU_FAILOVER_RESOURCES_PERCENT = "DAS_CPU_FAILOVER_RESOURCES_PERCENT";
		/**
		 * 故障切换空间容量保留的群集内存百分比
		 */
		public static final String DAS_MEMORY_FAILOVER_RESOURCES_PERCENT = "DAS_MEMORY_FAILOVER_RESOURCES_PERCENT";
		/**
		 * 主机隔离响应策略
		 */
		public static final String DAS_VM_SETTINGS_ISOLATION_RESPONSE = "DAS_VM_SETTINGS_ISOLATION_RESPONSE";
		/**
		 * 虚机重新启动优先级
		 */
		public static final String DAS_VM_SETTINGS_RESTART_PRIORITY = "DAS_VM_SETTINGS_RESTART_PRIORITY";
		/**
		 * 虚拟机监控状态
		 */
		public static final String DAS_MONITORING_CLUSTER_SETTING_FLAG = "DAS_MONITORING_CLUSTER_SETTING_FLAG";
		public static final String DAS_MONITORING_ENABLED = "DAS_MONITORING_ENABLED";
		/**
		 * 虚拟机监控策略
		 */
		public static final String DAS_VM_MONITORING = "DAS_VM_MONITORING";
		/**
		 * 虚机故障间隔时间
		 */
		public static final String DAS_MONITORING_FAILURE_INTERVAL = "DAS_MONITORING_FAILURE_INTERVAL";
		/**
		 * 最大重置次数
		 */
		public static final String DAS_MONITORING_MAX_FAILURES = "DAS_MONITORING_MAX_FAILURES";
		/**
		 * 最短正常运行时间
		 */
		public static final String DAS_MONITORING_MIN_UP_TIME = "DAS_MONITORING_MIN_UP_TIME";
		/**
		 * 电源管理设置开关
		 */
		public static final String DPM_CONFIG_ENABLED = "DPM_CONFIG_ENABLED";
		/**
		 * 电源管理设置策略级别
		 */
		public static final String DPM_BEHAVIOR = "DPM_BEHAVIOR";
		/**
		 * 电源管理阈值
		 */
		public static final String DPM_HOST_POWER_ACTION_RATE = "DPM_HOST_POWER_ACTION_RATE";
		/**
		 * drs设置开关
		 */
		public static final String DRS_CONFIG_ENABLED = "DRS_CONFIG_ENABLED";
		/**
		 * drs设置策略级别
		 */
		public static final String DRS_CONFIG_VM_BEHAVIOR = "DRS_CONFIG_VM_BEHAVIOR";
		/**
		 * drs迁移阈值
		 */
		public static final String DRS_VMOTION_RATE = "DRS_VMOTION_RATE";
		/**
		 * 集群下创建host配置参数，主机IP
		 */
		public static final String HOST_MANAGEMENT_IP = "HOST_MANAGEMENT_IP";
		/**
		 * 集群下创建host配置参数，主机名称
		 */
		public static final String HOST_USERNAME = "HOST_USERNAME";
		/**
		 * 集群下创建host配置参数，主机密码
		 */
		public static final String HOST_PASSWORD = "HOST_PASSWORD";
		/**
		 * 设为true时，如果当前主机已被其他vCenter管理,则原来vCenter丢失对该主机的连接
		 */
		public static final String HOST_FORCE = "HOST_FORCE";
		/**
		 * LICENSE_KEY
		 */
		public static final String LICENSE_KEY = "LICENSE_KEY";

		/**
		 * 集群下配置存储检测信号
		 */
		public static final String HBDS_ARRAY = "HBDS_ARRAY";
		public static final String HBDS_CANDIDATE_POLICY = "HBDS_CANDIDATE_POLICY";

		/**
		 * 端口组管理流量开关操作类型
		 */
		public static final String CONF_MANAGEMENT_TYPE = "CONF_MANAGEMENT_TYPE";

		public static final String VMK_ID = "VMK_ID";

		/**
		 * 集群接口操作类型
		 */
		public static final String INTERFACE_OPER_TYPE = "INTERFACE_OPER_TYPE";
		/**
		 * 虚拟交换机名称
		 */
		public static final String VSWITCH_NAME = "VSWITCH_NAME";
		/**
		 * 虚拟交换机的IP
		 */
		public static final String VSWITCH_IP = "VSWITCH_IP";
		/**
		 * 虚拟交换机的子网掩码
		 */
		public static final String VSWITCH_MASK = "VSWITCH_MASK";
		/**
		 * 虚拟交换机的vlan
		 */
		public static final String VSWITCH_VLAN = "VSWITCH_VLAN";
		/**
		 * 配置器名称
		 */
		public static final String ADAPTER_NAME = "ADAPTER_NAME";
		/**
		 * esxi电源操作类型
		 */
		public static final String ESXI_POWER_OPER_TYPE = "ESXI_POWER_OPER_TYPE";

		/**
		 * 警报
		 */
		public static final class Alarm {
			public static final String LIST = "ALARM_LIST";
			public static final String KEY = "ALARM_KEY";
			public static final String TRIGGERED_TIME = "ALARM_TRIGGERED_TIME";
			public static final String ENTITY_TYPE = "ALARM_ENTITY_TYPE";
			public static final String ENTITY_NAME = "ALARM_ENTITY_NAME";
			public static final String ALRAM_KEY = "ALARM_ALRAM_KEY";
			public static final String NAME = "ALARM_NAME";
			public static final String DESCRIPTION = "ALARM_DESCRIPTION";
			public static final String SYSTEM_NAME = "ALARM_SYSTEM_NAME";
			public static final String ACKNOWLEDGED = "ALARM_ACKNOWLEDGED";
			public static final String ACKNOWLEDGED_USER = "ALARM_ACKNOWLEDGED_USER";
			public static final String ACKNOWLEDGED_TIME = "ALARM_ACKNOWLEDGED_TIME";
			public static final String LAST_MODIFIED_TIME = "ALARM_LAST_MODIFIED_TIME";
			public static final String LAST_MODIFIED_USER = "ALARM_LAST_MODIFIED_USER";
		}

		/**
		 * 端口组列表
		 */
		public static final String PORT_GROUP_LIST = "PORT_GROUP_LIST";
		/**
		 * 虚拟交换机列表
		 */
		public static final String VSWITCH_NAME_LIST = "VSWITCH_NAME_LIST";
		/**
		 * 网络标识符
		 */
		public static final String NETWORK_LABEL = "NETWORK_LABEL";
		/**
		 * vlan ID
		 */
		public static final String VLAN_ID = "VLAN_ID";


		/**
		 * 分布式交换机
		 */
		public static final class DistributedSwitch {

			/**
			 * 名称
			 */
		    public static final String DVS_NAME = "DVS_NAME";

			/**
			 * UUID
			 */
			public static final String DVS_UUID = "DVS_UUID";

			/**
			 * 备注
			 */
			public static final String DVS_NOTES = "DVS_NOTES";
			/**
			 * 端口数
			 */
			public static final String NUMBER_OF_PORTS = "NUMBER_OF_PORTS";

			/**
			 * 上行链路端口数
			 */
			public static final String NUMBER_OF_UPLINK_PORTS = "NUMBER_OF_UPLINK_PORTS";

			/**
			 * 端口组列表
			 */
			public static final String DV_PORT_GROUP_LIST = "DV_PORT_GROUP_LIST";

            /**
             * 端口组
             */
			public static final class PortGroup{

                /**
                 * 端口组名称
                 */
                public static final String DV_PORT_GROUP_NAME = "DV_PORT_GROUP_NAME";
				/**
				 * UUID
				 */
				public static final String DV_PORT_GROUP_UUID = "DV_PORT_GROUP_UUID";
				/**
				 * 描述
				 */
				public static final String DV_PORT_GROUP_DESCRIPTION = "DV_PORT_GROUP_DESCRIPTION";
                /**
                 * 端口数
                 */
                public static final String DV_PORT_GROUP_PORT_NUM = "DV_PORT_GROUP_PORT_NUM";
                /**
                 * 端口绑定
                 */
                public static final String DV_PORT_GROUP_PORT_BINDING = "DV_PORT_GROUP_PORT_BINDING";
                /**
                 * VLAN 类型
                 */
                public static final String DV_PORT_GROUP_VLAN_TYPE = "DV_PORT_GROUP_VLAN_TYPE";
                /**
                 * VLAN ID
                 */
                public static final String DV_PORT_GROUP_VLAN_ID = "DV_PORT_GROUP_VLAN_ID";
				/**
				 * 是否是上行链路端口组
				 */
				public static final String DV_PORT_GROUP_UPLINK = "DV_PORT_GROUP_UPLINK";
			}


		}

	}

	/**
	 * 虚拟机信息常量配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class VM {
		/**
		 * 虚拟机用户名
		 */
		public static final String GUEST_USER_NAME = "GUEST_USER_NAME";
		/**
		 * 虚拟机密码
		 */
		public static final String GUEST_PASSWORD = "GUEST_PASSWORD";
		/**
		 * 虚机镜像名称
		 */
		public static final String VM_IMAGE_NAME = "VM_IMAGE_NAME";

		/**
		 * 虚拟机名称
		 */
		public static final String VAPP_NAME = "VAPP_NAME";
		/**
		 * UUID
		 */
		public static final	String UUID = "VM_UUID";
		/**
		 * Linux的hostname
		 */
		public static final String LINUX_HOSTNAME = "LINUX_HOSTNAME";
		/**
		 * 虚拟机模板路径
		 */
		public static final String OVF_URL = "OVF_URL";
		/**
		 * 存储路径
		 */
		public static final String DATASTORE_NAME = "DATASTORE_NAME";
		/**
		 * 虚拟机CPU核数
		 */
		public static final String CPU_CORE_VALUE = "CPU_CORE_VALUE";
		/**
		 * 虚拟机最大CPU核数
		 */
		public static final String MAX_CPU_VALUE = "MAX_CPU_VALUE";
		/**
		 * 虚拟机内存
		 */
		public static final String MEMORY_VALUE = "MEMORY_VALUE";
		/**
		 * 虚拟机最大内存
		 */
		public static final String MAX_MEMORY_VALUE = "MAX_MEMORY_VALUE";

		/**
		 * 虚拟机增加磁盘，磁盘名称
		 */
		public static final String ADD_DISK_NAME = "ADD_DISK_NAME";
		/**
		 * 虚拟机增加磁盘，磁盘大小
		 */
		public static final String ADD_DISK_SIZE = "ADD_DISK_SIZE";
		/**
		 * 虚拟机增加磁盘，磁盘配置
		 */
		public static final String ADD_DISK_MODE = "ADD_DISK_MODE";
		/**
		 * 虚拟机磁盘置备类型
		 */
		public static final String ADD_DISK_TYPE = "ADD_DISK_TYPE";
		/**
		 * 虚拟机网络配置信息
		 */
		public static final String NIC_INFO = "NIC_INFO";
		/**
		 * 虚拟机路由配置信息
		 */
		public static final String NIC_DNS = "NIC_DNS";

		/**
		 * 域名称
		 */
		public static final String NIC_DOMAIN_NAME = "NIC_DOMAIN_NAME";
		/**
		 * VLAN号
		 */
		public static final String VLAN_ID = "VLAN_ID";

		/**
		 * 虚拟机操作系统类型windows或Linux
		 */
		public static final String VM_OS_TYPE = "VM_OS_TYPE";

		/**
		 * 创建用户接口参数
		 */
		public static final String NEW_USER_NAME = "NEW_USER_NAME";
		/**
		 * 新建用户密码
		 */
		public static final String NEW_PASSWORD = "NEW_PASSWORD";
		/**
		 * 角色id
		 */
		public static final String ROLE_ID = "ROLE_ID";
		/**
		 * 迁移 优先级
		 */
		public static final String VMOTION_PRIORITY = "VMOTION_PRIORITY";
		/**
		 * 迁移 类型
		 */
		public static final String VMOTION_TYPE = "VMOTION_TYPE";
		/**
		 * 迁移 电源状态
		 */
		public static final String VMOTION_POWER_STATE = "VMOTION_POWER_STATE";
		/**
		 * 扫描 虚拟机电源状态
		 */
		public static final String VM_POWER_STATE = "VM_POWER_STATE";
		/**
		 * 重新配置CPU
		 */
		public static final String RECONFIG_TYPE_CPU = "RECONFIG_TYPE_CPU";
		/**
		 * 重新配置内存
		 */
		public static final String RECONFIG_TYPE_MEMORY = "RECONFIG_TYPE_MEMORY";
		/**
		 * 重置类型
		 */
		public static final String RECONFIG_TYPE = "RECONFIG_TYPE";
		/**
		 * 改变CPU类型
		 */
		public static final String CPU_ALTER_TYPE = "CPU_ALTER_TYPE";
		/**
		 * 改变内存类型
		 */
		public static final String MEMORY_ALTER_TYPE = "MEMORY_ALTER_TYPE";
		/**
		 * 改变CPU
		 */
		public static final String CPU_VALUE_CHANGED = "CPU_VALUE_CHANGED";
		/**
		 * 改变内存
		 */
		public static final String MEMORY_VALUE_CHANGED = "MEMORY_VALUE_CHANGED";
		/**
		 * 虚拟机迁移，原主机IP
		 */
		public static final String VMOTION_SOURCE_HOST = "VMOTION_SOURCE_HOST";
		/**
		 * 虚拟机迁移，目标主机存储
		 */
		public static final String VMOTION_TARGET_DATASTORE = "VMOTION_TARGET_DATASTORE";
		/**
		 * 虚拟机迁移，目标主机IP
		 */
		public static final String VMOTION_TARGET_HOST = "VMOTION_TARGET_HOST";
		/**
		 * 
		 */
		public static final String IF_MOVE_THIS_VM = "IF_MOVE_THIS_VM";
		/**
		 * 
		 */
		public static final String IF_NEED_MOVE_VM = "IF_NEED_MOVE_VM";

		/**
		 * 虚机文件允许扩展大小(kb)
		 */
		public static final String VM_VOL_CAPACITY_SIZE = "VM_VOL_CAPACITY_SIZE";
		/**
		 * 创建虚机镜像大小(kb)
		 */
		public static final String VM_VOL_ALLOCATION_SIZE = "VM_VOL_ALLOCATION_SIZE";
		/*
		 * 修改虚机镜像大小(kb)
		 */
		public static final String VM_VOL_ALLOCATION_CHANGED = "VM_VOL_ALLOCATION_CHANGED";

		/**
		 * 虚拟机快照名称
		 */
		public static final String VM_SNAPSHOT_NAME = "VM_SNAPSHOT_NAME";

		/**
		 * 虚拟机快照操作类型
		 */
		public static final String VM_SNAP_OPERATION = "VM_SNAP_OPERATION";
		/**
		 * 内存快照
		 */
		public static final String VM_SNAP_MEMORY = "VM_SNAP_MEMORY";
		/**
		 * 静默快照
		 */
		public static final String VM_SNAP_SILENCE = "VM_SNAP_SILENCE";

		/**
		 * 虚拟机详细信息
		 */
		public static final String VM_INFO = "VM_INFO";

		/**
		 * 虚拟机IP信息
		 */
		public static final String VM_IP = "VM_IP";
		/**
		 * 电源操作类型
		 */
		public static final String POWER_OPER_TYPE = "POWER_OPER_TYPE";
		/**
		 * 虚拟机类型
		 */
		public static final String VM_TYPE = "VM_TYPE";
		/**
		 * 虚拟机IP列表
		 */
		public static final String VM_IP_LIST = "VM_IP_LIST";
		/**
		 * 虚拟机描述信息
		 */
		public static final String VM_DESCRIPTION = "VM_DESCRIPTION";
	}

	/**
	 * 基于KVM的虚拟化系统常用参数配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class KVM {
		/**
		 * kvm物理主机IP
		 */
		public static final String URL = "URL";
		/**
		 * kvm物理主机用户名
		 */
		public static final String USERNAME = "USERNAME";
		/**
		 * kvm物理主机密码
		 */
		public static final String PASSWORD = "PASSWORD";
		/**
		 * 存储资源池名称
		 */
		public static final String STORAGEPOOL_NAME = "STORAGEPOOL_NAME";
		/**
		 * 本地存储路径
		 */
		public static final String LOCAL_IMAGE_PATH = "LOCAL_IMAGE_PATH";
		/**
		 * kvm镜像存储路径
		 */
		public static final String QCOW2_URL = "QCOW2_URL";
	}

	/**
	 * 基于Power的虚拟化系统常用参数配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class Power {
		/**
		 * Power物理主机IP
		 */
		public static final String URL = "URL";
		/**
		 * Power物理主机用户名
		 */
		public static final String USERNAME = "USERNAME";
		/**
		 * Power物理主机密码
		 */
		public static final String PASSWORD = "PASSWORD";
		/**
		 * 管理物理主机名称
		 */
		public static final String HOST_NAME = "HOST_NAME";
		/**
		 * 目标主机名称
		 */
		public static final String DEST_HOST_NAME = "DEST_HOST_NAME";
		/**
		 * 目标lpar ID
		 */
		public static final String DEST_LPAR_ID = "DEST_LPAR_ID";
		/**
		 * 目标lpar FC卡信息
		 */
		public static final String VIRTUAL_FC_MAPPINGS = "VIRTUAL_FC_MAPPINGS";
		/**
		 * 目标lpar scsi信息
		 */
		public static final String VIRTUAL_SCSI_MAPPINGS = "VIRTUAL_SCSI_MAPPINGS";
	}

	/**
	 * 执行脚本常量配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class SSH {
		/**
		 * 执行脚本的主机URL
		 */
		public static final String URL = "URL";
		/**
		 * 执行脚本的主机用户名
		 */
		public static final String USERNAME = "USERNAME";
		/**
		 * 执行脚本的主机密码
		 */
		public static final String PASSWORD = "PASSWORD";
		/**
		 * 执行脚本
		 */
		public static final String EXECUTE_SCRIPT = "EXECUTE_SCRIPT";
		/**
		 * 脚本list名
		 */
		public static final String SCRIPT_LIST_NAME = "SCRIPT_LIST_NAME";
		/**
		 * 脚本参数list名
		 */
		public static final String SCRIPT_ARGS_LIST_NAME = "SCRIPT_ARGS_LIST_NAME";
		/**
		 * 脚本服务器路径
		 */
		public static final String SCRIPT_SERVER_PATH = "SCRIPT_SERVER_PATH";
		/**
		 * 脚本下发到适配器服务器路径
		 */
		public static final String ADAPTER_SCRIPT_PATH = "ADAPTER_SCRIPT_PATH";
	}

	/**
	 * 资源回收常量配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class Recycling {
		/**
		 * 回收资源名称
		 */
		public static final String DESTORY_RESOURCE_NAME = "DESTORY_RESOURCE_NAME";
		/**
		 * 回收资源类型
		 */
		public static final String DESTORY_TYPE = "DESTORY_TYPE";
		/**
		 * 批量调用回收资源记录
		 */
		public static final String DESTORY_RESOURCE_RECS = "DESTORY_RESOURCE_RECS";
	}

	/**
	 * 
	 * IPMI
	 * 
	 * @author Wang Jingxin
	 * @date 2016-1-4 上午9:38:07
	 * 
	 */
	public static final class IPMI {
		/**
		 * IPMI版本
		 */
		public static final String IPMI_VERSION = "IPMI_VERSION";
		/**
		 * IPMI IP地址
		 */
		public static final String IPMI_IP = "IPMI_IP";
		/**
		 * IPMI用户名
		 */
		public static final String USER_NAME = "USER_NAME";
		/**
		 * IPMI密码
		 */
		public static final String PASSWORD = "PASSWORD";
		/**
		 * 电源操作类型
		 */
		public static final String POWER_OPER_TYPE = "POWER_OPER_TYPE";
		/**
		 * 传感器信息类型
		 */
		public static final String SDR_INFO_TYPE = "SDR_INFO_TYPE";
		/**
		 * 设备信息及可更换硬件信息
		 */
		public static final String FRU_INFO_TYPE = "FRU_INFO_TYPE";
	}

	/**
	 * 存储常量配置
	 * 
	 * @author zhangbh
	 * 
	 */
	public static final class Storage {
		/**
		 * 挂载Nas记录List
		 */
		public static final String NAS_ADD_RECS = "NAS_ADD_RECS";
		/**
		 * Nas存储ip
		 */
		public static final String NAS_HOST_REMOTE_IP = "NAS_HOST_REMOTE_IP";
		/**
		 * Nas存储服务端路径
		 */
		public static final String NAS_REMOTE_PATH = "NAS_REMOTE_PATH";
		/**
		 * 挂载后Nas在主机上显示名称
		 */
		public static final String NAS_LOCAL_PATH = "NAS_LOCAL_PATH";
		/**
		 * iSCSI的target的IP
		 */
		public static final String ISCSI_TARGET_HOST = "ISCSI_TARGET_HOST";
		/**
		 * iSCSI的target的guid
		 */
		public static final String ISCSI_TARGET_GUID = "ISCSI_TARGET_GUID";
		/**
		 * iSCSI的操作类型
		 */
		public static final String ISCSI_OPER_TYPE = "ISCSI_OPER_TYPE";
		/**
		 * Datastore的名字
		 */
		public static final String DATASTORE_NAME = "DATASTORE_NAME";
		/**
		 * Datastore的容量
		 */
		public static final String DATASTORE_CAPACITY = "DATASTORE_CAPACITY";
		/**
		 * 光纤SAN的操作类型
		 */
		public static final String FCSAN_OPER_TYPE = "FCSAN_OPER_TYPE";
		/**
		 * 光纤存储设备标识符
		 */
		public static final String FCSAN_DEVICE_ID = "FCSAN_DIVICE_ID";
		/**
		 * 存储类型
		 */
		public static final String DATASTORE_TYPE = "DATASTORE_TYPE";
		/**
		 * 存储资源池要挂载到的路径
		 */
		public static final String TARGET_PATH = "TARGET_PATH";
		/**
		 * 存储资源池的源路径
		 */
		public static final String SOURCE_PATH = "SOURCE_PATH";
		/**
		 * 存储资源池的源Host
		 */
		public static final String SOURCE_HOST = "SOURCE_HOST";
		/**
		 * 硬盘容量
		 */
		public static final String DISK_CAPACITY = "DISK_CAPACITY";
		/**
		 * 标识符
		 */
		public static final String CANONICAL_NAME = "CANONICAL_NAME";
		/**
		 * 显示名称
		 */
		public static final String DISPLAY_NAME = "DISPLAY_NAME";
		/**
		 * 设备类型
		 */
		public static final String DEVICE_TYPE = "DEVICE_TYPE";
		/**
		 * 驱动器类型
		 */
		public static final String DRIVE_TYPE = "DRIVE_TYPE";
		/**
		 * 容量
		 */
		public static final String CAPACITY = "CAPACITY";
		/**
		 * VFMS_标签
		 */
		public static final String VFMS_LABEL = "VFMS_LABEL";
		/**
		 * 硬件加速
		 */
		public static final String HARDWARE_ACCELERATION = "HARDWARE_ACCELERATION";

	}

	/**
	 * 虚拟机迁移常量
	 * 
	 * @author WangJingxin
	 * @date 2016年4月12日 下午1:48:56
	 *
	 */
	public static final class Migrate {
		/**
		 * 迁移类型
		 */
		public static final String TYPE = "Migrate_TYPE";
		/**
		 * 目标URL
		 */
		public static final String DESTINATION_URL = "DESTINATION_URL";

	}

	/**
	 * @ClassName: Vnc
	 * @Description: VNC常量
	 * @author WangJingxin
	 * @date 2016年7月4日 下午4:10:48
	 *
	 */
	public static final class Vnc {
		/**
		 * noVNC 的 token
		 */
		public static final String NOVNC_TOKEN = "NOVNC_TOKEN";
		/**
		 * 代理服务器
		 */
		public static final String NOVNC_HOST = "NOVNC_HOST";
		/**
		 * 端口
		 */
		public static final String NOVNC_PORT = "NOVNC_PORT";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(GeneralKeyField.VMware.VCENTER_URL);
		System.out.println(GeneralKeyField.VM.ADD_DISK_MODE);
		GeneralValueField.IPMI.IPMI_VERSION_V20.getValue();
	}

}
