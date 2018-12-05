package com.git.cloud.policy.model;

import java.util.Comparator;

import com.git.cloud.policy.model.vo.PolicyInfoVo;

/**
 * @Description
 * @author yangzhenhai
 * @version v1.0 2014-9-26
 */
public class PolicyInfoVoComparator implements Comparator<PolicyInfoVo> {

	private boolean isAsc;
	private boolean isAsc2;
	private String sortField1;
	private String sortField2;

	public PolicyInfoVoComparator() {

	}

	public PolicyInfoVoComparator(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public PolicyInfoVoComparator(boolean isAsc, String sortField1) {
		this.isAsc = isAsc;
		this.sortField1 = sortField1;
	}

	public PolicyInfoVoComparator(boolean isAsc, boolean isAsc2, String sortField1, String sortField2) {
		this.isAsc = isAsc;
		this.isAsc2 = isAsc2;
		this.sortField1 = sortField1;
		this.sortField2 = sortField2;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public void setAsc2(boolean isAsc2) {
		this.isAsc2 = isAsc2;
	}

	public void setSortField1(String sortField1, boolean isAsc) {
		this.sortField1 = sortField1;
		this.isAsc = isAsc;
	}

	public void setSortField2(String sortField2, boolean isAsc2) {
		this.sortField2 = sortField2;
		this.isAsc2 = isAsc2;
	}

	@Override
	public int compare(PolicyInfoVo o1, PolicyInfoVo o2) {
		int rs = 0;
		rs = this.compare(o1, o2, this.sortField1);
		if (rs == 0 || this.sortField1 == null) {
			if (this.sortField2 != null) {
				rs = this.compare(o1, o2, this.sortField2);
				if (this.isAsc2) {
					return rs;
				} else {
					return -rs;
				}
			}
		}
		if (this.isAsc) {
			return rs;
		} else {
			return -rs;
		}
	}

	/**
	 * 
	 * @Description:compare(根据字段排序)
	 * @param policyInfoVo1
	 * @param policyInfoVo2
	 * @param sortField
	 * @return
	 */
	private int compare(PolicyInfoVo policyInfoVo1, PolicyInfoVo policyInfoVo2, String sortField) {
		int rs = 0;
		if ((sortField == null) || sortField.equals("vmNum")) {
			rs = this.compareInteger(policyInfoVo1.getVmNum(), policyInfoVo2.getVmNum());
		} else if (sortField.equals("Differential")) {
			rs = this.compareInteger(policyInfoVo1.getDifferential(), policyInfoVo2.getDifferential());
		} else if (sortField.equals("orderNum")) {
			rs = this.compareInteger(policyInfoVo1.getOrderNum(), policyInfoVo2.getOrderNum());
		} else if (sortField.equals("vmDistriType")) {
			rs = this.compareInteger(policyInfoVo1.getVmDistriType(), policyInfoVo2.getVmDistriType());
		} else if (sortField.equals("CurrentUseRate")) {
			rs = this.compareDouble(policyInfoVo1.getCurrentUseRate(), policyInfoVo2.getCurrentUseRate());
		}
		return rs;
	}

	/**
	 * 
	 * @Description:compareLong(这里用一句话描述这个方法的作用)
	 * @param long1
	 * @param long2
	 * @return
	 */
	private int compareLong(Long long1, Long long2) {
		int rs = 0;
		if ((long1 != null) && (long2 != null)) {
			rs = long1.compareTo(long2);
		} else if (long1 != null) {
			rs = 1;
		} else {
			rs = -1;
		}
		return rs;
	}

	/**
	 * 
	 * @Description:compareInteger(这里用一句话描述这个方法的作用)
	 * @param integer1
	 * @param integer2
	 * @return
	 */
	private Integer compareInteger(Integer integer1, Integer integer2) {
		int rs = 0;
		if ((integer1 != null) && (integer2 != null)) {
			rs = integer1.compareTo(integer2);
		} else if (integer1 != null) {
			rs = 1;
		} else {
			rs = -1;
		}
		return rs;
	}

	/**
	 * 
	 * @Description:compareDouble(这里用一句话描述这个方法的作用)
	 * @param double1
	 * @param double2
	 * @return
	 */
	private int compareDouble(Double double1, Double double2) {
		int rs = 0;
		if ((double1 != null) && (double2 != null)) {
			rs = double1.compareTo(double2);
		} else if (double1 != null) {
			rs = 1;
		} else {
			rs = -1;
		}
		return rs;
	}

}
