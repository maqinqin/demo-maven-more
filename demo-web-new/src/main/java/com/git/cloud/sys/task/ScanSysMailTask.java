package com.git.cloud.sys.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.sys.model.po.SysMailMsgPo;
import com.git.cloud.sys.service.ISysMailMsgService;

public class ScanSysMailTask {
	private static Logger logger = LoggerFactory.getLogger(ScanSysMailTask.class);
	
	private ISysMailMsgService sysMailMsgServiceImpl;
	
	public void execute()  {
		// 获取待发送邮件集合
		logger.info("------ScanSysMailTask begin-----");
		try {
			boolean flag = false;
			List<SysMailMsgPo> sysMailMsgPoList = sysMailMsgServiceImpl.selectSysMailMsgPo();
			// 发送邮件信息
			int len = sysMailMsgPoList == null ? 0 : sysMailMsgPoList.size();
			if(len>0){
				for(SysMailMsgPo mail : sysMailMsgPoList) {
					try {
						flag = sysMailMsgServiceImpl.saveSysMailMsgHisAndSendMail(mail);
						if(flag){
							logger.info("------发送邮件成功-----");
						}else{
							logger.error("------发送邮件失败-----");
						}
					} catch(Exception ex) {
						ex.printStackTrace();
						logger.error("------发送邮件出现异常-----" + ex.getMessage());
					}
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		logger.info("------ScanSysMailTask end-----");
	}

	public void setSysMailMsgServiceImpl(ISysMailMsgService sysMailMsgServiceImpl) {
		this.sysMailMsgServiceImpl = sysMailMsgServiceImpl;
	}
}
