package com.git.cloud.iaas.hillstone.model;

import java.io.UnsupportedEncodingException;

public class Cookie {

	private String token;
	private String platform;
	private String hw_platform;
	private String host_name;
	private String company;
	private String oemid;
	private String vsysid;
	private String vsysname;
	private String role;
	private String license;
	private String httpProtocol;
	private String soft_version;
	private String username;
	private String overseaLicense;
	private String HS_frame_lang = "zh_CN";

	public Cookie() {

	}

	public Cookie(String token, String platform, String hw_platform, String host_name, String company, String oemid,
			String vsysid, String vsysname, String role, String license, String httpProtocol, String soft_version,
			String username, String overseaLicense, String HS_frame_lang) throws UnsupportedEncodingException {
		this.token = urlencode(token);
		this.platform = urlencode(platform);
		this.hw_platform = urlencode(hw_platform);
		this.host_name = urlencode(host_name);
		this.company = urlencode(company);
		this.oemid = urlencode(oemid);
		this.vsysid = urlencode(vsysid);
		this.vsysname = urlencode(vsysname);
		this.role = urlencode(role);
		this.license = urlencode(license);
		this.httpProtocol = urlencode(httpProtocol);
		this.soft_version = urlencode(soft_version);
		this.username = urlencode(username);
		this.overseaLicense = urlencode(overseaLicense);
		this.HS_frame_lang = urlencode(HS_frame_lang);

	}

	public String urlencode(String item) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(item, "utf-8");
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getHw_platform() {
		return hw_platform;
	}

	public void setHw_platform(String hw_platform) {
		this.hw_platform = hw_platform;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid;
	}

	public String getVsysid() {
		return vsysid;
	}

	public void setVsysid(String vsysid) {
		this.vsysid = vsysid;
	}

	public String getVsysname() {
		return vsysname;
	}

	public void setVsysname(String vsysname) {
		this.vsysname = vsysname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getHttpProtocol() {
		return httpProtocol;
	}

	public void setHttpProtocol(String httpProtocol) {
		this.httpProtocol = httpProtocol;
	}

	public String getSoft_version() {
		return soft_version;
	}

	public void setSoft_version(String soft_version) {
		this.soft_version = soft_version;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (vsysname != null && vsysname.equals("root")) {
			this.username = "hillstone";
		} else {
			this.username = "admin";
		}
	}

	public String getOverseaLicense() {
		return overseaLicense;
	}

	public void setOverseaLicense(String overseaLicense) {
		this.overseaLicense = overseaLicense;
	}

	public String getHS_frame_lang() {
		return HS_frame_lang;
	}

	public void setHS_frame_lang(String hS_frame_lang) {
		HS_frame_lang = hS_frame_lang;
	}

	/***
	 * this.token = token; this.platform = platform; this.hw_platform =
	 * hw_platform; this.company = company; this.oemid = oemid; this.vsysid =
	 * vsysid; this.vsysname = vsysname; this.role = role; this.license =
	 * license; this.httpProtocol = httpProtocol; this.username = username;
	 * this.overseaLicense = overseaLicense; this.HS_frame_lang = HS_frame_lang;
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("token=" + this.token + ";");
		sb.append("platform=" + this.platform + ";");
		sb.append("hw_platform=" + this.hw_platform + ";");
		sb.append("host_name=" + this.host_name + ";");
		sb.append("company=" + this.company + ";");
		sb.append("oemid=" + this.oemid + ";");
		sb.append("vsysid=" + this.vsysid + ";");
		sb.append("vsysName=" + this.vsysname + ";");
		sb.append("role=" + this.role + ";");
		sb.append("license=" + this.license + ";");
		sb.append("httpProtocol=" + this.httpProtocol + ";");
		sb.append("soft_version=" + this.soft_version + ";");
		sb.append("username=" + this.username + ";");
		sb.append("overseaLicense=" + this.overseaLicense + ";");
		sb.append("HS.frame.lang=" + this.HS_frame_lang);

		return sb.toString();
	}
}
