/**
 * @Title:CloudSoftwareVersVo.java
 * @Package:com.git.cloud.cloudservice.model.vo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-16 下午5:18:42
 * @version V1.0
 */
package com.git.cloud.cloudservice.model.vo;

import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;

/**
 * @ClassName:CloudSoftwareVersVo
 * @Description:TODO
 * @author yangzhenhai
 * @date 2014-10-16 下午5:18:42
 *
 *
 */
public class CloudSoftwareVersVo  extends CloudSoftwareVer {

	private String softwareName;
	
	private String imageSoftwareId;

	/**
	 * @return the softwareName
	 */
	public String getSoftwareName() {
		return softwareName;
	}

	/**
	 * @param softwareName the softwareName to set
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * @return the imageSoftwareId
	 */
	public String getImageSoftwareId() {
		return imageSoftwareId;
	}

	/**
	 * @param imageSoftwareId the imageSoftwareId to set
	 */
	public void setImageSoftwareId(String imageSoftwareId) {
		this.imageSoftwareId = imageSoftwareId;
	}
	
	
}
