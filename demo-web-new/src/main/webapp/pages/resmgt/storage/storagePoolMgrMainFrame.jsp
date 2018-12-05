<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
</head>

	<frameset id="main" cols="30%,*"  frameborder="no" border="0" framespacing="0">
				<frame src="${ctx}/storage/showStorageTree.action" name="leftFrame" noresize="noresize" id="leftFrame" title="leftFrame"/>
				 
	</frameset>
</html>