package com.git.cloud.resmgt.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmVmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceAndModelVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceVo;
import com.git.cloud.resmgt.common.model.vo.CmHostDatastoreRefVo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.model.vo.CmStorageVo;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.cloud.shiro.model.CertificatePo;

public interface ICmDeviceService extends IService {

	/**
	 * @Title: getCmDeviceHostInfo
	 * @Description: 通过物理机名和序列号，查询主机信息。
	 * @param method
	 *            操作方法
	 * @param deviceName
	 *            物理机名
	 * @param sn
	 *            序列号
	 * @return
	 * @throws Exception
	 *             List<T> 返回类型
	 * @throws
	 */
	public <T extends BaseBO> List<T> getCmDeviceHostInfo(String bizId, String deviceName, String sn);

	/**
	 * @Title: getCmDeviceHostInfo
	 * @Description: 根据主机id，获取物理主机信息。
	 * @param bizId
	 * @return T 返回类型
	 * @throws
	 */
	public <T extends BaseBO> T getCmDeviceHostInfo(String bizId);


	/**
	 * @Title: getCmDeviceVMInfo
	 * @Description: 根据虚机id，获取虚机信息。
	 * @param bizId
	 * @return T 返回类型
	 * @throws
	 */
	public <T extends BaseBO> T getCmDeviceVMInfo(String bizId);

	/**
	 * @param paginationParam
	 * @return
	 * @Description 获取设备信息，并分页展示到前台
	 */
	public Pagination<CmDeviceVo> getDevicePagination(PaginationParam paginationParam);


	/**
	 * 删除多条设备信息
	 * 
	 * @param ids
	 * @Description
	 */
	public String deleteCmDeviceList(String[] ids);


	/**
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @Description 通过id获取一条datastore信息
	 */
	public CmDatastoreVo selectCmDatastoreById(String id) throws RollbackableBizException;

	/**
	 * @param map
	 * @Description 修改设备主机信息
	 */
	public void updateCmDeviceHost(Map map);

	/**
	 * @param map
	 * @Description
	 */
	public void updateCmDeviceStorage(Map map);


	public CmHostDatastoreRefVo selectCmHostDatastoreRefVo(String id) throws RollbackableBizException;

	/**
	 * 
	 * @param paginationParam
	 * @return
	 * @Description查询存储设备下datastore信息列表
	 */
	public Pagination<CmDatastoreVo> getDatastorePagination(PaginationParam paginationParam);
	/**
	 * 
	 * @param cmDatastoreVo
	 * @throws RollbackableBizException
	 * @Description 根据storgeid删除datastore信息
	 */
	public void deleteCmDatastoreVo(CmDatastoreVo cmDatastoreVo) throws RollbackableBizException;
	/**
	 * 
	 * @param ids
	 * @throws RollbackableBizException
	 * @Description根据id删除datastore信息
	 */
	public void deleteCmDatastoreVoById(String[] ids) throws RollbackableBizException;
    /**
     * 
     * @param cmDatastoreVo
     * @throws RollbackableBizException
     * @Description根据iddatastor修改信息
     */
	public void updateCmDatastoreVo(CmDatastoreVo cmDatastoreVo) throws RollbackableBizException;
	/**
	 * 
	 * @param map
	 * @Description添加存储设备信息
	 */
	public void saveCmDeviceStorage(Map map);
	/**
	 * 
	 * @param map
	 * @Description添加服务器信息
	 */
	public void saveCmHost(Map map);
	/**
	 * 
	 * @param id
	 * @return
	 * @Description查询服务器信息
	 */
	public List selectCmDeviceHost(String id);
	
	/**
	 * 查询物理设备下的所有一上线的虚拟机
	 * @param hostId
	 * @return
	 */
	public List<CmDevicePo> findVMByHostId(String hostId);
	/**
	 * 
	 * @param id
	 * @return
	 * @Description查询存储设备信息
	 */
	public List selectCmStorage(String id);
	public String createHostInterface(String vcHostUserName, String vcHostPwd,
			String vcHostIP, String clusterName, String hostId,
			String hostpasswd, String license, String hostName,
			String hostMgrIp, String dcId) throws Exception;
	/**
	 * 
	 * @return
	 * @throws RollbackableBizException 
	 * @Description根据sn号查询设备对象
	 */
	public CmDeviceAndModelVo selectSNForTrim(String sn) throws RollbackableBizException;
	
	public CmDeviceAndModelVo selectDeviceNameForTrim(String deviceName) throws RollbackableBizException;
	
	public CmDeviceAndModelVo selectSeatIdForTrim(String seatId)  throws RollbackableBizException;
	
	
	public String deviceInPool(List<HashMap<String, String>> h_list,
			List<HashMap<String, String>> d_list,
			List<HashMap<String, String>> p_list,
			List<HashMap<String, String>> ds_list, String dcName,
			String poolName, String cdpName, String clusterName)
			throws RollbackableBizException;
	
	public String deviceInPoolLog(ArrayList<HashMap<String, String>> h_list,
			ArrayList<HashMap<String, String>> d_list,
			ArrayList<HashMap<String, String>> p_list,
			ArrayList<HashMap<String, String>> ds_list, String dcName,
			String poolName, String cdpName, String clusterName);
	public String saveCmDeviceStorageLog(HashMap map);
	
	public String saveCmHostLog(HashMap map);
	
	public String updateCmDeviceHostLog(HashMap map) throws RollbackableBizException;
	
	public String updateCmDeviceStorageLog(HashMap map) throws RollbackableBizException;

	public String deleteCmDeviceListLog(String[] ids) throws RollbackableBizException;

	public CertificatePo  findCertificatePath()throws RollbackableBizException;

	public List<CmDeviceVo> getCmDevicePoNumber()throws RollbackableBizException;//新增加验证物理机数目
	
	public String isDeleteCmDeviceList(String[] ids);//判断是否删除
	
	public void updatemDeviceHostIsInvc(Map map);
	
	public List<Object> isGetRelevanceInfoList(String relevanceInfo) ;

	public boolean selectCmClusterHostInfos(String id)throws RollbackableBizException;//查询集群下是否含有主机
	
	/**
	 * 查询最新的MODEL_ID
	 */
	public String selectBpmModelId(String modelName) throws RollbackableBizException ;

	public String createNasInterface(String vcHostIP,String vcHostUserName, String vcHostPwd,String hostMgrIp,String name,String path,String mgrIp,String dcId);
	
	/**
	 * 查询最新的MODEL_NAME
	 */
    public String selectModelName() throws RollbackableBizException ;
	
    public RmVirtualTypePo findVirtualTypeById(String clusterId) throws RollbackableBizException ;
    
    public CmDevicePo selectCmDevicePoById(String id) throws RollbackableBizException ;
    
    /**
     * 
     * @param id 设备ID
     * @return true:是物理机 false:不是物理机 
     * @throws RollbackableBizException
     */
    public boolean isCmHost(String id)throws RollbackableBizException ;
    /**
     * 
     * @param id 设备ID
     * @return true:是虚拟机 false:不是虚拟机 
     * @throws RollbackableBizException
     */
    public boolean isCmVm(String id)throws RollbackableBizException ;
    
    /**
     * 更新设备运行状态
     * @param id 
     * @throws RollbackableBizException
     */
    public void updateCmdeviceRunningState(CmDevicePo cmDevicePo)throws RollbackableBizException ;
    /**
     * 
     * @param hostId 物理机ID
     * @param datastoreId 关联的datastoreId
     * @throws RollbackableBizException
     */
    public void deleteDatastoreInfo(String hostId,String datastoreId)throws RollbackableBizException;
    
    public void saveDefaultDatastore(CmDevicePo cmDevicePo)throws RollbackableBizException;
    
    public CmDevicePo getDefaultDatastore(String hostId)throws RollbackableBizException;
    /**
	 * 查询物理机的默认Datastore信息
	  *
	  * @param  id
	  * @return CmHostDatastorePo    返回类型
	 */
    public CmHostDatastorePo selectCmHostDeafultDatastore(String id)throws RollbackableBizException;
    /**
     * 查询虚拟机的Datastore信息
      *
      * @param @param id
      * @return CmVmDatastorePo    返回类型
     */
    public CmVmDatastorePo selectCmVmDatastore(String id)throws RollbackableBizException;
    
    public CmVmPo findPowerInfoByVmId(String vmId) throws RollbackableBizException;

	public String findVmIdByName(String deviceName) throws RollbackableBizException;

	public String findHostIpById(String id) throws RollbackableBizException;
	/**
	 * 主要是物理机进入维护模式/退出维护模式时使用
	 * @throws RollbackableBizException
	 */
	public String getPmRunningState(String hostId) throws RollbackableBizException;
	
	public CmDevicePo findDeviceById(String id) throws RollbackableBizException;
	
	public List<CmDevicePo> findDeviceDefaultDatastore(String datastoreId)throws RollbackableBizException; 
	
	public void updateCmDeviceLparId(CmDevicePo device) throws RollbackableBizException;
	
	public void updateCmDeviceLparName(CmDevicePo device) throws RollbackableBizException;
	
	public void updateCmDeviceProfileName(CmDevicePo device) throws RollbackableBizException;
	
	public CmDevicePo findNewDevcieByHostId()throws RollbackableBizException;
	/**
	 * 根据分配ip段的前三位，查找端口组对应的物理机，物理机中，内存剩余多的进行资源分配
	 * @param ipInfo
	 * @return
	 * @throws RollbackableBizException
	 */
	public CmHostPo findDistribHost(String ipInfo)throws RollbackableBizException;
	
	/**
	 * 根据虚拟机ID获取虚拟化类型
	 * @param vmId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String findVmTypeCodeByVmId(String vmId) throws RollbackableBizException;
	/**
	 * 获取物理机配置信息cpu、mem、disk
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<CmHostPo> getHostConfigure(PaginationParam paginationParam)throws RollbackableBizException;
	/**
	 * 获取设备挂载的磁盘
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<RmDeviceVolumesRefPo> getRmDeviceVolumesRefPoList(PaginationParam paginationParam)throws RollbackableBizException;
	/**
	 * 保存单个，机器挂载的磁盘信息
	 * @param rmDeviceVolumesRefPoList
	 * @throws RollbackableBizException
	 */
	public void saveRmDeviceVolumesRefPo(RmDeviceVolumesRefPo rmDeviceVolumesRefPo)throws RollbackableBizException;
	/**
	 * 卸载卷时，删除关联关系
	 * @param ref
	 * @throws RollbackableBizException
	 */
	public void deleteRmDeviceVolumesRef(RmDeviceVolumesRefPo ref)throws RollbackableBizException;
	/**
	 * 虚机id，挂载卷状态为未挂载，卷大小等于挂载的数据卷大小，并且卷id为空，查找数据
	 * @param map
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmDeviceVolumesRefPo getRmDeviceVolumesRefByMap( HashMap<String,String> map)throws RollbackableBizException;
	/**
	 * 更新磁盘对应的卷Id
	 * @param ref
	 * @throws RollbackableBizException
	 */
	public void updateRmDvRefVolumeId(RmDeviceVolumesRefPo ref)throws RollbackableBizException;

	public void saveOpenstackVolume(VolumeDetailVo volumeVo);

	public List<String> findVolumeTypeList(String vmId) throws RollbackableBizException;
	
	public void updateVmProjectId(String vmId,String projectId)throws RollbackableBizException;
	/**
	 * 查询openstack的虚拟机ip信息
	 * @param bizId
	 * @return
	 */
	public <T extends BaseBO> T getCmDeviceVMInfoOpenstack(String bizId)throws Exception; 
}
