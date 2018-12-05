package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.resmgt.common.model.vo.SyncVmInfoVo;

/**
 * 同步物理机、虚拟机信息
  * @author WangJingxin
  * @date 2016年5月6日 上午10:43:02
  *
 */
public interface ISyncVmInfoService {

	public void saveVMInfo(List<SyncVmInfoVo> vo) throws Exception;
}
