package com.git.cloud.resmgt.common.action;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.IRmVmManageServerService;
import com.google.common.collect.Maps;

/**
 * @ClassName:RmVmManageServerAction
 * @Description:虚拟管理机信息
 * @author lizhizhong
 * @date 2014-12-17 下午1:33:36
 *
 *
 */
public class RmVmManageServerAction extends BaseAction<Object> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 1L;
	private RmVmManageServerPo rmVmManageServerPo;

	private IRmVmManageServerService iRmVmManageServerService;

	/* 平台类型id */
	private String platformId;
	/* 虚机管理服务器id */
	private String serverId;

	public String vmManageServerView() {
		return SUCCESS;
	}

	/**
	 * @Title: getVmTypeNameInfo
	 * @Description: 获取虚拟机类型信息列表
	 * @throws
	 */
	public void getVmTypeNameInfo() {

		try {
			List<RmVirtualTypePo> rmVirtualTypePoList = iRmVmManageServerService.getVmTypeNameInfo(getPlatformId());
			arrayOut(rmVirtualTypePoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

	}

	/**
	 * @Title: getPlatformInfo
	 * @Description: 获取平台类型类型信息列表 void 返回类型
	 * @throws
	 */
	public void getPlatformInfo() {

		try {
			List<RmPlatformPo> RmPlatformPoList = iRmVmManageServerService.getPlatformInfo();
			arrayOut(RmPlatformPoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

	}

	/**
	 * @Title: getvmManageServerList
	 * @Description: 查询虚机管理服务器信息，分页展示到前台
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void getvmManageServerList() throws Exception {
		this.jsonOut(iRmVmManageServerService.getvmManageServerList(this.getPaginationParam()));
	}

	/**
	 * @Title: getvmManageServerInfo
	 * @Description: 查询指定虚机管理服务器信息，并在前台对其信息修改。
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void getvmManageServerInfo() throws Exception {
		this.jsonOut(iRmVmManageServerService.getvmManageServerInfo(this.getServerId()));
	}

	public void checkServerName() throws Exception {
		ObjectOut(iRmVmManageServerService.checkServerName(this.getRmVmManageServerPo()));
	}

	public void checkServerIp() throws Exception {
		ObjectOut(iRmVmManageServerService.checkServerIp(this.getRmVmManageServerPo()));
	}

	/**
	 * @Title: insertvmManageServer
	 * @Description: 接受前台虚机管理服务器信息，添加或更新到数据库
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	//修改虚机管理保存或修改日志记录问题
	public void insertvmManageServer() throws Exception {
		String rtnMsg = "";
		int server = 0;
		try {

			// 检查虚机管理服务器是否已经存在
			server = iRmVmManageServerService.selectVMServer(rmVmManageServerPo);

			// 虚机管理服务器已经存在，执行更新操作
			if (server > 0) {
				 rtnMsg=iRmVmManageServerService.updatevmManageServer(rmVmManageServerPo);
			} else {
				// 虚机管理服务器不存在时，执行插入操作
				rtnMsg=iRmVmManageServerService.insertOfvmManageServer(rmVmManageServerPo);
			}
			Map<String, String> map = Maps.newHashMap();
			map.put("rtnMsg", rtnMsg);
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Description删除设备记录
	 */
	public void deleteVMServerList() {

		List<String> rtnids = iRmVmManageServerService.deleteVMServerList(rmVmManageServerPo.getId());
		try {
			arrayOut(rtnids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public IRmVmManageServerService getiRmVmManageServerService() {
		return iRmVmManageServerService;
	}

	public void setiRmVmManageServerService(IRmVmManageServerService iRmVmManageServerService) {
		this.iRmVmManageServerService = iRmVmManageServerService;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public RmVmManageServerPo getRmVmManageServerPo() {
		return rmVmManageServerPo;
	}

	public void setRmVmManageServerPo(RmVmManageServerPo rmVmManageServerPo) {
		this.rmVmManageServerPo = rmVmManageServerPo;
	}

}
