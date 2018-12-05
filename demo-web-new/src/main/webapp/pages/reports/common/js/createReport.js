//<script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery/jquery-1.7.min.js";></script>

//增加条件方法
var addConLi = function(){
	
	var conType = document.getElementById("conType").value;
	if(conType != null && conType == "text"){
		addTextConLi();
	}else if(conType != null && conType == "select"){
		addSelectConLi();
	}else if(conType != null && conType == "time"){
		addTime();
	}else if(conType != null && conType == "selectSql"){
		addSelectSqlConLi();
	}
}

function addSelectSqlConLi(){

	var conditionsUl = document.getElementById("conditionsUl");
	
	//创建所需元素
	var li = document.createElement("li");
	var div1 = document.createElement("div");
	var div2 = document.createElement("div");
	var ul1 = document.createElement("ul");
	var ul2 = document.createElement("ul");
	var li1 = document.createElement("li");
	var li2 = document.createElement("li");
	var input1_1 = document.createElement("input");
	var input1_2 = document.createElement("input");
	var input1_3 = document.createElement("input");
	var input1_4 = document.createElement("input");
	var input1_5 = document.createElement("input");
	var input2_2 = document.createElement("textarea");
	
	var conKey = document.createElement("span");
	var conValue = document.createElement("span");
	var SqlParam = document.createElement("span");
	var isSqlParam = document.createElement("span");
	conKey.innerText = i18nShow('sys_l_report_conKey')+" : ";
	conValue.innerText = i18nShow('sys_l_report_con_describe')+" : "; 
	SqlParam.innerText = i18nShow('sys_l_report_SQL_sentence')+":";
	isSqlParam.innerText = i18nShow('sys_l_report_sql_need');
	//	删除条件按钮
	var del = document.createElement("input");
	del.setAttribute("type","button");
	del.setAttribute("value",i18nShow('sys_l_report_delete_condition'));
	del.setAttribute("onclick","delCon(this);")
	del.setAttribute("class","btn_dd4");
	del.setAttribute("style","margin-right: 5px; margin-left:0;");
	
	//	条件计数
	var countCon;
	var lastDiv = conditionsUl.getElementsByTagName("div")[conditionsUl.getElementsByTagName("div").length-1];
	//取到最后一个div
	if(lastDiv == null){
		countCon = 0;
	}else{
		//lastDiv.id的形式为：div16_1
		countCon = Number(lastDiv.id.substring(3,lastDiv.id.indexOf('_')))+1;
		//取到div上对应的数字
	}
	
	//添加标题
	var div4 = document.createElement("div");
	var countT;
	countT= countCon+1;
	div4.innerText = i18nShow('sys_l_report_condition')+countT;
	div4.setAttribute("style","padding-left:18px; border-left: 3px solid #18a689; font-weight:bold;");
	li.appendChild(div4); 
	
	// 条件
	input1_1.setAttribute("type","text");
	input1_1.setAttribute("id","conKey_"+countCon);
	input1_1.setAttribute("name","conKey");
	input1_1.setAttribute("class","textInput readonly");
	input1_2.setAttribute("type","text");
	input1_2.setAttribute("id","conValue_"+countCon);
	input1_2.setAttribute("name","conValue");
	input1_2.setAttribute("class","textInput readonly");
	input1_3.setAttribute("type","hidden");
	input1_3.setAttribute("id","conType_"+countCon);
	input1_3.setAttribute("name","conType");
	input1_3.setAttribute("value","selectSql");
	input1_4.setAttribute("type","checkbox");
	input1_4.setAttribute("id","isSqlParam_"+countCon);
	input1_4.setAttribute("name","isSqlParam");
	input1_5.setAttribute("type","hidden");
	input1_5.setAttribute("id","conId_"+countCon);
	input1_5.setAttribute("name","conId");
	li1.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	div1.setAttribute("id","div"+countCon+"_0");
	li1.appendChild(conKey);
	li1.appendChild(input1_1);
	li1.appendChild(conValue);
	li1.appendChild(input1_2);
	li1.appendChild(input1_3);
	li1.appendChild(del); 
	li1.appendChild(input1_4);
	li1.appendChild(isSqlParam);
	li1.appendChild(input1_5);
	ul1.appendChild(li1);
	div1.appendChild(ul1);
	li.appendChild(div1); 

	
	//	SQL
	input2_2.setAttribute("id","sqlSelectValue_"+countCon);
	input2_2.setAttribute("name","sqlSelectValue");
	input2_2.setAttribute("class","sqlArea");
	input2_2.setAttribute("style","width: 392px;");
	//input2_2.setAttribute("style","margin-left :20px;");
	li2.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	li.setAttribute("style","list-style:none;margin-left :24px;");
	div2.setAttribute("id","div"+countCon+"_1");
	
	var hr = document.createElement("hr");
	hr.setAttribute("style","height:1px;border:none;border-top:1px dashed #0066CC;");
	
	li2.appendChild(SqlParam);
	li2.appendChild(input2_2);
	ul2.appendChild(li2); 
	div2.appendChild(ul2);
	li.appendChild(div2);
	
	conditionsUl.appendChild(li);

}

var addTime = function(){
	var conditionsUl = document.getElementById("conditionsUl");
	//	条件计数
	var countCon;
	var lastDiv = conditionsUl.getElementsByTagName("div")[conditionsUl.getElementsByTagName("div").length-1];
	if(lastDiv == null){
		countCon = 0;
	}else{
		countCon = Number(lastDiv.id.substring(3,lastDiv.id.indexOf('_')))+1;
	}
	
	
	//创建外层包裹
	var li = document.createElement("li");
	var div = document.createElement("div");
	var ul = document.createElement("ul");
	var liSon1 = document.createElement("li");
	var liSon2 = document.createElement("li");
	//div的ID
	div.setAttribute("id","div"+countCon+"_0");
	
	//添加标题
	var div4 = document.createElement("div");
	var countT;
	countT= countCon+1;
	div4.innerText = i18nShow('sys_l_report_condition')+countT;
	div4.setAttribute("style","padding-left:18px; border-left: 3px solid #18a689; font-weight:bold;");
	li.appendChild(div4); 
	
	//创建文字描述相关span
	var conKeySpan = document.createElement("span");
	var conValueSpan = document.createElement("span");
	var isSqlParam = document.createElement("span");
	conKeySpan.innerText = i18nShow('sys_l_report_conKey')+" : ";
	conValueSpan.innerText = i18nShow('sys_l_report_con_describe')+" : ";
	isSqlParam.innerText = i18nShow('sys_l_report_sql_need');
	
	//创建conkey和convalue以及issql输入框
	var inputKey = document.createElement("input");
	var inputValue = document.createElement("input");
	var inputIsSql = document.createElement("input");
	inputKey.setAttribute("type","text");
	inputKey.setAttribute("id","conKey_"+countCon);
	inputKey.setAttribute("name","conKey");
	inputKey.setAttribute("class","textInput readonly");
	inputValue.setAttribute("type","text");
	inputValue.setAttribute("id","conValue_"+countCon);
	inputValue.setAttribute("name","conValue");
	inputValue.setAttribute("class","textInput readonly");
	inputIsSql.setAttribute("id","isSqlParam_"+countCon);
	inputIsSql.setAttribute("name","isSqlParam");
	inputIsSql.setAttribute("type","checkbox");
	liSon1.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	liSon2.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	li.setAttribute("style","list-style:none;margin-left :24px;");
	//创建年月日复选框及span
	var year = document.createElement('input');
	var month = document.createElement('input');
	var day = document.createElement('input');
	var timeDecSpan = document.createElement('span');
	var yearSpan = document.createElement('span');
	var monthSpan = document.createElement('span');
	var daySpan = document.createElement('span');
	//设置年月日复选框属性
	year.setAttribute("id","year_"+countCon);
	year.setAttribute("name","year");
	year.setAttribute("type","checkbox");
	year.setAttribute("checked","checked");
	year.setAttribute("onchange","setDisabled(this);");
	month.setAttribute("id","month_"+countCon);
	month.setAttribute("name","month");
	month.setAttribute("type","checkbox");
	month.setAttribute("onchange","setDisabled(this);");
	day.setAttribute("id","day_"+countCon);
	day.setAttribute("name","day");
	day.setAttribute("type","checkbox");
	day.setAttribute("disabled","disabled");
	timeDecSpan.innerText = i18nShow('sys_report_selectOption');
	yearSpan.innerText = i18nShow('sys_report_year');
	monthSpan.innerText = i18nShow('sys_report_month');
	daySpan.innerText = i18nShow('sys_report_day');
	
	//	删除条件按钮
	var del = document.createElement("input");
	del.setAttribute("type","button");
	del.setAttribute("value",i18nShow('sys_l_report_delete_condition'));
	del.setAttribute("onclick","delCon(this);")
	del.setAttribute("class","btn_dd4");
	del.setAttribute("style","margin-right: 5px; margin-left:0;");
	
	//条件类型
	var inputType = document.createElement('input');
	inputType.setAttribute("type","hidden");
	inputType.setAttribute("id","conType_"+countCon);
	inputType.setAttribute("name","conType");
	inputType.setAttribute("value","time");
	
	//条件ID
	var inputId = document.createElement('input');
	inputId.setAttribute("type","hidden");
	inputId.setAttribute("id","conId_"+countCon);
	inputId.setAttribute("name","conId");
	
	var hr = document.createElement("hr");
	hr.setAttribute("style","height:1px;border:none;border-top:1px dashed #0066CC;");
	
	
	//添加各个元素
	liSon1.appendChild(conKeySpan);
	liSon1.appendChild(inputKey);
	liSon1.appendChild(conValueSpan);
	liSon1.appendChild(inputValue);
	liSon1.appendChild(del);
	liSon1.appendChild(inputIsSql);
	liSon1.appendChild(isSqlParam);
	liSon1.appendChild(inputType);
	liSon1.appendChild(inputId);
	liSon2.appendChild(timeDecSpan);
	liSon2.appendChild(year);
	liSon2.appendChild(yearSpan);
	liSon2.appendChild(month);
	liSon2.appendChild(monthSpan);
	liSon2.appendChild(day);
	liSon2.appendChild(daySpan);
	
	ul.appendChild(liSon1);
	ul.appendChild(liSon2);
	div.appendChild(ul);
	li.appendChild(div);
	
	conditionsUl.appendChild(li);
	
}

//添加text筛选条件
var addTextConLi = function(){
	var conditionsUl = document.getElementById("conditionsUl");
//	条件计数(添加再删除中间元素在添加,序号bug)
	var countCon;
	var lastDiv = conditionsUl.getElementsByTagName("div")[conditionsUl.getElementsByTagName("div").length-1];
	if(lastDiv == null){
		countCon = 0;
	}else{
		countCon = Number(lastDiv.id.substring(3,lastDiv.id.indexOf('_')))+1;
	}
	
	var li = document.createElement("li");
	var div = document.createElement("div");
	var ul = document.createElement("ul");
	var liSon = document.createElement("li");
	
	var conKeySpan = document.createElement("span");
	var conValueSpan = document.createElement("span");
	var isSqlParam = document.createElement("span");
	conKeySpan.innerText = i18nShow('sys_report_condition_keyword')+" : ";
	conValueSpan.innerText = i18nShow('sys_l_report_con_describe')+" : ";
	isSqlParam.innerText = i18nShow('sys_l_report_sql_need');
	
	//添加标题
	var div4 = document.createElement("div");
	var countT;
	countT= countCon+1;
	div4.innerText = i18nShow('sys_l_report_condition')+countT;
	div4.setAttribute("style","padding-left:18px; border-left: 3px solid #18a689; font-weight:bold;");
	li.appendChild(div4); 
	// 条件
	var input1 = document.createElement("input");
	var input2 = document.createElement("input");
	var input3 = document.createElement("input");
	var input4 = document.createElement("input");
	var input5 = document.createElement("input");
	input1.setAttribute("type","text");
	input1.setAttribute("id","conKey_"+countCon);
	input1.setAttribute("name","conKey");
	input1.setAttribute("class","textInput readonly");
	input2.setAttribute("type","text");
	input2.setAttribute("id","conValue_"+countCon);
	input2.setAttribute("name","conValue");
	input2.setAttribute("class","textInput readonly");
	input3.setAttribute("type","hidden");
	input3.setAttribute("id","conType_"+countCon);
	input3.setAttribute("name","conType");
	input3.setAttribute("value","text");
	input4.setAttribute("type","checkbox");
	input4.setAttribute("id","isSqlParam_"+countCon);
	input4.setAttribute("name","isSqlParam");
	input5.setAttribute("type","hidden");
	input5.setAttribute("id","conId_"+countCon);
	input5.setAttribute("name","conId");
	liSon.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	li.setAttribute("style","list-style:none;margin-left :24px;");
	//	删除条件按钮
	var del = document.createElement("input");
	del.setAttribute("type","button");
	del.setAttribute("value",i18nShow('sys_l_report_delete_condition'));
	del.setAttribute("onclick","delCon(this);");
	del.setAttribute("class","btn_dd4");
	del.setAttribute("style","margin-right: 5px; margin-left:0;");
	
	//div的ID
	div.setAttribute("id","div"+countCon+"_0");
	
	var hr = document.createElement("hr");
	hr.setAttribute("style","height:1px;border:none;border-top:1px dashed #0066CC;");
	
	//添加
	liSon.appendChild(conKeySpan);
	liSon.appendChild(input1);
	liSon.appendChild(conValueSpan);
	liSon.appendChild(input2);
	liSon.appendChild(input3);
	liSon.appendChild(del);
	liSon.appendChild(input4);
	liSon.appendChild(input5);
	liSon.appendChild(isSqlParam);
	ul.appendChild(liSon); 
	div.appendChild(ul);
	li.appendChild(div);
	
	conditionsUl.appendChild(li);
}

//添加select筛选条件
var addSelectConLi = function(){
	var conditionsUl = document.getElementById("conditionsUl");
	
	//创建所需元素
	var li = document.createElement("li");
	var div1 = document.createElement("div");
	var div2 = document.createElement("div");
	var div3 = document.createElement("div");
	var ul1 = document.createElement("ul");
	var ul2 = document.createElement("ul");
	var ul3 = document.createElement("ul");
	var li1 = document.createElement("li");
	var li2 = document.createElement("li");
	var li3 = document.createElement("li");
	var hr = document.createElement("hr");
	var input1_1 = document.createElement("input");
	var input1_2 = document.createElement("input");
	var input1_3 = document.createElement("input");
	var input1_4 = document.createElement("input");
	var input1_5 = document.createElement("input");
	var input2_1 = document.createElement("input");
	var input2_2 = document.createElement("input");
	var input2_3 = document.createElement("input");
	var input2_4 = document.createElement("input");
	var input3_1 = document.createElement("input");
	var input3_2 = document.createElement("input");
	var input3_3 = document.createElement("input");
	var input3_4 = document.createElement("input");
	
	var conKey = document.createElement("span");
	var conValue = document.createElement("span");
	var proKey = document.createElement("span");
	var proValue = document.createElement("span");
	var proKey1 = document.createElement("span");
	var proValue1 = document.createElement("span");
	var isSqlParam = document.createElement("span");
	conKey.innerText = i18nShow('sys_report_condition_keyword')+" : ";
	conValue.innerText = i18nShow('sys_l_report_con_describe')+" : "; 
	proKey.innerText = i18nShow('sys_report_attr_keyword');
	proValue.innerText = i18nShow('sys_report_attribute_describe')+" : ";
	proKey1.innerText = i18nShow('sys_report_attr_keyword');
	proValue1.innerText = i18nShow('sys_report_attribute_describe')+" : ";
	isSqlParam.innerText = i18nShow('sys_l_report_sql_need');
	//	删除条件按钮
	var del = document.createElement("input");
	del.setAttribute("type","button");
	del.setAttribute("value",i18nShow('sys_l_report_delete_condition'));
	del.setAttribute("onclick","delCon(this);")
	del.setAttribute("class","btn_dd4");
	del.setAttribute("style","margin-right: 5px; margin-left:0;");
	//	添加属性按钮
	var addPro = document.createElement("input");
	addPro.setAttribute("type","button");
	addPro.setAttribute("value",i18nShow('sys_report_attribute_add'));
	addPro.setAttribute("onclick","addPro(this);")
	addPro.setAttribute("class","btn_dd4");
	addPro.setAttribute("style","margin-right: 5px; margin-left:0;");
	//	条件计数
	var countCon;
	var lastDiv = conditionsUl.getElementsByTagName("div")[conditionsUl.getElementsByTagName("div").length-1];
	//取到最后一个div
	if(lastDiv == null){
		countCon = 0;
	}else{
		//lastDiv.id的形式为：div16_1
		countCon = Number(lastDiv.id.substring(3,lastDiv.id.indexOf('_')))+1;
		//取到div上对应的数字
	}
	
	//添加标题
	var div4 = document.createElement("div");
	var countT;
	countT= countCon+1;
	div4.innerText = i18nShow('sys_l_report_condition')+countT;
	div4.setAttribute("style","padding-left:18px; border-left: 3px solid #18a689; font-weight:bold;");
	li.appendChild(div4); 
	
	// 条件
	input1_1.setAttribute("type","text");
	input1_1.setAttribute("id","conKey_"+countCon);
	input1_1.setAttribute("name","conKey");
	input1_1.setAttribute("class","textInput readonly");
	input1_2.setAttribute("type","text");
	input1_2.setAttribute("id","conValue_"+countCon);
	input1_2.setAttribute("name","conValue");
	input1_2.setAttribute("class","textInput readonly");
	input1_3.setAttribute("type","hidden");
	input1_3.setAttribute("id","conType_"+countCon);
	input1_3.setAttribute("name","conType");
	input1_3.setAttribute("value","select");
	input1_4.setAttribute("type","checkbox");
	input1_4.setAttribute("id","isSqlParam_"+countCon);
	input1_4.setAttribute("name","isSqlParam");
	input1_5.setAttribute("type","hidden");
	input1_5.setAttribute("id","conId_"+countCon);
	input1_5.setAttribute("name","conId");
	div1.setAttribute("id","div"+countCon+"_0");
	li1.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :20px; list-style:none;");
	
	li1.appendChild(conKey);
	li1.appendChild(input1_1);
	li1.appendChild(conValue);
	li1.appendChild(input1_2);
	li1.appendChild(input1_3);
	li1.appendChild(del);
	li1.appendChild(addPro); 
	li1.appendChild(input1_4);
	li1.appendChild(isSqlParam);
	li1.appendChild(input1_5);
	ul1.appendChild(li1);
	div1.appendChild(ul1);
	li.appendChild(div1); 

	
	
	//	属性1
	input2_1.setAttribute("type","text");
	input2_1.setAttribute("id","conLi"+countCon+"_pro0_key");	//conLi0_pro0_key
	input2_1.setAttribute("name","proKey");
	input2_1.setAttribute("class","textInput readonly");
	input2_2.setAttribute("type","text");
	input2_2.setAttribute("id","conLi"+countCon+"_pro0_value");
	input2_2.setAttribute("name","proValue");
	input2_2.setAttribute("class","textInput readonly");
	input2_3.setAttribute("type","button"); 
	input2_3.setAttribute("value",i18nShow('sys_report_attribute_delete'));
	input2_3.setAttribute("onclick","delPro(this);");
	input2_3.setAttribute("class","btn_dd4");
	input2_3.setAttribute("style","margin-right: 5px; margin-left:0;");
	input2_4.setAttribute("type","hidden");
	input2_4.setAttribute("id","conLi"+countCon+"_pro0_Id");
	input2_4.setAttribute("name","proId");
	
	div2.setAttribute("id","div"+countCon+"_1");
	li2.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :44px; list-style:none;");
	li.setAttribute("style","list-style:none;margin-left :24px;");
	
	li2.appendChild(proKey);
	li2.appendChild(input2_1);
	li2.appendChild(proValue);
	li2.appendChild(input2_2);
	li2.appendChild(input2_3);
	li2.appendChild(input2_4);
	ul2.appendChild(li2); 
	div2.appendChild(ul2);
	li.appendChild(div2);
	
//	属性2
	input3_1.setAttribute("type","text");
	input3_1.setAttribute("id","conLi"+countCon+"_pro1_key");	//conLi0_pro0_key
	input3_1.setAttribute("name","proKey");
	input3_1.setAttribute("class","textInput readonly");
	input3_2.setAttribute("type","text");
	input3_2.setAttribute("id","conLi"+countCon+"_pro1_value");
	input3_2.setAttribute("name","proValue");
	input3_2.setAttribute("class","textInput readonly");
	input3_3.setAttribute("type","button"); 
	input3_3.setAttribute("value",i18nShow('sys_report_attribute_delete'));
	input3_3.setAttribute("onclick","delPro(this);");
	input3_3.setAttribute("class","btn_dd4");
	input3_3.setAttribute("style","margin-right: 5px; margin-left:0;");
	input3_4.setAttribute("type","hidden");
	input3_4.setAttribute("id","conLi"+countCon+"_pro1_Id");
	input3_4.setAttribute("name","proId");
	
	div3.setAttribute("id","div"+countCon+"_2");
	li3.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :44px; list-style:none;");
	li.setAttribute("style","list-style:none;margin-left :24px;");
	hr.setAttribute("style","height:1px;border:none;border-top:1px dashed #0066CC;");
	
	li3.appendChild(proKey1);
	li3.appendChild(input3_1);
	li3.appendChild(proValue1);
	li3.appendChild(input3_2);
	li3.appendChild(input3_3);
	li3.appendChild(input3_4);
	ul3.appendChild(li3); 
	div2.appendChild(ul3);
	li.appendChild(div2);
	conditionsUl.appendChild(li);
}

//更新年月日的可选状态
var setDisabled = function(obj){
	var timeCount = obj.id.substring(obj.id.indexOf('_')+1);
	var day = document.getElementById("day_"+timeCount);
	var month = document.getElementById("month_"+timeCount);
	if(obj.id.startWith("year_")){
		if(obj.checked == true){
			month.removeAttribute("disabled");
		}else if(obj.checked == false){
			month.setAttribute("disabled","disabled");
			day.setAttribute("disabled","disabled");
			month.checked = false;
			day.checked = false;
		}
	}else if(obj.id.startWith("month_")){
		if(obj.checked == true){
			day.removeAttribute("disabled");
		}else if(obj.checked == false){
			day.setAttribute("disabled","disabled");
			day.checked = false;
		}
	}else{
		alert("else");
	}
} 

String.prototype.startWith = function(str){
	if(str == null || str == '' || this.length == 0 || str.length > this.length){
		return false;
	}else if(this.substring(0,str.length) == str){
		return true;
	}else{
		return false;
	}
} 

//删除条件方法
var delCon = function(obj){
	var li = obj.parentNode.parentNode.parentNode.parentNode;
	li.parentNode.removeChild(li);
}
//删除属性方法
var delPro = function(obj){
	var ul = obj.parentNode.parentNode;
	ul.parentNode.removeChild(ul);
}


//添加属性方法
var addPro = function(obj){
	var divx_0 = obj.parentNode.parentNode.parentNode;	//条件DIV
	var divx_1 = document.getElementById(divx_0.id.substring(0,divx_0.id.indexOf('_'))+"_1");	//属性DIV
	
	//获取条件和属性的序号
	var countCon = divx_0.id.substring(3,divx_0.id.indexOf('_'));
	var countPro;
	var lastProValue = divx_1.getElementsByTagName("input")[divx_1.getElementsByTagName("input").length-3];
	if(lastProValue == null){
		//当没有属性时，下一个属性编号为0.
		countPro = 0;
	}else{
		//lastProValue.id的形式为：conLi0_pro16_value
		countPro = Number(lastProValue.id.substring(lastProValue.id.indexOf("pro")+3,lastProValue.id.lastIndexOf('_')))+1;
	}
	
	//创建属性选项
	var ul = document.createElement("ul");
	var li = document.createElement("li");
	var proKey = document.createElement("span");
	var proValue = document.createElement("span");
	var input1 = document.createElement("input");
	var input2 = document.createElement("input");
	var input3 = document.createElement("input"); 
	var input4 = document.createElement("input"); 
	
	//设置属性选项的属性
	proKey.innerText = i18nShow('sys_report_attr_keyword'); 
	proValue.innerText = i18nShow('sys_report_attribute_describe')+" : ";
	input1.setAttribute("type","text");
	input1.setAttribute("name","proKey");
	input1.setAttribute("id","conLi"+countCon+"_pro"+countPro+"_key");
	input1.setAttribute("class","textInput readonly");
	input2.setAttribute("type","text");
	input2.setAttribute("name","proValue");
	input2.setAttribute("id","conLi"+countCon+"_pro"+countPro+"_value");
	input2.setAttribute("class","textInput readonly");
	input3.setAttribute("type","button");
	input3.setAttribute("value",i18nShow('sys_report_attribute_delete'));
	input3.setAttribute("onclick","delPro(this);");
	input3.setAttribute("class","btn_dd4");
	input3.setAttribute("style","margin-right: 5px; margin-left:0;");
	input4.setAttribute("type","hidden");
	input4.setAttribute("id","conLi"+countCon+"_pro"+countPro+"_Id");
	input4.setAttribute("name","proId");
	li.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :44px; list-style:none;");
	//添加属性选项
	li.appendChild(proKey);
	li.appendChild(input1);
	li.appendChild(proValue);
	li.appendChild(input2);
	li.appendChild(input3);
	li.appendChild(input4);
	ul.appendChild(li);
	divx_1.appendChild(ul);

	
}



//添加sql语句
var addSql = function(){

	var ulSql = document.getElementById("sqlUL"); 
	var countSql = ulSql.getElementsByTagName("textarea").length;
	

	//创建元素 
	var li = document.createElement("li");
	var sqlKey = document.createElement("span");
	var sqlValue = document.createElement("span");
	var inputKey = document.createElement("input");
	var textarea = document.createElement("textarea");
	var input = document.createElement("input");
	var inputId = document.createElement("input");
	 
	//为元素赋值
	sqlKey.innerText = i18nShow('sys_report_sqlKey')+" : ";
	sqlValue.innerText = i18nShow('sys_report_sql_sentence')+" : ";
	inputKey.setAttribute("type","text");
	inputKey.setAttribute("id","sqlKey_"+countSql); 
	inputKey.setAttribute("name","sqlKey"); 
	inputKey.setAttribute("class","textInput readonly");
	
	textarea.setAttribute("id","sqlValue_"+countSql);
	textarea.setAttribute("name","sqlValue");
	textarea.setAttribute("class","sqlArea");
	textarea.setAttribute("style","width: 154px;");
	
	input.setAttribute("type","button");
	input.setAttribute("value",i18nShow('sys_report_sql_delete')); 
	input.setAttribute("onclick","delSqlStr(this);");
	input.setAttribute("class","btn_dd4");
	input.setAttribute("style","margin-right: 5px; margin-left:0;");
	
	inputId.setAttribute("type","hidden");
	inputId.setAttribute("id","sqlId_"+countSql);
	inputId.setAttribute("name","sqlId");
	
	li.setAttribute("style","margin-bottom :10px; margin-top :10px;margin-left :44px; list-style:none;");
	
	
 
	li.appendChild(sqlKey);
	li.appendChild(inputKey);
	li.appendChild(sqlValue);
	li.appendChild(textarea);
	li.appendChild(input);
	li.appendChild(inputId);
	ulSql.appendChild(li); 
	
}


//删除sql语句
var delSqlStr = function(obj){
	obj.parentNode.parentNode.removeChild(obj.parentNode);
}



//提交
var save = function(){
	var id = $("#reportID").val();
	var type = $("#reportType").val();
	var params = [];
	//报表名称map
	var reportNameMap = {};
	var reportNameKey = document.getElementById("reportNameKey").value;
	var reportNameValue = document.getElementById("reportNameValue").value;
	reportNameMap['reportNameKey'] = reportNameKey;
	reportNameMap['reportNameValue'] = reportNameValue;
	params.push(reportNameMap); 
	//报表描述map
	var reportDecMap = {};
	var descKey = document.getElementById("descKey").value;
	var descValue = document.getElementById("descValue").value;
	reportDecMap['reportDecKey'] = descKey;
	reportDecMap['reportDecValue'] = descValue;
	params.push(reportDecMap);
	
	//报表条件和属性map
	var conKeys = document.getElementsByName("conKey");
	var proKeys = document.getElementsByName("proKey");
	for(var i = 0; i < conKeys.length; i++){
		var conMap = {};
		var proMap = {};
		var select = [];
		var conCount = conKeys[i].id.substring(conKeys[i].id.indexOf('_')+1);
		conMap['conKey'] = conKeys[i].value;
		conMap['conValue'] = document.getElementById("conValue_"+conCount).value;
		conMap['conType'] = document.getElementById("conType_"+conCount).value;
		conMap['conId'] = document.getElementById("conId_"+conCount).value;
		if(document.getElementById("isSqlParam_"+conCount).checked){
			conMap['isSqlParam'] = "Y";
		}else{
			conMap['isSqlParam'] = "N";
		}
		if(document.getElementById("conType_"+conCount).value == "select"){
			for(var n = 0; n < proKeys.length; n++){
				if(proKeys[n].id.substring(5,proKeys[n].id.indexOf('_')) == conCount){
					var selectMap = {};
					/*proMap[proKeys[n].value] = document.getElementById(proKeys[n].id.substring(0,proKeys[n].id.lastIndexOf('_'))+"_value").value;*/
					selectMap["proKey"] = proKeys[n].value;
					selectMap["proValue"] = document.getElementById(proKeys[n].id.substring(0,proKeys[n].id.lastIndexOf('_'))+"_value").value;
					selectMap["proId"] = document.getElementById(proKeys[n].id.substring(0,proKeys[n].id.lastIndexOf('_'))+"_Id").value;
					select.push(selectMap); 
				}
			}
		} else if(document.getElementById("conType_"+conCount).value == "time"){
			var year = document.getElementById('year_'+conCount).checked;
			var month = document.getElementById('month_'+conCount).checked;
			var day = document.getElementById('day_'+conCount).checked;
			if(year == true && month == false && day == false){
				conMap['sqlSelectValue'] = 'y';
			}else if(year == true && month == true && day == false){
				conMap['sqlSelectValue'] = 'ym';
			}else if(year == true && month == true && day == true){
				conMap['sqlSelectValue'] = 'ymd';
			}
			if(document.getElementById("year_"+conCount).checked == false && document.getElementById("month_"+conCount).checked == false && document.getElementById("day_"+conCount).checked == false){
				showTip(i18nShow('sys_report_time_tip'));
				return false;
			}
		} else if(document.getElementById("conType_"+conCount).value == "selectSql"){
			conMap['sqlSelectValue'] = document.getElementById("sqlSelectValue_"+conCount).value;
		}
		conMap['select'] = select;
		params.push(conMap); 
	}
	
	//报表sql语句map
	var sqlKeys = document.getElementsByName("sqlKey");
	var sqlValues = document.getElementsByName("sqlValue");
	var sqlIds = document.getElementsByName("sqlId");
	var sqlCount;
	for(var i = 0; i < sqlKeys.length; i++){
		var sqlMap = {};
		sqlCount = sqlKeys[i].id.substring(sqlKeys[i].id.indexOf('_')+1);
		for(var n = 0; n < sqlValues.length; n++){
			if(sqlValues[n].id.substring(sqlValues[n].id.indexOf('_')+1) != null && sqlValues[n].id.substring(sqlValues[n].id.indexOf('_')+1) == (sqlCount)){
				for(var y = 0; y < sqlIds.length; y++){
					if(sqlIds[y].id.substring(sqlIds[y].id.indexOf('_')+1) != null && sqlIds[y].id.substring(sqlIds[y].id.indexOf('_')+1) == (sqlCount)){
						sqlMap['sqlKey'] = sqlKeys[i].value;
						sqlMap['sqlValue'] = sqlValues[n].value;
						sqlMap['sqlId'] = sqlIds[y].value;
						break;
					}
				}
			 }
		 }
		params.push(sqlMap);
	}
	 
	//报表Jasper路径
	var jasperPathMap = {};
	jasperPathMap['jasperPath'] = document.getElementById("jasperPath").value;
	params.push(jasperPathMap);
	//发送post请求
	$.ajax({
        type: "POST", //用POST方式传输
        dataType: "json", //数据格式:JSON
        url: ctx + "/reports/common/save.action", //目标地址
        data: {"params[]":JSON.stringify(params),"id":id,"type":type},
        error: function () { alert('Error loading XML document'); },
        success: function (data){showTip(i18nShow('sys_report_save_confirm'),function(){
        	closeView();
        	$("#deviceGridTable").jqGrid('setGridParam', {
        		url : ctx+"/reports/common/getReportList.action",//你的搜索程序地址
        		postData : {
        			"reportName" : ""
        		}, //发送搜索条件
        		pager : "#gridPager"
        	}).trigger("reloadGrid"); //重新载入
        });}
    });
}



function Map() {
    this.elements = new Array();
    //获取MAP元素个数
//    this.size = function() {
//        return this.elements.length;
//    };
    //判断MAP是否为空
//    this.isEmpty = function() {
//        return (this.elements.length < 1);
//    };
    //删除MAP所有元素
    this.clear = function() {
        this.elements = new Array();
    };
    //向MAP中增加元素（key, value)
    this.put = function(_key, _value) {
        this.elements.push( {
            key : _key,
            value : _value
        });
    };
    //删除指定KEY的元素，成功返回True，失败返回False
    this.remove = function(_key) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };
    //获取指定KEY的元素值VALUE，失败返回NULL
    this.get = function(_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    };
    //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
    this.element = function(_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    };
    //判断MAP中是否含有指定KEY的元素
    this.containsKey = function(_key) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };
    //判断MAP中是否含有指定VALUE的元素
    this.containsValue = function(_value) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };
    //获取MAP中所有VALUE的数组（ARRAY）
    this.values = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    };
    //获取MAP中所有KEY的数组（ARRAY）
    this.keys = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    };
}