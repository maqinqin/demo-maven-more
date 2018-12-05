package com.git.cloud.powervc.service;

import java.util.HashMap;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.cloud.powervc.model.OpenstackManageEnum;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Openstack认证资源接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcIdentityService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcIdentityService openstackIdentityService;
	
	private OpenstackPowerVcIdentityService(){};
	
	public static OpenstackPowerVcIdentityService getIdentityServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackIdentityService == null) {
			openstackIdentityService = new OpenstackPowerVcIdentityService();
		}
		openstackIdentityService.openstackIp = openstackIp;
		openstackIdentityService.domainName = domainName;
		openstackIdentityService.token = token;
		return openstackIdentityService;
	}
	
	public String createProjectForManageUser(String projectName) throws Exception {
		String projectId = "";
		try {
			// 获取管理员DomainId
			String domainId = this.getManageDomain();
			// 获取管理员用户Id
			String userId = this.getManageUser();
			// 获取管理员角色Id
			String roleId = this.getManageRole();
			// 创建project
			projectId = this.createProject(domainId, projectName);
			// 为project赋权限
			String msg = this.putProjectRole(projectId, userId, roleId);
			if(!"".equals(msg)) {
				throw new Exception("为project赋权限失败");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return projectId;
	}
	
	public String createProject(String domainId, String projectName) throws Exception {
		String projectId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_PROJECT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_NAME", projectName);
		body.setString("DOMAIN_ID", domainId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			projectId = json.getJSONObject("project").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return projectId;
	}
	
	
	/**
	 * 删除VPC
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteProject(String projectId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_PROJECT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	public String putProjectRole(String projectId, String userId, String roleId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_PROJECT_ROLE);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("USER_ID", userId);
		paramMap.put("ROLE_ID", roleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("USER_ID", userId);
		body.setString("ROLE_ID", roleId);
		reqData.setDataObject(MesgFlds.BODY, body);
		String result = "";
		try {
			IDataObject rspData = this.execute(reqData);
			if(this.getExecuteResult(rspData) != 204) { // 设置成功编码为204
				throw new Exception(this.getExecuteMessage(rspData));
			}
		} catch(Exception e) {
			result = e.getMessage();
		}
		return result;
	}
	
	public String getManageDomain() throws Exception {
		String domainId = "";
		String domainName = "Default";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_DOMAIN);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
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
				throw new Exception("domain名称["+domainName+"]在服务器["+openstackIp+"]不存在");
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return domainId;
	}
	
	public String getManageUser() throws Exception {
		String userId = "";
		String userName = null;// this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_USER.getValue());
		IDataObject reqData = this.getIDataObject(PVOperation.GET_USER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
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
				throw new Exception("用户名称["+userName+"]在服务器["+openstackIp+"]不存在");
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return userId;
	}
	
	public String getManageProject() throws Exception {
		String projectId = "";
		String projectName = null;//this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PV_MANAGE_PROJECT.getValue());
		IDataObject reqData = this.getIDataObject(PVOperation.GET_PROJECT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
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
				throw new Exception("project名称["+projectName+"]在服务器["+openstackIp+"]不存在");
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return projectId;
	}
	
	public String getManageRole() throws Exception {
		String roleId = "";
		String roleName = "admin";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_ROLE);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
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
				throw new Exception("角色名称["+roleName+"]在服务器["+openstackIp+"]不存在");
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return roleId;
	}
}
