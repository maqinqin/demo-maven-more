package com.git.cloud.sys.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.dao.ISysMailMsgDao;
import com.git.cloud.sys.model.po.SysMailMsgHisPo;
import com.git.cloud.sys.model.po.SysMailMsgPo;

public class SysMailMsgDaoImpl extends CommonDAOImpl implements ISysMailMsgDao {
	
	@Override
	public List<SysMailMsgPo> selectSysMailMsgPo() throws RollbackableBizException {
		return super.findAll("selectSysMailMsgPo");
	}
	
	@Override
	public void saveSysMailMsgPo(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException {
		super.save("saveSysMailMsgPo", sysMailMsgPo);
	}
	
	@Override
	public void saveSysMailMsgHisPo(SysMailMsgHisPo sysMailMsgHisPo) throws RollbackableBizException {
		super.save("saveSysMailMsgHisPo", sysMailMsgHisPo);
	}
	
	@Override
	public void deleteSysMailMsgPo(String id) throws RollbackableBizException {
		super.delete("deleteSysMailMsgPo", id);
	}
}
