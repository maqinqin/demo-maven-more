/**
 * @Title:VirtualRecycleVo.java
 * @Package:com.git.cloud.request.model.vo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-10 上午10:22:43
 * @version V1.0
 */
package com.git.cloud.request.model.vo;

import java.util.List;

import com.git.cloud.resmgt.common.model.vo.CmVmVo;

/**
 * @ClassName:VirtualRecycleVo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-10 上午10:22:43
 */
public class VirtualRecycleVo {
	private BmSrVo bmSr;
	private List<CmVmVo> cmVmList;
	
	public BmSrVo getBmSr() {
		return bmSr;
	}
	public void setBmSr(BmSrVo bmSr) {
		this.bmSr = bmSr;
	}
	public List<CmVmVo> getCmVmList() {
		return cmVmList;
	}
	public void setCmVmList(List<CmVmVo> cmVmList) {
		this.cmVmList = cmVmList;
	}
}
