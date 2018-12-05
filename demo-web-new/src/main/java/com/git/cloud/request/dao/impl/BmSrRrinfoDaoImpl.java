package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
/**
 * 资源申请数据层实现类
 * @ClassName:BmSrRrinfoDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
@Service
public class BmSrRrinfoDaoImpl extends CommonDAOImpl implements IBmSrRrinfoDao {
	
	public void insertBmSrRrinfo(BmSrRrinfoVo rrinfoVo) throws RollbackableBizException {
		this.save("insertBmSrRrinfo",rrinfoVo);
	}
	
	public void deleteBmSrRrinfoBySrId(String srId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srId", srId);
		this.deleteForParam("deleteBmSrRrinfo", paramMap);
	}
	public void deleteBmSrRrinfoById(String rrinfoId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("rrinfoId", rrinfoId);
		this.deleteForParam("deleteBmSrRrinfo", paramMap);
	}
	
	public BmSrRrinfoPo findBmSrRrinfoById(String rrinfoId) throws RollbackableBizException {
		return this.findObjectByID("findBmSrRrinfoById", rrinfoId);
	}
	
	public List<BmSrRrinfoVo> findBmSrRrinfoListBySrId(String srId) throws RollbackableBizException {
		return this.findByID("getBmSrRrinfo",srId);
	}
	
	public List<BmSrRrinfoPo> findNoAssignRrinfoListBySrId(String srId) throws RollbackableBizException {
		return this.findByID("findNoAssignRrinfoListBySrId",srId);
	}
	
	public String getHasAttrRrinfoIdBySrId(String srId) throws RollbackableBizException {
		String rrinfoIds = "";
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srId", srId);
		List<BmSrRrinfoPo> rrinfoList = this.findListByParam("getHasAttrRrinfoIdBySrId", paramMap);
		int len = rrinfoList == null ? 0 : rrinfoList.size();
		for(int i=0 ; i<len ; i++) {
			rrinfoIds += "," + rrinfoList.get(i).getRrinfoId();
		}
		return rrinfoIds;
	}

	public List<CmLun> getLunListByRrinfoId(String rrinfoId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("rrinfoId", rrinfoId);
		return this.findListByParam("getLunListByRrinfoId", paramMap);
	}
	
	@Override
	public List<BmSrRrinfoPo> findBmSrRrinfoBySrId(String srId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("srId", srId);
		return this.findListByParam("findBmSrRrinfoBySrId", map);
	}
}