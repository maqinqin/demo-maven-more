package com.git.cloud.cloudservice.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceService {
	public CloudServicePo save(CloudServicePo cloudService) throws RollbackableBizException;

	public void update(CloudServicePo cloudService) throws RollbackableBizException;
	
	public void start(CloudServicePo cloudService) throws RollbackableBizException;
	
	public void stop(CloudServicePo cloudService) throws RollbackableBizException;
	
	/**
	 * 查询云服务定义信息，修改时加载用
	 */
	public CloudServicePo findById(String id) throws RollbackableBizException;
	
	/**
	 * 查询云服务定义信息，查看详细用
	 * @Title: findAllById
	 * @Description: TODO
	 * @field: @param id
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CloudServicePo
	 * @throws
	 */
	public CloudServiceVo findAllById(String id) throws RollbackableBizException;
	/**
	 * 查询云服务定义信息，分页用
	 * @Title: queryPagination
	 * @Description: TODO
	 * @field: @param pagination
	 * @field: @return
	 * @return Pagination<CloudServicePo>
	 * @throws
	 */
	public Pagination<CloudServicePo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;

	public boolean checkCloudServices(CloudServicePo cloudServicePo) throws RollbackableBizException;
	public boolean checkCloudTypeCode(CloudServicePo cloudServicePo) throws RollbackableBizException;
	
	public List<Map<String, Object>> queryVmType(String id) throws RollbackableBizException;
}
