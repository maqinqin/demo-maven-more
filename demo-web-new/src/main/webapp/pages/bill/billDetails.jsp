<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>

<html>
<head>
<title>云平台管理系统</title>
<script type="text/javascript" src="${ctx}/pages/bill/js/billDetails.js"></script>
<script type='text/javascript' src='${ctx}/scripts/My97DatePicker/WdatePicker.js'></script>
<style type="text/css">
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg">详单查询</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:90px;">租户名称：</td>
					<td class="tabCon">
						<select id="selTenant" class="selInput" name="tenant">
							<option value="0"><icms-i18n:label name="com_l_choose"/></option>
						</select>
					</td>
					<td class="tabBt" style="width:90px;">服务类型：</td>
					<td class="tabCon">
						<icms-ui:dic id="serviceType" name="serviceType" kind="BILL_SERVICE_TYPE" 
										showType="select" attr="class='selInput'" />
					</td>
				    <td class="tabBt">日期查询：</td>
					<td class="tabCon">
						<p id="serviceSelectTime" style="width:200px; margin-bottom:0;">
							<input id="serviceBeginTimes"  name="serviceBeginTimes"  onClick="WdatePicker()" style="width: 79px; float:left;" type="text" class="textInput textInputTime" />
							<span style="float:left; margin: 0 5px;" >--</span>
							<input id="serviceEndtimes"  name="serviceEndtimes"  onClick="WdatePicker()" style="width: 79px; float:left;" type="text" class="textInput textInputTime"  />
						</p>
					</td>
				</tr>
			</table>
		</div>
	</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
			<table height="12%" width="100%" align="center">
				<tr style=" height:52px;">
					<td   width="10%" align="center" colspan="2">
			<shiro:hasPermission name="user:list">  
				<a href="javascript:void(0)" type="button" class="btn" onclick="searchBillDetails();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
					<span class="icon iconfont icon-icon-search"></span>
					<span>查询</span>
				</a>
			</shiro:hasPermission>
	       <button id="btn_reset"  type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btnDel" onclick="clearAll();" value='<icms-i18n:label name="common_btn_clear"/>'>
				<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
			</button>
			</td>
				</tr>
		   </table>
	   </div>
	</div>
</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridMain">
				<table id="gridTable" ></table>
				<div id="gridPager"></div>
			</div>
		</div>
	</div>
</body>
<script  type="text/javascript">  
	window.onload = function(){
	  	var d = new Date();
	    function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
	    var s = d.getFullYear().toString() + '-'+addzero(d.getMonth() + 1) + '-'+addzero(d.getDate());
	    $('.textInputTime').val(s)
	    
	  };
</script> 
</html>
	