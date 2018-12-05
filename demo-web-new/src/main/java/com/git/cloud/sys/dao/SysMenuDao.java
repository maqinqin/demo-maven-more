package com.git.cloud.sys.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysMenuPo;
/**
 * 菜单管理
 * @ClassName: SysMenuDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface SysMenuDao extends ICommonDAO {
	public SysMenuPo load(String id) throws RollbackableBizException;

	public List search(Map para) throws RollbackableBizException;

	public List<Object> loadTree() throws RollbackableBizException;

	public List<Object> loadChildren(String parentId) throws RollbackableBizException;

	public SysMenuPo save(SysMenuPo sysMenuPo) throws RollbackableBizException;

	public void delete(String id) throws RollbackableBizException;

	public List<SysMenuPo> findAll(String resourceType) throws RollbackableBizException;

	public List<Object> roles(String id) throws RollbackableBizException;

	public Boolean isrepeat(Map<String, String> map);
	
	public List<SysMenuPo> findMenusByParentId(String parentId);
}
