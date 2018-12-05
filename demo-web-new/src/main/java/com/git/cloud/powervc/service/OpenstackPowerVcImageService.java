package com.git.cloud.powervc.service;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * Openstack镜像资源接口
 * @author 
 * @version 
 */
public class OpenstackPowerVcImageService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcImageService openstackImageService;
	
	private OpenstackPowerVcImageService(){};
	
	public static OpenstackPowerVcImageService getImageServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackImageService == null) {
			openstackImageService = new OpenstackPowerVcImageService();
		}
		openstackImageService.openstackIp = openstackIp;
		openstackImageService.domainName = domainName;
		openstackImageService.token = token;
		return openstackImageService;
	}
	
	
	
	
	
	
	/**
	 * 创建端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String createPort(String networkId, String subnetId,String ipAddress,String name) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_PORT);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		body.setString("SUBNET_ID", subnetId);
		body.setString("IP_ADDRESS", ipAddress);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	
	
}
