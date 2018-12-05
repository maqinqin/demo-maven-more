<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	CmDeviceHostShowBo cmDeviceHostShowBo = (CmDeviceHostShowBo)request.getAttribute("CmDeviceHostInfo");
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
					所属物理机名称：${CmDeviceHostInfo.device_name }
				</td>
				<td>
					编号：${CmDeviceHostInfo.sn }
				</td>
			</tr>
			<tr>
				<td>
					CPU：${CmDeviceHostInfo.cpu }
				</td>
				<td>
					内存：${CmDeviceHostInfo.mem }
				</td>
			</tr>
			<tr>
				<td>
					已使用cpu：${CmDeviceHostInfo.cpuUsed }
				</td>
				<td>
					已使用内存：${CmDeviceHostInfo.memUsed }
				</td>
			</tr>
			<tr>
				<td>
					型号：${CmDeviceHostInfo.device_model }
				</td>
				<td>
					厂商：${CmDeviceHostInfo.manufacturer }
				</td>
			</tr>
			<tr>
				<td>
					状态：${CmDeviceHostInfo.is_active }
				</td>
				<td>
					位置：${CmDeviceHostInfo.seat_name }
				</td>
			</tr>
			<tr>
				<td>
					存储硬盘：${CmDeviceHostInfo.disk }
				</td>
				<td>
					所属集群名称：${CmDeviceHostInfo.cluster_name }
				</td>
			</tr>
			<tr>
				<td>
					所属cdp名称：${CmDeviceHostInfo.cdp_name }
				</td>
				<td>
					所属资源池名称：${CmDeviceHostInfo.pool_name }
				</td>
			</tr>
			<tr>
				<td>
					所属数据中心名称：${CmDeviceHostInfo.datacenter_cname }
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
