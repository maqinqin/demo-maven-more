package com.git.cloud.powervc.service;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * Openstack裸机接口
 * @author SunHailong
 * @version v1.0 2017-3-28
 */
public class OpenstackPowerVcBaremetalService extends OpenstackPowerVcService {

	private static OpenstackPowerVcBaremetalService openstackBaremetalService;
	
	private OpenstackPowerVcBaremetalService(){};
	
	public static OpenstackPowerVcBaremetalService getBaremetalServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackBaremetalService == null) {
			openstackBaremetalService = new OpenstackPowerVcBaremetalService();
		}
		openstackBaremetalService.openstackIp = openstackIp;
		openstackBaremetalService.domainName = domainName;
		openstackBaremetalService.token = token;
		return openstackBaremetalService;
	}
	
}
