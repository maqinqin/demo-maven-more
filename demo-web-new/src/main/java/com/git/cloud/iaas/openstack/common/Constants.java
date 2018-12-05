package com.git.cloud.iaas.openstack.common;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.parame.service.ParameterService;

public class Constants {

	//2.0.6 管理端用户信息
	public static String OPENSTACK_MANAGE_USER = "OPENSTACK_MANAGE_USER";
	public static String OPENSTACK_MANAGE_PWD = "OPENSTACK_MANAGE_PWD";
	public static String OPENSTACK_MANAGE_PROJECT = "OPENSTACK_MANAGE_PROJECT";
	public static String OPENSTACK_MANAGE_DOMAIN = "OPENSTACK_MANAGE_DOMAIN";
	public static String OPENSTACK_MANAGE_ROLE = "OPENSTACK_MANAGE_ROLE";

	//6.3 VDC用户
	public static String SC_VDC_USER = "SC_VDC_USER";
	public static String SC_VDC_PWD = "SC_VDC_PWD";
//	public static String SC_VDC_PROJECTID = "SC_VDC_PROJECTID";
	public static String SC_VDC_DOMINANAME = "SC_VDC_DOMINANAME";

	//6.3 SC运营管理员(创建规格使用)
	public static String SC_SYS_USER = "SC_SYS_USER";
	public static String SC_SYS_PWD = "SC_SYS_PWD";
	public static String SC_SYS_PROJECTNAME = "SC_SYS_PROJECTNAME";
	public static String SC_SYS_DOMAINNAME = "SC_SYS_DOMAINNAME";
	
	//6.3 OC用户
	public static String OC_MANAGE_USER = "OC_MANAGE_USER";
	public static String OC_MANAGE_PWD = "OC_MANAGE_PWD";
	
	//6.3 其他
	public static String OPENSTACK_PHY_NET = "OPENSTACK_PHY_NET";
	
	private Constants() {}
	
	private static ParameterService parameterServiceImpl;
	
	private static ParameterService getParameterService() {
		if (parameterServiceImpl == null) {
			parameterServiceImpl = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
		}
		return parameterServiceImpl;
	}

	public static String getConstantsParameter(String paramName) throws RollbackableBizException {
		return getParameterService().getParamValueByName(paramName);
	}
	
/*	public static Constants getInstance() throws RollbackableBizException {

		ParameterService parameterServiceImpl = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
		String manageUserSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_USER_SC");
		String managePwdSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_PWD_SC");
		String manageDomainSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_DOMAIN_SC");
		String manageDomainNameSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_DOMAIN_NAME_SC");
		String manageSysUserSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_SYS_USER_SC");
		String manageSysPwdSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_SYS_PWD_SC");
		String manageSysDomainSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_SYS_DOMAIN_SC");
		String manageSysProjectNameSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_SYS_PROJECT_NAME_SC");
		String manageProjectIdSc = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_PROJECT_ID_SC");
		String manageProjectId = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_PROJECT_ID");

		String manageUser = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_USER");
		String managePwd = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_PWD");
		String manageProject = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_PROJECT");
		String manageDomain = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_DOMAIN");
		String manageRole = parameterServiceImpl.getParamValueByName("OPENSTACK_MANAGE_ROLE");

		String phyNet = parameterServiceImpl.getParamValueByName("OPENSTACK_PHY_NET");

		Constants instance = new Constants();
		instance.openstackManageUser = manageUser;
		instance.openstackManagePwd = managePwd;
		instance.openstackManageProject = manageProject;
		instance.openstackManageDomain = manageDomain;
		instance.openstackManageRole = manageRole;

		instance.openstackManageUserSc = manageUserSc;
		instance.openstackManagePwdSc = managePwdSc;
		instance.openstackManageProjectIdSc = manageProjectIdSc;
		instance.openstackManageDomainNameSc = manageDomainNameSc;
		instance.openstackManageDomainSc = manageDomainSc;
		instance.openstackManageProjectId = manageProjectId;

		instance.openstackManageSysUserSc = manageSysUserSc;
		instance.openstackManageSysPwdSc = manageSysPwdSc;
		instance.openstackManageSysProjectNameSc = manageSysProjectNameSc;
		instance.openstackManageSysDomainSc = manageSysDomainSc;
		instance.openstackPhyNet = phyNet;

		return instance;
	}

	private String openstackManageUser;
	private String openstackManagePwd;
	private String openstackManageProject;
	private String openstackManageDomain;
	private String openstackManageRole;

	//6.3 VDC用户

	private String openstackManageUserSc;
	private String openstackManagePwdSc;
	private String openstackManageProjectIdSc;
	private String openstackManageDomainNameSc;
	private String openstackManageDomainSc;
	private String openstackManageProjectId;

	//6.3 SC运营管理员(创建规格使用)

	private String openstackManageSysUserSc;
	private String openstackManageSysPwdSc;
	private String openstackManageSysProjectNameSc;
	private String openstackManageSysDomainSc;

	private String openstackPhyNet;

	public String getOpenstackManageUser() {
		return openstackManageUser;
	}

	public String getOpenstackManagePwd() {
		return openstackManagePwd;
	}

	public String getOpenstackManageProject() {
		return openstackManageProject;
	}

	public String getOpenstackManageDomain() {
		return openstackManageDomain;
	}

	public String getOpenstackManageRole() {
		return openstackManageRole;
	}

	public String getOpenstackManageUserSc() {
		return openstackManageUserSc;
	}

	public String getOpenstackManagePwdSc() {
		return openstackManagePwdSc;
	}

	public String getOpenstackManageProjectIdSc() {
		return openstackManageProjectIdSc;
	}

	public String getOpenstackManageDomainNameSc() {
		return openstackManageDomainNameSc;
	}

	public String getOpenstackManageDomainSc() {
		return openstackManageDomainSc;
	}

	public String getOpenstackManageProjectId() {
		return openstackManageProjectId;
	}

	public String getOpenstackManageSysUserSc() {
		return openstackManageSysUserSc;
	}

	public String getOpenstackManageSysPwdSc() {
		return openstackManageSysPwdSc;
	}

	public String getOpenstackManageSysProjectNameSc() {
		return openstackManageSysProjectNameSc;
	}

	public String getOpenstackManageSysDomainSc() {
		return openstackManageSysDomainSc;
	}

	public String getOpenstackPhyNet() {
		return openstackPhyNet;
	}


	public static String OPENSTACK_MANAGE_USER = "cmp";
	public static String OPENSTACK_MANAGE_PWD = "FusionSphere123";
	public static String OPENSTACK_MANAGE_PROJECT = "cmpProject";
	public static String OPENSTACK_MANAGE_DOMAIN = "cmpDefault";
	public static String OPENSTACK_MANAGE_ROLE = "admin";
	
	//6.3 VDC用户
	public static String OPENSTACK_MANAGE_USER_SC = "gwd_admin";
	public static String OPENSTACK_MANAGE_PWD_SC = "Huawei@1234";
	public static String OPENSTACK_MANAGE_PROJECT_ID_SC = "{\"project\":{\"id\":\"d503aa91c94f454781a76959289debea\"}}";
	public static String OPENSTACK_MANAGE_DOMAIN_NAME_SC = "{\"domain\":{\"name\":\"gwd_tenant\"}}";
	public static String OPENSTACK_MANAGE_DOMAIN_SC = "gwd_tenant";
	public static String OPENSTACK_MANAGE_PROJECT_ID = "d503aa91c94f454781a76959289debea";
	//6.3 SC运营管理员(创建规格使用)
	public static String OPENSTACK_MANAGE_SYS_USER_SC = "gwd_sysadmin";
	public static String OPENSTACK_MANAGE_SYS_PWD_SC = "Huawei@1234";
	public static String OPENSTACK_MANAGE_SYS_PROJECT_NAME_SC = "{\"project\":{\"name\":\"STD_sa-fb-1_mo_bss_project\"}}";
	public static String OPENSTACK_MANAGE_SYS_DOMAIN_SC = "mo_bss_admin";
	
	public static String OPENSTACK_PHY_NET = "{\"provider\":[{\"vlanpool\":{\"start\":\"1\",\"end\":\"3999\"},\"om_vlan\":\"4002\",\"name\":\"physnet1\",\"description\":\"default physical network.\"}]}";
*/
}
