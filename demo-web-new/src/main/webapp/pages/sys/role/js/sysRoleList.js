function initSysRoleList() {
	$("#gridTable").jqGrid({
		url : ctx+"/sys/role/getSysRoleList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 85,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		multiboxonly: false,
		colModel : [ {
			name : "roleId",
			index : "roleId",
			label : "roleId",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},  {
			name : "roleName",
			index : "roleName",
			label : i18nShow('sys_role_name'),
			width : 200,
			sortable : true,
			align : 'left'
		},{
			name : "remark",
			index : "remark",
			label : i18nShow('com_remark'),
			width : 200,
			sortable : true,
			align : 'left'
		}, {
			name : 'option',
		    index : 'option',
		    label : i18nShow('com_operate'),
			width : 120,
			align : "left",
			sortable:false,
			title : false,
			formatter : function(cellValue,options,rowObject) {
				var updateflag = $("#updateFlag").val();
				var deleteFlag = $("#deleteFlag").val();
				var userListFlag = $("#userListFlag").val();
				var roleAuthorizationFlag = $("#roleAuthorizationFlag").val();
				var ret = "";
				if(updateflag=="1"){
					ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateDiv('"+rowObject.roleId+"')>"+i18nShow('com_update')+"</a>";
				}
				if(deleteFlag=="1"){
					ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteSysRole('"+rowObject.roleId+"') >"+i18nShow('com_delete')+"</a>";
				}
				var s = "";
				if(roleAuthorizationFlag == '1'){
					s  += "<option value='1' >"+i18nShow('sys_role_authorization')+"</option >";
				}
				if(userListFlag=="1"){
					s  += "<option  value='2' >"+i18nShow('sys_role_view_user')+"</option >";
				}
				if(userListFlag=="1" || roleAuthorizationFlag == '1'){
					 ret += "<select onchange=\"selMeched(this,'"+rowObject.roleId+"','"+rowObject.roleName+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;
				}
				return 	ret;
			}														
		}],
		
		viewrecords : true,
		sortname : "roleName",
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
		pager : "#gridPager",
//		caption : "角色信息记录",
		hidegrid : false
	});
	
	//自适应宽度
	$(window).resize(function() {
		$("#gridTable").setGridHeight(heightTotal() + 85);
	});	
	
	$("#updateForm").validate({
		rules: {
			roleNameInput: { required: true }
		},
	 messages: {
		  roleNameInput: {required: i18nShow('validate_data_required')}
		
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
}
function selMeched(element,roleId,roleName){
	var val = element.value;
	if(val == "1"){
		showRoleAuthorizationDiv(roleId,roleName);
	}else if(val == "2"){
		addUser(roleId);
	}
}
function clearAll(){
	$("#roleName").val("");
}


/**
 * 打开角色下关联用户窗口
 */
function addUser(roleId){
	$("#roleId").val(roleId);
	//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTableUser").jqGrid().GridUnload("gridTableUser");
	  autoRoleUser();//加载用户信息
	//openDialog('userAuthorizationDiv','所属角色下用户列表',667,470);
	$("#userAuthorizationDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 470,
		width : 667,
		title :i18nShow('sys_role_user_list'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}

/**
 * 角色下用户列表
 * @return
 */
function autoRoleUser() {
	$("#gridTableUser").jqGrid({
		url : ctx+"/sys/role/findSysUserListAct.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 300,
		autowidth : true, // 是否自动调整宽度
		multiselect:false,
		multiboxonly: false,
		postData: {"roleId" :$("#roleId").val()},
		colModel : [ {
			name : "userId",
			index : "userId",
			label : "userId",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},  {
			name : "lastName",
			index : "lastName",
			label : i18nShow('sys_role_user_name'),
			width : 140,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
					return rowObject.firstName+" "+cellValue;
		        }
		},{
			name : "loginName",
			index : "loginName",
			label : i18nShow('sys_role_user_login'),
			width : 139,
			sortable : true,
			align : 'left'
		},{
			name : "email",
			index : "email",
			label : i18nShow('sys_role_user_email'),
			width : 140,
			sortable : true,
			align : 'left'
		}, {
			name : "phone",
			index : "phone",
			label : i18nShow('sys_role_user_tel'),
			width : 135,
			sortable : true,
			align : 'left'
		}],
		
		viewrecords : true,
		sortname : "loginName",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchUserUser"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPagerUser",
		hidegrid : false
	});
}

function saveOrUpdateBtn(){
	$("#updateForm").submit();  
}

function search() {
	jqGridReload("gridTable", {
		"roleName" : $("#roleName").val().replace(/(^\s*)|(\s*$)/g, "")
	});
}

function deleteSysRole(roleId){
	if(roleId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/role/deleteSysRolePoByRoleIdAct.action",
				async : false,
				data:{"sysRolePo.roleId":roleId},
				success:(function(data){
					//如果返回数据不为空，说明被删除角色有关联用户数据
					if(data.rtnMsg!=""){
						showTip(data.rtnMsg);
					}else{
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('tip_delete_fail'));
				} 
			});
		});
	}else{
		var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');

		if(ids.length == 0){
			showError(i18nShow('error_select_one_data'));
			return;
		}
		
		var rtnMsgList = [];
		var list = [];
		$(ids).each(function (index,id2){
			var rowData = $("#gridTable").getRowData(id2);
			list[list.length] = rowData.roleId;
			})
	
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/role/deleteSysRolePoByRoleIdAct.action",
				async : false,
				data:{"sysRolePo.roleId":list.join(",")},
//				beforeSend:(function(data){
//					if(confirm("确定删除数据？")){
//						return true;
//					}else{
//						return false;
//					}
//				}),
				success:(function(data){
					//如果返回数据不为空，说明被删除角色有关联用户数据
					if(data.rtnMsg!=""){
						showTip(data.rtnMsg);
					}else{
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_delete_fail'));
				} 
			});
		
		});
	}
}




	// 修改角色信息
	function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#sysRoleUpdateDiv").dialog({ 
				autoOpen : true,
				modal:true,
				height:230,
				width:350,
				title:i18nShow('sys_role_update'),
		       // resizable:false
		});
//		clearTab();
		
		//清除form所有数据
		clearForm($('#updateForm'));
		
		$.post(ctx+"/sys/role/findSysRoleByIdAct.action",
			{"sysRolePo.roleId":objectId},
			function(data){
				$("#roleNameInput").val(data.roleName);
				$("#remarkInput").val(data.remark);
				$("#roleId").val(data.roleId);
				$("#roleIsActive").val(data.isActive);
			}
		)
		
		//添加隐藏域的默认值
		$("#roleMethod").val("update");
		
	}

	// 查看角色详细信息。
	function showSysRoleShowDiv(objectId){
		$("label.error").remove();
		$("#SysRoleShowDiv").dialog({
				autoOpen : true,
				modal:true,
				height:250,
				width:400,
				title:i18nShow('sys_role_detail'),
//				draggable: false,
		       // resizable:false
		})
//		clearTab();
		//清除form所有数据
		clearForm($('#showForm'));
		
		$.post(ctx+"/sys/role/findSysRoleByIdAct.action",
			{"sysRolePo.roleId":objectId},
			function(data){
				$("#roleNameShow").html(data.roleName);
				$("#remarkShow").html(data.remark);
				$("#roleIsActive").val(data.isActive);
			}
		)
	}
	

 	//修改或者添加角色信息
	function updateOrSaveData(){
		var roleMethod = $("#roleMethod").val();
		$("label.error").remove();
		var roleId=$("#roleId").val();//角色id
		var roleNameInput=$("#roleNameInput").val();//
		var remarkInput=$("#remarkInput").val();//
		var roleIsActive=$("#roleIsActive").val();//
		var url;
		if(roleMethod=="update"){
			url= ctx+"/sys/role/updateSysRolePoAct.action"
		}else{
			url= ctx+"/sys/role/saveSysRolePoAct.action"
		}
		var data = {	'sysRolePo.roleId':roleId,
						'sysRolePo.roleName':roleNameInput,
						'sysRolePo.remark':remarkInput,
						'sysRolePo.isActive':roleIsActive
						};
	
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			/*beforeSend:(function(data){
				return validate(datas);
			}),*/
			success:(function(data){
				if(data.rtnMsg!=""){
					showTip(data.rtnMsg);
				}else{
					showTip(i18nShow('tip_save_success'));
					$("#sysRoleUpdateDiv").dialog("close");
//					$("#gridTable").jqGrid().trigger("reloadGrid");
					// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#gridTable").jqGrid("GridUnload");
					initSysRoleList();
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_save_fail'));
			} 
		});
	}

	// 添加角色信息数据
	function createSysRole(){
		$("label.error").remove();
		$("#sysRoleUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:230,
				width:350,
				title:i18nShow('sys_role_save'),
				//resizable:false
		});
//		clearTab();
		//清除form所有数据
		clearForm($('#updateForm'));
		//添加隐藏域的默认值
		$("#roleMethod").val("save");
	}
	
	function clearTab(){
		 //var tab = document.getElementById("updateTab") ;
		 var inputs = document.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	
	function closeView(divId){
		$("#"+divId).dialog("close");
		
	}
	
	function clearForm(form) {
		  // iterate over all of the inputs for the form
		  // element that was passed in
		  $(':input', form).each(function() {
		    var type = this.type;
		    var tag = this.tagName.toLowerCase(); // normalize case
		    // it's ok to reset the value attr of text inputs,
		    // password inputs, and textareas
		    if (type == 'text' || type == 'password' || tag == 'textarea')
		      this.value = "";
		    // checkboxes and radios need to have their checked state cleared
		    // but should *not* have their 'value' changed
		    else if (type == 'checkbox' || type == 'radio')
		      this.checked = false;
		    // select elements need to have their 'selectedIndex' property set to -1
		    // (this works for both single and multiple select elements)
		    else if (tag == 'select')
		      this.selectedIndex = -1;
		  });
		};
	