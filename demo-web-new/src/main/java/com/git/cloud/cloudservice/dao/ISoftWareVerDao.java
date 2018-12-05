package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;
import com.git.cloud.cloudservice.model.vo.CloudSoftwareVersVo;

public interface ISoftWareVerDao extends ICommonDAO{

	/**
	 * 根据软件信息ID查询所有版本信息
	 * @param softWareId
	 * @return
	 * @throws Exception
	 */
	public List<CloudSoftwareVer> showSoftwareVerAll(String softWareId) throws Exception;
	
	
	
	/**
	 * 查询所有的软件版本信息
	 * @return
	 * @throws Exception
	 */
	public Pagination<CloudSoftwareVer> findSoftwareVerAllBySoftId(PaginationParam pagination) throws Exception;
	
	/**
	 * 软件版本新增
	 * @param cloudSoftwareVer
	 * @throws Exception
	 */
	public void insertSoftWareVer(CloudSoftwareVer cloudSoftwareVer) throws Exception;
	
	/**
	 * 修改时查询软件版本对象信息
	 * @param cloudSoftwareVer
	 * @return
	 * @throws Exception
	 */
	public CloudSoftwareVer findSoftWareVerByVerId(String cloudSoftwareVer)throws Exception;
	
	/**
	 * 更新软件版本信息
	 * @param cloudSoftwareVer
	 * @throws Exception
	 */
	public void updateSoftWareVer(CloudSoftwareVer cloudSoftwareVer) throws Exception;
	
	/**
	 * 删除软件版本信息
	 * @param ids
	 * @throws Exception
	 */
	public void deleteSoftWareVer(String[] ids) throws Exception;
	/**
	 * 
	 * @Title: findSoftsByImageId
	 * @Description: 根据镜像id查软件
	 * @field: @param imageId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<CloudSoftwareVersVo>
	 * @throws
	 */
	public  Pagination<CloudSoftwareVersVo> findSoftsByImageId(PaginationParam pagination) throws RollbackableBizException;
	
	public Integer findCloudSoftwareVers(CloudSoftwareVer cloudSoftwareVer) throws RollbackableBizException ;
}
