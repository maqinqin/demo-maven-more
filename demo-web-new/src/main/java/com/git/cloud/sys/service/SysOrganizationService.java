package com.git.cloud.sys.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.SysOrganizationPo;
/**
 * 组织机构管理
 * @ClassName: SysOrganizationService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface SysOrganizationService {

	public SysOrganizationPo load(String id) throws RollbackableBizException;

	public List search(Map para) throws RollbackableBizException;

	public List<Object> loadTree() throws RollbackableBizException;

	public SysOrganizationPo save(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException;
	public SysOrganizationPo insert(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException;
	public SysOrganizationPo update(SysOrganizationPo sysOrganizationPo) throws RollbackableBizException;

	public void delete(String id) throws RollbackableBizException;

	public Boolean isrepeat(Map<String, String> map);
}
