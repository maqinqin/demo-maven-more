package com.git.cloud.common.support;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;  
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;  
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class GetSql {
	public static <T> String getSql(SqlMapClientImpl sqlclientImpl, String sqlId, T bo) {
		String sqlStr = "";
		/** 获取隐身对象 */
		MappedStatement stmt = sqlclientImpl.getMappedStatement(sqlId);
		Sql sql = stmt.getSql();
		/** 获取规则 */
		SessionScope sessionScope = new SessionScope();
		sessionScope.incrementRequestStackDepth();
		StatementScope statementScope = new StatementScope(sessionScope);
		stmt.initRequest(statementScope);
		/** 获取sql映射对象 */
		sqlStr = sql.getSql(statementScope, bo);
		return sqlStr;
	}

}
