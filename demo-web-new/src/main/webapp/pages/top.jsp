<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>	
<%
	String _path = request.getContextPath();
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx}/css/public.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/iconfont/iconfont.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
$(function() {
	
	
	$.ajax({
        type : "POST",
        url : ctx + "/sys/uploadLogo/getLogo.action",
        data : {},
        async : true,
        cache : false,
        success : function(data) {
            for(var i = 0 ; i < data.length; i ++){
                if(data[i].paramName=="CloudTubeLogo"){
                    document.getElementById('logoImage').src = data[i].paramLogo;   
                    break;
                }
            }
        },
        error : function(e) {
            }
        });

	
	
	
	
	
    // 判断logo类型
	var logoType = '<s:property value="%{getText('logoStyle')}"/>';
	var gaoweidaLogoImage = '<s:property value="%{getText('gaoweidaTopLogoImage')}"/>';
	var defaultLogoImage = '<s:property value="%{getText('defaultTopLogoImage')}"/>';
	var huaweiLogoImage = '<s:property value="%{getText('huaweiTopLogoImage')}"/>';
	/* if(logoType == 'gaoweida') {
        document.getElementById('logoImage').src = gaoweidaLogoImage;
	} else if(logoType == 'huawei'){
		 document.getElementById('logoImage').src = huaweiLogoImage;
	}else {
        document.getElementById('logoImage').src = defaultLogoImage;
	} */
	
	 function getCurrentDate() {
         var date = new Date();
         var monthArray=new Array
         ("January","February","March","April","May","June","July","August",
         "September","October","November","December");
         var weekArray = new Array("Sunday","Monday","Tuesday",
              "Wednesday","Thursday","Friday","Saturday");
         month=date.getMonth();
         day=date.getDate();
         if(day.toString().length == 1){
             day="0"+day.toString();
         }
         
 		var result =date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
 		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"  "+result;
         /* return monthArray[month]+" "+day+"  "+
             date.getFullYear() + "  " + weekArray[date.getDay()] + " "+result; */
    }

	setInterval(function() {
		
		$("#server_time_span").text(getCurrentDate());
	}, 1000);
});
//退出系统平台;
function exitSystem(){
	 if(confirm(i18nShow('login_exist'))){
		 window.top.location='<%=_path%>/shiro/logout.action';
	 }
}
</script>
</head>

<body>
<div class="headerWrap">
	<h2 class="logo"><a href="#"><img src="" id="logoImage"/></a></h2> 
	<ul><li style="padding-right:900px;"><i class="topTime1">3.4.0_2016081902sp</i></li></ul>
    <ul class="sitNav" >
<%--    	  <li><i class="fa fa-clock-o"></i><strong> <icms-i18n:label name="top_system_time"/>: </strong><span id="server_time_span"></span></li> --%>
	  <li><i class="fa fa-user"></i> <strong><icms-i18n:label name="top_hello"/>,</strong><span><shiro:user><shiro:principal /></shiro:user></span></li>
      <li class="noMargin"><a href="#" class="topOut" onclick="exitSystem();"><i class="fa fa-power-off"></i> <icms-i18n:label name="topOut"/></a></li>
      
  </ul>
</div>
</body>

