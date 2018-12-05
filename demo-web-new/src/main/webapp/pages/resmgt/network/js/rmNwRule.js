var stard=0;//动态定义起始段位id
var num=0;//动态定义占位个数id
$(function() {
	 $("#use").change(function (){
		 if($("#use").val()){
			 $("#useCode").val($("#ucode").val());
			 var usecode=$("#useCode").val()+$("#use").val();
			 $("#useCode").val(usecode);
			 var use = document.getElementById("use");
			 var useText=use.options[use.selectedIndex].text;
			 $("#rmIpTypeName").val($("#rmRuleNamelink").val()+useText);
		 }else{
			 $("#useCode").val("");
			 $("#rmIpTypeName").val("");
		 }
		 
		 
	 });
	 $("#hostTypeId1").change(function (){
		 createRuleName();
	 });
	 $("#virtualTypeId").change(function (){
		 createRuleName();
	 });
	 $("#platFormId1").change(function (){
		 var platformId=$("#platFormId1").val();
		 initvirtualType(platformId);
		 createRuleName();
	 });
	 $("#haType").change(function (){
		 var haType=$("#haType").val();
		 createRuleName();
	 });
	 $("#virtualTypeId").click(function(){
		 var platformId=$("#platFormId1").val();
		 if(platformId==""){
			 showTip(i18nShow('tip_nw_platform_need'));
			 return;
		 }
	 });
	 $("#hostTypeId1").click(function(){
		 var virtualTypeId=$("#virtualTypeId").val();
		 if(virtualTypeId==""){
			 showTip(i18nShow('tip_nw_virtualType_need'));
			 return;
		 }
	 });
	$("#rmNwRuleGridTable").jqGrid({
		url : ctx+"/resmgt-network/rule/getRmNwRuleList.action", 
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height :heightTotal() + 40,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['rmNwRuleId','platFormId','virtualTypeId','hostTypeId','ucode',i18nShow('ip_rule_name'),i18nShow('cloud_service_platform'),i18nShow('rm_platform_virture_type'),i18nShow('cloud_service_host_type'),i18nShow('cloud_service_ha_type'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "rmNwRuleId",index : "rmNwRuleId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "platFormId",index : "platFormId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "virtualTypeId",index : "virtualTypeId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "hostTypeId",index : "hostTypeId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "ucode",index : "ucode",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "ruleName",index : "ruleName",	width : 120,sortable : false,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 120,sortable : false,align : 'left'},
		            {name : "virtualTypeName",index : "virtualTypeName",	width : 120,sortable : false,align : 'left'},
		            {name : "hsotTypeName",index : "hsotTypeName",	width : 120,sortable : false,align : 'left'},
		            {name : "haTypeName",index : "haTypeName",	width : 120,sortable : false,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var iplxglFlag = $("#iplxglFlag").val();
							var ret = " ";
							if(updateflag=="1"){
								ret += "<a  style='margin-right: 10px;margin-left: -5px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdate('"+rowObject.rmNwRuleId+"')>"+i18nShow('com_update')+"</a>"
							}
							if(deleteFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteData('"+rowObject.rmNwRuleId+"')>"+i18nShow('com_delete')+"</a>"
							}
							var s = "";
							if(iplxglFlag == "1"){
								s += "<option value='1' >"+i18nShow('ip_rule_use_type')+"</option >";
							}
							if(iplxglFlag == "1"){
							ret += "<select  onchange=\"selMeched(this,'"+rowObject.rmNwRuleId+"','"+rowObject.ucode+"','"+options.rowId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;' title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>";}
						return ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "rmNwRuleId",
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
		    		pager : "#rmNwRulePager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
//	//表单中platform_code不允许重复验证
//	jQuery.validator.addMethod("ruleNameTrim", function(value, element) { 
//		var validateValue=true;
//		var rmNwRuleMethod=$("#rmNwRuleMethod").val();
//		var validateRuleName = $("#validateRuleName").val();
//		if(value!=""){
//		$.ajax({
//			type:'post',
//			datatype : "json",
//			data:{"rmNwRulePo.ruleName":value},
//			url:ctx+"/resmgt-network/rule/selectRmNwRuleByRuleName.action",
//			async : false,
//			success:(function(data){
//				if(rmNwRuleMethod=="update"){
//					if(data==null||data.ruleName==validateRuleName){
//						validateValue=true;
//					}else{
//						validateValue=false;
//					}
//					
//				}else{
//				if(data==null){
//					validateValue=true;
//				}else{
//					validateValue=false;
//				}
//				}
//			}),
//			
//		});
//		}
//		return this.optional(element) || validateValue;
//		},
//	"规则名称不能重复"); 
	$("#updataRmNwRuleForm").validate({
		rules: {
			ruleName1:{required: true},
			hostTypeId1:{required: true},
			platFormId1:{required: true},
			virtualTypeId:{required: true},
			haType:{required:true}
		},
		messages: {
			ruleName1:{required: i18nShow('validate_data_required')},
			hostTypeId1:{required: i18nShow('validate_data_required')},
			platFormId1:{required: i18nShow('validate_data_required')},
			virtualTypeId:{required: i18nShow('validate_data_required')},
			haType:{required: i18nShow('validate_data_required')}
		},
		
		submitHandler: function() {
			updateOrSaveRmNwRule();
		}
	});
	//自适应宽度
	$("#rmNwRuleGridTable").setGridWidth($("#rmNwRuleGridTable_div").width());
	$(window).resize(function() {
		$("#rmNwRuleGridTable").setGridWidth($("#rmNwRuleGridTable_div").width());
		$("#rmNwRuleGridTable").setGridHeight(heightTotal() + 40);
    });
	
});	
function selMeched(element,useId,code,rowId){
	var val = element.value;
	if(val == "1"){
		showRmNwRuleList(useId,code,rowId);
	}
}
//查询
function search() {
		$("#rmNwRuleGridTable").jqGrid('setGridParam', {
			url : ctx + "/resmgt-network/rule/getRmNwRuleList.action",//你的搜索程序地址
			postData : {
				"ruleName" : $("#ruleName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"virtualTypeId" : $("#s_virtualTypeId").val(),
				"hostTypeId" : $("#s_hostTypeId").val(),
				"platFormId" : $("#s_platFormId").val(),
				"haType" : $("#c_haType").val()
			}, //发送搜索条件
			pager : "#rmNwRulePager"
		}).trigger("reloadGrid"); //重新载入
	}
//弹出添加页面
function createData(){
		  $("label.error").remove();
		  $("#updataRmNwRule").dialog({
			autoOpen : true,
			modal:true,
			height:270,
			width:654,
			title:i18nShow('ip_rule_save'),
			//resizable:false
	 });
	    $("#ruleName1").val("");
	    selectByValue("hostTypeId1","");
	    selectByValue("platFormId1","");
	    selectByValue("haType","");
	    //selectByValue("virtualTypeId","");
	    $("#virtualTypeId option").remove();
	    $("#virtualTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"</option>");
	    $("#rmNwRuleMethod").val("save");
	    $("#hostTypeId1").removeAttr("disabled");
	    $("#platFormId1").removeAttr("disabled");
	    $("#virtualTypeId").removeAttr("disabled");
	    $("#haType").removeAttr("disabled");
	    $("#tipRule").css("display","none");
	    $("#rule_btn_ok").css("display","Inline");
	}
	  //关闭窗口
	  function closeRmNwRuleView(){
		  
		  $("#updataRmNwRule").dialog("close");
	  }
	//提交表单
	function saveOrUpdateRmNwRuleBtn(){
		$("#updataRmNwRuleForm").submit();  
	}
	
	function updateOrSaveRmNwRule(){
		var ruleName=$("#ruleName1").val();
		var hostTypeId=$("#hostTypeId1").val();
		var platFormId=$("#platFormId1").val();
		var virtualTypeId=$("#virtualTypeId").val();
		var rmNwRuleMethod=$("#rmNwRuleMethod").val();
		var rmNwRuleId=$("#rmNwRuleId").val();
		var haType=$("#haType").val();
		var falg=checkRuleName(platFormId,virtualTypeId,hostTypeId,haType);
		if(falg=="false"){
			showError(i18nShow('error_ip_rule_save'));
			return;
		}
		var url;
			if(rmNwRuleMethod=="update"){
				url= ctx+"/resmgt-network/rule/updateRmNwRule.action"
			}else{
				url= ctx+"/resmgt-network/rule/saveRmNwRule.action"
			}
			var data = {
					'rmNwRulePo.hostTypeId':hostTypeId,
					'rmNwRulePo.platFormId':platFormId,
					'rmNwRulePo.virtualTypeId':virtualTypeId,
					'rmNwRulePo.ruleName':ruleName,
					'rmNwRulePo.rmNwRuleId':rmNwRuleId,
					'rmNwRulePo.haType':haType
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
					showTip(i18nShow('tip_save_success'));
					$("#updataRmNwRule").dialog("close");
					$("#rmNwRuleGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_save_fail'));   
				} 
			});	
	}
	 function showUpdate(id){
		 $("#tipRule").css("display","none");
		 $("#rule_btn_ok").css("display","Inline");
		   $("label.error").remove();
		   $("#hostTypeId1").removeAttr("disabled");
		   $("#platFormId1").removeAttr("disabled");
		   $("#virtualTypeId").removeAttr("disabled");
		   $("#haType").removeAttr("disabled");
			$("#updataRmNwRule").dialog({
					autoOpen : true,
					modal:true,
					height:290,
					width:650,
					title:i18nShow('ip_rule_update'),
//					draggable: false,selectCmtDatastoreVoById
			       // resizable:false
			})
			$.post(ctx+"/resmgt-network/rule/selectRmNwRuleById.action",{"rmNwRulePo.rmNwRuleId" : id},function(data){
				 $("#hostTypeId1 option").each(function(){
				        if($(this).text() == data.hsotTypeName){
				        $(this).attr("selected",true);
				        }
				    });  
				 $("#platFormId1 option").each(function(){
				        if($(this).text() == data.platformName){
				        $(this).attr("selected",true);
				        }
				    });  
				initvirtualType(data.platFormId);
				 $("#virtualTypeId option").each(function(){
				        if($(this).text() == data.virtualTypeName){
				        $(this).attr("selected",true);
				        }
				    }); 
				 $("#haType option").each(function(){
					 if($(this).text() == data.haTypeName){
						 $(this).attr("selected",true);
					 }
				 }); 
//				 $("input[name='haType'][value="+HA+"]").attr("checked",true);
				 if(data.rmNwRuleLIstCount!=0){
					 $("#hostTypeId1").attr("disabled","disabled");
					 $("#platFormId1").attr("disabled","disabled");
					 $("#virtualTypeId").attr("disabled","disabled");
					 $("#haType").attr("disabled","disabled");
					 $("#rule_btn_ok").css("display","none");
					 $("#tipRule").css("display","block");
				 }
				 $("#ruleName1").val(data.ruleName);
				 $("#rmNwRuleId").val(data.rmNwRuleId);
				$("#validateplatFormId").val(data.platFormId);
				$("#validatevirtualTypeId").val(data.virtualTypeId);
				$("#validatehostTypeId").val(data.hostTypeId);
				$("#validatehaType").val(data.haType);
				$("#rmNwRuleMethod").val("update");
			})
	   }

	 //删除分配规则
	 function deleteData(dataId){
		 if(dataId){
				showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-network/rule/deleteRmNwRule.action",
					async : false,
					data:{"rmNwRulePo.rmNwRuleId":dataId},
					success:(function(data){
						showTip(data.msg);
						$("#rmNwRuleGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_delete_fail'));
					} 
				});
				});
			}else{
				var ids = jQuery("#rmNwRuleGridTable").jqGrid('getGridParam','selarrrow');

				if(ids.length == 0){
					showError(i18nShow('error_select_one_data'));
					return;
				}
				var list = [];
				$(ids).each(function (index,id2){
					var rowData = $("#rmNwRuleGridTable").getRowData(id2);
					list[list.length] = rowData.rmNwRuleId;
					})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-network/rule/deleteRmNwRule.action",
					async : false,
					data:{"rmNwRulePo.rmNwRuleId":list.join(",")},
					success:(function(data){
						showTip(data.msg);
						$("#rmNwRuleGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_delete_fail'));
					} 
				});
			});
			};
			
		}
	 //以下为虚拟化类型操作
	 function showRmNwRuleList(rmNwRuleId,ucode,rowId){
		   var rmNwRuleList= $("#rmNwRuleGridTable").jqGrid("getRowData",rowId);
		   $("#rmRuleNamelink").val(rmNwRuleList.hsotTypeName);
		   $("#platFormId").val(rmNwRuleList.platFormId);
		   $("#virtualTypeId1").val(rmNwRuleList.virtualTypeId);
		   $("#hostTypeId").val(rmNwRuleList.hostTypeId);
            $("#rmNwRuleId1").val(rmNwRuleId);
            $("#ucode").val(ucode);
             $("#RmNwRuleListGridTable").jqGrid().GridUnload("RmNwRuleListGridTable");
			$("label.error").remove();
			$("#RmNwRuleListGridTable").jqGrid({
				url : ctx+"/resmgt-network/rule/getRmNwRuleListList.action",
				rownumbers : true, 
				datatype : "json", 
				mtype : "post", 
				postData :{"rmNwRuleId":rmNwRuleId},
				height : 370,
				autowidth : true,
//				multiselect:true,
				colNames:['rmNwRuleListId',i18nShow('ip_rule_use_typeName'),i18nShow('ip_rule_use_typeCode'),i18nShow('ip_rule_actNum'),i18nShow('ip_rule_occNum'),i18nShow('ip_rule_vmwarePgPefix'),i18nShow('ip_rule_occSite'),i18nShow('com_operate')],
				colModel : [ 
				            {name : "rmNwRuleListId",index : "rmNwRuleListId",width : 120,sortable : true,align : 'left',hidden:true},
				            {name : "rmIpTypeName",index : "rmIpTypeName",	width : 120,sortable : true,align : 'left'},
				            {name : "useCode",index : "useCode",	width : 120,sortable : true,align : 'left'},
				            {name : "actNum",index : "actNum",	width : 120,sortable : true,align : 'left'},
				            {name : "occNum",index : "occNum",	width : 120,sortable : true,align : 'left',hidden:true},
				            {name : "vmwarePgPefix",index : "vmwarePgPefix",	width : 120,sortable : true,align : 'left'},
				            {name : "occSite",index : "occSite",	width : 120,sortable : true,align : 'left'},
				            {name:"option",index:"option",width:120,sortable:false,align:"left",
								formatter:function(cellVall,options,rowObject){
		 							var updateflag = $("#updateFlag2").val();
		 							var deleteFlag = $("#deleteFlag2").val();
		 							var ret = " ";
		 							if(updateflag=="1"){
		 								//str1= "<input type='button' style=\" margin-right: 10px;\" class='btn_edit_s' onclick=showRmNwRuleListUpdate('"+rowObject.rmNwRuleListId+"')  title='修改' />"
		 								ret +="<a  style='margin-right: 10px;margin-left: -5px;text-decoration:none;' href='javascript:#' title='' onclick=showRmNwRuleListUpdate('"+rowObject.rmNwRuleListId+"') >"+i18nShow('com_update')+"</a>";
		 							}
		 							if(deleteFlag=="1"){
		 								//str2 = "<input type='button' style=\" margin-right: 0px;\" class='btn_del_s' onclick=deleteRmNwRuleListData('"+rowObject.rmNwRuleListId+"')  title='删除' />"
		 								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteRmNwRuleListData('"+rowObject.rmNwRuleListId+"') >"+i18nShow('com_delete')+"</a>";
		 							}
									
		 							return 	ret;
									
// 									return 	"<input type='button' style=\" margin-right: 10px;\" class='btn_edit_s' onclick=showRmNwRuleListUpdate('"+rowObject.rmNwRuleListId+"')  title='修改' /><input type='button' style=\" margin-right: 10px;\" class='btn_del_s' onclick=deleteRmNwRuleListData('"+rowObject.rmNwRuleListId+"')  title='删除' />";
									
								}
				            }
				            ],
				            viewrecords : true,
				    		sortname : "rmNwRuleListId",
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
				    		pager : "#RmNwRuleListGridPager",
//				    		caption : "设备信息记录",
				    		hidegrid : false
			});
			//展示属性信息dialog
			$("#RmNwRuleList").dialog({ 
				autoOpen : true,
				modal : true,
				height : 550,
				title:i18nShow('ip_rule_use_type'),
				width : 1050,
				close:function(){
					  $( "#rmNwRuleGridTable" ).jqGrid().trigger("reloadGrid" );	
				}
			});
			//自适应
			$("#RmNwRuleListGridTable").setGridWidth($("#RmNwRuleListGridDiv").width());
			$(window).resize(function() {
				$("#RmNwRuleListGridTable").setGridWidth($("#RmNwRuleListGridDiv").width());
		    });
			//表单验证
	   jQuery.validator.addMethod("useCodeTrim", function(value, element) { 
		var validateValue=true;
		//wmy
		var rmNwRuleId=$("#rmNwRuleId1").val();
		var rmNwRuleListMethod=$("#rmNwRuleListMethod").val();
		var validateUseCode = $("#validateUseCode").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmNwRuleListPo.useCode":value,"rmNwRuleListPo.rmNwRuleId":rmNwRuleId},
			url:ctx+"/resmgt-network/rule/selectRmNwRuleListByUseCode.action",
			async : false,
			success:(function(data){
				if(rmNwRuleListMethod=="update"){
					if(data==null||data.useCode==validateUseCode){
						validateValue=true;
					}else{
						validateValue=false;
					}
					
				}else{
				if(data==null){
					validateValue=true;
				}else{
					validateValue=false;
				}
				}
			}),
			
		});
		}
		return this.optional(element) || validateValue;
		},
	"用途编码不能重复"); 
			$("#updataRmNwRuleListForm").validate({
				rules: {
					actNum:{required: true,digits: true,min:1},
					occNum:{required: true,digits: true},
					use:{required: true},
					useCode:{useCodeTrim:true}
				},
				messages: {
					actNum:{required: i18nShow('validate_data_required'),digits:i18nShow('validate_number'),min:i18nShow('validate_vmBase_min')},
					occNum:{required: i18nShow('validate_data_required'),digits:i18nShow('validate_number')},
					use:{required: i18nShow('validate_data_required')},
					useCode:{useCodeTrim:i18nShow('validate_data_remote')}
				},
				
				submitHandler: function() {
					updateOrSaveRmNwRuleList();
				}
			});
	 }
	 
	//弹出添加页面
	 function createRmNwRuleList(){
		      $("#occSitTable").html("");
	 		  $("label.error").remove();
	 		  $("#updataRmNwRuleList").dialog({
	 			autoOpen : true,
	 			modal:true,
	 			height:350,
	 			width:650,
	 			title:i18nShow('ip_rule_use_type_save'),
	 			close:function (){ stard=0;num=0;},//关闭dialog时初始化id
	 			//resizable:false
	 	 });
	 		 clearTab();
	 	   $("#useCode").val($("#ucode").val());
	 	  $("#rmIpTypeName").val($("#rmRuleNamelink").val());
	 	    selectByValue("use","");
	 	   var platformId=$("#platFormId").val();
	 	   var virtualTypeId=$("#virtualTypeId1").val();
	 	   var hostTypeId=$("#hostTypeId").val();
	 		initUse(platformId,virtualTypeId,hostTypeId);//加载用途下拉框
	 	    $("#rmNwRuleListMethod").val("save");
	 	}
	
	 //关闭窗口
	  function closeRmNwRuleListView(){
		   stard=0;
			num=0;
		  $("#updataRmNwRuleList").dialog("close");
	  }
	//提交表单
	function saveOrUpdateRmNwRuleLisBtn(){
		$("#updataRmNwRuleListForm").submit();  
	}
	
	function updateOrSaveRmNwRuleList(){
		var occSite="";
		var use = document.getElementById("use");
		var useId= use.options[use.selectedIndex].id;
		var rmIpTypeName=$("#rmIpTypeName").val();
		var useCode=$("#useCode").val();
		var actNum=$("#actNum").val();
		//var occNum=$("#occNum").val();
		var occNum = "0";
		var vmwarePgPefix=$("#vmwarePgPefix").val();
		var rmNwRuleId=$("#rmNwRuleId1").val();
		var rmNwRuleListId=$("#RmNwRuleListId").val();
		var row=$("#occSitTable tr").length;
		var rmNwRuleListMethod=$("#rmNwRuleListMethod").val();
		var check=checkOccSite(row+1,row+1);
		var lastStardocc=$("#occStard"+row).val();
		var lastNumocc=$("#occNum"+row).val();
		if(parseInt(lastStardocc)+parseInt(lastNumocc)-1>parseInt(actNum)+parseInt(occNum)){
			showTip(i18nShow('tip_ip_rule_use_type_1'));
			return;
		}
		if(check!=i18nShow('tip_ip_rule_use_type_2')&&check!=i18nShow('tip_ip_rule_use_type_3')&&check!=i18nShow('tip_ip_rule_use_type_4')){
			showTip(check);
			return;
		}
		var occSiteNum=0;
		for ( var int = 1; int <row+1; int++) {
			
			 occSiteNum+=parseInt($("#occNum"+int).val());
		}
		if(occNum!=occSiteNum){
			showTip(i18nShow('tip_ip_rule_use_type_5'));
			return;
		}
		for ( var int = 1; int < row+1; int++) {
			occSite+=$("#occStard"+int).val()+"-"+$("#occNum"+int).val()+",";
//			occSite+=occSite;
		}
		var url;
			if(rmNwRuleListMethod=="update"){
				url= ctx+"/resmgt-network/rule/updateRmNwRuleList.action"
			}else{
				url= ctx+"/resmgt-network/rule/saveRmNwRuleList.action"
			}
			var data = {
					'rmNwRuleListPo.useId':useId,
					'rmNwRuleListPo.rmNwRuleListId':rmNwRuleListId,
					'rmNwRuleListPo.occSite':occSite,
					'rmNwRuleListPo.rmIpTypeName':rmIpTypeName,
					'rmNwRuleListPo.useCode':useCode,
					'rmNwRuleListPo.actNum':actNum,
					'rmNwRuleListPo.occNum':occNum,
					'rmNwRuleListPo.vmwarePgPefix':vmwarePgPefix,
					'rmNwRuleListPo.rmNwRuleId':rmNwRuleId
					
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
					showTip(i18nShow('tip_save_success'));
					stard=0;
					num=0;//初始化id
					$("#updataRmNwRuleList").dialog("close");
					$("#RmNwRuleListGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					stard=0;
					num=0;//初始化id
					showError(i18nShow('tip_save_fail'));   
				} 
			});	
	}
	//删除
	function deleteRmNwRuleListData(id){
		 if(id){
				showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-network/rule/deleteRmNwRuleList.action",
					async : false,
					data:{"rmNwRuleListPo.rmNwRuleListId":id},
					success:(function(data){
						showTip(i18nShow('tip_delete_success'));
						$("#RmNwRuleListGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_delete_fail'));
					} 
				});
				});
			}
		
	}
	 function showRmNwRuleListUpdate(id){
		   $("label.error").remove();
			$("#updataRmNwRuleList").dialog({
					autoOpen : true,
					modal:true,
					height:350,
					width:650,
					title:i18nShow('ip_rule_use_type_update'),
					close:function (){ stard=0;num=0;},//关闭dialog时初始化id
//					draggable: false,selectCmtDatastoreVoById
			        //resizable:false
			})
			$.post(ctx+"/resmgt-network/rule/selectRmNwRuleListById.action",{"rmNwRuleListPo.rmNwRuleListId" : id},function(data){
				var stcode=$("#ucode").val();
				var code=data.useCode;
				code=code.replace(stcode,"");
				 var platformId=$("#platFormId").val();
			 	 var virtualTypeId=$("#virtualTypeId1").val();
			 	 var hostTypeId=$("#hostTypeId").val();
			 	initUse(platformId,virtualTypeId,hostTypeId);//加载用途下拉框
				 $("#use option").each(function(){
				        if($(this).val() == code){
				        $(this).attr("selected",true);
				        }
				    });  
				 $("#RmNwRuleListId").val(data.rmNwRuleListId);
				 $("#rmNwRuleId1").val(data.rmNwRuleId);
				 $("#rmIpTypeName").val(data.rmIpTypeName);
				 $("#useCode").val(data.useCode);
				 $("#actNum").val(data.actNum);
				 $("#occNum").val(data.occNum);
				 $("#vmwarePgPefix").val(data.vmwarePgPefix);
				 $("#rmNwRuleListMethod").val("update");
				 $("#validateUseCode").val(data.useCode);
				 $("#occSitTable").html("");
				 var accSitList=new Array();
				 accSitList=data.occSite.split(",");
				 for ( var int = 0; int < accSitList.length-1; int++) {
//					 var occSite=new Array();
					 var occSite=accSitList[int];
					var occSit1=new Array();//name=\"occStard"
					occSit1=occSite.split("-");
					var occStard="occStard"+(int+1);
					var occNum="occNum"+(int+1);
					$("#occSitTable").append("<tr><td>  <input type=\"text\" name=\""+occStard+"\" id=\""+occStard+"\"   style=\"width: 40px;\" class=\"textInput\" /></td><td><label>"+"-"+"</label></td><td> <input type=\"text\" name=\""+occNum+"\" id=\""+occNum+"\" style=\"width: 40px;\" class=\"textInput\" /></td></tr>");
                    $("#occStard"+(int+1)).val(occSit1[0]);
                    $("#occNum"+(int+1)).val(occSit1[1]);
				}
			})
	   }
	 //添加占位段
	 function addOccSitTable(){
		 var rows=$("#occSitTable tr").length;
		var rmNwRuleListMethod=$("#rmNwRuleListMethod").val();
		if(rmNwRuleListMethod=="update"){
			var rows=$("#occSitTable tr").length;
			var occStard="occStard"+(rows+1);
			var occNum="occNum"+(rows+1);
			var occNumValue=$("#occNum").val();
			 var actNumValue=$("#actNum").val();
			var check=checkOccSite((rows+1),(rows+1));
			 if(occNumValue==""||actNumValue==""){
				 showTip(i18nShow('tip_ip_rule_use_type_6'));
				stard=rows;
				 num=rows;
			}else if(check!=i18nShow('tip_ip_rule_use_type_2')){
				showError(check);
				stard=rows;
				 num=rows;
			}else{
			 $("#occSitTable").append("<tr><td>  <input type=\"text\" name=\""+occStard+"\" id=\""+occStard+"\"   style=\"width: 40px;\" class=\"textInput\" /></td><td><label>"+"-"+"</label></td><td> <input type=\"text\" name=\""+occNum+"\" id=\""+occNum+"\" style=\"width: 40px;\" class=\"textInput\" /></td></tr>"
			 );
			}
		}else{
		 stard=stard+1;
		 var occStard="occStard"+stard;
		 num=num+1;
		 var occNum="occNum"+num;
		 var occNumValue=$("#occNum").val();
		 var actNumValue=$("#actNum").val();
		var check=checkOccSite(stard,num);
		if(occNumValue==""||actNumValue==""){
			showTip(i18nShow('tip_ip_rule_use_type_6'));
			stard=rows;
			 num=rows;
		}else if(i18nShow('tip_ip_rule_use_type_2')){
			showError(check);
			stard=rows;
			 num=rows;
		}else{
		
		 $("#occSitTable").append("<tr><td>  <input type=\"text\" name=\""+occStard+"\" id=\""+occStard+"\"   style=\"width: 40px;\" class=\"textInput\" /></td><td><label>"+"-"+"</label></td><td> <input type=\"text\" name=\""+occNum+"\" id=\""+occNum+"\" style=\"width: 40px;\" class=\"textInput\" /></td></tr>"
				 );
		}
		}
	 }
	//验证ip分配规则表单
   function	checkOccSite(cstard,cnum){
//	   if(cstard==1&&cnum==1){
//		   return "success";
//		   }
	   var stard1=cstard-1;
	   var stard2=cstard-2;
	   var num1=cnum-1;
	   var num2=cnum-2;
	   var stardValue1=$("#occStard"+stard1).val();
	   var stardValue2=$("#occStard"+stard2).val();
	   var numValue1=$("#occNum"+num1).val();
	   var numValue2=$("#occNum"+num2).val();
	   var rows=$("#occSitTable tr").length;
	   var actNum=$("#actNum").val();
	   var occNum=$("#occNum").val();
	   var occSiteNum=0;
	   var occSiteNum2;
	   var flag=false;
	   var flag2=false;
	   var rows=$("#occSitTable tr").length;
	   for ( var int = 1; int <rows+1; int++) {
			 occSiteNum2=parseInt($("#occNum"+int).val());
			 occSiteStard2=parseInt($("#occStard"+int).val());
			 if(isNaN(occSiteNum2)||isNaN(occSiteStard2)){
				 flag=true;
				 break;
			 }
		}
	   for ( var int2 = 1; int2 < rows+1; int2++) {
		   occSiteNum3=parseInt($("#occNum"+int2).val());
			 occSiteStard3=parseInt($("#occStard"+int2).val());
			 if(occSiteNum3==0||occSiteStard3==0||parseInt(occSiteNum3)<0||parseInt(occSiteStard3)<0){
				 flag2=true;
				 break;
			 }
		
	}
		for ( var int = 1; int <rows+1; int++) {
			 occSiteNum+=parseInt($("#occNum"+int).val());
		}
		if(occSiteNum>occNum){
			return i18nShow('tip_ip_rule_use_type_5');
		}else if(stardValue1==""||numValue1==""){
		   return i18nShow('tip_ip_rule_use_type_7');
	   }else if(occSiteNum==occNum){
		   return i18nShow('tip_ip_rule_use_type_4');
	   }else if(occNum==0){
		   return i18nShow('tip_ip_rule_use_type_3');
	   }else if(flag){
		   return i18nShow('tip_ip_rule_use_type_8');
	   }else if(flag2){
		   return i18nShow('tip_ip_rule_use_type_9');
	   }else if(rows>1&&stardValue1<=stardValue2){
		   return i18nShow('tip_ip_rule_use_type_10');
	   }else if(rows>1&&parseInt(stardValue1)<=parseInt(stardValue2)+parseInt(numValue2)-1){
		   return i18nShow('tip_ip_rule_use_type_11');
	   }else if(rows>1&&parseInt(stardValue1)+parseInt(numValue1)-1>parseInt(actNum)+parseInt(occNum)){
		   return i18nShow('tip_ip_rule_use_type_12');
	   }else{
	       return i18nShow('tip_ip_rule_use_type_2');
	   }
   }
 //清空表单
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
	//加载虚拟化类型下拉选
	function initvirtualType(platformId){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/network/findChangeVirtualTypeByPFAct.action",
			async : false,
			data:{"platformId":platformId},
			success:(function(data){
				$("#virtualTypeId").empty();
				$("#virtualTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				$(data).each(function(i,item){
					$("#virtualTypeId").append("<option value='"+item.virtualTypeId+"'>"+item.virtualTypeName+"</option>");					
				});	
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_req_fail'));
			} 
		});
	}
	//初始化用途下拉选
	function initUse(platformId,virtualTypeId,hostTypeId){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/network/findChangeUseByAllAct.action",
			async : false,
			data:{"platformId":platformId,"virtualTypeId":virtualTypeId,"hostTypeId":hostTypeId},
			success:(function(data){
				$("#use").empty();
				$("#use").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				$(data).each(function(i,item){
					$("#use").append("<option id='"+item.useId+"' value='"+item.useCode+"'>"+item.useName+"</option>");					
				});	
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_req_fail'));
			} 
		});
	}
	//拼写规则名称
	function createRuleName(){
		 var virtual = document.getElementById("virtualTypeId");
		 var virtualTypeId=virtual.options[virtual.selectedIndex].text;
		 var plat = document.getElementById("platFormId1");
		 var platFormId1=plat.options[plat.selectedIndex].text;
		 var host = document.getElementById("hostTypeId1");
		 var hostTypeId1=host.options[host.selectedIndex].text;
		 var haTypeStr = document.getElementById("haType");
		 var haType = haTypeStr.options[haTypeStr.selectedIndex].text;
		 if(virtualTypeId==""+i18nShow('com_select_defalt')+"" || virtualTypeId==i18nShow('com_select_defalt')+"..."){
			 virtualTypeId="";
		 }
		 if(platFormId1==i18nShow('com_select_defalt')+"..."){
			 platFormId1="";
		 }
		 if(hostTypeId1==i18nShow('com_select_defalt')+"..."){
			 hostTypeId1="";
		 }
		 if(haType==i18nShow('com_select_defalt')+"..."){
			 haType="";
		 }
		 $("#ruleName1").val(platFormId1+virtualTypeId+hostTypeId1+haType);
	}
	//验证先输入占位IP数后输入占位ip段
	function checkRuleName(platFormId,virtualTypeId,hostTypeId,haType){
		var validateValue=true;
		var rmNwRuleMethod=$("#rmNwRuleMethod").val();
		var validateplatFormId = $("#validateplatFormId").val();
		var validatevirtualTypeId = $("#validatevirtualTypeId").val();
		var validatehostTypeId = $("#validatehostTypeId").val();
		var validatehaType = $("#validatehaType").val();
		var flag=false;
		if(platFormId!=""&&virtualTypeId!=""&&hostTypeId!=""&&haType!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmNwRulePo.platFormId":platFormId,"rmNwRulePo.virtualTypeId":virtualTypeId,"rmNwRulePo.hostTypeId":hostTypeId,"rmNwRulePo.haType":haType},
			url:ctx+"/resmgt-network/rule/selectRmNwRuleByRuleName.action",
			async : false,
			success:(function(data){
				if(rmNwRuleMethod=="update"){
					if(data!=null){
					if(data.platFormId==validateplatFormId&&data.virtualTypeId==validatevirtualTypeId&&data.hostTypeId==validatehostTypeId&&data.haType==validatehaType){
						flag=true;
					}
					}
					if(data==null||flag){
						validateValue=true;
					}else{
						validateValue=false;
					}
					
				}else{
				if(data==null){
					validateValue=true;
				}else{
					validateValue=false;
				}
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_req_fail'));
				validateValue=false;
			} 
		});
		}
		if(validateValue){
			return "success";
		}else{
			return "false";
		}
		
	}
	
	
	
	