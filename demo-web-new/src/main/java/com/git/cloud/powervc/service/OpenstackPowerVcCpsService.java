package com.git.cloud.powervc.service;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * Openstack网络资源接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcCpsService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcCpsService openstackCpsService;
	
	private OpenstackPowerVcCpsService(){};
	
	public static OpenstackPowerVcCpsService getCpsServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackCpsService == null) {
			openstackCpsService = new OpenstackPowerVcCpsService();
		}
		openstackCpsService.openstackIp = openstackIp;
		openstackCpsService.domainName = domainName;
		openstackCpsService.token = token;
		return openstackCpsService;
	}
	
	
	
}
