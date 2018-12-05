package com.git.cloud.resmgt.compute.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.compute.model.po.CmVirtualSwitchPo;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.vo.RmNwIpAddressVo;

public interface ICmVirtualSwitchService {
	void updateRmNwOpstackIpAddress(OpenstackIpAddressPo rmNwIpAddressVo) throws Exception;}
