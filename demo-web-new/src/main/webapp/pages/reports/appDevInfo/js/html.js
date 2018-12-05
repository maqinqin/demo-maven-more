$(document).ready(function() {
	var i = -1;
	// 添加年份，从1910年开始
	var yearSelect = document.getElementById("year");
	for (i = 2000; i <= new Date().getFullYear(); i++) {
		var option = document.createElement("option");
		option.text = i+"年";
	    option.value = i;
	    yearSelect.insertBefore(option,yearSelect.options[1]);
	}
	yearSelect.options[0].selected = true;
	var monthSelect = document.getElementById("month");
	// 添加月份
	for (i = 1; i <= 12; i++) {
		var option = document.createElement("option");
		option.text = i+"月";
		option.value = i;
		if(i < 10){
			option.value = "0"+i;
		}
	    monthSelect.appendChild(option);
	    monthSelect.options[0].selected = true;
	}
	
//	返回后将筛选条件回显到select中
	var timeLineForBack = document.getElementById("timeLineForBack").value;
	var queryvmTypeForBack = document.getElementById("queryvmTypeForBack").value;
	
	var year = null;
	var month = null;
	if(timeLineForBack != null && timeLineForBack != "null"){
		var yAndm = timeLineForBack.split("-");
		year = yAndm[0];
		month = yAndm[1];
		var yearOptions = document.getElementById("year").options;
		for(var i = 0; i < yearOptions.length; i++){
			if(yearOptions[i].value == year){
				yearOptions[i].selected = "selected";
			}
		}
		var monthOptions = document.getElementById("month").options;
		for(var i = 0; i < monthOptions.length; i++){
			if(monthOptions[i].value == month){
				monthOptions[i].selected = "selected";
			}
		}
		var queryvmTypeOptions = document.getElementById("queryvmType").options;
		for(var i = 0; i < queryvmTypeOptions.length; i++){
			if(queryvmTypeOptions[i].value == queryvmTypeForBack){
				queryvmTypeOptions[i].selected = "selected";
			}
		}
	}
	
});


/**
 * 根据日期和虚拟机类型获取应用系统资源信息
 */
function search(){
	var serverName = document.getElementById("serverName").value;
	var serverPort = document.getElementById("serverPort").value;
	var contextPath = document.getElementById("contextPath").value;
	var year = $("#year").val();
	var month = $("#month").val();
	var timeLine = year+"-"+month;
	var monthNext = "1";
	if(month == "12"){
		year = parseInt(year)+1+"";
	}else{
		monthNext = "0"+(parseInt(month)+1);
	}
	var timeLineNext = year+"-"+monthNext;
	var queryvmType = $("#queryvmType").val();
	if(year == "" || timeLine == "" || queryvmType == ""){
		showTip("请选择年份、月份和虚拟机类型！");
		return false;
	}
	window.location.assign("http://"+serverName+":"+serverPort+contextPath+"/pages/reports/appDevInfo/searchForHtml.jsp?timeLine="+timeLine+"&timeLineNext="+timeLineNext+"&queryvmType="+queryvmType);
}