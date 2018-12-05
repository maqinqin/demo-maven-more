<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/vMware.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<div class="content-main" id="content_pm_div" style="margin-top:15px;">
	<form >
	<table width="95%" align="center" style="margin-top:15px;">
		<tr>
			<td width="8%"><icms-i18n:label name="res_l_comput_hostName"/>：</td>
			<td width="19%" align="left">
				<input id="cmNameInput" class="text_Input" type="text"/>
			</td>
			<td width="6%" ><icms-i18n:label name="res_l_ip"/>：</td>
			<td width="19%" align="left">
				<input id="ipAdrInput" class="text_Input" type="text"/>
			</td>
			<td  align="right"  width="30%" style="padding-left:20px;">
				<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="common_btn_query"/>' class="btn" onclick="search_pm();" style="vertical-align:middle" value='<icms-i18n:label name="common_btn_query"/>'>
					<span class="icon iconfont icon-icon-search"></span>
	  				<span>查询</span>
				</a>
				<!-- <input type="reset" title="清空" class="btn_reset" style="vertical-align:middle"/> -->
				<button class="btn" type="reset"  title='<icms-i18n:label name="common_btn_clear"/>' value='<icms-i18n:label name="common_btn_clear"/>'>
					<span  class="icon iconfont icon-icon-clear1"></span>
	  				<span>清空</span>
				</button>
	        </td>
		</tr>
	</table>
	</form>
	<div id="gridTable_pm_div"  style="margin:10px 2%;border:1px solid #d5d5d5;">
		<table id="gridTable_pm" style="width:95%; margin:0 auto;">
			
		</table>
		<table id="gridPager_pm" style="width:95%; margin:0 auto;">
		</table>
	</div>
</div>
<!--物理、虚拟机通知列表  -->
	<div id="gridTable_notiLog_div"  style="border:1px solid #d5d5d5; width:96%; margin:25px auto 0">
		<table id="gridTable_notiLog">
		</table>
		<table id="gridPager_notiLog">
		</table>
	</div>
