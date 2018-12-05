package com.git.cloud.workbench.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.workbench.dao.IDateCenterDAO;
import com.git.cloud.workbench.service.IDateCenterService;
import com.git.cloud.workbench.vo.DateCenterVo;

public class DateCenterServiceImpl implements IDateCenterService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private IDateCenterDAO iDateCenterDAO;
	
	
	
	public void setiDateCenterDAO(IDateCenterDAO iDateCenterDAO) {
		this.iDateCenterDAO = iDateCenterDAO;
	}



	/**
	 * 显示数据中心计算资源
	 */
	@Override
	public ArrayList<DateCenterVo> showDataCenter() {
//		List<DateCenterVo> list = new ArrayList<DateCenterVo>();
//		DateCenterVo vo = new DateCenterVo();
//		vo.setCpu("40");
//		vo.setDname("生产数据中心");
//		vo.setRam("30");
//		vo.setId(1);
//		list.add(vo);
//		DateCenterVo vo1 = new DateCenterVo();
//		vo1.setCpu("20");
//		vo1.setDname("灾备数据中心");
//		vo1.setRam("10");
//		vo1.setId(2);
//		list.add(vo1);
//		DateCenterVo vo2 = new DateCenterVo();
//		vo2.setCpu("50");
//		vo2.setDname("测试数据中心");
//		vo2.setRam("60");
//		vo2.setId(3);
//		list.add(vo2);
//		DateCenterVo vo3 = new DateCenterVo();
//		vo3.setCpu("70");
//		vo3.setDname("安全数据中心");
//		vo3.setRam("80");
//		vo3.setId(4);
//		list.add(vo3);
		
		List<DateCenterVo> list = new ArrayList<DateCenterVo>();
		try {
			list = iDateCenterDAO.findAll("selectDataCenterALL");
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
			
		
		//System.out.println("list------------>"+list.size());
		return (ArrayList<DateCenterVo>) list;
	}

}
