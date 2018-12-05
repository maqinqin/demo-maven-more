package com.git.cloud.iaas.openstack.model;

public class ImageRestModel {
	
	private String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ImageModel [imageName=" + imageName + "]";
	}

	


}
