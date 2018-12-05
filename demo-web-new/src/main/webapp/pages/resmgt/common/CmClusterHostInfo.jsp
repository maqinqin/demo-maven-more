<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.git.cloud.resmgt.common.model.bo.CmClusterHostShowBo"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	List cmClusterHostShowBoList  = new ArrayList();
	cmClusterHostShowBoList = (List)request.getAttribute("cmClusterHostShowBoList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>云平台管理</title>
</head>

<body class="main1">
<%-- <form action="<%=basePath%>common_hostinfoView/cmDevice.action" method="post"> --%>
<form action="">
		<table border="1px" align="center">
			<thead>
			<tr>
				<th align="center">主机名称</th>
				<th align="center">编号</th>
				<th align="center">位置</th>
				<th align="center">IP</th>
			<tr>
			</thead>
			<tbody>
<%
if(cmClusterHostShowBoList != null && cmClusterHostShowBoList.size()>0){
	for(int i = 0; i<cmClusterHostShowBoList.size(); i++){
		CmClusterHostShowBo cmClusterHostShowBo = (CmClusterHostShowBo)cmClusterHostShowBoList.get(i);
		if(!"".equals(cmClusterHostShowBo.getId())){%>
		
			<tr>
				<td>
					<%=cmClusterHostShowBo.getDevice_name() %>
				</td>
				<td>
					<%=cmClusterHostShowBo.getSn() %>
				</td>
				<td>
					<%=cmClusterHostShowBo.getSeat_name() %>
				</td>
				<td>
					<%=cmClusterHostShowBo.getIp_str() %>
				</td>
			</tr>
			
		<%}
	}
}
%>

			</tbody>
		</table>
	</form>
	
</body>
</html>
