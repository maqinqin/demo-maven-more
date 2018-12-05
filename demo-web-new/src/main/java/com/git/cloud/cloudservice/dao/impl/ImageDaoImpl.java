package com.git.cloud.cloudservice.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
@Service
public class ImageDaoImpl extends CommonDAOImpl implements ImageDao {
	@Override
	public List<CloudImage> findList(){
		@SuppressWarnings("unchecked")
		List<CloudImage> list = this.getSqlMapClientTemplate().queryForList("findCloudImageList");
		return list;
	}

	@Override
	public void deleteImage(String[] ids) throws Exception {
		for(String id : ids){
			super.deleteForIsActive("deleteImage",id);
		}
	}

	@Override
	public CloudImage findImage(String cloudImage) throws Exception {
		CloudImage image = findObjectByID("selectImage", cloudImage);
		return image;
	}

	@Override
	public Pagination<CloudImage> findList(PaginationParam pagination)
			throws Exception {
		return pageQuery("selectAllImagesCount", "findImageList", pagination);
	}

	@Override
	public void insertImage(CloudImage cloudImage) throws Exception {
		save("insertImage", cloudImage);
		
	}

	@Override
	public void updateImage(CloudImage cloudImage) throws Exception {
		update("updateImage", cloudImage);
	}


	@Override
	public Integer findCloudImages(CloudImage cloudImage)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudImagesByparam", cloudImage);
	}
}
