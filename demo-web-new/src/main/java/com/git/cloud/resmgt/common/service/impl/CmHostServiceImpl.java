package com.git.cloud.resmgt.common.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmIpShowBo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.service.ICmHostService;
import com.git.cloud.resmgt.common.util.AutomationHostsManager;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.support.constants.SAConstants;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;

public class CmHostServiceImpl implements ICmHostService {
	private ICmHostDAO cmHostDao;
	
	private static Logger logger = LoggerFactory.getLogger(CmHostServiceImpl.class);
	
    private IRmGeneralServerDAO rmGeneralServerDAO ;
	
    private IRmDatacenterDAO rmDatacenterDAO ; 
    
    private ResAdptInvokerFactory resInvokerFactory;
    
   	private IIpAllocToDeviceNewService ipAllocToDeviceImpl ;
   	
   	
   	
	public IRmGeneralServerDAO getRmGeneralServerDAO() {
		return rmGeneralServerDAO;
	}

	public void setRmGeneralServerDAO(IRmGeneralServerDAO rmGeneralServerDAO) {
		this.rmGeneralServerDAO = rmGeneralServerDAO;
	}

	public IRmDatacenterDAO getRmDatacenterDAO() {
		return rmDatacenterDAO;
	}

	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}

	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}


	public IIpAllocToDeviceNewService getIpAllocToDeviceImpl() {
		return ipAllocToDeviceImpl;
	}

	public void setIpAllocToDeviceImpl(
			IIpAllocToDeviceNewService ipAllocToDeviceImpl) {
		this.ipAllocToDeviceImpl = ipAllocToDeviceImpl;
	}

	public ICmHostDAO getCmHostDao() {
		return cmHostDao;
	}

	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}

	@Override
	public void updateCmHostUsed() throws RollbackableBizException {
		cmHostDao.updateCmHostUsed();
	}

	@Override
	public Pagination<CmHostVo> getHostList(PaginationParam paginationParam)
			throws RollbackableBizException {
		
		Pagination<CmHostVo> HostList = null;

		try {
			HostList = cmHostDao.getHostList(paginationParam);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取主机列表时发生异常:" + e);
		}
		
		return HostList;
	}

	@Override
	public CmHostUsernamePasswordPo getUserInfo(String hostId)
			throws RollbackableBizException {
		CmHostUsernamePasswordPo cupp = cmHostDao.findHostUsernamePassword(hostId);
		if(cupp != null)  //可能之前没有设置用户名密码
		cupp.setPassword(PwdUtil.decryption(cupp.getPassword()));
		return cupp;
	}

	@Override
	public String updateUserNamePasswd(CmHostUsernamePasswordPo cupp) throws RollbackableBizException {
		String result = "1";
		Boolean udateflg = true;
		try {
			String pwd = cupp.getPassword();
			if(!pwd.equals("") && pwd.length()>0){
				cupp.setPassword(PwdUtil.encryption(pwd));
			} else {
				cupp.setPassword(null);
			}
			
			SysUserPo createUser = (SysUserPo)SecurityUtils.getSubject().getPrincipal();
			
			//先判断是由存在用户名密码
			int count = cmHostDao.findExistUserNamePassWd(cupp);
			if(count == 0) {
				udateflg = false;
			}
			if(udateflg) {
				cupp.setUpdateUser(createUser.getLoginName());
				cupp.setUpdateDateTime(new Date());
				cmHostDao.updateUserNamePasswd(cupp);
				
			}else{
				cupp.setPrimaryId(UUIDGenerator.getUUID());
				cupp.setCreateDateTime(new Date());
				cupp.setCreateUser(createUser.getLoginName());
				cmHostDao.insertHostUserNamePasswd(cupp);
			}
		} catch (Exception e) {
			result = "0";
			logger.error("更新用户名密码"+e);
		}
		
		return result;
	}

	public String UpdataAutomationHost(String hostIP,String hostName,String optType,String dcId,String dcQueue,IResAdptInvoker invoker) throws Exception{

		//获取automation服务器信息
		List<RmGeneralServerVo> serverList = rmGeneralServerDAO.findRmGeneralServerBydcId(dcId);
		if(serverList==null||serverList.isEmpty()){
			throw new Exception("获取Automation服务器信息失败！");
		}
		RmGeneralServerVo serverVo = serverList.get(0);
		String autoServerIp = serverVo.getServerIp();
		String autoUser = serverVo.getUserName();
		String autoUserPwd = PwdUtil.decryption(serverVo.getPassword());
		Map<String,Object> contextParams = Maps.newHashMap();
		contextParams.put("DC_QUEUE", dcQueue);
		contextParams.put(SAConstants.SERVER_IP, autoServerIp);
		//user:icmsauto
		contextParams.put(SAConstants.USER_NAME, autoUser);
		contextParams.put(SAConstants.USER_PASSWORD, autoUserPwd);
		//optType : add/del
		//TODO abandon
		String cmd = SAConstants.AUTOMATION_HOSTS_SHELL+"  "+optType+" "+hostIP+" "+hostName;
		
		logger.info(cmd);
		contextParams.put(SAConstants.CMD_LIST, Arrays.asList(new String[]{cmd}));
		String result = AutomationHostsManager.updateHosts(invoker, contextParams);
		return result;
	}


}
