package com.git.cloud.cloudservice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.dao.ICloudServiceAttrSelDao;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public class CloudServiceAttrSelDaoImpl extends CommonDAOImpl implements ICloudServiceAttrSelDao {

	@Override
	public CloudServiceAttrSelPo save(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException {
		save("CloudServiceAttrSel.insert", cloudServiceAttrSelPo);
		return null;
	}

	@Override
	public void update(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException {
		update("CloudServiceAttrSel.update", cloudServiceAttrSelPo);
		
	}

	@Override
	public CloudServiceAttrSelPo findById(String id) throws RollbackableBizException {
		List<CloudServiceAttrSelPo> list = findByID("CloudServiceAttrSel.load", id);
		if (list != null) {
			for (CloudServiceAttrSelPo p : list)
				return p;
		}
		return null;
	}

	@Override
	public Pagination<CloudServiceAttrSelPo> queryPagination(PaginationParam pagination) {
		return pageQuery("CloudServiceAttrSel.count", "CloudServiceAttrSel.search", pagination);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		if (ids != null) {
			for (String id : ids)
				delete("CloudServiceAttrSel.delete", id);
		}
		
	}

	/**
	 * 文件导出时进行查询cloud_service+arrt_sel表进行数据拼接
	 */
	@Override
	public List<CloudServiceAttrSelPo> cloudLeading(ArrayList<String> list) throws RollbackableBizException {
		List<CloudServiceAttrSelPo> listAttrSel = new ArrayList<CloudServiceAttrSelPo>();
		for(String id : list) {
			List<CloudServiceAttrSelPo> cloudeServiceAttrSelPo = findByID("CloudServiceAttrSel.file",id);
			if(cloudeServiceAttrSelPo != null){
				for(CloudServiceAttrSelPo po : cloudeServiceAttrSelPo) {
					listAttrSel.add(po);
					return listAttrSel;
				}
			}

		}
		return null;
	}

}
