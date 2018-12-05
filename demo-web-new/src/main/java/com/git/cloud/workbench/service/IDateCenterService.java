package com.git.cloud.workbench.service;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.workbench.vo.DateCenterVo;

public interface IDateCenterService {

	/**
	 * 显示数据中心计算资源
	 * @return
	 */
	public ArrayList<DateCenterVo> showDataCenter();
}
