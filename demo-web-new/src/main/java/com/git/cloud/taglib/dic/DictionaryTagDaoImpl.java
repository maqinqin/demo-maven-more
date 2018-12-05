package com.git.cloud.taglib.dic;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.support.IbatisSql;
import com.git.cloud.common.support.IbatisUtil;


public class DictionaryTagDaoImpl extends CommonDAOImpl implements IDictionaryTagDao {

	public List<DictionaryTag> getDicList(DictionaryTag dic) throws SQLException{
		List<DictionaryTag> list = this.getSqlMapClientTemplate().queryForList("getDicList", dic);
		return list;
	}
	public List<DictionaryTag> getDicListBySql(String sql) throws SQLException{
		return this.getSqlMapClientTemplate().queryForList("execSql", sql);
	}
}
