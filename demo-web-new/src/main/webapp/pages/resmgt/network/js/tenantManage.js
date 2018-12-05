$(function() {
	$("#tenantManageTable")
			.jqGrid(
					{
						url : ctx + "/rest/tenant/list",
						rownumbers : true,
						datatype : "json",
						mtype : "post",
						height : heightTotal() + 85,
						autowidth : true,
						// multiselect:true,
						colNames : [ 'id',
								i18nShow('tenantManage_jsp_tenantName'),
								i18nShow('tenantManage_jsp_createUser'),
								i18nShow('tenantManage_jsp_createTime'),
								i18nShow('tenantManage_jsp_remark'),
								i18nShow('com_operate') ],
						colModel : [
								{
									name : "id",
									index : "id",
									width : 120,
									sortable : false,
									align : 'left',
									hidden : true
								},
								{
									name : "name",
									index : "name",
									width : 100,
									sortable : true,
									align : 'left'
								},
								{
									name : "createUser",
									index : "createUser",
									sortable : true,
									width : 80,
									align : 'left'
								},
								{
									name : "createTime",
									index : "createTime",
									sortable : true,
									width : 100,
									align : 'left',
									formatter : function(cellVall, options,
											rowObject) {
										if (cellVall == null) {
											return "";
										} else {
											return moment(cellVall).format(
													'YYYY-MM-DD HH:mm:ss')
										}
									}
								},
								{
									name : "remark",
									index : "remark",
									width : 150,
									sortable : true,
									align : 'left'
								},
								{
									name : "option",
									index : "option",
									width : 120,
									sortable : false,
									align : "left",
									formatter : function(cellVall, options,
											rowObject) {
										var str = JSON.stringify(rowObject);
										var ret = "";
										var s = "";
										ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=updateTenantDialog('"
												+ str
												+ "') >"
												+ i18nShow('tenantManage_option_update')
												+ "</a>" + "";
										ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteTenantDialog('"
												+ rowObject.id
												+ "') >"
												+ i18nShow('tenantManage_option_delete')
												+ "</a>" + "";

										s += "<option value='1' >"
												+ i18nShow('tenantManage_option_auto')
												+ "</option >";
										s += "<option value='2' >"
												+ i18nShow('tenantManage_option_quota')
												+ "</option >";
										s += "<option value='3' >"
												+ i18nShow('resource_pool_authorization')
												+ "</option >";

										ret += "<select onchange=\"selMeched(this,'"
												+ rowObject.id
												+ "')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"
												+ i18nShow('com_select_defalt')
												+ "</option>'"
												+ s
												+ "'</select>";
										/*
										 * ret += "<a style='margin-right:
										 * 10px;text-decoration:none;'
										 * href='javascript:#' title=''
										 * onclick=userAuthoDialog('" +
										 * rowObject.id + "') >" +
										 * i18nShow('tenantManage_option_auto') + "</a>" + " | ";
										 * 
										 * ret += "<a style='margin-right:
										 * 10px;text-decoration:none;'
										 * href='javascript:#' title=''
										 * onclick=quotaManageDialog('" +
										 * rowObject.id + "') >" +
										 * i18nShow('tenantManage_option_quota') + "</a>";
										 */
										return ret;
									}
								} ],
						viewrecords : true,
						sortname : "createTime",
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
						pager : "#tenantManagePager",
						hidegrid : false,
						gridComplete : function() {
							loadUserAuthoInfo(null);
							resourcePoolInfo(null)
						}
					});

	// 自适应宽度
	$("#tenantManageTable").setGridWidth(
			$("#tenantManageGridTable_div").width());
	$(window).resize(
			function() {
				$("#tenantManageTable").setGridWidth(
						$("#tenantManageGridTable_div").width());
				$("#tenantManageTable").setGridHeight(heightTotal() + 85);
			});
});

function selMeched(element, id) {
	var val = element.value;
	if (val == "1") {
		userAuthoDialog(id);
	} else if (val == "2") {
		//配额管理
		//先查询此租户下是否有资源池
		queryResPoolByTenantId(id);
		//quotaManageDialog(id);
	} else if (val == "3") {
		resourcePoolDialog(id);
	}
}

// 查询
function search() {
	$("#tenantManageTable").jqGrid(
			'setGridParam',
			{
				url : ctx + "/rest/tenant/list",// 你的搜索程序地址
				postData : {
					"tenantName" : $("#tenantNameSearch").val().replace(
							/(^\s*)|(\s*$)/g, "")
				}, // 发送搜索条件
				pager : "#tenantManagePager"
			}).trigger("reloadGrid"); // 重新载入
}
// 添加租户界面
function addTenantDialog() {
	$("#tenantAddAndUpdateDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 250,
		width : 370,
		position : "middle",
		title : i18nShow('tenantManage_jsp_add')
	// resizable:false
	});
	$("#tenantId").val("");
	$("#tenantName").val("");
	$("#tenantRemark").val("");
	$("#tenantMethod").val("save");
}
// 修改租户界面
function updateTenantDialog(str) {
	$("#tenantAddAndUpdateDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 250,
		width : 370,
		position : "middle",
		title : i18nShow('tenantManage_jsp_update'),
	});
	var obj = JSON.parse(str);
	$("#tenantId").val(obj.id);
	$("#tenantName").val(obj.name);
	$("#tenantRemark").val(obj.remark);
	$("#tenantMethod").val("update");
}
// 修改或者添加 保存操作
function addOrUpdateTenantBtn() {
	var tenantId = $("#tenantId").val();
	var tenantName = $("#tenantName").val();
	var remark = $("#tenantRemark").val();
	var method = $("#tenantMethod").val();
	if (tenantName == null || tenantName == "") {
		showTip(i18nShow('tenantName_submit_not_null'));
	} else {
		if (method == "save") {
			url = ctx + "/rest/tenant/add";
		} else {
			url = ctx + "/rest/tenant/update";
		}
		var data = {
			'id' : tenantId,
			'name' : tenantName,
			'remark' : remark
		};
		$
				.ajax({
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
						$("#tenantAddAndUpdateDiv").dialog("close");
						$("#tenantManageTable").jqGrid().trigger("reloadGrid");
					}),
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						closeTip();
						showTip(i18nShow('tip_save_fail')
								+ XMLHttpRequest.responseText);
					}
				});
	}

}
// 关闭操作
function closePlatformrView(pageDiv) {
	$("#" + pageDiv).dialog("close");
	$("#tenantIdRes").val();
}
// 用户授权
function userAuthoDialog(tenantId) {
	$("#tenantManageTable").jqGrid().trigger("reloadGrid");
	$("#tenantAuthoTable").jqGrid('clearGridData');
	$("#tenantAuthoGridTable_div").dialog({
		autoOpen : true,
		modal : true,
		height : 450,
		width : 650,
		position : "middle",
		title : i18nShow('tenantManage_option_auto'),
	});
	$("#authoTenantId").val(tenantId);
	reloadUserAuthoInfo(tenantId);
}

// 资源池授权
function resourcePoolDialog(tenantId) {
	$("#tenantManageTable").jqGrid().trigger("reloadGrid");
	$("#resourcePoolTable").jqGrid('clearGridData');
	$("#resourcePoolGridTable_div").dialog({
		autoOpen : true,
		modal : true,
		height : 450,
		width : 650,
		position : "middle",
		title : i18nShow('resource_pool_authorization'),
	});

	 $("#tenantIdRes").val(tenantId);
	 reloadtenantResPoolsInfo(tenantId);
}
//获取租户和资源池的关系列表
function reloadtenantResPoolsInfo(tenantId){
	$("#resourcePoolTable").jqGrid('setGridParam', {
		url : ctx + "/rest/tenant/respools/list",// 你的搜索程序地址
		postData : {
			"tenantId" : tenantId
		}, 
		pager : "#resourcePoolPager"
	}).trigger("reloadGrid");
}


// reload 租户下面的用户和非用户
function reloadUserAuthoInfo(tenantId) {
	$("#tenantAuthoTable").jqGrid('setGridParam', {
		url : ctx + "/rest/tenant/user/list",// 你的搜索程序地址
		postData : {
			"tenantId" : tenantId,
			"_a" : new Date().getTime()
		}, // 发送搜索条件
		pager : "#tenantAuthoPager"
	}).trigger("reloadGrid");
}
// 加载租户下面的用户和非用户
function loadUserAuthoInfo(tenantId) {
	$("#tenantAuthoTable")
			.jqGrid(
					{
						url : ctx + "/rest/tenant/user/list",
						postData : {
							"tenantId" : tenantId
						},
						rownumbers : true,
						datatype : "json",
						mtype : "post",
						height : 260,
						width : 620,
						multiselect : true,
						colNames : [ 'tenantId', 'userId',
								i18nShow('autho_jsp_loginName'),
								i18nShow('autho_jsp_loginName'),
								i18nShow('autho_jsp_roleName') ],
						colModel : [
								{
									name : "tenantId",
									index : "tenantId",
									width : 100,
									key : false,
									sortable : true,
									align : 'left',
									hidden : true
								},
								{
									name : "userId",
									index : "userId",
									sortable : true,
									key : true,
									width : 300,
									align : 'left',
									hidden : true
								},
								{
									name : "lastName",
									index : "lastName",
									sortable : true,
									width : 150,
									align : 'left',
									formatter : function(cellValue, options,
											rowObject) {
										return rowObject.firstName + " "
												+ cellValue;
									}
								},
								{
									name : "loginName",
									index : "loginName",
									sortable : true,
									width : 150,
									align : 'left',
								},
								{
									name : "roleName",
									index : "roleName",
									sortable : true,
									width : 150,
									align : 'left',
									formatter : function(cellVall, options,
											rowObject) {
										var select = "<select id='user_"
												+ rowObject.userId
												+ "' style='border-radius: 3px;border: 1px solid #442f27;'>";
										if (cellVall == null) {
											select += "<option value='0' selected = 'selected'>"
													+ i18nShow('autho_jsp_roleName_manager')
													+ "</option>"
													+ "<option value='1'>"
													+ i18nShow('autho_jsp_roleName_customer')
													+ "</option>";
										} else if (cellVall == "0") {
											select += "<option value='0' selected = 'selected'>"
													+ i18nShow('autho_jsp_roleName_manager')
													+ "</option>"
													+ "<option value='1'>"
													+ i18nShow('autho_jsp_roleName_customer')
													+ "</option>";
										} else if (cellVall == "1") {
											select += "<option value='0' >"
													+ i18nShow('autho_jsp_roleName_manager')
													+ "</option>"
													+ "<option value='1' selected = 'selected'>"
													+ i18nShow('autho_jsp_roleName_customer')
													+ "</option>";
										}
										select += "</select>";
										return select;
									}
								} ],
						viewrecords : true,
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
						pager : "#tenantAuthoPager",
						hidegrid : false,
						gridComplete : function() {
							var ids = $('#tenantAuthoTable').jqGrid(
									'getDataIDs');
							if (ids != null) {
								var len = ids.length;
								for (var i = 0; i < len; i++) {
									var rowData = $("#tenantAuthoTable")
											.jqGrid('getRowData', ids[i]);
									if (rowData.tenantId != null
											&& rowData.tenantId != "") {
										$("#tenantAuthoTable").jqGrid(
												'setSelection', ids[i]);
									}
								}

							}
						}
					});
}
// 加载资源池授权
function resourcePoolInfo(tenantId) {
	$("#resourcePoolTable")
			.jqGrid(
					{
						url : ctx + "/rest/tenant/respools/list",
						postData : {
							"tenantId" : tenantId
						},
						rownumbers : true,
						datatype : "json",
						mtype : "post",
						height : 260,
						width : 620,
						multiselect : true,
						colNames : [ 'tenantId',
							i18nShow('autho_jsp_autho_res_name'),
								"操作",
								i18nShow('sys_user_author') ,],
						colModel : [
								{
									name : "resPoolId",
									index : "resPoolId",
									width : 100,
									key : true,
									sortable : true,
									align : 'left',
									hidden : true
								},
								{
									name : "resPoolName",
									index : "resPoolName",
									sortable : true,
									width : 150,
									align : 'left'
								},
								{
									name : "selectFlag",
									index : "selectFlag",
									sortable : true,
									width : 150,
									align : 'left',
									hidden : true
								},
								{
									name : "option",
									index : "option",
									sortable : true,
									width : 150,
									align : 'left',
									formatter : function(cellVall, options,rowObject) {
										var result = "<font style='color:red'>"+i18nShow('sys_user_author_N')+"</font>";
										if(rowObject.selectFlag == 1)
											  result = "<font style='color:green'>"+i18nShow('sys_user_author_Y')+"</font>";
										return result;
									}
								} ],
						viewrecords : true,
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
						pager : "#resourcePoolPager",
						hidegrid : false,
						gridComplete : function() {
							var ids = $("#resourcePoolTable").jqGrid('getDataIDs');
							if (ids != null) {
								var len = ids.length;
								for (var i = 0; i < len; i++) {
									var rowData = $("#resourcePoolTable").jqGrid('getRowData', ids[i]);
									//alert(rowData.selectFlag);
									if (rowData.selectFlag == 1) {
										$("#resourcePoolTable").jqGrid('setSelection', ids[i]);
									}
								}

							}
						}
					});
}


function tenantRsePoolSaveBtn() {
    showTip(i18nShow('autho_jsp_autho_res_tip'), function () {
        var ids = $("#resourcePoolTable").jqGrid("getGridParam", "selarrrow");
        var tenantId = $("#tenantIdRes").val();
        var dataList = [];
        var data;
        if (ids != null) {
            var len = ids.length;
            for (var i = 0; i < len; i++) {
                var rowData = $("#resourcePoolTable").jqGrid('getRowData',
                    ids[i]);
                var resPoolId = rowData.resPoolId;
                var roleName = $("#user_" + rowData.userId).val();
                var obj = {
                    "tenantId": tenantId,
                    "resPoolId": resPoolId
                }
                dataList.push(obj);
            }
            data = {
                "tenantId": tenantId,
                "dataList": JSON.stringify(dataList)
            }
        }
        url = ctx + "/rest/tenant/respools";
        $.ajax({
            type: 'post',
            datatype: "json",
            url: url,
            async: false,
            data: data,
            beforeSend: function () {
                showTip("load");
            },
            success: (function (data) {
                if (data.success == true) {
                    closeTip();
                    showTip(i18nShow('tip_save_success'));
                    $("#resourcePoolGridTable_div").dialog("close");
                }
                else {
                    closeTip();
                    showTip(i18nShow('tip_save_fail') + data.msg);
                }
            }),
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                closeTip();
                showTip(i18nShow('tip_save_fail')
                    + XMLHttpRequest.responseText);
            }
        });
    })
}

// 用户授权保存
function userAuthoSaveBtn() {
    showTip(i18nShow('autho_jsp_autho_tip'), function () {
        var ids = $("#tenantAuthoTable").jqGrid("getGridParam", "selarrrow");
        var tenantId = $("#authoTenantId").val();
        var dataList = [];
        var data;
        if (ids != null) {
            var len = ids.length;
            for (var i = 0; i < len; i++) {
                var rowData = $("#tenantAuthoTable").jqGrid('getRowData',
                    ids[i]);
                var userId = rowData.userId;
                var roleName = $("#user_" + rowData.userId).val();
                var obj = {
                    "tenantId": tenantId,
                    "userId": userId,
                    "roleName": roleName
                }
                dataList.push(obj);
            }
            data = {
                "tenantId": tenantId,
                "dataList": JSON.stringify(dataList)
            }
        }
        url = ctx + "/rest/tenant/user/add";
        $.ajax({
            type: 'post',
            datatype: "json",
            url: url,
            async: false,
            data: data,
            beforeSend: function () {
                showTip("load");
            },
            success: (function (data) {
                if (data.success) {
                    closeTip();
                    showTip(i18nShow('tip_save_success'));
                    $("#tenantAuthoGridTable_div").dialog("close");
                } else {
                    closeTip();
                    showTip(i18nShow('tip_save_fail') + data.msg);
                }
            }),
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                closeTip();
                showTip(i18nShow('tip_save_fail')
                    + XMLHttpRequest.responseText);
            }
        });
    })

}

//配额管理，查询租户下是否存在资源池
function queryResPoolByTenantId(tenantId){
	$("#tenantManageTable").jqGrid().trigger("reloadGrid");
	var url = ctx + "/rest/quota/quotaconfig/queryResPoolByTenantId";
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		data : {
			"tenantId" : tenantId
		},
		async : false,
		beforeSend : function() {
			showTip("load");
		},
		success : (function(data) {
			closeTip();
			if(data.obj=="success"){
				quotaManageDialog(tenantId);
			}else{
				showTip("请先授权资源池！");	
			}
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			closeTip();
			showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
		}
	});
	
}





// 配额管理
function quotaManageDialog(tenantId) {
	$("#tenantManageTable").jqGrid().trigger("reloadGrid");
	// 默认显示windows界面
	// showTabInfo("windows");
	$("#quotaGridTable_div").dialog({
		autoOpen : true,
		modal : true,
		width : 850,
		title : i18nShow('tenantManage_option_quota'),
		resizable : false,
		position : {
			using : function(pos) {
				$(this).css('top', 50);
				$(this).css('left', 200);
			}
		}
	});
	$("#quotaTenantId").val(tenantId);
	// 加载配额指标
	loadQuotaConfig();
	// 加载租户配额配置
	loadQuotaInfo(tenantId);
	showTabInfo('openstack')
}
// 加载配额指标
function loadQuotaConfig() {
	var tenantId = $("#quotaTenantId").val();
	var url = ctx + "/rest/quota/quotaconfig/list";
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		data : {
			"tenantId" : tenantId
		},
		async : false,
		beforeSend : function() {
			showTip("load");
		},
		success : (function(data) {
			closeTip();
			var objStr = JSON.stringify(data);
			initQuotaConfigInfo(objStr);
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			closeTip();
			showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
		}
	});
}
// 初始化配额指标
function initQuotaConfigInfo(objStr) {
	$("#windows").html("");
	$("#power").html("");
	$("#tabInfo > div > span").remove();
	$("#openstackLeft").html("");
	$("#openstackRight").html("");
	$("#powervcLeft").html("");
	$("#powervcRight").html("");
	var data = JSON.parse(objStr);
	if (data != null && data.obj != null) {
		var quotaConfigs = data.obj;
		for ( var key in quotaConfigs) {
			var quotaConfig = quotaConfigs[key];
			var quotaConfigStr = JSON.stringify(quotaConfig);
			if (key == "O") {
				var projectsStr = JSON.stringify(quotaConfigs["projectsO"]);
				createQuotaConfigTabO("openstack", projectsStr, quotaConfigStr);
			} else if (key == "P") {
				createQuotaConfigTabPX("power", quotaConfigStr);
			} else if (key == "X") {
				createQuotaConfigTabPX("windows", quotaConfigStr);
			} else if (key == "PV") {
				var projectsStr = JSON.stringify(quotaConfigs["projectsPV"]);
				createQuotaConfigTabPV("powervc", projectsStr, quotaConfigStr);
			}

		}
	}
}
// 动态生成配额指标("O")
function createQuotaConfigTabO(divId, projectsStr, quotaConfigStr) {
	var projects = JSON.parse(projectsStr);
	var data = JSON.parse(quotaConfigStr);
	var rootHtmlLeft = $("#" + divId + "Left");
	var rootHtmlRight = $("#" + divId + "Right");
	if (projects != null && projects.length > 0) {
		if (data != null && data.length > 0) {
			var len = projects.length;
			var len1 = data.length;
			var html = "";
			html += "<li id='projecLi' style='cursor: none;background: rgb(255, 255, 255); color: #1ab394; display:none; font-size: 15px;  padding-left: 10px; height: 20px;line-height: 20px;cursor: default; border:none; font-weight:bold; font-family:'微软雅黑'>"
					+ i18nShow('tenantManage_quota_projectList') + "</li>";
			for (var i = 0; i < len; i++) {
				html += "<li id='project_" + projects[i].projectId
						+ "'  onclick=\"showProjectTab('"
						+ projects[i].projectId + "')\">"
						+ projects[i].projectName + "</li>"
				var html1 = "<input id='DB_" + projects[i].projectId
						+ "' value=" + projects[i].datacenterId
						+ " type='hidden' /><li id='tabLi_"
						+ projects[i].projectId + "' style='display:none'>";
				for (var j = 0; j < len1; j++) {
					html1 += "<div class='quotaManage'>"
							+ "<span class='lable_sapn'>"
							+ data[j].name
							+ " :</span>"
							+ "<input class='quota_"
							+ divId
							+ "_"
							+ projects[i].projectId
							+ "'  name='"
							+ data[j].code
							+ "' id='quota_" + projects[i].projectId + "_" + data[j].id + "' onblur=\"verify('"
							+ projects[i].projectId
							+ "_"
							+ data[j].id
							+ "')\"/>"
							+ "<span>"
							+ data[j].unit
							+ "</span>"
							+ "<span class='hidden' id='span_"
							+ projects[i].projectId
							+ "_"
							+ data[j].id
							+ "'></span>"
						+ "<span id='quota_used_" + projects[i].projectId + "_" + data[j].id + "'>，已使用:0</span>"
                        + "<span>" + data[j].unit + "</span>"
						+ "</div>"
				}
				html1 += "</li>"
				rootHtmlRight.append(html1);
			}
			rootHtmlLeft.append(html);
		} else {
			// 配额指标没有配置
			var span = "<span style='color:#e60d0d'>"
					+ i18nShow('tenantManage_quota_show_createO') + "</span>"
			$("#openstack").append(span);
			return;
		}
	} else {
		var span = "<span style='color:#e60d0d'>"
				+ i18nShow('tenantManage_quota_show_createProject') + "</span>"
		$("#openstack").append(span);
		return;
	}

}

// 动态生成配额指标("PV")
function createQuotaConfigTabPV(divId, projectsStr, quotaConfigStr) {
	var projects = JSON.parse(projectsStr);
	var data = JSON.parse(quotaConfigStr);
	var rootHtmlLeft = $("#" + divId + "Left");
	var rootHtmlRight = $("#" + divId + "Right");
	if (projects != null && projects.length > 0) {
		if (data != null && data.length > 0) {
			var len = projects.length;
			var len1 = data.length;
			var html = "";
			html += "<li id='projecLiPV' style='cursor: none;background: rgb(255, 255, 255); color: #151010; font-size: 15px; padding-left: 10px; height: 40px;line-height: 40px;cursor: default;border-bottom: 1px solid black;'>"
					+ i18nShow('tenantManage_quota_projectList') + "</li>";
			for (var i = 0; i < len; i++) {
				html += "<li id='project_" + projects[i].projectId
						+ "'  onclick=\"showProjectPVTab('"
						+ projects[i].projectId + "')\">"
						+ projects[i].projectName + "</li>"
				var html1 = "<input id='DB_" + projects[i].projectId
						+ "' value=" + projects[i].datacenterId
						+ " type='hidden' /><li id='tabLi_"
						+ projects[i].projectId + "' style='display:none'>";
				for (var j = 0; j < len1; j++) {
					html1 += "<div class='quotaManage'>"
							+ "<span class='lable_sapn'>"
							+ data[j].name
							+ " :</span>"
							+ "<input class='quota_"
							+ divId
							+ "_"
							+ projects[i].projectId
							+ "'  name='"
							+ data[j].code
							+ "' id='quota_"
							+ projects[i].projectId
							+ "_"
							+ data[j].id
							+ "' onblur=\"verify('"
							+ projects[i].projectId
							+ "_"
							+ data[j].id
							+ "')\"/>"
							+ "<span>"
							+ data[j].unit
							+ "</span>"
							+ "<span class='hidden' id='span_"
							+ projects[i].projectId
							+ "_"
							+ data[j].id
							+ "'></span>"
                        + "<span id='quota_used_" + projects[i].projectId + "_" + data[j].id + "'>，已使用:0</span>"
                        + "<span>" + data[j].unit + "</span>"
						+ "</div>"
				}
				html1 += "</li>"
				rootHtmlRight.append(html1);
			}
			rootHtmlLeft.append(html);
		} else {
			// 配额指标没有配置
			var span = "<span style='color:#e60d0d'>"
					+ i18nShow('tenantManage_quota_show_createO') + "</span>"
			$("#powervc").append(span);
			return;
		}
	} else {
		var span = "<span style='color:#e60d0d'>"
				+ i18nShow('tenantManage_quota_show_createProject') + "</span>"
		$("#powervc").append(span);
		return;
	}

}
// 动态生成配额指标("P","X")
function createQuotaConfigTabPX(divId, quotaConfigStr) {
	var data = JSON.parse(quotaConfigStr);
	var html = "";
	var rootHtml = $("#" + divId);
	if (data != null) {
		var len = data.length;
		for (var i = 0; i < len; i++) {
			html += "<div class='quotaManage'>" + "<span class='lable_sapn'>"
					+ data[i].name + " :</span>" + "<input class='quota_"
					+ divId + "'  name='" + data[i].code + "' "
				+ " id='quota_" + data[i].id + "' onblur=\"verify('" + data[i].id
					+ "')\"/>" + "<span>" + data[i].unit + "</span>"
					+ "<span class='hidden' id='span_" + data[i].id
					+ "'></span>"
                + "<span id='quota_used_" + data[i].id + "'>，已使用:0</span>"
                + "<span>" + data[i].unit + "</span>"
				+ "</div>"
		}
		rootHtml.append(html);
	} else {
		if (divId == "X") {
			var span = "<span style='color:#e60d0d'>"
					+ i18nShow('tenantManage_quota_show_createX') + "</span>"
			$("#windows").append(span);
			return;
		} else if (divId == "P") {
			var span = "<span style='color:#e60d0d'>"
					+ i18nShow('tenantManage_quota_show_createP') + "</span>"
			$("#power").append(span);
			return;
		}
	}
}
// 加载quota信息
function loadQuotaInfo(tenantId) {
	var url = ctx + "/rest/quota/" + tenantId + "/quota/list";
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		beforeSend : function() {
			showTip("load");
		},
		success : (function(data) {
			closeTip();
			var objStr = JSON.stringify(data);
			initQuotaInfo(objStr);
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			closeTip();
			showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
		}
	});
}

// 初始化租户配额
function initQuotaInfo(objStr) {
    var data = JSON.parse(objStr);
    if (data != null && data.obj != null) {
        var quotas = data.obj;
        var len = quotas.length;
        for (var i = 0; i < len; i++) {
            var quota = quotas[i];
            if (quota.code == "mem") {
            	if(quota.value != null) {
                    quota.value = quota.value / 1024;
				}
                quota.usedValue = parseInt(quota.usedValue) / 1024;
            }
            if (quota.projectId) {
                $("#quota_" + quota.projectId + "_" + quota.quotaConfigId).val(quota.value);
                $("#quota_used_" + quota.projectId + "_" + quota.quotaConfigId).html("，已使用:" + quota.usedValue);
            } else {
                $("#quota_" + quota.quotaConfigId).val(quota.value);
                $("#quota_used_" + quota.quotaConfigId).html("，已使用:" + quota.usedValue);
            }

        }
    }
}
// 显示tab数据
function showTabInfo(divId) {
	$("#windows").css("display", "none");
	$("#power").css("display", "none");
	$("#openstack").css("display", "none");
	$("#powervc").css("display", "none");
	$("#" + divId).css("display", "");
	$("#tab_windows").attr("class", "default_color");
	$("#tab_power").attr("class", "default_color");
	$("#tab_openstack").attr("class", "default_color");
	$("#tab_powervc").attr("class", "default_color");
	$("#tab_" + divId).attr("class", "chosen_color");
	if (divId == "openstack") {
		var lis1 = $("#openstackLeft >li");
		if (lis1 != null && lis1.length > 1) {
			var len1 = lis1.length;
			for (var j = 1; j < len1; j++) {
				lis1[j].style.background = "";
				lis1[j].style.color = "#3e3e3e";// 没点击的样式
			}
			lis1[1].style.background = "#1ab394";
			lis1[1].style.color = "#fff"; // 点击的样式

		}
		var lis2 = $("#openstackRight >li");
		if (lis2 != null && lis2.length > 0) {
			var len2 = lis2.length;
			for (var k = 0; k < len2; k++) {
				lis2[k].style.display = "none";
			}
			lis2[0].style.display = "";
		}
	}
	
	if (divId == "windows") {
		var lis1 = $("#windowsLeft >li");
		if (lis1 != null && lis1.length > 1) {
			var len1 = lis1.length;
			for (var j = 1; j < len1; j++) {
				lis1[j].style.background = "";
				lis1[j].style.color = "#3e3e3e";// 没点击的样式
			}
			lis1[1].style.background = "#1ab394";
			lis1[1].style.color = "#fff"; // 点击的样式

		}
		var lis2 = $("#windowsRight >li");
		if (lis2 != null && lis2.length > 0) {
			var len2 = lis2.length;
			for (var k = 0; k < len2; k++) {
				lis2[k].style.display = "none";
			}
			lis2[0].style.display = "";
		}
	}
	
	
	

	if (divId == "powervc") {
		var lis1 = $("#powervcLeft >li");
		if (lis1 != null && lis1.length > 1) {
			var len1 = lis1.length;
			for (var j = 1; j < len1; j++) {
				lis1[j].style.background = "";
				lis1[j].style.color = "#3e3e3e";
			}
			lis1[1].style.background = "#1ab394";
			lis1[1].style.background = "#fff";
		}
		var lis2 = $("#powervcRight >li");
		if (lis2 != null && lis2.length > 0) {
			var len2 = lis2.length;
			for (var k = 0; k < len2; k++) {
				lis2[k].style.display = "none";
			}
			lis2[0].style.display = "";
		}
	}
}
// 配额管理保存
function quotaManageSaveBtn() {
	var inputs = $(".quotaManage input");
	var tenantId = $("#quotaTenantId").val();
	var objStr ;
	
	var url = ctx + "/rest/quota/" + tenantId + "/quota/list";
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		success : (function(data) {
			 objStr = JSON.parse(JSON.stringify(data)).obj;
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
		}
	});
	
	
	var list = [];
	var reg = /^[0-9]\d*$/;
	var flag = true;
	var inputLen = inputs.length;
	for (var i = 0; i < inputLen; i++) {
		if (inputs[i].value) {
			// 校验数值是否合法
			if (reg.test(inputs[i].value)) {
				var data = {};
				var ids = inputs[i].id.split("_");
				if (ids != null) {
					if (ids.length == 2) {
						var tip="";
						for(var j = 0; j <  objStr.length ; j ++ ){			
							if(objStr[j].code == inputs[i].name){
								if (objStr[j].code == "mem") {
									tip = "内存";
									objStr[j].usedValue = objStr[j].usedValue/1024;
								} else if (objStr[j].code == "vm") {
									tip = "虚拟机";
								} else if (objStr[j].code == "storage") {
									tip = "存储";
								} else if (objStr[j].code == "cpu") {
									tip = "CPU";
								}
								
								if( parseInt(inputs[i].value) < parseInt(objStr[j].usedValue)){
									showTip("您输入的" +tip + "的值小于已使用的数值");
									flag = false;
								}
								
							}
						}					
						
						data = {
							"tenantId" : tenantId,
							"quotaConfigId" : inputs[i].id.split("_")[1],
							"dataCnterId" : "0",
							"value" : inputs[i].value,
							"code" : inputs[i].name
						}

					} else if (ids.length == 3) {
						data = {
							"tenantId" : tenantId,
							"quotaConfigId" : inputs[i].id.split("_")[2],
							"projectId" : inputs[i].id.split("_")[1],
							"dataCnterId" : $(
									"#DB_" + inputs[i].id.split("_")[1]).val(),
							"value" : inputs[i].value,
                            "code" : inputs[i].name
						}
					}
					if (inputs[i].name == "mem") {
						data.value = inputs[i].value * 1024;
					}
					list.push(data);

				} else {
					showTip(i18nShow('quota_invalid_long'));
					$("#span_" + inputs[i].id.substring(6))
							.attr("class", "red");
					flag = false;
				}
			}
			else {
                flag = false;
			}
			// 校验内容填写是否完整
		} else {
			var className = inputs[i].className;
			var shoWords = "";
			if (className.indexOf("windows") != -1) {
				shoWords = "X86";
			} else if (className.indexOf("power") != -1) {
				shoWords = "POWER";
			} else if (className.indexOf("openstack") != -1) {
				shoWords = "OPENSTACK";
			} else if (className.indexOf("powervc") != -1) {
				shoWords = "POWERVC";
			}
			keys = $("." + className);
			if (keys != null) {
				for ( var k in keys) {
					if (keys[k].value) {
						showTip(shoWords + i18nShow('quota_not_complete'));
						return;
					}
				}
			}

		}
	}
	// 校验结果
	if (flag) {
		var data = {
			"quotasStr" : JSON.stringify(list),
			"tenantId" : tenantId
		}
		var url = ctx + "/rest/quota/quota/save";
		$.ajax({
					type : 'post',
					datatype : "json",
					url : url,
					async : false,
					data : data,
					success : (function(data) {
						if(data.success) {
                            showTip(i18nShow('tip_save_success'));
                            closePlatformrView("quotaGridTable_div");
						}
						else {
                            closeTip();
                            showTip(i18nShow('tip_save_fail') + " : \n" + data.msg);
						}
					}),
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						closeTip();
						showTip(i18nShow('tip_save_fail')
								+ XMLHttpRequest.responseText);
					}
				});
	} else {
		return;
	}

}

// 校验文本框数值是否合法
function verify(inputId) {
	var val = $("#quota_" + inputId).val();
	var reg = /^[0-9]\d*$/;
	if (val && !reg.test(val)) {
		$("#span_" + inputId).attr("class", "red");
		$("#span_" + inputId).html(i18nShow('quota_invalid'));
	} else {
		$("#span_" + inputId).attr("class", "hidden");
	}
}

// project 列表切换
function showProjectTab(projectId) {
	var lis1 = $("#openstackLeft >li");
	if (lis1 != null) {
		var len1 = lis1.length;
		for (var j = 1; j < len1; j++) {
			lis1[j].style.background = "";
			lis1[j].style.color = "#3e3e3e";
		}
		$("#project_" + projectId).css({
			"background" : "#1ab394",
			"color" : "#FFF"
		});
	}
	var lis2 = $("#openstackRight >li");
	if (lis2 != null) {
		var len2 = lis2.length;
		for (var k = 0; k < len2; k++) {
			lis2[k].style.display = "none";
		}
		$("#tabLi_" + projectId).css("display", "");
	}
}
function showProjectPVTab(projectId) {
	var lis1 = $("#powervcLeft >li");
	if (lis1 != null) {
		var len1 = lis1.length;
		for (var j = 1; j < len1; j++) {
			lis1[j].style.background = "";
			lis1[j].style.color = "#3e3e3e";
		}
		$("#project_" + projectId).css({
			"background" : "#1ab394",
			"color" : "#FFF"
		});
	}
	var lis2 = $("#powervcRight >li");
	if (lis2 != null) {
		var len2 = lis2.length;
		for (var k = 0; k < len2; k++) {
			lis2[k].style.display = "none";
		}
		$("#tabLi_" + projectId).css("display", "");
	}
}

function deleteTenantDialog(tenantId) {
    showTip(
        i18nShow('tenantManage_option_delete_confirm'),
        function () {
            var data = {
                "tenantId": tenantId
            }
            var url = ctx + "/rest/tenant/delete";
            $.ajax({
                type: 'post',
                datatype: "json",
                url: url,
                async: false,
                data: data,
                success: (function (data) {
                    if (data.success) {
                        showTip(i18nShow('tip_delete_success'));
                        $("#tenantManageTable").jqGrid().trigger("reloadGrid");
                    } else {
                        showTip(i18nShow('tenantManage_option_delete_failed') + " : \n" + data.msg);
                    }
                }),
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    closeTip();
                    showTip(i18nShow('tip_save_fail') + XMLHttpRequest.responseText);
                }
            });
        })
}