package com.git.cloud.handler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.handler.dao.BizParamInstDAO;
import com.git.cloud.handler.po.BizParamInstPo;

public class BizParamInstServiceImpl implements BizParamInstService {
	
	private static Logger log = LoggerFactory.getLogger(BizParamInstServiceImpl.class);
	
	private BizParamInstDAO bizParamInstDAOImpl;
	
	public void setBizParamInstDAOImpl(BizParamInstDAO bizParamInstDAOImpl) {
		this.bizParamInstDAOImpl = bizParamInstDAOImpl;
	}

	@Override
	public List<BizParamInstPo> getInstanceParas(String instanceId)
			throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("instanceId", instanceId);
		return bizParamInstDAOImpl.findListByParam("getInstanceParasByInstanceId", paramMap);
	}

	@Override
	public List<BizParamInstPo> getInstanceParas(String instanceId,
			String deviceId) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("instanceId", instanceId);
		paramMap.put("deviceId", deviceId);
		return bizParamInstDAOImpl.findListByParam("getInstanceParasByDeviceId", paramMap);
	}
	
	@Override
	public Map<String, String> getInstanceParasMap(String instanceId, String deviceId) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("instanceId", instanceId);
		paramMap.put("deviceId", deviceId);
		List<BizParamInstPo> bizParams = getInstanceParas(instanceId, deviceId);
		Map<String, String> deviceMap = new HashMap<String, String>();
		for (BizParamInstPo bizParamInstPo : bizParams) {
			deviceMap.put(bizParamInstPo.getParamKey(), bizParamInstPo.getParamValue());
		}
		return deviceMap;
	}

	@Override
	public void saveParas(List<BizParamInstPo> paras) throws BizException {
		if(paras != null && paras.size() > 0) {
			bizParamInstDAOImpl.batchInsert("bizParamBatchInsert", paras);
		}
	}
	
	@Override
	public void saveOrUpdateParas(List<BizParamInstPo> paras) throws BizException {
		if(paras != null && paras.size() > 0) {
			String instanceId, deviceId, paramKey;
			HashMap<String, String> paramMap = new HashMap<String, String> ();
			List<BizParamInstPo> paramList;
			for (BizParamInstPo bizParamInstPo : paras) {
				instanceId = bizParamInstPo.getFlowInstId();
				deviceId = bizParamInstPo.getDeviceId();
				paramKey = bizParamInstPo.getParamKey();
				paramMap.put("flowInstId", instanceId);
				paramMap.put("deviceId", deviceId);
				paramMap.put("paramKey", paramKey);
				paramList = bizParamInstDAOImpl.findListByParam("getBizParamInst", paramMap);
				log.info("instanceId:" + instanceId + ",deviceId:" + deviceId + ",paramKey:" + paramKey + "共查询出:" + (paramList == null ? 0 : paramList.size()));
				if(paramList != null && paramList.size() > 0) {
					this.deleteParamInstForSameKey(deviceId, instanceId, paramKey);
					log.info("saveOrUpdateParas delete");
				}
			}
			bizParamInstDAOImpl.batchInsert("bizParamBatchInsert", paras);
		}
	}
	
	@Override
	public void deleteParamInstsOfFlow(String instanceId) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("instanceId", instanceId);
		bizParamInstDAOImpl.deleteForParam("deleteParamInstsOfFlow", paramMap);
	}
	
	@Override
	public void deleteParamInstPo(String deviceId,String flowInstId,String nodeId,String paramKey) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("deviceId", deviceId);
		paramMap.put("flowInstId", flowInstId);
		paramMap.put("nodeId", nodeId);
		paramMap.put("paramKey", paramKey);
		bizParamInstDAOImpl.deleteForParam("deleteParamInstPo", paramMap);
	}
	
	@Override
	public void deleteParamInstForSameKey(String deviceId,String flowInstId,String paramKey) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("deviceId", deviceId);
		paramMap.put("flowInstId", flowInstId);
		paramMap.put("paramKey", paramKey);
		bizParamInstDAOImpl.deleteForParam("deleteParamInstForSameKey", paramMap);
	}
	
	@Override
	public void savePara(BizParamInstPo bizParamInstPo) throws BizException {
		bizParamInstDAOImpl.save("bizParamInsert", bizParamInstPo);
	}
	
	@Override
	public List<BizParamInstPo> getBizParamInst(String deviceId,String flowInstId,String paramKey) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("deviceId", deviceId);
		paramMap.put("flowInstId", flowInstId);
		paramMap.put("paramKey", paramKey);
		return bizParamInstDAOImpl.findListByParam("getBizParamInst", paramMap);
	}
	
	@Override
	public List<?> getParamInitBySql(String sql) throws Exception{
	    List<?> list = bizParamInstDAOImpl.queryForObject(sql);
		return list;
	}
	
	@Override
	public Pagination<BizParamInstPo> findBizParamInstPage(
			PaginationParam paginationParam) throws RollbackableBizException{
		return bizParamInstDAOImpl.findBizParamInstPage(paginationParam);
	}

	@Override
	public void updateParamValue(BizParamInstPo bizParamInstPo)
			throws Exception {
		bizParamInstDAOImpl.updateParamValue(bizParamInstPo);
		
	}

	@Override
	public BizParamInstPo getBizParamInstById(String id)
			throws RollbackableBizException {
		return bizParamInstDAOImpl.getBizParamInstById(id);
	}
	
	@Override
	public HashMap<String, String> getInstanceParasMapByDevice(String srId, String rrinfoId, String deviceId) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("srId", srId);
		paramMap.put("rrinfoId", rrinfoId);
		paramMap.put("deviceId", deviceId);
		List<BizParamInstPo> bizParams = bizParamInstDAOImpl.findListByParam("getInstanceParasMapByDevice", paramMap);
		HashMap<String, String> deviceMap = new HashMap<String, String>();
		for (BizParamInstPo bizParamInstPo : bizParams) {
			deviceMap.put(bizParamInstPo.getParamKey(), bizParamInstPo.getParamValue());
		}
		return deviceMap;
	}
}