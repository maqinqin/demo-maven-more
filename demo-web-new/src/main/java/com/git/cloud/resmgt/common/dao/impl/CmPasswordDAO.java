package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.google.common.collect.Maps;

public class CmPasswordDAO extends CommonDAOImpl implements ICmPasswordDAO {

	public void insertCmPassword(List<CmPasswordPo> pwdList) throws RollbackableBizException {
		if(CollectionUtil.hasContent(pwdList)){
			this.batchInsert("cmPasswordBatchInsert",pwdList);
		}
	}
	
	public CmPasswordPo findCmPasswordByResourceId(String resourceId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("resourceId", resourceId);
		List<CmPasswordPo> pwdList = this.findListByParam("findCmPasswordByResourceId", paramMap);
		CmPasswordPo pwd = null;
		if(pwdList != null && pwdList.size() > 0) {
			pwd = pwdList.get(0);
		}
		return pwd;
	}
	
	public CmPasswordPo findCmPasswordByResourceId(String resourceId, String userName) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("resourceId", resourceId);
		paramMap.put("userName", userName);
		List<CmPasswordPo> pwdList = this.findListByParam("findCmPasswordByResourceId", paramMap);
		CmPasswordPo pwd = null;
		if(pwdList != null && pwdList.size() > 0) {
			pwd = pwdList.get(0);
		}
		return pwd;
	}
	
	public CmPasswordPo findCmPasswordByResourceUser(String resourceId,String userName) throws RollbackableBizException {
		Map<String,String> params = Maps.newHashMap();
		params.put("resourceId", resourceId);
		params.put("userName", userName);
		List<CmPasswordPo> pwdList = this.findListByParam("findCmPasswordByResourceUser", params);
		CmPasswordPo pwd = null;
		if(pwdList != null && pwdList.size() > 0) {
			pwd = pwdList.get(0);
		}
		return pwd;
	}

	@Override
	public void insertCmPassword(CmPasswordPo cmPasswordPo)
			throws RollbackableBizException {
		
		super.save("insertCmPassword", cmPasswordPo);
	}

	@Override
	public void updateCmPassword(CmPasswordPo cmPasswordPo)
			throws RollbackableBizException {
		
		super.update("updateCmPassword", cmPasswordPo);
	}
	
	public void deleteCmPassword(String resourceId) throws RollbackableBizException{
		Map<String,String> param = Maps.newHashMap();
		param.put("resourceId", resourceId);
		this.deleteForParam("cmPassword.delete", param);
	}
}
