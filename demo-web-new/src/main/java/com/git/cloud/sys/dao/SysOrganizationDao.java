package com.git.cloud.sys.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysOrganizationPo;
/**
 * 组织机构管理
 * @ClassName: SysOrganizationDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface SysOrganizationDao {
	public SysOrganizationPo load(String id) throws RollbackableBizException;

	public List search(Map para) throws RollbackableBizException;

	public List<Object> loadTree() throws RollbackableBizException;

	public List<Object> loadChildren(String parentId) throws RollbackableBizException;

	public SysOrganizationPo save(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException;

	public void delete(String id) throws RollbackableBizException;

	public Boolean isrepeat(Map<String, String> map);
}
