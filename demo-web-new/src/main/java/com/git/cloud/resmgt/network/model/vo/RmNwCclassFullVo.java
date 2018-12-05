package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwCclassFullVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 6997663773772673432L;
	private String cclassId;
	private String platformId;
	private String virtualTypeId;
	private String hostTypeId;
	private String useId;
	private String bclassId;
	private String useRelCode;
	private String secureAreaId;
	private String secureTierId;
	private String cclassName;
	private String subnetmask;
	private String gateway;
	private String vlanId;
	private Long ipStart;
	private Long ipEnd;
	private Long aclassIp;
	private Long bclassIp;
	private Long cclassIp;
	private Long ipTotalCnt;
	private Long ipAvailCnt;
	private String datacenterId;
	private String convergeId;
	private String isActive;
//
    private String bclassName;//B段名称
    private String datacenterName;//数据中心名称
    private Long ipNum;//已使用IP数=vIpNum+hIpNum
    private Long vIpNum;//虚拟机使用IP数
    private Long hIpNum;//物理机使用IP数
    private Long mIpNum;//管理
    private Long pIpNum;//生产
    private Long iLoIpNum;//ILO
    private Long vmoIpNum;//VMO
    private Long priIpNum;
    private Long fsp1Num;
    private Long fsp2Num;
	/** default constructor */
	public RmNwCclassFullVo() {
	}

	/** minimal constructor */
	public RmNwCclassFullVo(String cclassId,String bclassId) {
		this.cclassId = cclassId;
		this.bclassId=bclassId;
	}

	/** full constructor */

	public RmNwCclassFullVo(String cclassId, String platformId,
			String virtualTypeId, String hostTypeId, String useId,
			String bclassId, String useRelCode, String secureAreaId,
			String secureTierId, String cclassName, String subnetmask,
			String gateway, String vlanId, Long ipStart, Long ipEnd,
			Long aclassIp, Long bclassIp, Long cclassIp, Long ipTotalCnt,
			Long ipAvailCnt, String datacenterId, String convergeId,
			String isActive, String bclassName, String datacenterName,
			Long ipNum, Long vIpNum, Long hIpNum, Long mIpNum, Long pIpNum,
			Long iLoIpNum, Long vmoIpNum, Long priIpNum, Long fsp1Num,
			Long fsp2Num) {
		super();
		this.cclassId = cclassId;
		this.platformId = platformId;
		this.virtualTypeId = virtualTypeId;
		this.hostTypeId = hostTypeId;
		this.useId = useId;
		this.bclassId = bclassId;
		this.useRelCode = useRelCode;
		this.secureAreaId = secureAreaId;
		this.secureTierId = secureTierId;
		this.cclassName = cclassName;
		this.subnetmask = subnetmask;
		this.gateway = gateway;
		this.vlanId = vlanId;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.aclassIp = aclassIp;
		this.bclassIp = bclassIp;
		this.cclassIp = cclassIp;
		this.ipTotalCnt = ipTotalCnt;
		this.ipAvailCnt = ipAvailCnt;
		this.datacenterId = datacenterId;
		this.convergeId = convergeId;
		this.isActive = isActive;
		this.bclassName = bclassName;
		this.datacenterName = datacenterName;
		this.ipNum = ipNum;
		this.vIpNum = vIpNum;
		this.hIpNum = hIpNum;
		this.mIpNum = mIpNum;
		this.pIpNum = pIpNum;
		this.iLoIpNum = iLoIpNum;
		this.vmoIpNum = vmoIpNum;
		this.priIpNum = priIpNum;
		this.fsp1Num = fsp1Num;
		this.fsp2Num = fsp2Num;
	}
	// Property accessors

	public String getDatacenterName() {
		return datacenterName;
	}

	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}
	public String getCclassId() {
		return cclassId;
	}

	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
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

	public String getBclassId() {
		return bclassId;
	}

	public void setBclassId(String bclassId) {
		this.bclassId = bclassId;
	}

	public String getUseRelCode() {
		return useRelCode;
	}

	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}

	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}

	public String getSecureTierId() {
		return secureTierId;
	}

	public void setSecureTierId(String secureTierId) {
		this.secureTierId = secureTierId;
	}

	public String getCclassName() {
		return cclassName;
	}

	public void setCclassName(String cclassName) {
		this.cclassName = cclassName;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getVlanId() {
		return vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public Long getIpStart() {
		return ipStart;
	}

	public void setIpStart(Long ipStart) {
		this.ipStart = ipStart;
	}

	public Long getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(Long ipEnd) {
		this.ipEnd = ipEnd;
	}

	public Long getAclassIp() {
		return aclassIp;
	}

	public void setAclassIp(Long aclassIp) {
		this.aclassIp = aclassIp;
	}

	public Long getBclassIp() {
		return bclassIp;
	}

	public void setBclassIp(Long bclassIp) {
		this.bclassIp = bclassIp;
	}

	public Long getCclassIp() {
		return cclassIp;
	}

	public void setCclassIp(Long cclassIp) {
		this.cclassIp = cclassIp;
	}

	public Long getIpTotalCnt() {
		return ipTotalCnt;
	}

	public void setIpTotalCnt(Long ipTotalCnt) {
		this.ipTotalCnt = ipTotalCnt;
	}

	public Long getIpAvailCnt() {
		return ipAvailCnt;
	}

	public void setIpAvailCnt(Long ipAvailCnt) {
		this.ipAvailCnt = ipAvailCnt;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getConvergeId() {
		return convergeId;
	}

	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	

	public String getBclassName() {
		return bclassName;
	}

	public void setBclassName(String bclassName) {
		this.bclassName = bclassName;
	}

	public Long getIpNum() {
		return ipNum;
	}

	public void setIpNum(Long ipNum) {
		this.ipNum = ipNum;
	}

	public Long getvIpNum() {
		return vIpNum;
	}

	public void setvIpNum(Long vIpNum) {
		this.vIpNum = vIpNum;
	}

	public Long gethIpNum() {
		return hIpNum;
	}

	public void sethIpNum(Long hIpNum) {
		this.hIpNum = hIpNum;
	}

	public Long getmIpNum() {
		return mIpNum;
	}

	public void setmIpNum(Long mIpNum) {
		this.mIpNum = mIpNum;
	}

	public Long getpIpNum() {
		return pIpNum;
	}

	public void setpIpNum(Long pIpNum) {
		this.pIpNum = pIpNum;
	}

	public Long getiLoIpNum() {
		return iLoIpNum;
	}

	public void setiLoIpNum(Long iLoIpNum) {
		this.iLoIpNum = iLoIpNum;
	}

	public Long getVmoIpNum() {
		return vmoIpNum;
	}

	public void setVmoIpNum(Long vmoIpNum) {
		this.vmoIpNum = vmoIpNum;
	}

	public Long getPriIpNum() {
		return priIpNum;
	}

	public void setPriIpNum(Long priIpNum) {
		this.priIpNum = priIpNum;
	}

	public Long getFsp1Num() {
		return fsp1Num;
	}

	public void setFsp1Num(Long fsp1Num) {
		this.fsp1Num = fsp1Num;
	}

	public Long getFsp2Num() {
		return fsp2Num;
	}

	public void setFsp2Num(Long fsp2Num) {
		this.fsp2Num = fsp2Num;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
