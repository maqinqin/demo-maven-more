<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<html>
<head>
<title>流程模板列表</title>
<script type="text/javascript"
	src="${ctx}/scripts/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='${ctx}/scripts/json.js'></script>
<script type="text/javascript" src="${ctx}/common/javascript/main.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-jiajian.js"></script>
<script type="text/javascript" charset="UTF-8">
	var tempeTypes = new Array(); //模板类型集合;

	$(function() {
// 		formatType();
// 		buildTempLateType();
		showInstanceList();
		// 自适应宽度
		$("#modelTable").setGridWidth($("#gridMain").width());
		$(window).resize(function() {
			$("#modelTable").setGridWidth($("#gridMain").width());
		});
	});
	//初始化模板类型
	function buildTempLateType(){
		$.ajax({
		     type : "POST",
		     url : ctx+"/workflow/template/getGroupByUserId.action",
		     async:true,
		     cache:false,
		     success : function(data) {
		    	/**
		    	 * 应用选择下拉项初始化
		    	 */
		    	$('#tm_instanceType').find("option").remove();
		    	$("#tm_instanceType").append("<option value='' selected>请选择</option>");
		    	for(var a in data){
		    		$("#tm_instanceType").append("<option value='"+data[a].groupId+"' >"+data[a].groupName+"</option>");  
		    	}
		    	
		     },
		     error : function(e) {
		      	showTip("error");
		     }
		 });
		
	}
	/**
	 * 展示流程实例列表
	 * 
	 * @returns
	 */
	function showInstanceList() {
		$("#modelTable")
				.jqGrid(
						{
							url : ctx + "/workflow/model/model_selModelListByUserId.action",
							rownumbers : true, // 是否显示前面的行号
							datatype : "json", // 返回的数据类型
							mtype : "post", // 提交方式
							height : 280,
							autowidth : true, // 是否自动调整宽度
							colNames : [ '模板名称', '模板类型', '发布人','创建时间'],
							colModel : [
									{
										name : 'modelName',
										index : 'modelName',
										width : 75,
										align : "center",
										label : "模板名称",
										formatter : function(cellValue,
												options, rowObject) {
											return "<a href='#'  onclick=\"showInstance('"
													+ rowObject.modelId
													+ "')\">"
													+ cellValue
													+ "</a>"
										}
									},
									{
										name : 'typeId',
										index : 'typeId',
										width : 75,
										align : "center",
										label : "模板类型",
										formatter : function(cellValue,
												options, rowObject) {
											return formatModelType(cellValue);
										}
									},
									{
										name : 'createUser',
										index : 'createUser',
										width : 75,
										align : "center",
										label : "发布人"
									},
									{
										name : 'createDate',
										index : 'createDate',
										width : 90,
										align : "center",
										formatter : function(cellValue,
												options, rowObject) {
											return formatTime(cellValue);
										}
									} ],
							viewrecords : true,
							sortname : "createDate",
							sortorder : "desc",
							rowNum : 10,
							rowList : [ 5, 10, 15, 20, 30 ],
							jsonReader : {
								root : "dataList",
								records : "record",
								repeatitems : false
							},
							pager : "#gridPager",
							hidegrid : false
						});
	}

	//格式化时间;
	function formatTime(ns) {
		if (ns != null && ns != '') {
			var d = new Date(parseInt(ns.time + ""));
			var year = d.getFullYear();
			var month = d.getMonth() + 1;
			if (month <= 9) {
				month = "0" + month;
			}
			var date = d.getDate();
			if (date <= 9) {
				date = "0" + date;
			}
			var hour = d.getHours();
			if (hour <= 9) {
				hour = "0" + hour;
			}
			var minute = d.getMinutes();
			if (minute <= 9) {
				minute = "0" + minute;
			}
			return year + "-" + month + "-" + date + " " + hour + ":" + minute;
		} else {
			return "";
		}
	}

	//格式化类型
	function formatType() {

		$.ajax({
			type : "POST",
			url : ctx + "/workflow/model/model_selTempType.action",
			async:false,
			success : function(data) {
				tempeTypes = data;
			},
			error : function(e) {
				alert("error");
			}
		});
	}

	function formatModelType(typeId) {
		var typeName = "未知";
		for ( var i = 0; i < tempeTypes.length; i++) {
			if (tempeTypes[i].groupId == typeId) {
				typeName = tempeTypes[i].groupName;
			}
		}
		return typeName;
	}

	//格式化模板编号
	function showInstance(mid) {
		window.location.href = ctx
				+ "/workflow/model/model_processDefinition.action?state=definition&processDefinitionID="
				+ mid;
	}

	//实现搜索功能;
	function search() {
		$("#modelTable").jqGrid('setGridParam', {
			url : ctx + '/workflow/model/model_selModelListByUserId.action',//你的搜索程序地址
			postData : {
				'modelName' : $("#modelName").val(),
				'modelType' : $("#tm_instanceType").val()
			}, //发送搜索条件
			pager : "#gridPager"
		}).trigger("reloadGrid"); //重新载入
	}
	function pageFulsh() {
		$("#modelName").val("");
		$("#tm_instanceType").val("");
		search();
	}
</script>
<style type="text/css">
html,body {
	height: 100%;
}
</style>
<link rel="stylesheet" href="${ctx}/common/css/search_new.css"
	type="text/css"></link>

</head>
<body class="main1">
	<div id="list" class="content-main clear">
   <div class="panelTop">
			<h2>
				流程模板
			</h2>
		</div>
		<div class="pageFormContent"
			style="padding-bottom: 7px; overflow: hidden;">
			
			<table height="12%" width="97%" align="center" border="0"
				class="searchWrap">
				<tr>
					<td class="name">模板名称：</td>
					<td class="content"><input type="text" id="modelName"
						name="modelName" class="textInput1" /></td>
					<td class="name">模板类型：</td>
					<td><select id="tm_instanceType" name=tm_instanceType class="selInput" /></select></td>
<%-- 					<td><icms-ui:dic id="tm_instanceType" name="tm_instanceType" --%>
<%-- 							value="" --%>
<%-- 							sql="select TYPE_NAME name,TYPE_ID value from BPM_TEMPLATE_TYPE WHERE PARENT_ID != '0'" --%>
<%-- 							showType="select" attr="class='selInput2'" /></td> --%>
                   <td colspan="2"><input type="button" class="btn_search" title="查询"
						onclick="search()" style="vertical-align: middle" /> </td>
				</tr>
			</table>
		</div>
		<div class="panel clear" id="gridMain">
			<table id="modelTable"></table>
			<table id="gridPager"></table>
		</div>
	</div>
</body>
</html>