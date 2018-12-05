<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <script type="text/javascript" src="${ctx }/scripts/jquery-1.8.0.min.js"></script> --%>
<script type="text/javascript" src="${ctx }/pages/reports/common/js/createReport.js"></script>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript">
	function init() {
		$("#btn_reset").trigger("click");//触发reset按钮
		initCloudRequestList();
		//自适应宽度
		$("#gridTable").setGridWidth($("#div_list1").width());
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#div_list1").width());
	    });
	}
</script>
<title>创建报表</title>
<style>
.btn_dd4{
	float:right;
	height:24px;
	line-height:24px;
	font-size: 13px;
	text-align: center;
	border: 1px solid transparent;
	background-color: #1ab394;
	border-color: #1ab394;
	color: #FFF;
	border-radius: 2px;
}
</style>
</head>
<body>

<form id="createReportForm">
	<div id="main">
		<div id="name">
			<icms-i18n:label name="sys_l_report_keyName"/> : <input type="text" id="reportNameKey" name="reportName">
			<icms-i18n:label name="sys_l_reportName"/> : <input type="text" id="reportNameValue" name="reportName">
		</div>
		<div id="desc">
			<icms-i18n:label name="sys_l_report_key_describe"/> : <input type="text" id="descKey" name="desc">
			<icms-i18n:label name="sys_l_report_describe"/> : <input type="text" id="descValue" name="desc">
		</div>
		<div id="conditions">
			<ul id="conditionsUl">
				<li>
					<font  class="zjx"><icms-i18n:label name="sys_l_screenCond"/> : </font>
					<select id="conType" name="conType" >
						<option value="text"><icms-i18n:label name="sys_l_report_text"/></option>
						<option value="select"><icms-i18n:label name="sys_l_report_select"/></option>
						<option value="time"><icms-i18n:label name="sys_l_report_time"/></option>
						<option value="selectSql"><icms-i18n:label name="sys_l_report_selectSql"/></option>
					</select>
					<input type="button" value='<icms-i18n:label name="sys_l_report_btn_addCon"/>' onclick="addConLi();"/>
				</li>
				<li>
					<div id="div0_0">
						<ul>
							<li class="KeyBtn">
								<span class="zjx"><icms-i18n:label name="sys_l_report_conKey"/> : </span><input type="text" id="conKey_0" name="conKey"> 
								<span class="zjx"><icms-i18n:label name="sys_l_report_con_describe"/> : </span><input type="text" id="conValue_0" name="conValue">
								<input type="button" value='<icms-i18n:label name="sys_l_report_delete_condition"/>' onclick="delCon(this);"/>
								<input type="button" value='<icms-i18n:label name="sys_l_report_add_attribute"/>' onclick="addPro(this);"/>
								<input id="conType_0" type="hidden" name="conType" value="select">
								<input type="checkbox" id="isSqlParam_0" name="isSqlParam" ><icms-i18n:label name="sys_l_report_sql_need"/></input>
							</li>
						</ul>
					</div>
					<div id="div0_1"> 
						<ul>
							<li class="KeyBt" style="margin-left:0;">
								<span class="zjx"><icms-i18n:label name="sys_l_report_attribute_key"/> : </span><input type="text" id="conLi0_pro0_key" name="proKey">
								<span class="zjx"><icms-i18n:label name="sys_l_report_value"/> : </span><input type="text" id="conLi0_pro0_value" name="proValue"> 
								<input type="button" value='<icms-i18n:label name="sys_l_report_delete_attribute"/>' onclick="delPro(this);"/>
							</li>
						</ul>
					</div>
				</li>
				
			</ul>
		</div>
		<div id="sql">
			<ul>
				<li>
					<span class="zjx">SQL : </span><input type="button" value='<icms-i18n:label name="sys_l_report_add_sql"/>' onclick="addSql(this);"/><br>
				</li>
				<li>
					<span class="zjx"><icms-i18n:label name=""/><icms-i18n:label name="sys_l_report_SQL_key"/> : </span><input type="text" id="sqlKey_0" name="sqlKey"/>
					<span class="zjx"><icms-i18n:label name=""/><icms-i18n:label name="sys_l_report_SQL_sentence"/> : </span><textarea id="sqlValue_0" name="sqlValue"></textarea>
					<input type="button" value='<icms-i18n:label name="sys_l_report_delete_sql"/>' onclick="delSqlStr(this);"/>
				</li>
			</ul>
		</div>
		<div id="jasper">Jasper文件路径：<input type="text" id="jasperPath" name="jasperPath" /></div>
		<div id="tips">
			<p class="zjx">请在JasperReport中设置一下三个Parameter ： SERVERNAME、SERVERPORT和CONTEXTPATH.</p>
		</div>
	
		<input type="button" value="提交" onclick="save();"/>
		<input id="btn_reset" type="reset" title="清空" />
	</div>

</form>

</body>
</html>