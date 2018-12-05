package com.git.cloud.log.service;


import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.model.po.LogPo;

/**
 * 日志管理
 * @description: 
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */
public interface ILogService {

	/**
	 * 查询日志
	 * @Title: findLogPage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<LogPo>
	 * @throws
	 */
	public  Pagination<LogPo> findLogPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 根据 findLogById 查询日志
	 * @Title: findLogById
	 * @Description: TODO
	 * @field: @param logId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List
	 * @throws
	 */
	public LogPo findLogById(String logId) throws RollbackableBizException;
}
