package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;

public interface ICmHostDatastoreRefDAO extends ICommonDAO{
	
	/**
	 * 根据服务器Id获取默认的DatastoreId
	 * @Title: findDefaultDatastoreIdByHostId
	 * @Description: TODO
	 * @field: @param hostId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return String
	 * @throws
	 */
	public String findDefaultDatastoreIdByHostId(String hostId) throws RollbackableBizException;

	public List<CmHostDatastoreRefPo> getAllDatastoreRef(String datastoreId) throws Exception;
	
	/**
	 * 根据物理机列表查询所有的datastore信息，并按照大小进行倒序排序
	 * @param hostIdList
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<CmHostDatastorePo> findDatastoreIdByHosts(List<String> hostIdList) throws RollbackableBizException;
	CmDatastorePo findDataStoreById(String datastoreId) throws Exception;
}
