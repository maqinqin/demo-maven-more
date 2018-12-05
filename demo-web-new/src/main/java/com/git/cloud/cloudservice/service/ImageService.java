package com.git.cloud.cloudservice.service;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.vo.CloudImageVo;
import com.git.cloud.cloudservice.model.vo.ImageSynchInstanceVo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ImageService{
	
	
	

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
	public CloudImage findImageById(String cloudImage) throws Exception;
	
}
