<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/commandForm.js"></script>
<style type="text/css">
.form_table tr td {
	border: 1px solid #A6CACA;
	line-height: 30px;
}

.form_table2 tr td {
	border: 1px solid #A6CACA;
	border-top: none; //
	border-left: none; //
	border-right: none;
	border-bottom: none;
}


.form_win_label {
	padding-left: 5px;
}
</style>
</head>
<body style="overflow-y: hidden;">
	<!-- 标准定义 -->
	<input type="hidden" value="${ctx}" id="url" />
	<input type="hidden" value="<c:out value="${param.state}"></c:out>"
		id="state" />
	<!-- 流程设计器相关参数定义 -->
	<input type="hidden" value="<c:out value="${param.id}"></c:out>"
		id="dNodeId" />
	<input type="hidden" value="<c:out value="${param.nodeId}"></c:out>"
		id="nodeId" />
	<input type="hidden"
		value="<c:out value="${param.processDefinitionID}"></c:out>"
		id="modelId" />
	<input type="hidden"
		value="<c:out value="${param.processInstanceId}"></c:out>"
		id="instanceId" />
	<input type="hidden" value="<c:out value="${param.userAdmin}"></c:out>"
		id="userAdmin" />

	<form id="serviceModuleForm">
		<table class="form_table" width="100%" cellpadding="0" cellspacing="0" width="790px" style="border-collapse:collapse;table-layout:fixed;">
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font>任务名称</td>
				<td width="308px"><input type="text" id="tNodeName" name="tNodeName"
					class="form_input" /> <input type="hidden" id="tNodeId"
					name="tNodeId" class="form_input" /></td>
				<td class="form_win_label"><font class="font_r" color="red">*</font>超时时间</td>
				<td style="width: 266px"><input type="text" id="outtime" name="outtime"
					class="form_input" style="background-color: white;"/>(s)&nbsp;&nbsp;&nbsp;是否告警：<input type="checkbox" id="outtimeAlarm" name="outtimeAlarm" checked="checked"/>
				</td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font>异常处理</td>
				<td>
				<!-- <icms-ui:dic id="exceptionCode" name="exceptionCode"
						kind="BPM_EXCEPTION_CODE" value="" showType="select"
						attr="style='width: 132px;'" /> -->
					<select id="exceptionCode" name="exceptionCode">
					   <option id="no" name="no" value="no" selected="selected">请选择</option>
					   <option id="IGNORE" name="IGNORE" value="IGNORE">忽略异常</option>
					   <option id="WAIT" name="WAIT" value="WAIT">挂起待处理</option>
					   <option id="FORCE" name="FORCE" value="FORCE">强制结束</option>					   
					   <option id="REPEAT" name="REPEAT" value="REPEAT">重复执行</option>
					</select>
						&nbsp;&nbsp;&nbsp;是否告警：<input type="checkbox" id="exceptionAlarm" name="exceptionAlarm" checked="checked"/>
					<div id='exceptionRepeatDiv' style='display:none'>
					&nbsp;&nbsp;&nbsp;再重复执行：<input type='text' id='exceptionRepeat' name='exceptionRepeat' class="form_input" maxlength='2' style="background-color: white;width:30px;text-align:center"/>次
					</div>
				</td>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font>自动执行</td>
				<td><input type="checkbox" name="isAutoNode" id="isAutoNode"
					checked="checked"></td>
			</tr>
			<tr>
				<td class="form_win_label">命令</td>
				<td colspan="3" align="center"><textarea id="commandContent"
						name="commandContent" style="width: 99%; height: 100px;"></textarea></td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font>服务策略</td>
				<td colspan="3" ><select id="servicePolicyCode" name="servicePolicyCode"
					style="width: 132px;" onchange="buildModulePolicyParamTree('')"></select>
					<input type="hidden" id="servicePolicyId" name="servicePolicyId" /></td>
			</tr>
		</table>


		<table id="servicePolicyParamTable" width="100%" border="0"
			cellpadding="0" cellspacing="0" style="border: 1px solid #A6CACA;border-top: none;">
			<tbody>
			</tbody>
		</table>

		<div align="center" style="padding-top: 10px;">
			<a href="javascript:void(0)" id="saveTemplateBtn" class="btn" title="保存" onclick="componentSave();return false;"  style="float:none;">
				<span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelTemplateBtn" class="btn btn-green" title="取消" onclick="cancel();return false;"  style="float:none;">
		    	<span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</form>

</body>
</html>