package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
* @ClassName: CmVmSynInfo  
* @Description: 虚拟机信息同步查询PO
* @author WangJingxin
* @date 2016年7月29日 上午11:27:46  
*
 */
public class CmVmSynInfoPo extends BaseBO implements java.io.Serializable {
	
	private String vmName;
	private String vmIp;
	private String vmHostIp;
	private String dsName;
	private String rcName;
	private String mgtName;
	private String state;
	private String id;
	private String vmHostId;
	private String dsId;
	private String dsType;
    private String cpu;
	private String mem;
	private String disk;
	private String poolName;
	
	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getVmHostIp() {
		return vmHostIp;
	}

	public void setVmHostIp(String vmHostIp) {
		this.vmHostIp = vmHostIp;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getRcName() {
		return rcName;
	}

	public void setRcName(String rcName) {
		this.rcName = rcName;
	}

	public String getMgtName() {
		return mgtName;
	}

	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVmHostId() {
		return vmHostId;
	}

	public void setVmHostId(String vmHostId) {
		this.vmHostId = vmHostId;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}


	/**  
	* 
	*/  
	private static final long serialVersionUID = -942505816281627181L;

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
