package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.iaas.enums.ResultEnum;
import com.git.cloud.iaas.openstack.common.Constants;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.ProjectRestModel;
import com.git.cloud.iaas.openstack.model.TenantException;
import com.git.cloud.iaas.openstack.model.TokenModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;


public class OpenstackIdentityService2Impl extends OpenstackIdentityServiceAbstractImpl implements OpenstackIdentityService  {
	
	
	public String getToken(OpenstackIdentityModel model) throws Exception {
		// TODO Auto-generated method stub
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(model.getOpenstackIp());
		tokenModel.setDomainName(model.getDomainName());
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_DOMAIN));
		tokenModel.setProjectName(model.getProjectName());
		tokenModel.setProjectDomain(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_DOMAIN));
		return this.getToken(tokenModel);
	}

	
	public String getToken(String openstackIp,String domainName,String manegeOneIp) throws Exception {
		TokenModel tokenModel = new TokenModel();
		tokenModel.setOpenstackIp(openstackIp);
		tokenModel.setDomainName(domainName);
		tokenModel.setFsUserName(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_USER));
		tokenModel.setFsPassword(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_PWD));
		tokenModel.setUserDomain(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_DOMAIN));
		tokenModel.setProjectName(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_PROJECT));
		tokenModel.setProjectDomain(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_DOMAIN));
		return this.getToken(tokenModel);
	}
	
	public String getToken(TokenModel tokenModel) throws Exception {
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_TOKEN + 206,tokenModel.getOpenstackIp(),tokenModel.getDomainName(),"");
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(tokenModel.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO("");
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
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return token;
	}
	
	
	public String createProjectForManageUser(OpenstackIdentityModel model, ProjectRestModel project) throws Exception {
		String projectId = "";
		String token = this.getToken(model.getOpenstackIp(), model.getDomainName(),model.getManageOneIp());
		model.setToken(token);
		// 获取管理员DomainId
		String domainId = this.getManageDomain(model);
		// 获取管理员用户Id
		String userId = this.getManageUser(model);
//		// 获取管理员角色Id
		String roleId = this.getManageRole(model);
//		// 创建project
		projectId = this.createProject(model,domainId, project.getProjectName());
//		// 为project赋权限
		String msg = this.putProjectRole(model,projectId, userId, roleId);
		if(!"".equals(msg)) {
			throw new TenantException(ResultEnum.OPENSTACK_PROJECT_CREATE_ERROR, "为project赋权限失败");
		}
		return projectId;
	}
	
	/**
	 * 创建project绑定用户
	 * @param projectName
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String createProjectForUser(OpenstackIdentityModel model,String projectName, String userId) throws Exception {
		String projectId = "";
		String token = this.getToken(model.getOpenstackIp(), model.getDomainName(),model.getManageOneIp());
		model.setToken(token);
		try {
			// 获取管理员DomainId
			String domainId = this.getManageDomain(model);
			// 获取管理员用户Id
			String manageUserId = this.getManageUser(model);
			// 获取管理员角色Id
			String roleId = this.getManageRole(model);
			// 创建project
			projectId = this.createProject(model,domainId, projectName);
			// 为管理员project赋权限
			String msg = this.putProjectRole(model,projectId, manageUserId, roleId);
			if(!"".equals(msg)) {
				throw new Exception("为project赋权限失败");
			}
			// 为project赋权限
			msg = this.putProjectRole(model,projectId, userId, roleId);
			if(!"".equals(msg)) {
				throw new Exception("为用户project赋权限失败");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return projectId;
	}
	
	/**
	 * 删除project
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteProject(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_PROJECT + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public String createProject(OpenstackIdentityModel model,String domainId, String projectName) throws Exception {
		String projectId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_PROJECT + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_NAME", projectName);
		body.setString("DOMAIN_ID", domainId);
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
	
	public String putProjectRole(OpenstackIdentityModel model,String projectId, String userId, String roleId) throws Exception {
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_PROJECT_ROLE + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("USER_ID", userId);
		paramMap.put("ROLE_ID", roleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", projectId);
		body.setString("USER_ID", userId);
		body.setString("ROLE_ID", roleId);
		reqData.setDataObject(MesgFlds.BODY, body);
		String result = "";
		try {
			IDataObject rspData = this.execute(reqData);
			if(this.getExecuteResult(rspData) != 204) { // 设置成功编码为204
				throw new OpenstackException(this.getExecuteMessage(rspData));
			}
		} catch(Exception e) {
			result = e.getMessage();
		}
		return result;
	}
	
	public String getManageDomain(OpenstackIdentityModel model) throws Exception {
		String domainId = "";
		String domainName = Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_DOMAIN);
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_DOMAIN + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO(model.getToken()));
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			JSONArray jsonArr = json.getJSONArray("domains");
			String name = "";
			for(int i=0 ; i<jsonArr.size() ; i++) {
				name = jsonArr.getJSONObject(i).getString("name");
				if(name != null && name.equals(domainName)) {
					domainId = jsonArr.getJSONObject(i).getString("id");
					break;
				}
			}
			if("".equals(domainId)) {
				throw new Exception("domain名称["+domainName+"]在服务器["+model.getOpenstackIp()+"]不存在");
			}
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return domainId;
	}
	
	public String getManageUser(OpenstackIdentityModel model) throws Exception {
		String userId = "";
		String userName = Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_USER);
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_USER + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO(model.getToken()));
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			JSONArray jsonArr = json.getJSONArray("users");
			String name = "";
			for(int i=0 ; i<jsonArr.size() ; i++) {
				name = jsonArr.getJSONObject(i).getString("name");
				if(name != null && name.equals(userName)) {
					userId = jsonArr.getJSONObject(i).getString("id");
					break;
				}
			}
			if("".equals(userId)) {
				throw new Exception("用户名称["+userName+"]在服务器["+model.getOpenstackIp()+"]不存在");
			}
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return userId;
	}
	
	public String getManageRole(OpenstackIdentityModel model) throws Exception {
		String roleId = "";
		String roleName = Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_ROLE);
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_ROLE + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO(model.getToken()));
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			JSONArray jsonArr = json.getJSONArray("roles");
			String name = "";
			for(int i=0 ; i<jsonArr.size() ; i++) {
				name = jsonArr.getJSONObject(i).getString("name");
				if(name != null && name.equals(roleName)) {
					roleId = jsonArr.getJSONObject(i).getString("id");
					break;
				}
			}
			if("".equals(roleId)) {
				throw new Exception("角色名称["+roleName+"]在服务器["+model.getOpenstackIp()+"]不存在");
			}
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return roleId;
	}
	
	/**
	 * 创建HA stack
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void createHAStack(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_HA_STACK + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		this.execute(reqData);
	}
	
	/**
	 * 创建用户
	 * @param domainId
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String createUser(OpenstackIdentityModel model,String domainId, String username, String password) throws Exception {
		model.setToken(this.getToken(model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String userId = null;
		// 发送请求创建用户
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_USER + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("DOMAIN_ID", domainId);
		body.setString("USER_NAME", username);
		body.setString("USER_PASSWORD", password);
		reqData.setDataObject(MesgFlds.BODY, body);
		// 执行
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			JSONObject userObj = json.getJSONObject("user");
			String id = userObj.getString("id");
			userId = id;
		}
		else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return userId;
	}
	
	/**
	 * 删除用户
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUser(OpenstackIdentityModel model,String userId) throws Exception {
		model.setToken(this.getToken(model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_USER + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("USER_ID", userId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("USER_ID", userId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public String getManageProject(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String projectId = "";
		String projectName = Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_PROJECT);
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_PROJECT + 206,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO(model.getToken()));
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			JSONArray jsonArr = json.getJSONArray("projects");
			String name = "";
			for(int i=0 ; i<jsonArr.size() ; i++) {
				name = jsonArr.getJSONObject(i).getString("name");
				if(name != null && name.equals(projectName)) {
					projectId = jsonArr.getJSONObject(i).getString("id");
					break;
				}
			}
			if("".equals(projectId)) {
				throw new Exception("project名称["+projectName+"]在服务器["+model.getOpenstackIp()+"]不存在");
			}
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return projectId;
	}


	@Override
	public String getRegion(OpenstackIdentityModel model) throws Exception {
		// TODO Auto-generated method stub
		//使用6.3
		return null;
	}


	@Override
	public JSONObject getTokenForFlavor(String manegeOneIp) throws Exception {
		// TODO Auto-generated method stub
		//使用6.3
		return null;
	}
}
