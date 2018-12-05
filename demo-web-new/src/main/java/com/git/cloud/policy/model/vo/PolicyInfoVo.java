package com.git.cloud.policy.model.vo;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.BeanUtils;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.policy.model.PolicyInfoVoComparator;

/**
 * @Description
 * @author yangzhenhai
 * @version v1.0 2014-9-23
 */
public class PolicyInfoVo extends BaseBO implements Cloneable{

	private static final long serialVersionUID = 7966893751465801444L;

	private String vmId;

	private String hostId;// 物理机ID

	private String clusterId;// 集群ID

	private String cdpId;// 服务器角色ID

	private String poolId;// 资源池ID

	private Integer vmNum;// 虚机数

	private Integer cpu;// CPU核数

	private Integer usedCpu;// 已经分配的CPU核数

	private Double currentUseRate;// 当前cpu分配率

	private Integer orderNum;// 物理机编号

	private String deviceType;// 设备类型

	private ArrayList<PolicyInfoVo> childs;// 子集合

	private CurrentTypeEnum currentType;// 当前数据类型

	private String datastoreType;// cdp部署位置

	private int differential;// CPU使用情况的差值
	
	private Integer totalMem;
	private Integer usedMem;
	private Double memUsedRate;

	public int getVmDistriType() {
		return vmDistriType;
	}

	public void setVmDistriType(int vmDistriType) {
		this.vmDistriType = vmDistriType;
	}

	private int vmDistriType; // 虚机分配类型

	private PolicyInfoVoComparator policyInfoVoComparator = new PolicyInfoVoComparator();

	/**
	 * 计算CPU数量,使用CPU数量,CPU使用率
	 */
	public void computeVmNum() {
		switch (currentType) {
		case HOST:
			break;
		case CLUSTER:
		case CDP:
		case POOL:
			int sumvm = 0;
			if ((this.childs == null) || this.childs.isEmpty()) {
				this.vmNum = 0;
			} else {
				for (PolicyInfoVo child : this.childs) {
					child.computeVmNum();
					sumvm += child.getVmNum();
				}
				this.vmNum = sumvm;
			}
		default:
			return;
		}
	}
	/**
	 * 计算CPU数量,使用CPU数量,CPU使用率
	 */
	public void computeCPU() {
		switch (currentType) {
		case HOST:
			if ((this.cpu == null) || (this.cpu == 0)) {
				this.currentUseRate = null;
			} else {
				this.currentUseRate = this.usedCpu.doubleValue() / this.cpu.intValue();
			}
			break;
		case CLUSTER:
		case CDP:
		case POOL:
			int sumcpu = 0;
			int sumusedcpu = 0;
			int d1 = 0,
			d2 = 0;
			if ((this.childs == null) || this.childs.isEmpty()) {
				this.cpu = 0;
				this.usedCpu = 0;
				this.currentUseRate = null;
			} else {
				for (PolicyInfoVo child : this.childs) {
					child.computeCPU();
					sumcpu += child.getCpu();
					int used = child.getUsedCpu();
					sumusedcpu += used;
					if ((d1 == 0) || (used < d1)) {
						d1 = used;
					}
					if (used > d2) {
						d2 = used;
					}
				}
				this.cpu = sumcpu;
				this.usedCpu = sumusedcpu;
				this.differential = d2 - d1;
				if (this.cpu != 0) {
					this.currentUseRate = this.usedCpu.doubleValue() / this.cpu;
				}
			}
		default:
			return;
		}
	}
	
	/**
	 * 计算内存数量, 使用内存数量, 内存使用率
	 */
	public void calculateMem() {
		switch (currentType) {
		case HOST:
			if ((this.totalMem == null) || (this.totalMem == 0)) {
				this.memUsedRate = null;
			} else {
				this.memUsedRate = this.usedMem.doubleValue() / this.totalMem.longValue();
			}
			break;
		case CLUSTER:
		case CDP:
		case POOL:
			int sumTotal = 0;
			int sumUsed = 0;
			int d1 = 0, d2 = 0;
			if ((this.childs == null) || this.childs.isEmpty()) {
				this.totalMem = 0;
				this.usedMem = 0;
				this.memUsedRate = null;
			} else {
				for (PolicyInfoVo child : this.childs) {
					child.calculateMem();
					sumTotal += child.getTotalMem();
					int used = child.getUsedMem();
					sumUsed += used;
					if ((d1 == 0) || (used < d1)) {
						d1 = used;
					}
					if (used > d2) {
						d2 = used;
					}
				}
				this.totalMem = sumTotal;
				this.usedMem = sumUsed;
				this.differential = d2 - d1;
				if (this.totalMem != 0) {
					this.currentUseRate = this.usedMem.doubleValue() / this.totalMem;
				}
			}
		default:
			return;
		}
	}

	@Override
	public PolicyInfoVo clone() throws CloneNotSupportedException {
		PolicyInfoVo policyInfoVo = new PolicyInfoVo();
		BeanUtils.copyProperties(this, policyInfoVo);
		return policyInfoVo;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getCdpId() {
		return cdpId;
	}

	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public Integer getVmNum() {
		return vmNum;
	}

	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getUsedCpu() {
		return usedCpu;
	}

	public void setUsedCpu(Integer usedCpu) {
		this.usedCpu = usedCpu;
	}

	public Double getCurrentUseRate() {
		return currentUseRate;
	}

	public void setCurrentUseRate(Double currentUseRate) {
		this.currentUseRate = currentUseRate;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public ArrayList<PolicyInfoVo> getChilds() {
		return childs;
	}

	public ArrayList<PolicyInfoVo> getChilds(boolean isAsc) {
		if (this.childs != null) {
			this.policyInfoVoComparator.setAsc(isAsc);
			Collections.sort(this.childs, this.policyInfoVoComparator);
		}
		return this.childs;
	}

	public ArrayList<PolicyInfoVo> getChilds(boolean isAsc, String sortField2, boolean isAsc2) {
		if (this.childs != null) {
			this.policyInfoVoComparator.setAsc(isAsc);
			this.policyInfoVoComparator.setSortField2(sortField2, isAsc2);
			Collections.sort(this.childs, this.policyInfoVoComparator);
		}
		return this.childs;
	}

	public ArrayList<PolicyInfoVo> getChilds(String sortField1, boolean isAsc, String sortField2, boolean isAsc2) {
		if (this.childs != null) {
			this.policyInfoVoComparator.setSortField1(sortField1, isAsc);
			this.policyInfoVoComparator.setSortField2(sortField2, isAsc2);
			Collections.sort(this.childs, this.policyInfoVoComparator);
		}
		return this.childs;
	}

	public void setChilds(ArrayList<PolicyInfoVo> childs) {
		if (this.childs != null) {
			this.childs.clear();
			this.childs = null;
		}
		this.childs = childs;
	}

	public void addChild(PolicyInfoVo child) {
		if (this.childs == null) {
			this.childs = new ArrayList<PolicyInfoVo>();
		}
		this.childs.add(child);
	}

	public void addChilds(ArrayList<PolicyInfoVo> childs) {
		if (this.childs == null) {
			this.childs = new ArrayList<PolicyInfoVo>();
		}
		this.childs.addAll(childs);
	}

	public CurrentTypeEnum getCurrentType() {
		return currentType;
	}

	public void setCurrentType(CurrentTypeEnum currentType) {
		this.currentType = currentType;
	}

	public String getDatastoreType() {
		return datastoreType;
	}

	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

	public int getDifferential() {
		return differential;
	}

	public void setDifferential(int differential) {
		this.differential = differential;
	}

	public Integer getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(Integer totalMem) {
		this.totalMem = totalMem;
	}

	public Integer getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(Integer usedMem) {
		this.usedMem = usedMem;
	}

	public Double getMemUsedRate() {
		return memUsedRate;
	}

	public void setMemUsedRate(Double memUsedRate) {
		this.memUsedRate = memUsedRate;
	}

	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
