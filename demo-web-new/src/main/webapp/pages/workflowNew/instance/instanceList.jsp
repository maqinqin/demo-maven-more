<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<html>
<head>
	<script type="text/javascript"
		src="${ctx}/pages/workflow/instance/js/instanceList.js"></script>
	<style type="text/css">
		html,body{height:100%}
		.form_win_label {
			padding-left: 5px;
		}
		
		.form_table tr td {
			border: 1px solid #A6CACA;
			line-height: 30px;
		}
	</style>
</head>
<body class="main1">
	<div class="content-main clear" id="searchDiv">
		<div class="page-header">
			<h1>
				流程编排/流程实例
					<small>
					 查询云服务交付的自动化流程实例，监视流程执行进度，查看执行日志，暂停流程、单步执行等。
					</small>
			</h1>
		</div>
		<div class="pageFormContent">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:70px;">实例名称：</td>
					<td class="tabCon"><input type="text" id="tm_instanceName"
						name="tm_instanceName" class="textInput" style="width:150px"/></td>
					<td class="tabBt">实例类型：</td>
					<td class="tabCon"><icms-ui:dic id="tm_instanceType" name="tm_instanceType"
							value=""
							sql="select TYPE_NAME name,TYPE_ID value from BPM_TEMPLATE_TYPE WHERE PARENT_ID != '0'"
							showType="select" attr="class='selInput'" /></td>
					<td class="tabBt">实例状态：</td>
					<td class="tabCon"><select id="tm_instanceStateId" name="tm_instanceStateId" class="selInput" style="width:150px;">
							<option selected="selected" value=""><icms-i18n:label name="zxc2"/></option>
							<option value="0">创建</option>
							<option value="1">运行中</option>
							<option value="2">暂停</option>
							<option value="3">正常结束</option>
							<option value="4">强制结束</option>
					</select></td>
				</tr>
				<tr>
					<td class="tabBt">服务单号：</td>
					<td class="tabCon"><input type="text" id="tm_srCode" name="tm_srCode" class="textInput"style="width:150px"/></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:28%; float:left" >
		<table height="12%" width="100%" align="center">
			<tr style=" height:52px;">
				<td  align="left" >
						<input type="button" title="查询" class="Btn_Serch" id="query_btn" />
						<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;" onclick="clearAll()"/>
				</td>
			</tr>		
		</table>
	</div>
</div>
		</div>
		<div class="panel clear" id="gridMain">
			<div>
				<table id="instanceTable"></table>
				<table id="pageNav"></table>
			</div>
		</div>	
	</div>
</body>
</html>