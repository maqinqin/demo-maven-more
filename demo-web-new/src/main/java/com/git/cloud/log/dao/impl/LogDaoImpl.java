package com.git.cloud.log.dao.impl;


import java.util.HashMap;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.dao.ILogDao;
import com.git.cloud.log.model.po.LogPo;

public class LogDaoImpl extends CommonDAOImpl implements ILogDao{
	
	public Pagination<LogPo> findLogPage(PaginationParam paginationParam) throws RollbackableBizException{
		return this.pageQuery("queryLogListTotal","queryLogListPage", paginationParam);
	}
	
	public LogPo findLogById(String logId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("logId", logId);
		return this.findObjectByMap("queryLogByParams", map);
	}
	
	public void insertSystemLog(LogPo log) throws RollbackableBizException {
		this.save("insertSystemLog", log);
	}
}
