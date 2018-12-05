package com.git.cloud.handler.dao;
import java.util.List;

import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.handler.vo.VmDeviceVo;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;

public interface AutomationDAO extends ICommonDAO {
	
	/**
	 * 读取资源请求对应的数据中心
	 * @param rrInfoId	资源请求id
	 * @return
	 * @throws BizException
	 */
	public RmDatacenterPo getDatacenter(String rrInfoId) throws BizException;
	
	/**
	 * 读取路由信息
	 * @param datacenterId
	 * @return
	 * @throws BizException
	 */
	public List<CmRoutePo> getRoutes(String datacenterId) throws BizException;
	
	/**
	 * 读取device所在数据中心，deviceid可能为物理机或虚拟机
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	public RmDatacenterPo getDatacenterByDeviceId(String deviceId) throws BizException;
	
	/**
	 * 读取服务申请信息
	 * @param srId
	 * @return
	 * @throws BizException
	 */
	public BmSrPo getSrById(String srId) throws BizException;
	
	/**
	 * 读取资源管理机信息
	 * @param datacenterId	数据中心id
	 * @param type		管理机类型code
	 * @return
	 * @throws BizException
	 */
	public RmVmManageServerPo getVmManagementServer(String datacenterId, String type) throws BizException;
	
	/**
	 * 读取资源管理机信息
	 * @param datacenterId	数据中心id
	 * @param type		管理机类型code
	 * @throws BizException
	 */
	public List<RmVmManageServerPo> getVmManagementServers(String datacenterId, String type) throws BizException;
	
	/**
	 * 读取虚拟机列表
	 * @param deviceIdList	虚拟机id
	 * @return
	 * @throws BizException
	 */
	public List<CmVmPo> getVms(List<String> deviceIdList) throws BizException;
	
	/**
	 * 读取虚拟机的datastore名称
	 * @param vmId
	 * @return
	 * @throws BizException
	 */
	public String getDatastoreName(String vmId) throws BizException;
	
	/**
	 * 读取辅助服务器信息
	 * @Title: getGaneralServers
	 * @Description: TODO
	 * @field: @param datacenterId	
	 * @field: @param type
	 * @field: @return
	 * @field: @throws BizException
	 * @return List<RmGaneralServerPo>
	 * @throws
	 */
	public List<RmGeneralServerPo> getGaneralServers(String datacenterId, String type) throws BizException;
	
	/**
	 * 根据资源申请Id获取Vcenter相关信息
	 * @Title: getVmVcenter
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @field: @throws BizException
	 * @return List<VmDeviceVo>
	 */
	public List<VmDeviceVo> getVmVcenter(String rrinfoId) throws BizException;

	/**
	 * @Title: getDatacenterByDeviceIdInResPool
	 * @field: @param deviceId
	 * @field: @throws BizException
	 * @return RmDatacenterPo
	 */
	RmDatacenterPo getDatacenterByDeviceIdInResPool(String deviceId) throws BizException;
	
	/**
	 * 根据虚机ID获取位置信息
	 * @param deviceId
	 * @return
	 */
	public String getSeatCodeByVmId(String deviceId) throws BizException;
	
	/**
	 * 根据物理机ID获取位置信息
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	public String getSeatCodeByHostId(String deviceId) throws BizException;
	
	/**
	 * 根据位置编码获取父级编码
	 * @param seatCode
	 * @return
	 * @throws BizException
	 */
	public String getParentSeatCodeByCode(String seatCode) throws BizException;
	
	/**
	 * 根据设备ID获取服务器信息
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	public RmGeneralServerVo findDeviceServerInfo(String deviceId) throws BizException;
	
	public AppSysPO findAppInfoBySrId(String srId) throws Exception;
	
	public OpenstackVmParamVo findOpenstackVmParamByVmId(String vmId) throws Exception;
	
}