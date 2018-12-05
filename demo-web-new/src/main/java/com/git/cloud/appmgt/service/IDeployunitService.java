package com.git.cloud.appmgt.service;

import java.util.List;

import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwSecureTierPo;

/**
 * 服务器角色维护SERVICE层接口类
 * @author dongjinquan
 * @date 2014-9-17 下午4:16:14
 * @version v1.0
 *
 */
public interface IDeployunitService {
	
	/**
	 * 根据ID获取服务器角色信息
	 * @param duId
	 * @return
	 */
	DeployUnitVo getDeployUnitById(String duId);
	/**
	 * 根据云服务ID，检查是否有服务器角色占用该云服务
	 * @param serviceId
	 * @return
	 */
	List<DeployUnitVo> deployUnitCheckByServiceId(String serviceId);
	
	String getServiceIdByDuId(String duID) throws RollbackableBizException;
}
