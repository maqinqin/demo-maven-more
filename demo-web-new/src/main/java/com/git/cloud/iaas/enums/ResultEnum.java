package com.git.cloud.iaas.enums;

public enum ResultEnum {
	UNKOWN(-1,"未知异常")
	,SUCCESS(0,"")
	
	//登录
	,LOGIN_NOUSER(100,"此用户不存在")
	,LOGIN_USER_PWD_ERROR(101,"用户密码不相符")
	,LOGIN_USER_NOTENANT(102,"用户无授权租户")
	,LOGIN_SESSION_ERROR(103,"会话过期,请重新登陆")
	,LOGIN_NOT_AUTHO(104,"用户没有登录权限")
	,OLD_PWD_ERROR(105,"原密码不正确")
	,NOT_AUTHO_UPDATE_PWD(106,"用户没有权限修改指定用户密码")
	,OLD_PWD_NOT_EMPTY(107,"旧密码不能为空")
	,NEW_PWD_NOT_EMPTY(108,"新密码不能为空")
	,NAME_PWD_NOT_EMPTY(109,"用户名，密码不能为空")
	
	//虚机
	,VM_LIST_ERROR(200,"获取虚机列表失败！")
	,VM_DETAIL_ERROR(201,"获取虚机明细失败！")
	,VM_POWER_OPERATION_POWEROFF_ERROR(202,"vm poweroff error")
	,VM_POWER_OPERATION_POWERON_ERROR(203,"vm poweron error")
	,VM_RUNNING_STATE_ERROR(203,"get vm runningstate error")
	,VM_OPERATION_RESTART_ERROR(205,"虚机强制重启失败！")
	,VM_OPERATION_RESUME_ERROR(206,"虚机唤醒失败！")
	,VM_OPERATION_PAUSE_ERROR(207,"虚机挂起失败！")
	,VM_TRANSFER_ERROR(208,"虚机迁移失败！")
	,VM_LIST_UNMOUNT_ERROR(209,"获取未挂载虚机列表失败！")
	,VM_LIST_MOUNTED_ERROR(210,"获取已挂载的虚机列表失败！")
	,VM_CREATE_SNAPSHOT_ERROR(211,"创建快照失败！")
	,VM_REVERT_SNAPSHOT_ERROR(212,"恢复快照失败！")
	,VM_REMOVE_SNAPSHOT_ERROR(213,"删除快照失败！")
	,VM_ISEXIST_SNAPSHOT_ERROR(214,"快照已创建！")
	,VM_VNC_CONSOLE_ERROR(215,"获取VNCConsole失败！")
	,VM_PWD_NULL_ERROR(216,"vm device password is null")
	,VM_VCENTER_RESPONSE_ERROR(217,"vm vcenter response error")
	,VM_NULL_ERROR(218,"vm is null get by id")
	,VM_MANAGESERVER_NULL_ERROR(219,"vmManageServerPo is null")
	,VM_MANAGESERVER_PWD_NULL_ERROR(220,"vmManageServer pwd is null")
	,VM_DS_DEFAULT_UNMOUNT_ERROR(221,"vm datastore is default,can not unmount")
	
	//物理机
	,HOST_INFO_NULL_ERROR(250,"host info is null")
	,HOST_NOT_INVC_ERROR(251,"host is not invc")
	,HOST_VM_MOVE_LIST_ERROR(252,"get host error")
	
	//应用系统
	,APPSYS_LIST_ERROR(300,"获取应用系统异常")
	,APPSYS_LIST_NULL_ERROR(301,"appsys list is null")
	//服务类型
	,BRSRTYPE_LIST_NULL_ERROR(400,"brsr type list is null")
	//云服务
	,CLOUD_CPUMEMREF_LIST_NULL_ERROR(500,"获取虚拟机规格列表为空")
	,CLOUD_SERVICE_ATTR_ERROR(501,"获取云服务参数异常")
	,CLOUD_SERVICE_ATTR_SELECTION_ERROR(502,"获取选项列表异常")
	,CLOUD_SERVICE_LIST_NULL_ERROR(503,"cloud service list is null")
	//服务器角色
	,DEPLOYUNIT_LIST_ERROR(600,"获取服务器角色异常")
	//字典
	,DIC_LIST_ERROR(700,"获取字典列表信息异常")
	//数据中心
	,DATACENTER_LIST_ERROR(800,"获取数据中心异常")
	,DATACENTER_LIST_NULL_ERROR(801,"datacenter list is null")
	//安全区层
	,SECURE_AREA_LIST_ERROR(900,"获取安全区异常")
	,SECURE_TIER_LIST_ERROR(901,"获取安全层异常")
	//openstack 接口
	,OPENSTACK_INTERFACE_ERROR(1000,"调用接口失败")
	,OPENSTACK_VPC_INFO_ERROR(1001,"获取vpc信息失败")
	//数据卷
	,OPENSTACK_VOLUME_MOUNT_ERROR(1100,"挂载数据卷失败！")
	,OPENSTACK_VOLUEM_UNMOUNT_ERROR(1101,"卸载数据卷失败！")
	,OPENSTACK_VOLUME_LIST_ERROR(1102,"获取卷列表失败")
	,OPENSTACK_VOLUME_MOUNTABLE_ERROR(1103,"获取可挂载卷列表失败")
	,OPENSTACK_VOLUME_MOUNTED_ERROR(1104,"获取已挂载卷列表失败")
	,OPENSTACK_VOLUME_CREATE_ERROR(1105,"创建卷失败")
	,OPENSTACK_VOLUME_DELETE_ERROR(1106,"删除卷失败")
	,OPENSTACK_VOLUME_DELETE_MOUNT_ERROR(1107,"此卷已被挂载")
	,OPENSTACK_VOLUME_GET_TYPE(1108,"获取卷类型失败")
	,OPENSTACK_VOLUME_SNAP_LIST_ERROR(1109,"获取卷快照失败")
	,OPENSTACK_VOLUME_SNAP_CREATE_ERROR(1110,"创建卷快照失败")
	//可用分区
	,OPENSTACK_ZONE_LIST_NULL_ERROR(1200,"available zone list is null")
	//物理机
	,OPENSTACK_PHYSICALMACHINE_LIST_ERROR(1300,"获列物理机列表信息异常")
	,OPENSTACK_PHYSICALMACHINE_CPUMEMREF_ERROR(1301,"获取物理机规格列表异常")
	//弹性IP
	,OPENSTACK_FLOATINGIP_LIST_ERROR(1400,"获取租户下浮动IP列表失败！")
	,OPENSTACK_FLOATINGIP_PROJECT_LIST_ERROR(1401,"获取project下浮动IP列表失败！")
	,OPENSTACK_FLOATINGIP_BIND_ERROR(1402,"绑定浮动IP失败！")
	,OPENSTACK_FLOATINGIP_UNBIND_ERROR(1403,"解绑浮动IP失败！")
	//openstack
	,OPENSTACK_LIST_ERROR(1500,"获取openstack列表信息异常")
	//project
	,OPENSTACK_PROJECT_COUNT_RESOURCE_ERROR(1600,"统计project资源失败")
	,OPENSTACK_RPOJECT_USER_LIST_ERROR(1601,"获取用户project信息异常")
	,OPENSTACK_PROJECT_BYOPENSTACK_ERROR(1602,"获取openstack获取project列表信息异常")
	,OPENSTACK_PROJECT_NULL_ERROR(1603,"project is null")
	,OPENSTACK_PROJECT_CREATE_ERROR(1604, "创建Project失败！")
	//配额
	,OPENSTACK_QUOTA_ERROR(1700,"获取配额异常")
	,OPENSTACK_QUOTA_CPUNOTEUOUGH_ERROR(1701,"cpu quota not enough")
	,OPENSTACK_QUOTA_MEMNOTEUOUGH_ERROR(1702,"mem quota not enough")
	,OPENSTACK_QUOTA_DISKNOTEUOUGH_ERROR(1703,"disk quota not enough")
	,OPENSTACK_QUOTA_NULL_ERROR(1704,"quotaCountVoList is null")
	,OPENSTACK_QUOTA_PLATFORMTYPECODE_NULL_ERROR(1705,"platformTypeCode is null")
	,OPENSTACK_QUOTA_VMNOTEUOUGH_ERROR(1706," machine quota not enough")
	,OPENSTACK_QUOTA_NOTEUOUGH_ERROR(1703,"quota is null")
	,OPENSTACK_QUOTA_FLOATIP_ERROR(1708,"project下浮动IP超额")
	,OPENSTACK_QUOTA_NOT_ENOUGH_ERROR(1709,"quota not enough")
	//VLB
	,OPENSTACK_VLB_CONFIG_ERROR(1800,"配置负载均衡失败！")
	,OPENSTACK_VLB_REMOVE_ERROR(1801,"移除负载均衡member失败！")
	,OPENSTACK_VLB_LIST_ERROR(1802,"获取租户下负载均衡池列表失败！")
	,OPENSTACK_VLB_MEMBER_NULL_ERROR(1803,"vlb config:cloud not config nothing,reason:member null")
	,OPENSTACK_VLB_VMMANAGESERVER_NULL_ERROR(1804,"vmManageServerPo is null")
	,OPENSTACK_VLB_NULL_ERROR(1805,"vlbPool is null")
	//外部网络
	,OPENSTACK_CREATE_EXTER_NETWORK_ERROR(1806,"create an external network failure")
	,OPENSTACK_CREATE_EXTER_NETWORK_SYNC_ERROR(1807,"Create an external network synchronize failure ")
	,OPENSTACK_UPDATE_EXTER_NETWORK_ERROR(1808,"update an external network failure ")
	,OPENSTACK_UPDATE_EXTER_NETWORK_SYNC_ERROR(1809,"update an external network synchronize failure ")
	,OPENSTACK_DELETE_EXTER_NETWORK_ERROR(1810,"delete an external network failure ")
	,OPENSTACK_DELETE_EXTER_NETWORK_SYNC_ERROR(1811,"delete an external network synchronize failure ")
	//外部网络子网
	,OPENSTACK_CREATE_EXTER_NETWORK_SUBNET_ERROR(1812,"create an external network subnet failure")
	,OPENSTACK_CREATE_EXTER_NETWORK_SUBNET_SYNC_ERROR(1813,"create an external network subnet sync failure")
	,OPENSTACK_UPDATE_EXTER_NETWORK_SUBNET_ERROR(1814,"update an external network subnet failure")
	,OPENSTACK_UPDATE_EXTER_NETWORK_SUBNET_SYNC_ERROR(1815,"update an external network subnet sync failure")
	,OPENSTACK_DELETE_EXTER_NETWORK_SUBNET_ERROR(1816,"delete an external network subnet failure")
	,OPENSTACK_DELETE_EXTER_NETWORK_SUBNET_SYNC_ERROR(1817,"delete an external network subnet sync failure")
	//内部网络
	,OPENSTACK_CREATE_VIRTUAL_NETWORK_ERROR(1818,"create virtual network failure")
	,OPENSTACK_UPDATE_VIRTUAL_NETWORK_ERROR(1819,"update virtual external network failure ")
	,OPENSTACK_DELETE_VIRTUAL_NETWORK_ERROR(1820,"delete virtual network failure ")
	,OPENSTACK_GET_VIRTUAL_NETWORK_ERROR(1821,"get virtual network failure ")
	,OPENSTACK_ENABLE_VIRTUAL_NETWORK_ERROR(1822,"enable virtual network failure ")
	,OPENSTACK_UPDATE_VIRTUAL_NETWORK_SYNC_ERROR(1823,"update virtual network synchronize failure ")
	//内部网络子网
	,OPENSTACK_CREATE_VIRTUAL_NETWORK_SUBNET_ERROR(1824,"create virtual network subnet failure")
	,OPENSTACK_UPDATE_VIRTUAL_NETWORK_SUBNET_ERROR(1825,"update virtual network subnet failure")
	,OPENSTACK_DELETE_VIRTUAL_NETWORK_SUBNET_ERROR(1826,"delete virtual network subnet failure")
	,OPENSTACK_GET_VIRTUAL_NETWORK_SUBNET_ERROR(1827,"get virtual network subnet failure")
	,OPENSTACK_UPDATE_VIRTUAL_NETWORK_SUBNET_SYNC_ERROR(1828,"update virtual network subnet sync failure")
	,OPENSTACK_DELETE_VIRTUAL_NETWORK_SUBNET_SYNC_ERROR(1829,"delete virtual network subnet sync failure")
	,OPENSTACK_VIRTUAL_NETWORK_ADD_ROUTER_ERROR(1830,"virtual network add router failure")
	,OPENSTACK_VIRTUAL_NETWORK_REMOVE_ROUTER_ERROR(1831,"virtual network remove router failure")
	,OPENSTACK_VIRTUAL_NETWORK_VXLANID_ERROR(1832,"请先在系统参数中，配置vxlan_id的取值范围")
	,OPENSTACK_VIRTUAL_NETWORK_VXLANID_MIN_ERROR(1833,"vxlan_id的最小值，需要大于4096")
	,OPENSTACK_VPC_NULL_ERROR(1900,"vpc is null")
	
	,TENANT_NULL_ERROR(2000,"获取用户租户信息异常")
	
	//供给
	,SUPPLY_SERVICE_ATTR_NULL_ERROR(3000,"virtualSupplyParameter appSysSelectedState is null")
	,SUPPLY_QUOTA_ERROR(3001,"验证资源配额异常")
	,SUPPLY_QUOTA_LESS_ERROR(3002,"资源配额无法满足当前服务申请")
	,SUPPLY_SERVICE_ATTR_APPID_NULL_ERROR(3003,"virtualSupplyParameter appId is null")
	,SUPPLY_SERVICE_ATTR_DUID_NULL_ERROR(3004,"virtualSupplyParameter duId is null")
	,SUPPLY_RM_HOST_RES_POOL_ID_NULL_ERROR(3005,"virtualSupplyParameter rmHostResPoolId is null")
	,SUPPLY_SERVICE_DCID_NULL_ERROR(3007,"virtualSupplyParameter dcId is null")
	,SUPPLY_SERVICE_OPERMODELTYPE_NULL_ERROR(3008,"virtualSupplyParameter operModelType is null")
	,SUPPLY_SERVICE_CPU_NULL_ERROR(3009,"virtualSupplyParameter cpu is null")
	,SUPPLY_SERVICE_CPULESS0_ERROR(3010,"virtualSupplyParameter cpu need greater than 0")
	,SUPPLY_SERVICE_MEM_NULL_ERROR(3011,"virtualSupplyParameter mem is null")
	,SUPPLY_SERVICE_MEM_DIVISIBLE_ERROR(3012,"virtualSupplyParameter mem need divisible by 1024")
	,SUPPLY_SERVICE_DISK_NULL_ERROR(3013,"virtualSupplyParameter disk is null")
	,SUPPLY_SERVICE_SERVICEID_NULL_ERROR(3014,"virtualSupplyParameter serviceId is null")
	,SUPPLY_SERVICE_SERVICENUM_NULL_ERROR(3015,"virtualSupplyParameter serviceNum is null")
	,SUPPLY_SERVICE_SERVICENUM_LESS0_ERROR(3016,"virtualSupplyParameter serviceNum need greater than 0")
	,SUPPLY_SERVICE_PLATFORMCODE_NULL_ERROR(3017,"virtualSupplyParameter platformTypeCode is null")
	,SUPPLY_SERVICE_PARAM_NULL_ERROR(3018,"serviceRequestParam is null")
	,SUPPLY_SERVICE_CONNECTION_REFUSED_ERROR(3019,"Connection refused: connect")
	,SERVICE_CONNECTION_REFUSED_ERROR(3020,"Connection refused: connect")
	,SERVICE_CONNECTION_URL_NULL_ERROR(3021,"targetURL is null")
	,SUPPLY_RM_NETWORK_RES_POOL_ID_NULL_ERROR(3022,"virtualSupplyParameter rmNetworkResPoolId is null")
	,SUPPLY_RM_NW_RES_POOL_ID_NULL_ERROR(3023,"virtualSupplyParameter rmNwResPoolId is null")
	//回收参数，编码从3030开始
	,RECYCLE_SERVICE_ATTR_APPID_NULL_ERROR(3030,"virtualRecycleParameter appId is null")
	,RECYCLE_SERVICE_ATTR_DUID_NULL_ERROR(3031,"virtualRecycleParameter duId is null")
	,RECYCLE_SERVICE_DCID_NULL_ERROR(3032,"virtualRecycleParameter dcId is null")
	,RECYCLE_SERVICE_OPERMODELTYPE_NULL_ERROR(3033,"virtualRecycleParameter operModelType is null")
	,RECYCLE_SERVICE_CPU_NULL_ERROR(3034,"virtualRecycleParameter cpu is null")
	,RECYCLE_SERVICE_CPULESS0_ERROR(3035,"virtualRecycleParameter cpu need greater than 0")
	,RECYCLE_SERVICE_MEM_NULL_ERROR(3036,"virtualRecycleParameter mem is null")
	,RECYCLE_SERVICE_MEM_DIVISIBLE_ERROR(3037,"virtualRecycleParameter mem need divisible by 1024")
	,RECYCLE_SERVICE_SERVICEID_NULL_ERROR(3038,"virtualRecycleParameter serviceId is null")
	,RECYCLE_SERVICE_DEVICEID_NULL_ERROR(3039,"virtualRecycleParameter deviceId is null")
	,RECYCLE_SERVICE_DEVICE_NULL_ERROR(3040,"virtualRecycleParameter vm is null")
	,RECYCLE_SERVICE_CONNECTION_REFUSED_ERROR(3041,"Connection refused: connect")
	,RECYCLE_SERVICE_IMAGE_ERROR(3042,"端口组镜像未解除！")
	
	//扩容参数异常3050开始
	,EXTEND_SERVICE_CPU_NULL_ERROR(3050,"virtualExtendParameter cpu is null")
	,EXTEND_SERVICE_CPULESS0_ERROR(3051,"virtualExtendParameter cpu need greater than 0")
	,EXTEND_SERVICE_MEM_NULL_ERROR(3052,"virtualExtendParameter mem is null")
	,EXTEND_SERVICE_MEM_DIVISIBLE_ERROR(3053,"virtualExtendParameter mem need divisible by 1024")
	,EXTEND_SERVICE_DEVICEID_NULL_ERROR(3054,"virtualExtendParameter deviceId is null")
	,EXTEND_SERVICE_ATTR_APPID_NULL_ERROR(3055,"virtualExtendParameter appId is null")
	,EXTEND_SERVICE_ATTR_DUID_NULL_ERROR(3056,"virtualExtendParameter duId is null")
	,EXTEND_SERVICE_DCID_NULL_ERROR(3057,"virtualExtendParameter dcId is null")
	,EXTEND_SERVICE_SERVICEID_NULL_ERROR(3058,"virtualExtendParameter serviceId is null")
	,EXTEND_SERVICE_DEVICE_NULL_ERROR(3059,"virtualExtendParameter vm is null")
	,EXTEND_SERVICE_QUOTA_VALIDATE_ERROR(3060,"virtualExtendParameter quota validate fail")
	,EXTEND_QUOTA_LESS_ERROR(3061,"资源配额无法满足当前扩容申请")
	,EXTEND_SERVICE_PLATFORMCODE_NULL_ERROR(3062,"virtualExtendParameter platformTypeCode is null")
	
	//服务自动化参数，编码从3030开始
	,SERVICE_AUTO_DCID_NULL_ERROR(3063,"dcId is null")
	,SERVICE_AUTO_SERVICEID_NULL_ERROR(3064,"serviceId is null")
	,SERVICE_AUTO_DISK_NULL_ERROR(3065,"diskSize is null")
	,SERVICE_AUTO_NAME_NULL_ERROR(3066,"volume name is null")
	,SERVICE_AUTO_AZNAME_NULL_ERROR(3067,"volume azName is null")
	,SERVICE_AUTO_VOLUMETYPE_NULL_ERROR(3068,"volumeType is null")
	,SERVICE_AUTO_ISSHARE_NULL_ERROR(3069,"isShare is null")
	,SERVICE_AUTO_OPERMODELTYPE_NULL_ERROR(3070,"operModelType is null")
	,SERVICE_AUTO_PROJECTID_NULL_ERROR(3071,"projectId is null")
	,SERVICE_AUTO_CLOUDSERVICE_NULL_ERROR(3071,"cloudService is null")
	,SERVICE_AUTO_CONNECTION_REFUSED_ERROR(3072,"Connection refused: connect")
	,SERVICE_AUTO_DEVICEID_NULL_ERROR(3073,"deviceId is null")
	,SERVICE_AUTO_IP_NULL_ERROR(30784,"ip is null")
	,SERVICE_AUTO_DEVICEIP_NULL_ERROR(3083,"device ip is null")
	,SERVICE_AUTO_RMNWRESPOOL_NULL_ERROR(3074,"rmNwResPoolId is null")
	,SERVICE_AUTO_PLATFORM_TYPECODE_NULL_ERROR(3075,"platformCode is null")
	,RES_POOL_ID_IS_NULL(3076,"hostResPoolId is null")
	,RES_POOL_DATACENTER_ID_IS_NULL(3077,"datacenterId is null")
	,RES_POOL_PLATFORM_ID_IS_NULL(3078,"platformId is null")
	,RES_POOL_SECURE_AREA_ID_IS_NULL(3079,"secureAreaId is null")
	,RES_POOL_HOST_TYPE_ID_IS_NULL(3080,"hostTypeId is null")
	,RES_POOL_VIRTUAL_TYPE_ID_IS_NULL(3081,"virtualTypeId is null")
	,RES_POOL_PLATFORM_TYPE_CODE_IS_NULL(3082,"platformTypeCode is null")
	,RES_POOL_VM_MANAGE_SERVER_ID_IS_NULL(3083,"vmManageServerId is null")
	
	//系统其它异常
	,SYSTEM_PASS_NULL_ERROR(4000,"未查询到匹配的密码信息")
	,SYSTEM_RESPOOL_NULL_ERROR(4001,"res pool is null")
	,SYSTEM_PORTGROUP_NULL_ERROR(4002,"请先检查是否已将虚拟交换机信息导入到云平台,或检查该虚拟机IP所在的网络资源池，VLANID是否配置正确")
	
	//monitor
	,MONITOR_RESTFUL_ERROR(5000,"monitor restful error")
	,MONITOR_RESTFUL_RETCODE_ERROR(5001,"monitor restful retcode error")
	
	,BILLING_SERVICEID_NULL_ERROR(5002,"serviceId is null")
	,BILLING_CPUNUM_NULL_ERROR(5003,"cpuNum is null")
	,BILLING_MEMNUM_NULL_ERROR(5004,"memNum is null")
	,BILLING_DISKNUM_NULL_ERROR(5005,"diskNum is null")
	,BILLING_FLOATIP_NUM_NULL_ERROR(5006,"floatIpNum is null")
	,BILLING_NULL_ERROR(5007,"floatIpNum is null")
	,BILLING_PARAM_NULL_ERROR(5008,"parameter is null")
	,BILLING_VALUE_NULL_ERROR(5009,"cost is null")
	,BILLING_URL_NULL_ERROR(5010,"request url is null")
	,BILLING_TYPE_NULL_ERROR(5011,"costType  is null")
	,BILLING_TENANTID_NULL_ERROR(5012,"tenantId  is null")
	,BILLING_QUERY_ACCOUNT_BALANCE_URL_NULL_ERROR(5013,"queryAccountBalanceUrl  is null")
	,BILLING_QUERY_TOTAL_INVOICE_URL_NULL_ERROR(5014,"queryTotalInvoice  is null")
	,BILLING_BILLMONTH_NULL_ERROR(5015,"billMonth  is null")
	,BILLING_QUERY_VOUCHER_URL_NULL_ERROR(5016,"queryBillVoucherListUrl  is null")
	,HTTP_REST_ERROR(9999,null)

    /**
     * 错误: 无法删除有设备的资源池
     */
    ,DELETE_RESPOOL_ERROR(504, "错误: 当前资源池下已存在云主机，不能删除！")
	/**
	 * 创建端口镜像失败
	 */
	,CREATE_TAP_ERROR(505, "创建端口镜像失败!")
	/**
	 * 同步镜像失败
	 */
	,SYNC_IMAGE_ERROR(506, "同步镜像失败")
	/**
	 * 保存虚拟机组失败
	 */
	,SAVE_VM_GROUP_ERROR(507, "保存虚拟机组失败")
	/**
	 * vip要绑定的网卡id为空
	 */
	,VIP_NETWORK_CARD_ID_NULL_ERROR(507, "VIP networkCardId is null")
	/**
	 * vip要绑定的网卡ip为空
	 */
	,VIP_NETWORK_CARD_IP_NULL_ERROR(508, "VIP  networkCardIp null")
	/**
	 *  vip列表为空
	 */
	,VIP_LIST_NULL_ERROR(509, "VIP list is null")
	/**
	 *  deviceId为空
	 */
	,VIP_DEVICE_ID_NULL_ERROR(510, "deviceId is null")
	/**
	 *  projectId为空
	 */
	,VIP_PROJECT_ID_NULL_ERROR(511, "projectId is null")
	/**
	 * vip要解绑的网卡id列表为空
	 */
	,VIP_NETWORK_CARD_ID_LIST_NULL_ERROR(512, "VIP networkCardId list is null")
	/**
	 * vip id 列表
	 */
	,VIP_ID_LIST_NULL_ERROR(512, "VIP id list is null")
	/**
	 * vip ip 列表 
	 */
	,VIP_IP_LIST_NULL_ERROR(512, "VIP ip list is null")
	;
	
	private Integer code;
	private String message;
	
	
	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
