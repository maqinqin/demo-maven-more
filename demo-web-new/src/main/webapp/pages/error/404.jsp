<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>404 您访问的页面不存在</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-slider.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/slider.css"></link>
	</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				系统异常
					<small>
					404 您访问的页面不存在!
					</small>
			</h1>
		</div>
		<div class="pageFormContent">
		<h2>404 您访问的页面不存在</h2>
<%-- 		<a target="_self" href="${ctx}/pages/login.jsp">重新登录</a> --%>
		</div>
	</div>
</body>
</html>
