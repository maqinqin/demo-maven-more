package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;

public interface IRmVmManageServerService extends IService {
	/**
	 * @Title: getVmTypeNameInfo
	 * @Description: 根据平台类型，获取虚拟机类型。
	 * @param platformId
	 *            平台类型ID
	 * @return int 返回类型
	 * @throws
	 */
	public List<RmVirtualTypePo> getVmTypeNameInfo(String platformId);

	/**
	 * @Title: getPlatformInfo
	 * @Description: 获取平台类型信息
	 * @return List<RmPlatformPo> 返回类型
	 * @throws
	 */
	public List<RmPlatformPo> getPlatformInfo();

	/**
	 * @Title: getvmManageServerList
	 * @Description: 查询虚机管理服务器信息
	 * @param paginationParam
	 * @return Pagination<RmVmManageServerPo> 返回类型
	 * @throws
	 */
	public Pagination<RmVmManageServerPo> getvmManageServerList(PaginationParam paginationParam);
	
	/**
	 * @Title: getvmManageServerInfo
	 * @Description: 获取指定虚机管理服务器信息
	 * @param serverId
	 * @return RmVmManageServerPo 返回类型
	 * @throws
	 */
	public RmVmManageServerPo getvmManageServerInfo(String serverId);

	/**
	 * @Title: insertvmManageServer
	 * @Description: 添加或更新虚机管理服务器信息
	 * @param rmVmManageServerPo
	 * @return String 返回类型
	 * @throws
	 */
	//public String insertvmManageServer(RmVmManageServerPo rmVmManageServerPo);

	/**
	 * @Title: deleteVMServerList
	 * @Description: 批量删除虚机管理服务器
	 * @param ids
	 * @return List<String> 返回类型
	 * @throws
	 */
	public List<String> deleteVMServerList(String id);

	/**
	 * @Title: checkServerName
	 * @Description: 检查是否重名
	 * @param rmVmManageServerPo
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean checkServerName(RmVmManageServerPo rmVmManageServerPo);

	/**
	 * @Title: checkServerIp
	 * @Description: 检查Ip地址是否已经被使用
	 * @param rmVmManageServerPo
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean checkServerIp(RmVmManageServerPo rmVmManageServerPo);

	/**
	 * @Title: updatevmManageServer
	 * @Description:更新虚机管理服务器信息
	 * @param rmVmManageServerPo
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public String updatevmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	/**
	 * @Title: insertOfvmManageServer
	 * @Description: 添加虚机管理服务器信息
	 * @param rmVmManageServerPo
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public String insertOfvmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException;

	public int selectVMServer(RmVmManageServerPo rmVmManageServerPo)throws RollbackableBizException;
	
	/**
	 * 根据ID获取虚机管理服务器ID
	 * @param vmMsId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public RmVmManageServerPo getRmVmMsIpById(String vmMsId) throws RollbackableBizException;
	/**
	 * 根据ip地址获取虚机管理服务器ID
	 * @param vmMsId
	 * @return
	 */
	public List<RmVmManageServerPo> getRmVmMsIpByVmMsIp(String vmMsHostIp);
}
