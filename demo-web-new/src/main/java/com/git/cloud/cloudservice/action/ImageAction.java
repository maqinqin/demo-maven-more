package com.git.cloud.cloudservice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.vo.CloudImageVo;
import com.git.cloud.cloudservice.service.ImageService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;

public class ImageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageService imageService;
	private List<CloudImage> images ;
	private CloudImage cloudImage;
	private CloudImageSoftWareRef imageSoftWareRef;
	private String vmMsId;
	private String imageIds;
	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	public String getImageIds() {
		return imageIds;
	}

	public void setImageIds(String imageIds) {
		this.imageIds = imageIds;
	}

	public CloudImageSoftWareRef getImageSoftWareRef() {
		return imageSoftWareRef;
	}

	public void setImageSoftWareRef(CloudImageSoftWareRef imageSoftWareRef) {
		this.imageSoftWareRef = imageSoftWareRef;
	}

	public CloudImage getCloudImage() {
		return cloudImage;
	}

	public void setCloudImage(CloudImage cloudImage) {
		this.cloudImage = cloudImage;
	}


	public List<CloudImage> getImages() {
		return images;
	}

	public void setImages(List<CloudImage> images) {
		this.images = images;
	}

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	public String showImageAll(){
		return SUCCESS;
	}
	
	/**
	 * 查询所有
	 * @throws Exception
	 */
	public void queryImageAll() throws Exception {
		Pagination<CloudImage> pagination = imageService.findList(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	
	/**
	 * 新增
	 * @throws Exception
	 */
	public void insertImage() throws Exception{
		cloudImage.setImageId(UUIDGenerator.getUUID());
		cloudImage.setIsActive(IsActiveEnum.YES.getValue());
		cloudImage.setPassword(PwdUtil.encryption(cloudImage.getPassword()));
		imageService.insertImage(cloudImage);
		Map<String,String> map = new HashMap<String, String>();
		map.put("imageId", cloudImage.getImageId());
		jsonOut(map);
		
	}
	
	
	/**
	 * 修改
	 * @throws Exception
	 */
	public void updateImage() throws Exception{
		String  pwd = imageService.findImageById(cloudImage.getImageId()).getPassword();
		if (!pwd.equals(cloudImage.getPassword())) {
			cloudImage.setPassword(PwdUtil.encryption(cloudImage.getPassword()));
		}
		imageService.updateImage(cloudImage);
		Map<String,String> map = new HashMap<String, String>();
		map.put("imageId", cloudImage.getImageId());
		jsonOut(map);
	}
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void deleteImage() throws Exception{
		imageService.deleteImage(cloudImage.getImageId().split(","));
	}
	
	/**
	 * 查询
	 * @throws Exception
	 */
	public void showImage() throws Exception{
		String imagId = cloudImage.getImageId();
		cloudImage = imageService.findImageById(imagId);
		this.jsonOut(cloudImage);
	}
	
}
