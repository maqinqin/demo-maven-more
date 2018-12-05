<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
	<style type="text/css">
		.iconfont {
			font-size:13px;
		}
		.btn {display:none;}
	</style>
	<script type="text/javascript"
		src="${ctx}/pages/tankflowNew/js/processInstance.js"></script>
</head>

<body class="main1">

	<div style="overflow: hidden; clear: both;">
		<input id="instanceId" type="hidden"
			value='<c:out value="${param.instanceId}"></c:out>' />
		<input id="instanceStatus" type="hidden"
			value='<c:out value="${param.instanceStatus}"></c:out>' /> <input
			id="editType" type="hidden"
			value='<c:out value="${param.state}"></c:out>' /> <input
			id="userAdmin" type="hidden"
			value='<c:out value="${param.userAdmin}"></c:out>' />
			<input id="chosenGroupId" type="hidden"
			value='<c:out value="${param.chosenGroupId}"></c:out>' />
	</div>
	<div class="page-header">
		<div class="mainSeach3_top" >
		<div class="WorkBtBg">实例详情</div>
	</div>
	</div>
	
	<div style="position: absolute; top: 39px; right: 0px; bottom: 0px;left: 0px; width:98%; margin:0 auto;">
	<div id="topDiv"  style="width: 100%; padding-top:8px;">
		<div class="searchBg" style="overflow:hidden; width:100%;">
			<table cellpadding="0" cellspacing="0" class="instanceTable"
			align="left" style="margin-left:1%;">
			<tr>
				<td class="descript" style="width:70px;padding-top: 3px;font-size:12px;">实例名称：</td>
				<td style="width:220px;"><input type="text" id="instanceName"
					name="instanceName" class="readonly" readonly style="width: 190px;padding-top:11px;border-radius:4px; font-size:12px;"/>
					<input type="hidden" id="wfInstanceId" name="wfInstanceId" /> <input
					type="hidden" id="modelId" name="modelId" /></td>
				<td class="descript" style="width: 70px;padding-top: 3px;font-size:12px;">实例类型：</td>
				<td style="width: 16%"><input type="text" id="instanceType"
					name="instanceType" class="readonly" readonly style="width: 190px;padding-top:11px;border-radius:4px; font-size:12px;"/>
				</td>
				<td class="descript" style="width: 70px;padding-top: 3px;font-size:12px;">服务单号：</td>
				<td style="width: 16%"><input type="text" id="srCode"
					name="srCode" class="readonly" readonly style="width: 190px;padding-top:11px;border-radius:4px; font-size:12px;" />
				</td>
				<td class="descript" style="width:70px; padding-top: 3px;font-size:12px;">实例状态：</td>
				<td><input type="hidden" id="instanceStateId" /> <input
					type="text" id="instanceStateName" name="instanceStateName"
					class="readonly" readonly style="width: 190px;padding-top:11px;border-radius:4px; font-size:12px;" /></td>

				<td class="descript" style="width: 5%;"></td>
			</tr>
		</table>
		</div>
		
		<div style="float:right;padding-top:12px; padding-bottom:20px;">
			<a href="javascript:void(0)" id="flush_btn" class="btn" title="刷新" onclick="flush();return false;"><span class="icon iconfont icon-icon-refesh"></span><span class="text">刷新</span></a>
			<a href="javascript:void(0)" id="start_instance" class="btn" title="运行" onclick="instanceOper('resume');return false;"><span class="icon iconfont icon-icon-run"></span><span class="text">运行</span></a>
			<a href="javascript:void(0)" id="submit_btn" class="btn" title="返回" onclick="returnLastPage();return false;"><span class="icon iconfont icon-icon-return"></span><span class="text">返回</span></a>
			<a href="javascript:void(0)" id="view_report" class="btn" title="报告" onclick="viewReport();return false;"><span class="icon iconfont icon-icon-copy"></span><span class="text">报告</span></a>
			<a href="javascript:void(0)" id="pause_instance" class="btn" title="停止" onclick="instanceOper('pause');return false;"><span class="icon iconfont icon-icon-pause"></span><span class="text">停止</span></a>	
			<input id="resume_instance" class="btn_Function" type="button" style="display: none"  onclick="resumeInst()">  
			<a href="javascript:void(0)" id="resume_instance" style="display: none" class="btn" title="停止" onclick="resumeInst();return false;"><span class="icon iconfont icon-icon-pause"></span><span class="text">停止</span></a>	
			<a href="javascript:void(0)" id="end_instance" class="btn" title="结束" onclick="instanceOper('end');return false;"><span class="icon iconfont icon-icon-stop"></span><span class="text">结束</span></a>	
			<a href="javascript:void(0)" id="log_instance" class="btn" title="日志" onclick="instanceOper('log');return false;"><span class="icon iconfont icon-icon-copy"></span><span class="text">日志</span></a>
		</div>
		
		<iframe id = "WorkflowIframe" src="" style="width: 100%;margin-left:0px;border:1px solid #eee;background-color:#fff;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="true"></iframe>
	</div> 
	</div>
</body>
</html>