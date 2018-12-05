package com.git.cloud.common.support;

import java.util.HashMap;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;

public class ResolveObject {
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> getSubClass(Class clazz) throws InstantiationException, IllegalAccessException {
		Map<String, Class> classMap = new HashMap<String, Class> ();
		// 时间关系，目前现使用笨方法实现
		classMap.put("rrinfoList", BmSrRrinfoVo.class);
		classMap.put("attrValList", BmSrAttrValVo.class);
		classMap.put("rrVmRefList", BmSrRrVmRefPo.class);
		classMap.put("attrSelList", CloudServiceAttrSelPo.class);
		classMap.put("cmVmList", CmVmVo.class);
		return classMap;
	}
}
