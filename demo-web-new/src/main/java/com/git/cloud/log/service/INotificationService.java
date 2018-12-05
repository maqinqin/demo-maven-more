package com.git.cloud.log.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.model.po.LogPo;
import com.git.cloud.log.model.po.NotificationPo;

public interface INotificationService {
	/**
	 * 查询通知列表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<NotificationPo> findNotificationsPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 添加通知
	 * @param notiPo
	 * @throws RollbackableBizException
	 */
	public void insertNotification(NotificationPo notiPo) throws RollbackableBizException;
	/**
	 * 根据主键Id 查询通知
	 * @param id 通知ID
	 * @return
	 * @throws RollbackableBizException
	 */
	public NotificationPo findNotificationById(String id) throws RollbackableBizException;
	/**
	 * 更新状态
	 * @param notiPo
	 * @throws RollbackableBizException
	 */
	public void updateNotiState(NotificationPo notiPo)throws RollbackableBizException;
	/**
	* @Title: findNotificationIdByAlarmKey  
	* @Description: 根据alarm key查询报警信息
	* @param @param alarmKey
	* @return String    返回类型  
	 */
	public String findNotificationIdByAlarmKey(String alarmKey) throws RollbackableBizException;
	/**
	* @Title: updateAlarmState  
	* @Description: 更新报警的时间和状态
	* @param  notiPo
	 */
	public void updateAlarmState(NotificationPo notiPo) throws RollbackableBizException;
}
