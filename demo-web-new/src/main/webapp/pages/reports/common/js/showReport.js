var completeSelect = function(){
	var i = -1;
	var n = -1;
	// 添加年份，从1910年开始
	var yearSelects = document.getElementsByName("year");
	var y;
	for(n = 0; n < yearSelects.length; n++){
		y = yearSelects[n];
		for (i = 2000; i <= new Date().getFullYear(); i++) {
			var option = document.createElement("option");
			option.text = i+"年";
			option.value = i;
			y.insertBefore(option,y.options[1]);
		}
		y.options[0].selected = true;
	} 
	// 添加月份
	var monthSelects = document.getElementsByName("month");
	var m;
	for(n = 0; n < monthSelects.length; n++){
		m = monthSelects[n];
		for (i = 1; i <= 12; i++) {
			var option = document.createElement("option");
			option.text = i+"月";
			if(i < 10){
				option.value = "0"+i;
			}else{
				option.value = i;
			}
			m.appendChild(option);
		}
		m.options[0].selected = true;
	}
	
	//添加日期
	var daySelects = document.getElementsByName("day");
	var d;
	for(n = 0; n < daySelects.length; n++){
		d = daySelects[n];
		for (i = 1; i <= 31; i++) {
			var option = document.createElement("option");
			option.text = i+"日";
			if(i < 10){
				option.value = "0"+i;
			}else{
				option.value = i;
			}
			d.appendChild(option);
		}
		d.options[0].selected = true;
	}
	var con_list = document.getElementsByName("con");
	if(con_list.length == 0){
		search();
	}else{
		$('#searchDiv').show();
		$('#reportTop').show();
	}
}

var alterDaySelect = function(obj){
	var conKey = obj.id.substring(0,obj.id.indexOf('_'));
	var currentDaySelect = null;
	currentDaySelect = document.getElementById(conKey+"_day");
	if(currentDaySelect == null)
		return ;
	//若currentDaySelect存在,先检查是否是31天，不是则补全。然后再进行调整。
	//1.补全
	if(currentDaySelect.length != 32){
		for (var i = currentDaySelect.length; i <= 31; i++) {
			var option = document.createElement("option");
			option.text = i+"日";
			if(i < 10){
				option.value = "0"+i;
			}else{
				option.value = i;
			}
			currentDaySelect.appendChild(option);
		}
		currentDaySelect.options[0].selected = true;
	}
	//2.调整
	var currentYearSelect = document.getElementById(conKey+"_year");
	var currentMonthSelect = document.getElementById(conKey+"_month");
	if(currentMonthSelect.value == 01 || currentMonthSelect.value == 03 || currentMonthSelect.value == 05 || currentMonthSelect.value == 07 || currentMonthSelect.value == 08 || currentMonthSelect.value == 10 || currentMonthSelect.value == 12)
		return ;
	if(currentMonthSelect.value == 04 || currentMonthSelect.value == 06 || currentMonthSelect.value == 09 || currentMonthSelect.value == 11){
		currentDaySelect.remove(currentDaySelect.length-1);//31
		return ;
	}
	if(currentMonthSelect.value == 02 && currentYearSelect.value % 4 != 0){
		currentDaySelect.remove(currentDaySelect.length-1);//31
		currentDaySelect.remove(currentDaySelect.length-1);//30
		currentDaySelect.remove(currentDaySelect.length-1);//29
		return ;
	}
	if(currentMonthSelect.value == 02 && currentYearSelect.value % 4 == 0){
		currentDaySelect.remove(currentDaySelect.length-1);//31
		currentDaySelect.remove(currentDaySelect.length-1);//30
		return ;
	}
}
//定义startwith方法
String.prototype.startWith = function(str){
	if(str == null || str == '' || this.length == 0 || str.length > this.length){
		return false;
	}else if(this.substring(0,str.length) == str){
		return true;
	}else{
		return false;
	}
} 

var search = function(){
	var host = window.location.host;
	var paramMap = {};
	
//	报表名和描述
	var reportMap = {};
	var reportNameKey = document.getElementById("reportNameKey").value;
	var reportNameValue = document.getElementById("reportNameValue").value;
	var reportDecKey = document.getElementById("reportDecKey").value;
	var reportDecValue = document.getElementById("reportDecValue").value;
	var reportId = document.getElementById("reportId").value;
	reportMap[reportNameKey] = reportNameValue;
	reportMap[reportDecKey] = reportDecValue;
	reportMap["REPORTID"] = reportId;
	paramMap['reportMap'] = reportMap;
	
//	筛选条件
	var con_list = document.getElementsByName("con");
	var conMap = {};
	for(var i = 0; i < con_list.length; i++){
		var conKey = con_list[i].id;
		var conValue = con_list[i].value;
		var isSqlParam = document.getElementById("isSqlParam_"+conKey).value;
		conMap[conKey] = conValue;
		conMap["isSqlParam_"+conKey] = isSqlParam;
	}
	
	//time类型条件
	var years = document.getElementsByName("year");
	var y,m,d,conKey,timeValue;
	for(var n = 0; n < years.length; n++){
		y = years[n];
		conKey = y.id.substring(0,y.id.indexOf('_'));
		m = document.getElementById(conKey+"_month");
		d = document.getElementById(conKey+"_day");
		
		if(m == null && d == null){
			if(y.value == 'default'){
				showTip("请选择时间！");
				return;
			}
			timeValue = y.value;
		}else if(m != null && d == null){
			if(y.value == 'default' || m.value == 'default'){
				showTip("请选择时间！");
				return;
			}
			timeValue = y.value+"-"+m.value;
		}else if(m != null && d != null){
			if(y.value == 'default' || m.value == 'default' || d.value == 'default'){
				showTip("请选择时间！");
				return;
			}
			timeValue = y.value+"-"+m.value+"-"+d.value;
		}
		conMap[conKey] = timeValue;
		var isSqlParam = document.getElementById("isSqlParam_"+conKey).value;
		conMap["isSqlParam_"+conKey] = isSqlParam;
	}
	paramMap['conMap'] = conMap;
	//遍历conMap拼接get传输串
	var con = "" ;
	for (var prop in conMap) {  
		  if (conMap.hasOwnProperty(prop)) {
			  con = con + prop+"="+conMap[prop]+"&";
		  }  
		} 
//	sql语句
	var sql_list = document.getElementsByName("sql");
	var sqlMap = {};
	for(var n = 0; n < sql_list.length; n++){
		var sqlKey = sql_list[n].id;
		var sqlValue = sql_list[n].value;
		sqlMap[sqlKey] = sqlValue;
	}
	paramMap['sqlMap'] = sqlMap;
	
//	jasper路径 
	var jasperPath = document.getElementById("jasperPath").value;
	paramMap["jasperPath"] = jasperPath;
	
	//发送post请求
	$.ajax({
        type: "POST", //用POST方式传输
        url: ctx + "/reports/common/searchForHtml.action", //目标地址
        data: {"paramMap":JSON.stringify(paramMap)},
        dataType: "html", //数据格式:JSON
        error: function () { alert('请求错误！'); },
        beforeSend: function () {
        	showTip("load");
        },
        success: function (data){
        	document.write(/*'<table><tr><td width="40px" colspan="3"><p style="overflow: hidden; text-indent: 0px; "><input class="btn_gray" value="返回" onclick="javascript:history.go(-1);" style="margin-right: 5px; margin-left:0;" type="button"></p></td>'+*/
        				   '<table><tr><td width="70px" colspan="3"><p style="overflow: hidden; text-indent: 0px; "><a href="http://'+host+'/icms-web/pages/reports/appDevInfo/excel.jsp?'+con+'REPORTID='+reportId+'&jasperPath='+jasperPath+'" target="_blank"><span style="font-family: 宋体; font-size: 8pt; line-height: 1.140625;">EXCEL格式</span></a></p></td>'+
        				   '<td width="60px" colspan="3"><p style="overflow: hidden; text-indent: 0px; "><a href="http://'+host+'/icms-web/pages/reports/appDevInfo/pdf.jsp?'+con+'REPORTID='+reportId+'&jasperPath='+jasperPath+'" target="_blank"><span style="font-family: 宋体; font-size: 8pt; line-height: 1.140625;">PDF格式</span></a></p></td>'+
        				   '<td width="60px" colspan="3"><p style="overflow: hidden; text-indent: 0px; "><a href="http://'+host+'/icms-web/pages/reports/appDevInfo/word.jsp?'+con+'REPORTID='+reportId+'&jasperPath='+jasperPath+'" target="_blank"><span style="font-family: 宋体; font-size: 8pt; line-height: 1.140625;">WORD格式</span></a></p></td></tr></table>'+
        				   data);
        }
    });
}