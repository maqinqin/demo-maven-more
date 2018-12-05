package com.git.cloud.cloudservice.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.dao.ICloudServiceAttrDao;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.vo.QueryDataVo;
import com.git.cloud.cloudservice.service.ICloudServiceAttrService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.taglib.util.Internation;

public class CloudServiceAttrServiceImpl implements ICloudServiceAttrService {

	private ICloudServiceAttrDao cloudServiceAttrDao;

	@Override
	public CloudServiceAttrPo save(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException {
		return cloudServiceAttrDao.save(cloudServiceAttrPo);
	}

	@Override
	public void update(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException {
		cloudServiceAttrDao.update(cloudServiceAttrPo);
	}

	@Override
	public CloudServiceAttrPo findById(String id) throws RollbackableBizException {
		return cloudServiceAttrDao.findById(id);
	}

	@Override
	public Pagination<CloudServiceAttrPo> queryPagination(PaginationParam pagination) {
		Pagination<CloudServiceAttrPo> lis = cloudServiceAttrDao.queryPagination(pagination);
		List<CloudServiceAttrPo> li = lis.getDataList();
		List<CloudServiceAttrPo> li2 = new ArrayList<CloudServiceAttrPo>();
		for(CloudServiceAttrPo k : li){
			if(k.getIsVisible().equals("N")){
				k.setIsVisible(Internation.language("service_difine_para_invisible"));
				li2.add(k);
			}else if(k.getIsVisible().equals("Y")){
				k.setIsVisible(Internation.language("service_difine_para_visible"));
				li2.add(k);
			}else{
				k.setIsVisible(Internation.language("service_difine_para_nodata"));
				li2.add(k);
			}
		}
		lis.setDataList(li2);
		return lis;
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		cloudServiceAttrDao.deleteById(ids);
	}

	public ICloudServiceAttrDao getCloudServiceAttrDao() {
		return cloudServiceAttrDao;
	}

	public void setCloudServiceAttrDao(ICloudServiceAttrDao cloudServiceAttrDao) {
		this.cloudServiceAttrDao = cloudServiceAttrDao;
	}

	@Override
	public boolean checkCloudServiceAttrs(CloudServiceAttrPo cloudServiceAttrPo)
			throws RollbackableBizException {

		int count = cloudServiceAttrDao.findCloudServiceAttrCount(cloudServiceAttrPo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<QueryDataVo> queryDynamicSQL(String listSqlId, String deviceId) throws Exception {
		String listSql = cloudServiceAttrDao.queryListSql(listSqlId);
		if(deviceId != null && !"".equals(deviceId)) {
			listSql = listSql.replaceAll("#DEVICE_ID#", deviceId);
		}
		return cloudServiceAttrDao.queryDynamicSQL(listSql);
	}
}
