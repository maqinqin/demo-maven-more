var scriptId = "";
var zTree;
var demoIframe;

var setting = {
	view : {
		dblClickExpand : false,
		showLine : true,
		selectedMulti : false
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "nid",
			pIdKey : "pid",
			rootPId : ""
		}
	},
	callback : {
		onMouseDown : onMouseDown,
		beforeClick : function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				return false;
			} else {
				demoIframe.attr("src", treeNode.file + ".html");
				return true;
			}
		}
	}
};

var zNodes = [];

$(document).ready(function() {
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
		},
		error : function() {
			alert("添加失败！");
			flage = false;
		}
	});
	$("#forms1").validate({
		rules : {
			"packageModelVO.name" : {
				required : true,
				remote : {
					type : "POST",
					url : ctx + "/cloud-service/script/checkScriptPackageName.do",
					data : {
						"packageName" : function() {
							if($("input[name='packageModelVO.operationType']").val() == $("input[name='packageModelVO.name']").val()){
								return "";
							}
							return $("input[name='packageModelVO.name']").val();
						}						
					}
				}
			}
		},
		messages : {
			"packageModelVO.name" : {
				required : "包名不能为空",
				remote : "包名已经存在"
			}
		}
	});
	$("#forms2").validate({
		rules : {
			"modelModelVO.name" : {
				required : true,
				remote : {
					type : "POST",
					url : ctx + "/cloud-service/script/checkScriptModelName.do",
					data : {
						"modelName" : function() {
							return $("input[name='modelModelVO.name']").val();
						},
						"packageId" : function(){
							var treeObj = $.fn.zTree.getZTreeObj("tree");
							var nodes = treeObj.getSelectedNodes();
							return nodes[0].id;
						}
					}
				}
			}
		},
		messages : {
			"modelModelVO.name" : {
				required : "模块名称不能为空 ",
				remote : "模块名称已经存在",
			}
		}
	});
	
});
var theNode;
function onMouseDown(event, treeId, treeNode) {
	theNode = treeNode;
	$.ajax({
		type : "post",
		url : ctx + "/cloud-service/script/load.do?params['id']="
				+ treeNode.id + "&params['type']=" + treeNode.type,
		data : {},
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus) {
			if (treeNode.type == "1") {

				$("#div1").show();
				$("#div2").hide();
				$("#div3").hide();
				// $("#packageModelVO_name").html(data.result.data.name);
				document.getElementById("packageModelVO_name").innerText = data.result.data.name;
				$("#packageModelVO_remark").html(
						data.result.data.remark);
				$("#packageModelVO_filePath").html(
						data.result.data.filePath);
				$("#packageModelVO_fzr").html(data.result.data.fzr);

				$("#packageModelVO\\.id").val(data.result.data.id);
				$("#packageModelVO\\.name").val(data.result.data.name);
				$("#packageModelVO\\.remark").val(
						data.result.data.remark);
				$("#packageModelVO\\.filePath").val(
						data.result.data.filePath);
				$("#packageModelVO\\.fzr").val(data.result.data.fzr);
			} else if (treeNode.type == "2") {
				$("#div1").hide();
				$("#div2").show();
				$("#div3").hide();
				$("#modelModelVO\\.id").val(data.result.data.id);
				$("#modelModelVO\\.name").val(data.result.data.name);
				$("#modelModelVO\\.remark")
						.val(data.result.data.remark);
				$("#modelModelVO\\.filePath").val(
						data.result.data.filePath);
				$("#modelModelVO\\.packageModelVO\\.id").val(
						data.result.data.packageModelVO.id);
			} else if (treeNode.type == "3") {
				$("#div1").hide();
				$("#div2").hide();
				$("#div3").show();
				scriptId = data.result.data.id;
				$("#scriptModelVO\\.id").val(data.result.data.id);
				$("#scriptModelVO\\.name").val(data.result.data.name);
				$("#scriptModelVO\\.fileName").val(
						data.result.data.fileName);
				$("#scriptModelVO\\.hadFz").val(data.result.data.hadFz);
				$("#scriptModelVO\\.checkCode").val(
						data.result.data.checkCode);
				$("#scriptModelVO\\.runUser").val(
						data.result.data.runUser);
				$("#scriptModelVO\\.remark").val(
						data.result.data.remark);
				$("#scriptModelVO\\.modelModelVO\\.id").val(
						data.result.data.modelModelVO.id);
				$("#paramsTable").empty();
				$("#paramsTable")
						.append(
								"<tr><td colspan = 7 align = right ><button onclick='openAddParams(&apos;&apos;);return false;'>添加</button></td></tr>");
				$("#paramsTable")
						.append(
								"<tr><td align= center>参数名称</td><td align= center>参数编码</td><td align= center>间隔符</td><td align= center>参数类型</td><td align= center>参数值类型</td><td align= center>顺序</td><td align= center>操作</td></tr>");
				if (data.result.data.scriptParamModelVOs) {
					for (var i = 0; i < data.result.data.scriptParamModelVOs.length; i++) {
						$("#paramsTable")
								.append(
										"<tr id='"
												+ data.result.data.scriptParamModelVOs[i].id
												+ "'><td align= center><a href='javascript:void(0);' onclick='openAddParams(&apos;"
												+ data.result.data.scriptParamModelVOs[i].id
												+ "&apos;);return false;'>"
												+ data.result.data.scriptParamModelVOs[i].name
												+ "</a></td><td align= center>"
												+ data.result.data.scriptParamModelVOs[i].code
												+ "</td><td align= center>"
												+ data.result.data.scriptParamModelVOs[i].splitChar
												+ "</td><td align= center>"
												+ data.result.data.scriptParamModelVOs[i].paramType
												+ "</td><td align= center>"
												+ data.result.data.scriptParamModelVOs[i].paramValType
												+ "</td><td align= center>"
												+ data.result.data.scriptParamModelVOs[i].orders
												+ "</td><td align= center><a href='javascript:void(0);' onclick='deletes(&apos;"
												+ data.result.data.scriptParamModelVOs[i].id
												+ "&apos;,4,&apos;"
												+ data.result.data.scriptParamModelVOs[i].scriptModelVO.id
												+ "&apos;)'>删除</a></td></tr>");
					}
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
function loadReady() {
	var bodyH = demoIframe.contents().find("body").get(0).scrollHeight, htmlH = demoIframe
			.contents().find("html").get(0).scrollHeight, maxH = Math.max(
			bodyH, htmlH), minH = Math.min(bodyH, htmlH), h = demoIframe
			.height() >= maxH ? minH : maxH;
	if (h < 530)
		h = 530;
	demoIframe.height(h);
}

/*//脚本包页面的验证
function checkScriptPackageName(type){
	$("#forms1").validate({
		rules : {
			"packageModelVO.name" : {
				required : true,
				remote : {
					type : "POST",
					url : ctx + "/cloud-service/script/checkScriptPackageName.do",
					data : {
						"packageName" : function() {
							alert($("input[name='packageModelVO.name']").val());
							return $("input[name='packageModelVO.name']").val();
						}						
					}
				}
			}
		},
		messages : {
			"packageModelVO.name" : {
				required : "包名不能为空",
				remote : "包名已经存在了"
			}
		},
		submitHandler : function() {
			save(type);
		}
	});
}*/


/*function save(type) {
	alert(type);
	var params = ""; 
	params = $("#forms" + type).serialize();
	params += "&type=" + type;
	$.ajax({
		type : "post",
		url : ctx + "/cloud-service/script/save.do",
		data : params,
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus) {
			// 返回 节点信息 data.params.id data.params.name data.params.pid
			// data.params.type data.params.nid
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var sNodes = zTree.getSelectedNodes();
			//var node = zTree.getNodeByParam("nid", type + "_" + data.result.data.id, null);
			if (node == null) {
				var pnode = null;
				//node = {};
				if (type == '1') {
					alert("TYPE = 1");
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
//					node.id = data.result.data.id;
//					node.nid = type + "_" + data.result.data.id;
//					node.name = data.result.data.name;
//					node.type = type;
//					node.pid = '0';
					//zTree.addNodes(null, node, true);
					
				}
				if (type == '2') {
					alert();
					node.id = data.result.data.id;
					node.nid = type + "_" + data.result.data.id;
					node.name = data.result.data.name;
					node.type = type;
					node.pid = "1_" + data.result.data.packageModelVO.id;
					pnode = zTree.getNodeByParam("nid", node.pid, null);
					zTree.addNodes(pnode, node, true);
				}

				if (type == '3') {
					scriptId = data.result.data.id;
					node.id = data.result.data.id;
					node.nid = type + "_" + data.result.data.id;
					node.name = data.result.data.name;
					node.type = type;
					node.pid = "2_" + data.result.data.modelModelVO.id;
					pnode = zTree.getNodeByParam("nid", node.pid, null);
					zTree.addNodes(pnode, node, true);
				}

			} else {
				node.name = data.result.data.name;
			}
			zTree.updateNode(node, true);

			return true;
		},
		complete : function(XMLHttpRequest, textStatus) {
		},
		error : function() {
			alert("添加失败！");
			flage = false;
		}
	});
}*/
function deletes(id, type, pid) {
	var nid = id;
	if (type == 1)
		nid = $("#packageModelVO\\.id").val();
	if (type == 2)
		nid = $("#modelModelVO\\.id").val();
	if (type == 3)
		nid = $("#scriptModelVO\\.id").val();
	if (type == 4)
		nid = id;
	var params = "params['id']=" + nid + "&params['type']=" + type;
	$.ajax({
		type : "post",
		url : ctx + "/cloud-service/script/delete.do",
		data : params,
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(dataa, textStatus) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var node = zTree.getNodeByParam("nid", type + "_" + nid, null);
			zTree.removeNode(node, false);
			refreshScript(pid);
		},
		complete : function(XMLHttpRequest, textStatus) {
		},
		error : function() {
			alert("添加失败！");
			flage = false;
		}
	});
}
function refreshScript(pid) {

	$
			.ajax({
				type : "post",
				url : ctx + "/cloud-service/script/load.do?params['id']=" + pid
						+ "&params['type']=3",
				data : {},
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					$("#scriptModelVO\\.id").val(data.result.data.id);
					$("#scriptModelVO\\.name").val(data.result.data.name);
					$("#scriptModelVO\\.fileName").val(
							data.result.data.fileName);
					$("#scriptModelVO\\.hadFz").val(data.result.data.hadFz);
					$("#scriptModelVO\\.checkCode").val(
							data.result.data.checkCode);
					$("#scriptModelVO\\.runUser").val(data.result.data.runUser);
					$("#scriptModelVO\\.remark").val(data.result.data.remark);
					$("#scriptModelVO\\.modelModelVO\\.id").val(
							data.result.data.modelModelVO.id);
					$("#paramsTable").empty();
					$("#paramsTable")
							.append(
									"<tr><td colspan = 7 align = right ><button onclick='openAddParams(&apos;&apos;);return false;'>添加</button></td></tr>");
					$("#paramsTable")
							.append(
									"<tr><td align= center>参数名称</td><td align= center>参数编码</td><td align= center>间隔符</td><td align= center>参数类型</td><td align= center>参数值类型</td><td align= center>顺序</td><td align= center>操作</td></tr>");
					if (data.result.data.scriptParamModelVOs) {
						for (var i = 0; i < data.result.data.scriptParamModelVOs.length; i++) {
							$("#paramsTable")
									.append(
											"<tr id='"
													+ data.result.data.scriptParamModelVOs[i].id
													+ "'><td align= center><a href='javascript:void(0);' onclick='openAddParams(&apos;"
													+ data.result.data.scriptParamModelVOs[i].id
													+ "&apos;);return false;'>"
													+ data.result.data.scriptParamModelVOs[i].name
													+ "</a></td><td align= center>"
													+ data.result.data.scriptParamModelVOs[i].code
													+ "</td><td align= center>"
													+ data.result.data.scriptParamModelVOs[i].splitChar
													+ "</td><td align= center>"
													+ data.result.data.scriptParamModelVOs[i].paramType
													+ "</td><td align= center>"
													+ data.result.data.scriptParamModelVOs[i].paramValType
													+ "</td><td align= center>"
													+ data.result.data.scriptParamModelVOs[i].orders
													+ "</td><td align= center><a href='javascript:void(0);' onclick='deletes(&apos;"
													+ data.result.data.scriptParamModelVOs[i].id
													+ "&apos;,4,&apos;"
													+ data.result.data.scriptParamModelVOs[i].scriptModelVO.id
													+ "&apos;)'>删除</a></td></tr>");
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
					$("#" + selectId).append(
							"<option value='" + data.result.data[i].id + "'>"
									+ data.result.data[i].name + "</option>");
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
function openAddParams(id) {
	var r = window
			.showModalDialog(
					ctx
							+ "/cloud-service/script/loadParam.do?params['type']=4&params['pid']="
							+ scriptId + "&params['id']=" + id, '',
					"dialogWidth=700px;dialogHeight=500px");
	refreshScript(scriptId);
	return false;
}
function init() {
	refreshSelect("package.selectAll", "", "modelModelVO\\.packageModelVO\\.id");
	refreshSelect("model.selectAll", scriptId,
			"scriptModelVO\\.modelModelVO\\.id");
}
function toAdd(type) {
	$(':input', '#forms1').val('');
	$(':input', '#forms1').removeAttr('checked');
	$(':input', '#forms1').removeAttr('selected');
	$(':input', '#forms2').val('');
	$(':input', '#forms2').removeAttr('checked');
	$(':input', '#forms2').removeAttr('selected');
	$(':input', '#forms3').val('');
	$(':input', '#forms3').removeAttr('checked');
	$(':input', '#forms3').removeAttr('selected');
	$("#paramsTable").empty();
	$("#paramsTable")
			.append(
					"<tr><td colspan = 7 align = right ><button onclick='openAddParams(&apos;&apos;);return false;'>添加</button></td></tr>");
	$("#paramsTable")
			.append(
					"<tr><td align= center>参数名称</td><td align= center>参数编码</td><td align= center>间隔符</td><td align= center>参数类型</td><td align= center>参数值类型</td><td align= center>顺序</td><td align= center>操作</td></tr>");
	$("#div1").hide();
	$("#div2").hide();
	$("#div3").hide();
	$("#div" + type).show();
}