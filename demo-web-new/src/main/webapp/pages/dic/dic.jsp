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
<script type="text/javascript" src="${ctx}/pages/dic/js/dic.js"></script>
<style type="text/css">
#add_update_Dic_Div label{display:block; padding-left:106px;}
#add_update_Dic_Div i {text-align:left; padding-right:3px;}
#add_update_Dic_Div p{width:600px; margin-bottom:5px; margin-left:18px;  height:40px;}
#add_update_Dic_Div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
</style>
</head>
<body class="main1">
	<div class="content-main clear"  style="overflow-x:hidden;">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_dic"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:68%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="sys_l_dicName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="dicName_s" name="dicName_s" class="textInput"/>
					</td>
				    <td class="tabBt" style="width:90px;"><icms-i18n:label name="sys_l_dicCode"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="dicCode_s" name="dicCode_s" class="textInput"/>
					</td>
					 <td class="tabBt" style="width:110px;"><icms-i18n:label name="sys_l_dicType"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="dicTypeCode_s" name="dicTypeCode_s" class="textInput"/>
					</td>
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:26%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
					<shiro:hasPermission name="user:list">  
						<a href="javascript:void(0)" type="button" class="btn" onclick="searchDic();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					</shiro:hasPermission>
					<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  onclick="clearAll()">
						<span class="icon iconfont icon-icon-clear1"></span>
							<span>清空</span>
					</button>
					<shiro:hasPermission name="user:save">
			            <a href="javascript:void(0)" type="button" class="btn" onclick="addDic('insert');"  title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
			            	<span class="icon iconfont icon-icon-add"></span>
							<span>添加</span>
			            </a>
			        </shiro:hasPermission>
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
			<div class="pageTableBg" style="width:100%;">
			<div class="panel clear" id="gridMain">
			<table id="gridTable" ></table>
					<div id="gridPager"></div>
		</div>
		</div>
		</div>
		
				
	   </div>
		
		<div id="add_update_Dic_Div" style="display: none">
			<form action="" method="post" id="add_update_Dic_Form">
			<div  id="updateTab"  class="pageFormContent">
			 <p>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_blogsDicType"></icms-i18n:label>:</i>
				<icms-ui:dic id = "dicTypeCode" name="dicTypeCode"
									sql="select DIC_TYPE_NAME value,DIC_TYPE_NAME name from ADMIN_DICTYPE order by DIC_TYPE_NAME"
									showType="select" attr="class='selInput'" />
			 </span>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicName"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="dicName" name="dicName" />
			 </span>
			 </p>
			 <p>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicCode"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="dicCode" name="dicCode" />
			 </span>
			 <!--p>
				<i><font color="red">*</font>ATTR:</i>
				<input class="textInput"  type="text" id="dicName" name="dicName" />
			 </p>  -->
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_dicOrder"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="orderNum" name="orderNum" />
			 </span>
			 </p>
			 <p>
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>:</i>
				<textarea id="remark" name="remark" style="width:164px; height:40px; border: 1px solid #d5d5d5; background-color: #f5f5f5;"></textarea>
			 </p>
			</div>
		</form>
		<div class="winbtnbar clear btnbar1" style="text-align:right; width:586px; margin-bottom:20px;">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelDic" name="cencelDic" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left:0;"  onclick="closeView('add_update_Dic_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveDic" name="saveDic" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;"  onclick="saveDicBtn()">
	    </div>
	</div> 
	
<input type="hidden" id="dicId" name="dicId" value="">
<input type="hidden" id="createUser" name="createUser" value="">
<input type="hidden" id="createDatetime" name="createDatetime" value="">
</body>
</html>
