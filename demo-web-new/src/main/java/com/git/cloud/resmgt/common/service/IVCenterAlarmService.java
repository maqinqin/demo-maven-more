package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.resmgt.common.model.vo.VCenterAlarmVo;

/**
 * 处理vCenter触发的报警
  * @author WangJingxin
  * @date 2016年5月5日 下午2:59:37
  *
 */
public interface IVCenterAlarmService {
	
	public void saveLogInfo(List<VCenterAlarmVo> vo) throws Exception;
}