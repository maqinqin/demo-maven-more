package com.git.cloud.sys.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.dao.SysMenuDao;
import com.git.cloud.sys.model.po.SysMenuPo;
/**
 * 菜单管理
 * @ClassName: SysMenuDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysMenuDaoImpl extends CommonDAOImpl implements SysMenuDao {

	@Override
	public SysMenuPo load(String id) throws RollbackableBizException {
		List list = super.findByID("SysMenu.load", id);
		if (list != null)
			for (Object o : list) {
				return (SysMenuPo) o;
			}
		return null;
	}

	@Override
	public List search(Map para) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Object> loadTree() throws RollbackableBizException {
		List list = super.findAll("SysMenu.tree");
		return list;
	}

	@Override
	public SysMenuPo save(SysMenuPo sysMenuPo) throws RollbackableBizException {
		if (sysMenuPo.getId() == null || "".equals(sysMenuPo.getId())) {
			sysMenuPo.setCreateDateTime(new Date());
			sysMenuPo.setId(UUIDGenerator.getUUID());
			sysMenuPo.setIsActive("Y");
			if (sysMenuPo.getParent() == null || sysMenuPo.getParent().getId() == null || "".equals(sysMenuPo.getParent().getId())) {
				SysMenuPo p = new SysMenuPo();
				p.setId("0");
			}
			this.getSqlMapClientTemplate().insert("SysMenu.insert", sysMenuPo);
		} else {
			sysMenuPo.setUpdateDateTime(new Date());
			this.getSqlMapClientTemplate().update("SysMenu.update", sysMenuPo);
		}
		return sysMenuPo;
	}

	@Override
	public void delete(String id) throws RollbackableBizException {
		// 查询全部子菜单
		String pid = id;
		String allChildren = id;
		while (pid != null) {
			Object o = this.getSqlMapClientTemplate().queryForObject("SysMenu.loadChildren", pid);
			if (o != null) {
				allChildren += "," + o.toString();
				pid = o.toString();
			} else {
				pid = null;
			}
		}
		
		String[] ids = allChildren.split(",");
		for (String d : ids) {
			this.getSqlMapClientTemplate().update("SysMenu.delete0", d);
		}
		// this.getSqlMapClientTemplate().update("SysMenu.delete0", id);
		// super.update("SysMenu.delete0", id);
		// super.delete("SysMenu.delete", id);
	}

	@Override
	public List<Object> loadChildren(String parentId) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("SysMenu.loadChildren", parentId);
	}

	@Override
	public List<SysMenuPo> findAll(String resourceType) throws RollbackableBizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("resourceType", resourceType);
		return this.findListByParam("findAllFunctions", map);
	}

	@Override
	public List<Object> roles(String id) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("SysMenu.roles", id);
	}

	@Override
	public Boolean isrepeat(Map<String, String> map) {
		List list = getSqlMapClientTemplate().queryForList("SysMenu.isrepeat", map);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuPo> findMenusByParentId(String parentId) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("FindMenusByParentId", parentId);
	}

}
