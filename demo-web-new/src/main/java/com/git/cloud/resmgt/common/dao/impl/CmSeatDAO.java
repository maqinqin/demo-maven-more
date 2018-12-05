package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.resmgt.common.dao.ICmSeatDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;

public class CmSeatDAO extends CommonDAOImpl implements ICmSeatDAO {

	private static Logger logger = LoggerFactory.getLogger(CmSeatDAO.class);

	@Override
	public List<CmSeatPo> getComputerRoomByDcId(String dcId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getComputerRoomByDcId start---------------------");
		List<CmSeatPo> list = this.findByID("getComputerRoomByDcId", dcId);
		logger.info("CmSeatDAO---------------getComputerRoomByDcId end---------------------");
		return list;
	}

	@Override
	public List<CmSeatPo> getComputerCabinetByRoomCode(String roomCode) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getComputerCabinetByRoomCode start---------------------");
		List<CmSeatPo> list = this.findByID("getComputerCabinetByRoomCode", roomCode);
		logger.info("CmSeatDAO---------------getComputerCabinetByRoomCode end---------------------");
		return list;
	}

	@Override
	public List<CmSeatPo> getUByCabinetCode(String cabinetCode) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getUByCabinetCode start---------------------");
		List<CmSeatPo> list = this.findByID("getUByCabinetCode", cabinetCode);
		logger.info("CmSeatDAO---------------getUByCabinetCode end---------------------");
		return list;
	}

	@Override
	public CmSeatPo getComputerRoomByRoomId(String roomId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getComputerRoomByRoomId start---------------------");
		String id = roomId;

		CmSeatPo cmSeatPo = getCmSeatInfo(id);
		logger.info("CmSeatDAO---------------getComputerRoomByRoomId end---------------------");
		return cmSeatPo;
	}

	@Override
	public CmSeatPo getComputerCabinetByCabinetId(String cabinetId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getComputerCabinetByCabinetId start---------------------");
		String id = cabinetId;
		CmSeatPo cmSeatPo = getCmSeatInfo(id);
		logger.info("CmSeatDAO---------------getComputerCabinetByCabinetId end---------------------");
		return cmSeatPo;
	}

	@Override
	public CmSeatPo getUByUId(String uId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getUByUId start---------------------");
		String id = uId;
		CmSeatPo cmSeatPo = getCmSeatInfo(id);
		logger.info("CmSeatDAO---------------getUByUId end---------------------");
		return cmSeatPo;
	}

	public CmSeatPo getCmSeatInfo(String id) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getCmSeatInfo start---------------------");
		CmSeatPo cmSeatPo = this.findObjectByID("getSeatInfoById", id);
		logger.info("CmSeatDAO---------------getCmSeatInfo end---------------------");
		return cmSeatPo;
	}

	@Override
	public void insertSeatInfo(CmSeatPo cmSeatPo) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------insertSeatInfo start---------------------");
		this.save("insertSeatInfo", cmSeatPo);
		logger.info("CmSeatDAO---------------insertSeatInfo end---------------------");
	}

	@Override
	public void updateSeatInfo(CmSeatPo cmSeatPo) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------updateSeatInfo start---------------------");
		this.update("updateSeatInfo", cmSeatPo);
		logger.info("CmSeatDAO---------------updateSeatInfo end---------------------");
	}

	@Override
	public int selectCountU(String Uid) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------selectCountU start---------------------");
		List count = this.findByID("selectCountU", Uid);
		logger.info("CmSeatDAO---------------selectCountU end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public void deleteUInfo(String Uid) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------deleteUInfo start---------------------");
		this.deleteForIsActive("deleteUInfo", Uid);
		logger.info("CmSeatDAO---------------deleteUInfo end---------------------");
	}

	@Override
	public int selectCountUtoCabinet(String CabinetId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------selectCountUtoCabinet start---------------------");
		List count = this.findByID("selectCountUtoCabinet", CabinetId);
		logger.info("CmSeatDAO---------------selectCountUtoCabinet end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public int selectCountUtoRoom(String RoomId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------selectCountUtoRoom start---------------------");
		List count = this.findByID("selectCountUtoRoom", RoomId);
		logger.info("CmSeatDAO---------------selectCountUtoRoom end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public void deleteRoomInfo(String RoomId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------deleteRoomInfo start---------------------");
		this.deleteForIsActive("deleteRoomInfo", RoomId);
		logger.info("CmSeatDAO---------------deleteRoomInfo end---------------------");
	}

	@Override
	public void deleteCabinetInfo(String CabinetId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------deleteCabinetInfo start---------------------");
		this.deleteForIsActive("deleteCabinetInfo", CabinetId);
		logger.info("CmSeatDAO---------------deleteCabinetInfo end---------------------");
	}

	@Override
	public void deleteUInfoBatch(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(cmSeatPoList)) {
			this.batchUpdate("deleteUInfoBatch", cmSeatPoList);
		}
	}

	@Override
	public void deleteUInfoBatch2(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(cmSeatPoList)) {
			this.batchUpdate("deleteUInfoBatch2", cmSeatPoList);
		}
	}

	@Override
	public void deleteCabinetInfoBatch(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException {
		if (CollectionUtil.hasContent(cmSeatPoList)) {
			this.batchUpdate("deleteCabinetInfoBatch", cmSeatPoList);
		}
	}

	@Override
	public List<CmSeatPo> selectUByCabinetId(String CabinetId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------selectUByCabinetId start---------------------");
		List<CmSeatPo> cmSeatPoList = this.findByID("selectUByCabinetId", CabinetId);
		logger.info("CmSeatDAO---------------selectUByCabinetId end---------------------");
		return cmSeatPoList;
	}

	@Override
	public List<CmSeatPo> selectCabinetByRoomId(String RoomId) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------selectCabinetByRoomId start---------------------");
		List<CmSeatPo> cmSeatPoList = this.findByID("selectCabinetByRoomId", RoomId);
		logger.info("CmSeatDAO---------------selectCabinetByRoomId end---------------------");
		return cmSeatPoList;
	}

	public <T extends BaseBO> List<T> getAllHostCanRelevanceInfo(String method) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getAllHostCanRelevanceInfo start---------------------");
		List<T> list = this.findAll(method);
		logger.info("CmSeatDAO---------------getAllHostCanRelevanceInfo end---------------------");
		return list;
	}

	@Override
	public Pagination<CmDeviceHostShowBo> getCmseatAllHostCanRelevanceInfo(PaginationParam paginationParam) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------getCmseatAllHostCanRelevanceInfo start---------------------");
		Pagination<CmDeviceHostShowBo> list = this.pageQuery("getCmseatAllHostCanRelevanceTotal",
				"getCmseatAllHostCanRelevancePage", paginationParam);
		logger.info("CmSeatDAO---------------getCmseatAllHostCanRelevanceInfo end---------------------");
		return list;
	}

	@Override
	public void updateDeviceSeat(HashMap<String, String> map) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------updateDeviceSeat start---------------------");
		getSqlMapClientTemplate().update("updateDeviceSeat", map);
		logger.info("CmSeatDAO---------------updateDeviceSeat end---------------------");
	}

	@Override
	public int checkCode(CmSeatPo cmSeatPo) throws RollbackableBizException {
		logger.info("CmSeatDAO---------------checkCode start---------------------");
		List count = this.findListByParam("checkCode", cmSeatPo);
		logger.info("CmSeatDAO---------------checkCode end---------------------");
		return (Integer) count.get(0);
	}

}
