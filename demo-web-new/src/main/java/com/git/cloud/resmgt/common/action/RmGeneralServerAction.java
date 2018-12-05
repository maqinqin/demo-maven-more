/**
 * @Title:RmGeneralServerAction.java
 * @Package:com.git.cloud.resmgt.common.action
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-6 下午04:58:09
 * @version V1.0
 */
package com.git.cloud.resmgt.common.action;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.service.IRmGeneralServerService;

/**
 * @ClassName:RmGeneralServerAction
 * @Description:TODO
 * @author LINZI
 * @date 2015-3-6 下午04:58:09
 *
 *
 */
public class RmGeneralServerAction extends BaseAction<Object> {
	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 8942143620590312484L;
	private RmGeneralServerVo rmGeneralServerVo;
	private IRmGeneralServerService rmGeneralServerService;
	private List<Map<String, Object>> resultLst;

	public IRmGeneralServerService getRmGeneralServerService() {
		return rmGeneralServerService;
	}

	public void setRmGeneralServerService(IRmGeneralServerService rmGeneralServerService) {
		this.rmGeneralServerService = rmGeneralServerService;
	}

	public RmGeneralServerVo getRmGeneralServerVo() {
		return rmGeneralServerVo;
	}

	public void setRmGeneralServerVo(RmGeneralServerVo rmGeneralServerVo) {
		this.rmGeneralServerVo = rmGeneralServerVo;
	}
	public String index() {
		return SUCCESS;
	}
	public void queryRmGeneralServer() throws Exception{
		Pagination<RmGeneralServerVo> page = rmGeneralServerService.queryRmGeneralServerPagination(this.getPaginationParam());
		this.jsonOut(page);
	}
	
	public void queryServerType() throws Exception{
		resultLst = rmGeneralServerService.queryServerType();
		this.jsonOut(resultLst);
	}
	public void queryDataCenter() throws Exception{
		resultLst = rmGeneralServerService.queryDataCenter();
		this.jsonOut(resultLst);
	}
	public void saveRmGeneralServer() throws Exception {
		rmGeneralServerVo.setId(UUIDGenerator.getUUID());
		rmGeneralServerVo.setIsActive(IsActiveEnum.YES.getValue());
		rmGeneralServerService.saveRmGeneralServer(rmGeneralServerVo);
	}
	public void deleteRmGeneralServer() throws Exception{
		rmGeneralServerService.deleteRmGeneralServer(rmGeneralServerVo.getId().split(","));
	}
	public void queryRmGeneralServerById() throws Exception {
		rmGeneralServerVo = rmGeneralServerService.queryRmGeneralServerById(rmGeneralServerVo.getId());
		jsonOut(rmGeneralServerVo);
	}
	public void updateRmGeneralServer() throws Exception {
		rmGeneralServerService.updateRmGeneralServer(rmGeneralServerVo);
	}
	public void checkServerName() throws Exception {
		boolean result = rmGeneralServerService.checkServerName(rmGeneralServerVo);
		this.ObjectOut(result);
	}
}
