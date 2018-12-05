package com.git.cloud.resmgt.network.model.vo;

import java.util.HashMap;

//网络地址区间信息
public class NetIPInfo {
	/**
	 * 网络地址类型编码，主要取值范围：
	 * HP	物理机生产地址
	 * HM	物理机管理地址
	 * HV	物理机管理-vMontion地址
	 * HL	物理机管理-ILO地址
	 * HF1	物理机管理-FSP1地址
	 * HF2	物理机管理-FSP2地址
	 * NP	NAS存储生产地址
	 * NM	NAS存储管理地址
	 * VP	虚拟机生产地址
	 * VM	虚拟机管理地址
	 * VR	虚拟机RAC心跳地址
	 */
	private String ipType;
    
	/**
	 * 网络C段用途编码，主要取值范围：
	 * XHP	X86物理机生产 
	 * XHM	X86物理机管理 
	 * XHV	X86物理机管理-vMotion 
	 * XHL	X86物理机管理-ILO 
	 * XVP	X86虚拟机生产
	 * XVM	X86虚拟机管理
	 * PHP	Power物理机生产 
	 * PHM	Power物理机管理
	 * PHF1	Power物理机管理-FSP1 
	 * PHF2	Power物理机管理-FSP2 
	 * PVP	Power虚拟机生产
	 * PVM	Power虚拟机管理
	 * PVR	Power虚拟机RAC心跳
	 * BHM  X86非虚拟化物理机管理
	 * WHM  Power非虚拟化物理机管理
	 * HHM  惠普非虚拟化物理机管理
	 */
	private String ipClassType;
		
	// 网络C段名称
	private String className;
		
	// 安全区域编码
	private String secureArea;
		
	/**
	 * 安全分层编码，主要取值范围：
	 * 1	互联网DMZ服务区
	 * 2	外联网DMZ服务区
	 * 3	开放服务区
	 * 4	运行管理服务区（管理支撑区）
	 * 5	主机平台服务区
	 * 6	广域网区
	 * 7	托管服务区
	 */
	private String secureTier;
		
	// VLAN ID
	private String vlanID;
	
	// 子网掩码
	private String ipMask;
	
	// 网关
	private String gateWay;
	
	// 分配的地址
	private String ip;
	
	private String id;
//	//20140108增加 设备ID
//	private Long deviceId;
//	
//	public Long getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(Long deviceId) {
//		this.deviceId = deviceId;
//	}

	/**
	 *  扩展信息，包括端口组/SEA信息等
	 *  HashMap Key主要取值范围：
	 *  VMware：
	 *    VMWARE_VS      :   虚拟交换机名称
	 *    VMWARE_PGTYP   :   端口组类型
	 *    VMWARE_PGNAM   :   端口组名称
	 *   Power:
	 *    POWER_SEA      :   Power VIOS SEA名称
	 *    POWER_NIC      :   Power VIOC NIC名称 
	 */
	private HashMap<String, String> extendInfo = new HashMap<String, String>();
	
	/**
	 * @return ipType 网络地址类型编码
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType 设置的网络地址类型编码值
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
	/**
	 * @return ipClassType 网络C段用途编码
	 */
	public String getIpClassType() {
		return ipClassType;
	}

	/**
	 * @param ipClassType 设置的网络C段用途编码值
	 */
	public void setIpClassType(String ipClassType) {
		this.ipClassType = ipClassType;
	}

	/**
	 * @return className 网络C段名称
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className 设置的网络C段名称值
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * @return secureArea 安全区域编码
	 */
	public String getSecureArea() {
		return secureArea;
	}

	/**
	 * @param secureArea 设置的安全区域编码值
	 */
	public void setSecureArea(String secureArea) {
		this.secureArea = secureArea;
	}

	/**
	 * @return secureTier 安全分区编码
	 */
	public String getSecureTier() {
		return secureTier;
	}

	/**
	 * @param secureTier 设置的安全分区编码值
	 */
	public void setSecureTier(String secureTier) {
		this.secureTier = secureTier;
	}
	
	/**
	 * @return VLAN ID
	 */
	public String getVlanID() {
		return vlanID;
	}

	/**
	 * @param vlanID 设置的VLAN ID值
	 */
	public void setVlanID(String vlanID) {
		this.vlanID = vlanID;
	}

	/**
	 * @return ipMask 子网掩码
	 */
	public String getIpMask() {
		return ipMask;
	}

	/**
	 * @param ipMask 设置的子网掩码值
	 */
	public void setIpMask(String ipMask) {
		this.ipMask = ipMask;
	}

	/**
	 * @return ipMask 网关
	 */
	public String getGateWay() {
		return gateWay;
	}

	/**
	 * @param gateWay 设置的网关值
	 */
	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}

	/**
	 * @return ip 分配的地址
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * @param ip 设置的分配的地址值
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * @return extendInfo 分配地址的扩展信息
	 */
	public HashMap<String, String> getExtendInfo() {
		return extendInfo;
	}

	/**
	 * @param extendInfo 设置的分配地址的扩展信息
	 */
	public void setExtendInfo(HashMap<String, String> extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
