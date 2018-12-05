package com.git.cloud.sys.dao;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysMailMsgHisPo;
import com.git.cloud.sys.model.po.SysMailMsgPo;

public interface ISysMailMsgDao {

	/**
	 * 查询系统邮件
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<SysMailMsgPo> selectSysMailMsgPo() throws RollbackableBizException;
	/**
	 * 保存系统邮件信息
	 * @param sysMailMsgPo
	 * @throws RollbackableBizException
	 */
	public void saveSysMailMsgPo(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException;
	/**
	 * 保存系统邮件历史消息
	 * @param sysMailMsgHisPo
	 * @throws RollbackableBizException
	 */
	public void saveSysMailMsgHisPo(SysMailMsgHisPo sysMailMsgHisPo) throws RollbackableBizException;
	/**
	 * 根据Id删除系统邮件信息
	 * @param id
	 * @throws RollbackableBizException
	 */
	public void deleteSysMailMsgPo(String id) throws RollbackableBizException;
}
