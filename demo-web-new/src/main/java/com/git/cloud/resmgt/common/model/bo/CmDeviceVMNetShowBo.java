package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmDeviceHostVMBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 存储页面展示用的主机网络信息。
 * @author lizhizhong
 * @date 2014-9-15 下午5:08:15
 * @version V1.0
 */
public class CmDeviceVMNetShowBo extends BaseBO {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** id */
	private String id;

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

	@Override
	public void setBizId(String bizId) {
		this.id = bizId;
	}
}