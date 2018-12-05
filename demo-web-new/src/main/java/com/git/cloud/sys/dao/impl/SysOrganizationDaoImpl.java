package com.git.cloud.sys.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.dao.SysOrganizationDao;
import com.git.cloud.sys.model.po.SysOrganizationPo;
/**
 * 组织机构管理
 * @ClassName: SysOrganizationDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysOrganizationDaoImpl extends CommonDAOImpl implements SysOrganizationDao {

	@SuppressWarnings("rawtypes")
	@Override
	public SysOrganizationPo load(String orgId) throws RollbackableBizException {
		List list = super.findByID("SysOrganization.load", orgId);
		if (list != null)
			for (Object o : list) {
				return (SysOrganizationPo) o;
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
		List list = super.findAll("SysOrganization.tree");
		return list;
	}

	@Override
	public SysOrganizationPo save(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException {
		if (sysOrganizationPo.getOrgId() == null || "".equals(sysOrganizationPo.getOrgId())) {
			sysOrganizationPo.setCreateDateTime(new Date());
			sysOrganizationPo.setOrgId(UUIDGenerator.getUUID());
			sysOrganizationPo.setIsActive("Y");
			if (sysOrganizationPo.getParent() == null || sysOrganizationPo.getParent().getOrgId() == null || "".equals(sysOrganizationPo.getParent().getOrgId())) {
				SysOrganizationPo p = new SysOrganizationPo();
				p.setOrgId("0");
				sysOrganizationPo.setParent(p);
			}
			this.getSqlMapClientTemplate().insert("SysOrganization.insert", sysOrganizationPo);
		} else {
			sysOrganizationPo.setUpdateDateTime(new Date());
			this.getSqlMapClientTemplate().update("SysOrganization.update", sysOrganizationPo);
		}
		return sysOrganizationPo;
	}

	@Override
	public void delete(String id) throws RollbackableBizException {
		// 查询全部子菜单
		String pid = id;
		String allChildren = id;
		while (pid != null) {
			Object o = this.getSqlMapClientTemplate().queryForObject("SysOrganization.loadChildren", pid);
			if (o != null) {
				allChildren += "," + o.toString();
				pid = o.toString();
			} else {
				pid = null;
			}
		}
		
		String[] ids = allChildren.split(",");
		for (String d : ids) {
			this.getSqlMapClientTemplate().update("SysOrganization.delete0", d);
		}
		// this.getSqlMapClientTemplate().update("SysOrganization.delete0", id);
		// super.update("SysOrganization.delete0", id);
		// super.delete("SysOrganization.delete", id);
	}

	@Override
	public List<Object> loadChildren(String parentId) throws RollbackableBizException {
		return this.getSqlMapClientTemplate().queryForList("SysOrganization.loadChildren", parentId);
	}

	@Override
	public Boolean isrepeat(Map<String, String> map) {
		List<SysOrganizationPo> list = this.getSqlMapClientTemplate().queryForList("SysOrganization.isrepeat", map);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

}
