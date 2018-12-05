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
<script type="text/javascript" src="${ctx}/pages/bill/js/billRecharge.js"></script>
<script type='text/javascript' src='${ctx}/scripts/My97DatePicker/WdatePicker.js'></script>
<style type="text/css">
.page-header{padding-bottom:2px;}
.Tab_tabBt{display:inline-block; padding:0 18px; font-size:15px; color:#3E3E52; cursor:pointer;}
.tabBt_cur{border-bottom: 2px solid #A01830;}
ul,li{padding:0; margin:0; list-style:none;}
#updataProjectVpcForm p {
	width: 300px;
	padding: 0;
	padding-left: 16px;
}

#updataProjectVpcForm p i {
	width: 100px;
	text-align: left;
}

#updataProjectVpcForm label {
	display: block;
	padding-left: 115px;
}
.tabs {
    height: 28px;
	padding: 0 24px;
	margin: 1px 0 0 0px;
	border-bottom: 1px solid #1ab394;   
}
.tabs li {
	width: 100px;
	text-align: center;
	/* background: url(../images/biaoqian02.png) no-repeat; */
	
	font-size: 14px;
	font-weight: normal;
	cursor: pointer;
	float: left;
	margin-left:4px;
}
#tabInfo{
	width: 95%;
    height: auto;
    margin-left: 10px;
    padding-bottom: 20px;
    float: left;
    min-height: 100px;
    margin-top:20px;
}
.default_color{
 /* background: url(${ctx}/images/biaoqian02.png); */
	background: #EFF0F2;
	color: #666;
	height: 26px;
	line-height: 26px;
}
.chosen_color{
	color: #18a689;
	margin-top: 1px;
	height: 26px;
	line-height: 26px;
	background: #fff;
	color: #1ab394;
	font-weight: normal;
	border-top: 2px solid #1ab394;
	border-left: 1px solid #1ab394;
	border-right: 1px solid #1ab394;
}
.quotaManage{
	margin-left: 40px;
	margin-top: 10px;
    margin-bottom: 10px;
}
#openstackLeft,#openstackRight,#powervcLeft,#powervcRight{
	list-style: none;
	float:left;
	
}
#openstackLeft,#powervcLeft{
	width: 120px;
    height: 100%;	
    height:270px;
    /* background:#5F6273;  */
   /*  border-top:2px solid #F3F3F3; */
}

#openstackLeft li,#powervcLeft li{	
    font-size: 15px;
    border-bottom: 1px solid #fff;
    color: #3e3e3e;
	height:24px;
	line-height:24px;
	padding:2px 10px 4px; 
	border:1px solid #1ab394;
    border-radius:4px;
    margin-top:10px;
    width:100px;
    text-align:center;
    
	 
}

#openstackRight,#powervcRight{
	float:left;
	display: inline-block;
	width: 69%;
	height: auto;
	float:left;
	margin-bottom: -20px;
}

.quotaManage input {
	width: 154px;
	border: 1px solid #d5d5d5;
	height: 22px;
	line-height: 22px;
	color: #615d70;
	background-color: #f5f5f5;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	box-shadow: 1px 1px 2px #ededed;
	margin-right:10px; 
	margin-left:10px;
}
.lable_sapn{
	width: 100px;
	display: inline-block;
	text-align: right;
}
.hidden{
	display:none;
}
.red{
	color:red;
	margin-left: 20px;
}

</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg" style="border-left:0;">
					<span class="Tab_tabBt tabBt_cur">充值</span>
					<span class="Tab_tabBt" >充值查询</span>
				</div>
			</h1>
		</div>
		<div class="Tab_tabConBox">
		<div class="Tab_tabCon">
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
					<td class="tabBt"></td>
					<td class="tabCon">
					<td class="tabBt"></td>
					<td class="tabCon">
				</tr>
			</table>
		</div>
	</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
			<table height="12%" width="100%" align="center">
				<tr style=" height:52px;">
					<td   width="10%" align="center" colspan="2">
			<shiro:hasPermission name="user:list">  
				<a href="javascript:void(0)" type="button" class="btn" onclick="searchBillRecharge();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
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
<!-- 充值查询页面信息-->
	<div class="Tab_tabCon" style="display:none;">
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:90px;">租户：</td>
					<td class="tabCon">
						<select id="selTenantList" class="selInput" name="tenant">
							<option value="0"><icms-i18n:label name="com_l_choose"/></option>
						</select>
					</td>
					<td class="tabBt">日期查询：</td>
					<td class="tabCon" colspan="3" >
						<p id="serviceSelectTime" style="width:400px; margin-bottom:0;">
							<input id="serviceBeginTimes"  name="serviceBeginTimes"  onClick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width: 130px; float:left;" type="text" class="textInput textInputTime" />
							<span style="float:left; margin: 0 5px;" >--</span>
							<input id="serviceEndtimes"  name="serviceEndtimes"  onClick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width: 130px; float:left;" type="text" class="textInput textInputTime"  />
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
				<a href="javascript:void(0)" type="button" class="btn" onclick="searchBillRechargeList();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
					<span class="icon iconfont icon-icon-search"></span>
					<span>查询</span>
				</a>
			</shiro:hasPermission>
	      <%--  <button id="btn_reset"  type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btnDel" onclick="clearAll();" value='<icms-i18n:label name="common_btn_clear"/>'>
				<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
			</button> --%>
			</td>
				</tr>
		   </table>
	   </div>
	</div>
</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridMainList">
				<table id="gridTableList" ></table>
				<div id="gridPagerList"></div>
			</div>
		</div>
	</div>
	</div>
</div>
<!-- 充值弹出框 -->
<div id="billRecharge_modle" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="billRechargForm">
				<input type="hidden" id="tenantMethod" name="tenantMethod" />
				<input type="hidden" id="tenantId" name="tenantId" />						
				<p>
					<i style="width:160px;text-align:right"><font><span style="color: red;">*</span>充值金额：</font></i>
					<input type="text" name="tenantName" id="amount" style="vertical-align:middle;" class="textInput" />
				</p>
				<p>
					<i style="width:160px;text-align:right"><font><span style="color: red;">*</span>充值类型：</font></i>
					<select id="fundTypeCode" class="selInput" name="tenant">
						<option value="1">现金</option>
						<option value="6">代金券</option>
					</select>
				</p>
				<p>
					<i style="width:160px;text-align:right"><font>描述：</font></i>
					<input type="text" name="tenantRemark" id="busiDesc" style="vertical-align:middle;" class="textInput" />
				</p>					
			<p class="winbtnbar btnbar1" style="text-align:right; width:250px; margin-left: 70px;margin-top: 20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="BillRechargeCloseBtn('billRecharge_modle')" style="margin-left:30px" >
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="billRechargeSaveBtn()" >
			</p>
		</form>
	</div>	
</body>
<script  type="text/javascript">  
	window.onload = function(){
	  	var d = new Date();
	    function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
	    var s = d.getFullYear().toString() + '-' +addzero(d.getMonth() + 1) + '-' + addzero(d.getDate())+' ' +addzero(d.getHours()) + ':'+addzero(d.getMinutes()) +':'+ addzero(d.getSeconds());
	    $('.textInputTime').val(s);
	    $('.Tab_tabCon').eq(0).show();
	    $('.WorkBtBg').find('.Tab_tabBt').click(function(){
	     var index = $(this).index();
	     $(this).addClass('tabBt_cur').siblings().removeClass('tabBt_cur');
	     $('.Tab_tabConBox').find('.Tab_tabCon').eq(index).show().siblings().hide();
	     })
	  };
	  
</script>
<script type="text/javascript">

</script>
</html>
	