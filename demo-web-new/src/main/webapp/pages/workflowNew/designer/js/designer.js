/**
 * 工作流文件夹路径
 */ 
var parentNodeId;
var demo;
var jsondata;

//创建画布对象
function createFlow(){
	$("#demo").empty();
	var property={
		    width:1240,
		    height:600,
		    toolBtns:["start","end","decision","fork","join","cmd","task","scr","api"],
		    haveHead:true,
		    headBtns:["new","open","save","undo","redo","reload"],//如果haveHead=true，则定义HEAD区的按钮
		    haveTool:true,
		    haveGroup:true,
		    useOperStack:true
		};
	var remark={
	    cursor:i18nShow('instance_designer_cursor'),
	    direct:i18nShow('instance_designer_direct'),
	    start:i18nShow('instance_designer_start'),
	    "end":i18nShow('instance_designer_end'),
	    "decision":i18nShow('instance_designer_decision'),
	    "task":i18nShow('instance_designer_task'),
	    "cmd":i18nShow('instance_designer_cmd'),
	    "task":i18nShow('instance_designer_task'),
	    "scr":i18nShow('instance_designer_scr'),
	    "api":i18nShow('instance_designer_api'),
	    "fork":i18nShow('instance_designer_fork'),
	    "join":i18nShow('instance_designer_join')
	};
	demo=$.createGooFlow($("#demo"),property);
	demo.setTitle("designer");
	demo.setNodeRemarks(remark);
}
/**
 * 定义流程定义(模板)对象
 */
var templateObject = null;
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
		hideWidth=-300;
		timer=setInterval(goLeft,10);
	}
	hideTree.disabled="disabled";
}

/**
 * 页面初始化
 */
$(document).ready(function() {
	jQuery.extend({
		createGooFlow:function(bgDiv,property){
			return new GooFlow(bgDiv,property);
		}
	});	
	createFlow();
	/**
	 * 构造流程控件的Tab页面
	 */
	$("#tabs1").tabs();
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
var lastSelectedNode;
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
					createFlow();
					loadModel();
				}
				
				if (isGetTemplate == "startload") {
					createFlow();
					loadModel();
				}else if(isGetTemplate == "replace"){
						showTip(i18nShow('wf1')+templateObject.templateName+i18nShow('wf2')+nodeName+"?",function()	{
						createFlow();
						loadModel();
					});
				}else if(isGetTemplate == "reload"){
					showTip(i18nShow('tip_instance_designer_reload'),function(){
						createFlow();
						loadModel();
					});
				}else{
					//让左侧模板树还选择之前的模板
					if(lastSelectedNode){
						zTree.selectNode(lastSelectedNode);
					}else{
						showError(i18nShow('tip_instance_designer_retry'));
					}
				}
		} else {
			jsondata = "";
		}
	};
	
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
			    	if(templateObject) {
			    		//传入分组Id及模板类型
			    		$("#tempTypeId").val(templateObject.typeId);
			    		if(templateObject.jsonData){
			    			jsondata = JSON.parse(templateObject.jsonData);
			    			demo.loadData(jsondata);
			    		} else {
			    			jsondata = demo.exportData();
			    		}
				    	 
				    	//设置状态栏label
				    	setCurrentEditTemplateName(templateObject.templateName);
		    		}
			     },
		     error : function(e) {
		      	showTip("error");
		     }
		 });
	}
	
	var myExpandFun = function(){
		if(bizType == "tt"){					
			ajaxCall("/workflow/template/getTemplateByType.action",{"id":nodeId},
					asyncAddNode
			)
		}
	};	
	
	/*注册方法*/
	regFunction(myClickFun,myExpandFun);
	
	$.ajax({
	     type : "POST",
	     url : ctx+"/workflow/template/getTemplateTree.action",
	     async:true,
	     cache:false,
	     beforeSend: function () {
	        showTip("load");
        },
        error: function () {//请求失败处理函数   
            showError(i18nShow('tip_instance_designer_error1'));   
        },   
        success:function(data){ //请求成功后处理函数。     
        	zTreeInit("templateTree",data);
        	closeTip();
        }  	
	 });	
}

function setCurrentEditTemplateName(name){
	 $('#currentTemplate').html(i18nShow('instance_designer_currentModel')+"："+name);
}

/**
 * 重绘流程图
 * @param template
 */
function initFromTemp(template){
	var graph = getGraph();
	if(graph != null){
		graph.importTemplate(template);
	}
}

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
		title:i18nShow('instance_flow_save'),
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
	//表单验证
	$("#templateForm").validate({
		rules: {
			"templateName":{
				required:true,
				maxlength : 50
			},
			"templateType":{
				required:true
			}//,
//			"appType":{
//				required:true
//			}
		},
		messages: {
			"templateName":{
				required:i18nShow('validate_data_required'),
				maxlength : i18nShow('validate_instance_flow_1')
			},
			"templateType":{
				required:i18nShow('validate_data_required')
			}//,
//			"appType":{
//				required:"请选择模板所属应用"
//			}
		},
		submitHandler: function() {
			if(oper == "new"){
				addTemplate();
			}else if(oper =="edit"){
				saveTemplate();
			}else if(oper =="copy"){
				copyTemplate();
			}
		}
	});
	$("#templateForm").submit();
}
function closeTemplate(){
	$("#template_div").dialog("close");
}


function addTemplate(){
	//获取zTree信息和当前选中节点
	var zTree = $.fn.zTree.getZTreeObj("templateTree");
	//var sNodes = zTree.getSelectedNodes();
	var sNodes = zTree.getNodes()[0].children[0].children;
	var templateName = $("#templateName").val();
	var typeId = $("#templateType").val();
	//var appId = $("#appType").val();
	//没有选择任何节点直接添加
	/*if(sNodes.length == 0){
		sNodes = zTree.getNodes()[0].children[0].children;
	}*/
	$.ajax({
	     type : "POST",
	     url :  ctx+"/workflow/template/saveTemplate.action",
	     
	     data : {
	    	 "template.templateId":"",
	    	 "template.templateName":templateName,
	    	 "template.typeId":typeId,
//	    	 "template.appId":"",
//	    	 "template.creatorId":"",
	    	 "template.iconxml":"",
//	    	 "template.filePath":"",
//	    	 "template.modelId":""
	    	 "template.jsonData":''
	     },
	     async:true,
	     cache:false,
	     success : function(data) {
	    	 showTip(i18nShow("compute_res_operateSuccess"));
	    	 if(data.result == "success")
    		 {
	    		//清除当前节点下的子节点
	    		 $("#template_div").dialog("close");
					if (sNodes && sNodes.length>0) {
						 /*if(!sNodes[0].isParent){
							refreshZTreeAfterAdd(zTree,sNodes[0].getParentNode());
						}
					else{*/
							if(typeId==3){
								refreshZTreeAfterAdd(zTree,sNodes[0]);
							}else if(typeId==4){
								refreshZTreeAfterAdd(zTree,sNodes[1]);
							}
					//	}
						
					}
	    		 
	    		// addNode(data.templateId,templateName,null,null,false,"mb",true,false,$("#templateType").val());
	    		 
	    //		 initTempLateTree();
	/*    		 var treeObj = $.fn.zTree.getZTreeObj("templateTree");
	    		 treeObj.expandAll(true);*/

    		 }
	     },
	     error : function(e) {
	      	showTip(i18nShow("compute_res_requestError"));
	     }
	 });
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
			title:i18nShow('instance_flow_copy'),
			autoOpen : true,
			modal : true,
			width :350
		});
		
		//设置标示符
		oper = "copy";
	}else{
		showError(i18nShow('tip_instance_designer_error2'));
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
			title:i18nShow('instance_flow_update'),
			autoOpen : true,
			modal : true,
			width : 350
		});
		
		//设置标示符
		oper = "edit";
	}else{
		showError(i18nShow('tip_instance_designer_error3'));
	}
}
function saveTemplate(){
	var templateName = $("#templateName").val();
	var typeId = $("#templateType").val();
	//var appId = $("#appType").val();
	
	//更新操作
	$.ajax({
	     type : "POST",
	     url :  ctx+"/workflow/template/saveTemplate.action",
	     data : {
	    	 "template.templateId":templateObject.templateId,
	    	 "template.templateName":templateName,
	    	 "template.typeId":typeId
	    	 //"template.appId":appId
	     },
	     async:true,
	     cache:false,
	     success : function(data) {
	    	 //提示信息
	    	 showTip(i18nShow("compute_res_operateSuccess"));
	    	 
	    	 //如果保存成功
	    	 if(data.result == "success")
    		 {
	    		 $("#template_div").dialog("close");
	    		 
	    		 //修改当前持有的节点对象
	    		 //如果名称发生变动，更新下方状态栏中所选模板名称
	    		 if(templateObject.templateName != templateName){
	    			 templateObject.templateName = templateName;
	    			 setCurrentEditTemplateName(templateName);
	    			 //更新节点名称
	    			 var zTreeObj = $.fn.zTree.getZTreeObj("templateTree");
		    		 var selectNode = zTreeObj.getSelectedNodes()[0];
		    		 selectNode.name = templateObject.templateName;
		    		 zTreeObj.updateNode(selectNode);
	    		 }
	    		 //如果类型发生变动需要移动左侧树中相应节点
	    		 if(templateObject.typeId != typeId){
		    		 var zTreeObj = $.fn.zTree.getZTreeObj("templateTree");
		    		 var targetNode = zTreeObj.getNodesByParam("id",typeId)[0];
		    		 var selectNode = zTreeObj.getSelectedNodes()[0];
		    		 selectNode.name = templateObject.templateName;
		    		 zTreeObj.moveNode(targetNode,selectNode,"inner");
	    		 }
	    		 
//	    		 if(templateObject.appId != appId){
//	    			 templateObject.appId == appId;
//	    		 }
    		 }
	     },
	     error : function(e) {
	      	showTip("error");
	     }
	 });
}

/**
 * 复制模板
 */
function copyTemplate(){
	
	var templateName = $("#templateName").val();
	var typeId = $("#templateType").val();
	var zTree = $.fn.zTree.getZTreeObj("templateTree");
	var sNodes = zTree.getNodes()[0].children[0].children;
	//复制操作
	$.ajax({
	     type : "POST",
	     url :  ctx+"/workflow/template/saveTemplate.action",
	     data : {
	    	 "template.templateId":"",
	    	 "template.templateName":templateName,
	    	 "template.typeId":typeId,
//	    	 "template.appId":templateObject.appId,
	    	 "template.creatorId":templateObject.creatorId,
	    	 "template.iconxml":templateObject.iconxml,
	    	 "template.filePath":templateObject.filePath,
	    	 "template.modelId":templateObject.modelId,
	    	 "template.jsonData":templateObject.jsonData
	     },
	     async:true,
	     cache:false,
	     success : function(data) {
	    	 showTip(data.result);
	    	 
	    	 if(data.result == "success")
    		 {
	    		 $("#template_div").dialog("close");
	    		 if (sNodes && sNodes.length>0) {
						//refreshZTreeAfterAdd(zTree,sNodes[0].getParentNode());
						if(typeId==3){
							refreshZTreeAfterAdd(zTree,sNodes[0]);
						}else if(typeId==4){
							refreshZTreeAfterAdd(zTree,sNodes[1]);
						}
					}
	    		// addNode(data.templateId,templateName,null,null,false,"mb",true,false,$("#templateType").val());
	    		 
	    		// initTempLateTree();
	    	//	 var treeObj = $.fn.zTree.getZTreeObj("templateTree");
	    		 //treeObj.expandAll(true);
    		 }
	     },
	     error : function(e) {
	      	showTip(i18nShow("compute_res_requestError"));
	     }
	 });
}


/**
 * 删除选择模板
 */
function deleteTemplate(){
	if(templateObject){
			//根据templateId删除相应模板
		showTip(i18nShow('tip_instance_designer_delete'),function(){
			$.ajax({
				type : "POST",
				url : ctx+"/workflow/template/deleteTemplate.action",
				data : {
					"templateId":templateObject.templateId
				},
				async:true,
				cache:false,
				success : function(data) {
					if(data.result == "删除成功"){
						showTip(i18nShow('tip_delete_success'));
						
						//移除模板树中相应节点
						var zTreeObj = $.fn.zTree.getZTreeObj("templateTree");
						var deleteNode = zTreeObj.getSelectedNodes()[0];
						zTreeObj.removeNode(deleteNode);
						
						//把当前画布清空
						templateObject = null;//重置模板对象
						graph.getModel().clear();//清空画布
						editor.undoManager.clear();//清空撤销管理器缓存
						
						//设置选择模板标签为空
						setCurrentEditTemplateName("");
					}
				},
				error : function(e) {
					showTip("error");
				}
			});
		} );
	}else{
		showError(i18nShow('tip_instance_designer_error4'));
	}
}


/**
 * 点击开始节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function startStateClick(id,name,json){
	var param = path+"/formPage/globalParamsForm.jsp?state=design&id="+id;
	$('#globalParamsFrame').attr("src",param);
	$("#startComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#startComponent_div").dialog("option", "title", i18nShow('tip_instance_para'));
}
/**
 * 点击选择节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function decisionClick(id,name,json){
	var param = path+"/formPage/decisionForm.jsp?state=design&id="+id;
	$('#decisionFrame').attr("src",param);
	$("#decisionComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#decisionComponent_div").dialog("option", "title", i18nShow('tip_instance_choose'));
}
/**
 * 点击分支节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function forkClick(id,name,json){
	var param = path+"/formPage/forkForm.jsp?state=design&id="+id;
	$('#forkFrame').attr("src",param);
	$("#forkComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#forkComponent_div").dialog("option", "title", i18nShow('tip_instance_branch'));
}
/**
 * 点击聚合节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function joinClick(id,name,json){
	$("#joinComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 500
	});
	$("#joinComponent_div").dialog("option", "title", i18nShow('tip_instance_converge'));
}

/**
 * 点击子流程节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function subProcessClick(id,name,json){
	var param = path+"/formPage/copySubprocessForm.jsp?state=design&id="+id;
	$('#subprocessFrame').attr("src",param);
	$("#subprocessComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 485,
		height: 385
	});
	$("#subprocessComponent_div").dialog("option", "title", i18nShow('tip_instance_prog'));
}
/**
 * 点击结束节点回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 */
function endStateClick(id,name,json){
	$("#endComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 500
	});
	$("#endComponent_div").dialog("option", "title", i18nShow('tip_instance_ending'));
}

function setFrameHeight(height){
	$('#userComponentFrame').height(height);//重新设置iframe高度
	//重新设置对话框高度
	$("#userComponent_div").dialog({"height": height+90});
}

/**
 * 点击组件节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 * @param 组件Id
 * @param 组件表单路径
 */
function businessCompClick(id,name,json,page,type){
	var width,height;
	var param = "?state=design&id="+id;
	$('#userComponentFrame').attr("src",ctx+page+param);
	if(type=="OA"){
		width=400;
		height=360;
	}else if(type=="CMD"){
		width=700;
		height=400;
	}
	else{
		width=850;
		height=360;
	}
	$("#userComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : width,
		height: height
	});
	$("#userComponent_div").dialog("option", "title", name);
}
function getNextNodes(id){
	var graph = getGraph();
	if(graph != null){
		return graph.getNextNodes(id);
	}
	return null;
}

function getNextNodes(fromId){
	var array = new Array();
	var linesObj = demo.$lineData;
	for(var id in linesObj){
		var lineObj = linesObj[id];
		if(fromId == lineObj.from){
			var nodeObj = getComponentContent(lineObj.to);
			var nextNode ={
					"id" : lineObj.to,
					"name" : nodeObj.name
			}
			array.push(nextNode);
		}
	}
	return array;
}

/**
 * 子页面根据组件id获取组件信息
 * @param id
 * @returns
 */
function getComponentContent(id){
	var nodeObj = demo.$nodeData[id];
	var nodeObjTemp = {
	"name" : nodeObj.name,
	"compId" : nodeObj.type.toUpperCase(),
	"json" : nodeObj.data
	};
	return nodeObjTemp;
}

function getBaseComponentContent(id){
	var cell = graph.getModel().getCell(id);
	var objTemp = {
		"name":cell.getAttribute(NAME),
		"json":cell.getAttribute(DATA),
		"type":cell.getAttribute(TYPE)
	};
	return objTemp;
}

/**
 * 保存节点名字及属性数据
 */
function saveComponentNameData(id,name,data){
	demo.setName(id,name,"node");
	var nodeObj = demo.$nodeData[id];
	nodeObj.data = data;
	$("#userComponent_div").dialog("close");
	showTip(i18nShow('tip_save_success'));
}
function closeComponentDialog(id){
	$("#"+id).dialog("close");
}


/**
 * 校验模板合法性
 */
function validateTemplate(){
	//遍历所有节点，看是否存在同名节点
	var array = new Array();
	var array1 = new Array();
	var nodesObj = demo.$nodeData;
	if(JSON.stringify(nodesObj) != "{}"){
		for(var id in nodesObj){
			var nodeObj = nodesObj[id];
			if(array.indexOf(nodeObj.name) > -1){
				showTip(i18nShow('tip_instance_designer_error5'));
				return false;
			} else {
				array.push(nodeObj.name);
			}
		}
		//判断是否包含唯一开始结束节点
		var nodeNum = 0;
		for(var id in nodesObj){
			var nodeObj = nodesObj[id];
			if(nodeObj.type.indexOf("start") > -1){
				nodeNum += 2;
			} else if(nodeObj.type.indexOf("end") > -1){
				nodeNum += 3;
			}
		}
		if(nodeNum != 5){
			showTip(i18nShow('tip_instance_designer_error6'));
			return false;
		}
	} else {
		showTip(i18nShow('tip_instance_designer_error7'));
		return false;
	}
	jsondata = demo.exportData();//导出页面最新数据
	return true;
}


/**
 * 流程设计器mxgraph相关
 */
/** 节点属性常量 */
var NAME = 'Name', TYPE = 'Type', DATA = 'Data', PATH = 'Path', CID = 'ComponentId';
/** 画布对象 */
var graph;

/** 
 * 提交表单回调函数 
 */
function onSubmitComponent(id,name,json){
	/** 更新节点属性 */
	demo.setName(id,name,"node");
	var nodeObj = demo.$nodeData[id];
	nodeObj.data = json;
	showTip(i18nShow('wf3'));
}

/**
 * onload处理函数.初始化mxGraph
 * container为画布容器.outline为缩略图.toolbar为工具栏.sidebar为侧边栏.status为状态栏
 */ 
function main(container, outline, toolbar, sidebar, status){
	/** 检查浏览器兼容性 */
	if (!mxClient.isBrowserSupported()){
		mxUtils.error('浏览器不支持!', 200, false);
	}else{
		/** 设置全局常量.hotspot为热点大小 */
		mxConstants.MIN_HOTSPOT_SIZE = 16;
		mxConstants.DEFAULT_HOTSPOT = 1;
		
		/** 启用参考线 */
		mxGraphHandler.prototype.guidesEnabled = true;

	    /** 拖拽同时按下Alt禁用参考线 */
	    mxGuide.prototype.isEnabledForEvent = function(evt){
			return !mxEvent.isAltDown(evt);
		};

		/** 启用吸附到连线末端 */
		mxEdgeHandler.prototype.snapToTerminals = true;

		/** ie特殊设置 */
		if (mxClient.IS_QUIRKS){
			document.body.style.overflow = 'hidden';
			new mxDivResizer(container);
			new mxDivResizer(outline);
			new mxDivResizer(toolbar);
			new mxDivResizer(sidebar);
			new mxDivResizer(status);
		}
		
		/** 创建包装器editor.完成某些graph操作 */
		editor = new mxEditor();
		graph = editor.graph;
		editor.validation = false;

		/** 启用拖放 */
		graph.setDropEnabled(true);
		/** 启用画布拖动 */
		graph.setPanning(true);

		/** 禁用tooltips */
		graph.setTooltips(false);
		
		/** 连接指示图 */
		graph.connectionHandler.getConnectImage = function(state){
			return new mxImage(path+'/images/connector.gif', 16, 16);
		};

		/** 不允许无终点边 */
		graph.setAllowDanglingEdges(false);

		/** 设置graph容器div */
		editor.setGraphContainer(container);
		
		/** 设置快捷键配置文件 */
		var config = mxUtils.load(path+'/editors/config/keyhandler-commons.xml').getDocumentElement();
		editor.configure(config);
		
		/** 定义默认组用于分组，同时也用于侧边栏容器按钮 */
		var containerNode = createNode("Container");
		
		var group = new mxCell(containerNode, new mxGeometry(), 'group');
		group.setVertex(true);
		group.geometry.alternateBounds = new mxRectangle(0, 0, 50, 30);
		editor.defaultGroup = group;
		editor.groupBorderSize = 20;
		
		/** 只有泳道才被允许作为拖放目标 */
		graph.isValidDropTarget = function(cell, cells, evt){
			return this.isSwimlane(cell);
		};
		graph.isValidRoot = function(cell){
			return this.isValidDropTarget(cell);
		};

		/** 转换对象值为标签 */
		graph.convertValueToString = function(cell){
			var lbl = mxUtils.isNode(cell.value) ? cell.value.getAttribute(NAME) : cell.value;
	        return lbl;
	    };
	    
		/** 标签更改响应函数.更新 */
	  	var cellLabelChanged = graph.cellLabelChanged;
	    graph.cellLabelChanged = function(cell, newValue, autoSize){
	      if (mxUtils.isNode(cell.value)){
	        /** 为undo/redo的目的克隆当前值 */
	        var elt = cell.value.cloneNode(true);
	        elt.setAttribute(NAME, newValue);
	        newValue = elt;
	      }
	
	      cellLabelChanged.apply(this, arguments);
	    }; 
			
		/** 如果是分组则可以折叠 */
		graph.isCellFoldable = function(cell){
			return this.isSwimlane(cell);
		};

		/** 双击节点弹出一个窗口 */
		var dblClick = graph.dblClick;
		graph.dblClick = function(evt, cell){
			if(cell && cell.isVertex()){
				if (this.isEnabled() &&	!mxEvent.isConsumed(evt) &&	cell != null &&	this.isCellEditable(cell)){
					/** 根据节点属性创建表单 */
					if(mxUtils.isNode(cell.value)){
						switch(cell.value.getAttribute(TYPE)){
							case "start-state":
								startStateClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "end-state":
								endStateClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "decision":
								decisionClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "fork":
								forkClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "join":
								//joinClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "container":
								containerClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							case "sub-process":
								subProcessClick(cell.id,cell.value.getAttribute(NAME),cell.value.getAttribute(DATA));
								break;
							default : //业务组件回调
								businessCompClick(cell.id,
										cell.value.getAttribute(NAME),
										cell.value.getAttribute(DATA),
										cell.value.getAttribute(CID),
										cell.value.getAttribute(PATH),
										cell.value.getAttribute(TYPE));
								break;
						}
					}
				}
				/** 取消双击更改标签的默认行为 */
				mxEvent.consume(evt);
			}else{
				dblClick.apply(this,arguments);
			}
		};
		
		/** 启用连接性 */
		graph.setConnectable(true);
		
		/** 添加graph样式 */
		configureStylesheet(graph);

		/** 添加侧边栏图标 */
		addSidebarIcon(graph, sidebar,"StartNode",iconPath+'/component-start-icon.png');
		addSidebarIcon(graph, sidebar, "EndNode", iconPath+'/component-end-icon.png');
		addSidebarIcon(graph, sidebar, "DecisionNode",iconPath+'/component-decision-icon.png');
		addSidebarIcon(graph, sidebar,"ForkNode",iconPath+'/component-fork-icon.png');
		addSidebarIcon(graph, sidebar,"JoinNode",iconPath+'/component-join-icon.png');
		addSidebarIcon(graph, sidebar,"SubProcess",iconPath+'/component-subprocess-icon.png');

		/** 显示提示信息 */
		var hints = document.createElement('div');
		hints.style.position = 'absolute';
		hints.style.overflow = 'hidden';
		hints.style.width = '230px';
		hints.style.bottom = '56px';
		hints.style.height = '76px';
		hints.style.right = '20px';
		
		hints.style.background = 'black';
		hints.style.color = 'white';
		hints.style.fontFamily = 'Arial';
		hints.style.fontSize = '10px';
		hints.style.padding = '4px';

		mxUtils.setOpacity(hints, 50);
		
		/** 创建一个div，将工具栏按钮加入其中 */
		var spacer = document.createElement('div');
		spacer.style.display = 'inline';
		spacer.style.padding = '8px';
		
		/** 定义一个新的导出动作 */
		editor.addAction('export', function(editor, cell){
			var textarea = document.createElement('textarea');
			textarea.style.width = '400px';
			textarea.style.height = '400px';
			var enc = new mxCodec(mxUtils.createXmlDocument());
			var node = enc.encode(editor.graph.getModel());
			textarea.value = mxUtils.getPrettyXml(node);
			showModalWindow(graph, 'XML', textarea, 410, 440);
		});

		//addToolbarButton(editor, toolbar, 'export', '导出', path+'/images/export.png');
		
		/** 定义一个新的保存动作 */
		editor.addAction('save', function(editor, cell){
			saveTemplateDiagram();
		});
		//addToolbarButton(editor, toolbar, 'save', '保存', path+'/images/save.png');
		
		/** 定义部署动作 */
		editor.addAction("deploy",function(editor,cell){
			publishTemplate();
		});
		//addToolbarButton(editor, toolbar, 'deploy', '部署', path+'/images/export1.png');
		
		/** 添加按钮到状态栏 */
		addToolbarButton(editor, status, 'zoomIn', '', path+'/images/zoom_in.png', true);
		addToolbarButton(editor, status, 'zoomOut', '', path+'/images/zoom_out.png', true);
		addToolbarButton(editor, status, 'actualSize', '', path+'/images/view_1_1.png', true);
		addToolbarButton(editor, status, 'fit', '', path+'/images/fit_to_size.png', true);
		
		/** 创建大纲窗口 */
		new mxOutline(graph, outline);
		/** UI加载完毕.淡出加载画面 */
		var splash = document.getElementById('splash');
		if (splash != null){
			try{
				mxEvent.release(splash);
				mxEffects.fadeOut(splash, 100, true);
			}catch (e){
				splash.parentNode.removeChild(splash);
			}
		}
	}
};

/**
 * 点击工具栏按钮操作
 * @param action
 */
function processGraph(action){
	if(jsondata){
		if(action=="save"){
			saveTemplateDiagram();
		}else if(action=="export"){
			exportTemplate();
		}else if(action=="import"){
			importTemplate();
		}else if(action == "deploy"){
			publishTemplate();
		}
	}else {
		showTip(i18nShow('wf4'));
	}
}

/**
 * 保存整个流程模板
 */
function saveTemplateDiagram(){
	if(templateObject){
		if(validateTemplate()){
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplateDiagramNew.action",
				data : {
					'template.templateId':templateObject.templateId,
					'template.templateName':templateObject.templateName,
					'template.typeId':templateObject.typeId,
					'template.jsonData':JSON.stringify(jsondata)
				},
				async:true,
				cache:false,
				success : function(msg) {
					templateObject.jsonData = JSON.stringify(jsondata);
					showTip(msg.result);
				},
				error : function(e) {
					showTip("error");
				}
			});
		}
	}else{
		showError(i18nShow('wf5'));
	}
}

/**
 * 导出流程模板
 */
function exportTemplate(){
	$("#exportWorkFlow").val("");
	$("#exportData").dialog({
		autoOpen : true,
		modal : true,
		width : 680,
		height : 420,
		title:i18nShow('zh3')
	});
	$("#exportWorkFlow").val(JSON.stringify(demo.exportData()));
}

/**
 * 导入流程模板
 */
function importTemplate(){
	$("#importWorkFlow").val("");
	$("#importWorkFlow_div").dialog({
		autoOpen : true,
		modal : true,
		width : 680,
		height : 420,
		title : i18nShow('zh1')
	});
}

/**
 * 导入流程模板-确定
 */
function saveImport(){
	showTip(i18nShow('wf9'),function(){
		var tempJson = $("#importWorkFlow").val();
		jsondata = JSON.parse(tempJson);
		createFlow();
		demo.loadData(jsondata);
		cancelImport();
	})
}
/**;
 * 导入流程模板-取消
 */
function cancelImport(){
	 $("#importWorkFlow_div").dialog("close");
}

/**
 * 导出流程模板-取消
 */
function cancelExport(){
	 $("#exportData").dialog("close");
}

function selectExport(){
	var exportWorkFlow = $("#exportWorkFlow").val();
	alert();
}

/**
 * 流程定义的发布
 */
function publishTemplate(){
	//先保存
	if(templateObject){
		if(validateTemplate()){
			if(jsondata){
				$.ajax({
				     type : "POST",
				     url :  ctx+"/workflow/template/saveTemplateDiagramNew.action",
				     data : {
				    	 'template.templateId':templateObject.templateId,
				    	 'template.templateName':templateObject.templateName,
				    	 'template.typeId':templateObject.typeId,
				    	 'template.jsonData':JSON.stringify(jsondata)
				     },
				     async:true,
				     cache:false,
				     success : function(msg) {
			    		 if(msg.result == "success"){
			    			//后发布
		    				$.ajax({
		    				     type : "POST",
		    				     url :  ctx+"/workflow/template/publishTemplateNew.action",
		    				     data : {
		    				    	 "id" : templateObject.templateId
		    				     },
		    				     async:true,
		    				     cache:false,
		    				     success : function(msg) {
		    				    	 showTip(i18nShow('wf6'));
		    				     },
		    				     error : function(e) {
		    				      	 showError(i18nShow('wf7'));
		    				     }
		    				 });
			    		 }
				     },
				     error : function(e) {
				      	showTip("error");
				     }
				 });
			}
		}
	}else{
		showError(i18nShow('wf8'));
	}
}

/**
 * 添加工具栏
 * @param editor
 * @param toolbar
 * @param action
 * @param label
 * @param image
 * @param isTransparent
 */
function addToolbarButton(editor, toolbar, action, label, image, isTransparent){
	var button = document.createElement('button');
	button.style.fontSize = '10';
	button.style.paddingTop = '2px';
	button.style.paddingRight = '4px';
	button.style.paddingBottom = '2px';
	button.style.paddingLeft = '4px';
	if (image != null){
		var img = document.createElement('img');
		img.setAttribute('src', image);
		img.style.width = '16px';
		img.style.height = '16px';
		img.style.verticalAlign = 'middle';
		img.style.marginRight = '2px';
		button.appendChild(img);
	}
	if (isTransparent){
		button.style.background = 'transparent';
		button.style.color = '#FFFFFF';
		button.style.border = 'none';
	}
	mxEvent.addListener(button, 'click', function(evt){
		editor.execute(action);
	});
	mxUtils.write(button, label);
	toolbar.appendChild(button);
};

function showModalWindow(graph, title, content, width, height){
	var background = document.createElement('div');
	background.style.position = 'absolute';
	background.style.left = '0px';
	background.style.top = '0px';
	background.style.right = '0px';
	background.style.bottom = '0px';
	background.style.background = 'black';
	mxUtils.setOpacity(background, 50);
	document.body.appendChild(background);
	
	if (mxClient.IS_IE){
		new mxDivResizer(background);
	}
	var x = Math.max(0, document.body.scrollWidth/2-width/2);
	var y = Math.max(10, (document.body.scrollHeight || document.documentElement.scrollHeight)/2-height*2/3);
	var wnd = new mxWindow(title, content, x, y, width, height, false, true);
	wnd.setClosable(true);
	
	/** Fades the background out after after the window has been closed */
	wnd.addListener(mxEvent.DESTROY, function(evt){
		graph.setEnabled(true);
		mxEffects.fadeOut(background, 50, true, 
			10, 30, true);
	});

	graph.setEnabled(false);
	graph.tooltipHandler.hide();
	wnd.setVisible(true);
	
	return wnd;
};

/**
 * 添加侧边栏图标
 */
function addSidebarIcon(graph, sidebar, nodeType, image){
	var node = createNode(nodeType);
	/**
	 * 当图片被拖放到画布上时的执行函数
	 * graph 画布 
	 * evt   触发的事件
	 * cell  目标节点
	 * x|y   当前坐标
	 */
	var funct = function(graph, evt, cell, x, y){
		/** 是否新建流程图，是才允许添加组件到画布 */
		if(templateObject == null){
			showTip(i18nShow('wf10'));
			return;
		}
		/** 节点合法性检查 */
		if(doValid(graph, nodeType)){
			var parent = null;
			var model = graph.getModel();
			var v1 = null;
			
			model.beginUpdate();
			try{
				/**
				 * 如果目标是泳道，则将新节点父节点设置为该泳道
				 * 同时设置添加节点坐标为相对泳道的坐标
				 * 否则将新节点父节点设置为根节点
				 */ 
				if(graph.isSwimlane(cell)){
					parent = cell;
					x -= cell.geometry.x;
					y -= cell.geometry.y;
				}else{
					parent = graph.getDefaultParent();
				}
				
				/**
				 * 如果添加节点类型为容器.设置其样式为group.并设置节点折叠大小
				 * 否则设置应用默认节点样式
				 */ 
				if(nodeType != "container"){
					v1 = graph.insertVertex(parent, null, createNode(nodeType), x, y, 80, 80, 'image='+image);
				}
				else{
					v1 = graph.insertVertex(parent, null, createNode(nodeType), x, y, 80, 80, 'group');
					v1.geometry.alternateBounds = new mxRectangle(0, 0, 80, 40);
					v1.setConnectable(false);
				}
			}finally{
				model.endUpdate();
			}
			graph.setSelectionCell(v1);
		}else{
			showTip(i18nShow('wf11'));
		}
	};
	
	var doValid = function doValidation(graph,nodeType){
		if(nodeType == 'StartNode' || nodeType == 'EndNode'){
			return checkUniq(graph.getDefaultParent(),nodeType);
		}
		return true;
	};
	
	var checkUniq = function(parent,nodeType){
		var cells = graph.getChildVertices(parent);
		for(var i = 0;i < cells.length; i++){
			if(graph.isSwimlane(cells[i])){
				return checkUniq(cells[i],nodeType);
			}else{
				if(cells[i].value.nodeName == nodeType){
					return false;
				}
			}
		}
		return true;
	};
	/** 创建侧边栏图标 */
	var div = $("<div></div>").css({
		width:"60px",
		height:"60px",
		margin:"5px 5px 5px 5px",
		float:"left"
	})[0];

	var img = $("<img/>").attr({src: image,title:i18nShow('wf12')}).css({
		width:"40px",
		height:"40px",
		paddingLeft:"10px",
		cursor:"pointer"
	})[0];

	var lbl = $("<span/>").html(node.getAttribute(NAME)).css({
		display:"block",width:"60px",height:"20px",textAlign:"center"
	})[0];
	
	div.appendChild(img);
	div.appendChild(lbl);
	sidebar.appendChild(div);
	
	var dragElt = document.createElement('img');
	dragElt.setAttribute('src', image);
	dragElt.style.width = '80px';
	dragElt.style.height = '80px';
	dragElt.style.opacity = 0.5;
	  					
	/** 拖放侧边栏图标处理函数 */
	var ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
	ds.setGuidesEnabled(true);
};

/**
 * 返回需要节点xml标签
 * @param nodeType StartNode/EndNode/DecisionNode/ForkNode/JoinNode/Container/SubProcess/CustomNode
 * @returns xmlNode
 */
function createNode(nodeType){
	var doc = mxUtils.createXmlDocument();
	var node;
	switch(nodeType){
		case "StartNode"   :node = doc.createElement("StartNode");
							node.setAttribute(NAME,i18nShow('wf13'));
							node.setAttribute(TYPE,"start-state");
							node.setAttribute(DATA,"{}");
							break;
		case "EndNode"     :node = doc.createElement("EndNode");
							node.setAttribute(NAME,i18nShow('wf14'));
							node.setAttribute(TYPE,"end-state");
							node.setAttribute(DATA,"{}");
							break;
		case "DecisionNode":node = doc.createElement("DecisionNode");
							node.setAttribute(NAME,i18nShow('wf15'));
							node.setAttribute(TYPE,"decision");
							node.setAttribute(DATA,"{}");
							break;
		case "ForkNode"    :node = doc.createElement("ForkNode");
							node.setAttribute(NAME,i18nShow('wf16'));
							node.setAttribute(TYPE,"fork");
							node.setAttribute(DATA,"{}");
							break;
		case "JoinNode"    :node = doc.createElement("JoinNode");
							node.setAttribute(NAME,i18nShow('wf17'));
							node.setAttribute(TYPE,"join");
							node.setAttribute(DATA,"{}");
							break;
		case "Container"   :node = doc.createElement("CustomNode");
							node.setAttribute(NAME,i18nShow('wf18'));
							node.setAttribute(TYPE,"container");
							node.setAttribute(DATA,"{}");
							break;
		case "SubProcess"  :node = doc.createElement("CustomNode");
							node.setAttribute(NAME,i18nShow('wf18'));
							node.setAttribute(TYPE,"sub-process");
							node.setAttribute(DATA,"{}");
							break;
		default			   :var comp = customNodeDef[nodeType];
							node = doc.createElement("CustomNode");
							node.setAttribute(CID,comp.componentId);
							node.setAttribute(NAME,comp.componentName);
							node.setAttribute(TYPE,comp.componentType);
							node.setAttribute(DATA,"{}");
							node.setAttribute(PATH,comp.pagePathName);
							break;
	}
	
	return node;
}

/**
 * 配置样式
 */
function configureStylesheet(graph){
	/** 定义默认节点样式 */
	var style = new Object();
	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_LABEL;
	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
	style[mxConstants.STYLE_IMAGE_ALIGN] = mxConstants.ALIGN_CENTER;
	style[mxConstants.STYLE_IMAGE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
	style[mxConstants.STYLE_SPACING_TOP] = '50';
	style[mxConstants.STYLE_FONTCOLOR] = '#1d258f';
	style[mxConstants.STYLE_FONTFAMILY] = '微软雅黑';
	style[mxConstants.STYLE_FONTSIZE] = '12';
	style[mxConstants.STYLE_FONTSTYLE] = '1';
	style[mxConstants.STYLE_ROUNDED] = '1';
	style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
	style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
	style[mxConstants.STYLE_OPACITY] = '80';
	graph.getStylesheet().putDefaultVertexStyle(style); 

	style = new Object();
	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_BOTTOM;
	style[mxConstants.STYLE_FILLCOLOR] = '#CDE5F6';
	style[mxConstants.STYLE_GRADIENTCOLOR] = '#F8FBFE';
	style[mxConstants.STYLE_STROKECOLOR] = '#C0D3E2';
	style[mxConstants.STYLE_FONTCOLOR] = '#000000';
	style[mxConstants.STYLE_ROUNDED] = false;
	style[mxConstants.STYLE_OPACITY] = '80';
	style[mxConstants.STYLE_STARTSIZE] = '30';
	style[mxConstants.STYLE_FONTSIZE] = '16';
	style[mxConstants.STYLE_FONTSTYLE] = 1;
	graph.getStylesheet().putCellStyle('group', style);
	
	style = new Object();
	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
	style[mxConstants.STYLE_FONTCOLOR] = '#774400';
	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
	style[mxConstants.STYLE_PERIMETER_SPACING] = '6';
	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
	style[mxConstants.STYLE_FONTSIZE] = '14';
	style[mxConstants.STYLE_FONTSTYLE] = 1;
	style[mxConstants.STYLE_SPACING_BOTTOM] = '56';
	style[mxConstants.STYLE_IMAGE_WIDTH] = '64';
	style[mxConstants.STYLE_IMAGE_HEIGHT] = '64';
	graph.getStylesheet().putCellStyle('port', style);
	
	style = graph.getStylesheet().getDefaultEdgeStyle();
	style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
	style[mxConstants.STYLE_STROKEWIDTH] = '2';
	style[mxConstants.STYLE_ROUNDED] = true;
	style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
};