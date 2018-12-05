package com.git.cloud.cloudservice.service;

import com.git.cloud.cloudservice.model.po.CloudFile;
import com.git.cloud.common.exception.RollbackableBizException;

import net.sf.json.JSONObject;

/**
 * 进行数据的导入导出
 * @author gaosida
 *
 */
public interface ICloudFileService {
	
	public CloudFile cloudleading(String id) throws Exception;
	
	public void savecloudWrite() throws Exception;

}
