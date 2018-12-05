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
<script type="text/javascript" src="${ctx}/pages/log/js/log.js"></script>
<style type="text/css">
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_log"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="com_l_moduleName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="moduleName_s" name="moduleName_s" class="textInput"/>
					</td>
				    <td class="tabBt"><icms-i18n:label name="com_l_operaType"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="operateName_s" name="operateName_s" class="textInput"/>
					</td>
				    <td class="tabBt"><icms-i18n:label name="sys_l_operaCont"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="operateContent_s" name="operateContent_s" class="textInput"/>
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
						<a href="javascript:void(0)" type="button" class="btn" onclick="searchLog();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					</shiro:hasPermission>
					<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()">
						<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					</button>
			        <shiro:hasPermission name="user:save"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:delete"> 
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			        </shiro:hasPermission>
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
		
		<div id="add_update_Dic_Div" style="display: none">
			<form action="" method="post" id="add_update_Dic_Form">
			<div  id="updateTab"  class="pageFormContent">
			 <p>
				<i><font color="red">*</font><icms-i18n:label name="sys_l_blogsDicType"></icms-i18n:label>:</i>
				<icms-ui:dic id = "dicTypeCode" name="dicTypeCode"
									sql="select DIC_TYPE_NAME value,DIC_TYPE_NAME name from ADMIN_DICTYPE "
									showType="select" attr="class='selInput'" />
			 </p>
			 <p>
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicName"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="dicName" name="dicName" />
			 </p>
			 <p>
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicCode"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="dicCode" name="dicCode" />
			 </p>
			 <!--p>
				<i><font color="red">*</font>ATTR:</i>
				<input class="textInput"  type="text" id="dicName" name="dicName" />
			 </p>  -->
			 <p>
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicOrder"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="orderNum" name="orderNum" />
			 </p>
			 <p>
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>:</i>
				<textarea rows="6" cols="35" id="remark" name="remark"></textarea>
			 </p>
			</div>
		</form>
		<div class="winbtnbar clear">
				<input type="button"  class="btn_ok"  id="saveDic" name="saveDic" title="保存"  style="margin-right: 5px;margin-left: 5px;"  onclick="saveDicBtn()">
				<input type="button"  class="btn_cancel"  id="cencelDic" name="cencelDic" title="取消"  style="margin-right: 5px;"  onclick="closeView('add_update_Dic_Div')">
	    </div>
	</div> 
	<div id="log_detail_div" style="display: none;">
		<textarea id="log_detail_text" style="width:490px; height:279px;" readonly="readonly"></textarea>
	</div> 
	
<input type="hidden" id="dicId" name="dicId" value="">
<input type="hidden" id="createUser" name="createUser" value="">
<input type="hidden" id="createDatetime" name="createDatetime" value="">
</body>
</html>
	