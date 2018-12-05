package com.git.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.mail.EmailBean;
import com.git.cloud.common.mail.MailUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.dao.ISysMailMsgDao;
import com.git.cloud.sys.model.SendStatusEnum;
import com.git.cloud.sys.model.po.SysMailMsgHisPo;
import com.git.cloud.sys.model.po.SysMailMsgPo;
import com.git.cloud.sys.service.ISysMailMsgService;

public class SysMailMsgServiceImpl implements ISysMailMsgService {
	
	private static Logger logger = LoggerFactory.getLogger(SysMailMsgServiceImpl.class);
	
	private ISysMailMsgDao sysMailMsgDaoImpl;
	
	@Override
	public void saveSysMailMsgPo(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException {
		sysMailMsgDaoImpl.saveSysMailMsgPo(sysMailMsgPo);
	}
	
	/**
	 * 查询系统邮件
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public List<SysMailMsgPo> selectSysMailMsgPo() throws RollbackableBizException {
		return sysMailMsgDaoImpl.selectSysMailMsgPo();
	}
	
	public boolean saveSysMailMsgHisAndSendMail(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException{
		boolean flag = false;
		if(sysMailMsgPo != null) {
			if(sysMailMsgPo.getSendStatus().equals(SendStatusEnum.SEND_STATUS_UNSEND.getValue())) { // 未发送
				SysMailMsgHisPo sysMailMsgHisPo = this.getSysMailMsgHisPo(sysMailMsgPo);
				String mails = sysMailMsgPo.getReceives();
				String[] ms = mails.split(";");
				//1.发送邮件
				List<String> to = new ArrayList<String> ();
				for (String email : ms) {
					to.add(email);
				}
				EmailBean emailBean = new EmailBean();
				emailBean.setMailTo(to);
				emailBean.setSubject(sysMailMsgPo.getTitle());
				emailBean.setMsgContent(sysMailMsgPo.getContent());
				try {
					MailUtil.sendMail(emailBean);
					flag = true;
				} catch (MessagingException e) {
					logger.error("异常exception",e);
				}
				if(flag) {
					//2.保存到系统邮件历史记录表
					this.saveSysMailMsgHisPo(sysMailMsgHisPo);
					//3.删除系统邮件表
					this.deleteSysMailMsgPo(sysMailMsgPo.getId());
					logger.info("保存系统邮件历史信息，发送邮件成功。");
				}
			}
		}
		return flag;
	}
	
	/**
	 * 封装系统邮件消息历史记录
	 * @param sysMailMsgPo
	 * @return
	 */
	public SysMailMsgHisPo getSysMailMsgHisPo(SysMailMsgPo sysMailMsgPo){
		SysMailMsgHisPo sysMailMsgHisPo = new SysMailMsgHisPo();
		if(sysMailMsgPo != null){
			String id = UUIDGenerator.getUUID();
			sysMailMsgHisPo.setId(id);
			sysMailMsgHisPo.setType(sysMailMsgPo.getType());
			sysMailMsgHisPo.setReceives(sysMailMsgPo.getReceives());
//			sysMailMsgHisPo.setCarbonCopy(sysMailMsgPo.getCarbonCopy());
			sysMailMsgHisPo.setTitle(sysMailMsgPo.getTitle());
			sysMailMsgHisPo.setContent(sysMailMsgPo.getContent());
			sysMailMsgHisPo.setSendStatus(SendStatusEnum.SEND_STATUS_SENDSUCCESS.getValue());
			sysMailMsgHisPo.setSendTime(new Date(System.currentTimeMillis()));
		}
		return sysMailMsgHisPo;
	}
	
	public void saveSysMailMsgHisPo(SysMailMsgHisPo sysMailMsgHisPo) throws RollbackableBizException {
		sysMailMsgDaoImpl.saveSysMailMsgHisPo(sysMailMsgHisPo);
	}
	
	public void deleteSysMailMsgPo(String id) throws RollbackableBizException {
		sysMailMsgDaoImpl.deleteSysMailMsgPo(id);
	}

	public void setSysMailMsgDaoImpl(ISysMailMsgDao sysMailMsgDaoImpl) {
		this.sysMailMsgDaoImpl = sysMailMsgDaoImpl;
	}
}
