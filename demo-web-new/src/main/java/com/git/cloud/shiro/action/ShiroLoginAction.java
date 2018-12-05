package com.git.cloud.shiro.action;

import java.io.File;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.JsonVo;
import com.git.cloud.common.sha1.SHA1;
import com.git.cloud.common.support.TimeUtils;
import com.git.cloud.shiro.model.CertificateCheckEnum;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.cloud.shiro.service.ICertificateValidateService;
import com.git.cloud.shiro.service.PermissionService;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.tools.Security;
import com.git.cloud.taglib.util.Internation;
import com.git.license.ILicenseVerifyService;
import com.git.license.LicenseVerifyService;
/**
 * Shiro登录认证
 * @ClassName: ShiroLoginAction
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
@SuppressWarnings("rawtypes")
public class ShiroLoginAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 329347674483723005L;
	private String username;
	private String userid;
	private String idCode;
	private PermissionService permissionService;
	private ICertificateValidateService certificateValidateService;
	
	
	public void login() throws Exception {
		JsonVo json = new JsonVo();
		
		String vc = this.validateCertificate();
		if(!vc.equals("")) {
			json.setMsg(vc);
			json.setSuccess(false);
			this.jsonOut(json);
			return;
		}
		
		// 检测用户名不能为空
		if (this.username == null || this.username.equals("")) {
			json.setMsg(Internation.language("not4"));
			json.setSuccess(false);
			this.jsonOut(json);
			return;
		}

		// 检测密码不能为空
		if (userid == null || this.userid.equals("")) {
			// return ERROR;
			json.setMsg(Internation.language("not3"));
			json.setSuccess(false);
			this.jsonOut(json);
			return;
		}
        
		Security sec = new Security();
		String loginPassword = userid;
		// 1 根据username 查询用户信息；
		SysUserPo sysUserPo = permissionService.findUserByLoginName(username);
		Subject subject = SecurityUtils.getSubject();
		SysUserPo shiroUser = (SysUserPo) subject.getPrincipal();
		// 如果用户不存在或登录密码不一致则当前已登录用户退出；并返回登录失败
		if (sysUserPo == null || !loginPassword.equals(sysUserPo.getLoginPassword())) {
			subject.logout();
			json.setMsg(Internation.language("not5"));
			json.setSuccess(false);
			this.jsonOut(json);
			return;
		}else {
			String platUser = sysUserPo.getPlatUser();
			if(platUser.charAt(0)!='1') {		
				subject.logout();
				json.setMsg(Internation.language("not7"));
				json.setSuccess(false);
				this.jsonOut(json);
				return;
				}
		}

		// 2 检查登录用户和shiro是否同一用户
		if (shiroUser == null || !sysUserPo.getLoginName().equals(shiroUser.getLoginName())) {
			subject.logout();
			UsernamePasswordToken token = new UsernamePasswordToken(username, loginPassword);
			subject.login(token);
		}
		
		shiroUser = (SysUserPo) subject.getPrincipal();
		String ipSession = subject.getSession().getHost() +"-"+ subject.getSession().getId();
		logger.info("登陆成功记录ip-seesion:{}",ipSession);
		subject.getSession().setAttribute(ipSession, ipSession);
		// 首先，初始化'是否是超级管理员标识'为false
		shiroUser.setIsManager(false);
		// 超级管理员的role_id默认为1，这是系统初始数据。
		if ("1".equals(shiroUser.getRoleId())) {
			// 如果是超级管理员，则'是否是超级管理员标识'被赋值为ture
			shiroUser.setIsManager(true);
		}
		
		json.setMsg(Internation.language("not6"));
		json.setSuccess(true);
		this.jsonOut(json);
		return;
	}
	
	public String validateCertificate() throws Exception {
		
		CertificatePo certificate = null;
		try {
			certificate = certificateValidateService.findCertificateName();
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
			return "出现异常";
		}
		
		if(certificate == null) { // 不验证证书
			return "";
		}
		
		// 验证证书是否存在
		if (certificate != null && certificate.getCertificateName() != null && !"".equals(certificate.getCertificateName())) {
			// 验证文件是否真实存在
			File file = new File(certificate.getCertificatePath() + File.separator + certificate.getCertificateName());// 获取文件路径
			if (!file.exists()) {
				return CertificateCheckEnum.NOT_FOUNT_CERTIFICATE.getValue();
			}
		} else {
			return CertificateCheckEnum.NOT_FOUNT_CERTIFICATE.getValue();
		}

		// 验证客户端时间是否早于上次登陆时间
		if (certificate != null && certificate.getLastLoginTime() != null) {
			long lastTime = TimeUtils.formatDateToInt(certificate.getLastLoginTime());
			long newTime = System.currentTimeMillis() / 1000;// 获得当前系统时间
			if (newTime + 24 * 60 * 60 < lastTime) {
				return CertificateCheckEnum.TIME_LIMIT_CERTIFICATE.getValue();
			}
		}
		// 验证证书是否过期
		ILicenseVerifyService verifyService = new LicenseVerifyService();
		String storeName = certificate.getCertificateName().split("\\.")[0];
		String storePath = certificate.getCertificatePath() + "/" + certificate.getCertificateName();
		if (verifyService.verify(storeName, storePath)) {

		} else {
			return CertificateCheckEnum.TIME_LIMIT_CERTIFICATE.getValue();
		}
		return "";
	}
	
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public ICertificateValidateService getCertificateValidateService() {
		return certificateValidateService;
	}

	public void setCertificateValidateService(
			ICertificateValidateService certificateValidateService) {
		this.certificateValidateService = certificateValidateService;
	}

	

}
