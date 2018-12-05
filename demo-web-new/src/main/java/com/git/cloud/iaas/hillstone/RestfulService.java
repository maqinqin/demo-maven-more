package com.git.cloud.iaas.hillstone;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.model.CacheTypeEnum;
import com.git.cloud.common.tools.SysCacheManager;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.iaas.firewall.common.FirewallConstants;
import com.git.support.common.MesgFlds;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class RestfulService {
	
	private static Logger logger = LoggerFactory.getLogger(RestfulService.class);
	
	protected final int TIME_OUT = 120000; // 默认2分钟超时时间
	
	private ResAdptInvokerFactory invkerFactory;
	
	protected IDataObject getIDataObject(HeaderDO header) {
		IDataObject reqData = DataObject.CreateDataObject();
		reqData.setDataObject(MesgFlds.HEADER, header);
		return reqData;
	}
	
	protected HeaderDO createHeaderDO(String operation) {
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass("HS");
		header.setResourceType("COM");
		header.setAction(operation);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), "BJ");
		return header;
	}
	
	protected HeaderDO createHeaderDO(String restIp, String restPort, String restDomain, String operation) {
		HeaderDO header = this.createHeaderDO(operation);
		header.setString("REST_IP", restIp);
		header.setString("REST_PORT", restPort);
		header.setString("REST_DOMAIN", restDomain);
		return header;
	}
	
	protected BodyDO createBodyDO() {
		BodyDO bodyDO = BodyDO.CreateBodyDO();
		
		return bodyDO;
	}
	
	protected HashMap<String, Object> createParamDOMap() {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		return paramMap;
	}
	
	protected IDataObject execute(IDataObject reqData) throws Exception {
		if (FirewallConstants.HS_TEST_FLAG) {
			System.out.println(reqData.toString());
			return null;
		} else {
			return this.getResAdpterInvoker().invoke(reqData, TIME_OUT);
		}
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
