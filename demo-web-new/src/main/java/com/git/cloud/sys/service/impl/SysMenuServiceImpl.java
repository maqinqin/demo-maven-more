package com.git.cloud.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.cache.Cache;
import com.git.cloud.cache.CacheException;
import com.git.cloud.cache.CacheUtil;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.dao.SysMenuDao;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.service.SysMenuService;
/**
 * 菜单管理
 * @ClassName: SysMenuService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysMenuServiceImpl implements SysMenuService {
	private SysMenuDao SysMenuDao;

	@Override
	public SysMenuPo load(String id) throws RollbackableBizException {
		return SysMenuDao.load(id);
	}

	@Override
	public List search(Map para) throws RollbackableBizException {
		return null;
	}

	@Override
	public List<Object> loadTree() throws RollbackableBizException {
		return this.getSysMenuDao().loadTree();
	}

	@Override
	public SysMenuPo save(SysMenuPo sysMenuPo) throws RollbackableBizException {
		return this.getSysMenuDao().save(sysMenuPo);
	}

	@Override
	public void delete(String id) throws RollbackableBizException {
		this.getSysMenuDao().delete(id);
	}

	public SysMenuDao getSysMenuDao() {
		return SysMenuDao;
	}

	public void setSysMenuDao(SysMenuDao SysMenuDao) {
		this.SysMenuDao = SysMenuDao;
	}

	@Override
	public List<Object> roles(String id) throws RollbackableBizException {
		return this.getSysMenuDao().roles(id);
	}

	@Override
	public Boolean isrepeat(Map<String, String> map) {
		return getSysMenuDao().isrepeat(map);
	}

	@Override
	public SysMenuPo insert(SysMenuPo sysMenuPo) throws RollbackableBizException {
		return this.getSysMenuDao().save(sysMenuPo);
	}

	@Override
	public SysMenuPo update(SysMenuPo sysMenuPo) throws RollbackableBizException {
		return this.getSysMenuDao().save(sysMenuPo);
	}

}
