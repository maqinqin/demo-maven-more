package com.git.cloud.cloudservice.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.rest.model.CloudServiceOsRef;

public interface ICloudServiceDao extends ICommonDAO {

	public CloudServicePo save(CloudServicePo cloudService) throws RollbackableBizException;

	public void update(CloudServicePo cloudService) throws RollbackableBizException;
	
	public void updateCloudServiceRRinfo(CloudServicePo cloudService) throws RollbackableBizException;

	public CloudServicePo findById(String id) throws RollbackableBizException;
	
	public CloudServiceVo findAllById(String id) throws RollbackableBizException;

	public Pagination<CloudServicePo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;
	
	public Integer findCloudServicePoCount(CloudServicePo cloudService) throws RollbackableBizException;
	
	public String findCloudServicePoCountByCode(CloudServicePo cloudService) throws RollbackableBizException;
	
	public List<Map<String, Object>> queryVmType(String id) throws RollbackableBizException;
	
	public CloudServiceOsRef selectCloudServiceOsRef(Map<String,String> map)throws RollbackableBizException;
	
	public List<CmPasswordPo> cloudFilePassword(String serviceId) throws RollbackableBizException;
	
	public int savecmPassword(CmPasswordPo cmPasswordPo) throws RollbackableBizException;
	
	public CmPasswordPo selectIdBypassword(String id) throws RollbackableBizException;
}
