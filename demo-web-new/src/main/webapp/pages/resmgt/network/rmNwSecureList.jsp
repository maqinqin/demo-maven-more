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
<script type="text/javascript" src="${ctx}/pages/resmgt/network/js/rmNwSecureList.js"></script>
<style type="text/css">
#updatesSecureArea p{width:283px; padding:0;}
#updatesSecureArea p i{width:100px; text-align:left; margin-bottom:20px;}
#updatesSecureArea label{display:block; padding-left:110px;}

#add_secure_Tier_Div p{width:283px; padding:0;}
#add_secure_Tier_Div p i{width:100px; text-align:left; margin-bottom:20px;}
#add_secure_Tier_Div label{display:block; padding-left:110px;}
#secureSreaName-error{position: relative;bottom: 20px;right: 17px;width: 100px;}
</style>
</head>
<body  onload="initSecureArea()" class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_secArea"></icms-i18n:label></div>
					<div class="WorkSmallBtBg">
					<small>
					 <icms-i18n:label name="bim_title_secDescribe"></icms-i18n:label>
					</small>
					</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">	
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:64%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:40px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="bim_l_secAreaName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="secure_name" name="secure_name" class="textInput"/>
					</td>
					 <td class="tabBt" style="width:50px;"></td>
					 <td class="tabCon"></td>
				   <td class="tabBt" ></td>
					 <td class="tabCon"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:32%; float:left; padding-left:2%;" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:40px;">
							<td >
					  <a href="javascript:search()" type="" class="btn" onclick="search();return false;" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
					  	<span class="icon iconfont icon-icon-search"></span>
						<span>查询</span>
					  </a>
					  <button type="button" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="cancelSecureArea();" >
					  	<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					  </button>
					 <shiro:hasPermission name="secureArea_add">
					    <a href="javascript:void(0)" type="button" class="btn"  onclick="addSecureArea();" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
					    	<span class="icon iconfont icon-icon-add"></span>
						<span>添加</span>
					    </a>
			       	</shiro:hasPermission>
			         <shiro:hasPermission name="secureArea_delete">
			             <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			            </shiro:hasPermission>
			            <shiro:hasPermission name="secureArea_update">
			           <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			           </shiro:hasPermission>
			            <shiro:hasPermission name="secureTier">
			           <input type="hidden"  id="clickFlag" name="clickFlag" value="1"/>
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
		
		<div id="add_secure_Area_Div" style="display: none" >
			<form action="" method="post" id="add_secure_Area_Form">
			<div  id="updatesSecureArea"  class="pageFormContent">
			<p>
				<i style="width:50px;margin-left:40px;"><font color="red">*</font><icms-i18n:label name="bim_l_secAreaName"></icms-i18n:label>:</i>
				<input type="text" id="secureSreaName" name="secureSreaName" class="textInput"/>
			</p>
			<p>
				<i><font color="red">*</font>安全区域类型：</i>
				<icms-ui:dic name="secureAreaType" id="secureAreaType" sql="SELECT dic.DIC_ID as id, dic.DIC_NAME as name,dic.DIC_CODE as value ,'1' as kind from admin_dic dic where dic.DIC_TYPE_CODE = 'SECURE_AREA_TYPE' order by dic.ORDER_NUM "
							 showType="select"  attr="class='selInput'"/>
			</p >
			 <p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px;">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelSecureArea" name="cencelSecureArea" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 32px; margin-left:0;"  onclick="closeView('add_secure_Area_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveSecureArea" name="saveSecureArea" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;"  onclick="saveSecureAreaBtn()">
				</p>
				</div>
				</form>
			</div>
	<div id="secureTierDiv" style="display: none;">
	    <table width="100%">
		<tr>
			<td  align="right" style="padding-right: 12px;padding-bottom: 5px;">
				     <shiro:hasPermission name="secureTier_add">
					<a href="javascript:void(0)"  class="btn"  type="button" onclick="addSecureTier('')" title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-left:679px; color:#fff;">
						<span class="icon iconfont icon-icon-add"></span><span>添加</span>
					</a>	
					 </shiro:hasPermission>		
					 <shiro:hasPermission name="secureTier_update">
			       <input type="hidden" id="updateModelFlag" name="updateModelFlag" value="1"/>
			       </shiro:hasPermission>
			        <shiro:hasPermission name="secureTier_delete">
				  <input type="hidden" id="deleteModelFlag"  name="deleteModelFlag" value="1"/>
				  </shiro:hasPermission>
			 </td>
		</tr>
		
	</table>

	<div class="gridMain" id="serviceModelGridDiv">
		<table id="serviceModelGridTable"></table>
		<div id="serviceModelGridPager"></div>
	</div>
   </div>
	<div id="add_secure_Tier_Div" style="display: none" >
			<form action="" method="post" id="add_secure_Tier_Form">
			<div  id="updatesSecureTier"  class="pageFormContent">
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_secTierName"></icms-i18n:label>:</i>
			
				<input type="text" id="secureTierName" name="secureTierName" class="textInput"/>
				
			</p>
			 <p class="winbtnbar btnbar1">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelSecureArea" name="cencelSecureArea" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left:0;"  onclick="closeView('add_secure_Tier_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveSecureTier" name="saveSecureTier" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;"  onclick="saveSecureTierBtn()">
				</p>
				</div>
				</form>
			</div>
	<input type="hidden" id="secureId" name="secureId"/>
	<input type="hidden"  id="secureArea" name="secureArea"/>
	<input type="hidden"  id="secureTierId"  name="secureTierId"/>
	
	

</body>
</html>
