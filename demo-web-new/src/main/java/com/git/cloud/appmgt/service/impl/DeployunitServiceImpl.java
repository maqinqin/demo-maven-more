package com.git.cloud.appmgt.service.impl;

import java.util.List;

import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.common.exception.RollbackableBizException;

/**
 * 服务器角色维护SERVICE层实现类
 * @author dongjinquan
 * @date 2014-9-17 下午4:23:49
 * @version v1.0
 *
 */
public class DeployunitServiceImpl implements IDeployunitService {

	private IDeployunitDao deployunitDaoImpl;
	public void setDeployunitDaoImpl(IDeployunitDao deployunitDaoImpl) {
		this.deployunitDaoImpl = deployunitDaoImpl;
	}

	
	
	@Override
	public DeployUnitVo getDeployUnitById(String duId) {
		DeployUnitVo vo=deployunitDaoImpl.getDeployUnitById(duId);
		return vo;
	}


	public String getServiceIdByDuId(String duID) throws RollbackableBizException{
		return deployunitDaoImpl.getServiceIdByDuId(duID);
	}
	
	public List<DeployUnitVo> deployUnitCheckByServiceId(String serviceId){
		return deployunitDaoImpl.deployUnitCheckByServiceId(serviceId);
	}

}
