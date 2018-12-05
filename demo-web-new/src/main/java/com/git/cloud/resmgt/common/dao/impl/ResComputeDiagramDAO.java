package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IResComputeDiagramDAO;
import com.git.cloud.resmgt.common.model.vo.DeviceDiagramVo;
import com.git.cloud.resmgt.common.model.vo.DeviceVmTypeNumDiagramVo;
import com.git.cloud.resmgt.common.model.vo.ResPoolHostVmInfoVo;

public class ResComputeDiagramDAO extends CommonDAOImpl implements
		IResComputeDiagramDAO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@SuppressWarnings("unchecked")
	public List<DeviceDiagramVo> findDeviceDiagramInfo() {

		try {
			List<DeviceDiagramVo> list = getSqlMapClientTemplate()
					.queryForList("selectDeviceDiagram");
			return list;
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DeviceVmTypeNumDiagramVo> findVmNumberInfo() {
		try {
			List<DeviceVmTypeNumDiagramVo> l = getSqlMapClientTemplate()
					.queryForList("selectResComputeVmNumber");

			return l;
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ResPoolHostVmInfoVo> findResPoolHostVmInfo() {
		try {
			List<ResPoolHostVmInfoVo> l = getSqlMapClientTemplate()
					.queryForList("selectResPoolHostVmInfoVo");

			return l;
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return null;
	}

}
