package com.git.cloud.handler.automation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.sa.common.CommonParamInitAutomationHandler;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.sys.dao.IUserDao;
import com.git.cloud.sys.model.MailTypeEnum;
import com.git.cloud.sys.model.po.SysMailMsgPo;
import com.git.cloud.sys.model.vo.SysUserVo;
import com.git.cloud.sys.service.ISysMailMsgService;
import com.google.common.collect.Lists;

public class SendMailHandler extends LocalAbstractAutomationHandler{
	private static Logger log = LoggerFactory.getLogger(SendMailHandler.class);
	
	
	public ISysMailMsgService sysMailMsgService() throws Exception {
		return (ISysMailMsgService) WebApplicationManager.getBean("sysMailMsgServiceImpl");
	} 
	
	public IUserDao userDao() throws Exception {
		return (IUserDao) WebApplicationManager.getBean("userDaoImpl");
	} 
	
	public String execute(HashMap<String, Object> contenxtParams) throws Exception{
		String result = "9999";
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);
		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);
		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		
		BmSrPo bmSrPo = this.getAutomationService().getSrById(srvReqId);
		
		String loginName = bmSrPo.getCreateUser();
		//String loginName = (String) contenxtParams.get("LOGIN_NAME");
		List<SysUserVo> userList = Lists.newArrayList();
		String mailAddress = "";
		if(loginName != null && !"".equals(loginName.trim())){
			userList = userDao().findUserByLoginName(loginName);
			if(userList !=null && userList.size()>0){
				String mail = userList.get(0).getEmail().trim();
				if( mail != null && !"".equals(mail)){
					mailAddress = mail;
				}
			}
		}
		String[] mailList = null;
		if(mailAddress !=null && !"".equals(mailAddress)){
			mailList = mailAddress.split(";");
			if(mailList != null && mailList.length>0){
				for(String mail : mailList){
					SysMailMsgPo sysMailMsgPo = new SysMailMsgPo();
					sysMailMsgPo.setId(UUIDGenerator.getUUID());
					sysMailMsgPo.setType(MailTypeEnum.MAIL_REQUEST_FINISH.getValue());
					sysMailMsgPo.setReceives(mail);				
					sysMailMsgPo.setCarbonCopy("");				
					sysMailMsgPo.setTitle("云管理平台申请单["+bmSrPo.getSrCode()+"]消息");					
					sysMailMsgPo.setContent("您的申请单["+bmSrPo.getSrCode()+"]已成功执行完毕，请确认");
					//0.未发送；1.发送成功；2.发送失败
					sysMailMsgPo.setSendStatus("0");			
					sysMailMsgPo.setCreateDateTime(new Date());	
					sysMailMsgService().saveSysMailMsgPo(sysMailMsgPo);
				}
				result = "0000";
			}
		}else{
			result = "9999";
		}
		return result;
	}

	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
