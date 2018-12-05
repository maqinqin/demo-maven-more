
$(function() {
	var ruleType = $('#ruleType').val();
	var url = ctx + "/resmgt-storage/pool/findSePoolRuleByTypeAct.action?ruleType="
			+ ruleType;
	jQuery.post(url, {}, generateTable, 'json');
});

// 生成数据表格
function generateTable(data) {
	var ruleType = $('#ruleType').val();
	if (ruleType == 'AVAILABILITY_TYPE') {
		generateAvailabilityTable(data);
	}else if(ruleType == 'PERFORMANCE_TYPE'){
		generatePerformanceTable(data);
	}else if(ruleType=='NAS_SERVICE_LEVEL'){
		generateNasTable(data);
	}
}

function changeInfo(){
	var ruleType = $('#ruleType').val();
	if (ruleType == 'AVAILABILITY_TYPE') {
		$('select').attr("disabled",false);
	}else if(ruleType == 'PERFORMANCE_TYPE'){
		$('input[type=text]').attr("disabled",false);
	}else if(ruleType=='NAS_SERVICE_LEVEL'){
		$('select').attr("disabled",false);
	}
}

function disableInfo(){
	var ruleType = $('#ruleType').val();
	if (ruleType == 'AVAILABILITY_TYPE') {
		$('select').attr("disabled",true);
	}else if(ruleType == 'PERFORMANCE_TYPE'){
		$('input[type=text]').attr("disabled",true);
	}else if(ruleType=='NAS_SERVICE_LEVEL'){
		$('select').attr("disabled",true);
	}
}

function generateAvailabilityTable(data) {
	var row1 = data[0].row1;
	var row2 = data[0].row2;
	var row3 = data[0].row3;
	var row4 = data[0].row4;

	var row_array = new Array(row1, row2, row3, row4);
	for ( var m = 0; m < row_array.length; m++) {
		var row = row_array[m];
		for ( var i = 0; i < row.length; i++) {
			var cellId = row[i].cellId;
			var cellValue = row[i].cellValue;
			var cellSelect = document.getElementById(cellId);
			for ( var j = 0; j < cellSelect.options.length; j++) {
				if (cellSelect.options[j].value == cellValue) {
					cellSelect.options[j].selected = true;
				}
			}
		}
	}
	disableInfo();
}

function getDivJsonString(){
	var jsonObj = [];
	$('#ruleDiv').each(function(){
        var that =jQuery(this);   
        var o ={};   
        that.find('input,select').each(function(){   
            var el = jQuery(this);   
            var fieldFullName = el.attr('id');   
            o[fieldFullName] = el.val();   
        }); 

        jsonObj.push(o);
	});
	var jsonString = JSON.stringify(jsonObj);

	return jsonString;
}

function saveRule() {
	/*var obj = document.getElementsByTagName("select");
	var jsonObj = [];
	for ( var i = 0; i < obj.length; i++) {
		var o = {};
		var cellId = obj[i].id;
		var index = obj[i].selectedIndex; // 选中索引
		var cellValue = obj[i].options[index].value; // 选中值
		o["cellId"] = cellId;
		o["cellValue"] = cellValue;
		jsonObj.push(o);
	}
	var jsonString = JSON.stringify(jsonObj);*/
//	var myurl = ctx+'/resmgt-storage/pool/saveSePoolRuleCellAct.action?ruleType='+ruleType+"&sePoolRuleCells="+jsonString;
// 	 $.ajax( {  
//	     url :myurl,
//	     async : false,
//	     type :"POST",
//	     dataType: "json",
//		 data :{},
//	    // timeout :200000,// 设置请求超时时间（毫秒）。  
//	     error : callBackError,
//	     success : callBackSuccess
//   });
 	 

	var jsonString = getDivJsonString();
	var ruleType = $('#ruleType').val();
 	showTip("确定保存修改吗?",function(){
 		$.ajax({
 			type:'post',
 			datatype : "json",
 			url:ctx+'/resmgt-storage/pool/saveSePoolRuleCellAct.action?ruleType='+ruleType+"&sePoolRuleCells="+jsonString,
 			async : false,
 			data:{},
 			success:(function(data){
 			   showTip(i18nShow('zh4'));
 				disableInfo();
 			}),
 			error:function(XMLHttpRequest, textStatus, errorThrown){
 			   showTip(i18nShow('zh6'));
 			} 
 		});
 	});
}

function generatePerformanceTable(data) {
	var row1 = data[0].row1;
	var row2 = data[0].row2;
	var row3 = data[0].row3;

	var row_array = new Array(row1, row2, row3);
	for ( var m = 0; m < row_array.length; m++) {
		var row = row_array[m];
		for ( var i = 0; i < row.length; i++) {
			var cellId = row[i].cellId;
			var cellValue = row[i].cellValue;
			var cell = document.getElementById(cellId);
			cell.value = cellValue;
		}
	}
	disableInfo();
}

function generateNasTable(data) {
	var row1 = data[0].row1;
	var row2 = data[0].row2;
	var row3 = data[0].row3;

	var row_array = new Array(row1, row2, row3);
	for ( var m = 0; m < row_array.length; m++) {
		var row = row_array[m];
		for ( var i = 0; i < row.length; i++) {
			var cellIdOne = row[i].cellId+'_CONDITIONONE';
			var cellOneValue = row[i].conditionOne;
			var callIdTwo = row[i].cellId+'_CONDITIONTWO';
			var cellTwoValue = row[i].conditionTwo;
			
			var cellOneSelect = document.getElementById(cellIdOne);
			for ( var j = 0; j < cellOneSelect.options.length; j++) {
				if (cellOneSelect.options[j].value == cellOneValue) {
					cellOneSelect.options[j].selected = true;
				}
			}
			
			var cellTwoSelect = document.getElementById(callIdTwo);
			for ( var j = 0; j < cellTwoSelect.options.length; j++) {
				if (cellTwoSelect.options[j].value == cellTwoValue) {
					cellTwoSelect.options[j].selected = true;
				}
			}
		}
	}
	disableInfo();
}

