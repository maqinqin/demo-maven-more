package com.git.cloud.resmgt.common.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;

public interface ICmSeatService extends IService {

	/**
	 * @Title: getCmSeatTreeList
	 * @Description: 加载位置信息树
	 * @return
	 * @throws RollbackableBizException
	 *             List<CommonTreeNode> 返回类型
	 * @throws
	 */
	public List<CommonTreeNode> getCmSeatTreeList() throws RollbackableBizException;

	/**
	 * @Title: getComputerRoomByDcId
	 * @Description: 根据[数据中心id]查询机房信息列表
	 * @param dcId
	 *            数据中心id
	 * @return
	 * @throws RollbackableBizException
	 *             List<CommonTreeNode> 返回类型
	 * @throws
	 */
	public List<CommonTreeNode> getComputerRoomByDcId(String dcId) throws RollbackableBizException;

	/**
	 * @Title: getComputerCabinetByRoomCode
	 * @Description: 根据[机房code]查询机柜信息列表
	 * @param roomCode
	 * @return
	 * @throws RollbackableBizException
	 *             List<CommonTreeNode> 返回类型
	 * @throws
	 */
	public List<CommonTreeNode> getComputerCabinetByRoomCode(String roomCode) throws RollbackableBizException;

	/**
	 * @Title: getUByCabinetCode
	 * @Description: 根据[机柜code]查询U位信息列表
	 * @param cabinetCode
	 * @return
	 * @throws RollbackableBizException
	 *             List<CommonTreeNode> 返回类型
	 * @throws
	 */
	public List<CommonTreeNode> getUByCabinetCode(String cabinetCode) throws RollbackableBizException;

	/**
	 * @Title: getComputerRoomByRoomId
	 * @Description: 根据 机房id，获取机房信息
	 * @param roomId
	 * @return
	 * @throws RollbackableBizException
	 *             CmSeatPo 返回类型
	 * @throws
	 */
	public CmSeatPo getComputerRoomByRoomId(String roomId) throws RollbackableBizException;

	/**
	 * @Title: getComputerCabinetByCabinetId
	 * @Description: 根据机柜id，获取机柜信息
	 * @param cabinetId
	 * @return
	 * @throws RollbackableBizException
	 *             CmSeatPo 返回类型
	 * @throws
	 */
	public CmSeatPo getComputerCabinetByCabinetId(String cabinetId) throws RollbackableBizException;

	/**
	 * @Title: getUByUId
	 * @Description: 根据U位id，获取U位信息
	 * @param uId
	 * @return
	 * @throws RollbackableBizException
	 *             CmSeatPo 返回类型
	 * @throws
	 */
	public CmSeatPo getUByUId(String uId) throws RollbackableBizException;

	/**
	 * @Title: updateSeatInfo
	 * @Description: 修改位置信息：机房信息，机柜信息，U位信息
	 * @param cmSeatPo
	 * @throws RollbackableBizException
	 *             String 返回类型
	 * @throws
	 */
	public String updateSeatInfo(CmSeatPo cmSeatPo);

	/**
	 * @Title: deleteUInfo
	 * @Description: 删除没有使用的机房、机柜、U位信息。将isActive状态置为"N"
	 * @param Uid
	 * @throws RollbackableBizException
	 *             String 返回类型
	 * @throws
	 */
	public String deleteUInfo(String Uid) throws RollbackableBizException;

	/**
	 * @Title: deleteCabinetInfo
	 * @Description: 根据机柜id，删除机柜信息
	 * @param CabinetId
	 * @return
	 * @throws RollbackableBizException
	 *             String 返回类型
	 * @throws
	 */
	public String deleteCabinetInfo(String CabinetId) throws RollbackableBizException;

	/**
	 * @Title: deleteRoomInfo
	 * @Description: 根据机房id，删除机房信息
	 * @param CabinetId
	 * @return
	 * @throws RollbackableBizException
	 *             String 返回类型
	 * @throws
	 */
	public String deleteRoomInfo(String CabinetId) throws RollbackableBizException;

	/**
	 * @Title: insertSeatInfoOfRoom
	 * @Description: 添加机房信息
	 * @param cmSeatPo
	 * @return
	 * @throws RollbackableBizException
	 *             Map<String, String> 返回类型
	 * @throws
	 */
	public Map<String, String> insertSeatInfoOfRoom(CmSeatPo cmSeatPo) throws RollbackableBizException;

	/**
	 * @Title: insertSeatInfoOfCabinet
	 * @Description: 添加机柜信息
	 * @param cmSeatPo
	 * @return
	 * @throws RollbackableBizException
	 *             Map<String, String> 返回类型
	 * @throws
	 */
	public Map<String, String> insertSeatInfoOfCabinet(CmSeatPo cmSeatPo) throws RollbackableBizException;

	/**
	 * @Title: insertSeatInfoOfU
	 * @Description: 添加U位信息
	 * @param cmSeatPo
	 * @return
	 * @throws RollbackableBizException
	 *             Map<String, String> 返回类型
	 * @throws
	 */
	public Map<String, String> insertSeatInfoOfU(CmSeatPo cmSeatPo) throws RollbackableBizException;

	/**
	 * @Title: getCmseatAllHostCanRelevanceInfo
	 * @Description: 获取可关联的主机列表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 *             Pagination<CmDeviceHostShowBo> 返回类型
	 * @throws
	 */
	public Pagination<CmDeviceHostShowBo> getCmseatAllHostCanRelevanceInfo(PaginationParam paginationParam) throws RollbackableBizException;

	/**
	 * @Title: updateDeviceSeat
	 * @Description: 在位置上关联主机
	 * @param relevanceInfo
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void updateDeviceSeat(String relevanceInfo) throws RollbackableBizException;
}
