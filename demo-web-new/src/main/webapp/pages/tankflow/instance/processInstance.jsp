<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/pages/tankflow/instance/processInstanceResource.jsp"%>
</head>
<style>
.tj_form_textarea {
	width: 98%;
	height: 98%;
	line-height: 23px;
	color: #615d70;
	background-color: #f7f7f9;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	border: 1px solid #cbcad8;
	border-radius: 3px;
	resize: none;
}
</style>

<body class="main1">

	<div style="overflow: hidden; clear: both;">
		<input id="instanceId" type="hidden"
			value='<c:out value="${param.instanceId}"></c:out>' /> <input
			id="editType" type="hidden"
			value='<c:out value="${param.state}"></c:out>' /> <input
			id="userAdmin" type="hidden"
			value='<c:out value="${param.userAdmin}"></c:out>' />
			<input id="chosenGroupId" type="hidden"
			value='<c:out value="${param.chosenGroupId}"></c:out>' />
	</div>
	<div class="mainSeach3_top" >
		<div class="newsBt">实例详情</div>
		
	</div>
	<div style="position: absolute; top: 111px; right: 0px; bottom: 0px;left: 0px;">
	<div id="topDiv" style="width: 100%;height: 7%;padding-top:8px;">
		<table cellpadding="0" cellspacing="0" class="instanceTable"
			align="left" style="margin-left: 11px; line-height:43px;">
			<tr style="padding-top:30px; overflow:hidden;">
				<td class="descript" style="width:70px;padding-top: 3px;">实例名称：</td>
				<td style="width:220px;"><input type="text" id="instanceName"
					name="instanceName" class="readonly" readonly style="width: 220px;padding-top:11px;border-radius:4px" />
					<input type="hidden" id="wfInstanceId" name="wfInstanceId" /> <input
					type="hidden" id="modelId" name="modelId" /></td>
				<td class="descript" style="width: 70px;padding-top: 3px;">实例类型：</td>
				<td style="width: 16%"><input type="text" id="instanceType"
					name="instanceType" class="readonly" readonly style="width: 220px;padding-top:11px;border-radius:4px" />
				</td>
				<td class="descript" style="width:70px; padding-top: 3px;">实例状态：</td>
				<td><input type="hidden" id="instanceStateId" "/> <input
					type="text" id="instanceStateName" name="instanceStateName"
					class="readonly" readonly style="border-radius:4px;" /></td>

				<td class="descript" style="width: 5%;"></td>
			</tr>
			<tr><td align="right" colspan="6"> 
				 <a href="javascript:void(0)" id="start_instance" class="btn" title="运行" onclick="startInstance();return false;"><span class="icon iconfont icon-icon-run"></span><span class="text">运行</span></a>
				<!-- <input type="button" class="btn_return" id="submit_btn"
					onclick="returnLastPage()" /> -->
				<a href="javascript:void(0)" id="submit_btn" class="btn" title="返回" onclick="returnLastPage();return false;"><span class="icon iconfont icon-icon-return"></span><span class="text">返回</span></a>
				<a href="javascript:void(0)" id="view_report" class="btn" title="报告" onclick="viewReport();return false;"><span class="icon iconfont icon-icon-copy"></span><span class="text">报告</span></a>
				<a href="javascript:void(0)" id="pause_instance" class="btn" title="停止" onclick="pauseInst();return false;"><span class="icon iconfont icon-icon-pause"></span><span class="text">停止</span></a>	
				<input id="resume_instance" class="btn_Function" type="button" style="display: none"  onclick="resumeInst()">  
				<a href="javascript:void(0)" id="resume_instance" style="display: none" class="btn" title="停止" onclick="resumeInst();return false;"><span class="icon iconfont icon-icon-pause"></span><span class="text">停止</span></a>	
				<a href="javascript:void(0)" id="end_instance" class="btn" title="结束" onclick="endInst();return false;"><span class="icon iconfont icon-icon-stop"></span><span class="text">结束</span></a>	
				<a href="javascript:void(0)" id="log_instance" class="btn" title="日志" onclick="viewInstanceLog();return false;"><span class="icon iconfont icon-icon-copy"></span><span class="text">日志</span></a>
					</td></tr>
		</table>
	</div> 

		<!--画布容器-->
		<div id="container" style="position: absolute; width: 97%; height: 80%;top:80px; bottom: 8px; left: 0px; right: 0px; overflow: auto; margin: 64px  auto;border-radius:4px">
		</div>
	</div>

	<div id="div_tip"></div>

	<!-- 日志显示配置窗口 -->
	<div id="nodeLog_div" style="display: none; overflow: no; width: 500px; height: 300px;">
		<textarea id="logInfo" class="tj_form_textarea" style="font-size:12px; background-color: #eee;" readonly="readonly"></textarea>
	</div>

	<!-- 错误重做操作选择窗口 -->
	<div id="nodeDetails_div" class="form_main_div" style="display: none;">
		<table class="table_l" style="width: 100%;">
			<thead>
				<tr>
					<th>设备Id</th>
					<th>设备名称</th>
					<th style="width: 70px;">执行状态</th>
					<th>执行结果</th>
				</tr>
			</thead>
			<tbody id="nodeDetail_tbody">
			</tbody>
		</table>
		<table style="width: 100%;">
			<tr>
				<td colspan="4" align="center"><input type="button"
					onclick="setAutoNodeDetail('true');" value="执行错误" class="btn" /> <input
					type="button" onclick="setAutoNodeDetail('false');" value="全部执行"
					class="btn" /></td>
			</tr>
		</table>
	</div>
	
	<!-- 操作菜单 -->
	<div id="menuContainer"
		style="background-color: white; display: none; width: 140px; position: absolute; z-index:99; border: 0px solid #a6caca; border-bottom:">
		<!-- 单步执行并流转 -->
		<button id="reExecuteSignal"
			onclick="autoNodeOperate('reExecuteSignal')"
			style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-reExecuteSignal.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>单步执行并流转</span>
		</button>
	
		<!-- 单步执行 -->
		<button id="reExecute" onclick="autoNodeOperate('reExecute')"
			style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-reExecute.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>单步执行</span>
		</button>

		<!-- 忽略执行并流转 -->
		<button id="unExecuteSignal"
			onclick="autoNodeOperate('unExecuteSignal')"
			style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-unExecuteSignal.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>忽略执行并流转</span>
		</button>
		
		<!-- 强制重制节点状态为超时 -->
		<button id="froceTimeoutNode"
			onclick="autoNodeOperate('froceTimeoutNode')"
			style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-unExecuteSignal.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>强制超时</span>
		</button>
	
		<!-- 查看日志 -->
		<button id="seeLog" onclick="autoNodeOperate('seeLog')"
			style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-seeLog.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>查看日志</span>
		</button>
		<!-- 修该节点信息 -->
		<button id="modifyParams" onclick="autoNodeOperate('modifyParams')"
			style="padding: 2px 4px; width: 100%; height: 30px; margin-bottom: 0px; text-align: left; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-saveDefinition.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>修改节点数据</span>
		</button>
		
		<!--查看子流程-->
		<button id="showSubProcess" onclick="showSubProcess()"
			style="padding: 2px 4px; width: 100%; height: 30px; margin-bottom: 0px; text-align: left; display: none"
			class="btn_workflow">
			<img
				src="${ctx}/pages/tankflow/designer/images/toolbar/nodeoperation-saveDefinition.png"
				style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>查看子流程</span>
		</button>
	</div>

	<!-- 自定义控件配置窗口 -->
	<div id="userComponent_div" class="form_main_div" style="display: none">
		<iframe id="userComponentFrame" name="userComponentFrame"
			frameborder="0" style="width: 100%; height: 100%; scrolling: auto;"
			src=""></iframe>
	</div>
	<div id="log-container" style="height: 450px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;display: none;">
        <div>
        </div>
    </div>
</body>
</html>