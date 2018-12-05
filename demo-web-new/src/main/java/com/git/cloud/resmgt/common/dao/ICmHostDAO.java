package com.git.cloud.resmgt.common.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;

public interface ICmHostDAO extends ICommonDAO{

	/**
	 * 更新CmHost的已用CPU和内存
	 * @Title: updateCmHostUsed
	 * @Description: TODO
	 * @field: @param cmHost
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateCmHostUsed(CmHostPo cmHost) throws RollbackableBizException;
	
	
	public void updateCmHostUsed() throws RollbackableBizException;
	/**
	 * 根据Id获取CmHost
	 * @Title: findCmHostById
	 * @Description: TODO
	 * @field: @param id
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CmHostPo
	 * @throws
	 */
	public CmHostPo findCmHostById(String id) throws RollbackableBizException;
	
	
	public List<?> findHostCpuCdpInfo(String hostId) throws RollbackableBizException;
	
	public String findHostIpById(String id) throws SQLException;
	
	public CmHostUsernamePasswordPo findHostUsernamePassword(String hostId) throws RollbackableBizException;

	public DataStoreVo findDiskInfoByHostId(String hostId) throws RollbackableBizException;
	
	/**
	* @Title: findDatastoreByHostIp  
	* @Description: 查询物理机挂载的所有存储信息
	* @param  hostIp
	* @return List<DataStoreVo>    返回类型  
	 */
	public List<DataStoreVo> findDatastoreByHostIp(String hostIp) throws RollbackableBizException;
	
	public List<HostVo> findHostInfoListByParams(Map<String,String> params);
	
	public List<DataStoreVo> findSanStorgeListByHostId(String hostId);
	
	public void deleteSanDataStoreList(List<DataStoreVo> dataStoreList);
	public void deleteHostDataStoreRefList(List<DataStoreVo> dataStoreList);

	public Pagination<CmHostVo> getHostList(PaginationParam paginationParam) throws RollbackableBizException ;


	public void updateUserNamePasswd(CmHostUsernamePasswordPo cupp) throws RollbackableBizException;

	/**
	 * 通过物理机ID，在cm_datastore表中，查找datastore的名称
	 * @param hostId,物理机ID
	 * @return
	 * @throws RollbackableBizException
	 */
	public String getGYRXHostDatastore(String hostId)throws SQLException;
	public int findExistUserNamePassWd(CmHostUsernamePasswordPo cupp)throws RollbackableBizException ;


	public void insertHostUserNamePasswd(CmHostUsernamePasswordPo cupp)throws RollbackableBizException ;

	public List<PolicyInfoVo> findHostByResPoolId(String resPoolId)throws RollbackableBizException;
	
	/**
	 * 查询物理机id，通过物理机ip
	 * @param hostId
	 * @return
	 */
	CmHostVo selectHostIdByIp(String hostIp) ;
}
