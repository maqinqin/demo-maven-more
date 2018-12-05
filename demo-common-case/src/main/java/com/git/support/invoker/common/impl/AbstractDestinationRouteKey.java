package com.git.support.invoker.common.impl;

import com.git.support.common.MesgFlds;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public abstract class AbstractDestinationRouteKey {
	private String[] routeKeyFlds = null;
	private final char delimter = '_';

	public String getRouteKey(IDataObject reqData) {
		String key = "";

		if (routeKeyFlds == null || routeKeyFlds.length == 0) {
			return key;
		}

		HeaderDO header = reqData
				.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		if (header == null) {
			throw new RuntimeException("Get Request Header flds error!");
		}

		StringBuffer keybuf = new StringBuffer();

		for (int i = 0; i < routeKeyFlds.length; i++) {
			Object obj = header.get(routeKeyFlds[i]);
			if (obj != null) {
				String s = obj.toString();
				if (s != null && s.length() > 0) {
					keybuf.append(s);
					keybuf.append(delimter);
				}
			}
		}

		int endIndex = keybuf.length() - 1;
		while (endIndex >= 0 && keybuf.charAt(endIndex) == delimter)
			endIndex--;

		if (endIndex >= 0) {
			key = keybuf.substring(0, endIndex + 1);
		}
		return key;
	}

	public void setRouteKeyFlds(String[] routeKeyFlds) {
		this.routeKeyFlds = routeKeyFlds;
	}
}
