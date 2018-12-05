/**
 * @Title:ResourceDaoImpl.java
 * @Package:com.git.cloud.resource.dao.impl
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:24:57
 * @version V1.0
 */
package com.git.cloud.resource.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resource.dao.IResouceDao;
import com.git.cloud.resource.model.po.MachineInfoPo;
import com.git.cloud.resource.model.po.VmInfoPo;

/**
 * @ClassName:ResourceDaoImpl
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:24:57
 *
 *
 */
public class ResourceDaoImpl extends CommonDAOImpl implements IResouceDao {

	/* (non-Javadoc)
	 * <p>Title:queryMachineInfoPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryMachineInfoPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<MachineInfoPo> queryMachineInfoPagination(PaginationParam paginationParam) {
		return pageQuery("queryMachineInfoPoCount", "queryMachineInfoPoList", paginationParam);
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmInfoPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryVmInfoPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<VmInfoPo> queryVmInfoPagination(PaginationParam paginationParam) {
		return pageQuery("queryVmInfoPoCount", "queryVmInfoPoList", paginationParam);
	}

	/* (non-Javadoc)
	 * <p>Title:queryCluster</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryCluster(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryCluster(String id) throws RollbackableBizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("poolId", id);
		return super.findForList("queryCluster", map);
	}

	/* (non-Javadoc)
	 * <p>Title:queryPool</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryPool(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryPool(String id) throws RollbackableBizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataCenterId", id);
		return super.findForList("queryPool", map);
	}

	/* (non-Javadoc)
	 * <p>Title:queryDeployUnit</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.dao.IResouceDao#queryDeployUnit(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryDeployUnit(String id) throws RollbackableBizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", id);
		return super.findForList("queryDeployUnit", map);
	}

	/* (non-Javadoc)
	 * <p>Title:queryMachineInfo</p>
	 * <p>Description:</p>
	 * @param hostId
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryMachineInfo(java.lang.String)
	 */
	@Override
	public MachineInfoPo queryMachineInfo(String hostId) throws RollbackableBizException {
		return super.findObjectByID("queryMachineInfo", hostId);
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmInfo</p>
	 * <p>Description:</p>
	 * @param vmId
	 * @return
	 * @see com.git.cloud.resource.dao.IResouceDao#queryVmInfo(java.lang.String)
	 */
	@Override
	public VmInfoPo queryVmInfo(String vmId) throws RollbackableBizException {
		return super.findObjectByID("queryVmInfo", vmId);
	}

}
