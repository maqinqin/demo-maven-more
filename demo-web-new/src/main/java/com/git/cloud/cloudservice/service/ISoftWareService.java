package com.git.cloud.cloudservice.service;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;
import com.git.cloud.cloudservice.model.vo.CloudSoftwareVersVo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ISoftWareService{

	/**
	 * 镜像管理点击新增时查询所有的软件信息
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
	
	/**
	 * 根据软件信息ID查询所有版本，镜像模块使用
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
	Pagination<CloudSoftwareVersVo> findSoftsByImageId(PaginationParam pagination) throws RollbackableBizException;
	
	
	public boolean findCloudSoftwaresByParam(CloudSoftware cloudSoftware) throws RollbackableBizException;
	
	public boolean findCloudSoftwareVersByParam(CloudSoftwareVer cloudSoftwareVer) throws RollbackableBizException;

}
