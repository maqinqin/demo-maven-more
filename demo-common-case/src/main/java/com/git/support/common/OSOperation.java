package com.git.support.common;

public class OSOperation {
	/**
	 * openstack接口操作码从8000开始
	 */
	private final static int BASE					= 8000;
	private final static int IDENTITY				= BASE; // 认证接口，8000至8099
	private final static int COMPUTE				= BASE + 100; // 计算接口，8100至8199
	private final static int NETWORK				= BASE + 200; // 网络接口，8200至8299
	private final static int VOLUME					= BASE + 300; // 存储接口，8300至8399
	private final static int BAREMETAL				= BASE + 400; // 存储接口，8400至8499
	private final static int IMAGE				    = BASE + 500; // 镜像接口，8400至8499
	private final static int CPS				    = BASE + 600; //
	private final static int ORCHESTRATION			= BASE + 700; // 
	private final static int OMS			        = BASE + 800; // 
	/**
	 * 认证接口，8000起
	 */
	// 8000至8049是创建操作编码定义
	public final static int GET_TOKEN				= IDENTITY; // 获取创建的TOKEN
	public final static int CREATE_PROJECT			= IDENTITY + 1; // 创建PROJECT
	public final static int PUT_PROJECT_ROLE		= IDENTITY + 2; // 为用户指定的PROJECT配置角色
	public final static int DELETE_PROJECT			= IDENTITY + 3; // 删除PROJECT
	// 8050至8099是查询操作编码定义
	public final static int GET_USER				= IDENTITY + 50; // 获取用户列表
	public final static int GET_USER_DETAIL			= IDENTITY + 51; // 获取用户详细信息
	public final static int GET_DOMAIN				= IDENTITY + 52; // 获取DOMAIN列表
	public final static int GET_DOMAIN_DETAIL		= IDENTITY + 53; // 获取DOMAIN详细信息
	public final static int GET_PROJECT				= IDENTITY + 54; // 获取PROJECT列表
	public final static int GET_PROJECT_DETAIL		= IDENTITY + 55; // 获取PROJECT详细信息
	public final static int GET_ROLE				= IDENTITY + 56; // 获取角色列表
	public final static int GET_PROJECT_ROLE		= IDENTITY + 57; // 获取用户指定PROJECT的角色
	/**
	 * 计算接口，8100起
	 */
	// 8100至8149是创建操作编码定义
	public final static int PUT_COMPUTE_QUOTA		= COMPUTE; // 更新计算配额
	public final static int CREATE_VM				= COMPUTE + 1; // 创建虚拟机
	public final static int RESIZE_VM				= COMPUTE + 2; // 变更虚拟机
	public final static int CONFIRM_RESIZE			= COMPUTE + 3; // 确认变更虚拟机
	public final static int START_VM				= COMPUTE + 4; // 启动虚拟机
	public final static int STOP_VM					= COMPUTE + 5; // 停止虚拟机
	public final static int REBOOT_VM				= COMPUTE + 6; // 重启虚拟机
	public final static int REBOOT_VM_HARD			= COMPUTE + 17; // 强制重启虚拟机
	public final static int DELETE_VM				= COMPUTE + 7; // 删除虚拟机
	public final static int CREATE_FLAVOR			= COMPUTE + 8; // 创建规格
	public final static int CONFIG_FLAVOR_VM		= COMPUTE + 9; // 虚机规格配置扩展
	public final static int CONFIG_FLAVOR_HOST		= COMPUTE + 10; // 物理机规格配置扩展
	public final static int CREATE_HOST				= COMPUTE + 11; // 创建物理机
	public final static int MOUNT_SERVER_VOLUME		= COMPUTE + 12; // 挂载服务器数据卷
	public final static int UNMOUNT_SERVER_VOLUME	= COMPUTE + 13; // 卸载服务器数据卷
	public final static int MOVE_VM					= COMPUTE + 14; // 迁移虚拟机
	public final static int ADD_NETWORK_CARD     	= COMPUTE + 15; // 添加网卡
	public final static int DELETE_NETWORK_CARD		= COMPUTE + 16; // 删除网卡
	public final static int ADD_SECURITY_GROUP		= COMPUTE + 18; // 虚拟机添加安全组
	public final static int REMOVE_SECURITY_GROUP	= COMPUTE + 19; // 虚拟机移除安全组
	public final static int CREATE_FLOATING_IP	    = COMPUTE + 20; // 创建浮动IP
	public final static int DELETE_FLOATING_IP	    = COMPUTE + 21; // 删除浮动IP
	public final static int ADD_FLOATING_IP	        = COMPUTE + 22; // 虚拟机绑定浮动IP
	public final static int REMOVE_FLOATING_IP	    = COMPUTE + 23; // 虚拟机解绑浮动IP
	public final static int PUT_VM				= COMPUTE + 24; // 变更虚拟机名称
	// 8150至8199是查询操作编码定义
	public final static int GET_COMPUTE_QUOTA		= COMPUTE + 50; // 查询计算配额
	public final static int GET_VM_DETAIL			= COMPUTE + 51; // 查询虚拟机详情
	public final static int GET_IP_SERVER			= COMPUTE + 52; // 查询指定IP的服务器
	public final static int GET_HOST_GROUP			= COMPUTE + 53; // 查询主机组列表
	public final static int GET_HOST_RES			= COMPUTE + 54; // 查询主机资源列表
	public final static int GET_HOST_RES_DETAIL		= COMPUTE + 55; // 查询主机资源详情
	public final static int GET_FLAVOR				= COMPUTE + 56; // 查询规格列表
	public final static int GET_FLAVOR_DETAIL		= COMPUTE + 57; // 查询规格详情
	public final static int GET_VNC_CONSOLE			= COMPUTE + 58; // 获取VNC控制台URL
	public final static int GET_SERVER_VOLUME		= COMPUTE + 59; // 查询服务器挂载的数据卷
	public final static int GET_NETWORK_CARD		= COMPUTE + 60; // 查询网卡
	public final static int GET_NETWORK_CARD_STATUS	= COMPUTE + 61; // 查询网卡
	public final static int GET_VNC_CONSOLE_PHY		= COMPUTE + 62; // 获取VNC控制台URL
	public final static int GET_FLOATING_IP_POOL	= COMPUTE + 63; // 查询浮动IP池
	public final static int GET_FLOATING_IP_LIST	= COMPUTE + 64; // 查询浮动IP列表
	public final static int GET_SERVER_LIST			= COMPUTE + 65; // 查询服务器列表
	
	/**
	 * 网络接口，8200起
	 */
	// 8200至8249是创建操作编码定义
	public final static int PUT_NETWORK_QUOTA		= NETWORK; // 更新网络配额
	public final static int CREATE_NETWORK			= NETWORK + 1; // 创建网络
	public final static int CREATE_SUBNET			= NETWORK + 2; // 创建子网
	public final static int CREATE_ROUTER			= NETWORK + 3; // 创建虚拟路由器
	public final static int PUT_ROUTER_NETWORK		= NETWORK + 4; // 配置路由网络
	public final static int DELETE_PORT     		= NETWORK + 5; // 删除端口
	public final static int CREATE_PORT     		= NETWORK + 6; // 创建端口
	public final static int CREATE_PORT_PHY     	= NETWORK + 7; // 创建端口（物理机）
	public final static int CREATE_EXT_NETWORK		= NETWORK + 8; // 创建外部网络
	public final static int REMOVE_ROUTER_NETWORK	= NETWORK + 9; // 移除路由网络
	public final static int DELETE_EXT_NETWORK		= NETWORK + 10; // 删除外部网络
	public final static int DELETE_SUBNET		    = NETWORK + 11; // 删除外部网络子网
	public final static int DELETE_ROUTER			= NETWORK + 12; // 删除虚拟路由器
	public final static int UPDATE_ROUTER			= NETWORK + 13; // 更新虚拟路由器
	public final static int PUT_EXT_NETWORK		    = NETWORK + 14; // 修改外部网络
	public final static int PUT_SUBNET			    = NETWORK + 15; // 修改子网
	public final static int CREATE_FWP			    = NETWORK + 16; // 创建防火墙策略
	public final static int CREATE_FW			    = NETWORK + 17; // 创建防火墙
	public final static int CREATE_FWR			    = NETWORK + 18; // 创建防火墙规则
	public final static int PUT_FWP_IN_FW			= NETWORK + 19; // 在虚拟防火墙中更新防火墙策略
	public final static int REMOVE_FWR_IN_FWP		= NETWORK + 20; // 防火墙策略中移除防火墙规则
	public final static int DELETE_FWR			    = NETWORK + 21; // 删除防火墙规则
	public final static int ADD_FWR_IN_FWP		    = NETWORK + 22; // 防火墙策略中添加防火墙规则
	public final static int PUT_FWR					= NETWORK + 23; // 修改防火墙规则
	public final static int CREATE_SECURITY_GROUP	= NETWORK + 24; // 创建安全组
	public final static int UPDATE_SECURITY_GROUP	= NETWORK + 25; // 更新安全组
	public final static int DELETE_SECURITY_GROUP	= NETWORK + 26; // 删除安全组
	public final static int CREATE_SECURITY_GROUP_RULE	= NETWORK + 27; // 创建安全组规则
	public final static int DELETE_SECURITY_GROUP_RULE	= NETWORK + 28; // 删除安全组规则
	public final static int CREATE_VLB_POOL			= NETWORK + 29; // 创建负载均衡池
	public final static int CREATE_VLB			    = NETWORK + 30; // 创建负载均衡
	public final static int CREATE_PORT_NO_IP		= NETWORK + 31; // 创建端口(无ip)
	public final static int CREATE_VLB_MEMBER		= NETWORK + 32; // 创建member
	public final static int CREATE_VLB_HEALTH		= NETWORK + 33; // 创建健康检查
	public final static int SET_POOL_HEALTH			= NETWORK + 34; // 设置负载均衡池的健康检查
	public final static int DELETE_VLB_POOL			= NETWORK + 35; // 删除负载均衡池
	public final static int DELETE_VLB			= NETWORK + 36; // 删除VIP
	public final static int DELETE_VLB_MEMBER		= NETWORK + 37; // 删除成员
	public final static int DISSET_POOL_HEALTH		= NETWORK + 38; // 解关联负载均衡池的健康检查
	public final static int DELETE_VLB_HEALTH		= NETWORK + 39; // 删除健康检查
	public final static int DELETE_FW				= NETWORK + 40; // 删除防火墙
	public final static int GET_NETWORK	        	= NETWORK + 41; // 查询网络
	public final static int CREATE_VLB_LISTENER			    = NETWORK + 41; // 创建负载均衡监控
	public final static int DELETE_VLB_LISTENER			    = NETWORK + 42; // 删除负载均衡监控
	
	// 8250至8299是查询操作编码定义
	public final static int GET_NETWORK_QUOTA		= NETWORK + 50; // 查询网络配额
	public final static int GET_PORT_DETAIL 		= NETWORK + 51; // 查询端口详细
	public final static int GET_VLB_POOL_LIST 		= NETWORK + 52; // 获取负载均衡池列表
	public final static int GET_SECURITY_GROUP_LIST	= NETWORK + 53; // 查询安全组列表
	public final static int GET_SECURITY_GROUP		= NETWORK + 54; // 查询安全组
	public final static int GET_SECURITY_GROUP_RULE_LIST	= NETWORK + 55; // 查询安全组规则列表
	public final static int GET_SECURITY_GROUP_RULE	= NETWORK + 56; // 查询安全组规则
	public final static int GET_VLB_HEALTH_DETAIL	= NETWORK + 57; // 查询健康检查
	public final static int GET_PORT_IP  			= NETWORK + 58; // 查询端口IP
	public final static int GET_NETWORK_PORT  			= NETWORK + 59; // 查询网络端口
	/**
	 * 存储接口，8300起
	 */
	// 8300至8349是创建操作编码定义
	public final static int PUT_VOLUME_QUOTA		= VOLUME; // 更新存储配额
	public final static int CREATE_VOLUME			= VOLUME + 1; // 创建系统磁盘
	public final static int CREATE_DATA_VOLUME		= VOLUME + 2; // 创建数据磁盘
	public final static int DELETE_VOLUME			= VOLUME + 3; // 删除磁盘
	public final static int CREATE_VOLUME_SNAPSHOT	= VOLUME + 4; // 创建磁盘快照
	public final static int DELETE_VOLUME_SNAPSHOT	= VOLUME + 5; // 删除磁盘快照
	public final static int SNAPSHOT_CREATE_VOLUME	= VOLUME + 6; // 快照创建磁盘
	public final static int PUT_VOLUME			    = VOLUME + 7; // 更新磁盘
	// 8350至8399是查询操作编码定义
	public final static int GET_VOLUME_QUOTA		= VOLUME + 50; // 查询存储配额
	public final static int GET_VOLUME_DETAIL		= VOLUME + 51; // 查询磁盘详情
	public final static int GET_VOLUME_TYPE			= VOLUME + 52; // 查询存储类型列表
	public final static int GET_VOLUME_LIST			= VOLUME + 53; // 查询磁盘列表
	public final static int GET_VOLUME_SNAPSHOT_LIST	= VOLUME + 54; // 查询磁盘快照列表
	public final static int GET_VOLUME_SNAPSHOT		= VOLUME + 55; // 查询快照
	public final static int GET_STORAGE_POOL		= VOLUME + 56; // 查询后端存储
	/**
	 * 裸机接口，8400起
	 */
	// 8400至8449是创建操作编码定义
//	public final static int PUT_VOLUME_QUOTA		= BAREMETAL; // 更新存储配额
	// 8450至8499是查询操作编码定义
	public final static int GET_BAREMETAL			= BAREMETAL + 50; // 查询裸机列表
	public final static int GET_BAREMETAL_DETAIL	= BAREMETAL + 51; // 查询裸机详情
	
	/**
	 * 镜像接口，8500起
	 */
	// 8500至8549是创建操作编码定义
	public final static int CREATE_IMAGE		    = IMAGE; // 创建镜像(KVM)
	public final static int CREATE_IMAGE_PYH		= IMAGE + 1; // 创建镜像(物理机)
	public final static int CREATE_IMAGE_FUS	    = IMAGE + 2; // 创建镜像(FusionCompute)
	public final static int DELETE_IMAGE				= IMAGE + 3; // 删除镜像
	// 8550至8599是查询操作编码定义
	public final static int GET_IMAGE				= IMAGE + 50; // 查询镜像
	
	/**
	 * 接口，8600起
	 */
	// 8500至8549是创建操作编码定义
	
	// 8550至8599是查询操作编码定义
	public final static int GET_PHY_NET				= CPS + 50; // 查询物理网络
	public final static int GET_OVER_SPLIT				= CPS + 51; // 查询超分值
	
	/**
	 * 接口，8700起
	 */
	// 8700至8749是创建操作编码定义
	public final static int CREATE_HA_STACK			= ORCHESTRATION + 1; 
	// 8550至8599是查询操作编码定义
	
	/**
	 * 接口，8800起
	 */
	public final static int GET_STORAGE_VIRTUAL_POOL= OMS + 50; // 
}
