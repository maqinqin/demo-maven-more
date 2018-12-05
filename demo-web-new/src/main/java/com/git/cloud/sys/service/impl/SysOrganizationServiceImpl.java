package com.git.cloud.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.dao.SysOrganizationDao;
import com.git.cloud.sys.model.po.SysOrganizationPo;
import com.git.cloud.sys.service.SysOrganizationService;
/**
 * 组织机构管理
 * @ClassName: SysMenuService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysOrganizationServiceImpl implements SysOrganizationService {
	private SysOrganizationDao SysOrganizationDao;

	@Override
	public SysOrganizationPo load(String id) throws RollbackableBizException {
		return SysOrganizationDao.load(id);
	}

	@Override
	public List search(Map para) throws RollbackableBizException {
		return null;
	}

	@Override
	public List<Object> loadTree() throws RollbackableBizException {
		return this.getSysOrganizationDao().loadTree();
	}

	@Override
	public SysOrganizationPo save(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException {
		return this.getSysOrganizationDao().save(sysOrganizationPo);
	}

	@Override
	public void delete(String id) throws RollbackableBizException {
		this.getSysOrganizationDao().delete(id);
	}

	public SysOrganizationDao getSysOrganizationDao() {
		return SysOrganizationDao;
	}

	public void setSysOrganizationDao(SysOrganizationDao SysOrganizationDao) {
		this.SysOrganizationDao = SysOrganizationDao;
	}

	@Override
	public Boolean isrepeat(Map<String, String> map) {
		return SysOrganizationDao.isrepeat(map);
	}

	@Override
	public SysOrganizationPo insert(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException {
		return this.getSysOrganizationDao().save(sysOrganizationPo);
	}

	@Override
	public SysOrganizationPo update(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException {
		return this.getSysOrganizationDao().save(sysOrganizationPo);
	}

}
