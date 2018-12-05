function heightTotal(){
	  var heightTotala =$(window).height()-230;
	  return heightTotala;
};
//jqGrid表格自适应高度


function formatTime(ns) {
	if (ns) {
		var d = new Date(parseInt(ns.time + ""));
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();
		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	}
}
function heightTotal(){
	  var heightTotala =$(window).height()-280;
	  return heightTotala;
};
function jqGridReload(tableId, params) {
	var jTable = $("#" + tableId);
	var postData = jTable.jqGrid("getGridParam", "postData");
	params.reloadFlag = "true";
	$.extend(postData, params);
	jTable.jqGrid("setGridParam", {
		datatype : "json",
		search : true,
		mtype : "post"
	}).trigger("reloadGrid");
	
	params.reloadFlag="false";
	$.extend(postData, params);
	$("#gridTable").jqGrid("setGridParam", {datatype : "json",
		search : true,
		mtype : "post"});
	
}
function i18nShow(key) {
	var value = '';
	if(_language == 'zh_CN' || _language == 'zh') {
		value = i18n_zh_CN[key];
	} else if(_language == 'en_US' || _language == 'en') {
		value = i18n_en_US[key];
	} else {
		// alert(_language);
		value = i18n_zh_CN[key];
	}
	if(!value) {
		value = '';
	}
	return value;
}