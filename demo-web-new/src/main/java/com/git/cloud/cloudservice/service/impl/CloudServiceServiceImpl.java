package com.git.cloud.cloudservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.cloudservice.service.ICloudServiceService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.model.po.SysUserPo;

public class CloudServiceServiceImpl implements ICloudServiceService {

	private ICloudServiceDao cloudServiceDao;

	@Override
	public CloudServicePo save(CloudServicePo cloudService) throws RollbackableBizException {
		cloudService.setServiceId(UUIDGenerator.getUUID());
		cloudService.setIsActive(IsActiveEnum.YES.getValue());
		cloudService.setCreateDateTime(new Date());
		cloudService = cloudServiceDao.save(cloudService);
		return cloudService;
	}

	@Override
	public void update(CloudServicePo cloudService) throws RollbackableBizException {
		cloudService.setIsActive(IsActiveEnum.YES.getValue());
		cloudService.setUpdateDateTime(new Date());
		cloudServiceDao.update(cloudService);
	}

	@Override
	public CloudServicePo findById(String id) throws RollbackableBizException {
		return cloudServiceDao.findById(id);
	}

	@Override
	public Pagination<CloudServicePo> queryPagination(PaginationParam paginationParam) {
		//在云服务定义中，显示所有的云服务
		//SysUserPo user = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		paginationParam.getParams().put("userId", "");
		return cloudServiceDao.queryPagination(paginationParam);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		cloudServiceDao.deleteById(ids);
	}

	public ICloudServiceDao getCloudServiceDao() {
		return cloudServiceDao;
	}

	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}

	/* (non-Javadoc)
	 * <p>Title:findAllById</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.ICloudServiceService#findAllById(java.lang.String)
	 */
	@Override
	public CloudServiceVo findAllById(String id) throws RollbackableBizException {
		return cloudServiceDao.findAllById(id);
	}

	@Override
	public boolean checkCloudServices(CloudServicePo cloudServicePo)
			throws RollbackableBizException {
		int count = cloudServiceDao.findCloudServicePoCount(cloudServicePo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmType</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.ICloudServiceService#queryVmType(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryVmType(String id) throws RollbackableBizException {
		return cloudServiceDao.queryVmType(id);
	}

	/* (non-Javadoc)
	 * <p>Title:start</p>
	 * <p>Description:</p>
	 * @param cloudService
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.ICloudServiceService#start(com.git.cloud.cloudservice.model.po.CloudServicePo)
	 */
	@Override
	public void start(CloudServicePo cloudService) throws RollbackableBizException {
		cloudServiceDao.update(cloudService);
	}

	/* (non-Javadoc)
	 * <p>Title:stop</p>
	 * <p>Description:</p>
	 * @param cloudService
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.ICloudServiceService#stop(com.git.cloud.cloudservice.model.po.CloudServicePo)
	 */
	@Override
	public void stop(CloudServicePo cloudService) throws RollbackableBizException {
		cloudServiceDao.update(cloudService);
	}

	@Override
	public boolean checkCloudTypeCode(CloudServicePo cloudServicePo) throws RollbackableBizException {
		if(cloudServicePo.getCloudTypeCode()==null || "".equals(cloudServicePo.getCloudTypeCode())){
			return true;
		}
		String count = cloudServiceDao.findCloudServicePoCountByCode(cloudServicePo);
		if(count==null || "".equals(count)){
			return true;
		}else if(count.equals(cloudServicePo.getServiceId())){
			return true;
		}else {
			return false;
		}
	}

}
