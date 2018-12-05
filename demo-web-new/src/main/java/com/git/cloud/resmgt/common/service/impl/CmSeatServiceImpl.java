package com.git.cloud.resmgt.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.model.ZtreeIconEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.dao.ICmSeatDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.service.ICmSeatService;

/**
 * @ClassName:CmSeatServiceImpl
 * @Description:位置管理
 * @author lizhizhong
 * @date 2014-12-17 下午1:41:55
 *
 *
 */
public class CmSeatServiceImpl implements ICmSeatService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ROOT_NAME = "位置信息";

	private static final String ROOT_ID = "-1";

	private ICmSeatDAO iCmSeatDAO;
	private IRmDatacenterDAO rmDatacenterDAO;

	@Override
	public List<CommonTreeNode> getCmSeatTreeList() throws RollbackableBizException {

		List<CommonTreeNode> result_json_nodes = new ArrayList<CommonTreeNode>();

		CommonTreeNode rootNode = new CommonTreeNode();
		rootNode.setId(ROOT_ID);
		rootNode.setName(ROOT_NAME);
		rootNode.setOpen(true);
		rootNode.setParent(true);
		rootNode.setFirstExpand(false);
		rootNode.setBizType("xd");// 使用向导

		List<RmDatacenterPo> dcs = rmDatacenterDAO.getDataCenters();
		for (RmDatacenterPo dc : dcs) {
			CommonTreeNode dcNode = new CommonTreeNode();
			dcNode.setId(dc.getId());
			dcNode.setName(dc.getDatacenterCname());
			dcNode.setOpen(false);
			dcNode.setParent(true);
			dcNode.setFirstExpand(true);
			dcNode.setBizType("dc");
			dcNode.setIcon(ZtreeIconEnum.DATA_CENTER.getIcon());
			rootNode.addChildNode(dcNode);
		}

		result_json_nodes.add(rootNode);
		return result_json_nodes;
	}

	@Override
	public List<CommonTreeNode> getComputerRoomByDcId(String dcId) throws RollbackableBizException {
		List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
		List<CmSeatPo> cmSeatPos = iCmSeatDAO.getComputerRoomByDcId(dcId);

		for (CmSeatPo cmSeatPo : cmSeatPos) {
			CommonTreeNode ComputerRoom = new CommonTreeNode();
			ComputerRoom.setId(cmSeatPo.getId());
			ComputerRoom.setName(cmSeatPo.getSeatName());
			ComputerRoom.setOpen(false);
			ComputerRoom.setParent(true);
			ComputerRoom.setFirstExpand(true);
			ComputerRoom.setBizType("room");
			ComputerRoom.setIcon(ZtreeIconEnum.ROOM.getIcon());
			treeNodes.add(ComputerRoom);
		}

		return treeNodes;
	}

	@Override
	public List<CommonTreeNode> getComputerCabinetByRoomCode(String roomCode) throws RollbackableBizException {
		List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
		List<CmSeatPo> cmSeatPos = iCmSeatDAO.getComputerCabinetByRoomCode(roomCode);

		for (CmSeatPo cmSeatPo : cmSeatPos) {
			CommonTreeNode computerCabinet = new CommonTreeNode();
			computerCabinet.setId(cmSeatPo.getId());
			computerCabinet.setName(cmSeatPo.getSeatName());
			computerCabinet.setOpen(false);
			computerCabinet.setParent(true);
			computerCabinet.setFirstExpand(true);
			computerCabinet.setBizType("cabinet");
			computerCabinet.setIcon(ZtreeIconEnum.CABINET.getIcon());
			treeNodes.add(computerCabinet);
		}

		return treeNodes;
	}

	@Override
	public List<CommonTreeNode> getUByCabinetCode(String cabinetCode) throws RollbackableBizException {
		List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
		List<CmSeatPo> cmSeatPos = iCmSeatDAO.getUByCabinetCode(cabinetCode);

		for (CmSeatPo cmSeatPo : cmSeatPos) {
			CommonTreeNode U = new CommonTreeNode();
			U.setId(cmSeatPo.getId());
			U.setName(cmSeatPo.getSeatName());
			U.setOpen(false);
			U.setParent(false);
			U.setFirstExpand(true);
			U.setBizType("u");
			U.setIcon(ZtreeIconEnum.U.getIcon());
			treeNodes.add(U);
		}

		return treeNodes;
	}

	@Override
	public CmSeatPo getComputerRoomByRoomId(String roomId) throws RollbackableBizException {
		CmSeatPo cmSeatPo = iCmSeatDAO.getComputerRoomByRoomId(roomId);
		return cmSeatPo;
	}

	@Override
	public CmSeatPo getComputerCabinetByCabinetId(String cabinetId) throws RollbackableBizException {
		CmSeatPo cmSeatPo = iCmSeatDAO.getComputerCabinetByCabinetId(cabinetId);
		return cmSeatPo;
	}

	@Override
	public CmSeatPo getUByUId(String uId) throws RollbackableBizException {
		CmSeatPo cmSeatPo = iCmSeatDAO.getUByUId(uId);
		return cmSeatPo;
	}

	@Override
	public String updateSeatInfo(CmSeatPo cmSeatPo) {
		String rtnMsg = "";

		try {
			int count = iCmSeatDAO.checkCode(cmSeatPo);
			if (count > 0) {
				rtnMsg = "编码已存在。";
			} else {
				cmSeatPo.setUpdateDateTime(new Date());
				iCmSeatDAO.updateSeatInfo(cmSeatPo);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

		return rtnMsg;
	}

	@Override
	public String deleteUInfo(String Uid) {
		String rtnMsg = "";
		try {
			int count = iCmSeatDAO.selectCountU(Uid);
			if (count > 0) {
				rtnMsg = "该U位已被使用。";
			} else {
				iCmSeatDAO.deleteUInfo(Uid);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

		return rtnMsg;
	}

	@Override
	public String deleteCabinetInfo(String CabinetId) {
		String rtnMsg = "";
		try {
			int count = iCmSeatDAO.selectCountUtoCabinet(CabinetId);
			if (count > 0) {
				rtnMsg = "该机柜下有被使用的U位。";
			} else {
				List<CmSeatPo> cmSeatPoList = iCmSeatDAO.selectUByCabinetId(CabinetId);
				// 批量删除机柜下的U位。
				iCmSeatDAO.deleteUInfoBatch(cmSeatPoList);
				// 删除机柜信息。
				iCmSeatDAO.deleteCabinetInfo(CabinetId);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

		return rtnMsg;
	}

	@Override
	public String deleteRoomInfo(String roomId) {
		String rtnMsg = "";
		try {
			int count = iCmSeatDAO.selectCountUtoRoom(roomId);
			if (count > 0) {
				rtnMsg = "该机房下有被使用的U位。";
			} else {
				// 通过机房Id查找该机房下的机柜列表
				List<CmSeatPo> cmSeatPoList = iCmSeatDAO.selectCabinetByRoomId(roomId);
				// 批量删除机柜下的U位。
				iCmSeatDAO.deleteUInfoBatch2(cmSeatPoList);
				// 批量删除机房下的机柜。
				iCmSeatDAO.deleteCabinetInfoBatch(cmSeatPoList);
				// 删除机房信息。
				iCmSeatDAO.deleteRoomInfo(roomId);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}

		return rtnMsg;
	}

	@Override
	public Map<String, String> insertSeatInfoOfRoom(CmSeatPo cmSeatPo) throws RollbackableBizException {
		Map<String, String> rtnInfo = new HashMap<String, String>();
		String rtnMsg;
		int count = iCmSeatDAO.checkCode(cmSeatPo);
		if (count > 0) {
			rtnMsg = "编码已存在。";
			rtnInfo.put("rtnMsg", rtnMsg);
		} else {

			CmSeatPo cmSeatPoTemp = new CmSeatPo();
			String id = UUIDGenerator.getUUID();
			cmSeatPoTemp = cmSeatPo;
			cmSeatPoTemp.setId(id);
			cmSeatPoTemp.setIsActive(IsActiveEnum.YES.getValue());
			cmSeatPoTemp.setCreateDateTime(new Date());

			iCmSeatDAO.insertSeatInfo(cmSeatPo);
			rtnInfo.put("id", id);
		}

		return rtnInfo;
	}

	@Override
	public Map<String, String> insertSeatInfoOfCabinet(CmSeatPo cmSeatPo) throws RollbackableBizException {
		Map<String, String> rtnInfo = new HashMap<String, String>();
		String rtnMsg;
		int count = iCmSeatDAO.checkCode(cmSeatPo);
		if (count > 0) {
			rtnMsg = "编码已存在。";
			rtnInfo.put("rtnMsg", rtnMsg);
		} else {
			CmSeatPo cmSeatPoTemp = new CmSeatPo();
			String id = UUIDGenerator.getUUID();
			cmSeatPoTemp = cmSeatPo;
			cmSeatPoTemp.setId(id);
			cmSeatPoTemp.setIsActive(IsActiveEnum.YES.getValue());
			cmSeatPoTemp.setCreateDateTime(new Date());

			iCmSeatDAO.insertSeatInfo(cmSeatPo);
			rtnInfo.put("id", id);
		}

		return rtnInfo;
	}

	@Override
	public Map<String, String> insertSeatInfoOfU(CmSeatPo cmSeatPo) throws RollbackableBizException {
		Map<String, String> rtnInfo = new HashMap<String, String>();
		String rtnMsg;
		int count = iCmSeatDAO.checkCode(cmSeatPo);
		if (count > 0) {
			rtnMsg = "编码已存在。";
			rtnInfo.put("rtnMsg", rtnMsg);
		} else {
			String id = UUIDGenerator.getUUID();
			CmSeatPo cmSeatPoTemp = new CmSeatPo();
			cmSeatPoTemp = cmSeatPo;
			cmSeatPoTemp.setId(id);
			cmSeatPoTemp.setIsActive(IsActiveEnum.YES.getValue());
			cmSeatPoTemp.setCreateDateTime(new Date());

			iCmSeatDAO.insertSeatInfo(cmSeatPo);
			rtnInfo.put("id", id);
		}

		return rtnInfo;
	}

	@Override
	public Pagination<CmDeviceHostShowBo> getCmseatAllHostCanRelevanceInfo(PaginationParam paginationParam) throws RollbackableBizException {
		Pagination<CmDeviceHostShowBo> cmDeviceHostShowBoList = iCmSeatDAO
				.getCmseatAllHostCanRelevanceInfo(paginationParam);
		return cmDeviceHostShowBoList;
	}

	@Override
	public void updateDeviceSeat(String relevanceInfo) throws RollbackableBizException {
		JSONObject jSONObject = JSONObject.fromObject(relevanceInfo);
		Object relevanceInfoMap = JSONObject.toBean(jSONObject, HashMap.class);
		iCmSeatDAO.updateDeviceSeat((HashMap<String, String>) relevanceInfoMap);
	}

	public ICmSeatDAO getiCmSeatDAO() {
		return iCmSeatDAO;
	}

	public void setiCmSeatDAO(ICmSeatDAO iCmSeatDAO) {
		this.iCmSeatDAO = iCmSeatDAO;
	}

	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}

}
