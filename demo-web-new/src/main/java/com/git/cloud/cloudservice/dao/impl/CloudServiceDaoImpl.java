package com.git.cloud.cloudservice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.rest.model.CloudServiceOsRef;
@Repository
public class CloudServiceDaoImpl extends CommonDAOImpl implements ICloudServiceDao {

	@Override
	public CloudServicePo save(CloudServicePo cloudService) throws RollbackableBizException {
		save("CloudService.insert", cloudService);
		return cloudService;
	}

	@Override
	public void update(CloudServicePo cloudService) throws RollbackableBizException {
		update("CloudService.update", cloudService);
	}
	
	@Override
	public void updateCloudServiceRRinfo(CloudServicePo cloudService) throws RollbackableBizException {
		update("updateCloudServiceRRinfo", cloudService);
	}

	@Override
	public CloudServicePo findById(String id) throws RollbackableBizException {
		List<CloudServicePo> list = findByID("CloudService.load", id);
		if (list != null) {
			for (CloudServicePo p : list)
				return p;
		}
		return null;
	}

	@Override
	public Pagination<CloudServicePo> queryPagination(PaginationParam pagination) {
		return pageQuery("CloudService.count", "CloudService.search", pagination);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		if (ids != null) {
			for (String id : ids)
			{	
//				delete("CloudService.delete", id);
				deleteForIsActive("CloudService.delete", id);
				deleteForIsActive("CloudService.deleteAttr", id);
				deleteForIsActive("CloudService.deleteAttrSel", id);
			}	
		}
	}

	/* (non-Javadoc)
	 * <p>Title:findAllById</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.ICloudServiceDao#findAllById(java.lang.String)
	 */
	@Override
	public CloudServiceVo findAllById(String id) throws RollbackableBizException {
		return super.findObjectByID("CloudService.loadAll", id);
	}

	/* (non-Javadoc)
	 * <p>Title:findCloudServicePoCount</p>
	 * <p>Description:</p>
	 * @param cloudService
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.ICloudServiceDao#findCloudServicePoCount(com.git.cloud.cloudservice.model.po.CloudServicePo)
	 */
	@Override
	public Integer findCloudServicePoCount(CloudServicePo cloudService)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudServicesByparam", cloudService);
	}

	/* (non-Javadoc)
	 * <p>Title:queryVmType</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.ICloudServiceDao#queryVmType(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryVmType(String id) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("platFormId", id);
		return getSqlMapClientTemplate().queryForList("CloudService.queryVmType", paramMap);
	}

	@Override
	public CloudServiceOsRef selectCloudServiceOsRef(Map<String, String> map)
			throws RollbackableBizException {
		List<CloudServiceOsRef> list = this.findListByParam("CloudService.selectCloudServiceOsRef", map);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
		
	}

	@Override
	public String findCloudServicePoCountByCode(CloudServicePo cloudService) throws RollbackableBizException {
		return (String) getSqlMapClientTemplate().queryForObject("findCloudServicesByCode", cloudService);
	}

	@Override
	public List<CmPasswordPo> cloudFilePassword(String serviceId) throws RollbackableBizException {
		List<CmPasswordPo> list = findObjectByID("CloudService.file",serviceId);
		if(list != null) {
			return list;
		}
		return null;
	}
	
	@Override
	public int savecmPassword(CmPasswordPo cmPasswordPo) throws RollbackableBizException {
		int flag = this.update("insertCmPasswordPo",cmPasswordPo);
		return flag;
	}

	@Override
	public CmPasswordPo selectIdBypassword(String id) throws RollbackableBizException {
		List<CmPasswordPo> cmPasswordPo = findByID("selectByPassword.file",id);
		if(cmPasswordPo != null) {
			for(CmPasswordPo po : cmPasswordPo) {
				return po;
			}
		}
		return null;
	}
}