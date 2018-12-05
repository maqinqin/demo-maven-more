
package com.git.cloud.taglib.dic;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;


/**
 * @Title 		IDictionaryTagDao.java 
 * @Package 	com.git.cloud.taglib.dic
 * @author 		wxg
 * @date 		2014-9-16 上午010:54:15
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IDictionaryTagDao extends ICommonDAO{

	public List<DictionaryTag> getDicList(DictionaryTag dic) throws SQLException;
	public List<DictionaryTag> getDicListBySql(String sql) throws SQLException;
}
