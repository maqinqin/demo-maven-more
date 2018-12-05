package com.git.cloud.log.dao;


import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.model.po.LogPo;

/**
 * 日志数据库接口
 * @author shl
 */
public interface ILogDao extends ICommonDAO{
	
	/**
	 * 查询日志列表
	 * @Title: findLogPage
	 * @Description: TODO
	 * @field: @param paginationParam
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return Pagination<LogPo>
	 * @throws
	 */
	public Pagination<LogPo> findLogPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 根据日志Id 查询日志
	 * @Title: findLogById
	 * @Description: TODO
	 * @field: @param logId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List
	 * @throws
	 */
	public LogPo findLogById(String logId) throws RollbackableBizException;
	
	/**
	 * 插入日志
	 * @param log
	 * @throws RollbackableBizException
	 */
	public void insertSystemLog(LogPo log) throws RollbackableBizException;
	
}