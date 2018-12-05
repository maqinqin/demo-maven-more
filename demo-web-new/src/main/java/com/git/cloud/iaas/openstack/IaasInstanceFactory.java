package com.git.cloud.iaas.openstack;

import com.git.cloud.iaas.openstack.enums.VersionEnum;
import com.git.cloud.iaas.openstack.service.OpenstackComputeService;
import com.git.cloud.iaas.openstack.service.OpenstackComputeService2Impl;
import com.git.cloud.iaas.openstack.service.OpenstackComputeService6Impl;
import com.git.cloud.iaas.openstack.service.OpenstackIdentityService;
import com.git.cloud.iaas.openstack.service.OpenstackIdentityService2Impl;
import com.git.cloud.iaas.openstack.service.OpenstackIdentityService6Impl;
import com.git.cloud.iaas.openstack.service.OpenstackImageService;
import com.git.cloud.iaas.openstack.service.OpenstackImageService2Impl;
import com.git.cloud.iaas.openstack.service.OpenstackImageService6Impl;
import com.git.cloud.iaas.openstack.service.OpenstackNetworkService;
import com.git.cloud.iaas.openstack.service.OpenstackNetworkService2Impl;
import com.git.cloud.iaas.openstack.service.OpenstackNetworkService6Impl;
import com.git.cloud.iaas.openstack.service.OpenstackStorageService;
import com.git.cloud.iaas.openstack.service.OpenstackStorageService2Impl;
import com.git.cloud.iaas.openstack.service.OpenstackStorageService6Impl;

public class IaasInstanceFactory {
	//认证
	private static final OpenstackIdentityService openstackIdentityService2Impl = new OpenstackIdentityService2Impl();
	private static final OpenstackIdentityService openstackIdentityService6Impl = new OpenstackIdentityService6Impl();
	//计算
	private static final OpenstackComputeService openstackComputeService2Impl = new OpenstackComputeService2Impl();
	private static final OpenstackComputeService openstackComputeService6Impl = new OpenstackComputeService6Impl();
	//存储
	private static final OpenstackStorageService openstackStorageService2Impl = new OpenstackStorageService2Impl();
	private static final OpenstackStorageService openstackStorageService6Impl = new OpenstackStorageService6Impl();
	//网络
	private static final OpenstackNetworkService openstackNetworkService2Impl = new OpenstackNetworkService2Impl();
	private static final OpenstackNetworkService openstackNetworkService6Impl = new OpenstackNetworkService6Impl();
	//镜像
	private static final OpenstackImageService openstackImageService2Impl = new OpenstackImageService2Impl();
	private static final OpenstackImageService openstackImageService6Impl = new OpenstackImageService6Impl();
	
	public static OpenstackIdentityService identityInstance(String version) {
		if(VersionEnum.HW_VSERSION_63.getValue().equals(version)) {
			return openstackIdentityService6Impl;
		} else {
			return openstackIdentityService2Impl;
		}
	}
	
	public static OpenstackComputeService computeInstance(String version) {
		if(VersionEnum.HW_VSERSION_63.getValue().equals(version)) {
			return openstackComputeService6Impl;
		} else {
			return openstackComputeService2Impl;
		}
	}
	
	public static OpenstackStorageService storageInstance(String version) {
		if(VersionEnum.HW_VSERSION_63.getValue().equals(version)) {
			return openstackStorageService6Impl;
		} else {
			return openstackStorageService2Impl;
		}
	}
	
	public static OpenstackNetworkService networkInstance(String version) {
		if(VersionEnum.HW_VSERSION_63.getValue().equals(version)) {
			return openstackNetworkService6Impl;
		} else {
			return openstackNetworkService2Impl;
		}
	}
	
	public static OpenstackImageService imageInstance(String version) {
		if(VersionEnum.HW_VSERSION_63.getValue().equals(version)) {
			return openstackImageService6Impl;
		} else {
			return openstackImageService2Impl;
		}
	}
}
