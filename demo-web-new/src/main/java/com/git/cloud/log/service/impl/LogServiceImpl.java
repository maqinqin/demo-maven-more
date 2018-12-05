package com.git.cloud.log.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.dao.ILogDao;
import com.git.cloud.log.model.po.LogPo;
import com.git.cloud.log.service.ILogService;

/**
 * 日志管理
 * 
 * @description:
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */

public class LogServiceImpl implements ILogService {
	
	private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	private ILogDao logDao;
	private static String mat = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public Pagination<LogPo> findLogPage(PaginationParam paginationParam)
			throws RollbackableBizException {
		return logDao.findLogPage(paginationParam);
	}


	@Override
	public LogPo findLogById(String logId) throws RollbackableBizException {
		return logDao.findLogById(logId);
	}

/************************************setter&&getter*******************************/
	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}


}
