package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.request.dao.IBmSrStatusMapDao;
import com.git.cloud.request.model.po.BmSrStatusMapPo;

public class BmSrStatusMapDaoImpl extends CommonDAOImpl implements IBmSrStatusMapDao {

	public BmSrStatusMapPo findBmSrStatusMap(String srId, String nodeName) {
		BmSrStatusMapPo bmSrStatusMap = null;
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srId", srId);
		paramMap.put("nodeName", nodeName);
		List<BmSrStatusMapPo> list = this.findListByParam("findBmSrStatusMap", paramMap);
		if(list != null && list.size() > 0) {
			bmSrStatusMap = list.get(0);
		}
		return bmSrStatusMap;
	}
}
