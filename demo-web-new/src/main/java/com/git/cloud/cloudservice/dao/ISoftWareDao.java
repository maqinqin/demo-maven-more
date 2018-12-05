package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;

public interface ISoftWareDao extends ICommonDAO {

	/**
	 * 查询所有软件信息
	 * @return
	 * @throws Exception
	 */
	public List<CloudSoftware> showCloudSoftwareAll() throws Exception;

	
	/**
	 * 软件管理时查询所有的软件信息
	 * @return
	 * @throws Exception
	 */
	public Pagination<CloudSoftware> showSoftwareAll(PaginationParam pagination) throws Exception;
	
	

	
	/**
	 * 新增
	 * @param cloudSoftware
	 * @throws Exception
	 */
	public void insertCloudSoftware(CloudSoftware cloudSoftware) throws Exception;
	
	/**
	 * 修改
	 * @param cloudSoftware
	 * @throws Exception
	 */
	public void updateCloudSoftware(CloudSoftware cloudSoftware) throws Exception;
	
	/**
	 *修改时查询对象信息 
	 * @param cloudSoftware
	 * @return
	 * @throws Exception
	 */
	public CloudSoftware showSoftWareBysoftWareId(String cloudSoftware) throws Exception;
	
	/**
	 * 删除
	 * @param ids
	 * @throws Exception
	 */
	public void deleteCloudSoftware(String[] ids) throws Exception;
	

	
	public Integer findCloudSoftwaresByParam(CloudSoftware cloudSoftware) throws RollbackableBizException;

}
