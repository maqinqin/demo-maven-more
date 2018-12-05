<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<style type="text/css">
#serviceModelLoad p{width:280px; padding:0; padding-left:16px;}
#serviceModelLoad p i{width:80px; text-align:left;}
#serviceModelLoad label{display:block; padding-left:83px;}
</style>
<div id="serviceModel" style="display: none;">
	<table width="97%">
		<tr width="100%">
			<td align="right" class="btnbar1" style="padding-left:0;">
				<shiro:hasPermission name="model:add">
					<input  class="btn btn_dd2 btn_dd3"  type="button" onclick="loadModel('')" title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-right:0;"/>				
				</shiro:hasPermission>
				<shiro:hasPermission name="model:update">
					<input type="hidden" id="updateModelFlag" value="1"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="model:delete">
					<input type="hidden" id="deleteModelFlag" value="1"/>
				</shiro:hasPermission>
			</td>
		</tr>
		<tr height="10px"><td></td></tr>
	</table>

	<div class="gridMain" id="serviceModelGridDiv">
		<table id="serviceModelGridTable"></table>
		<div id="serviceModelGridPager"></div>
	</div>
</div>

<div id="serviceModelLoad" style="display: none;">
	<form action="" id=serviceModelForm>
		<input type="hidden" id=serviceIdf name="serviceIdf" />
		<input type="hidden" id=serviceFlowId name="serviceFlowId" />
		<input type="hidden"	id=isActivef name="isActivef" />
		<div  id="serviceModelTab"  class="pageFormContent">	
			<p>
				<i><font color="red">*</font><icms-i18n:label name="com_l_operaType"/>：</i>
				<icms-ui:dic name="operModelType" id="operModelType" sql="SELECT '1' as id, sr.SR_TYPE_NAME as name,sr.SR_TYPE_MARK as value ,'1' as kind from BM_SR_TYPE sr where sr.IS_ACTIVE='Y' and sr.SR_TYPE_CODE='L'"
						showType="select"  attr="class='selInput'"/>
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="cs_l_operaFlow"/>：</i>
				<icms-ui:dic name="flowId" id="flowId"	sql="select '1' as id ,CONCAT(model.MODEL_NAME,'(',date_format(model.CREATE_DATE,'%y-%m-%d %H:%i:%S'),')') as name,model.MODEL_ID as value,'1' AS kind  from BPM_MODEL model where model.IS_ACTIVE='Y' AND TYPE_ID='4' ORDER BY model.CREATE_DATE desc "
						showType="select" attr="class='selInput'" />
			</p>
			<div class="nowrap right"></div>
		</div>
		<div class="winbtnbar btnbar1" style="width:294px; text-align:right; padding-left:0; ">
				<input type="button"   title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('serviceModelLoad')" style="margin-right: 21px; margin-left:5px;" />
				<input type="button"  class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveServiceModel()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left:0px;" />
			</div>
	</form>
</div>