package com.git.cloud.powervc.common;

import java.util.HashMap;

import com.git.cloud.powervc.model.OpenstackManageEnum;
import com.git.cloud.powervc.model.TokenModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * OpenstackToken接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcTokenService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcTokenService openstackTokenService;
	
	private OpenstackPowerVcTokenService(){};
	
	public static OpenstackPowerVcTokenService getTokenServiceInstance(String openstackIp, String domainName) {
		if(openstackTokenService == null) {
			openstackTokenService = new OpenstackPowerVcTokenService();
		}
		openstackTokenService.openstackIp = openstackIp;
		openstackTokenService.domainName = domainName;
		return openstackTokenService;
	}
	
	public String getToken() throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(openstackIp);
		/*tokenModel.setFsUserName(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_USER.getValue()));
		tokenModel.setFsPassword(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_PWD.getValue()));
		tokenModel.setUserDomain(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_DOMAIN.getValue()));
		tokenModel.setProjectName(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_PROJECT.getValue()));
		tokenModel.setProjectDomain(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_DOMAIN.getValue()));*/
		return this.getToken(tokenModel);
	}
	
	public String getToken(String projectName) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(openstackIp);
		tokenModel.setFsUserName(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_USER.getValue()));
		tokenModel.setFsPassword(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_PWD.getValue()));
		tokenModel.setUserDomain(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_DOMAIN.getValue()));
		tokenModel.setProjectName(projectName);
		tokenModel.setProjectDomain(this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_DOMAIN.getValue()));
		return this.getToken(tokenModel);
	}
	
	public String getToken(TokenModel tokenModel) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_TOKEN);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("FS_USER_NAME", tokenModel.getFsUserName());
		body.setString("FS_PASSWORD", tokenModel.getFsPassword());
		body.setString("PROJECT_NAME", tokenModel.getProjectName());
		body.setString("USER_DOMAIN", tokenModel.getUserDomain());
		body.setString("PROJECT_DOMAIN", tokenModel.getProjectDomain());
		reqData.setDataObject(MesgFlds.BODY, body);
		String token = "";
		IDataObject rspData = this.execute(reqData);
		token = this.getExecuteResultData(rspData, "TOKEN");
		if(token == null || "".equals(token)) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return token;
	}
}
