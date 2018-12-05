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
<script type="text/javascript" src="${ctx}/pages/dic/js/dicType.js"></script>
<style type="text/css">
</style>
<style>
#add_update_DicType_Div p{width:280px; padding:0; padding-left:16px;}
#add_update_DicType_Div p i{width:80px; text-align:left;}
#add_update_DicType_Div label{display:block; padding-left:82px;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_dataDic"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="com_l_name"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="dicTypeName_s" name="dicTypeName_s" class="textInput"/>
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td  align="center" colspan="2" width="30%" style="line-height:60px;">
					<shiro:hasPermission name="user:list">  
						<a href="javascript:void(0)" type="button" class="btn" onclick="searchDicType();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					</shiro:hasPermission>
					<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()">
						<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					</button>
					<shiro:hasPermission name="user:save">
			            <a href="javascript:void(0)" type="button" class="btn" onclick="addDicType('insert');"  title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
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
			<div class="panel clear" id="gridMain">
			<table id="gridTable" ></table>
					<div id="gridPager"></div>
		</div>
		</div>
				
	   </div>
		
		<div id="add_update_DicType_Div" style="display: none">
			<form action="" method="post" id="add_update_DicType_Form">
			<div  id="updateTab"  class="pageFormContent">
			 <p>
				<i><font color="red">*</font><icms-i18n:label name="com_l_name"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="dicTypeName" name="dicTypeName" />
			 </p>
			 <p>
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>:</i>
				<textarea style="width:162px; height:40px; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"" id="remark" name="remark"></textarea>
			 </p>
			</div>
		</form>
		<div class="winbtnbar clear btnbar1" style="text-align:right; margin-bottom:20px; margin-right:33px;">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelDicType" name="cencelDicType" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'  style="margin-right: 5px; margin-left:0;"  onclick="closeView('add_update_DicType_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveDicType" name="saveDicType" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0;"  onclick="saveDicTypeBtn()">
	    </div>
	</div> 
	
<input type="hidden" id="dicTypeCode" name="dicTypeCode" value="" />
<input type="hidden" id="createUser" name="createUser" value="" />
<input type="hidden" id="createDatetime" name="createDatetime" value="" />
<input type="hidden" id="dicTypeNameCheck" name="dicTypeNameCheck" /><!-- 字典类型名称 -->
<input type="hidden" id="dicTypeNameMethod" name="dicTypeNameMethod" /><!-- 方法名称 -->
</body>
</html>
