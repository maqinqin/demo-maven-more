package com.git.cloud.common.enums;

public enum KeyTypeEnum {
	COMPUTE_CLUSTER("COMPUTE_CLUSTER"),					// 集群-主机组
	COMPUTE_HOST("COMPUTE_HOST"), 						// 物理机-主机
	COMPUTE_VM("COMPUTE_VM"), 							// 虚拟机-云主机
	COMPUTE_BAREMETAL("COMPUTE_BAREMETAL"),				// 裸机-物理机
	COMPUTE_FLAVOR("COMPUTE_FLAVOR"), 					// 规格：openstack_cloud_flavor
	//VOLUME("VOLUME"), 									// disk-卷
	VOLUME_TYPE("VOLUME_TYPE"), 						// disk-卷类型
	VOLUME_ID("VOLUME_ID"), 							// disk-卷ID
	NETWORK_EXTERNAL("NETWORK_EXTERNAL"), 				// 网络资源池-外部网络：rm_nw_openstack_external_network
	NETWORK_EXTERNAL_SUBNET("NETWORK_EXTERNAL_SUBNET"), // 外部网络子网：rm_nw_openstack_external_subnet
	NETWORK_PRIVATE("NETWORK_PRIVATE"), 				// 网络资源池-内部网络
	NETWORK_PRIVATE_SUBNET("NETWORK_PRIVATE_SUBNET"), 	// 内部网络子网
	NETWORK_VIRTUAL_ROUTER("NETWORK_VIRTUAL_ROUTER"), 	// 虚拟路由器：rm_nw_openstack_virtual_router
	NETWORK_FLOATING_IP("NETWORK_FLOATING_IP"), 		// 弹性ip：rm_nw_openstack_floating_ip
	IMAGE_IMAGE_ID("IMAGE_IMAGE_ID"), 					// 镜像ID
	IDENTITY_PROJECT("IDENTITY_PROJECT") ,				// project（VPC）：rm_nw_openstack_project
	NETWORK_VLB_POOL("NETWORK_VLB_POOL"),				// 负载均衡池ID：rm_nw_openstack_vlb_pool
	NETWORK_VLB_VIP("NETWORK_VLB_VIP"),					// 负载均衡VIP：rm_nw_openstack_vlb_vip
	NETWORK_VLB_HEALTH("NETWORK_VLB_HEALTH"),			// 负载均衡监控：rm_nw_openstack_vlb_health_monitor
	NETWORK_VLB_MEMBER("NETWORK_VLB_MEMBER"),			// 负载均衡成员：rm_nw_openstack_vlb_member
	NETWORK_SG("NETWORK_SG"),							// 安全组：rm_nw_openstack_security_groups
	NETWORK_SG_RULES("NETWORK_SG_RULES"),				// 安全组规则：rm_nw_openstack_security_groups_rules
	NETWORK_VFW_POLICY("NETWORK_VFW_POLICY"),			// 虚拟防火墙策略：rm_nw_openstack_vfw_policy
	NETWORK_VFW_POLICY_RULE("NETWORK_VFW_POLICY_RULE"),	// 虚拟防火墙策略规则：rm_nw_openstack_vfw_policy_rule
	NETWORK_VFW("NETWORK_VFW")	,						// 虚拟防火墙：rm_nw_openstack_vfw
	FI("FI"),                                   //大数据
	// 虚拟防火墙：rm_nw_openstack_vfw

	/**
	 * 虚拟机组
	 */
	VM_GROUP("VM_GROUP")
	;
    
    private final String value;

    private KeyTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static KeyTypeEnum fromString(String value ) {
		if (value != null) {
			for (KeyTypeEnum c : KeyTypeEnum.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }
}
