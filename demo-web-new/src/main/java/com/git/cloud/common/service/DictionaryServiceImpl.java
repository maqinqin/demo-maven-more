package com.git.cloud.common.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.model.AdminDicPo;

/**
 * 查询字典信息SERVICE层实现类
 * @author liangshuang
 * @date 2014-9-17 上午11:23:01
 * @version v1.0
 *
 */
public class DictionaryServiceImpl implements IDictionaryService {
	
	public ICommonDAO comDAO;

	public ICommonDAO getComDAO() {
		return comDAO;
	}

	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}

	private static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
	
	@Override
	public <T extends AdminDicPo> List<T> getByTypeCode(String dicTypeCode)
			throws Exception {
		logger.info("获取数据字典 Service Begin......");
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("dicTypeCode", dicTypeCode);
		List<T> dicList=comDAO.findListByParam("getDicByDicTypeCode", map);
		return dicList;
	}

	@Override
	public <T extends AdminDicPo> List<T> getByCode(String dicCode,String dicTypeCode) throws Exception {
		logger.info("获取数据字典 Service Begin......");
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("dicCode", dicCode);
		map.put("dicTypeCode", dicTypeCode);
		List<T> dicList=comDAO.findListByParam("getDicByDicTypeCode", map);
		return dicList;
	}

	@Override
	public <T extends AdminDicPo> List<T> getAllDic() throws Exception {
		logger.info("获取全部数据字典 Service Begin......");
		List<T> dicList=comDAO.findAll("getAllDic");
		return dicList;
	}

	

}
