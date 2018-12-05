<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	CmDeviceVMShowBo cmDeviceVMShowBo = (CmDeviceVMShowBo)request.getAttribute("CmDeviceVMInfo");
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
		<table>

			<tr>
				<td>
					虚机名称：${CmDeviceVMInfo.vm_name }
				</td>
				<td>
					编号：${CmDeviceVMInfo.sn }
				</td>
			</tr>
			<tr>
				<td>
					CPU：${CmDeviceVMInfo.cpu }
				</td>
				<td>
					内存：${CmDeviceVMInfo.mem }
				</td>
			</tr>
			<tr>
				<td>
					所属物理机名称：${CmDeviceVMInfo.device_name }
				</td>
				<td>
					所属集群名称：${CmDeviceVMInfo.cluster_name }
				</td>
			</tr>
			<tr>
				<td>
					所属cdp名称：${CmDeviceVMInfo.cdp_name }
				</td>
				<td>
					所属资源池名称：${CmDeviceVMInfo.pool_name }
				</td>
			</tr>
			<tr>
				<td>
					所属数据中心名称：${CmDeviceVMInfo.datacenter_cname }
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
