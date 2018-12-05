package com.git.cloud.cloudservice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.vo.QueryDataVo;
import com.git.cloud.cloudservice.service.ICloudServiceAttrService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;

public class CloudServiceAttrAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1461015574729711914L;

	private ICloudServiceAttrService cloudServiceAttrService;
	private CloudServiceAttrPo cloudServiceAttrPo;
	private Pagination pagination;
	private Map<String, String> pageParams;
	private Map<String, Object> result;
	private String listSqlId;
	private String deviceId;


	public String getListSqlId() {
		return listSqlId;
	}

	public void setListSqlId(String listSqlId) {
		this.listSqlId = listSqlId;
	}

	public String index() {
		return SUCCESS;
	}

	public String search() {
		pagination = cloudServiceAttrService.queryPagination(this.getPaginationParam());
		return SUCCESS;
	}

	public String save() throws Exception {

		if (cloudServiceAttrPo != null) {
			if (cloudServiceAttrPo.getAttrId() == null || "".equals(cloudServiceAttrPo.getAttrId())) {
				cloudServiceAttrPo.setAttrId(UUIDGenerator.getUUID());
				cloudServiceAttrService.save(cloudServiceAttrPo);
			} else {
				cloudServiceAttrService.update(cloudServiceAttrPo);
			}
		}
		return SUCCESS;
	}
	/**
	 * 保存属性信息
	 * @Title: saveAttr
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveAttr() throws Exception {
		cloudServiceAttrPo.setAttrId(UUIDGenerator.getUUID());
		cloudServiceAttrPo.setIsActive(IsActiveEnum.YES.getValue());
		if(cloudServiceAttrPo.getIsVisible().equals("") || cloudServiceAttrPo.getIsVisible()=="null"){
			cloudServiceAttrPo.setIsVisible("Y");
		}
		if(cloudServiceAttrPo.getIsRequire().equals("") || cloudServiceAttrPo.getIsRequire()=="null"){
			cloudServiceAttrPo.setIsRequire("Y");
		}
		cloudServiceAttrService.save(cloudServiceAttrPo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("attrId", cloudServiceAttrPo.getAttrId());
		this.jsonOut(map);
	}
	
	public void updateAttr() throws Exception {
		cloudServiceAttrService.update(cloudServiceAttrPo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("attrId", cloudServiceAttrPo.getAttrId());
		this.jsonOut(map);
	}
	public String stop() throws Exception {
		if (cloudServiceAttrPo != null) {
			if (cloudServiceAttrPo.getAttrId() != null && !"".equals(cloudServiceAttrPo.getAttrId())) {
				cloudServiceAttrPo = cloudServiceAttrService.findById(cloudServiceAttrPo.getAttrId());
				cloudServiceAttrPo.setIsActive("0");
				cloudServiceAttrService.update(cloudServiceAttrPo);
			}
		}
		return SUCCESS;
	}

	public String delete() throws Exception {
		if (cloudServiceAttrPo != null) {
			if (cloudServiceAttrPo.getAttrId() != null && !"".equals(cloudServiceAttrPo.getAttrId())) {
				String[] ids = new String[] { cloudServiceAttrPo.getAttrId() };
				cloudServiceAttrService.deleteById(ids);
			}
		}
		return SUCCESS;
	}

	public String load() throws Exception {
		cloudServiceAttrPo = cloudServiceAttrService.findById(cloudServiceAttrPo.getAttrId());
		return SUCCESS;
	}
	
	public void checkCloudServiceAttrs()throws Exception{
		super.ObjectOut(cloudServiceAttrService.checkCloudServiceAttrs(cloudServiceAttrPo));
	}

	public ICloudServiceAttrService getCloudServiceAttrService() {
		return cloudServiceAttrService;
	}

	public void setCloudServiceAttrService(ICloudServiceAttrService cloudServiceAttrService) {
		this.cloudServiceAttrService = cloudServiceAttrService;
	}

	public CloudServiceAttrPo getCloudServiceAttrPo() {
		return cloudServiceAttrPo;
	}

	public void setCloudServiceAttrPo(CloudServiceAttrPo cloudServiceAttrPo) {
		this.cloudServiceAttrPo = cloudServiceAttrPo;
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
	
	public void queryDynamicSql() throws Exception {
		List<QueryDataVo> list = cloudServiceAttrService.queryDynamicSQL(this.listSqlId, this.deviceId);
		Map<String, List<QueryDataVo>> map = new HashMap<String, List<QueryDataVo>>();
		map.put("dataList", list);
//		JSONArray fromObject = JSONArray.fromObject(list);
		this.jsonOut(map);
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
