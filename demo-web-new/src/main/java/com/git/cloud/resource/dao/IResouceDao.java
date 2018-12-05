/**
 * @Title:IResouceDao.java
 * @Package:com.git.cloud.resource.dao
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:16:24
 * @version V1.0
 */
package com.git.cloud.resource.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resource.model.po.MachineInfoPo;
import com.git.cloud.resource.model.po.VmInfoPo;

/**
 * @ClassName:IResouceDao
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:16:24
 *
 *
 */
public interface IResouceDao extends ICommonDAO {
	public Pagination<MachineInfoPo> queryMachineInfoPagination(PaginationParam paginationParam);
	public Pagination<VmInfoPo> queryVmInfoPagination(PaginationParam paginationParam);
	public List<Map<String, Object>> queryPool(String id) throws RollbackableBizException;
	public List<Map<String, Object>> queryCluster(String id) throws RollbackableBizException;
	public List<Map<String, Object>> queryDeployUnit(String id) throws RollbackableBizException;
	public MachineInfoPo queryMachineInfo(String hostId) throws RollbackableBizException;
	public VmInfoPo queryVmInfo(String vmId) throws RollbackableBizException;
}
