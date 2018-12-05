package com.git.cloud.resmgt.common.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmVmSynInfoPo;

/**
 * 同步物理机、虚拟机状态DAO
  * @author WangJingxin
  * @date 2016年5月11日 上午11:20:40
  *
 */
public interface ISyncVmInfoDAO extends ICommonDAO  {

	/**
	 * 更新物理机、虚拟机运行状态
	  * @param  pMap
	  * @throws RollbackableBizException    
	  * @return void  
	 */
	public void saveSyncVmRunningState(Map<String,String> pMap) throws RollbackableBizException;
	
	/**
	* @Title: findSyncVmInfoByManageIp  
	* @Description: 查询vCenter下的虚拟机信息
	* @param vmName
	* @return List<CmVmSynInfoPo>    返回类型  
	 */
	public List<CmVmSynInfoPo> findSyncVmInfoByManageIp(String manageIp) throws RollbackableBizException;
	
	/**
	* @Title: saveSyncVmDatastore  
	* @Description: 更新虚拟机所在datastore id
	* @param pMap
	 */
	public void saveSyncVmDatastore(Map<String,String> pMap) throws RollbackableBizException;
}
