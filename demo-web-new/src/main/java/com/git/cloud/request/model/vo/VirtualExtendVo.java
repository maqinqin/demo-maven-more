package com.git.cloud.request.model.vo;

import java.io.Serializable;
import java.util.List;

public class VirtualExtendVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BmSrVo bmSr;
	private List<BmSrRrinfoVo> rrinfoList;
	
	public BmSrVo getBmSr() {
		return bmSr;
	}
	public void setBmSr(BmSrVo bmSr) {
		this.bmSr = bmSr;
	}
	public List<BmSrRrinfoVo> getRrinfoList() {
		return rrinfoList;
	}
	public void setRrinfoList(List<BmSrRrinfoVo> rrinfoList) {
		this.rrinfoList = rrinfoList;
	}
}
