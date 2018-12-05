package com.git.cloud.tenant.service.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.cloud.sys.dao.IUserDao;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.tenant.dao.ITenantAuthoDao;
import com.git.cloud.tenant.dao.ITenantQuotaDao;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.po.TenantResPoolPo;
import com.git.cloud.tenant.model.po.TenantUsersPo;
import com.git.cloud.tenant.model.vo.AllQuotaCountVo;
import com.git.cloud.tenant.service.ITenantAuthoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author
 */
@Service
public class TenantAuthoServiceImpl extends CommonDAOImpl implements ITenantAuthoService {

	@Autowired
	private ITenantAuthoDao tenantDao;
	@Autowired
	private ITenantQuotaDao tenantQuotaDao;
	@Autowired
	@Qualifier("userDaoImpl")
	private IUserDao userDao;

	public ITenantAuthoDao getTenantDao() {
		return tenantDao;
	}
	public void setTenantDao(ITenantAuthoDao tenantDao) {
		this.tenantDao = tenantDao;
	}

	@Override
	public Pagination<TenantUsersPo> selectTenantAndNotTenantUsersByTenantId(PaginationParam paginationParam) throws RollbackableBizException {
		return  tenantDao.selTenAndNotUsersByTenantId(paginationParam);
	}

	@Override
	public void addTenantAndUserRelation(String tenantId,List<TenantUsersPo> list) throws RollbackableBizException {
		String[] ids = null;
		// 查询租户关联的用户列表
		List<TenantUsersPo> usersPoList = tenantDao.selectUserListByTenantId(tenantId);
		// 查询要取消关联的资源池列表
		List<TenantUsersPo> deleteUsers = new ArrayList<>();
		for (TenantUsersPo u : usersPoList ) {
			boolean exist = false;
			for(TenantUsersPo n : list) {
				if(u.getUserId().equals(n.getUserId())) {
					exist = true;
					break;
				}
			}
			if(!exist) {
				deleteUsers.add(u);
			}
		}
		// 查询用户的资源数
		for(TenantUsersPo usersPo : deleteUsers) {
			AllQuotaCountVo used = tenantQuotaDao.selectOpenstackUsedResourceByUserId(tenantId, usersPo.getUserId());
			if(used.getVmNumUsed() > 0 || used.getCpuUsed() > 0 || used.getMemUsed() > 0) {
				SysUserPo sysuserPo = userDao.findUserPoById(usersPo.getUserId());
				throw new RuntimeException("用户[" + sysuserPo.getLoginName() + "]在当前租户已使用，不能移除此用户！");
			}
		}

		if(list!=null && list.size()>0) {
			for (TenantUsersPo tenantUsersPo : list) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				tenantUsersPo.setId(id);
			}
			tenantDao.deleteTenantAndUserRelation(tenantId);
			tenantDao.addTenantAndUserRelation(list);
		}else {
			tenantDao.deleteTenantAndUserRelation(tenantId);
		}

	}

	@Override
	public void addTenant(TenantPo tenantPo) throws RollbackableBizException {
		String id = UUIDGenerator.getUUID();
		tenantPo.setId(id);
		Date date = new Date();
		Subject subject = SecurityUtils.getSubject();
		SysUserPo shiroUser = (SysUserPo) subject.getPrincipal();
		tenantPo.setCreateUser(shiroUser.getUserId());
		tenantPo.setCreateTime(date);
		tenantDao.addTenant(tenantPo);
	}

	@Override
	public void updateTenant(TenantPo tenantPo) throws RollbackableBizException {
		tenantDao.updateTenant(tenantPo);
	}

	@Override
	public boolean deleteTenant(String tenantId) throws RollbackableBizException {
		List<QuotaPo> list = tenantQuotaDao.selectQuotaByTenantId(tenantId);
		for (QuotaPo quotaPo : list) {
			// 校验值是否大于已经使用的值
			AllQuotaCountVo allQuotaCountVo = tenantQuotaDao.countOpenstackUsedQuota(quotaPo.getTenantId(), quotaPo.getProjectId());
			switch (quotaPo.getCode()) {
				case "vm":
					if(allQuotaCountVo.getVmNumUsed() != null) {
						if(allQuotaCountVo.getVmNumUsed() > 0) {
							throw new RuntimeException("虚拟机已经使用的值大于0！");
						}
					}
					break;
				case "cpu" :
					if(allQuotaCountVo.getCpuUsed() != null) {
						if(allQuotaCountVo.getCpuUsed() > 0) {
							throw new RuntimeException("CPU已经使用的值大于0！");
						}
					}
					break;
				case "mem" :
					if(allQuotaCountVo.getMemUsed() != null) {
						if(allQuotaCountVo.getMemUsed() > 0) {
							throw new RuntimeException("内存已经使用的值大于0！");
						}
					}
					break;
				case "storage" :
					if(allQuotaCountVo.getStorageUsed() != null) {
						if(allQuotaCountVo.getStorageUsed() > 0) {
							throw new RuntimeException("存储已经使用的值大于0！");
						}
					}
					break;
				case "ip" :
					if(allQuotaCountVo.getFloatingIpNumUsed() != null) {
						if(allQuotaCountVo.getFloatingIpNumUsed() > 0) {
							throw new RuntimeException("弹性IP已经使用的值大于0！");
						}
					}
					break;
				case "vlb" :
					if(allQuotaCountVo.getVlbNumUsed() != null) {
						if(allQuotaCountVo.getVlbNumUsed() > 0) {
							throw new RuntimeException("负载均衡已经使用的值大于0！");
						}
					}
					break;
				case "network" :
					if(allQuotaCountVo.getNetworkNumUsed() != null) {
						if(allQuotaCountVo.getNetworkNumUsed() > 0) {
							throw new RuntimeException("网络已经使用的值大于0！");
						}
					}
					break;
				case "group" :
					if(allQuotaCountVo.getSecurityGroupNumUsed() != null) {
						if(allQuotaCountVo.getSecurityGroupNumUsed() > 0) {
							throw new RuntimeException("安全组已经使用的值大于0！");
						}
					}
					break;
				default:
					break;
			}
		}
		// 校验租户下是否有project
		List<CloudProjectPo> projectList = tenantQuotaDao.getProjectsByTenantId(tenantId);
		if(projectList != null && projectList.size() >0) {
			throw new RuntimeException("当前租户下存在Project！");
		}
		Integer count = tenantDao.selectTenantUsersCount(tenantId);
		if(count == 0 && list.size() == 0) {
			// 删除租户
			tenantDao.deleteTenant(tenantId);
			//删除资源池关联关系
			tenantDao.deleteTenantAndResPoolRelation(tenantId);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public TenantPo selectTenant(String tenantId) throws RollbackableBizException {
		return tenantDao.selectTenant(tenantId);
	}

	@Override
	public Pagination<TenantPo> selectTenants(PaginationParam paginationParam) throws RollbackableBizException {
		return tenantDao.selectTenants(paginationParam);
	}

	@Override
	public List<TenantPo> selectTenantWithoutLimit() throws RollbackableBizException {
		return tenantDao.selectTenantWithoutLimit();
	}

	@Override
	public String selectTenantByUserId(String userId) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return tenantDao.selectTenantByUserId(userId);
	}

	@Override
	public void addTenantAndResPoolRelation(String tenantId, List<TenantResPoolPo> list)
			throws RollbackableBizException {
		if(!CommonUtil.isEmpty(list)) {
			for(TenantResPoolPo po : list) {
				po.setId(UUIDGenerator.getUUID());
				po.setTenantId(tenantId);
			}
		}
		/**
		 * 查询已经关联的资源池列表
		 */
		List<TenantResPoolPo> tenantResPoolPos = tenantDao.selectTenantPoolList(tenantId);
		// 查询要取消关联的资源池列表
		List<TenantResPoolPo> deleteResPools = new ArrayList<>();
		for (TenantResPoolPo r : tenantResPoolPos ) {
			boolean exist = false;
			for(TenantResPoolPo n : list) {
				if(r.getResPoolId().equals(n.getResPoolId())) {
					exist = true;
					break;
				}
			}
			if(!exist) {
				deleteResPools.add(r);
			}
		}
		// 查询租户在资源池的资源数
		for(TenantResPoolPo r : deleteResPools) {
			AllQuotaCountVo used = tenantQuotaDao.selectOpenstackUsedResourceByResPoolId(tenantId, r.getResPoolId());
			if(used.getVmNumUsed() > 0 || used.getCpuUsed() > 0 || used.getMemUsed() > 0) {
				throw new RuntimeException("资源池[" + r.getResPoolName() + "]在当前租户已使用，不能移除此资源池！");
			}
		}
		//删除原有关联关系
		tenantDao.deleteTenantAndResPoolRelation(tenantId);
		//新增关联关系
		tenantDao.addTenantAndResPoolRelation(list);
	}

	@Override
	public Pagination<TenantResPoolPo> getResPoolsRelationByTenantId(PaginationParam paginationParam)
			throws RollbackableBizException {
		return tenantDao.getResPoolsRelationByTenantId(paginationParam);
	}


}
