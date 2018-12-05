package com.git.cloud.cloudservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.po.ImageSynchRef;
import com.git.cloud.cloudservice.model.vo.CloudImageVo;
import com.git.cloud.cloudservice.model.vo.ImageSynchInstanceVo;
import com.git.cloud.cloudservice.service.ImageService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.workflow.service.IRequestService;
import com.gitcloud.tankflow.model.po.BpmModelPo;
import com.gitcloud.tankflow.util.Constants;



/**
 * @ClassName:ImageServiceImpl
 * @Description:TODO
 * @author 聂海洋
 * @date 2014-12-17 下午2:38:49
 *
 *
 */
public class ImageServiceImpl implements ImageService {

	private ImageDao imageDao;
	
	private ICmPasswordDAO cmPasswordDAO;
	
	private IRequestService requestService;
	
	public void setRequestService(IRequestService requestService) {
		this.requestService = requestService;
	}


	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}


	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}


	@Override
	public void insertImage(CloudImage cloudImage) throws Exception {
		CmPasswordPo cmPasswordPo = new CmPasswordPo(UUIDGenerator.getUUID());
		cmPasswordPo.setResourceId(cloudImage.getImageId());
		cmPasswordPo.setUserName(cloudImage.getManager());
		cmPasswordPo.setPassword(cloudImage.getPassword());
		cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
		cmPasswordDAO.insertCmPassword(cmPasswordPo);
		imageDao.insertImage(cloudImage);
		
	}

	@Override
	public void updateImage(CloudImage cloudImage) throws Exception {
		CmPasswordPo cmPasswordPo =cmPasswordDAO.findCmPasswordByResourceId(cloudImage.getImageId());
		cmPasswordPo.setUserName(cloudImage.getManager());
		cmPasswordPo.setPassword(cloudImage.getPassword());
		cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
		cmPasswordDAO.updateCmPassword(cmPasswordPo);
		imageDao.updateImage(cloudImage);
		
	}

	@Override
	public void deleteImage(String[] ids) throws Exception {
		
		imageDao.deleteImage(ids);
		
	}

	@Override
	public CloudImage findImageById(String cloudImage) throws Exception {
		return imageDao.findImage(cloudImage);
	}
	
	
	@Override
	public Pagination<CloudImage> findList(PaginationParam pagination)
			throws Exception {
		return imageDao.findList(pagination);
	}
}
