package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;

public interface ICmPasswordDAO extends ICommonDAO{

	/**
	 * 批量插入设备密码信息
	 * @Title: insertCmPassword
	 * @Description: TODO
	 * @field: @param pwdList
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertCmPassword(List<CmPasswordPo> pwdList) throws RollbackableBizException;
	
	/**
	 * 
	 * @Title: insertCmPassword
	 * @Description: 插入密码信息
	 * @field: @param cmPasswordPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertCmPassword(CmPasswordPo cmPasswordPo) throws RollbackableBizException;
	
	/**
	 * 
	 * @Title: updateCmPassword
	 * @Description: 更新密码信息
	 * @field: @param cmPasswordPo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateCmPassword(CmPasswordPo cmPasswordPo) throws RollbackableBizException;
	
	/**
	 * 根据资源Id获取密码对象
	 * @Title: findCmPasswordByResourceId
	 * @Description: TODO
	 * @field: @param resourceId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CmPasswordPo
	 * @throws
	 */
	public CmPasswordPo findCmPasswordByResourceId(String resourceId) throws RollbackableBizException;
	
	/**
	 * 根据资源Id和用户名，获取密码
	 * @Title: findCmPasswordByResourceId
	 * @Description: TODO
	 * @field: @param resourceId
	 * @field: @param userName
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CmPasswordPo
	 * @throws
	 */
	public CmPasswordPo findCmPasswordByResourceId(String resourceId, String userName) throws RollbackableBizException;
	
	/**
	 * @Title: findCmPasswordByResourceUser
	 * @Description: 根据资源ID和登陆用户获取密码信息
	 * @field: @param resourceId
	 * @field: @param userName
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return CmPasswordPo
	 * @throws
	 */
	public CmPasswordPo findCmPasswordByResourceUser(String resourceId,
			String userName) throws RollbackableBizException;
	
	public void deleteCmPassword(String resourceId) throws RollbackableBizException;

}
