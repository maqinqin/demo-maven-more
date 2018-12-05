package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmHostVmInfoPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;

public interface IRmVmManageServerDAO extends ICommonDAO {
	/**
	 * @Title: getVmTypeNameInfo
	 * @Description: 获取虚机类型信息
	 * @param platformId
	 * @return
	 * @throws RollbackableBizException
	 *             List<RmVirtualTypePo> 返回类型
	 * @throws
	 */
	public List<RmVirtualTypePo> getVmTypeNameInfo(String platformId) throws RollbackableBizException;

	/**
	 * 根据Id查询
	 * @Title: getPlatformInfoById
	 * @Description: TODO
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return RmPlatformPo
	 * @throws
	 */
	public RmPlatformPo getPlatformInfoById(String platformId) throws RollbackableBizException;
	
	/**
	 * @Title: getPlatformInfo
	 * @Description: 获取平台类型信息
	 * @return
	 * @throws RollbackableBizException
	 *             List<RmPlatformPo> 返回类型
	 * @throws
	 */
	public List<RmPlatformPo> getPlatformInfo() throws RollbackableBizException;

	/**
	 * @Title: getvmManageServerList
	 * @Description:在数据库中分页查询虚机服务管理机
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 *             Pagination<RmVmManageServerPo> 返回类型
	 * @throws
	 */
	public Pagination<RmVmManageServerPo> getvmManageServerList(PaginationParam paginationParam) throws RollbackableBizException;
	public List<RmVmManageServerPo> getvmManageServerList() throws RollbackableBizException;
	/**
	 * @Title: getvmManageServerInfo
	 * @Description: 获取指定虚机管理服务器信息
	 * @param serverId
	 * @return
	 * @throws RollbackableBizException
	 *             RmVmManageServerPo 返回类型
	 * @throws
	 */
	public <T extends BaseBO> T getvmManageServerInfo(String serverId) throws RollbackableBizException;

	/**
	 * @Title: insertVmManageServer
	 * @Description: 添加虚机管理服务器信息到数据库
	 * @param rmVmManageServerPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int insertVmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: updateVmManageServer
	 * @Description: 修改虚机管理服务器信息
	 * @param rmVmManageServerPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int updateVmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: insertCmPasswordPo
	 * @Description: 将虚机管理服务器的用户名和密码进行保存
	 * @param cmPasswordPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int insertCmPasswordPo(CmPasswordPo cmPasswordPo) throws RollbackableBizException;

	/**
	 * @Title: updateCmPasswordPo
	 * @Description: 修改虚机管理服务器的用户名和密码
	 * @param cmPasswordPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int updateCmPasswordPo(CmPasswordPo cmPasswordPo) throws RollbackableBizException;

	/**
	 * @Title: selectVMServerCount
	 * @Description: 检查虚机管理服务器是否存在 RM_CDP表中
	 * @param Bizid
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectVMServerCount(String Bizid) throws RollbackableBizException;

	/**
	 * @Title: selectVMServerIp
	 * @Description: 查询虚机管理服务器的ip是否被使用
	 * @param rmVmManageServerPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectVMServerIp(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: selectVMServerName
	 * @Description: 查询虚机管理服务器的名称是否已经存在
	 * @param rmVmManageServerPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectVMServerName(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: selectVMServer
	 * @Description: 查询虚机管理服务器是否已经存在
	 * @param rmVmManageServerPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectVMServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: deleteVMServer
	 * @Description: 根据id删除虚机管理服务器
	 * @param ids
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int deleteVMServer(String ids) throws RollbackableBizException;

	/**
	 * 根据虚拟机Id获取管理机
	 * 
	 * @Title: findRmVmManageServerListByVmIdList
	 * @Description: TODO
	 * @field: @param vmIdList
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<RmVmManageServerPo>
	 * @throws
	 */
	public List<RmVmManageServerPo> findRmVmManageServerListByVmIdList(List<String> vmIdList) throws RollbackableBizException;

	/**
	 * 根据虚拟机Id获取管理机
	 * 
	 * @Title: findRmVmManageServerByVmId
	 * @Description: TODO
	 * @field: @param vmId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return RmVmManageServerPo
	 * @throws
	 */
	public RmVmManageServerPo findRmVmManageServerByVmId(String vmId) throws RollbackableBizException;

	public List<RmVmManageServerPo> findAllHmcServer();
	/**
	 * 查询所有物理机、虚拟机信息
	  *
	  * @throws RollbackableBizException    
	  * @return List<RmHostVmInfoPo>  
	 */
	public RmHostVmInfoPo findHostVmInfo(Map<String,String> pMap) throws RollbackableBizException;
	/**
	 * 查询所有Datastore信息
	  * @throws RollbackableBizException    
	  * @return List<CmDatastorePo>    
	 */
	public CmDatastorePo findDatastoreInfo(Map<String,String> pMap) throws RollbackableBizException;

	RmVmManageServerPo findRmVmManageServerById(String vmMsId)
			throws RollbackableBizException;

	public <T extends RmVmManageServerPo> List<T> findListByFieldsAndOrder(
			String method, HashMap<String, String> params) throws RollbackableBizException ;

	List<RmVmManageServerPo> selectOpenstackServerList() throws RollbackableBizException;

	String getVmMsIdByHostPoolId(String resPoolId);
}
