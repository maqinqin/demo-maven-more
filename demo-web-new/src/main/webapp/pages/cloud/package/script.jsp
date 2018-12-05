<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>

<html>
<head>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
<!--
	function reloadParamsTable(scriptId) {
		var queryData = {
			"params['scriptId']" : scriptId
		};
		/* $("#paramsTable").jqGrid("setGridParam", {
			postData : queryData
		}).trigger("reloadGrid"); */
		jqGridReload("paramsTable", queryData);
	}
	function initParamsTable() {
		var queryData = {};
		$("#paramsTable").jqGrid({
			url : ctx + "/cloud-service/script/loadScriptParams.action",
			datatype : "json",
			postData : queryData,
			mtype : "post",
			autowidth : true, 
			height :240,
			// multiselect : true,
			colNames : [ '<icms-i18n:label name="Sname"/>', '<icms-i18n:label name="Scode"/>', '<icms-i18n:label name="Sspace"/>', '<icms-i18n:label name="Stype"/>', '<icms-i18n:label name="Svtype"/>', '<icms-i18n:label name="Seq"/>', '<icms-i18n:label name="Sop"/>' ],
			colModel : [ {
				name : "name",
				index : "name",
				sortable : false,
				align : 'center'
			}, {
				name : "code",
				index : "code",
				sortable : false,
				align : 'center'
			}, {
				name : "splitChar",
				index : "splitChar",
				sortable : false,
				align : 'center',
				hidden : false
			}, {
				name : "paramType",
				index : "paramType",
				sortable : false,
				align : 'center'
			}, {
				name : "paramValType",
				index : "paramValType",
				sortable : false,
				align : 'center',
				hidden : false
			}, {
				name : "orders",
				index : "orders",
				sortable : false,
				align : 'center',
				hidden : false
			}, {
				name : "option",
				index : "option",
				sortable : false,
				align : "center",
				title : false,
				formatter : function(cellVall, options, rowObject) {
					/* var ret = "<a href='javascript:void(0);' onclick='deletes(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)'>删除</a>";
					 ret += "<a href='javascript:void(0);' onclick='openAddParams(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)'>修改</a>"; 
					return ret; */
					/* ret += "<input class='btn_edit_s' style=' margin-right: 10px;' type='button' onclick='openAddParams(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)' title='修改'/>";	
					ret += "<input class='btn_del_s' style=' margin-right: 10px;' type='button' onclick='deletes(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)' title='删除'/>"; */
					var updateFlag = $('#updateFlag').val();
					var deleteFlag = $('#deleteFlag').val();
					
					var ret = "　　";
					if(updateFlag){
						ret += "<a href='javascript:void(0);' style=' text-decoration:none' onclick='openAddParams(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)'><icms-i18n:label name="script_para_modify"/></a>&nbsp;&nbsp;&nbsp;"; 
					}
					if(deleteFlag){
						ret += "<a href='javascript:void(0);' style=' text-decoration:none' onclick='deletes(&apos;" + rowObject.id + "&apos;,4,&apos;" + rowObject.scriptModelVO.id + "&apos;)'><icms-i18n:label name="script_para_delete"/></a>";
					}
					ret += "　";
					return ret;
				}
			} ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "result.data",
				repeatitems : false
			},
			hidegrid : false
		});
		
		$(window).resize(function() {
			$("#paramsTable").setGridWidth($("#gridTableDiv").width());
	    });

		$("#paramsTable").closest(".ui-jqgrid-bdiv").css({
			'overflow-y' : 'scroll'
		});
	}
	function edit3(flag) {
		if(flag){
			openDialog("edit3", "<icms-i18n:label name='sc_update' />", 650, 250);
		}else{
			openDialog("edit3", "<icms-i18n:label name='sc_create' />", 650, 250);
		}
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		for (var i = 0; i < nodes.length; i++) {
			load(nodes[i].id, nodes[i].type);
		}
	}
//-->
	$(document).ready(function() {
		$("#forms3").validate({
			rules : {
				"scriptModelVO.name" : {
					required : true,
					remote : {
						type : "POST",
						url : ctx + "/cloud-service/script/checkScriptName.do",
						data : {
							"scriptName" : function() {
								return $("input[name='scriptModelVO.name']").val();
							},
							"modelId" : function(){
								var treeObj = $.fn.zTree.getZTreeObj("tree");
								var nodes = treeObj.getSelectedNodes();
								return nodes[0].id;
							}
						}
					}
				}
			},
			messages : {
				"scriptModelVO.name" : {
					required : "<icms-i18n:label name="sc_name_not_null"/> ",
					remote : "<icms-i18n:label name="sc_name_exit"/>",
				}
			}
		});
	});
</script>
<style>
#edit3 i {text-align:left; padding-right:3px;}
#edit3 p{width:600px; margin-bottom:5px; margin-left:18px;  }
#edit3 p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
 label{display:block; padding-left:110px;}
#scriptModelVO_id label{padding-left:0;}
</style>
</head>
<body>
<div id="div3" style="display: none; padding-top:20px;">
	<input type=hidden name="scriptModelVO_id" id="scriptModelVO_id">
		<table border="0" class="report" width="97%">
					<tr>
						<th ><icms-i18n:label name="bim_l_scriptName"></icms-i18n:label>：</th>
						<td  width="30%"><label name="scriptModelVO_name" id="scriptModelVO_name" style="padding-left:0;"></label></td>
						<th><icms-i18n:label name="bim_l_fileName"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_fileName" id="scriptModelVO_fileName" style="padding-left:0;"></label></td>
					</tr>
					<tr>
						<th><icms-i18n:label name="bim_l_isThreValue"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_hadFz" id="scriptModelVO_hadFz" style="padding-left:0;"></label></td>
						<th><icms-i18n:label name="bim_l_testCode"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_checkCode" id="scriptModelVO_checkCode" style="padding-left:0;"></label></td>
					</tr>
					<tr>
						<th><icms-i18n:label name="bim_l_runUser"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_runUser" id="scriptModelVO_runUser" style="padding-left:0;"></label></td>
						<th><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_remark" id="scriptModelVO_remark" style="padding-left:0;"></label></td>
					</tr>
					<tr>
						<th><icms-i18n:label name="bim_l_blogsModule"></icms-i18n:label>：</th>
						<td><label name="scriptModelVO_modelModelVO_name" id="scriptModelVO_modelModelVO_name" style="padding-left:0;"> </label></td>
					</tr>					
		</table>
		<p class="btnbar1" style="margin-left: 12px;">
			<shiro:hasPermission name="script:createParam">
				<input type="button" value='<icms-i18n:label name="bim_l_addScriptParaBtn"/>' onclick="openAddParams()" class="btn btn_dd2" />
			</shiro:hasPermission>
			<shiro:hasPermission name="script:updateScript">
				<input type="button" value='<icms-i18n:label name="bim_l_editScriptBtn"/>' onclick="edit3('update')" class="btn btn_dd2" />
			</shiro:hasPermission>
			<shiro:hasPermission name="script:deleteScript">
				<input type="button" value='<icms-i18n:label name="bim_l_deleteScriptBtn"/>'	onclick="deletes('',3)" class="btn btn_dd2" />
			</shiro:hasPermission>
		</p>
		<div class="pageTableBg pageTableBg2" style="padding-top:7px; margin-top:20px;width:97%;">
			<div class="panel clear" id="gridTableDiv" style="margin-top: 10px;">
			<table id="paramsTable"></table>
		</div>
		</div>
</div>

<div id="edit3" style="display: none;" class="pageFormContent">
	<form action="" id="forms3">
		<input type=hidden name="scriptModelVO.id" id="scriptModelVO.id"> 
		<input type=hidden name="scriptModelVO.modelModelVO.id" id="scriptModelVO.modelModelVO.id">
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_scriptName"></icms-i18n:label>：</i>
				<input name="scriptModelVO.name" id="scriptModelVO.name" class="textInput" />
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_fileName"></icms-i18n:label>：</i>
				<input name="scriptModelVO.fileName" id="scriptModelVO.fileName" class="textInput" />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_isThreValue"></icms-i18n:label>：</i>
				<input name="scriptModelVO.hadFz" id="scriptModelVO.hadFz" class="textInput" />
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_testCode"></icms-i18n:label>：</i>
				<input name="scriptModelVO.checkCode" id="scriptModelVO.checkCode" class="textInput" />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_runUser"></icms-i18n:label>：</i>
				<input name="scriptModelVO.runUser" id="scriptModelVO.runUser" class="textInput" />
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>
				<input name="scriptModelVO.remark" id="scriptModelVO.remark" class="textInput" />
			</span>
			</p>
		<p class="btnbar btnbar1" style="width:570px; text-align:right;">
			<input type="button" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('edit3')" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
			<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="save(3);" class="btn btn_dd2 btn_dd3"  style="margin-left:0; margin-right:5px;"/> 
		</p>
	</form>
	<jsp:include page="scriptParam.jsp"></jsp:include>
</div>