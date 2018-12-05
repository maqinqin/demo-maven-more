package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.model.vo.BmSrAttrValVo;

/**
 * 云服务参数数据层实现类
 * @ClassName:BmSrAttrValDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class BmSrAttrValDaoImpl extends CommonDAOImpl implements IBmSrAttrValDao {

	public void insertBmSrAttrList(List<BmSrAttrValVo> attrList) throws RollbackableBizException {
		if(CollectionUtil.hasContent(attrList)){
			this.batchInsert("insertBmSrAttr",attrList);	
		}
	}
	
	public void deleteBmSrAttrByRrinfoId(String rrinfoId) throws RollbackableBizException {
		this.delete("deleteBmSrAttr", rrinfoId);
	}
	
	public List<BmSrAttrValVo> findBmSrAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException {
		return this.findByID("request-query.getBmSrAttr", rrinfoId);
	}
	
	public List<BmSrAttrValVo> findBmSrDeviceAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException {
		return this.findByID("request-query.getBmSrAttrDevice", rrinfoId);
	}
	
	@Override
	public List<BmSrAttrValVo> findBmSrNotVisibleAttrValListByRrinfoId(String rrinfoId) throws RollbackableBizException {
		return this.findByID("request-query.getBmSrNotVisibleAttr", rrinfoId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BmSrAttrValVo> findBmSrAttrAutoListByDeviceId(String rrinfoId, String deviceId) {
		HashMap<String, String> param = new HashMap<String, String> ();
		param.put("rrinfoId", rrinfoId);
		param.put("deviceId", deviceId);
		return this.getSqlMapClientTemplate().queryForList("request-query.findBmSrAttrAutoListByDeviceId", param);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BmSrAttrValVo> haveFloatingIp(String rrinfoId, String serviceId) {
		HashMap<String, String> param = new HashMap<String, String> ();
		param.put("rrinfoId", rrinfoId);
		param.put("serviceId", serviceId);
		return this.getSqlMapClientTemplate().queryForList("haveFloatingIp", param);
	}
}
