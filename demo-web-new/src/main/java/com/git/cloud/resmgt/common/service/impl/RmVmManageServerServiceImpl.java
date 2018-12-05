package com.git.cloud.resmgt.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.IRmVmManageServerService;

/**
 * @ClassName:RmVmManageServerServiceImpl
 * @Description:虚拟机管理机
 * @author lizhizhong
 * @date 2014-12-17 下午1:44:01
 */
public class RmVmManageServerServiceImpl implements IRmVmManageServerService {

	private IRmVmManageServerDAO iRmVmManageServerDAO;
	private static Logger logger = LoggerFactory.getLogger(RmVmManageServerServiceImpl.class);

	@Override
	public List<RmPlatformPo> getPlatformInfo() {
		List<RmPlatformPo> RmPlatformList = null;
		try {
			RmPlatformList = iRmVmManageServerDAO.getPlatformInfo();
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取列表时发生异常:" + e);
		}

		return RmPlatformList;
	}

	@Override
	public List<RmVirtualTypePo> getVmTypeNameInfo(String platformId) {

		List<RmVirtualTypePo> RmVirtualTypeList = null;
		try {
			RmVirtualTypeList = iRmVmManageServerDAO.getVmTypeNameInfo(platformId);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机管理机类型时发生异常:" + e);
		}

		return RmVirtualTypeList;
	}

	@Override
	public Pagination<RmVmManageServerPo> getvmManageServerList(PaginationParam paginationParam) {
		Pagination<RmVmManageServerPo> RmVmManageServer = null;

		try {
			RmVmManageServer = iRmVmManageServerDAO.getvmManageServerList(paginationParam);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机管理机列表时发生异常:" + e);
		}

		return RmVmManageServer;
	}

	@Override
	public RmVmManageServerPo getvmManageServerInfo(String serverId) {
		RmVmManageServerPo RmVmManageServer = null;

		try {
			RmVmManageServer = iRmVmManageServerDAO.getvmManageServerInfo(serverId);
			if (StringUtils.isBlank(RmVmManageServer.getPassword())) {
				RmVmManageServer.setPassword("");
			} else {
				// 先对密码解密
				RmVmManageServer.setPassword(PwdUtil.decryption(RmVmManageServer.getPassword()));
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机管理机时发生异常:" + e);
		}

		return RmVmManageServer;
	}

	@Override
	public boolean checkServerName(RmVmManageServerPo rmVmManageServerPo) {

		int countname = 0;
		try {
			// 检查服务器名是否已经被使用
			countname = iRmVmManageServerDAO.selectVMServerName(rmVmManageServerPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机管理机数量时发生异常:" + e);
		}
		if (countname > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean checkServerIp(RmVmManageServerPo rmVmManageServerPo) {

		int countip = 0;
		try {
			// 检查IP是否已经被使用
			countip = iRmVmManageServerDAO.selectVMServerIp(rmVmManageServerPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取Ip数时发生异常:" + e);
		}
		if (countip > 0) {
			return false;
		} else {
			return true;
		}
	}

//	@Override
//	public String insertvmManageServer(RmVmManageServerPo rmVmManageServerPo) {
//
//		String rtnMsg = "";
//		int countCdp = 0;
//		int server = 0;
//		try {
//
//			// 检查虚机管理服务器是否已经存在
//			server = iRmVmManageServerDAO.selectVMServer(rmVmManageServerPo);
//
//			// 虚机管理服务器已经存在，执行更新操作
//			if (server > 0) {
//				// 检查虚机管理服务器是否已经被使用
//				// countCdp = iRmVmManageServerDAO.selectVMServerCount(rmVmManageServerPo.getId());
//				// if (countCdp > 0) {
//				// rtnMsg = "该虚机管理服务器   正在使用。";
//				// // 管理机未被使用时，才可以执行下一步操作
//				// } else {
//				this.updatevmManageServer(rmVmManageServerPo);
//				// }
//			} else {// 虚机管理服务器不存在时，执行插入操作
//				this.insertOfvmManageServer(rmVmManageServerPo);
//			}
//
//		} catch (RollbackableBizException e) {
//			// TODO Auto-generated catch block
//			logger.error("获取虚机列表时发生异常:" + e);
//		}
//
//		return rtnMsg;
//	}

	@Override
	public String updatevmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		// 设置更新日期
		String rtnMsg = "";
		try{
			rmVmManageServerPo.setUpdateDateTime(new Date());
		    iRmVmManageServerDAO.updateVmManageServer(rmVmManageServerPo);
			// 获取虚机管理服务器的用户名和密码
			CmPasswordPo cmPasswordPo = rmVmManageServerPo.getCmPasswordPo();
			cmPasswordPo.setResourceId(rmVmManageServerPo.getId());
			cmPasswordPo.setUpdateDateTime(new Date());
			cmPasswordPo.setPassword(PwdUtil.encryption(cmPasswordPo.getPassword()));
			iRmVmManageServerDAO.updateCmPasswordPo(cmPasswordPo);
		}catch(RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机列表时发生异常:" + e);
		}
		return rtnMsg;
	}

	@Override
	public String insertOfvmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		// 设置主键
		String rtnMsg = "";
		try{
		rmVmManageServerPo.setId(UUIDGenerator.getUUID());
		rmVmManageServerPo.setIsActive(IsActiveEnum.YES.getValue());
		// 设置添加日期
		rmVmManageServerPo.setCreateDateTime(new Date());

		iRmVmManageServerDAO.insertVmManageServer(rmVmManageServerPo);

		// 获取虚机管理服务器的用户名和密码
		CmPasswordPo cmPasswordPo = rmVmManageServerPo.getCmPasswordPo();
		// 设置主键
		cmPasswordPo.setId(UUIDGenerator.getUUID());
		cmPasswordPo.setResourceId(rmVmManageServerPo.getId());
		cmPasswordPo.setCreateDateTime(new Date());
		cmPasswordPo.setPassword(PwdUtil.encryption(cmPasswordPo.getPassword()));

		iRmVmManageServerDAO.insertCmPasswordPo(cmPasswordPo);
		}catch(RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚机列表时发生异常:" + e);
		}
		return rtnMsg;
	}

	@Override
	public List<String> deleteVMServerList(String id) {
		int count = 0;
		List<String> rtnids = new ArrayList<String>();
		try {
			count = iRmVmManageServerDAO.selectVMServerCount(id);
			// 如果管理服务器已使用(与CDP存在关联关系)
			if (count > 0) {
				rtnids.add(id);
			}
			// 如果管理服务器都未使用
			if (rtnids.size() == 0) {
				iRmVmManageServerDAO.deleteVMServer(id);
			}
			
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("删除虚机管理机时发生异常:" + e);
		}
		return rtnids;
	}

	public IRmVmManageServerDAO getiRmVmManageServerDAO() {
		return iRmVmManageServerDAO;
	}

	public void setiRmVmManageServerDAO(IRmVmManageServerDAO iRmVmManageServerDAO) {
		this.iRmVmManageServerDAO = iRmVmManageServerDAO;
	}

//新增加检查检查虚机管理服务器是否已经存在
	@Override
	public int selectVMServer(RmVmManageServerPo rmVmManageServerPo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return iRmVmManageServerDAO.selectVMServer(rmVmManageServerPo);
	}
	
	/**
	 * 根据ID获取虚机管理服务器ID
	 * @param vmMsId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public RmVmManageServerPo getRmVmMsIpById(String vmMsId) throws RollbackableBizException {
		return iRmVmManageServerDAO.findObjectByID("getRmVmMsIpById", vmMsId);
	}
	/**
	 * 根据ip地址获取虚机管理服务器ID
	 * @param vmMsId
	 * @return
	 */
	public List<RmVmManageServerPo> getRmVmMsIpByVmMsIp(String vmMsHostIp) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("vmMsHostIp", vmMsHostIp);
		return iRmVmManageServerDAO.findListByParam("getRmVmMsIpByVmMsIp", map);
	}

}
