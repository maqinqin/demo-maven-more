package com.git.cloud.iaas.openstack.service;

import com.git.cloud.iaas.openstack.model.ImageRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

public interface OpenstackImageService {
	
	 String createImage(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception;
	
	 String createImagePhy(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception;
	
	 String createImageFus(OpenstackIdentityModel model,ImageRestModel imageModel) throws Exception;
	
	 String getImageList(OpenstackIdentityModel model) throws Exception;
	
	 String getImageList(OpenstackIdentityModel model,String next) throws Exception;
	
	 String getImage(OpenstackIdentityModel model,String imageId) throws Exception;
	
	 void deleteImage(OpenstackIdentityModel model,String imageId) throws Exception;
}
