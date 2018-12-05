package com.git.cloud.workbench.service;

import java.util.ArrayList;

import com.git.cloud.workbench.vo.ResourcePoolVo;

public interface IResourcePoolService {

	/**
	 * 显示资源池计算资源
	 * @return
	 */
	public ArrayList<ResourcePoolVo> showResourcePoolById(String id);
}
