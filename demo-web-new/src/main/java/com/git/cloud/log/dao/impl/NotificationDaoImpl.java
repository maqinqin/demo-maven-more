package com.git.cloud.log.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.log.dao.INotificationDao;
import com.git.cloud.log.model.po.NotificationPo;

public class NotificationDaoImpl extends CommonDAOImpl implements INotificationDao {

	@Override
	public Pagination<NotificationPo> findNotificationsPage(
			PaginationParam paginationParam) throws RollbackableBizException {
		return this.pageQuery("queryNotiListTotal","queryNotiListPage", paginationParam);
	}

	@Override
	public void insertNotification(NotificationPo notiPo)
			throws RollbackableBizException {
		this.save("addNotification", notiPo);
	}

	@Override
	public NotificationPo findNotificationById(String id)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNotiState(NotificationPo notiPo) throws RollbackableBizException {
		this.update("updateNotiState", notiPo);
	}

	@Override
	public String findNotificationIdByAlarmKey(String alarmKey) throws RollbackableBizException {
		NotificationPo po = (NotificationPo)this.findObjectByID("findNotificationIdByAlarmKey", alarmKey);
		if(po != null){
			return po.getId();
		}
		else{
			return null;
		}
	}

	@Override
	public void updateAlarmState(NotificationPo notiPo) throws RollbackableBizException {
		this.update("updateAlarmState", notiPo);
	}

}
