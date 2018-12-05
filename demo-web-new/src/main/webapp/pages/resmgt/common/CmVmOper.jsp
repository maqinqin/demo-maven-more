<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>



<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmVmOper.js"></script>
<script type="text/javascript">
	$(function() {
		initCmVmOperList();
	});
</script>
</head>
<body class="main1">
	<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				虚拟机 <small>虚拟机</small>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent">
			<form>
				<table style="width: 90%" align="center">
					<tr style="height: 40px;">
						<td style="width: 15%" align="right">机器名：</td>
						<td><select id="tm_istState" name="tm_istState"
							class="selInput" style="width: 100%;">
								<option selected="selected" value=""><icms-i18n:label name="zxc2"/></option>
								<option value="I">等待初始化</option>
								<option value="P">未初始化</option>
								<option value="C">初始化完成</option>
						</select>
						</td>
						<td style="width: 15%" align="right">IP地址：</td>
						<td><select id="tm_istState" name="tm_istState"
							class="selInput" style="width: 100%;">
								<option selected="selected" value=""><icms-i18n:label name="zxc2"/></option>
								<option value="I">等待初始化</option>
								<option value="P">未初始化</option>
								<option value="C">初始化完成</option>
						</select>
						</td>
						<td style="width: 15%"></td>
						<td align="center" style="width:30%">
						<input type="button" class="btn_search"title="查询" onclick="search()" /> 
						<shiro:hasPermission name="sbxxgl_delete">
						<input type="hidden" id="initFlag" name="initFlag" value="1" />
						</shiro:hasPermission> 
						<shiro:hasPermission name="sbxxgl_update">
						<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
						</shiro:hasPermission></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="panel clear" id="deviceGridTable_div">
		<table id="macGridTable"></table>
		<table id="gridPager"></table>
	</div>


</body>
</html>
