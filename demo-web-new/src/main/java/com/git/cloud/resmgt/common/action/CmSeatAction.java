package com.git.cloud.resmgt.common.action;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.common.service.ICmSeatService;
import com.google.common.collect.Maps;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmSeatAction.java
 * @Package com.git.cloud.resmgt.common.action
 * @Description: 主机位置信息维护
 * @author lizhizhong
 * @date 2014-10-21 下午2:38:25
 * @version V1.0
 */
public class CmSeatAction extends BaseAction<Object> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 1L;
	private ICmSeatService iCmSeatService;

	private String dcId;
	private String roomCode;
	private String cabinetCode;
	private String roomId;
	private String cabinetId;
	private String uId;
	private CmSeatPo cmSeatPo;

	/* 用于关联主机的相关信息 */
	private String relevanceInfo;

	/**
	 * @Title: cmseatView
	 * @Description: 展示U位信息树
	 * @return String 返回类型
	 * @throws
	 */
	public String cmseatView() {
		return SUCCESS;
	}

	/**
	 * @Title: getCmSeatTreeList
	 * @Description: 获取位置信息数据，供前台页面加载为树。
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void getCmSeatTreeList() {
		List<CommonTreeNode> treeDatas;
		try {
			treeDatas = iCmSeatService.getCmSeatTreeList();
			arrayOut(treeDatas);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: getComputerRoomByDcId
	 * @Description: 根据数据中心id，获取机房信息列表
	 * @throws
	 */
	public void getComputerRoomByDcId() {
		List<CommonTreeNode> treeDatas;
		try {
			treeDatas = iCmSeatService.getComputerRoomByDcId(getDcId());
			arrayOut(treeDatas);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public void getComputerCabinetByRoomCode() {
		List<CommonTreeNode> treeDatas;
		try {
			treeDatas = iCmSeatService.getComputerCabinetByRoomCode(getRoomCode());
			arrayOut(treeDatas);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public void getUByCabinetCode() {
		List<CommonTreeNode> treeDatas;
		try {
			treeDatas = iCmSeatService.getUByCabinetCode(getCabinetCode());
			arrayOut(treeDatas);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public void getComputerRoomByRoomId() {
		try {
			CmSeatPo cmSeatPo = iCmSeatService.getComputerRoomByRoomId(getRoomId());
			jsonOut(cmSeatPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public void getComputerCabinetByCabinetId() {
		try {
			CmSeatPo cmSeatPo = iCmSeatService.getComputerCabinetByCabinetId(getCabinetId());
			jsonOut(cmSeatPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public void getUByUId() {
		try {
			CmSeatPo cmSeatPo = iCmSeatService.getUByUId(getUId());
			jsonOut(cmSeatPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: deleteSeatInfo
	 * @Description: 根据主键删除没有使用的机房、机柜、U位信息。同时检查该U位上是否已经存在主机，若存在则返回提示信息。
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void deleteUInfo() throws Exception {
		String rtnMsg = iCmSeatService.deleteUInfo(this.getUId());
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: deleteCabinetInfo
	 * @Description: 删除机柜信息，以及机柜下属的所有U位信息
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void deleteCabinetInfo() throws Exception {
		String rtnMsg = iCmSeatService.deleteCabinetInfo(this.getCabinetId());
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: deleteRoomInfo
	 * @Description: 删除机房信息，以及机房下属的所有机柜，U位信息
	 * @throws Exception
	 *             void 返回类型
	 * @throws
	 */
	public void deleteRoomInfo() throws Exception {
		String rtnMsg = iCmSeatService.deleteRoomInfo(this.getRoomId());
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @throws RollbackableBizException
	 * @Title: insertSeatInfoOfRoom
	 * @Description: 新建机房 void 返回类型
	 * @throws
	 */
	public void insertSeatInfoOfRoom() throws RollbackableBizException {

		Map map = iCmSeatService.insertSeatInfoOfRoom(this.getCmSeatPo());

		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @throws RollbackableBizException
	 * @Title: insertSeatInfoOfCabinet
	 * @Description: 新建机柜 void 返回类型
	 * @throws
	 */
	public void insertSeatInfoOfCabinet() throws RollbackableBizException {
		Map map = iCmSeatService.insertSeatInfoOfCabinet(this.getCmSeatPo());

		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @throws RollbackableBizException
	 * @Title: insertSeatInfoOfU
	 * @Description: 新建U位信息 void 返回类型
	 * @throws
	 */
	public void insertSeatInfoOfU() throws RollbackableBizException {
		Map map = iCmSeatService.insertSeatInfoOfU(this.getCmSeatPo());

		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: getCmseatAllHostCanRelevanceInfo
	 * @Description: 抽取未关联位置的所有指定数据中心下的主机。
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void getCmseatAllHostCanRelevanceInfo() throws RollbackableBizException {
		// 获取可关联的所有物理机信息。
		try {
			this.jsonOut(iCmSeatService.getCmseatAllHostCanRelevanceInfo(this.getPaginationParam()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @Title: updateSeatInfo
	 * @Description: 修改位置信息。
	 * @throws
	 */
	public void updateSeatInfo() {
		String rtnMsg = iCmSeatService.updateSeatInfo(this.getCmSeatPo());
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		try {
			jsonOut(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	/**
	 * @throws RollbackableBizException
	 * @Title: updateDeviceSeat
	 * @Description: 将主机关联到相应的位置上 void 返回类型
	 * @throws
	 */
	public void updateDeviceSeat() throws RollbackableBizException {
		iCmSeatService.updateDeviceSeat(getRelevanceInfo());
	}

	public ICmSeatService getiCmSeatService() {
		return iCmSeatService;
	}

	public void setiCmSeatService(ICmSeatService iCmSeatService) {
		this.iCmSeatService = iCmSeatService;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getCabinetCode() {
		return cabinetCode;
	}

	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getUId() {
		return uId;
	}

	public void setUId(String uId) {
		this.uId = uId;
	}

	public CmSeatPo getCmSeatPo() {
		return cmSeatPo;
	}

	public void setCmSeatPo(CmSeatPo cmSeatPo) {
		this.cmSeatPo = cmSeatPo;
	}

	public String getRelevanceInfo() {
		return relevanceInfo;
	}

	public void setRelevanceInfo(String relevanceInfo) {
		this.relevanceInfo = relevanceInfo;
	}

}
