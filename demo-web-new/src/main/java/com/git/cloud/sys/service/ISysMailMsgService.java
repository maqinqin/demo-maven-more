package com.git.cloud.sys.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysMailMsgPo;

/**
 * 邮件接口类
 * @author Sunhailong
 */
public interface ISysMailMsgService {
	/**
	 * 保存系统邮件信息
	 * @param sysMailMsgPo
	 * @throws RollbackableBizException
	 */
	public void saveSysMailMsgPo(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException;
	
	/**
	 * 查询系统邮件
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<SysMailMsgPo> selectSysMailMsgPo() throws RollbackableBizException;
	
	/**
	 * 发送邮件 保存系统邮件历史信息
	 * @param sysMailMsgPo
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean saveSysMailMsgHisAndSendMail(SysMailMsgPo sysMailMsgPo) throws RollbackableBizException;
}
