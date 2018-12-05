package com.git.cloud.cloudservice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.dao.ICloudServiceFlowRefDao;
import com.git.cloud.cloudservice.model.po.CloudFile;
import com.git.cloud.cloudservice.model.po.CloudFileTemplate;
import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

import net.sf.json.JSONObject;

/**
 * @Description
 */
public class CloudServiceFlowRefDaoImpl extends CommonDAOImpl implements ICloudServiceFlowRefDao {

	@Override
	public CloudServiceFlowRefPo save(CloudServiceFlowRefPo cloudService) throws RollbackableBizException {
		save("CloudServiceFlowRef.insert", cloudService);
		return cloudService;
	}

	@Override
	public void update(CloudServiceFlowRefPo cloudService) throws RollbackableBizException {
		update("CloudServiceFlowRef.update", cloudService);
	}

	@Override
	public CloudServiceFlowRefPo findById(String id) throws RollbackableBizException {

		List<CloudServiceFlowRefPo> list = findByID("CloudServiceFlowRef.load", id);
		if (list != null) {
			for (CloudServiceFlowRefPo p : list)
				return p;
		}
		return null;
	}

	@Override
	public Pagination<CloudServiceFlowRefPo> queryPagination(PaginationParam pagination) {
		return pageQuery("CloudServiceFlowRef.count", "CloudServiceFlowRef.search", pagination);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		if (ids != null) {
			for (String id : ids)
				deleteForIsActive("CloudServiceFlowRef.delete", id);
		}
	}

	@Override
	public Integer findCloudServiceFlowRefPoCount(
			CloudServiceFlowRefPo cloudServiceFlowRefPo)
			throws RollbackableBizException {
		
		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudServiceFlowRefsByparam", cloudServiceFlowRefPo);
	}
	
	/**
	 * 文件导出时进行cloud_service_flow_ref表进行数拼接
	 */

	@Override
	public List<CloudServiceFlowRefPo> cloudLeading(String id) throws RollbackableBizException {
		List<CloudServiceFlowRefPo> list = findByID("CloudServiceFlowRef.file",id);
		if(list != null) {
			return list;
		}
		return null;
	}

	/**
	 * 文件导出时进行查询bpm_template表拼接
	 */
	@Override
	public CloudFileTemplate templateFile(String mdoelId) throws RollbackableBizException {

		List<CloudFileTemplate> cloudFileTemplate = null;
	
		cloudFileTemplate = findByID("BpmTemplateFile", mdoelId);
		if(cloudFileTemplate != null) {
			for(CloudFileTemplate tem : cloudFileTemplate) {
				return tem;
			}
		}
		return null;
	}

	/**
	 * 保存数据到bpm_template
	 */
	@Override
	public void savebpmTemplate(CloudFileTemplate cloudFileTemplate) throws RollbackableBizException {
		save("CloudTemplate.insert",cloudFileTemplate);
	}

	/**
	 * 根据ID查询bom_template表此条数据是否存在
	 */
	@Override
	public CloudFileTemplate selectTmeplateById(String id) throws RollbackableBizException {
		List<CloudFileTemplate> cloudFileTemplate = findByID("BpmTemplateFile.select",id);
		if(cloudFileTemplate != null) {
			for(CloudFileTemplate po : cloudFileTemplate) {
				return po;
			}
		}
		return null;
	}

	@Override
	public int selectBpmModelByFlowId(String flowId) throws RollbackableBizException {
		int flag = (int) getSqlMapClientTemplate().queryForObject("BpmTemplateFlowId.select",flowId);
		
		return flag;
	}

	@Override
	public List<String> selectTemplateByCreateDate() throws RollbackableBizException {
		List<String> list = getSqlMapClientTemplate().queryForList("BpmModelByCreate");
		return list;
	}
	
	

}
