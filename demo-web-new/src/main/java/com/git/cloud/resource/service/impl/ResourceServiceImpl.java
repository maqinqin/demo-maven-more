/**
 * @Title:ResourceServiceImpl.java
 * @Package:com.git.cloud.resource.service.impl
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:11:40
 * @version V1.0
 */
package com.git.cloud.resource.service.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resource.dao.IResouceDao;
import com.git.cloud.resource.model.po.MachineInfoPo;
import com.git.cloud.resource.model.po.VmInfoPo;
import com.git.cloud.resource.service.IResourceService;

/**
 * @ClassName:ResourceServiceImpl
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:11:40
 *
 *
 */
public class ResourceServiceImpl implements IResourceService {
	private IResouceDao resouceDao;
	public IResouceDao getResouceDao() {
		return resouceDao;
	}

	public void setResouceDao(IResouceDao resouceDao) {
		this.resouceDao = resouceDao;
	}

	/* (non-Javadoc)
	 * <p>Title:queryMachineInfoPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resource.service.ResourceService#queryMachineInfoPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<MachineInfoPo> queryMachineInfoPagination(PaginationParam paginationParam) {
		return resouceDao.queryMachineInfoPagination(paginationParam);
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmInfoPagination</p>
	 * <p>Description:</p>
	 * @param paginationParam
	 * @return
	 * @see com.git.cloud.resource.service.ResourceService#queryVmInfoPagination(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<VmInfoPo> queryVmInfoPagination(PaginationParam paginationParam) {
		return resouceDao.queryVmInfoPagination(paginationParam);
	}
	
	/* (non-Javadoc)
	 * <p>Title:queryCluster</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.service.IResourceService#queryCluster(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryCluster(String id) throws RollbackableBizException {
		return resouceDao.queryCluster(id);
	}

	/* (non-Javadoc)
	 * <p>Title:queryPool</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.service.IResourceService#queryPool(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryPool(String id) throws RollbackableBizException {
		return resouceDao.queryPool(id);
	}

	/* (non-Javadoc)
	 * <p>Title:queryDeployUnit</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.service.IResourceService#queryDeployUnit(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryDeployUnit(String id) throws RollbackableBizException {
		return resouceDao.queryDeployUnit(id);
	}

	/* (non-Javadoc)
	 * <p>Title:queryMachineInfo</p>
	 * <p>Description:</p>
	 * @param hostId
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.service.IResourceService#queryMachineInfo(java.lang.String)
	 */
	@Override
	public MachineInfoPo queryMachineInfo(String hostId) throws RollbackableBizException {
		return resouceDao.queryMachineInfo(hostId);
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmInfo</p>
	 * <p>Description:</p>
	 * @param vmId
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.resource.service.IResourceService#queryVmInfo(java.lang.String)
	 */
	@Override
	public VmInfoPo queryVmInfo(String vmId) throws RollbackableBizException {
		return resouceDao.queryVmInfo(vmId);
	}

}
