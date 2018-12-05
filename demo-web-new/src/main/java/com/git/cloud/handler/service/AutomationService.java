package com.git.cloud.handler.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.vo.ScriptFullPathVo;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.HandlerReturnObject;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.handler.vo.VcenterRfDeivceVo;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;

/**
 * 自动化子流程服务接口
 * @author zhuzhaoyong
 *
 */
/**
 * @ClassName:AutomationService
 * @Description:TODO
 * @author zhuzhaoyong
 * @date 2014-10-14 下午3:15:22
 *
 *
 */
public interface AutomationService {
	/**
	 * 读取系统配置表的参数值
	 * @param paraName	参数名
	 * @return
	 * @throws BizException
	 */
	public String getAppParameter(String paraName) throws BizException;
	/**
	 * 读取资源请求包含的设备id列表，以设备id升序排序
	 * @param instanceId
	 * @return
	 * @throws BizException
	 */
	public List<String> getDeviceIdsSort(String rrinfoId) throws BizException;
	
	/**
	 * 构造一个流程节点中所有设备执行后的的返回结果
	 * @param retObj
	 * @return
	 * @throws BizException
	 */
	public String makeInstanceReturnString(HandlerReturnObject retObj) throws BizException;
	
	/**
	 * 读取资源请求对应的数据中心
	 * @param rrInfoId	资源请求id
	 * @return
	 * @throws BizException
	 */
	public RmDatacenterPo getDatacenter(String rrInfoId) throws BizException;
	
	/**
	 * 读取云服务属性信息
	 * @param rrinfoId	资源请求id
	 * @return
	 * @throws BizException
	 */
	public Map<String, String> getSrAttrInfoByRrinfoId(String rrinfoId) throws BizException;
	
	/**
	 * 读取脚本信息
	 * @param scriptId	脚本id
	 * @return
	 * @throws BizException
	 */
	public ScriptModelVO getScript(String scriptId) throws BizException;
	
	/**
	 * 读取脚本全路径，包括路径和名称
	 * @param scriptId
	 * @return
	 * @throws BizException
	 */
	public String getScriptFullPath(String scriptId) throws BizException;
	
	/**
	 * 读取脚本的参数，并排序
	 * @param scriptId
	 * @return
	 * @throws BizException
	 */
	public List<String> getScriptParasSort(String scriptId) throws BizException;
	
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
	 * @return
	 * @throws BizException
	 */
	public List<RmVmManageServerPo> getVmManagementServers(String datacenterId, String type) throws BizException;
	
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
	public List<RmGeneralServerPo> getGeneralServers(String datacenterId, String type) throws BizException;
	
	/**
	 * 读取资源的密码，解密后的
	 * @param resourceId	资源id
	 * @param userName		用户名
	 * @return
	 * @throws BizException
	 */
	public String getPassword(String resourceId, String userName) throws BizException;
	
	/**
	 * 读取脚本包集合
	 * @param packNames	名称，多个包名称以“,”分割
	 * @return
	 * @throws BizException
	 */
	public List<Map<String, ?>> getPackagePackNames(List<String> packNames) throws BizException;
	
	/**
	 * 读取云服务参数信息
	 * @param rrinfoId
	 * @return
	 * @throws BizException
	 */
	public Map<String, String> getServiceAttr(String rrinfoId) throws BizException;
	
	public Map<String, String> getServiceAttrDevice(String rrinfoId, String deviceId) throws BizException;
	
	/**
	 * 读取基于设备的云服务参数信息
	 * @param rrinfoId
	 * @return
	 * @throws BizException
	 */
	public Map<String, String> getServiceAttrForDevice(String rrinfoId, String deviceId) throws BizException;
	
	/**
	 * 读取虚拟机列表
	 * @param deviceIdList	虚拟机id
	 * @return
	 * @throws BizException
	 */
	public List<CmVmPo> getVms(List<String> deviceIdList) throws BizException;
	
	/**
	 * 读取虚拟机的管理机列表，vcenter或hmc，每个设备返回一个对象
	 * @param deviceIdList
	 * @return
	 * @throws BizException
	 */
	public List<RmVmManageServerPo> getMgrServerInfoByDeviceId(List<String> deviceIdList) throws BizException;
	
	/**
	 * 读取虚拟机的管理机，vcenter或hmc，每个设备返回一个对象
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	public RmVmManageServerPo getMgrServerInfoByDeviceId(String deviceId) throws BizException;
	
	
	/**
	 * 根据资源请求读取镜像
	 * @param rrinfoId	资源请求
	 * @return
	 * @throws BizException
	 */
	public CloudImage getImage(String rrinfoId) throws BizException;
	
	/**
	 * 读取device
	 * @param devId
	 * @return
	 * @throws BizException
	 */
	public CmDevicePo getDevice(String devId) throws BizException;
	
	/**
	 * 读取虚拟机的datastore名称
	 * @param vmId
	 * @return
	 * @throws BizException
	 */
	public String getDatastoreName(String vmId) throws BizException;
	
	/**
	 * 根据脚本包和脚本模块读取脚本文件名列表
	 * @param packId
	 * @param moduleId
	 * @return
	 * @throws BizException
	 */
	public List<String> getScriptNameByPackageIdAndModuleId(String packId, String moduleId) throws BizException;
	
	/**
	 * 读取资源请求
	 * @param rrinfoId
	 * @return
	 * @throws BizException
	 */
	public BmSrRrinfoPo getRrinfo(String rrinfoId) throws BizException;
	
	/**
	 * 读取云服务信息
	 * @param serviceId
	 * @return
	 * @throws BizException
	 */
	public CloudServicePo getService(String serviceId) throws BizException;
	
	/**
	 * 读取vm
	 * @param vmId
	 * @return
	 * @throws BizException
	 */
	public CmVmPo getVm(String vmId) throws BizException;
	
	/**
	 * 读取设备管理ip
	 * @param devId
	 * @return
	 * @throws BizException
	 */
	public String getDeviceManagementIp(String devId) throws BizException;
	
	/**
	 * 计算cpu核数
	 * @param cpuNum
	 * @return
	 * @throws BizException
	 */
	public int getCpuCoreNum(int cpuNum) throws BizException;
	
	/**
	 * 根据资源请求读取关联设备列表
	 * @param rrinfoId
	 * @return
	 * @throws BizException
	 */
	public List<BmSrRrVmRefPo> getSrRrVmRefs(String rrinfoId) throws BizException;
	
	/**
	 * 读取物理机的datastore
	 * @param hostId
	 * @return
	 * @throws BizException
	 * @throws Exception 
	 */
	public String getHostDatastore(String hostId) throws Exception;
	
	/**
	 * 根据资源申请Id获取Vcenter的信息
	 * @Title: getVcenterRfDeivce
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @return
	 * @field: @throws BizException
	 * @return List<VcenterRfDeivceVo>
	 * @throws
	 */
	public List<VcenterRfDeivceVo> getVcenterRfDeivce(String rrinfoId) throws BizException;
	
	/**
	 * 
	 * @Title: findDcIdenById
	 * @Description: TODO
	 * @field: @param datacenterId
	 * @field: @return
	 * @field: @throws BizException
	 * @return String
	 * @throws
	 */
	public String findDcIdenById(String datacenterId) throws BizException;
	
	/**
	 * @Title: getCmHostPo
	 * @Description: TODO
	 * @field: @param hostId
	 * @field: @return
	 * @field: @throws BizException
	 * @return CmHostPo
	 * @throws
	 */
	public CmHostPo getCmHostPo(String hostId) throws BizException;
	
	/**
	 * 读取管理机列表
	 * @Title: getManageServers
	 * @Description: TODO
	 * @field: @param vmId
	 * @field: @return
	 * @field: @throws BizException
	 * @return List<RmVmManageServerPo>
	 * @throws
	 */
	public List<RmVmManageServerPo> getManageServers(String vmId) throws BizException;
	/**
	 * @Title: getScriptFullPathVo
	 * @Description: TODO
	 * @field: @param scriptId
	 * @field: @return
	 * @field: @throws BizException
	 * @return ScriptFullPathVo
	 * @throws
	 */
	ScriptFullPathVo getScriptFullPathVo(String scriptId) throws BizException;
	/**
	 * @Title: getPassword
	 * @Description: TODO
	 * @field: @param resourceId
	 * @field: @return
	 * @field: @throws BizException
	 * @return CmPasswordPo
	 * @throws
	 */
	CmPasswordPo getPassword(String resourceId) throws BizException;
	/**
	 * @Title: getDatacenterByDeviceIdInResPool
	 * @Description: TODO
	 * @field: @param deviceId
	 * @field: @return
	 * @field: @throws BizException
	 * @return RmDatacenterPo
	 * @throws
	 */
	RmDatacenterPo getDatacenterByDeviceIdInResPool(String deviceId) throws BizException;
	/**
	 * @Title: getSingleStringValueBySql
	 * @Description: TODO
	 * @field: @param sql
	 * @field: @return
	 * @field: @throws Exception
	 * @return String
	 * @throws
	 */
	String getSingleStringValueBySql(String sql) throws Exception;
	/**
	 * @Title: getHandlerReturnString
	 * @Description: TODO
	 * @field: @param returnObjs
	 * @field: @return
	 * @field: @throws Exception
	 * @return String
	 * @throws
	 */
	String getHandlerReturnString(HandlerReturnObject returnObjs) throws Exception;
	/**
	 * 查询设备服务器信息
	 * ip，用户名，密码，设备名
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	RmGeneralServerVo findDeviceServerInfo(String deviceId) throws Exception;
	
	AppSysPO findAppInfoBySrId(String srId) throws Exception;
	
	public String getGYRXHostDatastore(String hostId) throws Exception;
	
	public OpenstackVmParamVo findOpenstackVmParamByVmId(String vmId) throws Exception;
	
}