package com.git.support.common;

public class PVOperation {
	public final static String GET_TOKEN				= "getToken"; // 获取创建的TOKEN
	public final static String CREATE_PROJECT			= "createProject"; // 创建PROJECT
	public final static String PUT_PROJECT_ROLE		= "putProjectRole"; // 为用户指定的PROJECT配置角色
	public final static String DELETE_PROJECT			= "deleteProject"; // 删除PROJECT
	
	public final static String GET_USER				= "getUser"; // 获取用户列表
	public final static String GET_USER_DETAIL			= "getUserDetail"; // 获取用户详细信息
	public final static String GET_DOMAIN				= "getDomain"; // 获取DOMAIN列表
	public final static String GET_DOMAIN_DETAIL		= "getDomainDetail"; // 获取DOMAIN详细信息
	public final static String GET_PROJECT				= "getProject"; // 获取PROJECT列表
	public final static String GET_PROJECT_DETAIL		= "getProjectDetail"; // 获取PROJECT详细信息
	public final static String GET_ROLE				= "getRole"; // 获取角色列表
	public final static String GET_PROJECT_ROLE		= "getProjectRole"; // 获取用户指定PROJECT的角色

	public final static String PUT_COMPUTE_QUOTA		= "putComputeQuota"; // 更新计算配额
	public final static String CREATE_VM				= "createVm"; // 创建虚拟机
	public final static String RESIZE_VM				= "resizeVm"; // 变更虚拟机
	public final static String CONFIRM_RESIZE			= "confirmResize"; // 确认变更虚拟机
	public final static String START_VM				= "startVm"; // 启动虚拟机
	public final static String STOP_VM					= "stopVm"; // 停止虚拟机
	public final static String REBOOT_VM				= "rebootVm"; // 重启虚拟机
	public final static String REBOOT_VM_HARD			= "rebootVmHard"; // 强制重启虚拟机
	public final static String DELETE_VM				= "deleteVm"; // 删除虚拟机
	public final static String CREATE_FLAVOR			= "createFlavor"; // 创建规格
	public final static String CONFIG_FLAVOR_VM		= "configFlavorVm"; // 虚机规格配置扩展
	public final static String CONFIG_FLAVOR_HOST		= "configFlavorHost"; // 物理机规格配置扩展
	public final static String CREATE_HOST				= "createHost"; // 创建物理机
	public final static String MOUNT_SERVER_VOLUME		= "mountServerVolume"; // 挂载服务器数据卷
	public final static String UNMOUNT_SERVER_VOLUME	= "unmountServerVolume"; // 卸载服务器数据卷
	public final static String MOVE_VM					= "moveVm"; // 迁移虚拟机
	public final static String ADD_NETWORK_CARD     	= "addNetworkCard"; // 添加网卡
	public final static String DELETE_NETWORK_CARD		= "deleteNetworkCard"; // 删除网卡
	public final static String ADD_SECURITY_GROUP		= "addSecurityGroup"; // 虚拟机添加安全组
	public final static String REMOVE_SECURITY_GROUP	= "removeSecurityGroup"; // 虚拟机移除安全组
	public final static String CREATE_FLOATING_IP	    = "createFloatingIp"; // 创建浮动IP
	public final static String DELETE_FLOATING_IP	    = "deleteFloatingIp"; // 删除浮动IP
	public final static String ADD_FLOATING_IP	        = "addFloatingIp"; // 虚拟机绑定浮动IP
	public final static String REMOVE_FLOATING_IP	    = "removeFloatingIp"; // 虚拟机解绑浮动IP
	public final static String PUT_VM				= "putVm"; // 变更虚拟机名称

	
	public final static String GET_COMPUTE_QUOTA		= "getComputeQuota"; // 查询计算配额
	public final static String GET_VM_DETAIL			= "getVmDetail"; // 查询虚拟机详情
	public final static String GET_IP_SERVER			= "getServerByIp"; // 查询指定IP的服务器
	public final static String GET_HOST_GROUP			= "getHostGroup"; // 查询主机组列表
	public final static String GET_HOST_RES			= "getHostRes"; // 查询主机资源列表
	public final static String GET_HOST_RES_DETAIL		= "getHostResDetail"; // 查询主机资源详情
	public final static String GET_FLAVOR				= "getFlavor"; // 查询规格列表
	public final static String GET_FLAVOR_DETAIL		= "getFlavorDetail"; // 查询规格详情
	public final static String GET_VNC_CONSOLE			= "getVNCConsole"; // 获取VNC控制台URL
	public final static String GET_SERVER_VOLUME		= "getServerVolume"; // 查询服务器挂载的数据卷
	public final static String GET_NETWORK_CARD		= "getNetworkCard"; // 查询网卡
	public final static String GET_NETWORK_CARD_STATUS	= "getNetworkCardStatus"; // 查询网卡
	public final static String GET_VNC_CONSOLE_PHY		= "getVNCConsolePhy"; // 获取VNC控制台URL
	public final static String GET_FLOATING_IP_POOL	= "getFloatingIpPool"; // 查询浮动IP池
	public final static String GET_FLOATING_IP_LIST	= "getFloatingIpList"; // 查询浮动IP列表
	public final static String GET_SERVER_LIST			= "getServerList"; // 查询服务器列表
	
	public final static String PUT_NETWORK_QUOTA		= "putNetworkQuota"; // 更新网络配额
	public final static String CREATE_NETWORK			= "createNetwork"; // 创建网络
	public final static String CREATE_SUBNET			= "createSubnet"; // 创建子网
	public final static String CREATE_ROUTER			= "createRouter"; // 创建虚拟路由器
	public final static String PUT_ROUTER_NETWORK		= "putRouterNetwork"; // 配置路由网络
	public final static String DELETE_PORT     		= "deletePort"; // 删除端口
	public final static String CREATE_PORT     		= "createPort"; // 创建端口
	public final static String CREATE_PORT_PHY     	= "createPortPhy"; // 创建端口（物理机）
	public final static String CREATE_EXT_NETWORK		= "createExtNetwork"; // 创建外部网络
	public final static String REMOVE_ROUTER_NETWORK	= "removeRouterNetwork"; // 移除路由网络
	public final static String DELETE_EXT_NETWORK		= "deleteExtNetwork"; // 删除外部网络
	public final static String DELETE_SUBNET		    = "deleteSubnet"; // 删除外部网络子网
	public final static String DELETE_ROUTER			= "deleteRouter"; // 删除虚拟路由器
	public final static String UPDATE_ROUTER			= "updateRouter"; // 更新虚拟路由器
	public final static String PUT_EXT_NETWORK		    = "putExtNetwork"; // 修改外部网络
	public final static String PUT_SUBNET			    = "putSubnet"; // 修改子网
	public final static String CREATE_FWP			    = "createFwp"; // 创建防火墙策略
	public final static String CREATE_FW			    = "createFw"; // 创建防火墙
	public final static String CREATE_FWR			    = "createFwr"; // 创建防火墙规则
	public final static String PUT_FWP_IN_FW			= "putFwpInFw"; // 在虚拟防火墙中更新防火墙策略
	public final static String REMOVE_FWR_IN_FWP		= "removeFwrInFwp"; // 防火墙策略中移除防火墙规则
	public final static String DELETE_FWR			    = "deleteFwr"; // 删除防火墙规则
	public final static String ADD_FWR_IN_FWP		    = "addFwrInFwp"; // 防火墙策略中添加防火墙规则
	public final static String PUT_FWR					= "putFwr"; // 修改防火墙规则
	public final static String CREATE_SECURITY_GROUP	= "createSecurityGroup"; // 创建安全组
	public final static String UPDATE_SECURITY_GROUP	= "updateSecurityGroup"; // 更新安全组
	public final static String DELETE_SECURITY_GROUP	= "deleteSecurityGroup"; // 删除安全组
	public final static String CREATE_SECURITY_GROUP_RULE	= "createSecurityGroupRule"; // 创建安全组规则
	public final static String DELETE_SECURITY_GROUP_RULE	= "deleteSecurityGroupRule"; // 删除安全组规则
	public final static String CREATE_VLB_POOL			= "createVlbPool"; // 创建负载均衡池
	public final static String CREATE_VLB			    = "createVlb"; // 创建负载均衡
	public final static String CREATE_PORT_NO_IP		= "createPortNoIp"; // 创建端口(无ip)
	public final static String CREATE_VLB_MEMBER		= "createVlbMember"; // 创建member
	public final static String CREATE_VLB_HEALTH		= "createVlbHealth"; // 创建健康检查
	public final static String SET_POOL_HEALTH			= "setPoolHealth"; // 设置负载均衡池的健康检查
	public final static String DELETE_VLB_POOL			= "deleteVlbPool"; // 删除负载均衡池
	public final static String DELETE_VLB			= "deleteVlb"; // 删除VIP
	public final static String DELETE_VLB_MEMBER		= "deleteVlbMember"; // 删除成员
	public final static String DISSET_POOL_HEALTH		= "dissetPoolHealth"; // 解关联负载均衡池的健康检查
	public final static String DELETE_VLB_HEALTH		= "deleteVlbHealth"; // 删除健康检查
	public final static String DELETE_FW				= "deleteFw"; // 删除防火墙
	
	public final static String GET_NETWORK_QUOTA		= "getNetworkQuota"; // 查询网络配额
	public final static String GET_PORT_DETAIL 		= "getPortDetail"; // 查询端口详细
	public final static String GET_VLB_POOL_LIST 		= "getVlbPoolList"; // 获取负载均衡池列表
	public final static String GET_SECURITY_GROUP_LIST	= "getSecurityGroupList"; // 查询安全组列表
	public final static String GET_SECURITY_GROUP		= "getSecurityGroup"; // 查询安全组
	public final static String GET_SECURITY_GROUP_RULE_LIST	= "getSecurityGroupRuleList"; // 查询安全组规则列表
	public final static String GET_SECURITY_GROUP_RULE	= "getSecurityGroupRule"; // 查询安全组规则
	public final static String GET_VLB_HEALTH_DETAIL	= "getVlbHealthDetail"; // 查询健康检查
	public final static String GET_PORT_IP  			= "getPortIp"; // 查询端口IP
	public final static String GET_NETWORK_PORT  			= "getNetworkPort"; // 查询网络端口

	public final static String PUT_VOLUME_QUOTA		= "putVolumeQuota"; // 更新存储配额
	public final static String CREATE_VOLUME			= "createVolume"; // 创建系统磁盘
	public final static String CREATE_DATA_VOLUME		= "createDataVolume"; // 创建数据磁盘
	public final static String DELETE_VOLUME			= "deleteVolume"; // 删除磁盘
	public final static String CREATE_VOLUME_SNAPSHOT	= "createVolumeSnapshot"; // 创建磁盘快照
	public final static String DELETE_VOLUME_SNAPSHOT	= "deleteVolumeSnapshot"; // 删除磁盘快照
	public final static String SNAPSHOT_CREATE_VOLUME	= "snapshotCreateVolume"; // 快照创建磁盘
	public final static String PUT_VOLUME			    = "putVolume"; // 更新磁盘
	// 8350至8399是查询操作编码定义
	public final static String GET_VOLUME_QUOTA		= "getVolumeQuota"; // 查询存储配额
	public final static String GET_VOLUME_DETAIL		= "getVolumeDetail"; // 查询磁盘详情
	public final static String GET_VOLUME_TYPE			= "getVolumeTypes"; // 查询存储类型列表
	public final static String GET_VOLUME_LIST			= "getVolumeList"; // 查询磁盘列表
	public final static String GET_VOLUME_SNAPSHOT_LIST	= "getVolumeSnapshotList"; // 查询磁盘快照列表
	public final static String GET_VOLUME_SNAPSHOT		= "getVolumeSnapshot"; // 查询快照
	public final static String GET_STORAGE_POOL		= "getStoragePool"; // 查询后端存储
	//获取镜像
	public final static String GET_IMAGES = "getImages";//获取镜像列表

	//获取物理机列表信息
	public final static String GET_HOSTINFOLISTS = "getHostInfos";//获取物理机列表信息

}
