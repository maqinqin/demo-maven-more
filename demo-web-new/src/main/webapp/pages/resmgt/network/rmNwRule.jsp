<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>IP分配规则管理</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type='text/javascript' src='${ctx}/pages/resmgt/network/js/rmNwRule.js'></script>
<style>
#updataRmNwRule i {text-align:left; padding-right:3px;}
#updataRmNwRule p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updataRmNwRule p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#updataRmNwRule label{display:block; padding-left:105px;}

#updataRmNwRuleList i {text-align:left; padding-right:3px;}
#updataRmNwRuleList p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updataRmNwRuleList p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#updataRmNwRuleList label{display:block; padding-left:120px;}
</style>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sa_title_ipRule"></icms-i18n:label></div>
				
				<div class="WorkSmallBtBg">
				<small>  
				<icms-i18n:label name="sa_title_ipdescribe"></icms-i18n:label> 
				</small>
				</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
					<table height="12%" width="100%" align="center">
					<tr style=" height:55px; ">
						<td class="tabBt" style="width:90px;"><icms-i18n:label name="sa_l_ruleName"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="ruleName" id="ruleName"
							type="text" class="textInput readonly" style="width: 140px"/></td>
						<td class="tabBt" style="width:70px;"><icms-i18n:label name="com_l_platType"></icms-i18n:label>：</td>
						<td class="tabCon">
						<icms-ui:dic id="s_platFormId"
								name="s_platFormId" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'" />
						</td>
						<td class="tabBt" style="width:90px;"><icms-i18n:label name="com_l_haType"></icms-i18n:label>：</td>
						<td class="tabCon">
						<icms-ui:dic id="c_haType"
								name="c_haType" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT adt.DIC_CODE AS value,adt.DIC_NAME AS name FROM ADMIN_DIC adt WHERE adt.DIC_TYPE_CODE = 'HA_TYPE' ORDER BY adt.ORDER_NUM" />
						</td>
					</tr>
					<tr style=" overflow:hidden;">
					<td class="tabBt" style="width:90px;"><icms-i18n:label name="sa_l_vmType"></icms-i18n:label>：</td>
					<td class="tabCon">
					<icms-ui:dic id="s_virtualTypeId"
								name="s_virtualTypeId" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT VIRTUAL_TYPE_ID AS value, VIRTUAL_TYPE_NAME AS name FROM RM_VIRTUAL_TYPE WHERE IS_ACTIVE = 'Y'" />
					</td>
					<td class="tabBt" style="width:70px;"><icms-i18n:label name="com_l_hostType"></icms-i18n:label>：</td>
					<td class="tabCon">
					<icms-ui:dic id="s_hostTypeId"
								name="s_hostTypeId" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT HOST_TYPE_ID AS value, HOST_TYPE_NAME AS name FROM RM_HOST_TYPE WHERE IS_ACTIVE = 'Y'" />
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					
					</tr>
				</table>
				</div>
			</div>
			<div class="searchBtn_right" style="width:31%; float:left; padding-left:2%; " >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
							<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;" >
								<span class="icon iconfont icon-icon-search"></span>
								<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
							<span class="icon iconfont icon-icon-clear1"></span>
							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
						<!--  style="text-indent:-999px;" -->
						<shiro:hasPermission name="rmRule_sava">
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()" >
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="rmRule_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
						</shiro:hasPermission>
						<shiro:hasPermission name="rmRule_update">
							<input type="hidden" id="updateFlag" name="updateFlag2" value="1" />
						</shiro:hasPermission> 	
						<shiro:hasPermission name="iplxgl">
							<input type="hidden" id="iplxglFlag" name="iplxglFlag" value="1" />
						</shiro:hasPermission>
						</td>
						</tr>
					</table>
		</div>
	</div>
			</form>
		</div>
		<div class="pageTableBg">		
			<div class="panel clear" id="rmNwRuleGridTable_div">
				<table id="rmNwRuleGridTable"></table>
				<table id="rmNwRulePager"></table>
			</div>
		</div>
		</div>
	<div id="updataRmNwRule" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataRmNwRuleForm">
				<input type="hidden" id="rmNwRuleMethod" name="rmNwRuleMethod" />
				<input type="hidden" id="rmNwRuleId" name="rmNwRuleId" />
				<input type="hidden" id="validateplatFormId" name="validateplatFormId" />
				<input type="hidden" id="validatevirtualTypeId" name="validatevirtualTypeId" />
				<input type="hidden" id="validatehostTypeId" name="validatehostTypeId" />
				<input type="hidden" id="validatehaType" name="validatehaType" />
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_platType"></icms-i18n:label>：</i>
                              <icms-ui:dic id="platFormId1"
								name="platFormId1" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'" />
              </span>
			  <span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_vmType"></icms-i18n:label>：</i>
								<select id="virtualTypeId" name="virtualTypeId" class="selInput" style="width: 150px;"></select>
			</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_hostType"></icms-i18n:label>：</i>
                            <icms-ui:dic id="hostTypeId1"
								name="hostTypeId1" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT HOST_TYPE_ID AS value, HOST_TYPE_NAME AS name FROM RM_HOST_TYPE WHERE IS_ACTIVE = 'Y'" />
			</span>
			<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_haType"></icms-i18n:label>：</i>
                            <icms-ui:dic id="haType"
								name="haType" showType="select"
								attr="style='width: 150px;'class='selInput'"
								sql="SELECT adt.DIC_CODE AS value,adt.DIC_NAME AS name FROM ADMIN_DIC adt WHERE adt.DIC_TYPE_CODE = 'HA_TYPE' ORDER BY adt.ORDER_NUM" />
			</span>
			
					<i><font color="red">*</font><icms-i18n:label name="sa_l_ruleName"></icms-i18n:label>：</i>
					<input type="text" name="ruleName1" id="ruleName1" style="width: 200px;" class="readonly" disabled="disabled"/>
	
			</p>
				<p id="tipRule" style="display: none">
					<font color="red"><icms-i18n:label name="sa_l_ipNotice"></icms-i18n:label></font>
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right;padding-right:45px; margin-bottom:20px; width:555px;">
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeRmNwRuleView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" id="rule_btn_ok" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateRmNwRuleBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
	<div id="RmNwRuleList" style="display: none;" > 
	<table width="100%">
		<tr style="width: 100%">
			<td align="right"></td>
		</tr>
		<tr style="width: 100%">
			<td align="right">
			<shiro:hasPermission name="rmRuleList_sava">
					<a href="javascript:void(0)" type="button" class="btn" onclick="createRmNwRuleList()" title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-left:930px; color:#fff;">
						<span class="icon iconfont icon-icon-add"></span>
	  					<span>添加</span>
					</a>
			</shiro:hasPermission>	
			<shiro:hasPermission name="rmRuleList_update">
								<input type="hidden" id="updateFlag2" name="updateFlag2" value="1" />
			</shiro:hasPermission>
			<shiro:hasPermission name="rmRuleList_delete">
								<input type="hidden" id="deleteFlag2" name="deleteFlag2" value="1" />
			</shiro:hasPermission>	
			</td>
		</tr>
		<tr style="width: 100%"  height="10px">
		</tr>
	</table>

	<div class="panel clear" id="RmNwRuleListGridDiv">
		<table id="RmNwRuleListGridTable"></table>
		<div id="RmNwRuleListGridPager"></div>
	</div>
</div>
<div id="updataRmNwRuleList" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataRmNwRuleListForm">
				<input type="hidden" id="rmNwRuleListMethod" name="rmNwRuleListMethod" />
				<input type="hidden" id="rmNwRuleId1" name="rmNwRuleId1" />
				<input type="hidden" id="RmNwRuleListId" name="RmNwRuleListId" />
				<input type="hidden" id="platFormId" name="platFormId" />
				<input type="hidden" id="virtualTypeId1" name="virtualTypeId1" />
				<input type="hidden" id="hostTypeId" name="hostTypeId" />
				<input type="hidden" id="ucode" name="ucode" />
				<input type="hidden" id="validateUseCode" name="validateUseCode" />
				<input type="hidden" id="rmRuleNamelink" name="rmRuleNamelink" />
				
              <p>
              	<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_purpose"></icms-i18n:label>：</i>
					<select id="use" name="use" class="selInput" ></select>
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_purTypeName"></icms-i18n:label>：</i>
                        <input type="text" name="rmIpTypeName" id="rmIpTypeName"  class="textInput" readonly="readonly"/>
                </span>
                </p>
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_purRelaCode"></icms-i18n:label>：</i>
					<input type="text" name="useCode" id="useCode"   class="readonly"/>
					
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_validIP"></icms-i18n:label>：</i>
                       <input type="text" name="actNum" id="actNum"  class="textInput" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
					<i><icms-i18n:label name="sa_l_port"></icms-i18n:label>：</i>
                       <input type="text" name="vmwarePgPefix" id="vmwarePgPefix"  class="textInput" />
				</span>
				<span class="updateDiv_list" style="display:none">
					<i><font color="red">*</font><icms-i18n:label name="sa_l_perchIP"></icms-i18n:label>：</i>
                       <input type="text" name="occNum" id="occNum" value="1" class="textInput"  />
				</span>
			<p  style="display:none">
					<i><icms-i18n:label name="sa_l_perchLocal"></icms-i18n:label>：</i>
					    <label style="display:inline; padding-left:0;"><icms-i18n:label name="IP_assign_start_position"/></label>
                        <label style="margin-left:0px; display:inline; padding-left:0;">-</label>
                        <label style="margin-left:0px;display:inline; padding-left:0;"><icms-i18n:label name="IP_occupy_number"/></label>
                        <input type="button" class="btn_add_s" onclick="addOccSitTable()" title="新建" style="margin-left:15px"/>
			</p>
			<p></p>
			<p>
			<table id="occSitTable" style="position: absolute;right: 670px;top: 210px">
			</table>
			<p/>
			<p class="winbtnbar btnbar1" style="text-align:right; width:572px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeRmNwRuleListView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateRmNwRuleLisBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
</body>

</html>