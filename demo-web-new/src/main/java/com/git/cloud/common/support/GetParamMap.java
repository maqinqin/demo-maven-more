package com.git.cloud.common.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetParamMap {

	public static Map<String, Object> getParamMap(Map<String, ?> requestParam) {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		List<String> keyList = getMapKeys(requestParam);
		Integer pageSize = 0;
		Integer currentPage = 0;
		String value;
		String orderBy = "";
		String sidx = "";
		String sord = "";
		String reloadFlag = "";
		for(int i=0 ; i<keyList.size() ; i++) {
			value = ((String[]) requestParam.get(keyList.get(i)))[0];
			if("rows".equals(keyList.get(i))) {
				pageSize = Integer.valueOf(value);
				paramMap.put("pageSize", pageSize);
			}
			if("page".equals(keyList.get(i))) {
				currentPage = Integer.valueOf(value);
			}
			if("sidx".equals(keyList.get(i))) {
				sidx = value;
			}
			if("sord".equals(keyList.get(i))) {
				sord = value;
			}
			if("reloadFlag".equals(keyList.get(i))) {
				reloadFlag = value;
			}
			paramMap.put(keyList.get(i), value);
		}
		if("true".equals(reloadFlag)) {
			paramMap.put("startIndex", 0);
			paramMap.put("page", "1");
		} else {
			paramMap.put("startIndex", getStartIndex(pageSize, currentPage));
		}
		if(!"".equals(sidx)) {
			orderBy = " order by " + sidx;
		}
		if(!"".equals(sord) && !"".equals(orderBy)) {
			orderBy += " " + sord;
		}
		if(orderBy.length() > 0) {
			paramMap.put("_orderby", orderBy);
		}
		return paramMap;
	}
	
	private static Integer getStartIndex(Integer pageSize, Integer currentPage) {
		Integer startIndex = 0;
		if (currentPage == null || currentPage.intValue() == 0) {
			startIndex = new Integer(0);
		} else {
			startIndex = (currentPage.intValue() - 1) * pageSize.intValue();
		}
		return startIndex;
	}
	
	private static List<String> getMapKeys(Map<String, ?> map)
	{
		List<String> keyList = new ArrayList<String> ();
		Set<String> keys = map.keySet();
		Object[] keyObj = keys.toArray();
		for(int i=0;i<keyObj.length;i++) {
			keyList.add((String) keyObj[i]);
		}
		return keyList;
	}
}
