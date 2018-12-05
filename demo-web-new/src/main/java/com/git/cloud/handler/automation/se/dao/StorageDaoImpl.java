package com.git.cloud.handler.automation.se.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.automation.se.po.CmSwitchPo;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.FcsPo;
import com.git.cloud.handler.automation.se.po.StorageFCPortGroupPo;
import com.git.cloud.handler.automation.se.po.StoragePo;
import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.automation.se.po.VsanPo;
import com.git.cloud.handler.automation.se.vo.FabricVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.google.common.collect.Maps;

public class StorageDaoImpl  extends CommonDAOImpl implements StorageDAO{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Map<String, ?>> findStorageDevInfoBySuId(String su_id,
			String model_code) {
		StringBuffer sb = new StringBuffer();
		sb.append("select dev.device_id as STORAGE_DEV_ID,");
		sb.append(" dev.sn        AS STORAGE_DEV_SN,");
		sb.append(" dic.dicname   AS STORAGE_MODEL");
		sb.append(" from cm_device dev, admin_dic dic");
		sb.append(" where 1 = 1");
		sb.append(" and dic.dic_type_code = 'DEVICE_MODEL'");
		sb.append(" and dev.device_model = dic.dic_code");
		sb.append(" and dev.is_active = 'Y'");
		sb.append(" and dic.is_active = 'Y'");
		sb.append(" and dev.su_id = " + su_id);
		if(!"".equals(model_code)&&null!=model_code){
			sb.append(" and dic.dic_code = '"+model_code+"'");
		}
		return null;
	}

	public List<Map<String, ?>> findUsedStorageDevInfoBySuId(Long su_id,
			long deviceId) {
		StringBuffer sb = new StringBuffer();
		//sb.append("select distinct (storage_dev.device_id),");         
		//sb.append(" storage_dev.sn AS STORAGE_DEV_SN,");                
		sb.append("select distinct(dic.dicname) AS STORAGE_MODEL,");
		sb.append(" dic.dic_code as STORAGE_MODEL_CODE");
		sb.append(" from cm_storage_assign_res assign,");        
		sb.append(" cm_device             storage_dev,");               
		sb.append(" cm_device             host_dev,");                  
		sb.append(" admin_dic             dic");                        
		sb.append(" where 1 = 1");                                      
		sb.append(" and storage_dev.su_id = "+su_id);                    
		sb.append(" and dic.dic_type_code = 'DEVICE_MODEL'");           
		sb.append(" and storage_dev.device_model = dic.dic_code");      
		sb.append(" and storage_dev.device_id = assign.storage_id");    
		sb.append(" and storage_dev.is_active = 'Y'");
		sb.append(" and host_dev.device_id = "+deviceId);                   
		sb.append(" and host_dev.device_id = assign.host_id"); 
		sb.append(" and dic.is_active = 'Y'");
		return null;
	}

	@Override
	public StorageMgrVo findStorageMgrInfoBySuId(String su_id) {
		/*StringBuffer sb = new StringBuffer();
		sb.append(" select ");//su.su_id      as SU_ID,");                      
		sb.append(" mgr.managerip as SMISISERVER_IP,");                    
		sb.append(" mgr.namespace as NAME_SPACE,");                        
		sb.append(" pw.username   as USER_NAME,");                         
		sb.append(" pw.password   as USER_PASSWD");                        
		sb.append(" from rm_storage_su su, cm_storagemgr mgr, cm_pw pw");  
		sb.append(" where 1 = 1");                                         
		sb.append(" and su.storagemgr_id = mgr.storagemgr_id");            
		sb.append(" and pw.resource_id = mgr.storagemgr_id");              
		sb.append(" and su_id = "+su_id);
		*/
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("suId", su_id);
		return (StorageMgrVo)getSqlMapClientTemplate().queryForObject("se.findSeMgrInfoBySu", paramMap);
	}
	
	
	@Override
	public StorageMgrVo findStorageMgrInfoByFabric(String fabricId) {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("fabricId", fabricId);
		return (StorageMgrVo)getSqlMapClientTemplate().queryForObject("se.findSwitchMgrInfoByFabric", paramMap);
	}
	@Override
	public StorageSuPo findSuInfoBySN(String sn) {
		try {
			return this.findObjectByID("se.querySuBySn", sn);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		} 
		return null;
	}

	@Override
	public List<Map<String, ?>> findUsedStorageDevInfoBySuId(String su_id,
			String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StorageSuPo> findStorageSuByPoolId(String poolId) {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("poolId", poolId);
		return this.findListByParam("se.getSuByPool", paramMap);
	}

	@Override
	public StorageDeviceVo findStorageDevBySn(String sn){
		StorageDeviceVo vo = new StorageDeviceVo();
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("sn", sn);
		try {
			vo = this.findObjectByMap("se.findStoragetDevVoBySn", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return vo;
	}
	@Override
	public StorageSuPo findStorageSuById(String suId){
		
		StorageSuPo po = new StorageSuPo();
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("suId", suId);
		try {
			po = this.findObjectByMap("se.getSuPoById", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return po;
	}

	@Override
	public FabricVo findFabricVoById(String fabricId){
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("fabricId", fabricId);
		try {
			return this.findObjectByMap("se.getFabricVoById", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}	
	@Override
	public FabricPo findFabricPoByMgrId(String storageMgrId){
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("storageMgrId", storageMgrId);
		try {
			return this.findObjectByMap("se.getFabricPoMgrId", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}
	@Override
	public FabricPo findFabricPoByName(String fabricName){
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("fabricName", fabricName);
		try {
			return this.findObjectByMap("se.getFabricPoByName", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}


	@Override
	public VsanPo findVsanPoByParams(Map<String,String> paramMap){
		try {
			return this.findObjectByMap("se.getVsanPoByP", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}
	@Override
	public StoragePo getLastSelectedPortGroup(String sn){

		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("sn", sn);
		try {
			return this.findObjectByMap("se.getLastFCPortGroup", paramMap);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}
	@Override
	public List<StorageFCPortGroupPo> getStorageFcPortListBySN(String sn){
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("sn", sn);
		return this.findListByParam("se.getSeFCPortList", paramMap);
	}

	@Override
	public List<VsanPo> findVsanInfoByFabircId(String fabricId) {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("fabricId", fabricId);
		return this.findListByParam("se.getVsanPoByParams", paramMap);
	}
	
	@Override
	public List<FcsPo> findFcsPoListByFabricName(Map<String,String> paramMap){
		return this.findListByParam("se.getFcsPoListByFabricName", paramMap);
	}
	
	@Override
	public List<CmSwitchPo> findSwitchList(Map<String,String> paramMap){
		return this.findListByParam("se.getSwitchList", paramMap);
	}
	@Override
	public List<CmLun> findLunList(Map<String,String> paramMap){
		return this.findListByParam("se.getLunList", paramMap);
	}

	@Override
	public void lunInfoBatchupdate(List<CmLun> cmLunList) {
		try {
			// 开始批处理
			getSqlMapClient().startBatch();

			for (CmLun cmlun : cmLunList) {
				// 插入操作
				getSqlMapClient().update("se.batchUpdateLun", cmlun);
			}
			// 执行批处理
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}
	
	@Override
	public void batchUpdateLunStatusAndType(List<CmLun> cmLunList) {
		try {
			for (CmLun cmlun : cmLunList) {
				// 插入操作
				getSqlMapClient().update("se.updateLunStatusAndType", cmlun);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}
	
	public void batchUpdateLunName(List<CmLun> cmLunList) {
		try {
			for (CmLun cmlun : cmLunList) {
				// 插入操作
				getSqlMapClient().update("se.updateLunName", cmlun);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}
	
	public List<CmLun> getUsedLunsByResourceIds(List<String> deviceIdList) {
		Map<String, String> map = Maps.newHashMap();
		String deviceIds = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			deviceIds += ",'" + deviceIdList.get(i) + "'";
		}
		map.put("deviceIds", deviceIds.substring(1));
		return this.findListByParam("se.getUsedLunsByResourceIds", map);
	}
	
	@Override
	public StorageMgrVo findStorageMgrInfoByParam(Map<String,String> map) {
		return (StorageMgrVo)getSqlMapClientTemplate().queryForObject("se.findSeMgrInfoByMap", map);
	}

	@Override
	public StorageFCPortGroupPo findFCPortByWWN(String wwn){
		Map<String,String> map = Maps.newHashMap();
		map.put("wwn", wwn);
		return (StorageFCPortGroupPo)getSqlMapClientTemplate().queryForObject("se.findFcportInfoByMap", map);
	}
	@Override
	public List<String> findOccupyLunBysn(String sn) {
//		List<String> list = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append("select l.lun_name as name ");
		builder.append("from cm_device d, cm_lun l ");
		builder.append("where d.device_id=l.storage_id and d.sn=? ");
		builder.append("and l.lun_status=1");
		return null;
	}
	
	public CmLun findLunByPath(String path) {
		CmLun lun = null;
		HashMap<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("lunPath", path);
		List<CmLun> lunList = this.findListByParam("findLunByPath", paramMap);
		if(lunList != null && lunList.size() > 0) {
			lun = lunList.get(0);
		}
		return lun;
	}
}
