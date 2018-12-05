package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.git.cloud.iaas.openstack.OpenstackInvokeTools;
import com.git.cloud.iaas.openstack.model.ImageRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public abstract class OpenstackImageServiceAbstractImpl extends OpenstackInvokeTools implements OpenstackImageService {
	
	private static final int RESPONSE_CODES_200 = 200;
	
	/**
	 * 创建镜像(KVM)
	 * @param imageModel
	 * @return
	 * @throws Exception
	 */
	public String createImage(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_IMAGE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IMAGE_NAME", imageModel.getImageName());
		
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	public String createImagePhy(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_IMAGE_PYH+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IMAGE_NAME", imageModel.getImageName());
		
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	public String createImageFus(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_IMAGE_FUS+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IMAGE_NAME", imageModel.getImageName());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	/**
	 * 查询镜像列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getImageList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_IMAGE_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == RESPONSE_CODES_200) {
			return this.getExecuteResultData(rspData);
		}
		else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询镜像列表的下一页
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getImageList(OpenstackIdentityModel model,String next) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_IMAGE_LIST_NEXT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("NEXT", next);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NEXT", next);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == RESPONSE_CODES_200) {
			return this.getExecuteResultData(rspData);
		}
		else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询镜像
	 * @param imageId
	 * @return
	 * @throws Exception
	 */
	public String getImage(OpenstackIdentityModel model,String imageId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_IMAGE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("IMAGE_ID", imageId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IMAGE_ID", imageId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		result = this.getExecuteResultData(rspData);
		return result;
	}
	
	/**
	 * 删除镜像
	 * @param imageId
	 * @return
	 * @throws Exception
	 */
	public void deleteImage(OpenstackIdentityModel model,String imageId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_IMAGE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("IMAGE_ID", imageId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IMAGE_ID", imageId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		this.getExecuteResultData(rspData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

}
