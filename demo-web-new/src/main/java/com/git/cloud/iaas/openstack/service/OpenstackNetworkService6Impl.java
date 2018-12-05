package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.openstack.model.OpenstackManageEnum;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public class OpenstackNetworkService6Impl extends OpenstackNetworkServiceAbstractImpl implements OpenstackNetworkService {

	@Override
	public String getPhyNet(OpenstackIdentityModel model) throws Exception {
		return this.getAdminParamCache(OpenstackManageEnum.OPENSTACK_PHY_NET.getValue());
	}

	@Override
	public String getOverSplit(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_OVER_SPLIT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
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

}
