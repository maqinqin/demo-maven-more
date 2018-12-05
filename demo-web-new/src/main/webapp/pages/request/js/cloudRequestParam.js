// 查看所在行的服务器角色申请云服务
function detailFromTable(id, du_id, rrinfoId) {
	$("#duId_attr").val(du_id);
	// 弹出填写参数页面
	$("#select_attr_div").dialog({
		autoOpen : true,
		modal : true,
		width : 980
	});
	$("#select_attr_div").dialog("option", "title", i18nShow('my_req_det_view_para'));
	$("#pre_attr").hide();
	$("#save_service").hide();
	if (actionType == 'new' || actionType == 'update') {
		$("#save_attr_btn").show();
		$("#cancel_attr_btn").show();
	} else if (actionType == 'detail'||actionType == 'view') {
		$("#save_attr_btn").hide();
		$("#cancel_attr_btn").hide();
	}
	// 清空参数表
	$("#attrTable").clearGridData();
	
	var attrList = new Array();
	// 获取服务申请详细信息
     $.ajax({  
         type : "post",  
         url : ctx + '/request/base/getBmSrAttr.action',  
         data : {"rrinfoId":rrinfoId},  
         async : false,  
         success : function(data){  
            attrList = data;
         }  
       }); 
	// 往参数表中插入记录
	for ( var j = 0; j < attrList.length; j++) {
		var jsobj = {
			attrValId : attrList[j].srAttrValId,
			rrinfoId : attrList[j].rrinfoId,
			attrTypeCode : attrList[j].attrType,
			remark : attrList[j].remark,
			attrId : attrList[j].attrId,
			attrName : attrList[j].attrName,
			attrCname : attrList[j].attrCname,
			attrClass : attrList[j].attrClass,
			attrType : attrList[j].attrType,
			isRequire : attrList[j].isRequire,
			defVal : attrList[j].defVal,
			attrSelList : attrList[j].attrSelList,
			inputVal : attrList[j].attrValue
		};
		// 往参数表中插入记录
		$("#attrTable").jqGrid("addRowData", j, jsobj);
	}
}

// 读取用户需要填写的参数
function attrTableInit() {
	$("#attrTable").jqGrid().GridUnload("attrTable");		
	$("#attrTable").jqGrid(
			{
				url :'',
				datatype : "json",
				height : 300,
				width : 900,
				colNames : [ '参数值ID', '资源请求ID', '参数ID', '数据类型', '参数名称英文', i18nShow('cloud_service_attr_name'),i18nShow('my_req_det_para_class'), i18nShow('my_req_det_para_need'), i18nShow('my_req_det_para_defalt'), i18nShow('my_req_det_para_radio'), i18nShow('my_req_det_para_L'),i18nShow('my_req_det_para_L1'),i18nShow('my_req_det_para_fill'), i18nShow('my_req_det_para_rule') ],
				colModel : [
				{
					name : 'attrValId',
					index : 'attrValId',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'rrinfoId',
					index : 'rrinfoId',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrId',
					index : 'attrId',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrTypeCode',
					index : 'attrTypeCode',
					hidden : true,
					width : 5,
					align : "left"
				},
				
				{
					name : 'attrName',
					index : 'attrName',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrCname',
					index : 'attrCname',
					sortable : false,
					width : 25,
					align : "left"
				},{
					name : 'attrClass',
					index : 'attrClass',
					sortable : false,
					hidden : false,
					width : 10,
					align : "left"
				},{
					name : 'isRequire',
					index : 'isRequire',
					sortable : false,
					hidden : true,
					width : 5,
					align : "left"
				},{
					name : 'defVal',
					sortable : false,
					hidden : true,
					index : 'defVal',
					width : 10,
					align : "left"
				},{
					name : 'attrSelList',
					sortable : false,
					hidden : true,
					index : 'attrSelList',
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return JSON.stringify(cellValue);
					}
				},{
					name : 'attrType',
					sortable : false,
					hidden : true,
					index : 'attrType',
					width : 10,
					align : "left"
		      	},{
					name : 'Classes',
					index : 'Classes',
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						      var className= "";
						        if(rowObject.attrType=='COMPUTE') {className=i18nShow('my_req_det_para_com');}else
				                if (rowObject.attrType=='NET') {className=i18nShow('my_req_det_para_net');}else
				        	    if(rowObject.attrType=='STORAGE') {className=i18nShow('my_req_det_para_stor');}
						
						return  className;
					}
				},{
					name : 'inputVal',
					index : 'inputVal',
					sortable : false,
					width : 60,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						if (actionType == 'detail') {
							return retValInput(cellValue, rowObject, true);
						}
						  if(editclass==rowObject.attrClass||editclass=='0'){
						      return retValInput(cellValue, rowObject, false);
						   }
						  else {
							  return retValInput(cellValue, rowObject, true);
							  
						  }
						
						
						
					}
				},{
					name : 'remark',
					index : 'remark',
					sortable : false,
					align : "left",
					width : 60,
					formatter : function(cellValue, options, rowObject) {
						if (cellValue != undefined) {
							//return cellValue;
							return "<textarea id='textarea_"+options.rowId+"'  style='width: 300px;height:35px;overflow-y:auto' cols='60' rows='1' disabled='disabled' class='textInput'>" + cellValue + "</textarea>";
						} else {
							return '';
						}
					}
				}],
				rowNum : 100,
				rowList : [ 100, 20, 30, 50 ],
				sortname : 'attrId',
				sortorder : "asc",
				// pager : '#pageNav_attr',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}

			});
	// setGridSize("setTable", $("#searchDiv").width()-1, 300);
}

// 输入区域构建
function retValInput(cellValue, rowObject, isread,type) {
	var cValue = ""; // 单元格数值
	var restStr = "";// 返回数值
	var readonly = "";// 是否只读
	var disabled = "";
	var inputType=rowObject.attrClass;
	var clickfun="onClick=checkDate(this,'"+inputType+"')";//点击调用方法
	inputValWidth = '300';
	
	if (isread) {
		readonly = "readonly";
		disabled = "disabled";
		clickfun="";
	}
	// 当前没有值获默认数值
	if (cellValue == undefined) {
		if (rowObject.defVal != undefined) {
			cValue = rowObject.defVal;
		}
	} else {
		cValue = cellValue;
	}
	if (inputType == 'TEXT') { // input输入
	   if(cValue.length <= 100){
		 //restStr = "<input type='text'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:180px'" + readonly + ">";
		   restStr = "<textarea style='width:"+inputValWidth+"px' cols='50' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	   }else{
	     //restStr = "<textarea cols='34' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	      restStr = "<textarea style='width:"+inputValWidth+"px' cols='110' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	   }
	} 
	//else if (inputType == 'A') {  // Textarea 输入域
	//	restStr = "<textarea cols='34' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	//} 
	else if (inputType == 'SELECT') {  // select 下拉框
		restStr = "<select style='width:"+inputValWidth+"px' id='attr_" + rowObject.attrId + "'  class='form_select'  " + disabled + " >";
		var attrsel = rowObject.attrSelList;
		for ( var t = 0; t < attrsel.length; t++) {
			if (cValue == attrsel[t].attrValue) { // 选中数值
				restStr = restStr + "<option  selected=true value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			} else {
				restStr = restStr + "<option value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			}
		}
		restStr = restStr + "</select>";
	} else if(inputType == 'N') { // 数值
		restStr = "<input type='text'   onchange=checkNum(this,'"+rowObject.attrCname+"') id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:180px'" + readonly + ">";
	} else if(inputType == 'D') { //日期  
		restStr = "<input type='text'   readonly='readonly'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:180px'"+clickfun+" >";
	} else {
		restStr = "<input type='text'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:180px'>";
	}
	return restStr;
}