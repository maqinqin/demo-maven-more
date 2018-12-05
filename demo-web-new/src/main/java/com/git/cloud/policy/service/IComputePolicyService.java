package com.git.cloud.policy.service;

import java.util.List;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.policy.model.vo.RequsetResInfoVo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public interface IComputePolicyService {
	/**
	 * @Title: distribHostForVm
	 * @Description: 根据策略提供可供虚机分配使用的物理机信息集合
	 * @field: @param bmSrRrinfoPo
	 * @field: @return
	 * @field: @throws Exception
	 * @return List<PolicyResultVo>
	 * @throws
	 */
	public List<PolicyResultVo> distribHostForVm(BmSrRrinfoPo bmSrRrinfoPo) throws Exception;
	
	public List<PolicyResultVo> getHostResPoolInfo(RequsetResInfoVo requsetResInfoVo,int cpu, int mem ) throws RollbackableBizException; 
	
	public boolean checkHostResPoolInfo(String devId,int extCpu, int extMem ) throws RollbackableBizException; 
	/**
	 * VMware分配计算资源
	 * @param rmHostList 资源池下面的物理机列表
	 * @param bmSrRrinfoPo 用户服务请求参数
	 * @param distribCount 已经分配的虚拟机数
	 * @return
	 * @throws Exception
	 */
	List<PolicyResultVo> distribHostForVmWare(List<PolicyInfoVo> rmHostList, BmSrRrinfoPo bmSrRrinfoPo, int distribCount) throws Exception; 
}