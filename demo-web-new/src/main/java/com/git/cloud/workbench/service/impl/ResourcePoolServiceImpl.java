package com.git.cloud.workbench.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.workbench.service.IResourcePoolService;
import com.git.cloud.workbench.vo.DateCenterVo;
import com.git.cloud.workbench.vo.ResourcePoolVo;

public class ResourcePoolServiceImpl implements IResourcePoolService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ICommonDAO iCommonDAO;
	
	
	
	public void setiCommonDAO(ICommonDAO iCommonDAO) {
		this.iCommonDAO = iCommonDAO;
	}



	/**
	 * 显示资源池计算资源
	 */
	@Override
	public ArrayList<ResourcePoolVo> showResourcePoolById(String id) {
		List<ResourcePoolVo> list = new ArrayList<ResourcePoolVo>();
//		ResourcePoolVo vo = new ResourcePoolVo();
//		vo.setCpu("40");
//		vo.setPname("CDP");
//		vo.setRam("30");
//		vo.setId(1);
//		list.add(vo);
//		ResourcePoolVo vo1 = new ResourcePoolVo();
//		vo1.setCpu("20");
//		vo1.setPname("物理机");
//		vo1.setRam("10");
//		vo1.setId(2);
//		list.add(vo1);
//		ResourcePoolVo vo2 = new ResourcePoolVo();
//		vo2.setCpu("50");
//		vo2.setPname("虚拟机");
//		vo2.setRam("60");
//		vo2.setId(3);
//		list.add(vo2);
//		ResourcePoolVo vo3 = new ResourcePoolVo();
//		vo3.setCpu("70");
//		vo3.setPname("集群");
//		vo3.setRam("80");
//		vo3.setId(4);
//		list.add(vo3);
		try {
			list = iCommonDAO.findByID("selectResourcePool", id+"");
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		//System.out.println("list------------>"+list.size());
		return (ArrayList<ResourcePoolVo>) list;
	}

}
