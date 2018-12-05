package com.git.cloud.iaas.openstack;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.model.CacheTypeEnum;
import com.git.cloud.common.tools.SysCacheManager;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.iaas.openstack.enums.VersionEnum;
import com.git.cloud.iaas.openstack.model.OpenstackErrorModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.openstack.common.OpenstackService;
import com.git.support.common.MesgFlds;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class OpenstackInvokeTools {

	
	private static Logger logger = LoggerFactory.getLogger(OpenstackService.class);
	
	protected final int TIME_OUT = 120000; // 默认2分钟超时时间
	
	private ResAdptInvokerFactory invkerFactory;
	
	
//	protected void 
	
	protected IDataObject getIDataObject(String operation,String openstackIp,String domainName,String token) {
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass("OS");
		header.setResourceType("COM");
		header.setAction(operation);
		header.setString("OPENSTACK_IP", openstackIp);
		header.setString("DOMAIN_NAME", domainName);
		if(!"".equals(token)) {
			header.setString("X-Auth-Token", token);
		}
		// 根据服务器ip查询所属数据中心
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), "BJ");
		reqData.setDataObject(MesgFlds.HEADER, header);
		return reqData;
	}
	
	protected BodyDO createBodyDO(String token) {
		BodyDO bodyDO = BodyDO.CreateBodyDO();
		if(!"".equals(token)) {
			bodyDO.setString("TOKEN", token);
		}
		return bodyDO;
	}
	
	protected HashMap<String, Object> createParamDOMap(String domainName) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		if(!"".equals(domainName)) {
			paramMap.put("domainName", domainName);
		}
		return paramMap;
	}
	
	
	protected IDataObject execute(IDataObject reqData) throws Exception {
		return this.getResAdpterInvoker().invoke(reqData, TIME_OUT);
	}
	
	protected IDataObject execute(IDataObject reqData, int timeOut) throws Exception {
		return this.getResAdpterInvoker().invoke(reqData, timeOut);
	}
	
	protected int getExecuteResult(IDataObject rspData) {
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
		return rspBody.getInt("OPENSTACK_RESULT");
	}

	/**
	 * 获取返回错误信息
	 *
	 * @param rspData
	 * @return
	 */
	protected OpenstackErrorModel getExecuteMessage(IDataObject rspData) {
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
		String jsonStr = rspBody.getString("OPENSTACK_MESSAGE");
		final String splitStr = ";";
		final String trueJsonSplitStr = "异常信息:";
		final String exceptionSplitStr = ": 状态码:";
		final String requestSplitStr = "request for";
		OpenstackErrorModel openstackErrorModel = new OpenstackErrorModel();
		if (jsonStr.contains(splitStr) && jsonStr.contains(trueJsonSplitStr)) {
			String part1 = jsonStr.split(splitStr)[0];
			// 获取异常信息
			String exceptionStr = part1.split(exceptionSplitStr)[0];
			if (exceptionStr.contains(requestSplitStr)) {
				// url
				String url = exceptionStr.split(requestSplitStr)[1].trim().replace("\"", "");
				openstackErrorModel.setUrl(url);
				// requestMethod
				String requestMethodStr = exceptionStr.split(requestSplitStr)[0];
				if (requestMethodStr.contains(RequestMethod.POST.name())) {
					openstackErrorModel.setRequestMethod(RequestMethod.POST);
				} else if (requestMethodStr.contains(RequestMethod.GET.name())) {
					openstackErrorModel.setRequestMethod(RequestMethod.GET);
				} else if (requestMethodStr.contains(RequestMethod.PUT.name())) {
					openstackErrorModel.setRequestMethod(RequestMethod.PUT);
				} else if (requestMethodStr.contains(RequestMethod.DELETE.name())) {
					openstackErrorModel.setRequestMethod(RequestMethod.DELETE);
				}
			}
			// 状态码
			String code = part1.split(exceptionSplitStr)[1].split(trueJsonSplitStr)[0].replace(",", "");
			// 获取openstack返回信息
			String trueJson = part1.split(trueJsonSplitStr)[1];
			JSONObject jsonObject = JSONObject.parseObject(trueJson);
			JSONObject errorObj = jsonObject.getJSONObject("error");
			JSONObject neutronErrorObj = jsonObject.getJSONObject("NeutronError");
			if (errorObj != null) {
				openstackErrorModel.setTitle(errorObj.getString("title"));
				openstackErrorModel.setCode(errorObj.getString("code"));
				openstackErrorModel.setMessage(errorObj.getString("message"));
			}
			else if(neutronErrorObj != null) {
				openstackErrorModel.setMessage(neutronErrorObj.getString("message"));
				openstackErrorModel.setType(neutronErrorObj.getString("type"));
				openstackErrorModel.setDetail(neutronErrorObj.getString("detail"));
				openstackErrorModel.setCode(code);
			}
			else {
				openstackErrorModel.setMessage(trueJson);
			}
		} else {
			openstackErrorModel.setMessage(jsonStr);
		}

		return openstackErrorModel;
	}
	
	protected String getExecuteResultData(IDataObject rspData) {
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
		return rspBody.getString("OPENSTACK_RESULT_DATA");
	}
	
	protected String getExecuteResultData(IDataObject rspData, String key) {
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
		return rspBody.getString(key);
	}
	
	protected IResAdptInvoker getResAdpterInvoker() throws Exception {
		if (invkerFactory == null) {
			invkerFactory = (ResAdptInvokerFactory) WebApplicationManager.getBean("resInvokerFactory");
		}
		return invkerFactory.findInvoker("AMQ");
	}
	
	protected String getAdminParamCache(String key) {
		String value = "";
		try {
			value = SysCacheManager.getString(CacheTypeEnum.SYSTEM_PARAMETER.getValue(), key);
			if(value == null || "".equals(value)) {
				value = "";
				logger.error("获取缓存信息为空，key:" + key);
			}
		} catch(Exception e) {
			logger.error("异常exception",e);
			logger.error("获取缓存信息失败，key:" + key);
		}
		return value;
	}
	
	/**
	 * 管理员token
	 * @param openstackIp
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	public String getToken(String version,String openstackIp,String domainName,String manegeOneIp) throws Exception{
		String token = IaasInstanceFactory.identityInstance(version).getToken(openstackIp, domainName, manegeOneIp);
		return token;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String getToken(String version,OpenstackIdentityModel model) throws Exception{
		String token = IaasInstanceFactory.identityInstance(version).getToken(model);
		return token;
	}
	
	/**
	 * 6.3创建规格使用
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public JSONObject getToken(String manageOneIp) throws Exception{
		JSONObject tokenForFlavor = IaasInstanceFactory.identityInstance(VersionEnum.HW_VSERSION_63.getValue()).getTokenForFlavor(manageOneIp);
		return tokenForFlavor;
	}

}
