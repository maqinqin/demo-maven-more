<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>聚合</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/jquery_common.jsp"%>
	<%@ include file="/common/zTree_load.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script type="text/javascript" src="js/joinForm.js"></script>

<style type="text/css">

</style>
</head>
<body style="padding:10px;width:95%;">
	<!-- 上下文定义 -->
	<input type="hidden" value="${ctx}" id="url"/>
	<!-- 流程所处阶段定义：design/processDefinition/processInstance -->
	<input type="hidden" value="<c:out value="${param.state}"></c:out>" id="state"/>
	<!-- mxGraph节点Id -->
	<input type="hidden" value="<c:out value="${param.id}"></c:out>" id="dNodeId"/>
	<!-- 流程节点Id -->
	<input type="hidden" value="<c:out value="${param.nodeId}"></c:out>" id="nodeId"/>
	<!-- 流程模板Id -->
	<input type="hidden" value="<c:out value="${param.processDefinitionID}"></c:out>" id="modelId"/>
	<!-- 流程实例Id -->
	<input type="hidden" value="<c:out value="${param.processInstanceId}"></c:out>" id="instanceId"/>
	<!-- 管理权限 -->
	<input type="hidden" value="<c:out value="${param.userAdmin}"></c:out>" id="userAdmin"/>
	
	<!-- 聚合节点表单 -->
	<form id="joinForm">
		<table width="95%" align="center" cellpadding="0" cellspacing="0" border="0"
				style="text-align: left; line-height: 30px;">
				<tr>
					<td style="font-weight: bold; bolder: 0; width: 100px;">节点名称：</td>
					<td colspan="2" align="left" style="bolder: 0;">
						<input type="text" class="tf_form_input" id="dNodeName" name="dNodeName"
							style="width: 150px;" />
					</td>
				</tr>
		</table>
	
<!-- 		<table width="100%" cellpadding="0" cellspacing="0" border="0"> -->
<!-- 			<tr> -->
<!-- 				<td> -->
<!-- 					节点名称: -->
<!-- 				</td> -->
<!-- 				<td> -->
<!-- 					<input id="dNodeName" name="dNodeName" type="text"/> -->
<!-- 				</td> -->
<!-- 				<td> -->
<!-- 					业务场景: -->
<!-- 				</td> -->
<!-- 				<td> -->
<!-- 					<select id="businessType" name="businessType" onchange="businessTypeChange()"> -->
<!-- 						<option value="">请选择</option> -->
<!-- 					</select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
	
		<!-- 多路径审批表单 -->
<!-- 		<div id="div_multiPathApprove" style="width:100%;display:none;"> -->
<!-- 			<div style="width:100%;height:30px;"> -->
<!-- 				<a href="#" onclick="addMpaData()" style="float:right;"> -->
<!-- 					<div class="panel_btn"> -->
<!-- 						<div class="btn_icon add_row_icon"></div> -->
<!-- 						<span>增加分支路由&nbsp;&nbsp;</span> -->
<!-- 					</div> -->
<!-- 				</a> -->
<!-- 			</div>	 -->
<!-- 			<div style="height:170px;overflow: auto;"> -->
<!-- 				<table id="tb_multiPathApprove" class="table_2" width="100%" cellpadding="0" cellspacing="0" border="0"> -->
<!-- 					<thead> -->
<!-- 						<tr> -->
<!-- 						<th width="120" >参数</th> -->
<!-- 						<th width="120" >表达式</th> -->
<!-- 						<th width="120" >参数值</th> -->
<!-- 						<th width="120" >跳转节点</th> -->
<!-- 						<th width="60" >操作</th> -->
<!-- 						</tr> -->
<!-- 					</thead> -->
<!-- 					<tbody> -->
<!-- 					</tbody> -->
<!-- 				</table> -->
<!-- 			</div>	 -->
<!-- 		</div> -->
	
		<!-- 提交按钮
			<div id="btnTable" style="margin-top:50px;" align="center">
				<input class="btn" onclick="save();" value="保存" type="button">
				<input class="btn" onclick="cancel();" value="取消" type="button">
			</div>
			 -->
			<div id="btnTable" align="center" style="padding-top: 10px;">
			<a href="javascript:void(0)" id="saveJoinBtn"
				class="btn" title="保存" onclick="save();return false;" style="float:none;">
				<span class="text">保存</span>
			</a>
			<a href="javascript:void(0)" id="cancelJoinBtn"
				class="btn" title="取消" onclick="cancel();return false;" style="float:none;">
				<span class="text">取消</span>
			</a>
		</div>
	</form>
</body>
</html>