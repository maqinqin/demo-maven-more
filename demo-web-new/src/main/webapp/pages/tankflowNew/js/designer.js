/**
 * 工作流文件夹路径
 */ 
var path = '../../pages/workflow/designer';
var iconPath = '../../pages/workflow/images';

/**
 * 存放操作节点数据的map
 */ 
var nodeMap = {};

/**
 * 创建包装器editor，完成某些graph操作
 */ 
var editor; 
var graph;//画布
/**
 * 定义流程定义(模板)对象
 */
var templateObject = null;
var demo;
var jsondata;
/**
 * 添加还是修改操作标示符
 */
var oper;


var flag=true,hideWidth=0,timer=null;

function hideOrShowTree(){
	if(flag){
		hideWidth=0;
		timer=setInterval(goRight,10);
	}else{
		hideWidth=-290;
		timer=setInterval(goLeft,10);
	}
	hideTree.disabled="disabled";
}
function goRight(){
	if(hideWidth<=-290){
		clearInterval(timer);
		flag=!flag;
		hideTree.removeAttribute("disabled");
		return false;
	}else{
		hideWidth-=5;
		tabs_3.style.left=hideWidth+"px";
		graphContainer.style.left=(290+hideWidth)+"px";
	}
}
function goLeft(){
	if(hideWidth>=0){
		clearInterval(timer);
		flag=!flag;
		hideTree.removeAttribute("disabled");
		return false;
	}else{
		hideWidth+=5;
		tabs_3.style.left=hideWidth+"px";
		graphContainer.style.left=(290+hideWidth)+"px";
	}
}




/**
 * 页面初始化
 */
$(document).ready(function() {
	TankFlow = new TankFlow();
	TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
	/**
	 * 构造流程控件的Tab页面
	 */
	$("#tabs1").tabs();
	var height=$(window).height()*0.9134;
	$("#tabs1").css({width:"300px",height:height+"px"});
	$("#graphContainer").css({width:$(window).width()-355+"px",height:height-30+"px"});
	window.onresize = function(){
		height = $(window).height()*0.9134;
		var height1 = $(window).height()*0.89-10;
		$("#tabs1").css({width:"300px",height:height+"px"});
		$("#graphContainer").css({width:$(window).width()-355+"px",height:height-30+"px"});
		$(".GooFlow_work").css({width:$(window).width()-355-55+"px",height:height1-24+"px"});
		var width1 = $(".GooFlow_work").width();
		if(width1 == 720) {
			width1 = 660;
		}
		$("#draw_graphContainer").css({width:width1+"px",height:height1-30+"px"});
		$("#gundongtiao").css({width:width1+"px",height:height1-30+"px"});
	}
	
	/**
	 * 初始化流程模型目录树
	 */
	initTempLateTree();
	
	$("#top").attr("style","overflow:hidden");
	
	document.getElementById('templateName').onkeydown = function(event){
    	var e = event || window.event || arguments.callee.caller.arguments[0];
    	if(e && e.keyCode==13){ // enter 键
    		//要做的事情
    		e.preventDefault();
    	}
    };
});

/**
 * 初始化模型目录树数据
 * 模型目录树以应用为顶层目录
 * 通过后台当前用户过滤权限
 */
function initTempLateTree(){
	var myClickFun = function(){
		if(bizType == "lc"){//模版
				var isGetTemplate = "cancel";
				//判断选择模板是否是当前编辑模板
				if(templateObject && nodeId != templateObject.templateId){
					isGetTemplate = "replace";//confirm("您正在编辑流程“"+templateObject.templateName+"”，确定切换为“"+nodeName+"”？");
				}else if(templateObject && nodeId == templateObject.templateId){
					isGetTemplate = "reload";//confirm("您正在编辑当前选择的模板，是否重新加载？");
				}else {
					TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
					loadModel();
				}
				
				if (isGetTemplate == "startload") {
					TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
					loadModel();
				}else if(isGetTemplate == "replace"){
					showTip("您正在编辑流程“"+templateObject.templateName+"”，确定切换为“"+nodeName+"”？",function(){
						TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
						loadModel();
					});
				}else if(isGetTemplate == "reload"){
					showTip("您正在编辑当前选择的模板，是否重新加载？",function(){
						TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
						loadModel();
					});
				}else{
					//让左侧模板树还选择之前的模板
					if(lastSelectedNode){
						zTree.selectNode(lastSelectedNode);
					}else{
						showError("没有获得之前选择节点，请刷新重试");
					}
				}
		}
	};

	
	var myExpandFun = function(){
		if(bizType == "tt"){					
			ajaxCall("/tankflow/designer/getTemplateByType.action",{"id":nodeId},
					asyncAddNode
			)
		}
	};	
	
	/*注册方法*/
	regFunction(myClickFun,myExpandFun);
	
	$.ajax({
	     type : "POST",
	     url : ctx+"/tankflow/designer/getTemplateTree.action",
	     async:true,
	     cache:false,
	     beforeSend: function () {
	        showTip("load");
        },
        error: function () {//请求失败处理函数   
            showError('获取模板列表错误');   
        },   
        success:function(data){ //请求成功后处理函数。     
        	zTreeInit("templateTree",data);
        	closeTip();
        }  	
	 });	
}

function loadModel() {
	lastSelectedNode = getCurNode();
	$.ajax({
	     type : "POST",
	     url : ctx+"/workflow/template/getTemplateById.action",
	     data : {
	    	 "id":nodeId
	     },
	     async:true,
	     cache:false,
	     success : function(data) {
	    	templateObject = data;
	    	if(templateObject)
    		{
	    		//传入分组Id及模板类型
	    		$("#tempTypeId").val(templateObject.typeId);
	    		if(templateObject.jsonData){
	    			jsondata = JSON.parse(templateObject.jsonData);
	    			demo.loadData(jsondata);
	    		} else {
	    			jsondata = demo.exportData();
	    		}
    		}
	     },
	     error : function(e) {
	      	showTip("error");
	     }
	 });
}
var lastSelectedNode;

/**
 * 新增流程定义
 * 初始化基础数据
 * 只加载一次
 */
function newTemplate(){
	//清空验证信息
	$("label.error").remove();
	//清空表单
	$('#templateName').val(""),
	$('#templateType').val(""),
	//$('#appType').val(""),
	
	//弹出窗口
	$("#template_div").dialog({
		title:"新增流程定义",
		autoOpen : true,
		modal : true,
		width : 350
	});
	
	oper = "new";
}

/**
 * 保存流程定义，先验证，然后根据oper觉得是新增还是编辑
 */
function onSaveTemplate(){
	var templateName = $("#templateName").val();
	var typeId = $("#templateType").val();
	if (!templateName) {
		showTip("流程名称不能为空！");
		return;
	}
	if (!typeId) {
		showTip("模板类型不能为空！");
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("templateTree");
	var node = zTree.getNodeByParam("id", typeId, null);
	//表单验证
	if(oper == "new"){
		TankFlow.addTemplate(templateName,typeId).then(function(data) {
			showTip(data);
			refreshZTreeAfterAdd(zTree,node);
		}, function(error) {
			showTip(error);
		});
	}else if(oper =="edit"){
		TankFlow.saveTemplate(templateObject.templateId,templateName,typeId).then(function(data) {
			showTip(data);
			if(templateObject.templateName != templateName){
	   			templateObject.templateName = templateName;
	   			//更新节点名称
		    	var selectNode = zTree.getSelectedNodes()[0];
		    	selectNode.name = templateObject.templateName;
		    	zTree.updateNode(selectNode);
	   		}
	   		//如果类型发生变动需要移动左侧树中相应节点
	   		if(templateObject.typeId != typeId){
				//移除模板树中相应节点
				var deleteNode = zTree.getSelectedNodes()[0];
				zTree.removeNode(deleteNode);
				refreshZTreeAfterAdd(zTree,node);
	   		}
			TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
			loadModel();
		}, function(error) {
			showTip(error);
		});
	}else if(oper =="copy"){
		TankFlow.copyTemplate(templateName,typeId,templateObject.creatorId,templateObject.iconxml
				,templateObject.filePath,templateObject.modelId,templateObject.jsonData).then(function(data) {
					showTip(data);
					refreshZTreeAfterAdd(zTree,node);
				}, function(error) {
					showTip(error);
				});
	}
	closeTemplate();

}
function closeTemplate(){
	$("#template_div").dialog("close");
}

/**
 * 复制流程模型
 */
function fuzhiTemplate(){
	if(templateObject){
		//清空验证信息
		$("label.error").remove();
		//给表单赋值
		$('#templateName').val(templateObject.templateName);
		$('#templateType').val(templateObject.typeId);
		//$('#appType').val(templateObject.appId),
		
		//弹出窗口
		$("#template_div").dialog({
			title:"复制流程模版",
			autoOpen : true,
			modal : true,
			width :350
		});
		
		//设置标示符
		oper = "copy";
	}else{
		showError("请选择要复制的流程模板");
	}
	
}

/**
 * 编辑流程模型
 */
function editTemplate(){
	if(templateObject){
		//清空验证信息
		$("label.error").remove();
		//给表单赋值
		$('#templateName').val(templateObject.templateName);
		$('#templateType').val(templateObject.typeId);
		//$('#appType').val(templateObject.appId),
		
		//弹出窗口
		$("#template_div").dialog({
			title:"编辑流程定义",
			autoOpen : true,
			modal : true,
			width : 350
		});
		
		//设置标示符
		oper = "edit";
	}else{
		showError("请选择要编辑的流程模板");
	}
}

/**
 * 删除选择模板
 */
function deleteTemplate(){
	if(templateObject){
			//根据templateId删除相应模板
		showTip("确定删除所选模板？",function(){
			TankFlow.deleteTemplate(templateObject.templateId).then(function(data) {
				//移除模板树中相应节点
				var zTreeObj = $.fn.zTree.getZTreeObj("templateTree");
				var deleteNode = zTreeObj.getSelectedNodes()[0];
				zTreeObj.removeNode(deleteNode);

				// 清空画布
				templateObject = null;//重置模板对象
				TankFlow.createFlow("graphContainer", "流程编排", "startComponent_div", "globalParamsFrame");
				showTip(data);
			}, function(error) {
				showTip(error);
			});
		} );
	}else{
		showError("请选择要删除的流程模板");
	}
}


/**
 * 点击工具栏按钮操作
 * @param action
 */
function processGraph(action){
	if(jsondata){
		if(action=="export"){
			if (isIE()) {
				//弹出窗口
				$("#exportData").empty().dialog({
					autoOpen : true,
					modal : true,
					width : 700,
					height: 500
				}).html(JSON.stringify(demo.exportData()));
			} else {
				TankFlow.exportAsFile();
			}
		}else if(action=="save"){
			if(templateObject){
				if(TankFlow.validateTemplate()){
					TankFlow.saveTemplateDiagram(templateObject, jsondata).then(function(data) {
						templateObject.jsonData = JSON.stringify(jsondata);
						showTip(data);
					}, function(error) {
						console.error(error);
					});
				}
			}else{
				showTip('当前没有编辑任何模板');
			}
		}else if(action=="import"){
			//if (isIE()) {
				$("#importWorkFlow").val("");
				$("#importWorkFlow_div").dialog({
					autoOpen : true,
					modal : true,
					width : 600,
					height : 350
				});
			//} else {
			//	TankFlow.importAsFile();
			//}
		}else{
			if(templateObject){
				if(TankFlow.validateTemplate()){
					if(jsondata){
						TankFlow.saveTemplateDiagram(templateObject, jsondata).then(function(data) {
							templateObject.jsonData = JSON.stringify(jsondata);
							TankFlow.publishTemplate(templateObject.templateId).then(function(data) {
								showTip(data);
							});
						}, function(error) {
							console.error(error);
						});
					}
				}
			}else{
				showError('当前没有选择任何模板');
			}
		}
	}
}

function isIE() {  
    if(!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
}  


//导入流程模板
function saveImport(){
	TankFlow.saveImport("importWorkFlow","importWorkFlow_div");
}
function cancelImport(){
	TankFlow.cancelImport("importWorkFlow_div");
}


function undo(){
	TankFlow.undo();
}
function redo(){
	TankFlow.redo();
}
function printProcessGraph(){
	TankFlow.printProcessGraph();
}
function exportDiagram(){
	TankFlow.exportDiagram("file");
}
function bigger() {
	TankFlow.bigger();
}
function smaller() {
	TankFlow.smaller();
}