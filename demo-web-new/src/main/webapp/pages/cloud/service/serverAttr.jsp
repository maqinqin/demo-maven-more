<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/pages/cloud/service/js/serverAttr.js?version=1.0"></script>
<style type="text/css">
#attrOptionDiv p{width:280px; padding:0; padding-left:16px;}
#attrOptionDiv p i{width:80px; text-align:left;}
#attrOptionDiv label{display:block; padding-left:83px;}
#serviceModel.ui-dialog-content{padding-top:0;}
#serviceAttr.ui-dialog-content{padding-top:0;}
</style>
<!-- 展示属性信息 -->
<div id="serviceAttr" style="display: none;" > 
	<table width="100%">
		<tr width="100%">
			<td align="right"></td>
		</tr>
		<tr width="100%">
			<td align="right" class="btnbar1" style="width:985px; padding-right:15px;">
				<shiro:hasPermission name="attr:add">
					<input type="button" class="btn btn_dd2 btn_dd3" onclick="loadAttr('')"	title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'/>
				</shiro:hasPermission>
				<shiro:hasPermission name="attr:update">
					<input type="hidden" id="updateAttrFlag" value="1"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="attr:delete">
					<input type="hidden" id="deleteAttrFlag" value="1"/>
				</shiro:hasPermission>
			</td>
		</tr>
		<tr width="100%" height="10px">
			<td align="right"></td>
		</tr>
	</table>

	<div class="panel clear" id="serviceAttrGridDiv">
		<table id="serviceAttrGridTable"></table>
		<div id="serviceAttrGridPager"></div>
	</div>
</div>

<div id="serviceAttrLoad"	style="display: none;" width="100%">
	<form action="" id=serviceAttrForm>
	<input type="hidden"  id=cloudServiceId name="cloudServiceId" />
	<div id="attrDiv" class="pageFormContent">
		<input type="hidden" id=attrMethod name="attrMethod" />
		<input type="hidden" id=cloudIsActive name="cloudIsActive" />
		<input type="hidden" id=cloudAttrId name="cloudAttrId" />
		<p style="width: 480px;">
			<i><font color="red">*</font><icms-i18n:label name="Sname"/>：</i>
			<input id=cloudAttrName name="cloudAttrName" class="textInput" style="width:260px;" />
		</p>
		<p style="width: 480px;">
			<i><icms-i18n:label name="cs_l_paramZhName"/>：</i>
			<input id=cloudAttrCname name="cloudAttrCname" class="textInput"  style="width:260px;"/>
		</p>
		<p style="width: 480px;">
			<i><font color="red">*</font><icms-i18n:label name="cs_l_param_type"/>：</i>
			<icms-ui:dic id="attrType" name="attrType" kind="ATTR_TYPE"  showType="flatSelect"  attr="style='width: 86px;'" /> 
		</p>
		<p style="width: 480px;">
			<i><font color="red">*</font><icms-i18n:label name="cs_l_paramCate"/>：</i>
			<icms-ui:dic id="attrClass" name="attrClass" kind="ATTR_CLASS"  showType="flatSelect"  click="initAttrTypeSelect(this.getAttribute('value'))"/> 
		</p>
		<p style="width: 480px;">
			<i><icms-i18n:label name="cs_l_default"/>：</i>
			<input id=cloudDefVal name="cloudDefVal" class="textInput"  showType="flatSelect" style="width:260px;"/>
		</p>
		<p style="width: 350px;">
			<i><icms-i18n:label name="cs_l_isUserVisible"/>：</i>
			<icms-ui:dic id="isVisible" name="isVisible" kind="YES_OR_NO"  showType="flatSelect"  attr="style='width: 86px;'"/>
		</p>
		<p style="width: 480px;">
			<i><icms-i18n:label name="cs_l_isRequire"/>：</i>
			<icms-ui:dic id="isRequire" name="isRequire" kind="YES_OR_NO"  showType="flatSelect"  attr="style='width: 86px;'"/>
		</p>
		<p style="width: 870px;">
			<i><icms-i18n:label name="cs_l_writRule"/>：</i>
			<textarea style="width: 740px;height:40px;resize: none;margin-right:0;" id=cloudRemark name="cloudRemark" rows="" cols="" class="textInput"></textarea>
		</p>
		<p style="width: 870px;" id="attrListSqlText"> 
			<i><icms-i18n:label name="cs_l_querySQL"/>：</i>
			<textarea style="width: 740px;height:40px;resize: none; margin-right:0;" id="attrListSql" name="attrListSql" rows="" cols="" class="textInput"></textarea>
		</p>
		<p id='optionsInfoTop' align="right" style="width:806px">
			<!-- <input type="button" class="btn_gray" title='选项信息' value='选项信息'> -->
			
			<span align="right" style="font-weight:bold"><icms-i18n:label name="cs_l_optInfo"/></span>
			<input type="button" class="btn_add_s" onclick="createAttrOption()" title="添加选项" />
			
		</p>
		
		<div class="panel clear" id='optionsInfo' style=" margin-left:0;width: 90%;">
				<table id="serviceAttrSelGridTable"></table>
				<div id="serviceAttrSelGridPager"></div>
		</div>
		<div class="nowrap right"></div>  
		<div class="btnbar btnbar1" style="width:858px;" >	 
			 <input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('serviceAttrLoad')" />
			 <input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveAttrValidate()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'/>
		</div>	 
	</div>
	</form>
</div>

<div id='viewOptionsInfo' style="display: none;">
	<div  class="panel clear"  style=" margin-left:0;width: 100%; height:98%;">
	<i ><icms-i18n:label name="cs_l_optInfo"/></i>
	<table id="viewServiceAttrSelGridTable"></table>
	<div id="viewServiceAttrSelGridPager"></div>
	</div>
</div>
		
<div id="attrOptionDiv"   style="display: none; height: 180px;">
	<form action="" method="post" id="attrForm">
		<div class="pageFormContent">
		<input type="hidden" id=attrSelId name="attrSelId" />
		<input type="hidden" id="attrRowId" name="attrRowId" />
		<input type="hidden" id="attrOptionMethod" name="attrOptionMethod" />
		<p>
			<i><font color="red">*</font><icms-i18n:label name="cs_l_optName"/>：</i>	
			<input type="text" id="KEY" name="KEY" class="textInput" />
		</p>
		<p>
			<i><font color="red">*</font><icms-i18n:label name="cs_l_optValue"/>：</i>
			<input type="text" id="value" name="value" class="textInput" />
		</p>
		<div class="btnbar btnbar1" style="width:261px;" >	 
			 <input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('attrOptionDiv')" style="margin-right:16px;" />
			 <input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveAttrOption()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'/>
		</div>	
		</div>
	</form>
</div>