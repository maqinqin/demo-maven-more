package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudFile;
import com.git.cloud.cloudservice.model.po.CloudFileTemplate;
import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

/**
 * @Description 
 */
public interface ICloudServiceFlowRefDao extends ICommonDAO {
	public CloudServiceFlowRefPo save(CloudServiceFlowRefPo cloudService) throws RollbackableBizException;

	public void update(CloudServiceFlowRefPo cloudService) throws RollbackableBizException;

	public CloudServiceFlowRefPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceFlowRefPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;
	
	public Integer findCloudServiceFlowRefPoCount(CloudServiceFlowRefPo cloudServiceFlowRefPo) throws RollbackableBizException;

	public List<CloudServiceFlowRefPo> cloudLeading(String id) throws RollbackableBizException;
	
	public CloudFileTemplate templateFile(String id) throws RollbackableBizException;
	
	public void savebpmTemplate(CloudFileTemplate cloudFileTemplate) throws RollbackableBizException;
	
	public CloudFileTemplate selectTmeplateById(String id) throws RollbackableBizException;
	
	public int selectBpmModelByFlowId(String flowId) throws RollbackableBizException;
	
	public List<String> selectTemplateByCreateDate() throws RollbackableBizException;
}
