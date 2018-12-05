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
<script type="text/javascript" src="${ctx}/scripts/My97DatePicker/WdatePicker.js?t=${sfx}"></script>
<script type="text/javascript" src="js/apiForm.js?t=${sfx }"></script>

<style type="text/css">
</style>
</head>
<body >
	<br/>
	<!-- 标准定义 -->
	<input type="hidden" value="${ctx}" id="url" />
	<input type="hidden" value="<c:out value="${param.state}"></c:out>"
		id="state" />
	<input type="hidden" value="<c:out value="${param.tempTypeId}"></c:out>"
		id="tempTypeId" />

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
		<table class="tf_form_table" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tf_form_win_label" style="width: 80px;">
					<font class="font_r" color="red">*</font>任务名称
				</td>
				<td style="width: 260px;" >
					<input type="text" id="tNodeName" name="tNodeName"
							class="tf_form_input" style="background-color: white;"/> 
					<input type="hidden" id="tNodeId" name="tNodeId" class="tf_form_input" />
				</td>
				<td style="width: 80px;" class="tf_form_win_label"><font class="font_r" color="red">*</font>超时时间</td>
				<td style="width: 260px;" >
					<input type="text" id="outtime" name="outtime" class="tf_form_input" style="width:50px;"/>秒
					&nbsp;&nbsp;&nbsp;是否告警
					<input type="checkbox" class="tf_form_check" id="outtimeAlarm" name="outtimeAlarm" checked="checked"/>
				</td>
			</tr>
			<tr>
                <td class="tf_form_win_label"><font class="font_r" color="red">*</font>服务策略</td>
                <td ><select id="servicePolicyCode" name="servicePolicyCode" class="tf_form_select"
                       onchange="buildModulePolicyParamTree('')"></select> 
                    <input type="hidden" id="servicePolicyId" name="servicePolicyId" />
                </td>
                <td class="tf_form_win_label">自动执行</td>
                <td><input type="checkbox" name="isAutoNode" id="isAutoNode" checked="checked"></td>
            </tr>
			<tr>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>异常处理</td>
				<td colspan="3">
					<select id="exceptionCode" name="exceptionCode" class="tf_form_select">
					   <option id="no" name="no" value="" selected="selected">请选择</option>
					   <option id="IGNORE" name="IGNORE" value="IGNORE">忽略异常</option>
					   <option id="WAIT" name="WAIT" value="WAIT">挂起待处理</option>
					   <option id="FORCE" name="FORCE" value="FORCE">强制结束</option>
					   <option id="REPEAT" name="REPEAT" value="REPEAT">重复执行</option>
					</select>
					&nbsp;&nbsp;&nbsp;是否告警：<input type="checkbox" id="exceptionAlarm" name="exceptionAlarm" checked="checked"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div id='exceptionRepeatDiv' style='display:none'>
						再重复执行：<input type='text' id='exceptionRepeat' name='exceptionRepeat' class="tf_form_input" maxlength='2' style="background-color: white;width:30px;text-align:center"/>次
					</div>
				</td>
			</tr>
			<tr>
				<td class="tf_form_win_label">激活时间</td>
				<td colspan='3'>
					<input id="isActivity" name="isActivity" type="checkbox">
					&nbsp;&nbsp;&nbsp;<div id='activitySubmitDiv' style='display:none'>
					<input name="activityTime" id="activityTime" type="text" class="tf_textInput1" readonly="true"
						onClick="WdatePicker({ dateFmt: 'HH:mm' })" style="width: 50px;" />&nbsp;&nbsp;(HH:MM)
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					是否告警
					<input type="checkbox" id="activityAlarm" name="activityAlarm"/>
						&nbsp;&nbsp;&nbsp;<div id='activityAlarmDiv' style='display:none'>
					告警时间
					<input name="activityAlarmTime" id="activityAlarmTime" type="text" 
						class="tf_textInput1" readonly="true"
						onClick="WdatePicker({ dateFmt: 'HH:mm' })" style="width: 50px;" />&nbsp;&nbsp;(HH:MM)</div></div>
				</td>
			</tr>
			<tr>
				<td class="tf_form_win_label">循环执行</td>
				<td colspan='3'>
					<input id="isCircle" name="isCircle" type="checkbox">
				</td>
			</tr>
			<tr id="circleInfo" style="display:none">
			    <td class="tf_form_win_label">时间间隔</td>
                <td><input type="text" name="circleInterval" id="circleInterval" class="tf_form_input" 
                    style="background-color: white;" />(s)</td>
			    <td class="tf_form_win_label">执行次数</td>
                <td><input type="text" name="circleNum" id="circleNum" class="tf_form_input"
                    style="width:100px" />次(0无限循环)</td>
            </tr>
		 
		</table>
		<textarea rows="4" cols="40" readonly style="width: 210px; height: 60px; display:none;" 
			id="managementTargetId" name="managementTargetId" disabled="disabled"></textarea>
		<textarea rows="4" cols="40" readonly style="width: 210px; height: 60px; display:none;" 
			id="serverTargetId" name="serverTargetId" disabled="disabled"></textarea>
		<textarea rows="4" cols="40" readonly style="width: 210px; height: 60px; display:none;" 
			id="fileTargetId" name="fileTargetId" disabled="disabled"></textarea>

		<table id="servicePolicyParamTable" class="tf_form_table" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
			</tbody>
		</table>
		<br/>
		<div style="padding-top: 10px;text-align: center; width: 100%;">
			<a href="javascript:void(0)" id="saveTemplateBtn" class="btn" title="保存" onclick="componentSave()" style="float:none;">
				<span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
			<a href="javascript:void(0)" id="cancelTemplateBtn" class="btn btn-green" title="取消" onclick="cancel();return false;" style="float:none;">
				<span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</form>
	<!-- 选择脚本树弹出框 start -->
	<div id="shellTreeDiv" class="form_main_div" style="display:none; width: 400px; height: 400px;">
		<div style="overflow: auto;height: 300px;">
			<ul id="shellTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveShellBtn" class="btn" title="保存" onclick="saveCustom('shellTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelShellBtn" class="btn" title="取消" onclick="cancelCustom('shellTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	<!-- 选择脚本树弹出框 end -->
	<!-- 选择服务器树弹出框 start -->
	<div id="serverTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 300px;">
			<ul id="serverTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveServerBtn" class="btn" title="保存" onclick="saveCustom('serverTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelServerBtn" class="btn" title="取消" onclick="cancelCustom('serverTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	<!-- 选择服务器树弹出框 end -->
	<!-- 选择软件包树弹出框 start -->
	<div id="softwareTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 300px;">
			<ul id="softwareTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="savesoftwareBtn" class="btn" title="保存" onclick="saveCustom('softwareTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelsoftwareBtn" class="btn btn-green" title="取消" onclick="cancelCustom('softwareTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	<!-- 选择服务器树弹出框 end -->
		<!-- 选择管理对象树弹出框 start -->
	<div id="managementTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 300px;">
			<ul id="managementTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveManageBtn" class="btn" title="保存" onclick="saveCustom('managementTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelManageBtn" class="btn btn-green" title="取消" onclick="cancelCustom('managementTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	
	<!-- 选择服务器对象树弹出框 end -->
	<div id="serverVarTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 300px;">
			<ul id="serverVarTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveServerVarBtn" class="btn" title="保存" onclick="saveCustom('serverVarTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelServerVarBtn" class="btn btn-green" title="取消" onclick="cancelCustom('serverVarTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	<!-- 选择服务器对象树弹出框 end -->
	<br/>
	<!-- 选择文件对象树弹出框 end -->
	<div id="fileTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 300px;">
			<ul id="fileTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveFileBtn" class="btn" title="保存" onclick="saveCustom('fileTreeDiv');return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		    <a href="javascript:void(0)" id="cancelFileBtn" class="btn btn-green" title="取消" onclick="cancelCustom('fileTreeDiv');return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
	<!-- 选择文件对象树弹出框 end -->

</body>
</html>