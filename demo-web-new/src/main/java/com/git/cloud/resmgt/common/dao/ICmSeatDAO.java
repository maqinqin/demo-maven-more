package com.git.cloud.resmgt.common.dao;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;

public interface ICmSeatDAO extends ICommonDAO {

	/**
	 * @Title: getComputerRoomByDcId
	 * @Description: 根据数据中心ID，查询机房信息列表
	 * @param dcId
	 *            数据中心ID
	 * @return
	 * @throws RollbackableBizException
	 *             List<CmSeatPo> 返回类型
	 * @throws
	 */
	public List<CmSeatPo> getComputerRoomByDcId(String dcId) throws RollbackableBizException;

	/**
	 * @Title: getComputerCabinetByRoomCode
	 * @Description: 根据机房code，查询机柜信息列表
	 * @param roomCode
	 * @return
	 * @throws RollbackableBizException
	 *             List<CmSeatPo> 返回类型
	 * @throws
	 */
	public List<CmSeatPo> getComputerCabinetByRoomCode(String roomCode) throws RollbackableBizException;

	/**
	 * @Title: getUByCabinetCode
	 * @Description: 根据 机柜code，查询U位信息列表
	 * @param cabinetCode
	 * @return
	 * @throws RollbackableBizException
	 *             List<CmSeatPo> 返回类型
	 * @throws
	 */
	public List<CmSeatPo> getUByCabinetCode(String cabinetCode) throws RollbackableBizException;

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
	 * @Title: insertSeatInfo
	 * @Description: 添加位置信息：机房信息，机柜信息，U位信息
	 * @param cmSeatPo
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void insertSeatInfo(CmSeatPo cmSeatPo) throws RollbackableBizException;

	/**
	 * @Title: updateSeatInfo
	 * @Description: 修改位置信息：机房信息，机柜信息，U位信息
	 * @param cmSeatPo
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void updateSeatInfo(CmSeatPo cmSeatPo) throws RollbackableBizException;

	/**
	 * @Title: selectCountU
	 * @Description: 检查U位是否被使用
	 * @param Uid
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectCountU(String Uid) throws RollbackableBizException;

	/**
	 * @Title: deleteUInfo
	 * @Description: 删除没有使用的机房、机柜、U位信息。将U位的isActive状态置为"N"
	 * @param Uid
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteUInfo(String Uid) throws RollbackableBizException;

	/**
	 * @Title: selectCountUtoCabinet
	 * @Description: 检查指定机柜下的U位是否被使用。
	 * @param CabinetId
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectCountUtoCabinet(String CabinetId) throws RollbackableBizException;

	/**
	 * @Title: selectCountUtoRoom
	 * @Description: 检查指定机房下的U位是否被使用。
	 * @param RoomId
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int selectCountUtoRoom(String RoomId) throws RollbackableBizException;

	/**
	 * @Title: deleteCabinetInfo
	 * @Description: 删除没有使用的机房、机柜、U位信息。将机柜的isActive状态置为"N"
	 * @param CabinetId
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteCabinetInfo(String CabinetId) throws RollbackableBizException;

	/**
	 * @Title: deleteRoomInfo
	 * @Description: 删除机房信息。
	 * @param RoomId
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteRoomInfo(String RoomId) throws RollbackableBizException;

	/**
	 * @Title: deleteUInfoBatch
	 * @Description: 批量删除U位信息
	 * @param cmSeatPoList
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteUInfoBatch(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException;

	/**
	 * @Title: deleteUInfoBatch2
	 * @Description: 批量删除U位信息
	 * @param cmSeatPoList
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteUInfoBatch2(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException;

	/**
	 * @Title: deleteCabinetInfoBatch
	 * @Description: 批量删除机柜信息
	 * @param cmSeatPoList
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void deleteCabinetInfoBatch(List<CmSeatPo> cmSeatPoList) throws RollbackableBizException;

	/**
	 * @Title: selectUByCabinetId
	 * @Description: 根据机柜id，查询指定机柜下的所有U位信息
	 * @param CabinetId
	 * @return
	 * @throws RollbackableBizException
	 *             List<CmSeatPo> 返回类型
	 * @throws
	 */
	public List<CmSeatPo> selectUByCabinetId(String CabinetId) throws RollbackableBizException;

	/**
	 * @Title: selectCabinetByRoomId
	 * @Description: 根据机房Id,获取机柜列表信息
	 * @param RoomId
	 * @return
	 * @throws RollbackableBizException
	 *             List<CmSeatPo> 返回类型
	 * @throws
	 */
	public List<CmSeatPo> selectCabinetByRoomId(String RoomId) throws RollbackableBizException;

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
	 * @Description: 关联物理机到位置
	 * @param HashMap
	 *            <String, String> map
	 * @throws RollbackableBizException
	 *             void 返回类型
	 * @throws
	 */
	public void updateDeviceSeat(HashMap<String, String> map) throws RollbackableBizException;

	/**
	 * @Title: checkCode
	 * @Description: 检查位置编码的存在与否
	 * @param CmSeatPo
	 * @return
	 * @throws RollbackableBizException
	 *             int 返回类型
	 * @throws
	 */
	public int checkCode(CmSeatPo cmSeatPo) throws RollbackableBizException;
}
