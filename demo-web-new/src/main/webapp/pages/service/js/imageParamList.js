$(function() {
	var queryData = {};
		$("#gridTable").jqGrid({
			url : ctx+"/cloud-service/image/queryImageAll.action", 
			rownumbers : true, 
			datatype : "json", 
			postData : queryData,
			mtype : "post", 
			height : heightTotal() + 95,
			autowidth : true, 
			//multiselect:true,
			colNames:['imageId',i18nShow('image_name'),'镜像存储路径','镜像存储URL',i18nShow('image_size'),i18nShow('image_disk_size'),i18nShow('image_username'),i18nShow('image_remark'),'密码','是否有效', "is_sync", "is_admin_user_entered",i18nShow('com_operate')],
			colModel : [ 
			            {name : "imageId",index : "imageId",width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "imageName",index : "imageName",width : 120,sortable : true,align : 'left',
			                formatter: function(cellValue,options,rowObject){
			    				return '<a href="javascript:;" style=" text-decoration:none" onclick="showView(\''+rowObject.imageId+'\')">'+rowObject.imageName+'</a>';
			                }},
			            {name : "imagePath",index : "imagePath",	width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "imageUrl",index : "imagePath",	width : 130,sortable : true,align : 'left',hidden:true},
			            {name : "imageSize",index : "imageSize",	width : 50,sortable : true,align : 'left'},
			            {name : "diskCapacity",index : "diskCapacity",	width : 50,sortable : true,align : 'left'},
			            {name : "manager",index : "manager",	width : 60,sortable : false,align : 'left'},
			            {name : "remark",index : "remark",	width : 200,sortable : false,align : 'left'},
			            {name : "password",index : "password",	width : 120,sortable : false,align : 'left',hidden:true},
			            {name : "isActive",index : "isActive",	width : 120,sortable : false,align : 'left',hidden:true},
                {name : "isSync",index : "isSync", hidden:true},
                {name : "isAdminUserEntered",index : "isAdminUserEntered", hidden:true},
			            {name :"option",index:"option",width:120,sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateFlag =  $("#updateFlag").val();
								 var deleteFlag = $("#deleteFlag").val();
								 var softWareFlag = $("#softWareFlag").val();
							
								 if(updateFlag == "1"){
									 result += "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.imageId+"','" + rowObject.isSync + "') >"+i18nShow('com_update')+"</a>";
								 }
								 if(deleteFlag == "1" && rowObject.isSync != 'Y'){
									 result += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.imageId+"') >"+i18nShow('com_delete')+"</a>";
								 }
								 var s = "";
								 if(softWareFlag == "1"){
									 s += "<option value='1' >"+i18nShow('image_soft_manage')+"</option >";
								 }
								 if(softWareFlag == "1"){
									 result += "<select onchange=\"selMeched(this,'"+rowObject.imageId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;
								 } 
								 
								 
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "imageId",
			rowNum : 10,
			rowList : [10, 20, 50, 100 ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#gridPager",
			hidegrid : false
		});
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridTableDiv").width());
			$("#gridTable").setGridHeight(heightTotal()+ 95);
	    });
		initSoftWareTable({imageId:"null"});
		initSoftWareShow({imageId:"null"});
		softWareSelect();
		$("#updateForm").validate({
			rules: {
				"cloudImage.imageName": {  required:true,
										   remote:{                
								               type:"POST",
								               url:ctx+"/cloud-service/image/checkCloudImagesfAct.action",
								               data:{
								            	   "cloudImage.imageId":function(){return $("#imageId").val()},"cloudImage.imageName":function(){return $("#imageName").val()}}
								               } 
								            },
				"cloudImage.imagePath": "required",
				"cloudImage.imageUrl": {   required:true,
										   remote:{                
								               type:"POST",
								               url:ctx+"/cloud-service/image/checkCloudImagesfAct.action",
								               data:{
								            	   "cloudImage.imageId":function(){return $("#imageId").val()},"cloudImage.imageUrl":function(){return $("#imageUrl").val()}}
								               } 
								            },
				"cloudImage.imageSize": {
					number:true ,
					required:true,
					min:0
				},
				"cloudImage.diskCapacity": {
					number:true ,
					required:true,
					min:0
				},
				"cloudImage.manager": "required",
				"cloudImage.password": "required",
				"cloudImage.manageType": "required",
				"cloudImage.imageType": "required"
			},
			messages: {
				"cloudImage.imageName": {required:i18nShow('validate_image_name_required'),remote:i18nShow('validate_image_name_remote')},
				"cloudImage.imagePath": i18nShow('validate_image_path_required'),
				"cloudImage.imageUrl": {required:i18nShow('validate_image_url_required'),remote:i18nShow('validate_image_url_remote')},
				"cloudImage.imageSize": {
					number:i18nShow('validate_image_number') ,
					required:i18nShow('validate_image_size_required'),
					min:i18nShow('validate_image_size_0')
				},
				"cloudImage.diskCapacity": {
					number:i18nShow('validate_image_number') ,
					required:i18nShow('validate_image_disk_required'),
					min:i18nShow('validate_image_size_0')
				},
				"cloudImage.manager": i18nShow('validate_image_username_required'),
				"cloudImage.password": i18nShow('validate_image_pwd_required'),
				"cloudImage.manageType": i18nShow('validate_image_service_required'),
				"cloudImage.imageType": i18nShow('validate_image_image_required')
			},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
		
		$("#softWareForm").validate({
			rules: {
				"softWareSelectId":{required:true,
								   remote:{                
					               type:"POST",
					               url:ctx+"/cloud-service/image/checkCloudImageSoftWareRefAct.action",
					               data:{
					            	   "imageSoftWareRef.imageSoftwareId":function(){return $("#imageSoftwareId").val()},"imageSoftWareRef.imageId":function(){return $("#softWareImageId").val()},"imageSoftWareRef.softWareVerId":function(){return $("#softWareVerSelectId").val()}
					               } 
					              } 
					            },
				"softWareVerName": "required"
			},
			messages: {
				"softWareSelectId":{required:i18nShow('validate_image_soft_required'),remote:i18nShow('validate_image_soft_remote')},
				"softWareVerName":i18nShow('validate_image_soft_version_required')
			},
			submitHandler: function() {
				saveOrUpdateSoftVer();
			}
		});
		
	});	

function selMeched(element,id){
	var val = element.value;
	if(val == "1"){
		showSoftWare(id);
	}
}
function clearAll(){
	$("#selectImageName").val("");
}


function initSoftWareTable(queryData){
	$("#softWareTable").jqGrid(	{
		url : ctx+"/cloud-service/image/findSoftsByImageIdAct.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : 200,
		autowidth : true,
		colNames : [ 'imageSoftwareId','softWareVerSelectId', i18nShow('image_soft_name'),"softWareVerId",i18nShow('image_soft_version'),'是否有效',i18nShow('com_operate') ],
		colModel : [
				{ name : "imageSoftwareId", index : "imageSoftwareId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "softwareId", index : "softwareId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "softwareName", index : "softwareName", sortable : false,   align : 'left' },
				{ name : "softwareVerId", index : "softwareVerId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "verName", index : "verName", sortable : false,   align : 'left' },
				{ name : "isActive", index : "isActive", sortable : false,   align : 'left' ,hidden:true },
				{name : "option", index : "option", sortable : false, align : "left",
					formatter:function(cellVall,options,rowObject){
						 var result = "  ";
						 var updateBut = "<a  style='margin-right: 10px;margin-left: -10px;text-decoration:none;' href='javascript:#' title=''  onclick=updSoftVer('"+options.rowId+"')>"+i18nShow('com_update')+"</a>";
						 var deleteBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=delSoftVer('"+rowObject.imageSoftwareId+"')>"+i18nShow('com_delete')+"</a>";
						 var updateSoftWareFlag =  $("#updateSoftWareFlag").val();
						 var deleteSoftWareFlag = $("#deleteSoftWareFlag").val();
						 if(updateSoftWareFlag == "1"){
							 result += updateBut;
						 }
						 if(deleteSoftWareFlag == "1"){
							 result += deleteBut;
						 }
						return result;
					}
	            } ],
		viewrecords : true,
		sortname : "attrKey",
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		hidegrid : false
	});
	$.getJSON(ctx+"/cloud-service/image/showSoftWareAll.action", {"_":new Date().getTime()}, function(data) {
		$("#softWareSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
		$("#softWareVerSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
		$.ajaxSettings.async = false;
		$.each(data, function(i,item) {
			$("#softWareSelectId").append("<option value='" + item.softwareId + "'>" + item.softwareName + "</option>");
		});
	});
}

function initSoftWareShow(queryData){
	$("#showSoftWareTable").jqGrid(	{
		url : ctx+"/cloud-service/image/findSoftsByImageIdAct.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : 130,
		autowidth : true,
		colNames : [ 'imageSoftwareId','softWareVerSelectId', i18nShow('image_soft_name'),"softWareVerId",i18nShow('image_soft_version') ],
		colModel : [
		        { name : "imageSoftwareId", index : "imageSoftwareId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "softwareId", index : "softwareId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "softwareName", index : "softwareName", sortable : false, width:233,  align : 'left' },
				{ name : "softwareVerId", index : "softwareVerId", sortable : false,   align : 'left' ,hidden:true},
				{ name : "verName", index : "verName", sortable : false, width:233,   align : 'left' } ],
		viewrecords : true,
		sortname : "attrKey",
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		hidegrid : false
	});
}
//软件管理
function showSoftWare(objectId){
	$("#softWareVerDiv").dialog({
		autoOpen : true,
		modal:true,
		height:400,
		title:i18nShow('image_soft_manage'),
		width:630,
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
	$("#softWareImageId").val(objectId);
	$("#softWareTable").jqGrid("setGridParam", { postData : {imageId:objectId} }).trigger("reloadGrid");
	
	//自适应宽度
	$("#softWareTable").setGridWidth($("#softWareTableDiv").width());
	$(window).resize(function() {
		$("#softWareTable").setGridWidth($("#softWareTableDiv").width());
    });
}

function showView(objectId){
	$("#showDiv").dialog({
		autoOpen : true,
		modal:true,
		height:530,
		title:i18nShow('image_view'),
		width:950
	});
	
	$.post(ctx+"/cloud-service/image/showImage.action",{"cloudImage.imageId" : objectId},function(data){
		$("#imageNameLab").val(data.imageName);
		$("#imagePathLab").val(data.imagePath);
		$("#imageUrlLab").val(data.imageUrl);
		$("#imageSizeLab").val(data.imageSize);
		$("#remarkLab").val(data.remark);
		$("#managerLab").val(data.manager);
		$("#diskCapacity").val(data.diskCapacity);
	});
	$("#showSoftWareTable").jqGrid("setGridParam", { postData : {imageId:objectId} }).trigger("reloadGrid");
	
	//自适应宽度
	$("#showSoftWareTable").setGridWidth($("#showSoftWareDiv").width());
	$(window).resize(function() {
		$("#showSoftWareTable").setGridWidth($("#showSoftWareDiv").width());
    });
}

/**
 * 点击修改时调用的方法
 * @param objectId
 * @return
 */
function showUpdateDiv(objectId, isSync){
		emptyValue("imageName");
		emptyValue("imageUrl");
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:400,
				title:i18nShow('image_update'),
				width:670
		})
		$.post(ctx+"/cloud-service/image/showImage.action",{"cloudImage.imageId" : objectId},function(data){
			$("#imageId").val(data.imageId);
			$("#imageName").val(data.imageName);
			$("#imagePath").val(data.imagePath);
			$("#imageUrl").val(data.imageUrl);
			$("#imageSize").val(data.imageSize);
			$("#udiskCapacity").val(data.diskCapacity);
			$("#remark").val(data.remark);
			$("#manager").val(data.manager);
			$("#password").val(data.password);
			$("#isActive").val(data.isActive);
			$("#manageType").val(data.manageType);
			//$("#imageType").val(data.imageType);
			if($("#manageType").val()=='OPENSTACK'){
				$("#imageType").val(data.imageType);
				$("#spanImageType").css("display","block");
			}else{
				$("#spanImageType").css("display","none");
			}
			$("#method").val("update");
			// 当镜像为openstack同步过来的镜像时，禁用一部分输入内容
			if(isSync == 'Y') {
                $('#imageName').attr("readonly","readonly");
                $('#imagePath').attr("readonly","readonly");
                $('#imageUrl').attr("readonly","readonly");
                $('#imageSize').attr("readonly","readonly");
                $('#udiskCapacity').attr("readonly","readonly");
                $('#manageType').attr("disabled","disabled");
                $('#remark').attr("readonly","readonly");
                $('#imageType').attr("disabled","disabled");
			}
			else {
                $('#imageName').removeAttr("readonly");
                $('#imagePath').removeAttr("readonly");
                $('#imageUrl').removeAttr("readonly");
                $('#imageSize').removeAttr("readonly");
                $('#udiskCapacity').removeAttr("readonly");
                $('#manageType').removeAttr("disabled");
                $('#remark').removeAttr("readonly");
                $('#imageType').removeAttr("disabled");
			}
		});
//		$("#softWareTable").jqGrid("setGridParam", { postData : {imageId:objectId} }).trigger("reloadGrid");
	}

	/**
	 * 点击新建时调用的方法
	 * @param objectId
	 * @return
	 */
	function showCreateDiv(){
		emptyValue("imageName");
		emptyValue("imageUrl");
		$("#manageType").val("");
		$("#imageType").val("");
		$("#imageUrl").val("");
		$("#remark").val("");
		
		$("#spanImageType").css("display","none");
		$("label.error").remove();
		clearTab();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:400,
				title:i18nShow('image_save'),
				width:670
		})
		$("#method").val("create");
	}
	/**
	 * 新增页面点击保存时调用
	 * @return
	 */
	function saveOrUpdateBtn(){
		$("#updateForm").submit();  
	}
	function updateOrSaveData(){
		var method = $("#method").val();
//		var imageId = $("#imageId").val();
//		var imageName = $("#imageName").val();
//		var imagePath = $("#imagePath").val();
//		var imageUrl = $("#imageUrl").val();
//		var imageSize = $("#imageSize").val();
//		var remark = $("#remark").val();
//		var manager = $("#manager").val();
//		var password = $("#password").val();
//		var isActive = $("#isActive").val();
//		var verSelect = $("#verSelect").val();
		
//		var datas = [[imageName,"镜像名称"] ,[imagePath,"镜像存储路径"],[imageUrl,"镜像存储URL"],[imageSize,"镜像大小"],[remark,"镜像描述"],[manager,"用户名"],[password,"密码"]];
		var url;
		if(method=="update"){
			url= ctx+"/cloud-service/image/updateImage.action"
		}else{
			url= ctx+"/cloud-service/image/insertImage.action"
		}
//		var data = {'cloudImage.imageId':imageId,'cloudImage.imageName':imageName,'cloudImage.imagePath':imagePath,'cloudImage.imageUrl':imageUrl,'cloudImage.imageSize':imageSize,'cloudImage.remark':remark,'cloudImage.manager':manager,'cloudImage.password':password,'cloudImage.isActive':isActive,"cloudImage.cloudImageSoftWareRefs[0].softWareVerId":verSelect};
		var data = $("#updateForm").serialize();
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			/*beforeSend:(function(data){
				return validate(datas);
			}),*/
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
				$("#updateDiv").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid");
//				saveSoftVer(data.imageId);
				showTip(i18nShow('tip_save_success'));
				closeTip();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	showTip(i18nShow('tip_save_fail'));
			} 
		});
	}
	
	function saveSoftVer(imageId){
		var ids = $("#softWareTable").jqGrid('getDataIDs');
		var softWareVerId;
		if(ids.length==0){
			$("#updateDiv").dialog("close");
			$("#gridTable").jqGrid().trigger("reloadGrid");
			closeTip();
		}else{
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#softWareTable").getRowData(id);
				list[list.length] = rowData.softwareVerId;
				});
			softWareVerId=list.join(",")
		}
		
		
		var data = {"imageSoftWareRef.imageId":imageId,"imageSoftWareRef.softWareVerId":softWareVerId};
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/cloud-service/image/saveCloudImageSoftWareRefAct.action",
			async : false,
			data:data,
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
				$("#updateDiv").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid");
				showTip(i18nShow('tip_save_success'));
				closeTip();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	showTip(i18nShow('tip_save_fail'));
			} 
		});
	}
	/**
	 * 下拉框2级联动
	 * @return
	 */
	function softWareSelect(){
		$("#softWareSelectId").change(function(){
			if($("#softWareSelectId").val()){
				$.getJSON(ctx+"/cloud-service/image/showSoftWareVerAllBySoftWareId.action", {"softWareSelect":$("#softWareSelectId").val(),"_":new Date().getTime()}, function(data) {
					$("#softWareVerSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
					$.each(data, function(i,item) {
						$("#softWareVerSelectId").append("<option value='" + item.softwareVerId + "'>" + item.verName + "</option>");
					});
				});
			}else{
				$("#softWareVerSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
			}
			
		})

	}
	function createSoftWare(){
		emptyValue("softWareVerSelectId");
		$("label.error").remove();
		$("#softWareDiv").dialog({
				autoOpen : true,
				modal:true,
				height:300,
				title:i18nShow('image_soft_ware_save'),
				width:400
		});
		$("#softWareMethod").val("create");
		$("#imageSoftwareId").val("");
		selectByValue("softWareSelectId","");
		var id = $("#softWareTable").jqGrid('getDataIDs').length+1;
		$("#softWareRowId").val(id);
		$("#softWareVerSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
	}
	function addSoftVer(){
		var id =$("#softWareRowId").val();// $("#softWareTable").jqGrid('getDataIDs').length+1;
		var method = $("#softWareMethod").val();
		var dataObj ={'softwareId':$(
				"#softWareSelectId").val(),
				'softwareName':$("#softWareSelectId").find("option:selected").text(), 
				'softwareVerId':$("#softWareVerSelectId").val(),
				'verName':$("#softWareVerSelectId").find("option:selected").text(),
				'option':""};
		if("update" == method){
			$("#softWareTable").jqGrid("setRowData", id, dataObj);
		}else{
			$("#softWareTable").jqGrid("addRowData", id, dataObj);
		}
		
		$("#softWareDiv").dialog("close");
	}
	
	//更新软件
	function saveOrUpdateSoftVer(){
		var data = {"imageSoftWareRef.imageId":$("#softWareImageId").val(),"imageSoftWareRef.softWareVerId":$("#softWareVerSelectId").val(),"imageSoftWareRef.imageSoftwareId":$("#imageSoftwareId").val(),"imageSoftWareRef.isActive":$("#softWareIsActive").val()};
		var method = $("#softWareMethod").val();
		var url;
		if("update" == method){
			url=ctx+"/cloud-service/image/updateCloudImageSoftWareRefAct.action";
		}else{
			url=ctx+"/cloud-service/image/saveCloudImageSoftWareRefAct.action";
		}
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
				$("#softWareDiv").dialog("close");
				$("#softWareTable").jqGrid().trigger("reloadGrid");
				showTip(i18nShow('tip_save_success'));
				closeTip();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	showTip(i18nShow('tip_save_fail'));
			} 
		});
	}
	
	
	function addSoftVerBtn(){
		$("#softWareForm").submit();
	}
	
	function delSoftVer(id){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/cloud-service/image/deleteCloudImageSoftWareRefAct.action",
				async : false,
				data:{"imageSoftWareRef.imageSoftwareId":id},
				success:(function(data){
					showTip(i18nShow('tip_delete_success'));
					$("#softWareTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('tip_delete_fail'));
				} 
			});
		});
	}
	
	function updSoftVer(id){
		emptyValue("softWareVerSelectId");
		$("label.error").remove();
		$("#softWareDiv").dialog({
				autoOpen : true,
				modal:true,
				height:280,
				title:'更新软件',
				width:500,
				//resizable:false
		});
		var rowData = $("#softWareTable").jqGrid("getRowData", id);
		$.getJSON(ctx+"/cloud-service/image/showSoftWareVerAllBySoftWareId.action", {"softWareSelect":rowData.softwareId,"_":new Date().getTime()}, function(data) {
			$("#softWareVerSelectId").html("<option value=''>--"+i18nShow('com_select_defalt')+"--</option>");
			$.each(data, function(i,item) {
				$("#softWareVerSelectId").append("<option value='" + item.softwareVerId + "'>" + item.verName + "</option>");
			});
		});
		selectByValue("softWareSelectId",rowData.softwareId);
		selectByValue("softWareVerSelectId",rowData.softwareVerId);
		$("#softWareMethod").val("update");
		$("#softWareRowId").val(id);
		$("#imageSoftwareId").val(rowData.imageSoftwareId);
		$("#softWareIsActive").val(rowData.isActive);
	}
	
	function closeSoftVer(){
		$("#softWareDiv").dialog("close");
	}
	
	function deleteData( dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/cloud-service/image/deleteImage.action",
					async : false,
					data:{"cloudImage.imageId":dataId},
					success:(function(data){
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
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
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#gridTable").getRowData(id);
				list[list.length] = rowData.imageId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/cloud-service/image/deleteImage.action",
					async : false,
					data:{"cloudImage.imageId":list.join(",")},
					success:(function(data){
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		};

	}
	function clearTab(){
		 var tab = document.getElementById("updateTab") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function closeView(){
		$("#updateDiv").dialog("close");
	}

	function verselect(a){
			var num = "";
			if(a == null){
				var softNum = $(this).parent().eq(0).attr("id");
				num = softNum.split("_");
				num = num[1] ;
			}else{
				num = a ;
			}
		
			$.getJSON(ctx+"/cloud-service/image/showSoftWareVerAllBySoftWareId.action", {"softWareSelect":$("#softWareSelect_"+num+"").val(),"_":new Date().getTime()}, function(data) {
				$("#verSelect_"+num+"").html("");
				$("#verSelect_"+num+"").append("<option value=''> "+i18nShow('com_select_defalt')+"--- </option>");
				$.each(data, function(i,item) {
					$("#verSelect_"+num+"").append("<option value='" + item.softwareVerId + "'>" + item.verName + "</option>");
				});
			});
	}
	
	function search(){
		var queryData = {
				selectImageName : $("#selectImageName").val().replace(/(^\s*)|(\s*$)/g, "")
			};
		jqGridReload("gridTable", queryData);
	}
	
$(function() {
	$("#manageType").change(function(){
		var val=this.value;
		if(val == 'OPENSTACK'){
			$("#spanImageType").css("display","block");
		}else{
			$("#spanImageType").css("display","none");
		}
	});
		
});
function changePassword(){
	var type = $("#password").attr("type");
	var val = $("#password").val();
	if(type == "password"){
		$("#password").remove();
		var text = '<input class="textInput" type="text" id="password" name="cloudImage.password" value="'+val+'"/>'
		$("#passwordDiv").prepend(text);
		$("#passwordDiv img").attr('src',ctx+'/images/eyeBlock.jpg');
		
	}else{
		$("#password").remove();
		var text = '<input class="textInput" type="password" id="password" name="cloudImage.password" value="'+val+'"/>'
		$("#passwordDiv").prepend(text);
		$("#passwordDiv img").attr('src',ctx+'/images/eyeClose.jpg');
	}
}