/**
 * @Title:ResourceService.java
 * @Package:com.git.cloud.resource.service
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:05:52
 * @version V1.0
 */
package com.git.cloud.resource.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resource.model.po.MachineInfoPo;
import com.git.cloud.resource.model.po.VmInfoPo;

/**
 * @ClassName:ResourceService
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:05:52
 *
 *
 */
public interface IResourceService {
	/**
	 * 
	 * @Title: queryMachineInfoPagination
	 * @Description: 查询物理机信息
	 * @field: @param paginationParam
	 * @field: @return
	 * @return Pagination<MachineInfoPo>
	 * @throws
	 */
	public Pagination<MachineInfoPo> queryMachineInfoPagination(PaginationParam paginationParam); 
	/**
	 * 
	 * @Title: queryVmInfoPagination
	 * @Description: 查询虚拟机信息
	 * @field: @param paginationParam
	 * @field: @return
	 * @return Pagination<VmInfoPo>
	 * @throws
	 */
	public Pagination<VmInfoPo> queryVmInfoPagination(PaginationParam paginationParam);
	public List<Map<String, Object>> queryPool(String id) throws RollbackableBizException;
	public List<Map<String, Object>> queryCluster(String id) throws RollbackableBizException;
	public List<Map<String, Object>> queryDeployUnit(String id) throws RollbackableBizException;
	
	public MachineInfoPo queryMachineInfo(String hostId) throws RollbackableBizException;
	public VmInfoPo queryVmInfo(String vmId) throws RollbackableBizException;
	
}
