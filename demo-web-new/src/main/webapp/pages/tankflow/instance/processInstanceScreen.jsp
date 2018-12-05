<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
<title>流程实例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/tankflow/instance/css/TankFlow.css?t=${sfx}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/tankflow/instance/css/default.css?t=${sfx}"/>
<link href="${ctx}/pages/tankflow/designer/font/iconfont.css?t=${sfx}" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/pages/tankflow/plugin/promise.min.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/TankFlowFunc.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/json2.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/js/NewTankFlow.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/js/constants.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/processInstanceScreen.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/designer/icon/iconfont.js?t=${sfx}"></script>

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
   	.GooFlow{border:none;}
	#showlog_div{font-size:12px; }
	#showlog_div .newsBt{font-weight: bold;height: auto;font-size: 13px; }
	.log-title{ background-color: #e4eaec;padding:8px 5px;}
	#showlog_div table tr{border: 1px solid #e4eaec;color: #58666e;}
	#showlog_div table tr td{padding:3px; text-align:left;}
	.main1{overflow-x : hidden ; }
	#logDetailDiv {font-size:12px;}
	.GooFlow_work .GooFlow_work_inner{height:300px;}
</style>
<style type="text/css">
.form_table tr th{
	border: 1px solid #A6CACA;
	line-height: 30px;
}
.form_table tr td{
	border: 1px solid #A6CACA;
	line-height: 30px;
}
.tj_form_textarea {
	width: 100%;
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
</head>
</head>

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
			<input type="hidden" id="wfInstanceId" name="wfInstanceId" /> 
			<input type="hidden" id="modelId" name="modelId" /></td>
	</div>
	<!--画布容器-->
	<div id="container" style="margin:0px;width:100%;height:100%;"></div>
	<!--执行动态日志DIV  -->
	<div id="showlog_div" style="margin: 0px auto; background-color:#D1E9E9; display: none">
		<div class="log-title">日志详情</div>
		<div id="log_details">
		</div>
		<div id="logDetailDiv">
			<!-- <textarea id="logInfoDetail" rows="15" style="width: 950px" readonly="readonly"></textarea> -->
		</div>
	</div>

	<div id="div_tip"></div>

	<!-- 日志显示配置窗口 -->
	<div id="nodeLog_div" style="display: none; overflow-y: no; width: 500px; height: 300px; background-color: #eee;">
		<textarea id="logInfo" class="tj_form_textarea" style="font-size:12px;" readonly="readonly"></textarea>
	</div>

	<!-- 错误重做操作选择窗口 -->
	<div id="nodeDetails_div" style="display: none; font-size:16px;high:180px;text-align:center;overflow-x: inherit;">
		<table class="form_table" style="width: 100%;" cellpadding="0" cellspacing="0">
			<thead>
				<tr align="center">
					<!-- <th>设备Id</th> -->
					<th style="text-align:center;">设备名称</th>
					<th style="width: 70px;text-align:center;">执行状态</th>
					<th style="text-align:center;text-align:center;">执行结果</th>
				</tr>
			</thead>
			<tbody id="nodeDetail_tbody">
			</tbody>
		</table>
		<!-- 
		<table style="width: 100%; margin:20px;">
			<tr>
				<td colspan="4" align="center"><input type="button"
					onclick="setAutoNodeDetail('true');" value="执行错误" class="btn" /> <input
					type="button" onclick="setAutoNodeDetail('false');" value="全部执行"
					class="btn" /></td>
			</tr>
		</table>
		 -->
		<div id="btnTable" align="center" style="padding-top: 10px;">
			<a href="javascript:void(0)" id="autoNodeDetailErrorBtn" class="btn"
				title="执行错误" onclick="setAutoNodeDetail('true');return false;">
				<span class="text">执行错误</span>
			</a>
			<a href="javascript:void(0)" id="autoNodeDetailAllBtn" class="btn" 
				title="全部执行" onclick="setAutoNodeDetail('false');return false;">
				<span class="text">全部执行</span>
			</a>
		</div>
		<br>
		<br>
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