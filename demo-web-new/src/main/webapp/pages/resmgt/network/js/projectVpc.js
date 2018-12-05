$(function() {
	$("#projectVpcTable")
			.jqGrid(
					{
						url : ctx + "/network/getProjectVpcList.action",
						rownumbers : true,
						datatype : "json",
						mtype : "post",
						height : heightTotal() + 65,
						autowidth : true,
						// multiselect:true,
						// multiselect:true,
						colNames : [ 'vpcId', 
						        i18nShow('vpc_name'),
								i18nShow('rm_datacenter'),
								'平台类型',
								i18nShow('rm_openstack_server_name'),
								i18nShow('com_status'), 
								i18nShow('com_remark'),
								i18nShow('com_operate') ],
						colModel : [
								{
									name : "vpcId",
									index : "vpcId",
									width : 120,
									sortable : false,
									align : 'left',
									hidden : true
								},
								{
									name : "vpcName",
									index : "vpcName",
									width : 120,
									sortable : true,
									align : 'left'
								},
								
								{
									name : "datacenterName",
									index : "datacenterName",
									sortable : true,
									align : 'left',
									editor : "text"
								},
								{
									name : "platformName",
									index : "platformName",
									width : 120,
									sortable : true,
									align : 'left'
								},
								
								{
									name : "serverName",
									index : "serverName",
									sortable : true,
									align : 'left',
									editor : "text"
								},
								{
									name : "isActive",
									index : "isActive",
									sortable : true,
									width : 120,
									align : 'left',
									formatter : function(cellVall, options,
											rowObject) {
										if (cellVall == 'Y') {// <span
																// class="tip_green">已激活</span>
											return "<span class='tip_green'>"
													+ i18nShow('com_status_Y')
													+ "</span>";
										} else {
											return "<span class='tip_red'>"
													+ i18nShow('com_status_N')
													+ "</span>";
										}
										return cellVall;
									}
								},
								{
									name : "remark",
									index : "remark",
									width : 120,
									sortable : true,
									align : 'left'
								},
								{
									name : "option",
									index : "option",
									width : 50,
									sortable : false,
									align : "left",
									formatter : function(cellVall, options,
											rowObject) {
										var updateflag = $("#updateFlag").val();
										var deleteFlag = $("#deleteFlag").val();
										var s = "";
										var ret = "";
										if (updateflag == "1") {
											ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"
													+ rowObject.vpcId
													+ "') >"
													+ i18nShow('com_update')
													+ "</a>";
										}
										if (deleteFlag == "1") {
											ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"
													+ rowObject.vpcId
													+ "') >"
													+ i18nShow('com_delete')
													+ "</a>";
										}
										return ret;
									}
								} ],
						viewrecords : true,
						sortname : "vpcId",
						rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#projectVpcPager",
						hidegrid : false
					});

	// 自适应宽度
	$("#projectVpcTable").setGridWidth($("#projectVpcGridTable_div").width());
	$(window).resize(
			function() {
				$("#projectVpcTable").setGridWidth($("#projectVpcGridTable_div").width());
				$("#projectVpcTable").setGridHeight(heightTotal() + 65);
			});

	/** ************表单验证开始*************** */
	jQuery.validator.addMethod("checkVpcName", function() {
		var validateValue = true;
		var projectVpcMethod = $("#projectVpcMethod").val();
		var addIsActive = "Y";
		var projectVpcName1 = $("#projectVpcName1").val();
		var projectVpcId = $("#projectVpcId").val();
		var addDatacenterId = $("#addDatacenterId").val();
		var addVmMsId = $("#addVmMsId").val();
		var validateProjectVpcName = $("#validateProjectVpcName").val();
		var vpcId;
		if (projectVpcMethod != "update") {
			vpcId = "";
		}
		$
				.ajax({
					type : 'post',
					datatype : "json",
					data : {
						'projectVpcPo.vpcId' : projectVpcId,
						'projectVpcPo.vpcName' : projectVpcName1,
						'projectVpcPo.datacenterId' : addDatacenterId,
						'projectVpcPo.vmMsId' : addVmMsId,
						'projectVpcPo.idActive' : addIsActive
					},
					url : ctx + "/network/checkVpcName.action",
					async : false,
					success : (function(data) {
						// alert(data.result);
						if (projectVpcMethod == "update") {
							if (data == null
									|| data.vpcName == validateProjectVpcName) {
								validateValue = true;
							} else {
								validateValue = false;
							}

						} else {
							if (data == null) {
								validateValue = true;
							} else {
								validateValue = false;
							}
						}
					}),
				});
		return validateValue;
	}, "vpc名称已存在！");
	$("#updataProjectVpcForm").validate({
		rules : {
			projectVpcName1 : {
				required : true,
				checkVpcName : true
			},
			datacenterId : {
				required : true
			},
			vmMsId : {
				required : true
			}
		},
		messages : {
			projectVpcName1 : {
				required : i18nShow('validate_vpc_name_required'),
				checkVpcName : i18nShow('validate_vpc_name_remote')
			},
			datacenterId : {
				required : i18nShow('validate_vpc_data_center_required')
			},
			vmMsId : {
				required : i18nShow('validate_vpc_server_required')
			}
		},

		submitHandler : function() {
			updateOrSaveProjectVpc();
		}
	});

	initDatacenterSelect();
	initServerNameSelect();
});

// 初始化服务器名称下拉框
function initServerNameSelect() {
	$("#vmMsId").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/resmgt-compute/az/getAllServerName.action", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#vmMsId").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}

// 初始化数据中心名称下拉框
function initDatacenterSelect() {
	$("#datacenterId").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/network/converge/queryDatacenterList.action", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#datacenterId").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}

// 初始化新增 下拉框
function initAddDatacenterSelect() {
	$("#addDatacenterId").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/network/converge/queryDatacenterList.action", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#addDatacenterId").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}
function initAddServerNameSelect() {
	$("#addVmMsId").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/resmgt-compute/az/getAllServerName.action", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#addVmMsId").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}
function initAddTenantNameSelect() {
	$("#selTenant").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/rest/tenant/getTenants", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#selTenant").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}
// 查询
function search() {
	$("#projectVpcTable").jqGrid('setGridParam', {
		url : ctx + "/network/getProjectVpcList.action",// 你的搜索程序地址
		postData : {
			"vpcName" : $("#vpcName").val().replace(/(^\s*)|(\s*$)/g, ""),
			"datacenterId" : $("#datacenterId").val(),
			"vmMsId" : $("#vmMsId").val(),
			"platformId" : $("#platformId").val()
		}, // 发送搜索条件
		pager : "#projectVpcPager"
	}).trigger("reloadGrid"); // 重新载入
}

// 弹出添加页面
function createData() {
	$("label.error").remove();
	$("#updataProjectVpc").dialog({
		autoOpen : true,
		modal : true,
		height : 350,
		width : 370,
		position : "middle",
		title : i18nShow('vpc_save'),
	// resizable:false
	});
	$('#projectVpcName1').attr("readonly",false);
	$("#projectVpcName1").val("");
	$("#remark").val("");
	initAddDatacenterSelect();
	initAddServerNameSelect();
	initAddTenantNameSelect();
	$("#projectVpcMethod").val("save");
}
// 关闭窗口
function closePlatformrView() {
	$("#updataProjectVpc").dialog("close");
}
// 点击保存按钮提交
function saveOrUpdateProjectVpcBtn() {
	var projectVpcMethod = $("#projectVpcMethod").val();
	var projectVpcId = $("#projectVpcId").val();
	var projectVpcName1 = $("#projectVpcName1").val();
	var addremark = $("#remark").val();
	var addDatacenterId = $("#addDatacenterId").val();
	var addVmMsId = $("#addVmMsId").val();
	var addTenantId = $("#selTenant").val();
	var url;
	if (projectVpcMethod == "update") {
		url = ctx + "/network/updateProjectVpc.action"
	} else {
		url = ctx + "/network/saveProjectVpc.action"

	}
	var data = {
		'projectVpcPo.vpcName' : projectVpcName1,
		'projectVpcPo.vpcId' : projectVpcId,
		'projectVpcPo.remark' : addremark,
		'projectVpcPo.datacenterId' : addDatacenterId,
		'projectVpcPo.vmMsId' : addVmMsId,
		'projectVpcPo.tenantId' : addTenantId
	};
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		beforeSend : function() {
			showTip("load");
		},
		success : (function(data) {
			closeTip();
			showTip(i18nShow('tip_save_success'));
			$("#updataProjectVpc").dialog("close");
			$("#projectVpcTable").jqGrid().trigger("reloadGrid");
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			closeTip();
			showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
		}
	});
}
// 弹出修改vpc窗口
function showUpdate(id) {
	$("label.error").remove();
	$("#updataProjectVpc").dialog({
		autoOpen : true,
		modal : true,
		height : 320,
		width : 390,
		position : "middle",
		title : i18nShow('vpc_update'),
	// draggable: false,selectCmtDatastoreVoById
	// resizable:false
	})
	$.post(ctx + "/network/selectProjectVpc.action", {
		"projectVpcPo.vpcId" : id
	}, function(data) {
		initAddDatacenterSelect();
		initAddServerNameSelect();
		initAddTenantNameSelect();
		$("#projectVpcId").val(data.vpcId);
		$("#projectVpcName1").val(data.vpcName);
		$('#projectVpcName1').attr("readonly",true);
		$("#validateProjectVpcName").val(data.vpcName);
		$("#remark").val(data.remark);
		$("#addDatacenterId").val(data.datacenterId);
		$("#addVmMsId").val(data.vmMsId);
		$("#selTenant").val(data.tenantId);
		$("#projectVpcMethod").val("update");
	})
}
// 删除vpc
function deleteData(dataId) {
	if (dataId) {

		showTip(i18nShow('tip_delete_confirm'), function() {

			if (projectVpcUseValidate(dataId)) {
				$.post(ctx + "/network/deleteProjectVpc.action", {
					"projectVpcPo.vpcId" : dataId
				}, function(data) {
					search();
					showTip(i18nShow('tip_delete_success'));
				});
			} else {
				return false;
			}

		});
	} else {
		var ids = jQuery("#projectVpcTable")
				.jqGrid('getGridParam', 'selarrrow');

		if (ids.length == 0) {
			showError(i18nShow('error_select_one_data'));
			return;
		}
		var list = [];
		$(ids).each(function(index, id2) {
			var rowData = $("#projectVpcTable").getRowData(id2);
			list[list.length] = rowData.vpcId;
		})
		showTip(i18nShow('tip_delete_confirm'), function() {
			if (projectVpcUseValidate(list.join(","))) {
				$.post(ctx + "/network/deleteProjectVpc.action", {
					"projectVpcPo.vpcId" : list.join(",")
				}, function(data) {
					search();
					showTip(i18nShow('tip_delete_success'));
				});
			} else {
				// showTip("无法删除!");
				return false;
			}
		});
	}
}
function projectVpcUseValidate(id) {
	var flag = false;
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/deleteProjectVpcCheck.action",
		async : false,
		data : {// 获得表单数据
			"projectVpcPo.vpcId" : id
		},
		success : function(data) {
			var cs = ""
			var reason = "";
			var result = "";
			for ( var key in data) {
				if (data[key] == "") {
					continue;
				}
				result += i18nShow('tip_delete_fail') + "（";
				var temps = data[key];
				var temp = temps.split(",");
				var rs = new Array(2);
				reason = "";
				for (var i = 0; i < temp.length; i++) {
					if (temp[i] == "vr") {
						reason += "、" + i18nShow('error_router_use');
					}
					if (temp[i] == "pv") {
						reason += "、" + i18nShow('error_private_net_use');
					}
					cs += reason;

				}
				result += reason.substring(1) + "）\n";
			}
			showTip(result);
			if (cs.length > 0) {
				flag = false;
			} else {
				flag = true;
			}
		},
		error : function() {
			showTip(i18nShow('tip_error'), null, "red");
		}

	});
	return flag;
}