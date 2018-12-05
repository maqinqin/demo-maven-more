<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/reports/common/js/createReport.js"></script> 
<script type="text/javascript" src="${ctx}/pages/reports/common/js/reportList.js"></script>

<script type="text/javascript">
	$(function() {
		initCmDeviceList();
	});
</script>
<style type="text/css">
.textInput{
    background-color: #f5f5f5;
    border: 1px solid #d5d5d5;
    box-shadow: 1px 1px 2px #ededed;
    color: #615d70;
    font-size: 12px;
    height: 22px;
    line-height: 22px;
    padding: 1px 4px 0 5px;
    width: 154px;
    margin-right :10px;
}
.sqlArea{
    color: #615d70;
    font-size: 12px;
    height: 25px!important;
    line-height: 25px;
    padding: 1px 4px 0 5px;
    margin-right :10px;
    background-color: #f5f5f5;
	border: 1px solid #d5d5d5;
	box-shadow: 1px 1px 2px #ededed;
	color: #615d70;
	font-size: 12px;
	height: 22px;
	line-height: 22px;
	padding: 1px 4px 0 5px;
	width: 154px;
	margin-right: 10px;
}
.Inputborder{display:inline-block; width:154px;/*  border:1px solid #d5d5d5; */ height:22px; line-height:22px; color:#615d70; /* background:#f5f5f5; */ /* padding:1px 4px 0 5px; */ box-shadow:1px 1px 2px #ededed;}
.pageFormContent i {width:90px; padding-right:0;}
#OaForm input{height:20px; line-height:20px; margin-left:4px; background-color: #f5f5f5;padding: 1px 4px 0px 5px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
#OaForm select{margin-left:4px;}
.btn_dd4{
	float:right;
	height:24px;
	font-size: 13px;
	text-align: center;
	border: 1px solid transparent;
	background-color: #1ab394;
	border-color: #1ab394;
	color: #FFF;
	border-radius: 2px;
	padding:0 20px;
}
.btn_dd4:hover{
	float:right;
	height:24px;
	
	font-size: 13px;
	text-align: center;
	border: 1px solid transparent;
	background-color: #18a689;
	border-color: #1ab394;
	color: #FFF;
	border-radius: 2px;
	padding:0 20px;
}
.sqlArea{height:18px; line-height:18px;}
</style>
</head>
<body class="main1">
	<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_report"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">
					<small> 
						<icms-i18n:label name="sys_title_reportDesc"></icms-i18n:label>
					</small>
				</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="right">
					<tr style=" height:55px; "align="right">
						<td class="tabBt" style="width:55px;"><icms-i18n:label name="sys_l_reportName1"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="reportName"
							id="reportName" type="text" class="textInput readonly" /></td>
						<td class="tabBt"></td>	
						<td class="tabCon"></td>
						<td class="tabBt"></td>	
						<td class="tabCon"></td>	
				</table>
				</div>
			</div>
			<div class="searchBtn_right" style="width:28%; float:left" >
				<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
							<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;">
								<span class="icon iconfont icon-icon-search"></span>
								<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
								<span class="icon iconfont icon-icon-clear1"></span>
								<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<shiro:hasPermission name="sbxxgl_sava">
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()">
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_match">
	                        <input type="hidden" id="matchFlag" name="matchFlag" value="1" />
	                        </shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_delete">
							<!-- <input type="button" class="Btn_Del" title="删除" onclick="deleteDevice()" /> -->
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission> 
						</td>
						</tr>
				</table>
			</div>
		</div>
			</form>
		</div>
		<div class="pageTableBg" style="">
			<div class="panel clear" id="deviceGridTable_div">
				<table id="deviceGridTable"></table>
				<table id="gridPager"></table>
			</div>
		</div>
		
	</div>
	<div id="updateDiv" class="div_center" style="display: none">
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_reportMag"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">
					<small id="s_updateTitle">
						<icms-i18n:label name="sys_title_reportManAdd"></icms-i18n:label>
					</small>
				</div>
				
			</h1>
		</div>
		<input id="reportType" type="hidden"/>
		<input id="reportID" type="hidden"/>
		<form id="createReportForm">
	<div id="main">
		<div class="searchBg searchBg2 searchBg3"style="padding-bottom:14px;">
			<div id="name" style="margin-bottom :10px; margin-top :10px;margin-left :20px;">
			<icms-i18n:label name="sys_l_rnKeyWord"></icms-i18n:label> : <input id="reportNameKey" name="reportNameKey" type="text" class="textInput readonly" />
			<icms-i18n:label name="sys_l_reportName"></icms-i18n:label> : <input type="text" id="reportNameValue" name="reportNameValue"class="textInput readonly" />
		</div>
		<div id="desc" style="margin-bottom :10px; margin-top :10px;margin-left :20px;">
			<icms-i18n:label name="sys_l_rdKeyWord"></icms-i18n:label> : <input type="text" id="descKey" name="descKey" class="textInput readonly" />
			<icms-i18n:label name="sys_l_rDesc"></icms-i18n:label> : <input type="text" id="descValue" name="descValue" class="textInput readonly" />
		</div>
		</div>
		<div class="searchBg searchBg2 searchBg3" style="margin-top:20px; overflow:hidden;">
			<div id="conditions" style=" border:1px solid #DCD; width:96%; margin:0 auto;">
			<ul id="conditionsUl">
				<li class="btnbar1" style="list-style:none; margin-left :20px;margin-top :10px;margin-bottom :10px; overflow:hidden;">
					<font  class="zjx" style="float:left;"><icms-i18n:label name="sys_l_screenCond"></icms-i18n:label> :&nbsp;&nbsp;</font>
					<!-- <select id="conType" name="conType" class="selInput" style="width:150px; " >
						<option value="text">文本框</option>
						<option value="select">下拉框</option>
						<option value="time">时间框</option>
						<option value="selectSql">自动下拉框</option>
					</select>
					<input type="button" value="添加条件" onclick="addConLi();"/> -->
					<input type="button" value='<icms-i18n:label name="sys_l_report_text"/>' onclick="addTextConLi();"class="btn btn_dd2" style="margin-right: 5px; margin-left:0;"/>
					<input type="button" value='<icms-i18n:label name="sys_l_report_select"/>' onclick="addSelectConLi();"class="btn btn_dd2" style="margin-right: 5px; margin-left:0;"/>
					<input type="button" value='<icms-i18n:label name="sys_l_report_time"/>' onclick="addTime();"class="btn btn_dd2" style="margin-right: 5px; margin-left:0;"/>
					<input type="button" value='<icms-i18n:label name="sys_l_report_selectSql"/>' onclick="addSelectSqlConLi();"class="btn btn_dd2" style="margin-right: 5px; margin-left:0;"/>
				</li>
				<li style="list-style:none;" id = "conditionsLI">
					<div id="div0_0" style="padding-left:20px;">
						<ul style="list-style:none;">
							<li style="margin-bottom :10px; margin-top :10px;list-style:none;" >
								<span class="zjx"><icms-i18n:label name="sys_l_report_conKey"/> : </span><input type="text" id="conKey_0" name="conKey" class="textInput readonly" /> 
								<span class="zjx"><icms-i18n:label name="sys_l_report_con_describe"/> : </span><input type="text" id="conValue_0" name="conValue" class="textInput readonly" />
								<input id="deletecon" type="button" value='<icms-i18n:label name="sys_l_report_delete_condition"/>' onclick="delCon(this);"class="unit unit2" style="margin-right: 5px; margin-left:0;"/>
								<input type="button" value='<icms-i18n:label name="sys_l_report_add_attribute"/>' onclick="addPro(this);"class="unit unit2" style="margin-right: 5px; margin-left:0;"/>
								<input id="conType_0" type="hidden" name="conType" value="select">
								<input type="checkbox" id="isSqlParam_0" name="isSqlParam" ><icms-i18n:label name="sys_l_report_sql_need"/>
							</li>
						</ul>
					</div>
					<div id="div0_1"> 
						<ul>
							<li style="margin-bottom :10px; margin-top :20px;margin-left:0; list-style:none;">
								<span class="zjx"><icms-i18n:label name="sys_l_report_attribute_key"/> : </span><input type="text" id="conLi0_pro0_key" name="proKey" class="textInput readonly" />
								<span class="zjx"><icms-i18n:label name="sys_l_report_attribute_describe"/> : </span><input type="text" id="conLi0_pro0_value" name="proValue" class="textInput readonly" /> 
								<input type="button" value='<icms-i18n:label name="sys_l_report_delete_attribute"/>' onclick="delPro(this);"class="unit unit2" style="margin-right: 5px; margin-left:0;"/>
							</li>
						</ul>
					</div>
				</li>
				
			</ul>
		</div>
		</div>
		<div class="searchBg searchBg2 searchBg3" style="margin-top:20px; overflow:hidden;">
			<div id="sql" style=" border:1px solid #DCD; width:96%; margin:0 auto;">
			<ul id = "sqlUL" style="margin-top :10px; margin-bottom :10px;">
				<li style="list-style:none;margin-left :20px;" class="btnbar1">
					<span class="zjx" style="float:left;"><icms-i18n:label name="sys_l_sqlSent"></icms-i18n:label> :&nbsp;&nbsp;</span>
					<input type="button" value='<icms-i18n:label name="sys_l_report_add_sql"/>' onclick="addSql();" id = "addSqlButton"class="btn btn_dd2" style="margin-right: 5px; margin-left:0;"/><br>
				</li>
				<li style="list-style:none; margin-bottom :10px; margin-top :10px;margin-left :20px;" id = "sqlLI" >
					<span class="zjx"><icms-i18n:label name="sys_l_report_SQL_key"/> : </span><input type="text" id="sqlKey_0" name="sqlKey" class="textInput readonly" />
					<span class="zjx"><icms-i18n:label name="sys_l_report_SQL_sentence"/> : </span><textarea id="sqlValue_0" name="sqlValue" class="sqlArea textInput"></textarea>
					<input type="button" value='<icms-i18n:label name="sys_l_report_delete_sql"/>' onclick="delSqlStr(this);"class="unit unit2" style="margin-right: 5px; margin-left:0;"/>
				</li>
			</ul>
		</div>
		</div>
		<div class="searchBg searchBg2 searchBg3" style="margin-top:20px; overflow:hidden;"><div id="jasper" style="margin-bottom :10px; margin-top :10px;margin-left :20px;"><icms-i18n:label name="sys_l_jaspFilePath"></icms-i18n:label>：<input type="text" style="width: 419px;" id="jasperPath" name="jasperPath" class="textInput readonly" /></div></div>
		<div class="searchBg searchBg2 searchBg3" style="margin-top:20px; overflow:hidden;">
			<div id="tips" style="margin-bottom :10px; margin-top :10px;margin-left :20px;">
				<p class="zjx"><icms-i18n:label name="sys_l_notice"></icms-i18n:label></p>
			</div>
		</div>
		
		<p class="btnbar btnbar1" style="text-align:right;">
		<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView()" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left:0;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px; margin-left: 5px;" />
		</p>
	</div>

</form>
	</div>
</body>
</html>
