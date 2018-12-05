<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>无访问权限</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/sysRoleList.js"></script>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/authorization.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-slider.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/slider.css"></link>
	</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				无权限访问该页面
					<small>
					你无权限访问该页面
					</small>
			</h1>
		</div>
		<div class="pageFormContent">
		<h2>你无权限访问该页面</h2>
		<a target="_self" href="${ctx}/pages/login.jsp">重新登录</a>
		</div>
	</div>
</body>
</html>
