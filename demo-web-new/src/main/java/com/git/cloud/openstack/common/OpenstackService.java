package com.git.cloud.openstack.common;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.model.CacheTypeEnum;
import com.git.cloud.common.tools.SysCacheManager;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.support.common.MesgFlds;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * Openstack通用接口类
 * 对一些通用的处理方法进行抽象
 * 有整体逻辑修改时，编译维护
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackService {
	
	private static Logger logger = LoggerFactory.getLogger(OpenstackService.class);
	
	protected final int TIME_OUT = 120000; // 默认2分钟超时时间
	
	private ResAdptInvokerFactory invkerFactory;
	
	protected String token = "";
	protected String openstackIp = "";
	protected String domainName = "";
	
//	protected void 
	
	protected IDataObject getIDataObject(String operation) {
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
	
	protected BodyDO createBodyDO() {
		BodyDO bodyDO = BodyDO.CreateBodyDO();
		if(!"".equals(token)) {
			bodyDO.setString("TOKEN", token);
		}
		return bodyDO;
	}
	
	protected HashMap<String, Object> createParamDOMap() {
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
	
	protected String getExecuteMessage(IDataObject rspData) {
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
		return rspBody.getString("OPENSTACK_MESSAGE");
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
}
