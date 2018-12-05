package com.git.cloud.sys.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysMenuPo;
/**
 * 菜单管理
 * @ClassName: SysMenuService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface SysMenuService {

	public SysMenuPo load(String id) throws RollbackableBizException;

	public List search(Map para) throws RollbackableBizException;

	public List<Object> loadTree() throws RollbackableBizException;

	public SysMenuPo save(SysMenuPo sysMenuPo) throws RollbackableBizException;
	public SysMenuPo insert(SysMenuPo sysMenuPo) throws RollbackableBizException;
	public SysMenuPo update(SysMenuPo sysMenuPo) throws RollbackableBizException;

	public void delete(String id) throws RollbackableBizException;

	public List<Object> roles(String id) throws RollbackableBizException;

	/**
	 * 判断是否重复
	 * 
	 * @param map
	 * @return
	 */
	public Boolean isrepeat(Map<String, String> map);
}
