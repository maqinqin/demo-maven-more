package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.gitcloud.tankflow.model.po.BpmModelPo;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.po.ImageSynchRef;
import com.git.cloud.cloudservice.model.vo.CloudImageVo;
import com.git.cloud.cloudservice.model.vo.ImageSynchInstanceVo;

public interface ImageDao extends ICommonDAO {

	
	/**
	 * 分页查询所有镜像信息
	 * @return
	 * @throws Exception
	 */
	public Pagination<CloudImage> findList(PaginationParam pagination) throws Exception;
	
	/**
	 * 新增
	 * @param cloudImage
	 * @throws Exception
	 */
	public void insertImage(CloudImage cloudImage) throws Exception;
	
	
	/**
	 * 修改
	 * @param cloudImage
	 * @throws Exception
	 */
	public void updateImage(CloudImage cloudImage) throws Exception;
	
	/**
	 * 删除
	 * @param cloudImage
	 * @throws Exception
	 */
	public void deleteImage(String[] ids) throws Exception;
	
	/**
	 * 根据镜像信息ID查询
	 * @param cloudImage
	 * @throws Exception
	 */
	public CloudImage findImage(String cloudImage) throws Exception;
	
	public Integer findCloudImages(CloudImage cloudImage) throws RollbackableBizException ;
	
	public List<CloudImage> findList();

}
