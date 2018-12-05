package com.git.cloud.handler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.handler.po.BizParamInstPo;

public interface BizParamInstService {

	/**
	 * 读取参数列表，按照nodeId排序
	 * @param instanceId	流程实例id
	 * @return
	 * @throws BizException
	 */
	public List<BizParamInstPo> getInstanceParas(String instanceId) throws BizException;
	
	/**
	 * 读取设备的流程参数
	 * @param instanceId	流程实例id
	 * @param deviceId		设备id
	 * @return
	 * @throws BizException
	 */
	public List<BizParamInstPo> getInstanceParas(String instanceId, String deviceId) throws BizException;
	
	public Map<String, String> getInstanceParasMap(String instanceId, String deviceId) throws BizException;
	
	/**
	 * 批量保存流程参数
	 * @param paras
	 * @throws BizException
	 */
	public void saveParas(List<BizParamInstPo> paras) throws BizException;
	
	/**
	 * 批量保存流程参数，如果数据库中已经存在相同流程id、相同设备id、相同名称的参数，则覆盖之
	 * @param paras
	 * @throws BizException
	 */
	public void saveOrUpdateParas(List<BizParamInstPo> paras) throws BizException;
	
	/**
	 * 删除一个流程实例的所有参数
	 * @param instanceId	实例id
	 * @throws BizException
	 */
	public void deleteParamInstsOfFlow(String instanceId) throws BizException;
	
	/**
	 * 删除一个流程实例
	 * @param deviceId, flowInstId, nodeId, paramKey
	 * @throws BizException
	 */
	public void deleteParamInstPo(String deviceId,String flowInstId,String nodeId,String paramKey) throws BizException;
	
	public void deleteParamInstForSameKey(String deviceId,String flowInstId,String paramKey) throws BizException;
	
	/**
	 * 保存
	 * @param bizParamInstPo
	 * @throws BizException
	 */
	public void savePara(BizParamInstPo bizParamInstPo) throws BizException;
	/**
	 * 
	 * @param 流程变量Sql
	 * @return
	 * @throws Exception
	 */
	public List<BizParamInstPo> getBizParamInst(String deviceId,String flowInstId,String paramKey) throws BizException;
	/**
	 * 本服务策略的参数
	 * @param 流程变量Sql
	 * @return
	 * @throws Exception
	 */
	public List<?> getParamInitBySql(String sql) throws Exception;
	/**
	 *获取流程实例参数表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<BizParamInstPo> findBizParamInstPage(PaginationParam paginationParam) throws RollbackableBizException;
	/**
	 * 修改流程实例参数中的value
	 * @param bizParamInstPo
	 * @throws Exception
	 */
	public void updateParamValue(BizParamInstPo bizParamInstPo)throws Exception;
	/**
	 * 获取流程实例通过id
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 */
	public BizParamInstPo getBizParamInstById(String id)throws RollbackableBizException;
	/**
	 * 根据设备ID及服务申请ID相关信息获取流程参数
	 * @param srId
	 * @param rrinfoId
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	public HashMap<String, String> getInstanceParasMapByDevice(String srId, String rrinfoId, String deviceId) throws BizException;
}