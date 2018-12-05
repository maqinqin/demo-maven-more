package com.git.cloud.powervc.common;

import com.git.cloud.powervc.service.OpenstackPowerVcBaremetalService;
import com.git.cloud.powervc.service.OpenstackPowerVcComputeService;
import com.git.cloud.powervc.service.OpenstackPowerVcCpsService;
import com.git.cloud.powervc.service.OpenstackPowerVcIdentityService;
import com.git.cloud.powervc.service.OpenstackPowerVcImageService;
import com.git.cloud.powervc.service.OpenstackPowerVcNetworkService;
import com.git.cloud.powervc.service.OpenstackPowerVcVolumeService;

/** 
 * Openstack接口实例工厂
 * @author SunHailong 
 * @version 版本号 2017-3-21
 */
public class OpenstackPowerVcServiceFactory {
	/**
	 * 获取Token接口实例
	 * @return
	 */
	public static OpenstackPowerVcTokenService getTokenServiceInstance(String openstackIp,String domainName) {
		return OpenstackPowerVcTokenService.getTokenServiceInstance(openstackIp,domainName);
	}
	/**
	 * 获取认证类接口实例
	 * @return
	 */
	public static OpenstackPowerVcIdentityService getIdentityServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcIdentityService.getIdentityServiceInstance(openstackIp,domainName, token);
	}
	/**
	 * 获取计算资源类接口实例
	 * @return
	 */
	public static OpenstackPowerVcComputeService getComputeServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcComputeService.getComputeServiceInstance(openstackIp,domainName, token);
	}
	/**
	 * 获取网络资源类接口实例
	 * @return
	 */
	public static OpenstackPowerVcNetworkService getNetworkServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcNetworkService.getNetworkServiceInstance(openstackIp,domainName, token);
	}
	/**
	 * 获取存储资源类接口实例
	 * @return
	 */
	public static OpenstackPowerVcVolumeService getVolumeServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcVolumeService.getVolumeServiceInstance(openstackIp,domainName, token);
	}
	/**
	 * 获取裸机类接口实例
	 * @return
	 */
	public static OpenstackPowerVcBaremetalService getBaremetalServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcBaremetalService.getBaremetalServiceInstance(openstackIp,domainName, token);
	}
	/**
	 * 获取镜像类接口实例
	 * @return
	 */
	public static OpenstackPowerVcImageService getImageServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcImageService.getImageServiceInstance(openstackIp,domainName, token);
	}
	
	public static OpenstackPowerVcCpsService getCpsServiceInstance(String openstackIp,String domainName, String token) {
		return OpenstackPowerVcCpsService.getCpsServiceInstance(openstackIp,domainName, token);
	}
}
