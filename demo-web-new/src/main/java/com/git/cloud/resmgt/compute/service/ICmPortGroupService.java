package com.git.cloud.resmgt.compute.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.model.po.CmPortGroupPo;
import com.git.cloud.resmgt.network.model.vo.RmNwIpAddressVo;

public interface ICmPortGroupService {/*
	
	*//**
	 * 保存端口组信息
	 * @param cmPortGroupPo
	 * @throws RollbackableBizException
	 *//*
	public void saveCmPortGroup(CmPortGroupPo cmPortGroupPo)throws RollbackableBizException;

	*//**
	 * 删除端口组信息
	 * @param switchId
	 * @throws RollbackableBizException
	 *//*
	public void deleteCmPortGroup(String switchId)throws RollbackableBizException;
	
	*//**
	 * 通过交换机id查询端口组列表
	 * @param virtualSwitchId
	 * @return
	 * @throws RollbackableBizException
	 *//*
	public List<CmPortGroupPo> findCmPortGroupBySwitchId(String virtualSwitchId)throws RollbackableBizException;
	
	*//**
	 * 查询端口组下对应的虚拟机
	 * @param rmNwIpAddressPo
	 * @throws RollbackableBizException
	 *//*
	public List<RmNwIpAddressVo> findvmListByPortGroupId(String portGroupId)throws RollbackableBizException;
*/}
