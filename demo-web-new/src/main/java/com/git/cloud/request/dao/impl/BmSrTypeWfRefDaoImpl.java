/**
 * @Title:BmSrTypeWfRefDaoImpl.java
 * @Package:com.git.cloud.request.dao.impl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-13 下午4:10:48
 * @version V1.0
 */
package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.dao.IBmSrTypeWfRefDao;
import com.git.cloud.request.model.po.BmSrTypeWfRefPo;

/**
 * 服务申请类别和流程关系
 * @ClassName:BmSrTypeWfRefDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-13 下午4:10:56
 */
public class BmSrTypeWfRefDaoImpl extends CommonDAOImpl implements IBmSrTypeWfRefDao {

	public BmSrTypeWfRefPo findBmSrTypeWfRefBySrTypeMark(String srTypeMark) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("srTypeMark", srTypeMark);
		List<BmSrTypeWfRefPo> refList = this.findListByParam("findBmSrTypeWfRefBySrTypeMark", paramMap);
		BmSrTypeWfRefPo ref = null;
		if(refList != null && refList.size() > 0) {
			ref = refList.get(0);
		}
		return ref;
	}
}
