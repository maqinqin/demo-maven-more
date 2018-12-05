<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>流程设计器</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/jquery_common.jsp"%>
	<%@ include file="/common/zTree_load.jsp"%>
	<%@ include file="/pages/tankflow/designer/designeResource.jsp"%>
	
	<script type="text/javascript" src="${ctx}/pages/tankflowNew/js/designer.js"></script>
	<link href="${ctx}/css/new.css" type="text/css" rel="stylesheet">
	<style type="text/css" media="screen"> 
        object:focus { outline:none; }
        #flashContent { display:none; }
    </style>
    <style type="text/css">
    #template_div p{width:280px; padding:0; padding-left:16px;}
    #template_div p i{width:80px; text-align:left;}
    #template_div p label{display:block; padding-left:95px;}
    .btnbarWidth .btn_dd2{ padding:0 10px; margin-right:1px; margin-left:0px; margin-bottom:10px; height:24px; line-height:24px;}
    .btnbarWidth .btn_dd2:hover{ padding:0 10px; margin-right:1px; margin-left:0px; margin-bottom:10px; height:24px; line-height:24px;}
    #templateTree li{margin:10px 3px 0 3px;}
    
    </style>
</head>
<body class="main1">
<div>
	<!-- 左侧布局  -->
	<!-- 流程定义树 -->
	<div class="page-header" style="left:0px;bottom:24px; top:0px;height:45px;min-width:1065px;">
		<h1>
			<div class="WorkBtBg">流程编排/流程设计器</div>
			<div class="WorkSmallBtBg">
				<small>
				 定义云服务申请审批流程，定义云服务资源自动化流程。
				</small>
			</div>	
		</h1>
	</div>
	<div id="tabs1" style="left:1%;bottom:24px; top:5px;width:300px;min-height:500px; background: #e4eaec; border-top: 2px solid #A0C4BF;
		border-bottom: 2px solid #A0C4BF;display: inline-block;height: 505px;">
		
		<ul style="width:0; height:0; padding:0; margin:0; border:none; border-radius: 0px;">
		</ul>
		<div id="tabs_3" style="overflow-y:auto;overflow-x:auto;height:100%;">
			<ul id="templateTree" class="ztree" style="margin-top:0;width:85%;"></ul>
		</div>
	</div>
	
	<!-- 画布工具栏 -->
<!-- 	<div style="overflow:hidden; text-align:left;top:65px; height:525px;width: 71%;float: right;display: inline-block;"> -->
	<div style="left:340px;top:60px;position:absolute;">
		<div id="toolbarContainer" style="background-color:#f0f0f4;width:100%;min-width:730px;">
			<div class="btnbar1 btnbarWidth" style="width:730px;background-color:#f0f0f4; padding-left:0;">
				<!-- <button id="hideTree" onclick="hideOrShowTree()" style="padding:13px 4px;" class="imgBtnMiNi0 imgBtnMiNi"></button> -->
				<!-- <button id="hideTree" class="imgBtnMiNi0 imgBtnMiNi"></button> -->
				<!-- 创建模板 -->
				<button onclick="newTemplate()"  class="btn btn_dd2">创建</button>
				<!-- 编辑模板 -->
				<button onclick="editTemplate()"  class="btn btn_dd2">编辑</button>
				<!-- 删除模板 -->
				<button onclick="deleteTemplate()"   class="btn btn_dd2">删除</button>
				<!-- 保存模板 -->
				<button onclick="processGraph('save')"  class="btn btn_dd2">保存</button>
				<!-- 复制模板 -->
				<button onclick="fuzhiTemplate()" class="btn btn_dd2">复制</button>
				<!-- 导入模板 -->
				<button onclick="processGraph('import')"   class="btn btn_dd2">导入</button>
				<!-- 导出模板 -->
				<button onclick="processGraph('export')"   class="btn btn_dd2">导出</button>
				<!-- 发布模板 -->
				<button onclick="processGraph('deploy')"  class="btn btn_dd2">发布</button>
	<!-- 			<button onclick="bigger()"  class="btn btn_dd2">放大</button> -->
	<!-- 			<button onclick="smaller()" class="btn btn_dd2">缩小</button> -->
				<button onclick="undo()"  class="btn btn_dd2">撤销</button>
				<button onclick="redo()"  class="btn btn_dd2">恢复</button>
				<button onclick="printProcessGraph()"  class="btn btn_dd2">打印</button>
				<button onclick="exportDiagram()"  class="btn btn_dd2">导出流程图</button>
				<%-- <span style="margin-right: 35px">&nbsp;</span>
				<button onclick="operating('duiqiX')" title="水平对齐" style="padding:0px 4px;"  class="imgBtnMiNi9 imgBtnMiNi"></button>
				<button onclick="operating('duiqiY')" title="垂直对齐" style="padding:0px 4px;"  class="imgBtnMiNi10 imgBtnMiNi"></button>
				<button onclick="operating('kuaizhao')" title="快照" style="padding:0px 4px;"  class="imgBtnMiNi11 imgBtnMiNi"></button>
				<button onclick="operating('zuidahua')" title="最大化" style="padding:0px 4px;"  class="imgBtnMiNi12 imgBtnMiNi"></button> --%>
			</div>
		</div>
	</div>
	
	<!-- 流程画布 -->
	<div id="graphContainer"
		 style="position:absolute; overflow:no;
		 		top:100px;left:340px;bottom:6px;right:0px;border:1px #d8d9dc solid;min-height:470px;min-width:730px;" >
		<div id="flashContent">
            <p>
               	 请确保FlashPlayer版本高于11.1.0
            </p>
            <script type="text/javascript"> 
                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
            </script> 
        </div>
	</div>
	</div>


	<!-- 状态栏容器 -->
	
	<!-- 弹出窗口定义 -->
	<!-- 新增、编辑流程窗口 -->
	<div id="template_div" class="pageFormContent" style="display: none" >
		<form id="templateForm">
			<input type="hidden" id=templateId name="templateId" />
			<p>
				<i>流程名称<font color="red">*</font></i>
					<input type="text" id="templateName" name="templateName" class="textInput" />
			</p>
			<p>
				<i>模板类型<font color="red">*</font></i>
					<icms-ui:dic id="templateType" name="templateType" value="" showType="select" attr="class='selInput'"
						sql="select TYPE_ID as value,TYPE_NAME as name from BPM_TEMPLATE_TYPE WHERE TYPE_LEVEL=2"/>
			</p>
			<p class="btnbar btnbar1" style="text-align:right; margin-bottom:20px; margin-left:18px; width:242px;">
				<input id="closeTemplateBtn" class="btn btn_dd2 btn_dd3" type="button" style="margin-right: 5px; margin-left: 0px;"  value="取消" onclick="closeTemplate()">
				<input type="button" id="saveTemplateBtn" onclick="onSaveTemplate();" style="margin-right: 5px; margin-left: 0px;" value="保存" class="btn btn_dd2 btn_dd3" /> 
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
	<div id="exportData" class="form_main_div" style="display: none;word-wrap:break-word;height:400px;"></div>
	<!-- 导入文件 -->
	<div id="importWorkFlow_div" class="pageFormContent" style="display: none">
		<div>
			<textarea rows="13" cols="78" id="importWorkFlow"></textarea>
		</div>
		<div style="text-align: center;">
			<a href="javascript:void(0)" id="saveImport" class="btn" title="保存" onclick="saveImport();return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>
			<a href="javascript:void(0)" id="cancelImport" class="btn btn-green" title="取消" onclick="cancelImport();return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
		</div>
	</div>
</div>
</body>
</html>