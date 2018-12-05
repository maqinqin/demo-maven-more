function initSecureArea() {
	var queryData = {};
		$("#gridTable").jqGrid({
			url : ctx + "/network/secure/area/getSecureAreaList.action",
			rownumbers : true,
			datatype : "json",
			postData : queryData,
			mtype : "post",
			height : heightTotal() + 80,
			autowidth : true,
			//multiselect:true,// 是否显示下拉框
			multiboxonly: false,
			colNames : [ 'secureAreaId',i18nShow('rm_nw_secure_name'), i18nShow('com_status'), '安全区域类型',i18nShow('com_operate')],
			colModel : [ {
				name : "secureAreaId",
				index : "secureAreaId",
				width: 120,
				sortable : true,
				hidden : true
			}, {
				name : "secureAreaName",
				index : "secureAreaName",
				sortable : true,
				width: 180,
				align : 'left',
				
			},{
				name : "isActive",
				index : "isActive",
				sortable : true,
				width: 180,
				align : 'left',
				formatter:function(cellVall,options,rowObject){
					if(cellVall=='Y'){// <span class="tip_green">已激活</span>
						return "<span class='tip_green'>"+i18nShow('com_status_Y')+"</span>";
					}else{
						return "<span class='tip_red'>"+i18nShow('com_status_N')+"</span>";
					}
					return cellVall;
				}
			},
                {
                    name : "secureAreaTypeName",
                    index : "secureAreaTypeName",
                    sortable : true,
                    width: 180,
                    align : 'left',
                },
				{
				name : "option",
				index : "option",
				width : 180,
				sortable : false,
				align:'left',
				formatter : function(cellVall, options, rowObject) {
					var updateFlag = $('#updateFlag').val();
					var deleteFlag = $('#deleteFlag').val();
					var clickFlag = $('#clickFlag').val();
					var s = "";
					var ret = "　　";
					if(updateFlag == '1'){
						ret +='<a  style="margin-right: 10px;margin-left: -25px;text-decoration:none;" href="javascript:#" title=""  onclick=addSecureArea("' + rowObject.secureAreaId + '")>'+i18nShow('com_update')+'</a>';
					}
					if(deleteFlag == '1'){
						ret +='<a  style="margin-right: 10px;text-decoration:none;" href="javascript:#" title=""  onclick=deletes("' + rowObject.secureAreaId + '","'+rowObject.secureAreaName+'")>'+i18nShow('com_delete')+'</a>';
					}
					if(clickFlag == '1'){
						s +='<option value="1" >'+i18nShow('rm_nw_secure_tier')+'</option >';
					}
					/*if(clickFlag == '1'){
						ret += '<select onchange="selMeched(this,\''+rowObject.secureAreaId+'\')" style=" margin-right: 10px;text-decoration:none;width:90px;"title=""><option vallue="" select="selected">'+i18nShow('com_select_defalt')+'</option>"'+s+'"</select>' ;
					}	*/
					return ret;
					}
				}],
			viewrecords : true,
			sortname : "secureAreaName",
			rowNum : 10,
			rowList : [5, 10, 15, 20, 30 ],
			jsonReader : {
				root : "dataList",
				records : "record",
				page : "page",
				total : "total",
				repeatitems : false
			},
			pager : "#gridPager"
		});
		
			$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridMain").width());
			$("#gridTable").setGridHeight(heightTotal() + 80);
	    });
		$("#add_secure_Area_Form").validate({
			rules:{
				"secureSreaName":{required:true}
					},
			messages:{
				"secureSreaName":{required:i18nShow('validate_data_required')}
					},
			 submitHandler:function(){
				 save();
			 }
	 	});
}

function selMeched(element,id){
	var val = element.value;
	if(val == "1"){
		showSecureTier(id);
	}
}
function addSecureArea(secureAreaId){
		$("#secureId").val("");
		$("#secureSreaName").val("");
		 $("label.error").remove();//清楚提示信息
		var title;
		if(secureAreaId){
	     title=i18nShow('rm_nw_secure_update');
			
			$.post(ctx + "/network/secure/area/viewSecureArea.action",{"rmNwSecurePo.secureAreaId" : secureAreaId},function(data){
				$("#secureId").val(data.secureAreaId);
				$("#secureSreaName").val(data.secureAreaName);
                $("#secureAreaType").val(data.secureAreaType);
				$("#add_secure_Area_Div").dialog({
					width : 380,
					autoOpen : true,
					modal : true,
					height :250,
					title:title,
					//resizable:false // 控制div大小
				});
			});
		}else{
			title=i18nShow('rm_nw_secure_save');
		$("#add_secure_Area_Div").dialog({
			width : 380,
			autoOpen : true,
			modal : true,
			height : 250,
			title:title,
			//resizable:false
		});
		}
}
function saveSecureAreaBtn(){
	     
			$("#add_secure_Area_Form").submit();
		}
function save(){
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/network/secure/area/saveSecureArea.action",
			async : false,
	       data : {// 获得表单数据
	    	    "rmNwSecurePo.secureAreaId" : $("#secureId").val(),
	    	    "rmNwSecurePo.secureAreaName" : $("#secureSreaName").val()
			   ,"rmNwSecurePo.secureAreaType":$("#secureAreaType").val()
				},
			success : function(){
				closeView("add_secure_Area_Div");
				search();
			},
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
		});
}
function closeView(id){
			$("#"+id).dialog("close");
}
function search(){
		var queryData = {
				
				secureAreaName : $("#secure_name").val().replace(/(^\s*)|(\s*$)/g, ""),
				
			};
		jqGridReload("gridTable", queryData);
}
// 安全区域删除
function deletes(secureAreaId,secureAreaName){
	//alert(secureAreaId);
		  if(secureAreaId){	
		  showTip(i18nShow('tip_delete_confirm'),function()	{
			 if(SecureAreaValidate(secureAreaId,secureAreaName)){
				$.post(ctx+"/network/secure/area/deleteArea.action",{
					"rmNwSecurePo.secureAreaId" : secureAreaId
				},function(data){ 
					search();
					showTip(i18nShow('tip_delete_success'));
				});
	        }else{
        		return false;
	        }
		});
	  }else{
		  var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id3){
				var rowData = $("#gridTable").getRowData(id3);
				list[list.length] = rowData.secureAreaId;
				})
		     showTip(i18nShow('tip_delete_confirm'),function(){
						if(SecureAreaValidates(list.join(","))){
							$.post(ctx+"/network/secure/area/deleteArea.action",{
								"rmNwSecurePo.secureAreaId" : list.join(",")
							},function(data){ 
								search();
								showTip(i18nShow('tip_delete_success'));
							});
				        }else{
				        	//showTip("无法删除!");
				        	return false;
				        }
					});
   }
}
//单删除验证
function SecureAreaValidate(saIds,secureAreaName){
	var fg = false;
    $.ajax({
		type : 'post',
		datatype : "json",
		url : ctx+"/network/secure/area/deleteCheckArea.action",
		async : false,
        data : {// 获得表单数据
        	"saIds" : saIds
		}, success : function(data) {
			var cs=""
			var reason="";
		    var result = "";
			for(var key in data){
				if(data[key] == "") {
					fg=true;return
		      }
			  var name = secureAreaName;
			 result += i18nShow('com_delete')+"[" + name + "]"+i18nShow('com_fail')+"（";
				var temps=data[key];
				var temp=temps.split(",");
				var rs=new Array(4);
				 reason="";
				for(var i=0;i<temp.length;i++){
					 if(temp[i]=="tier"){
						reason += "、"+i18nShow('rm_nw_secure_tier_use');
					}if(temp[i]=="cnet"){
						reason += "、"+i18nShow('rm_nw_secure_cclass_use');
					}if(temp[i]=="app"){
						reason += "、"+i18nShow('rm_nw_secure_du_use');
					}if(temp[i]=="pool"){
						reason += "、"+i18nShow('rm_nw_secure_pool_use');
					}
					cs+=reason;
					
					}
				result += reason.substring(1) + "）\n";
			}
			 showTip(result);
			if(cs.length>0){
				 fg =false;
			}else{
				fg=true;
			}
		},
		error : function() {
			showTip(i18nShow('tip_error'),null,"red");
		}
	});
     return fg;
}
function SecureAreaValidates(saIds) {
		var flag = false;
	    $.ajax({
			type : 'post',
			datatype : "json",
			url : ctx+"/network/secure/area/deleteCheckArea.action",
			async : false,
	        data : {// 获得表单数据
	        	"saIds" : saIds
			}, success : function(data) {
				var cs=""
				var reason="";
			    var result = "";
				for(var key in data){
					if(data[key] == "") {
						continue;
			      }
				  var name = getNameById(key);
				  result += i18nShow('com_delete')+"[" + name + "]"+i18nShow('com_fail')+"（";
					var temps=data[key];
					var temp=temps.split(",");
					var rs=new Array(4);
					 reason="";
					for(var i=0;i<temp.length;i++){
						if(temp[i]=="tier"){
							reason += "、"+i18nShow('rm_nw_secure_tier_use');
						}if(temp[i]=="cnet"){
							reason += "、"+i18nShow('rm_nw_secure_cclass_use');
						}if(temp[i]=="app"){
							reason += "、"+i18nShow('rm_nw_secure_du_use');
						}if(temp[i]=="pool"){
							reason += "、"+i18nShow('rm_nw_secure_pool_use');
						}
						cs+=reason;
						
						}
					result += reason.substring(1) + "）\n";
				}
				 showTip(result);
				if(cs.length>0){
					 flag =false;
				}else{
					flag=true;
				}
			},
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
		});
	     return flag;
}
		
function getNameById(key){
		var cccc = $("#gridTable").jqGrid('getGridParam','selarrrow');
		var result = "";
		$(cccc).each(function(index, rowNum){
			var rowData = $("#gridTable").getRowData(rowNum);
			if(rowData["secureAreaId"] == key) {
				result = rowData["secureAreaName"];
			}
			});
		return result;
}
		
var firstModel = true;
function showSecureTier(secureAreaId){
		$("#secureArea").val(secureAreaId);
		var queryData = {secureAreaId:secureAreaId};
		
		$("#secureTierDiv").dialog({
			autoOpen : true,
			modal : true,
			height :420,
			width : 800,
			title:i18nShow('rm_nw_secure_tier'),
			close:function(){
				 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
			}
		});
		if (firstModel) {
			firstModel = false;
			$("#serviceModelGridTable").jqGrid({
				url : ctx + "/network/secure/area/getSecureTierList.action",
				rownumbers : true,
				datatype : "json",
				postData : queryData,
				mtype : "post",
				height : 260,
				autowidth : true,
				multiselect:false,// 是否显示下拉框
				multiboxonly: false,
				colNames : [ 'secureTierId',i18nShow('rm_nw_secure_tier_name'), i18nShow('com_status'),i18nShow('com_operate')],
				colModel : [ {
					name : "secureTierId",
					index : "secureTierId",
					width: 120,
					sortable : true,
					hidden : true
				}, {
					name : "secureTierName",
					index : "secureTierName",
					sortable : true,
					width: 180,
					align : 'left',
					
				},{
					name : "isActive",
					index : "isActive",
					sortable : true,
					width: 180,
					align : 'left',
					formatter:function(cellVall,options,rowObject){
						if(cellVall=='Y'){// <span class="tip_green">已激活</span>
		// cellVall="启用";
							return "<span class='tip_green'>"+i18nShow('com_status_Y')+"</span>";
						}else{
		// cellVall="禁用";
							return "<span class='tip_red'>"+i18nShow('com_status_N')+"</span>";
						}
						return cellVall;
					}
				},{
					name : "option",
					index : "option",
					width : 180,
					sortable : false,
					align:'left',
					formatter : function(cellVall, options, rowObject) {
						var updateModelFlag = $('#updateModelFlag').val();
						var deleteModelFlag = $('#deleteModelFlag').val();
						var ret = "　　";
						if(updateModelFlag){
							ret +="<a  style='margin-right: 10px;margin-left: -25px;text-decoration:none;' href='javascript:#' title='' onclick=addSecureTier('" + rowObject.secureTierId + "') >"+i18nShow('com_update')+"</a>"
						}
						if(deleteModelFlag){
							ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteTier('" + rowObject.secureTierId + "','"+rowObject.secureTierName+"') >"+i18nShow('com_delete')+"</a>"
						}
						return ret;
						}
					}],
				viewrecords : true,
				sortname : "secureTierId",
				
				jsonReader : {
					root : "dataList",
					records : "record",
					page : "page",
					total : "total",
					repeatitems : false
				},
				hidegrid : false
			});
		}else {
			$("#serviceModelGridTable").jqGrid("setGridParam", {
				postData : queryData
			}).trigger("reloadGrid");
		}
		$(window).resize(function() {
			$("#serviceModelGridTable").setGridWidth($("#serviceModelGridDiv").width());
	    });
		$("#add_secure_Tier_Form").validate({
			rules:{
				"secureTierName":{required:true}
				
				},
			messages:{
				"secureTierName":{required:i18nShow('validate_data_required')}
				
			},
			 submitHandler:function(){
				 saveTier();
			 }
			 
		 });
			
}
function  saveTier(){
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/network/secure/area/saveSecureTier.action",
			async : false,
	       data : {// 获得表单数据
				
	    	   "rmNwSecureVo.secureTierId" : $("#secureTierId").val(),
	    	    "rmNwSecureVo.secureTierName" : $("#secureTierName").val(),
	    	    "rmNwSecureVo.secureAreaId" : $("#secureArea").val()
				},
			success : function(){
				closeView("add_secure_Tier_Div");
				searchTier();
			},
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
		});
}
function searchTier(){
		var queryData = {};
		jqGridReload("serviceModelGridTable", queryData);
}
function addSecureTier(secureTierId){
	 $("label.error").remove();//清楚提示信息
			$("#secureTierId").val("");
			$("#secureTierName").val("");
			if(secureTierId){
				// alert(secureTierId);
				 title=i18nShow('rm_nw_secure_tier_update');
					
					$.post(ctx + "/network/secure/area/viewSecureTier.action",{"rmNwSecureVo.secureTierId" : secureTierId},function(data){
						$("#secureTierId").val(data.secureTierId);
						$("#secureTierName").val(data.secureTierName);
						$("#add_secure_Tier_Div").dialog({
							width : 550,
							autoOpen : true,
							modal : true,
							height :200,
							title:title,
							//resizable:false // 控制div大小
						});
					});
			}else{
				title=i18nShow('rm_nw_secure_tier_save');
			$("#add_secure_Tier_Div").dialog({
				width : 350,
				autoOpen : true,
				modal : true,
				height : 200,
				title:title,
				//resizable:false
			});
			}
}
function saveSecureTierBtn(){
			$("#add_secure_Tier_Form").submit();
}
function deleteTier(secureTierId,secureTierName){
	  showTip(i18nShow('tip_delete_confirm'),function(){
			if(SecureTierValidate(secureTierId,secureTierName)){
				$.post(ctx+"/network/secure/area/deleteSecureTier.action",{
					"rmNwSecureVo.secureTierId" : secureTierId
				},function(data){ 
					searchTier();
					showTip(i18nShow('tip_delete_success'));
				});
	        }else{
	        	//showTip("无法删除成功!");
	        	return false;
	        }
		});
}
function SecureTierValidate(secureTierId,secureTierName){
	var flag1=false;
            $.ajax({
	    		type : 'post',
	    		datatype : "json",
	    		url : ctx+"/network/secure/area/viewSecureTierCclass.action",
	    		async : false,
	            data : {
	            	"rmNwSecureVo.secureTierId" : secureTierId
	    			},
	    		success : function(data){
	    			var reasons = "";
	    			var rs = "";
    			for(var key in data){
    				 var name = secureTierName;
    				  if(data[key] == "") {
		    				flag1=true;return
		    			}
		    		 rs += i18nShow('com_delete')+"[" + name + "]"+i18nShow('com_fail')+"（";
			    		var temps=data[key];
		   				 var temp=temps.split(",");
		    			 var tier=new Array(3);
			    		for(var i=0;i<temp.length;i++)	{
			    			if(temp[i]=="cnet"){
			    				rs += "、"+i18nShow('rm_nw_secure_cclass_use');
							}if(temp[i]=="app"){
								rs += "、"+i18nShow('rm_nw_secure_du_use');
							}if(temp[i]=="pool"){
								rs += "、"+i18nShow('rm_nw_secure_pool_use');
							}
		    				
			    				}
			    		rs += reasons.substring(1) + "）\n";
		    				}
		    			    showTip(rs);
		    			
		    				flag1=false;
	  			
	             },error : function() {
	    			showTip(i18nShow('tip_error'),null,"red");
	    		}
	    	});
	   return flag1;     
}
//清楚模糊查询内容
function cancelSecureArea(){
	$("#secure_name").val("");
}			
	
			
			
			