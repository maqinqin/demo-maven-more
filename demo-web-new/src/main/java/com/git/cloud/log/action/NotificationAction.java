package com.git.cloud.log.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;

public class NotificationAction extends BaseAction<Object>{
	
	private static Logger logger = LoggerFactory.getLogger(NotificationAction.class);
	private INotificationService notiServiceImpl;
	private NotificationPo notiPo;
	
	public NotificationPo getNotiPo() {
		return notiPo;
	}

	public void setNotiPo(NotificationPo notiPo) {
		this.notiPo = notiPo;
	}

	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}

	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}

	/**
	 * 分页查询通知管理
	 * @throws Exception
	 */
	public void findNotificationsPage() throws Exception {
		this.jsonOut(notiServiceImpl.findNotificationsPage(this.getPaginationParam()));
	}
	
	public void updateNotiState()throws Exception{
		notiServiceImpl.updateNotiState(notiPo);
	}
}
