<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<head>

<title>自动化脚本</title>
<script type="text/javascript" src="${ctx}/pages/cloud/package/js/main.js"></script>
<script type="text/javascript">
	var scriptId = "";
	var zTree;
	var demoIframe;

	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "nid",
				pIdKey : "pid",
				rootPId : ""
			}
		},
		callback : {
			onMouseDown : onMouseDown
		}
	};

	var zNodes = [];
	$(document).ready(function() {
		initTree();
	});
	
	//初始化左侧树
	function initTree(){
		var t = $("#tree");
		// 加载左侧树
		$.ajax({
			type : "post",
			url : ctx + "/cloud-service/script/loadTree.do",
			data : {},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				zNodes = data.treeList;
				t = $.fn.zTree.init(t, setting, zNodes);
				demoIframe = $("#testIframe");
				demoIframe.bind("load", loadReady);
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var nodes = treeObj.getNodes();
				if (nodes.length>0) {
					treeObj.selectNode(nodes[0]);
				}
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
	
	
	var theNode;
	function onMouseDown(event, treeId, treeNode) {
		theNode = treeNode;
		load(treeNode.id, treeNode.type);
	}
	function load(nodeId, nodeType) {

		if (nodeType == '0') {
			$("#div0").show();
			$("#div1").hide();
			$("#div2").hide();
			$("#div3").hide();
			return false;
		}
		
		$.ajax({
			type : "post",
			url : ctx + "/cloud-service/script/load.do?params['id']=" + nodeId + "&params['type']=" + nodeType,
			data : {},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				$("#div0").hide();
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (nodeType == "1") {

					$("#div1").show();
					$("#div2").hide();
					$("#div3").hide();
					$("#packageModelVO_name").html(data.result.data.name);
					$("#packageModelVO_remark").html(data.result.data.remark);
					$("#packageModelVO_filePath").html(data.result.data.filePath);
					$("#packageModelVO_fzr").html(data.result.data.fzr);

					$("#packageModelVO\\.id").val(data.result.data.id);
					$("#packageModelVO\\.name").val(data.result.data.name);
					$("#packageModelVO\\.remark").val(data.result.data.remark);
					$("#packageModelVO\\.filePath").val(data.result.data.filePath);
					$("#packageModelVO\\.fzr").val(data.result.data.fzr);
					$("#modelModelVO\\.packageModelVO\\.id").val(data.result.data.id);
				} else if (nodeType == "2") {
					
					$("#div1").hide();
					$("#div2").show();
					$("#div3").hide();
					$("#modelModelVO_name").html(data.result.data.name);
					$("#modelModelVO_remark").html(data.result.data.remark);
					$("#modelModelVO_filePath").html(data.result.data.filePath);

					var pnode = zTree.getNodeByParam("nid", '1_' + data.result.data.packageModelVO.id, null);
					$("#modelModelVO_packageModelVO_name").html(pnode.name);
 
					document.getElementById("modelModelVO.id").value = data.result.data.id;
					document.getElementById("modelModelVO.name").value = data.result.data.name;
					document.getElementById("modelModelVO.remark").value = data.result.data.remark;
					document.getElementById("modelModelVO.filePath").value = data.result.data.filePath;
					document.getElementById("scriptModelVO.modelModelVO.id").value = data.result.data.id;
				
				} else if (nodeType == "3") {
					$("#div0").hide();
					$("#div1").hide();
					$("#div2").hide();
					$("#div3").show();

					$("#scriptModelVO_id").html(data.result.data.id);
					$("#scriptModelVO_name").html(data.result.data.name);
					$("#scriptModelVO_fileName").html(data.result.data.fileName);
					$("#scriptModelVO_hadFz").html(data.result.data.hadFz);
					$("#scriptModelVO_checkCode").html(data.result.data.checkCode);
					$("#scriptModelVO_runUser").html(data.result.data.runUser);
					$("#scriptModelVO_remark").html(data.result.data.remark);
					var pnode = zTree.getNodeByParam("nid", '2_' + data.result.data.modelModelVO.id, null);
					$("#scriptModelVO_modelModelVO_name").html(pnode.name);

					scriptId = data.result.data.id;
					$("#scriptModelVO\\.id").val(data.result.data.id);
					$("#scriptModelVO\\.name").val(data.result.data.name);
					$("#scriptModelVO\\.fileName").val(data.result.data.fileName);
					$("#scriptModelVO\\.hadFz").val(data.result.data.hadFz);
					$("#scriptModelVO\\.checkCode").val(data.result.data.checkCode);
					$("#scriptModelVO\\.runUser").val(data.result.data.runUser);
					$("#scriptModelVO\\.remark").val(data.result.data.remark);
					$("#scriptModelVO\\.modelModelVO\\.id").val(data.result.data.modelModelVO.id);
					$("#scriptParamModelVO\\.scriptModelVO\\.id").val(data.result.data.id);
					$("#paramsTable").setGridWidth($("#gridTableDiv").width());
					reloadParamsTable(data.result.data.id);
				}
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight, htmlH = demoIframe.contents().find("html").get(0).scrollHeight, maxH = Math.max(bodyH, htmlH), minH = Math
				.min(bodyH, htmlH), h = demoIframe.height() >= maxH ? minH : maxH;
		if (h < 530)
			h = 530;
		demoIframe.height(h);
	}
	
	//保存自动化脚本相关  包、模块和脚本信息。
	function save(type) {
		if (!$("#forms" + type).valid()) {
			return false;
		} else {
			var params = "";
			params = $("#forms" + type).serialize();
			params += "&type=" + type;
			var nid = "";
			$.ajax({
				type : "post",
				url : ctx + "/cloud-service/script/save.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					nid = type + "_" + data.result.data.id;
					// 返回 节点信息 data.params.id data.params.name data.params.pid
					// data.params.type data.params.nid
					zTree = $.fn.zTree.getZTreeObj("tree");
					var node = zTree.getNodeByParam("nid", type + "_" + data.result.data.id, null);
					var pnode = null;
					if (type == '1') {
						closeView('edit1');
						if(node != null){		//修改
							node.name=data.result.data.name;
							zTree.updateNode(node);
						}else{					//添加
							node = {};
							node.id = data.result.data.id;
							node.nid = type + "_" + data.result.data.id;
							node.name = data.result.data.name;
							node.type = type;
							node.icon = "${ctx}/css/zTreeStyle/img/icons/scriptpackage.png";
							node.pid = '0';
							pnode = zTree.getNodeByParam("nid", node.pid, null);
							zTree.addNodes(pnode, node, false); 
							zTree.cancelSelectedNode();
							var nodec = zTree.getNodeByParam("nid", node. nid, null);
							zTree.selectNode(nodec,true ); 
							/* initTree();
							zTree.cancelSelectedNode();
							var nodec = zTree.getNodeByParam("nid", type + "_" + data.result.data.id, null);
							zTree.selectNode(nodec,true);
							zTree.updateNode(nodec); */
						}
					}
					if (type == '2') {
						closeView('edit2');
						if(node != null){		//修改
							node.name=data.result.data.name;
							zTree.updateNode(node);
						}else{
							node = {};
							node.id = data.result.data.id;
							node.nid = type + "_" + data.result.data.id;
							node.name = data.result.data.name;
							node.type = type;
							node.icon = "${ctx}/css/zTreeStyle/img/icons/script.png";
							node.pid = "1_" + data.result.data.packageModelVO.id;
							pnode = zTree.getNodeByParam("nid", node.pid, null);
							pnode.isParent = true;
							zTree.addNodes(pnode, node, false);
							zTree.cancelSelectedNode();
							var nodec = zTree.getNodeByParam("nid", node. nid, null);
							zTree.selectNode(nodec,true ); 
						}
					}
					if (type == '3') {
						closeView('edit3');
						if(node != null){		//修改
							node.name=data.result.data.name;
							zTree.updateNode(node);
						}else{
							node = {};
							scriptId = data.result.data.id;
							node.id = data.result.data.id;
							node.nid = type + "_" + data.result.data.id;
							node.name = data.result.data.name;
							node.type = type;
							node.icon = "${ctx}/css/zTreeStyle/img/icons/scriptScript.png";
							node.pid = "2_" + data.result.data.modelModelVO.id;
							pnode = zTree.getNodeByParam("nid", node.pid, null);
							pnode.isParent = true;
							zTree.addNodes(pnode, node, false);
							zTree.cancelSelectedNode();
							var nodec = zTree.getNodeByParam("nid", node. nid, null);
							zTree.selectNode(nodec,true ); 
						}
					}
					load(data.result.data.id, type);
					return true;
				},
				complete : function(XMLHttpRequest, textStatus) {
					
				},
				error : function() {
					alert("添加失败！");
					flage = false;
				}
			});
		}
	}
	function deletes(id, type, pid) {

		showTip(
				"<icms-i18n:label name="ensure_to_delete_line"/>", function() {
			var nid = id;
			if (type == 1)
				nid = $("#packageModelVO\\.id").val();
			if (type == 2)
				nid = $("#modelModelVO\\.id").val();
			if (type == 3)
				nid = $("#scriptModelVO\\.id").val();
			if (type == 4)
				nid = id;

			//clear(type);
			var params = "params['id']=" + nid + "&params['type']=" + type;
			if(modelValidate(params)){
					$.ajax({
					type : "post",
					async : false,
					url : ctx + "/cloud-service/script/delete.do",
					data : params,
					beforeSend : function(XMLHttpRequest) {
					},
					success : function(dataa, textStatus) {
						var zTree = $.fn.zTree.getZTreeObj("tree");
						var node = zTree.getNodeByParam("nid", type + "_" + nid, null);
						zTree.removeNode(node, false);
						refreshScript(pid);
						showTip(i18nShow('tip_delete_success'));
						if (type != 4) {
							$("#div0").show();
							$("#div1").hide();
							$("#div2").hide();
							$("#div3").hide();
						}
					},
					complete : function(XMLHttpRequest, textStatus) {
					},
					error : function() {
						showTip(i18nShow('tip_delete_fail'));
						flage = false;
					}
				});
			}else{
				showTip(i18nShow('tip_delete_catalog'));
			}
		});
	}
	function modelValidate(params){
		var result;
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/cloud-service/script/modelValidate.do",
			async : false,
			data : params,
			success : function(data){
				if(data.result=="false"){
					result = false;
				}else{
					result = true;
				}
			}, error : function() {
				showTip("发生错误",null,"red");
			}
		});
		return result;
	}
	function refreshScript(pid) {

		$.ajax({
			type : "post",
			url : ctx + "/cloud-service/script/load.do?params['id']=" + pid + "&params['type']=3",
			data : {},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				$("#scriptModelVO.id").val(data.result.data.id);
				$("#scriptModelVO.name").val(data.result.data.name);
				$("#scriptModelVO.fileName").val(data.result.data.fileName);
				$("#scriptModelVO.hadFz").val(data.result.data.hadFz);
				$("#scriptModelVO.checkCode").val(data.result.data.checkCode);
				$("#scriptModelVO.runUser").val(data.result.data.runUser);
				$("#scriptModelVO.remark").val(data.result.data.remark);
				$("#scriptModelVO.modelModelVO.id").val(data.result.data.modelModelVO.id);
				reloadParamsTable(data.result.data.id);
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
	function refreshSelect(sqlId, pid, selectId) {
		var params = "params['sqlId']=" + sqlId + "&params['pid']=" + pid;
		$.ajax({
			type : "post",
			url : ctx + "/cloud-service/script/loadDict.do",
			data : params,
			beforeSend : function(XMLHttpRequest) {

			},
			success : function(data, textStatus) {
				if (data.result.code == "success") {
					$("#" + selectId).empty();
					for (var i = 0; i < data.result.data.length; i++) {
						$("#" + selectId).append("<option value='" + data.result.data[i].id + "'>" + data.result.data[i].name + "</option>");
					}
				}
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
	/* 	function openAddParams(id) {
	 window.showModalDialog(ctx + "/cloud-service/script/loadParam.do?params['type']=4&params['pid']=" + scriptId + "&params['id']=" + id, '',
	 "dialogWidth=700px;dialogHeight=500px");
	 refreshScript(scriptId);
	 return false;
	 } */
	function init() {
		refreshSelect("package.selectAll", "", "modelModelVO\\.packageModelVO\\.id");
		refreshSelect("model.selectAll", scriptId, "scriptModelVO\\.modelModelVO\\.id");
	}
	function toAdd(type) {
		clear(type);
		if (type == '1') {
			$("label.error").remove();
			openDialog("edit1", "<icms-i18n:label name="pack_create"/>", 650, 220);
		}
		if (type == '2') {
			$("label.error").remove();
			openDialog("edit2", "<icms-i18n:label name="module_create"/>", 650, 220);
			$('#modelModelVOname').val('');
			$('#modelModelVO.remark').val('');
		}
		if (type == '3') {
			edit3();
			$("#scriptParamAddButton").hide();
		}
	}
	function closeView(divId) {
		$("#" + divId).dialog("close");
	}
	function clear(type) {
		if (type == '1') {
			$("#packageModelVO\\.id").val('');
			$("#packageModelVO\\.name").val('');
			$("#packageModelVO\\.remark").val('');
			$("#packageModelVO\\.filePath").val('');
			$("#packageModelVO\\.fzr").val('');
		}
		if (type == '2') {
			$("#modelModelVO\\.id").val('');
			$("#modelModelVO\\.name").val('');
			$("#modelModelVO\\.remark").val('');
			$("#modelModelVO\\.filePath").val('');
		}
		if (type == '3') {
			$("#scriptModelVO\\.id").val('');
			$("#scriptModelVO\\.name").val('');
			$("#scriptModelVO\\.fileName").val('');
			$("#scriptModelVO\\.hadFz").val('');
			$("#scriptModelVO\\.checkCode").val('');
			$("#scriptModelVO\\.runUser").val('');
			$("#scriptModelVO\\.remark").val('');
		}
	}

	//tree查询

	function focusKey(e) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	function blurKey(e) {
		if (key.get(0).value === "") {
			key.addClass("empty");
		}
	}
	var lastValue = "", nodeList = [], fontCss = {};
	function clickRadio(e) {
		lastValue = "";
		searchNode(e);
	}
	function searchNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		if (!$("#getNodesByFilter").attr("checked")) {
			var value = $.trim(key.get(0).value);
			var keyType = "";
			keyType = "name";
			if (key.hasClass("empty")) {
				value = "";
			}
			if (lastValue === value)
				return;
			lastValue = value;
			if (value === "") {
				zTree.showNodes(zTree.transformToArray(zTree.getNodes()));
				return;
			}

			nodeList = zTree.getNodesByParamFuzzy(keyType, value);
		} else {
			updateNodes(false);
			nodeList = zTree.getNodesByFilter(filter);
		}
		/**不查询父级
		for(var x = 0 ; x<nodeList.length ; x++){
		    if(nodeList[x].isParent){
		        nodeList.splice(x--,1);
		    }
		}
		 */
		nodeList = zTree.transformToArray(nodeList);
		updateNodes(true);

	}
	function updateNodes(highlight) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var allNode = zTree.transformToArray(zTree.getNodes());
		zTree.hideNodes(allNode);
		for ( var n in nodeList) {
			findParent(zTree, nodeList[n]);
		}

		zTree.showNodes(nodeList);
	}

	function findParent(zTree, node) {
		zTree.expandNode(node, true, false, false);
		var pNode = node.getParentNode();
		if (pNode != null) {
			nodeList.push(pNode);
			findParent(zTree, pNode);
		}

	}

	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {
			color : "#A60000",
			"font-weight" : "bold"
		} : {
			color : "#333",
			"font-weight" : "normal"
		};
	}
	function filter(node) {
		return !node.isParent && node.isFirstNode;
	}

	var key;
	$(document).ready(function() {
		$.fn.zTree.init($("#tree"), setting, zNodes);
		key = $("#key");
		key.bind("focus", focusKey).bind("blur", blurKey).bind("propertychange", searchNode).bind("input", searchNode);
	});
</script>
</head>
<body onload="init();initParamsTable();" class="main1">

	

		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_media"></icms-i18n:label></div>
			</h1>
		</div>

		<div id="treeDiv"
			class="tree-div tree-div1" style="top:44px; min-height:499px;height:499px;overflow-x:auto;">
			<div  class="pageFormContent tree-search">
				<p style="margin: 0px;">
					<input name="key" type="text" id="key" size="30" class="textInput"
						style="width:80%;border-radius:20px;">
						<!-- <span class="input-group-btn"><a href="#" class="btn-default"><i class="fa fa-search" style="line-height:15px;width:auto;" onclick="searchTreeByCname()"></i></a></span> -->
				</p>
			</div>
			<ul id="tree" class="ztree clear" style="padding:0 0 0 5px"></ul>
		</div>

		<div id="rightContentDiv" class="right-div" style="top:44px;left: 26%;width: 72%;">
			<div id="div0" style="display: block; padding-top:20px;">
				<table border=0 width="50%">
					<tr>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:90%;margin-top:0; padding-top:0;">
								<tr>
									<th><img src="${ctx}/css/zTreeStyle/img/icons/scriptpackage.png" /></th>
									<td><label style="font-size: 14px; padding-left:0; text-align:center"><icms-i18n:label name="bim_l_scriptPack"></icms-i18n:label></label></td>
								</tr>
								<tr>
									<th><img src="${ctx}/css/zTreeStyle/img/icons/script.png" /></th>
									<td><label style="font-size: 14px; padding-left:0;text-align:center"><icms-i18n:label name="bim_l_scriptModule"></icms-i18n:label></label></td>
								</tr>
								<tr>
									<th><img src="${ctx}/css/zTreeStyle/img/icons/scriptScript.png" /></th>
									<td><label style="font-size: 14px; padding-left:0;text-align:center"><icms-i18n:label name="bim_l_scriptIcon"></icms-i18n:label></label></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<p class="btnbar1" style="padding-left:16px;">
							<shiro:hasPermission name="script:createPackage">
								<a href="javascript:void(0)" type="button" value='<icms-i18n:label name="bim_l_addPackageBtn"/>' onclick="toAdd(1)" class="btn" style="margin-left:5px; margin-right:5px;">
									<span>添加包</span>
								</a>
							</shiro:hasPermission>
							</p>
						</td>
					</tr>
				</table>
			</div>
			<jsp:include page="package.jsp"></jsp:include>
			<jsp:include page="model.jsp"></jsp:include>
			<jsp:include page="script.jsp"></jsp:include>
		</div>
</body>
</html>