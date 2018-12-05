<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<style type="text/css">
.table_show{border:1px solid #e1e1e1; margin-top:20px; margin-left:40px; clear:both;width:90%}
.table_show td{height:23px; line-height:23px; padding:4px 16px; color:#646964; border:1px solid #e1e1e1;}
a{
text-decoration:none;
}
</style>

<div id = "dialog-switch" class="panel clear" style="margin-left:-20px; margin-top:0px; margin-right:0px; margin-bottom: 30px;overflow: auto">
  	<table id="switchInfo_list"  class="table_show" ></table>
  	<p style="margin-top: 10px;margin-bottom:10px;padding-left:0;" class="btnbar1">
			<input type="button" class="btn btn_dd2 btn_dd3" onclick="closeVsView()" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' />
			<input type="button" class="btn btn_dd2 btn_dd3" onclick="savevSwitchInfo()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>'/>
	</p>
</div>



