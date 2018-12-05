package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmIpShowBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 保存主机的ip信息
 * @author lizhizhong
 * @date 2014-11-19 下午3:47:37
 * @version V1.0
 */
public class CmIpShowBo extends BaseBO {

	private static final long serialVersionUID = 1L;
	/** id */
	private String id;
	/** ip */
	private String ip;
	/** IP名称 */
	private String rm_ip_type_name;
	
	private String useId;//ip管理类型
	
	private String hostTypeId;//主机类型
	
	private String secureAreaId;//安全区域
	
	private String secureTireId;//安全层
	
	
	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}

	public String getSecureTireId() {
		return secureTireId;
	}

	public void setSecureTireId(String secureTireId) {
		this.secureTireId = secureTireId;
	}

	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRm_ip_type_name() {
		return rm_ip_type_name;
	}

	public void setRm_ip_type_name(String rm_ip_type_name) {
		this.rm_ip_type_name = rm_ip_type_name;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

}