package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.iaas.openstack.common.Constants;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.ProjectRestModel;
import com.git.cloud.iaas.openstack.model.TokenModel;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.IRmVmManageServerService;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public class OpenstackIdentityService6Impl extends OpenstackIdentityServiceAbstractImpl implements OpenstackIdentityService {

	@Override
	public String getToken(OpenstackIdentityModel model) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(model.getOpenstackIp());
		tokenModel.setDomainName(model.getManageOneIp());
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.SC_VDC_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.SC_VDC_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.SC_VDC_DOMINANAME));
		tokenModel.setScopeInfo("{\"project\":{\"id\":"+"\""+model.getProjectId()+"\""+"}}");
		return this.getToken(tokenModel);
	}
	
	public JSONObject getTokenForCreateProject(String manegeOneIp) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(manegeOneIp);
		tokenModel.setDomainName(manegeOneIp);
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.SC_VDC_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.SC_VDC_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.SC_VDC_DOMINANAME));
		//{"domain":{"name":"gwd_tenant"}}
		tokenModel.setScopeInfo("{\"domain\":{\"name\":"+"\""+tokenModel.getUserDomain()+"\""+"}}");
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_TOKEN + 63,tokenModel.getOpenstackIp(),tokenModel.getDomainName(),"");
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(manegeOneIp);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO("");
		body.setString("FS_USER_NAME", tokenModel.getFsUserName());
		body.setString("FS_PASSWORD", tokenModel.getFsPassword());
		body.setString("USER_DOMAIN", tokenModel.getUserDomain());
		body.setString("SCOPE", tokenModel.getScopeInfo());
		reqData.setDataObject(MesgFlds.BODY, body);
		String result = "";
		IDataObject rspData = this.execute(reqData);
		String token = this.getExecuteResultData(rspData, "TOKEN");
		result = this.getExecuteResultData(rspData);
		if(result == null || "".equals(result)) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String userId = JSONObject.parseObject(result).getJSONObject("token").getJSONObject("user").getString("id");
		JSONObject jso = new JSONObject();
		jso.put("token", token);
		jso.put("userId", userId);
		return jso;
	}

	@Override
	public JSONObject getTokenForFlavor(String manegeOneIp) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(manegeOneIp);
		tokenModel.setDomainName(manegeOneIp);
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.SC_SYS_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.SC_SYS_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.SC_SYS_DOMAINNAME));
		tokenModel.setScopeInfo("{\"project\":{\"name\":"+"\""+Constants.getConstantsParameter(Constants.SC_SYS_PROJECTNAME)+"\""+"}}");
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_TOKEN + 63,tokenModel.getOpenstackIp(),tokenModel.getDomainName(),"");
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(manegeOneIp);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO("");
		body.setString("FS_USER_NAME", tokenModel.getFsUserName());
		body.setString("FS_PASSWORD", tokenModel.getFsPassword());
		body.setString("USER_DOMAIN", tokenModel.getUserDomain());
		body.setString("SCOPE", tokenModel.getScopeInfo());
		reqData.setDataObject(MesgFlds.BODY, body);
		String result = "";
		IDataObject rspData = this.execute(reqData);
		String token = this.getExecuteResultData(rspData, "TOKEN");
		result = this.getExecuteResultData(rspData);
		if(result == null || "".equals(result)) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String projectId = JSONObject.parseObject(result).getJSONObject("token").getJSONObject("project").getString("id");
		JSONObject jso = new JSONObject();
		jso.put("token", token);
		jso.put("projectId", projectId);
		return jso;
	}

	@Override
	public String getToken(String openstackIp, String domainName,String manegeOneIp) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(openstackIp);
		tokenModel.setDomainName(manegeOneIp);
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.SC_VDC_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.SC_VDC_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.SC_VDC_DOMINANAME));
		IRmVmManageServerService rmVmManageServerServiceImpl = (IRmVmManageServerService) WebApplicationManager.getBean("rmVmManageServerServiceImpl");
		List<RmVmManageServerPo> vmMsList = rmVmManageServerServiceImpl.getRmVmMsIpByVmMsIp(openstackIp);
		if (vmMsList != null && vmMsList.size() > 0) {
			tokenModel.setScopeInfo("{\"project\":{\"id\":"+"\""+vmMsList.get(0).getManageProjectId()+"\""+"}}");
		}
		return this.getToken(tokenModel);
	}
	
	public String getToken(TokenModel tokenModel) throws Exception {
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_TOKEN + 63,tokenModel.getOpenstackIp(),tokenModel.getDomainName(),"");
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(tokenModel.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO("");
		body.setString("FS_USER_NAME", tokenModel.getFsUserName());
		body.setString("FS_PASSWORD", tokenModel.getFsPassword());
		body.setString("USER_DOMAIN", tokenModel.getUserDomain());
		body.setString("SCOPE", tokenModel.getScopeInfo());
		reqData.setDataObject(MesgFlds.BODY, body);
		String token = "";
		IDataObject rspData = this.execute(reqData);
		token = this.getExecuteResultData(rspData, "TOKEN");
		if(token == null || "".equals(token)) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return token;
	}
	
	public String getVdcId(OpenstackIdentityModel model,String userId) throws Exception {
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_USER_DETAIL + 63,model.getOpenstackIp(),model.getManageOneIp(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getManageOneIp());
		paramMap.put("USER_ID", userId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		String result = "";
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String vdcId = JSONObject.parseObject(result).getJSONObject("user").getString("vdc_id");
		return vdcId;
	}

	@Override
	public String createProjectForManageUser(OpenstackIdentityModel model, ProjectRestModel project) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jso = this.getTokenForCreateProject(model.getManageOneIp());
		String token = jso.getString("token");
		String userId = jso.getString("userId");
		model.setToken(token);
		String vdcId = this.getVdcId(model, userId);
		//create project
		String projectId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_PROJECT + 63,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getManageOneIp());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_NAME", project.getProjectName());
		body.setString("VDC_ID", vdcId);
		body.setString("REGION_ID", project.getRegionId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			projectId = json.getJSONObject("project").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return projectId;
	}

	@Override
	public String getRegion(OpenstackIdentityModel model) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jso = this.getTokenForCreateProject(model.getManageOneIp());
		String token = jso.getString("token");
		model.setToken(token);
		String result = "";
		IDataObject reqData = this.getIDataObject("getRegion" + 63,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getManageOneIp());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}

	@Override
	public void deleteProject(OpenstackIdentityModel model) throws Exception {
		// TODO Auto-generated method stub
		JSONObject result = this.getTokenForCreateProject(model.getManageOneIp());
		String token = result.getString("token");
		model.setToken(token);
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_PROJECT + 63,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getManageOneIp());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public void createHAStack(OpenstackIdentityModel model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createUser(OpenstackIdentityModel model, String domainId, String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(OpenstackIdentityModel model, String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createProjectForUser(OpenstackIdentityModel model, String projectName, String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getManageProject(OpenstackIdentityModel model) throws Exception {
		IRmVmManageServerService rmVmManageServerServiceImpl = (IRmVmManageServerService) WebApplicationManager.getBean("rmVmManageServerServiceImpl");
		RmVmManageServerPo vmMs = rmVmManageServerServiceImpl.getRmVmMsIpById(model.getVmMsId());
		return vmMs.getManageProjectId();
	}
}
