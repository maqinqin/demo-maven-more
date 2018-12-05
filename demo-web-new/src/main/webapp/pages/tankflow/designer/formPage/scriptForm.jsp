<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />

<style type="text/css">

</style>
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
			onMouseDown : onMouseDown,
			onClick: onClick,
			beforeClick: beforeClick
		}
	};
	
	var zNodes = [];

	
	var theNode;
	function onMouseDown(event, treeId, treeNode) {
		theNode = treeNode;
		load(treeNode.id, treeNode.type);
	}
	
	//树节点选择前事件;
	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) alert("只能选择脚本...");
		return check;
	}
	
	//树节点点击事件;
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#scriptCode");
		cityObj.attr("value", v);
		//编号赋值;
		var scriptId = $("#scriptId");
		scriptId.attr("value",treeNode.id);
		$("#menuContent11").dialog("close");
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

					$("#modelModelVO\\.id").val(data.result.data.id);
					$("#modelModelVO\\.name").val(data.result.data.name);
					$("#modelModelVO\\.remark").val(data.result.data.remark);
					$("#modelModelVO\\.filePath").val(data.result.data.filePath);
					$("#modelModelVO\\.packageModelVO\\.id").val(data.result.data.packageModelVO.id);
					$("#scriptModelVO\\.modelModelVO\\.id").val(data.result.data.id);
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

	

	//点击选择事件,弹出选择脚本框;
	function showMenu() {
		$("#menuContent11").dialog({
			autoOpen : true,
			modal : true,
			width : 500,
			height : 450,
			resizable : false
		});
		$("#menuContent11").dialog("option", "title", "选择脚本");
	}

	//初始化树
	$(document).ready(function() {
		var t = $("#treeDemo");
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
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	});
</script>
<script type="text/javascript" src="js/scriptForm.js"></script>
</head>
<body style="overflow-y: auto;">
	<!-- 标准定义 -->
	<input type="hidden" value="${ctx}" id="url" />
	<input type="hidden" value="<c:out value="${param.state}"></c:out>"
		id="state" />
	<!-- 流程设计器相关参数定义 -->
	<input type="hidden" value="<c:out value="${param.id}"></c:out>"
		id="dNodeId" />
	<input type="hidden" value="<c:out value="${param.nodeId}"></c:out>"
		id="nodeId" />
	<input type="hidden"
		value="<c:out value="${param.processDefinitionID}"></c:out>"
		id="modelId" />
	<input type="hidden"
		value="<c:out value="${param.processInstanceId}"></c:out>"
		id="instanceId" name="instanceId"/>
	<input type="hidden" value="<c:out value="${param.userAdmin}"></c:out>"
		id="userAdmin" />

	
	<form id="serviceModuleForm">
		<table class="tf_form_table" width="100%" cellpadding="0" cellspacing="0" width="790px" style="border-collapse:collapse;table-layout:fixed;">
			<tr>
				<td class="tf_form_win_label" style="width: 90px;"><font class="font_r" color="red">*</font>任务名称</td>
				<td width="250px"><input type="text" id="tNodeName" name="tNodeName"
					class="tf_form_input" /> <input type="hidden" id="tNodeId"
					name="tNodeId" class="tf_form_input" /></td>
				<td class="tf_form_win_label" style="width: 100px;"><font class="font_r" color="red">*</font>超时时间</td>
				<td style="width: 266px"><input type="text" id="outtime" name="outtime"
					class="tf_form_input" style="background-color: white; width: 30px;"/>(s)&nbsp;&nbsp;&nbsp;是否告警：<input type="checkbox" id="outtimeAlarm" name="outtimeAlarm" checked="checked"/>
				</td>
			</tr>
			<tr>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>异常处理</td>
				<td>
					<div class="select_border">
						<div class="select_container">
							<!-- <icms-ui:dic id="exceptionCode" name="exceptionCode"
								kind="BPM_EXCEPTION_CODE" value="" showType="select"
								attr="style='width: 132px;'" />
								 -->
							<select id="exceptionCode" name="exceptionCode" class="tf_form_select">
							   <option id="no" name="no" value="no" selected="selected">请选择</option>
							   <option id="IGNORE" name="IGNORE" value="IGNORE">忽略异常</option>
							   <option id="WAIT" name="WAIT" value="WAIT">挂起待处理</option>
							   <option id="FORCE" name="FORCE" value="FORCE">强制结束</option>			
							   <option id="REPEAT" name="REPEAT" value="REPEAT">重复执行</option>
							</select>
								&nbsp;&nbsp;&nbsp;是否告警：<input type="checkbox" id="exceptionAlarm" name="exceptionAlarm" checked="checked"/>
							<div id='exceptionRepeatDiv' style='display:none'>
								&nbsp;&nbsp;&nbsp;再重复执行：<input type='text' id='exceptionRepeat' name='exceptionRepeat' class="tf_form_input" maxlength='2' style="background-color: white;width:30px;text-align:center"/>次
							</div> 
						</div>
					</div>
				</td>
				<td class="tf_form_win_label">自动执行</td>
				<td><input type="checkbox" name="isAutoNode" id="isAutoNode"
					checked="checked"></td>
			</tr>

			<tr>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>服务策略</td>
				<td style="text-align: left;"><select id="servicePolicyCode" class="tf_form_select"
					name="servicePolicyCode" class="form_select"
					onchange="buildModulePolicyParamTree('')"></select> <input
					type="hidden" id="servicePolicyId" name="servicePolicyId" /></td>

				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>脚本选择</td>
				<td style="text-align: left; padding-top: 10px;">
					<input type="text" id="scriptCode" name="scriptCode" class="tf_form_input"
						style="width: 200px;" />
					<input type="hidden" id="scriptId" name="scriptId" />
						<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
				</td>
			</tr>
		</table>

		<table id="servicePolicyParamTable" width="100%" border="0"	cellpadding="0" cellspacing="0"
			style="border: 1px solid #A6CACA; border-top: none;">
			<tbody>
			</tbody>
		</table>
	</form>

	<!-- 选择脚本树弹出框-苗有虎 start -->
	<div id="menuContent11" class="form_main_div" style="display: none;">
		<ul id="treeDemo" class="ztree" style="width: 160px;"></ul>
	</div>
	<!-- 选择脚本树弹出框 end -->

	<div align="center" style="padding-top: 10px;">
		<a href="javascript:void(0)" id="saveTemplateBtn" class="btn" title="保存" onclick="saveForm();return false;" style="float:none;">
			<span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>	
		<a href="javascript:void(0)" id="cancelTemplateBtn" class="btn btn-green" title="取消" onclick="cancel();return false;" style="float:none;">
			<span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
	</div>

</body>
</html>