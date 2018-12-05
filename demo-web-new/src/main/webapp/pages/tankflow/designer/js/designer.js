var demo;
var canvasID;
var jsondata;
// 工作流文件夹路径
var parentNodeId;
// 定义流程定义(模板)对象
var templateObject = null;
// 添加还是修改操作标示符
var oper;
// 当前选择树节点
var currentNode;
// 双击节点弹出框
var componentDiv;
var componentFrame;

function TankFlow(){}

TankFlow.prototype = {
	/**
	 * 创建画布对象
	 * 
	 * @param canvasId
	 *            画布ID
	 * @param title
	 *            画布题目
	 * @returns
	 */
	createFlow:function(canvasId, title, userComponentDiv, userComponentFrame){
		canvasID = canvasId;
		componentDiv = userComponentDiv;
		componentFrame = userComponentFrame;
		$("#" + canvasId).empty();
		var property = {
//			width : 900,
//			height : 900,
			toolBtns : ["start", "end", "decision", "fork", "join", "cmd", "task", "scr", "api","sub-process"],
			haveHead : false,
			headLabel : true,
			initLabelText : "initLabelText",
			headBtns : [ "new", "open", "save", "undo", "redo", "reload" ],// 如果haveHead=true，则定义HEAD区的按钮
			haveTool : true,
			haveGroup : false,
			useOperStack : true
		};
		var remark = {
			"cursor" : "选择指针",
			"direct" : "结点连线",
			"start" : "入口结点",
			"end" : "结束结点",
			"decision" : "选择",
			"task" : "任务结点",
			"cmd" : "命令",
			"task" : "人工任务",
			"scr" : "脚本",
			"api" : "API",
			"fork" : "分支",
			"join" : "聚合",
		    "sub-process":"子流程"
		};
		demo = new GooFlow($("#" + canvasId), property, "designer", userComponentDiv, userComponentFrame);
		demo.setNodeRemarks(remark);
		demo.resetScale(1.0);
	},
	/**
	 * 根据typeId获取模板节点
	 * 
	 * @param typeId
	 * @returns
	 */
	getTemplatesByTypeId : function (typeId) {
		$.ajax({
		     type : "POST",
		     url : ctx + "/workflow/template/getTemplateByType.action",
		     data : {
		    	 "typeId" : typeId
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	 ajaxResult = data;
		    	 asyncAddNode();
		     },
		     error : function(e) {
		      	showTip("获取模板列表发生错误\n" + e);
		     }
		 });
	},
	/**
	 * 根据模板ID获取模板信息
	 * 
	 * @param templateId
	 * @param tempTypeId
	 * @returns
	 */
	getTemplateById : function(templateId, tempTypeId) {
		$.ajax({
		     type : "POST",
		     url : ctx+"/workflow/template/getTemplateById.action",
		     data : {
		    	 "id":templateId
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	templateObject = data;
		    	if(templateObject) {
		    		// 传入分组Id及模板类型
		    		$("#"+tempTypeId).val(templateObject.typeId);
		    		if(templateObject.jsonData){
		    			jsondata = JSON.parse(templateObject.jsonData);
		    			demo.loadData(jsondata);
		    		} else {
		    			jsondata = demo.exportData();
		    		}
			    	// 设置状态栏label
		    		TankFlow.setCurrentEditTemplateName(templateObject.templateName);
	    		}
		     },
		     error : function(e) {
		      	showTip("获取模板数据发生错误\n" + e);
		     }
		 });
	},

	setCurrentEditTemplateName : function(name){
		 $('#currentTemplate').html("当前编辑模板："+name);
	},


	/**
	 * 新建模板
	 * 
	 * @param templateName
	 * @param typeId
	 * @returns
	 */
	addTemplate : function(templateName, typeId){
		return new Promise(function(resolve, reject) { 
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplate.action",
				data : {
					"template.templateId":"",
					"template.templateName":templateName,
					"template.typeId":typeId,
					"template.iconxml":'',
					"template.jsonData":''
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
		});
	},

	/**
	 * 更新模板
	 * 
	 * @param templateId
	 * @param templateName
	 * @param typeId
	 * @returns
	 */
	saveTemplate : function(templateId, templateName, typeId){
		return new Promise(function(resolve, reject) { 
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplate.action",
				data : {
					"template.templateId":templateId,
					"template.templateName":templateName,
					"template.typeId":typeId
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
		});
	},

	/**
	 * 复制模板
	 * 
	 * @param templateName
	 * @param typeId
	 * @param creatorId
	 * @param iconxml
	 * @param filePath
	 * @param modelId
	 * @param jsonData
	 * @returns
	 */
	copyTemplate : function(templateName, typeId, creatorId, iconxml, filePath, modelId, jsonData){
		return new Promise(function(resolve, reject) { 
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplate.action",
				data : {
					"template.templateId":"",
					"template.templateName":templateName,
					"template.typeId":typeId,
					"template.creatorId":creatorId,
					"template.iconxml":iconxml,
					"template.filePath":filePath,
					"template.modelId":modelId,
					"template.jsonData":jsonData
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
		});
	},

	/**
	 * 删除选择模板
	 * 
	 * @param templateId
	 * @returns
	 */
	deleteTemplate : function(templateId){
		return new Promise(function(resolve, reject) { 
			$.ajax({
				type : "POST",
				url : ctx+"/workflow/template/deleteTemplate.action",
				data : {
					"templateId":templateId
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
		});
	},

	/**
	 * 保存模板视图
	 * 
	 * @param templateObject
	 * @param jsondata
	 * @returns
	 */
	saveTemplateDiagram : function (templateObject, jsondata){
		return new Promise(function(resolve, reject) {
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplateDiagram.action",
				data : {
					'template.templateId':templateObject.templateId,
					'template.templateName':templateObject.templateName,
					'template.typeId':templateObject.typeId,
					'template.jsonData':JSON.stringify(jsondata)
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
			
		});
	},
	/**
	 * 保存流程模板视图
	 * 
	 * @param templateObject
	 * @param jsondata
	 * @returns
	 */
	saveTemplateAndDiagram : function (templateObject){
		return new Promise(function(resolve, reject) {
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/saveTemplateAndDiagram.action",
				data : {
					'templateId':templateObject.templateId,
					'templateName':templateObject.templateName,
					'typeId':templateObject.typeId,
					'creatorId':templateObject.creatorId,
					'jsonData':JSON.stringify(demo.exportData())
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data);
				},
				error : function(e) {
					reject(e);
				}
			});
			
		});
	},
	/**
	 * 发布模板
	 * 
	 * @param templateId
	 * @returns
	 */
	publishTemplate : function (templateId) {
		return new Promise(function(resolve, reject) { 
			$.ajax({
				type : "POST",
				url :  ctx+"/workflow/template/publishTemplate.action",
				data : {
					"id" : templateId
				},
				async:true,
				cache:false,
				success : function(data) {
					resolve(data.result);
				},
				error : function(e) {
					reject(e);
				}
			});
		});
	},

	/**
	 * 撤销操作
	 */
	undo : function() {
		demo.undo();
	},
	
	/**
	 * 重做操作
	 */
	redo : function() {
		demo.redo();
	},
	
	/**
	 * 打印流程图
	 */
	printProcessGraph : function() {
		demo.print();
	},
	
	/**
	 * 导出流程图
	 */
	exportDiagram : function(fileName) {
		demo.exportDiagram(fileName);
	},
	
	/**
	 * 流程图缩放变大
	 */
	bigger : function() {
		demo.bigger();
	},
	
	/**
	 * 流程图缩放变小
	 */
	smaller : function() {
		demo.smaller();
	},
	
	/**
	 * 导入流程json文件
	 */
	importAsFile : function() {
		var downloadURL = ctx + "/workflow/template/importAsFile.action";
		var form = $("<form>");  
		form.attr('action',downloadURL);  
		form.attr('method','post');  
	    form.attr('enctype','multipart/form-data');   
	    form.attr('style','display:none');   
	                          
	    var input1 = $('<input>');   
	    input1.attr('type','file');   
	    input1.attr('name','upload');   
	    input1.attr('style','display:none');     
	    form.append(input1);   
	    $('body').append(form);
	    input1.click();
	    input1.change(function(){
			var filePath = input1.val();
			if (filePath != "") {
				var extStart = filePath.lastIndexOf(".");
				var ext = filePath.substring(extStart, filePath.length).toUpperCase();
				if (ext != ".TXT") {
					showTip("请上传文件格式为.txt的文件!");
					return false;
				}
				var options ={
						type : "POST",
						url : downloadURL,
						data : null,
						dataType : "text",
						async : true,
						cache : false,
						success : function(data) {
							var listStartIndex = data.indexOf("[");
							var objStartIndex = data.indexOf("{");
							if (listStartIndex > objStartIndex) {
								data = data.substring(objStartIndex, data.lastIndexOf("}") + 1);
							} else {
								data = data.substring(listStartIndex, data.lastIndexOf("]") + 1);
							}
							var regn = new RegExp("\\\\\"{","g");
							var regn1 = new RegExp("}\\\\\"","g");
							data = data.replace(regn,"{");
							data = data.replace(regn1,"}");
							var regn2 = new RegExp("\\\\\"\\\[","g");
							var regn3 = new RegExp("\\\]\\\\\"","g");
							data = data.replace(regn2,"[");
							data = data.replace(regn3,"]");
							jsondata = JSON.parse(data);
							TankFlow.createFlow(canvasID, "designer", componentDiv, componentFrame);
							demo.loadData(jsondata);
							showTip("导入模板完成");
						},
						error : function(e) {
							showTip("导入模板发生错误，详情：" + e.message);
						}
				}
	            form.ajaxSubmit(options);  
			}else{
				showTip("请选择文件");
				return false;
			}
		});
	
	},
	
	/**
	 * 导出流程json文件
	 */
	exportAsFile : function() {
	    var downloadURL = ctx + "/workflow/template/exportAsFile.action";  
	    var form = $("<form>");  
	    form.attr('style','display:none');   // 在form表单中添加查询参数
	    form.attr('method','post');  
	    form.attr('action',downloadURL);  
	                          
	    var input1 = $('<input>');   
	    input1.attr('id','jsonData');   
	    input1.attr('name','jsonData');   
	    input1.attr('type','text');   
	    input1.attr('value',JSON.stringify(templateObject.jsonData));
	    input1.attr('style','display:none');
	    var input2 = $('<input>');   
	    input2.attr('id','templateName');   
	    input2.attr('name','templateName');   
	    input2.attr('type','text');   
	    input2.attr('value',templateObject.templateName);  
	    input2.attr('style','display:none');
	    
	    form.append(input1); 
	    form.append(input2);
	    $('body').append(form);
	    form.submit();
	},
	
	/**
	 * 校验模板合法性
	 */
	validateTemplate : function(){
		// 遍历所有节点，看是否存在同名节点
		var array = new Array();
		var array1 = new Array();
		var nodesObj = demo.$nodeData;
		if(JSON.stringify(nodesObj) != "{}"){
			for(var id in nodesObj){
				var nodeObj = nodesObj[id];
				if(array.indexOf(nodeObj.name) > -1){
					showTip('流程模板中不能存在同名节点');
					return false;
				} else {
					array.push(nodeObj.name);
				}
			}
			// 判断是否包含唯一开始结束节点
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
				showTip('流程模版必须包含唯一的开始和结束节点');
				return false;
			}
		} else {
			showTip('流程模版无节点');
			return false;
		}
		jsondata = demo.exportData();// 导出页面最新数据
		return true;
	},
	
	/**
	 * 自定义控件配置窗口
	 * 
	 * @param height
	 * @param userComponentFrame
	 * @param userComponent_div
	 * @returns
	 */
	setFrameHeight : function (height){
		$('#' + componentFrame).height(height);// 重新设置iframe高度
		// 重新设置对话框高度
		$("#" + componentDiv).dialog({"height": height});
	},

	/**
	 * 保存节点名字及属性数据
	 * 
	 * @param id
	 * @param name
	 * @param data
	 * @returns
	 */
	saveComponentNameData : function (id, name, data){
		demo.setName(id, name, "node");
		var nodeObj = demo.$nodeData[id];
		nodeObj.data = data;
		$("#" + componentDiv).dialog("close");
		showTip("保存成功！");
	},
	
	/**
	 * 子页面根据组件id获取组件信息
	 * 
	 * @param id
	 * @returns
	 */
	getComponentContent : function(id){
		var nodeObj = demo.$nodeData[id];
		var nodeObjTemp = {
		"name" : nodeObj.name,
		"compId" : nodeObj.type.toUpperCase(),
		"json" : nodeObj.data
		};
		return nodeObjTemp;
	},
	
	getNextNodes : function(fromId){
		var array = new Array();
		var linesObj = demo.$lineData;
		for(var id in linesObj){
			var lineObj = linesObj[id];
			if(fromId == lineObj.from){
				var nodeObj = TankFlow.getComponentContent(lineObj.to);
				var nextNode ={
						"id" : lineObj.to,
						"name" : nodeObj.name
				}
				array.push(nextNode);
			}
		}
		return array;
	},

	/**
	 * 关闭窗口
	 * 
	 * @param id
	 * @returns
	 */
	closeComponentDialog : function (){
		$("#"+componentDiv).dialog("close");
	},

	/**
	 * 提交表单回调函数
	 */
	onSubmitComponent : function (id,name,json){
		demo.setName(id,name,"node");
		var nodeObj = demo.$nodeData[id];
		nodeObj.data = json;
		showTip("保存成功！");
	},
	
	/**
	 * 导入流程模板
	 * 
	 * @returns
	 */
	saveImport : function (importContentId, importDivId){
		showTip("原模板将被覆盖,确定导入新模板？",function(){
			var tempJson = $("#"+importContentId).val();
			jsondata = JSON.parse(tempJson);
			TankFlow.createFlow(canvasID, "designer", componentDiv, componentFrame);
			demo.loadData(jsondata);
			TankFlow.cancelImport(importDivId);
		})
	},

	cancelImport : function (importDivId){
		 $("#"+importDivId).dialog("close");
	},
	

	promise : function() {
		return new Promise(function(resolve, reject) {
			showTip("确认强制结束？", function() {
				TankFlow.promiseTest().then(function(data) {
					resolve(data);
				}, function(error) {
					reject(error);
				});
			});
		});
	},
	promiseTest : function() {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type : "get",
				url : ctx + "/workflow/template/getTemplateByType.action",
				data : {
					"typeId" : "E889870EAE9144AA8F19DFA5815F0C64"
				},
				async : true,
				cache : false,
				success : function(data) {
					resolve(data)// 在异步操作成功时调用
				},
				error : function(e) {
					reject(e);// 在异步操作失败时调用
				}
			});
		});
	}
//	promiseTest : function() {
//		var result;
//		$.ajax({
//			type : "get",
//			url : ctx + "/workflow/template/getTemplateByType.action",
//			data : {
//				"typeId" : "E889870EAE9144AA8F19DFA5815F0C64"
//			},
//			async : true,
//			cache : false,
//			success : function(data) {
//				result = data;
//			},
//			error : function(e) {
//				result = e;
//			}
//		});
//		console.log(result);
//		setTimeout(function() {
//			console.log(result);
//		}, 2000)
//		return result;
//	}
//	promiseTest : function() {
//		return $.ajax({
//			type : "get",
//			url : ctx + "/workflow/template/getTemplateByType.action",
//			data : {
//				"typeId" : "E889870EAE9144AA8F19DFA5815F0C64"
//			},
//			async : true,
//			cache : false,
//			success : function(data) {
//				setTimeout(function() {
//					return data;
//				},7000);
//			},
//			error : function(e) {
//				result = e;
//			}
//		});
//	}
}




