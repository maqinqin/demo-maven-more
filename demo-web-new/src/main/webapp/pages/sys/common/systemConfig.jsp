<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<title>菜单管理</title>
<script type="text/javascript" src="${ctx }/pages/sys/common/js/systemConfig.js">


</script>

</head>
<body onload="initLog()" class="main1">

	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg">系统管理 </div>
				<div class="WorkSmallBtBg"><small> 日志管理1111111111111</small></div>
			</h1>
		</div>
	</div>

  

	<div class="pageTableBg">
		<div class="panel clear"  id="gridTableDiv">
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
	</div>
	

</body>
</html>