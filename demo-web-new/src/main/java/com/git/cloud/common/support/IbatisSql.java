package com.git.cloud.common.support;

import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;

public class IbatisSql {

	// SQL语句
	private String sql = "";
	// SQL字段信息
	private ResultMapping[] resultMappings = null;

	/**
	 * 获得：SQL语句
	 * 
	 * @return the sql
	 */

	public final String getSql() {
		return sql;
	}

	/**
	 * 设置：SQL语句
	 * 
	 * @param sql
	 *            the sql to set
	 */

	public final void setSql(String sql) {
		if (sql != null) {
			this.sql = sql;
		}
	}

	/**
	 * 获得：SQL字段信息
	 * 
	 * @return the resultMappings
	 */

	public final ResultMapping[] getResultMappings() {
		return resultMappings;
	}

	/**
	 * 设置：SQL字段信息
	 * 
	 * @param resultMappings
	 *            the resultMappings to set
	 */

	public final void setResultMappings(ResultMapping[] resultMappings) {
		if (resultMappings != null) {
			this.resultMappings = resultMappings;
		}
	}

}

