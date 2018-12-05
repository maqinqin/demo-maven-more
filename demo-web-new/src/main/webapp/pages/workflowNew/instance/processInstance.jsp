<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
	<title><icms-i18n:label name="keyii14"/></title>
	<script type="text/javascript" src="${ctx}/pages/workflow/js/constants.js"></script>
	<script type="text/javascript" src="${ctx}/pages/workflowNew/instance/js/processInstance.js"></script>
	<script type="text/javascript" src="${ctx}/pages/workflow/instance/js/swfobject.js"></script>
	<script type="text/javascript" src="${ctx}/pages/workflowNew/instance/js/GooFlow.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/pages/workflowNew/instance/css/GooFlow2.css"/>
	<style type="text/css">
	.instanceTable tr td { 
	}
	.instanceTable tr td input{font-size:12px; padding:6px 4px;}
	.descript {
		padding-left: 10px;
	}
	#menuContainer{
        background:#fff;
        border:1px solid red;
        box-shadow: 10px 10px 5px #888888;
    }
    #menuContainer button:hover{
        color:red;
       	transform:scale(1,1.15);
       	background:#c1dcfc;
       	opacity:0.5;
   	 }
</style>
</head>

<body class="main1">
	<input id="instanceId" type="hidden" value='<c:out value="${param.instanceId}"></c:out>' />
	<input id="editType" type="hidden" value='<c:out value="${param.state}"></c:out>' />
	<input id="userAdmin" type="hidden" value='<c:out value="${param.userAdmin}"></c:out>' />
	<div style="position:absolute;top: 0px;right:0px;bottom:0px;left:0px;">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="mw_title_viewProgress"></icms-i18n:label></div>
			</h1>
		</div>
		<div class="searchBg searchBg2" style="margin-top:10px;">
			<table cellpadding="0" cellspacing="0" class="instanceTable" align="left" style="width:100%;" >
			<tr>
				<td class="descript" style="width:80px; text-align:left;  padding-left:10px;" ><icms-i18n:label name="mw_l_instaName"></icms-i18n:label>：</td>
				<td style="width:100px; font-size:12px; text-align:left;">
					<input type="text" id="instanceName" name="instanceName" class="readonly" readonly style="font-size:12px;"/> 
					<input type="hidden" id="wfInstanceId" name="wfInstanceId" /> 
					<input type="hidden" id="modelId" name="modelId" />
				</td>

				<td class="descript" style="width:80px;text-align:left;"><icms-i18n:label name="mw_l_instaType"></icms-i18n:label>：</td>
				<td style="width:120px; font-size:12px;  text-align:left;">
					<input type="text" id="instanceType" name="instanceType" class="readonly" readonly style="font-size:12px;" /> 
				</td>
				<td class="descript" style="width:80px;text-align:left;"><icms-i18n:label name="bm_l_service_number"></icms-i18n:label>：</td>
				<td style="width:100px; font-size:12px;  text-align:left;">
					<input type="text" id="srCode" name="srCode" class="readonly" readonly/> 
				</td>

				<td class="descript" style="width:80px;text-align:left; "><icms-i18n:label name="mw_l_instaStatus"></icms-i18n:label>：</td>
				<td style="text-align:left;"><input type="hidden" id="instanceStateId" /></td>
				<td style="text-align:left;"><input type="text" id="instanceStateName" name="instanceStateName" class="readonly" readonly style="font-size:12px; width:60px; float:left;"/></td>
			</tr>
		</table>
		<div style="text-align:right; margin-bottom:18px; padding-top:12px; padding-right:20px; clear:both; overflow:hidden;" class="btnbar1">
			<input type="button" id="start_instance" onclick="startInstance()" value='<icms-i18n:label name="keyii7"/>' class="btn btn_dd2 btn_dd3" style="display:none;margin-left:0; margin-right:5px;" /> 
			<input type="button"id="end_instance" onclick="endInstance()" value='<icms-i18n:label name="keyii6"/>' class="btn btn_dd2 btn_dd3" style="display:none;margin-left:0; margin-right:5px;" /> 
			<input type="button" id="resume_instance" onclick="resumeInstance()" value='<icms-i18n:label name="keyii5"/>' class="btn btn_dd2 btn_dd3" style="display:none;margin-left:0; margin-right:5px;" /> 
			<%-- <input type="button" id="pause_instance" onclick="pauseInstance()" value='<icms-i18n:label name="keyii4"/>' class="btn btn_dd2 btn_dd3"style="display:none;margin-left:0; margin-right:5px;" />   --%>
			<input type="button" id="view_report" onclick="viewReport()" value='<icms-i18n:label name="keyii1"/>' class="btn btn_dd2 btn_dd3" style="display:none; margin-left:0; margin-right:5px;"/>
			<input type="button" id="flush_instance" onclick="flushPage1()" value='<icms-i18n:label name="keyii3"/>' class="btn btn_dd2 btn_dd3" style="display:none; margin-left:0; margin-right:5px;" />
			<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="keyii2"/>'  style="width:88px; margin-left:0; margin-right:5px;"/>
		</div>
		</div>
		
	 		<!--画布容器-->
		<div id="container"	style="width: 96%;height:110%; margin:14px auto;">
		</div>
	</div>

	<div id="div_tip"></div>

	<!-- 日志显示配置窗口 -->
	<div id="nodeLog_div" style="display: none; overflow-y: no">
		<textarea id="logInfo" rows="25" style="width: 670px"></textarea>
	</div>
	
	<!-- 错误重做操作选择窗口 -->
	<div id="nodeDetails_div" class="form_main_div" style="display: none;">
		<table class="table_l" style="width: 100%;">
			<thead>
				<tr>
					<th><icms-i18n:label name="mw_l_deviceID"></icms-i18n:label><icms-i18n:label name="keyii8"/></th>
					<th><icms-i18n:label name="mw_l_deviceName"></icms-i18n:label><icms-i18n:label name="keyii9"/></th>
					<th style="width: 70px;"><icms-i18n:label name="mw_l_runStatus"></icms-i18n:label><icms-i18n:label name="keyii10"/></th>
					<th><icms-i18n:label name="mw_l_runResult"></icms-i18n:label><icms-i18n:label name="keyii11"/></th>
				</tr>
			</thead>
			<tbody id="nodeDetail_tbody">
			</tbody>
		</table>
		<table style="width: 100%;">
			<tr>
				<td colspan="4" align="center"><input type="button"
					onclick="setAutoNodeDetail('true');" value='<icms-i18n:label name="keyii12"/>' class="btn" /> <input
					type="button" onclick="setAutoNodeDetail('false');" value='<icms-i18n:label name="keyii13"/>'
					class="btn" /></td>
			</tr>
		</table>
	</div>
	
	<!-- 操作菜单 -->
	<div id="menuContainer" style="background-color:white;display:none;width:140px;position:absolute;border: 0px solid #a6caca; border-bottom:">
		<!-- 单步执行并流转 -->
		<button id="reExecuteSignal" onclick="autoNodeOperate('reExecuteSignal')" style="padding:2px 4px;width:100%;text-align: left;height:30px;display: none" class="btn_workflow">
			<img src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-reExecuteSignal.png" style="width: 16px;height: 16px;vertical-align: middle;margin-right: 2px;"/><span><icms-i18n:label name="mw_l_stepRunFlow"></icms-i18n:label></span>
		</button>
		<br/>
		<!-- 单步执行 -->
		<button id="reExecute" onclick="autoNodeOperate('reExecute')" style="padding:2px 4px;width:100%;text-align: left;height:30px;display: none"  class="btn_workflow">
			<img src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-reExecute.png" style="width: 16px;height: 16px;vertical-align: middle;margin-right: 2px;"/><span><icms-i18n:label name="mw_l_stepRun"></icms-i18n:label></span>
		</button>
		<br/>
		<!-- 忽略执行并流转 -->
		<button id="unExecuteSignal" onclick="autoNodeOperate('unExecuteSignal')" style="padding:2px 4px;width:100%;text-align: left;height:30px;display: none"  class="btn_workflow">
			<img src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-unExecuteSignal.png" style="width: 16px;height: 16px;vertical-align: middle;margin-right: 2px;"/><span><icms-i18n:label name="mw_l_igRunFlow"></icms-i18n:label></span>
		</button>
		<br/>
		<!-- 查看日志 -->
		<button id="seeLog" onclick="autoNodeOperate('seeLog')" style="padding:2px 4px;width:100%;text-align: left;height:30px;display: none"  class="btn_workflow">
			<img src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-seeLog.png" style="width: 16px;height: 16px;vertical-align: middle;margin-right: 2px;"/><span><icms-i18n:label name="mw_l_viewLog"></icms-i18n:label></span>
		</button>
		<!-- 修该节点信息 -->
		<button id="modifyParams" onclick="autoNodeOperate('modifyParams')" style="padding:2px 4px;width:100%;height:30px; margin-bottom:0px;text-align: left; display: none"  class="btn_workflow">
			<img src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-saveDefinition.png" style="width: 16px;height: 16px;vertical-align: middle;margin-right: 2px;"/><span><icms-i18n:label name="mw_l_updateNode"></icms-i18n:label></span>
		</button>
	</div>
	
	<!-- 自定义控件配置窗口 -->
	<div id="userComponent_div" class="form_main_div" style="display: none" >
		<iframe id="userComponentFrame" name="userComponentFrame" frameborder="0" 
			style="width:100%;height:100%;scrolling:auto;" src=""></iframe>
	</div>
	<div id="log-container" style="height: 450px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;display: none;">
        <div>   </div>
    </div>
</body>
</html>