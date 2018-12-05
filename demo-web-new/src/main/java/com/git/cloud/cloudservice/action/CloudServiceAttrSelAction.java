package com.git.cloud.cloudservice.action;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.cloudservice.service.ICloudServiceAttrSelService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;

public class CloudServiceAttrSelAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1461015574729711914L;
	private static Logger logger = LoggerFactory.getLogger(CloudServiceAttrSelAction.class);
	
	private ICloudServiceAttrSelService cloudServiceAttrSelService;
	private CloudServiceAttrSelPo cloudServiceAttrSelPo;
	@SuppressWarnings("rawtypes")
	private Pagination pagination;
	private Map<String, String> pageParams;
	private Map<String, Object> result;

	public String index() {
		return SUCCESS;
	}

	public String search() {
		pagination = cloudServiceAttrSelService.queryPagination(this.getPaginationParam());
		return SUCCESS;
	}

	public String save() throws Exception {

		if (cloudServiceAttrSelPo != null) {
			if (cloudServiceAttrSelPo.getAttrSelId() == null || "".equals(cloudServiceAttrSelPo.getAttrSelId())) {
				cloudServiceAttrSelPo.setAttrSelId(UUIDGenerator.getUUID());
				cloudServiceAttrSelService.save(cloudServiceAttrSelPo);
			} else {
				cloudServiceAttrSelService.update(cloudServiceAttrSelPo);
			}
		}
		return SUCCESS;
	}
	/**
	 * 保存属性选项
	 * @Title: saveOption
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveOption() throws Exception{
		String[] ids = new String[]{cloudServiceAttrSelPo.getAttrId()};		
		//通过AttrId删除所有选项信息
		cloudServiceAttrSelService.deleteById(ids);
		
		String mapStr = cloudServiceAttrSelPo.getAttrSelId();
		JSONArray array = JSONArray.fromObject(mapStr);
		JSONObject obj;
		for (int i = 0; i < array.size(); i++) {
			obj = array.getJSONObject(i);
//			obj.getString("attrKey");
//			obj.getString("attrValue");
			cloudServiceAttrSelPo.setAttrKey(obj.getString("attrKey"));
			cloudServiceAttrSelPo.setAttrSelId(UUIDGenerator.getUUID());
			cloudServiceAttrSelPo.setAttrValue(obj.getString("attrValue"));
			
			cloudServiceAttrSelService.save(cloudServiceAttrSelPo);
		}
	}
	/**
	 * 通过AttrId删除该AttrId下所有选项信息
	 * @Title: deleteOption
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteOption() throws Exception{
		String[] ids = new String[]{cloudServiceAttrSelPo.getAttrId()};		
		cloudServiceAttrSelService.deleteById(ids);
	}
	
	public String stop() throws Exception {
		if (cloudServiceAttrSelPo != null) {
			if (cloudServiceAttrSelPo.getAttrSelId() != null && !"".equals(cloudServiceAttrSelPo.getAttrSelId())) {
				cloudServiceAttrSelPo = cloudServiceAttrSelService.findById(cloudServiceAttrSelPo.getAttrSelId());
				cloudServiceAttrSelPo.setIsActive("0");
				cloudServiceAttrSelService.update(cloudServiceAttrSelPo);
			}
		}
		return SUCCESS;
	}

	public String delete() throws Exception {
		if (cloudServiceAttrSelPo != null) {
			if (cloudServiceAttrSelPo.getAttrSelId() != null && !"".equals(cloudServiceAttrSelPo.getAttrSelId())) {
				String[] ids = new String[] { cloudServiceAttrSelPo.getAttrSelId() };
				cloudServiceAttrSelService.deleteById(ids);
			}
		}
		return SUCCESS;
	}

	public String load() throws Exception {
		cloudServiceAttrSelPo = cloudServiceAttrSelService.findById(cloudServiceAttrSelPo.getAttrSelId());
		return SUCCESS;
	}

	public ICloudServiceAttrSelService getCloudServiceAttrSelService() {
		return cloudServiceAttrSelService;
	}

	public void setCloudServiceAttrSelService(ICloudServiceAttrSelService cloudServiceAttrSelService) {
		this.cloudServiceAttrSelService = cloudServiceAttrSelService;
	}

	public CloudServiceAttrSelPo getCloudServiceAttrSelPo() {
		return cloudServiceAttrSelPo;
	}

	public void setCloudServiceAttrSelPo(CloudServiceAttrSelPo cloudServiceAttrSelPo) {
		this.cloudServiceAttrSelPo = cloudServiceAttrSelPo;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Map<String, String> getPageParams() {
		return pageParams;
	}

	public void setPageParams(Map<String, String> pageParams) {
		this.pageParams = pageParams;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
