<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>流程设计器</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/jquery_common.jsp"%>
	<%@ include file="/common/zTree_load.jsp"%>
	
	<script type="text/javascript"
		src="${ctx}/pages/workflowNew/designer/js/GooFlow.js"></script>
	<script type="text/javascript"
		src="${ctx}/pages/workflowNew/designer/js/GooFunc.js"></script>
	<script type="text/javascript"
		src="${ctx}/pages/workflowNew/designer/js/json2.js"></script>
	<script type="text/javascript" src="${ctx}/pages/workflowNew/designer/js/designer.js"></script>
	
	<script type="text/javascript">
	
// 	/**
// 	 * 获取画布对象
// 	 */
// 	function getGraph(){
// 		debugger;
// 		//获取画布对象
// 		graph = (navigator.appName.indexOf ("Microsoft") !=-1) ? window["designer"]:document["designer"];
// 		if(graph == null){
// 			showTip("获取画布对象失败!");
// 		}
// 		return graph;
// 	}
	
	</script>
	
	<link href="${ctx}/css/new.css" type="text/css" rel="stylesheet">
	<link href="${ctx}/pages/workflowNew/designer/css/default.css" type="text/css" rel="stylesheet">
	<link href="${ctx}/pages/workflowNew/designer/css/GooFlow2.css" type="text/css" rel="stylesheet">

	<style type="text/css" media="screen"> 
        object:focus { outline:none; }
        #flashContent { display:none; }
    </style>
    <style type="text/css">
    #template_div p{width:280px; padding:0; padding-left:16px; font-size:12px;}
    #template_div p i{width:80px; text-align:left; font-size:12px;}
    #template_div p label{display:block; padding-left:95px; font-size:12px;}
    #template_div p input{font-size:12px;}
    </style>
</head>
<body style="overflow: hidden" class="main1">

	<!-- 左侧布局  -->
	<!-- 流程定义树 -->
	<div class="page-header">
			<h1>
			<div class="WorkBtBg">
				<icms-i18n:label name="pro_title_design"></icms-i18n:label>
			</div>
			<div class="WorkSmallBtBg">
					<small>
					 <icms-i18n:label name="pro_title_describe"></icms-i18n:label>
					</small>
			</div>		
			</h1>
			
		</div>
	<div id="tabs1" style="position:absolute;left:16px;bottom:24px; top:65px;width:280px;">
		
		<ul style="width:0; height:0; padding:0; margin:0; border:none; border-radius: 0px;">
		</ul>
		<div id="tabs_3" style="overflow-y:auto;overflow-x:auto;height:100%;">
			<ul id="templateTree" class="ztree" style="margin-top:0;width:85%;"></ul>
		</div>
	</div>
	
	<!-- 画布工具栏 -->
	<div id="toolbarContainer"
		 style="position:absolute; white-space:nowrap;overflow:hidden;
		 		text-align:left;left:310px; top:65px; right:0px; padding:0px 4px 0px 0px;
		 		height:34px;">
		<div style="width:853px;height:34px;float:left;background-color:#f0f0f4;" class="btnbar3">
			<!-- <button id="hideTree" onclick="hideOrShowTree()" style="padding:13px 4px;" class="imgBtnMiNi0 imgBtnMiNi"></button> -->
			<button id="hideTree" class="imgBtnMiNi0 imgBtnMiNi"></button>
			<span style="margin-left: 8px">&nbsp;</span>
			<!-- 创建模板 -->
			<button onclick="newTemplate()"  class="btn" style="margin-left:20px;"><icms-i18n:label name="pro_btn_newTemp"></icms-i18n:label></button>
			<!-- 编辑模板 -->
			<button onclick="editTemplate()" class="btn"><icms-i18n:label name="pro_btn_editTemp"></icms-i18n:label></button>
			<!-- 删除模板 -->
			<button onclick="deleteTemplate()"  class="btn"><icms-i18n:label name="pro_btn_delTemp"></icms-i18n:label></button>
			<!-- 保存模板 -->
			<button onclick="processGraph('save')" class="btn"><icms-i18n:label name="pro_btn_saveTemp"></icms-i18n:label></button>
			<!-- 复制模板 -->
			<button onclick="fuzhiTemplate()" class="btn"><icms-i18n:label name="pro_btn_copyTemp"></icms-i18n:label></button>
			<!-- 导入模板 -->
			<button onclick="processGraph('import')" class="btn"><icms-i18n:label name="pro_btn_importTemp"></icms-i18n:label></button>
			<!-- 导出模板 -->
			<button onclick="processGraph('export')"  class="btn"><icms-i18n:label name="pro_btn_exportTemp"></icms-i18n:label></button>
			<!-- 发布模板 -->
			<button onclick="processGraph('deploy')"  class="btn"><icms-i18n:label name="pro_btn_publishTemp"></icms-i18n:label></button>
			<span style="margin-right: 35px">&nbsp;</span>
		</div>
	</div>
	
	<!-- 流程画布 -->
	<div id="graphContainer"
		 style="float:left;height:445px;margin-left:315px;margin-top:40px;width:853px;overflow:hidden;" >
<div id="demo" style="width:98.5%;height:97%;"></div>
	</div>
	<!-- 操作菜单 menuContainer-->
				<div id="contextmenu" style="background-color: white; display: none; width: 140px; position: absolute; border: 0px solid #a6caca; border-bottom:">
					<!-- 单步执行并流转 -->
					<button id="reExecuteSignal"
						onclick="autoNodeOperate('reExecuteSignal')"
						style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
						class="btn_workflow">
						<img
							src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-reExecuteSignal.png"
							style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>单步执行并流转</span>
					</button>
					<br />
					<!-- 单步执行 -->
					<button id="reExecute" onclick="autoNodeOperate('reExecute')"
						style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
						class="btn_workflow">
						<img
							src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-reExecute.png"
							style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>单步执行</span>
					</button>
					<br />
					<!-- 忽略执行并流转 -->
					<button id="unExecuteSignal"
						onclick="autoNodeOperate('unExecuteSignal')"
						style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
						class="btn_workflow">
						<img
							src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-unExecuteSignal.png"
							style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>忽略执行并流转</span>
					</button>
					<br />
					<!-- 查看日志 -->
					<button id="seeLog" onclick="autoNodeOperate('seeLog')"
						style="padding: 2px 4px; width: 100%; text-align: left; height: 30px; display: none"
						class="btn_workflow">
						<img
							src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-seeLog.png"
							style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>查看日志</span>
					</button>
					<!-- 修该节点信息 -->
					<button id="modifyParams" onclick="autoNodeOperate('modifyParams')"
						style="padding: 2px 4px; width: 100%; height: 30px; margin-bottom: 0px; text-align: left; display: none"
						class="btn_workflow">
						<img
							src="${ctx}/pages/workflow/designer/images/toolbar/nodeoperation-saveDefinition.png"
							style="width: 16px; height: 16px; vertical-align: middle; margin-right: 2px;" /><span>修改节点数据</span>
					</button>
				</div>
	<!-- 状态栏容器 -->
	
	<!-- 弹出窗口定义 -->
	<!-- 新增、编辑流程窗口 -->
	<div id="template_div" class="pageFormContent" style="display: none" >
		<form id="templateForm">
			<input type="hidden" id=templateId name="templateId" />
			<p>
				<i><icms-i18n:label name="pro_l_name"></icms-i18n:label><font color="red">*</font></i>
					<input type="text" id="templateName" name="templateName" class="textInput" />
			</p>
			<p>
				<i><icms-i18n:label name="pro_l_tempType"></icms-i18n:label><font color="red">*</font></i>
					<icms-ui:dic id="templateType" name="templateType" value="" showType="select" attr="class='selInput'"
						sql="select TYPE_ID as value,TYPE_NAME as name from BPM_TEMPLATE_TYPE WHERE TYPE_LEVEL=2"/>
			</p>
			<p class="btnbar btnbar1" style="text-align:right; margin-bottom:20px; margin-left:18px; width:250px;">
				<input id="closeTemplateBtn" class="btn btn_dd2 btn_dd3"  type="button" style="margin-right: 5px; margin-left: 0px; font-size:12px;"  value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeTemplate()"> 
				<input type="button" id="saveTemplateBtn" onclick="onSaveTemplate();" style="margin-right: 5px; margin-left: 0px; font-size:12px;" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' class="btn btn_dd2 btn_dd3"  />
			</p>
		</form>
	</div>
	
	<!-- Fork控件配置窗口 -->
	<div id="forkComponent_div" class="form_main_div" style="display: none" >
		<iframe id="forkFrame" name="forkFrame" frameborder="0" 
			style="width:100%;height:250px;scrolling:auto;" src=""></iframe>
	</div>
	
	<!-- Join控件配置窗口 -->
	<div id="joinComponent_div" class="form_main_div" style="display: none" >
		<iframe id="joinFrame" frameborder="0" 
			style="width:100%;height:300px;scrolling:auto;" src=""></iframe>
	</div>
	
	<!-- decision控件配置窗口 -->
	<div id="decisionComponent_div" class="form_main_div" style="display: none" >
		<iframe id="decisionFrame" name="decisionFrame" frameborder="0" style="width:100%;height:250px;scrolling:auto;" src=""></iframe>
	</div>
	
	<!-- 嵌套子流程控件配置窗口 -->
	<div id="subprocessComponent_div" class="form_main_div" style="display: none;">
		<iframe id="subprocessFrame" name="subprocessFrame" frameborder="0" style="width:100%;height:100%;scrolling:auto;" src=""></iframe>
	</div>
	<!-- 开始控件配置窗口 -->
	<div id="startComponent_div" class="form_main_div" style="display: none" >
		<iframe id="globalParamsFrame" name="globalParamsFrame" frameborder="0" 
				style="width:100%;height:240px;scrolling:auto;" src="">
		</iframe>
	</div>
	<!-- 结束控件配置窗口 -->
	<div id="endComponent_div" class="form_main_div" style="display: none" >
		<font size="18">定义流程执行结束的后续动作处理.eg:通知提醒/关联流程调用等--需求场景待补充
		</font>
	</div>
	
	<!-- 容器控件配置窗口 -->
	<div id="containerComponent_div" class="form_main_div" style="display: none" >
		<form id="containerComponentForm">
			<table class="form_table" width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="4">
						<div class="form">  
							<iframe id="containerComponentFrame" name="containerComponentFrame" frameborder="0" 
									style="width:100%;height:220px;scrolling:auto;" src="">
							</iframe>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 自定义控件配置窗口 -->
	<div id="userComponent_div" class="form_main_div" style="display: none" >
		<iframe id="userComponentFrame" name="userComponentFrame" frameborder="0" 
			style="width:100%;height:80%;scrolling:auto;" src=""></iframe>
	</div>
	
	
	<!-- 导出文件内容 -->
	<div id="exportData" class="form_main_div" style="display: none">
		<div>
			<textarea rows="13" cols="78" id="exportWorkFlow" readonly="readonly" onclick="this.focus();this.select()"></textarea>
		</div>
		<div style="text-align: right;">
			<input id="cancelExport" class="btn_gray" type="button" onclick="cancelExport()" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left: 0px; font-size:12px;">
		</div>
	</div>
	<!-- 导入文件 -->
	<div id="importWorkFlow_div" class="pageFormContent" style="display: none">
		<div>
			<textarea rows="13" cols="78" id="importWorkFlow"></textarea>
		</div>
		<div style="text-align: right;">
			<input id="cancelImport" class="btn_gray" type="button" onclick="cancelImport()" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left: 0px; font-size:12px;">
			<input id="saveImport" class="btn_gray" type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveImport()" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px; margin-left: 0px; font-size:12px;">
		</div>
	</div>
	
</body>
</html>