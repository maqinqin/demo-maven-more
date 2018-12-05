package com.git.cloud.rest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.rest.model.ReturnMeta;
import com.git.cloud.rest.model.ServiceRequestParam;
import com.git.cloud.rest.service.ServiceRequest;

public class ServiceRequestImpl extends ServiceRequest {
	private static Logger log = LoggerFactory.getLogger(ServiceRequestImpl.class);
	
	public BmSrVo startService(String type, Object jsonData) throws RollbackableBizException {
		BmSrVo bmSr = null;
		log.info("startService 云服务类型："+type);
		if(type.equals(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue()) || type.equals(SrTypeMarkEnum.PHYSICAL_SUPPLY.getValue())) {
			bmSr = super.saveVirtualSupply((JSONObject)jsonData);
		}else if(type.equals(SrTypeMarkEnum.VIRTUAL_EXTEND.getValue())){
			bmSr = super.saveVirtualExtend((JSONObject)jsonData);
		}else if(type.equals(SrTypeMarkEnum.VIRTUAL_RECYCLE.getValue()) || type.equals(SrTypeMarkEnum.PHYSICAL_RECYCLE.getValue())){
			bmSr = super.saveVirtualRecycle((JSONObject)jsonData);
		}else if(type.equals(SrTypeMarkEnum.SERVICE_AUTO.getValue())) {
			bmSr = super.saveServiceAuto((JSONObject)jsonData);
		}else if(type.equals(SrTypeMarkEnum.PRICE_EXAMINE_APPROVE.getValue())) {
			bmSr = saveExamineAndApprove((JSONObject)jsonData);
		}
		return bmSr;
	}

	@Override
	public ReturnMeta startServiceAuto(String type, ServiceRequestParam serviceRequestParam)
			throws Exception {
		if(type.equals("ADD_NETWORKCARD")) {
			return super.addNetworkCard(serviceRequestParam);
		}else if(type.equals("DELETE_NETWORKCARD")) {
			return super.deleteNetworkCard(serviceRequestParam);
		}
		return null;
	}
}
