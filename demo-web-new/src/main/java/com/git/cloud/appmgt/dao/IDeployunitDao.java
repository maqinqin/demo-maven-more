package com.git.cloud.appmgt.dao;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.appmgt.model.po.DeployUnitPo;
import java.util.List;

import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwSecureTierPo;


/**
 * 服务器角色DAO接口类
 * @author liangshuang
 * @date 2014-12-17 上午11:17:05
 * @version v1.0
 *
 */
public interface IDeployunitDao extends ICommonDAO{
	/**
	 * 根据ID获取服务器角色信息
	 * @param nodeId
	 * @return
	 */
	DeployUnitVo getDeployUnitById(String duId);
	
	String getServiceIdByDuId(String duID)throws RollbackableBizException;
	/**
	 * 根据云服务ID，检查是否有服务器角色占用该云服务
	 * @param serviceId
	 * @return
	 */
	List<DeployUnitVo> deployUnitCheckByServiceId(String serviceId);
	
	//服务器角色下存在虚拟机，返回true,否则相反
	boolean includeVm(String duId)throws RollbackableBizException;
	
	void updateDeployUnitServiceId(DeployUnitPo deploy)throws RollbackableBizException;
	/**
	 * 修改服务器角色
	 * @param deploy
	 * @return
	 */
	void updateDeployunit(DeployUnitPo deploy) throws RollbackableBizException;
	
	<T extends DeployUnitPo> List<T> findListByFields(String method, HashMap<String, String> params);
	
}
