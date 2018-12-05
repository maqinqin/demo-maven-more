<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	</head>

	<body>
		静态<icms-ui:dic id="staticType" name="staticType" staticDic="T:是;F:否" showType="select" attr="style='width: 132px;'"/>
		字典<icms-ui:dic name="dicType" kind="DU_SEV_TYPE" value="AP" showType="select" attr="style='width: 132px;'"/>
		自定义sql<icms-ui:dic name="sqlType" value="dicType" sql="select DIC_ID id,DIC_NAME name,DIC_CODE value,DIC_TYPE_CODE kind from ADMIN_DIC" showType="select" attr="style='width: 132px;'"/>
<script type="text/javascript">
$(function() {
	selectByValue("staticType","F");
});
</script>
</body>
</html>
