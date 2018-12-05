package com.git.cloud.common.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author Spring.Cao
 * @version v1.0 2013-03-22
 */
@Repository  
public class CommonDAOImpl extends SqlMapClientDaoSupport implements ICommonDAO {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	public CommonDAOImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public <T extends BaseBO> void batchInsert(String method, List<T> boList) 
			throws RollbackableBizException {
		getSqlMapClientTemplate().insert(method, boList);
	}
	
	
	/**
	 * 
	 * @Title: batchUpdate
	 * @Description: 根据对象列表，批量更新
	 * @param method
	 * @param boList
	 * @throws RollbackableBizException void    返回类型
	 * @throws
	 */
	public <T extends BaseBO> void batchUpdate(String method, List<T> boList) 
			throws RollbackableBizException {
		getSqlMapClientTemplate().update(method, boList);
	}
	
	@Override
	public <T extends BaseBO> void save(String method, T bo)
			throws RollbackableBizException {
		bo.setCreateDateTime(new Date());
		getSqlMapClientTemplate().insert(method, bo);
	}

	@Override
	public <T extends BaseBO> int delete(String method, T bo)
			throws RollbackableBizException {
		String bizId = String.valueOf(bo.getBizId());
		return getSqlMapClientTemplate().delete(method, bizId);
	}

	@Override
	public <T extends BaseBO> int update(String method, T bo)
			throws RollbackableBizException {
		bo.setUpdateDateTime(new Date());
		return getSqlMapClientTemplate().update(method, bo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseBO> List<T> findAll(String method)
			throws RollbackableBizException {
		List<T> list = getSqlMapClientTemplate().queryForList(method);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseBO> List<T> findByID(String method, String bizId)
			throws RollbackableBizException {
		List<T> list = getSqlMapClientTemplate().queryForList(method,bizId);
		return list;
	}

	public <T extends BaseBO> T findVmMgServerByType(String method, String type){
		return (T) this.getSqlMapClientTemplate().queryForObject(method);
		//return (T) this.getSqlMapClientTemplate().queryForObject(method, type);
	}
	@Override
	public <T extends BaseBO> T callProc(String method, T bo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> commonQuery(String method) {
		return this.getSqlMapClientTemplate().queryForList(method);
	}

	@Override
	public <T extends BaseBO> int delete(String method, String bizId)
			throws RollbackableBizException {
		return getSqlMapClientTemplate().delete(method, bizId);
	}
	
	public <T extends BaseBO> int deleteForParam(String method, Map<String, ?> paramMap)
			throws RollbackableBizException {
		return getSqlMapClientTemplate().delete(method, paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Pagination<T> pageQuery(String sqlTotal, String sqlPage, PaginationParam paginationParam) {
		int record = (Integer) getSqlMapClientTemplate().queryForObject(sqlTotal, paginationParam.getParams());
		List<T> list = (List<T>)getSqlMapClientTemplate().queryForList(sqlPage, paginationParam.getParams());
		Pagination<T> pagination = new Pagination<T>(paginationParam);
		pagination.setRecord(record);
		int total = pagination.getRecord() / pagination.getRows() + (pagination.getRecord() % pagination.getRows() == 0 ? 0 : 1);
		pagination.setTotal(total);
		pagination.setDataList(list);
		return pagination;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.service.base.ICommonDAO#findObjectByID(java.lang.String, java.lang.String)
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseBO> T findObjectByID(String method, String bizId)
			throws RollbackableBizException {
		
		return (T) getSqlMapClientTemplate().queryForObject(method,bizId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends BaseBO> List<T> findListByParam(String method, Map map) {
		
		List<T> list = getSqlMapClientTemplate().queryForList(method, map);
		
		return list;
	}

	@Override
	public <T extends BaseBO> int deleteForIsActive(String method, String bizId)
			throws RollbackableBizException {

		return getSqlMapClientTemplate().update(method, bizId);
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.dao.ICommonDAO#findListByParam(java.lang.String, com.git.cloud.common.model.base.BaseBO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseBO> List<T> findListByParam(String method, T bo)
			throws RollbackableBizException {
		List<T> list = getSqlMapClientTemplate().queryForList(method, bo);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.dao.ICommonDAO#findForList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findForList(String method)
			throws RollbackableBizException {
		List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList(method);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.dao.ICommonDAO#findForList(java.lang.String, java.util.Map)
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> findForList(String method, Map map)
			throws RollbackableBizException {
		List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList(method,map);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends BaseBO> T findObjectByMap(String method, Map map)
			throws RollbackableBizException {
		return (T) getSqlMapClientTemplate().queryForObject(method, map);
	}
	
	@SuppressWarnings({ "unchecked"})
	public <T> T findObjectByPagination(String method, PaginationParam pagination)
			throws RollbackableBizException {
		return (T) getSqlMapClientTemplate().queryForObject(method, pagination.getParams());
	}
	
	@Override
	public <T extends BaseBO> List<T> findListByParam(String method,
			Object object)  throws RollbackableBizException{
		
		@SuppressWarnings("unchecked")
		List<T> list = getSqlMapClientTemplate().queryForList(method, object);
		return list;
	}

	/* (non-Javadoc)
	 * <p>Title:findListByList</p>
	 * <p>Description:</p>
	 * @param method
	 * @param list
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.common.dao.ICommonDAO#findListByList(java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findListByList(String method, List<Object> list) throws RollbackableBizException {
		List <? extends Object> results = Collections.emptyList(); 
		results = getSqlMapClientTemplate().queryForList(method,list);
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findStringForSql(String sql) throws Exception {
		List<String> results = getSqlMapClientTemplate().queryForList("general_query_for_one_return", sql);
		if (results == null || results.size() < 1) {
			throw new Exception("Not found result, sql is : " + sql);
		}
		return results;
	}

	@Override
	public List<?> queryForObject(String sql) throws Exception {
		List<?> resultStr = (List<?>) getSqlMapClientTemplate().queryForList("general_query_for_xml_return" , sql);
		return resultStr;
	}

	public RmResPoolVo findRmResPoolVoByEname(Map map)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteForParam(String method, List<? extends BaseBO> lists) throws RollbackableBizException {
		return getSqlMapClientTemplate().delete(method, lists);
	}


}