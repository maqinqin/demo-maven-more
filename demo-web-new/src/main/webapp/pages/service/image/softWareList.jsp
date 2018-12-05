<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/service/js/softWareParamList.js"></script>
<script type="text/javascript">
	
	
</script>
<style type="text/css">
#updateDiv i {text-align:left; padding-right:3px;}
#updateDiv p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#updateDiv label{display:block; padding-left:110px;}

#updateVerTab i {text-align:left; padding-right:3px;}
#updateVerTab p{width:300px; margin-bottom:5px; margin-left:18px;  }
#updateVerTab label{display:block; padding-left:110px;}
#updateTab span{height:55px;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_mirSoft"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:70px;"><icms-i18n:label name="bim_l_softName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="selectSoftName" name="selectSoftName"  class="textInput"/>
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					
				</tr>
			</table>
			</div>
		</div>
		<div class="searchBtn_right" >
					<table height="12%" width="100%">
						<tr style=" height:52px;">
							<td>
						<a href="javascript:search()" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' class="btn" onclick="search();return false;">
							<span class="icon iconfont icon-icon-search"></span>
	  						<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
						</a>
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  onclick="clearAll()">
							<span class="icon iconfont icon-icon-clear1"></span>
	  						<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
						<shiro:hasPermission name="software:create">
							<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' class="btn" onclick="createData()" >
								<span class="icon iconfont icon-icon-add"></span>
	  							<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="software:delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
						</shiro:hasPermission>
						<shiro:hasPermission name="software:update"> 
				            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
				        </shiro:hasPermission>
				        <shiro:hasPermission name="software:ver"> 
				            <input type="hidden" id="verFlag" name="verFlag" value="1" />
				        </shiro:hasPermission>
					</td>
						</tr>
					</table>
		</div>
	</div>
			</form>
		</div>
	<div class="pageTableBg">
		<div class="panel clear" id="gridTableDiv">
			<table id="gridTable"></table>
			<div id="gridPager"></div>
		</div>
	</div>
	</div>
	<div id="updateDiv" class="cmxform"   style="display: none;">
		<form action="" method="post" id="updateForm">
		<div  id="updateTab"  class="pageFormContent">		
			<input type="hidden" id="softwareId" name="softwareId" />
			<input type="hidden" id="isActive" name="isActive" />
			<input type="text" style="display: none" id="method" name="method" />
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_softName"></icms-i18n:label>:</i><input type="text" class="textInput"  id="softwareName" name="softwareName" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_softType"></icms-i18n:label>:</i>
					<icms-ui:dic id="softwareType" name="softwareType" kind="CLOUD_SOFTWARE_TYPE"  showType="select"  attr="class='selInput'"/>
				
			</span>
			
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_savePath"></icms-i18n:label>:</i><input type="text" class="textInput"  id="softwarePath" name="softwarePath" />
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_softDescribe"></icms-i18n:label>:</i>
					<input type="text" id="remark" name="remark" class="textInput"  />
			</span>
			</p>
			<div class="nowrap right"></div>  
		</div>
		<div class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px;  width:590px;"><label id="sp_error_tip"></label>
		<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeView()" style="margin-right: 5px; margin-left:0;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 5px;" />
		</div>
		</form>
	</div>
	
	<!-- 软件版本 -->
	<div id="softwareVersDiv"  style="display: none; padding-top:0;" class="content-main clear" >
		<div class="pageFormContent" >
		<table width="100%" >
			<tr>
				<td width="100%" align="right" class="btnbar1" style="padding-left:0;">
				<shiro:hasPermission name="software:createVar"> 
					<input type="button" title='<icms-i18n:label name="common_btn_add"/>' value='<icms-i18n:label name="common_btn_add"/>' class="btn btn_dd2 btn_dd3" onclick="showVerData()"  style="margin-left: 570px;"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="software:deleteVar">
					<input type="hidden" id="deleteVarFlag"  name="deleteVarFlag" value="1" />
				</shiro:hasPermission>
				<shiro:hasPermission name="software:updateVar">
					<input type="hidden" id="updateVarFlag"   name="updateVarFlag" value="1" />
				</shiro:hasPermission>
				</td>
			</tr>
		</table>
		</div>
		<div  class="panel clear" id="softwareVerTableDiv">
			<table id="softwareVerTable"></table>
		</div>
		
	</div>
	
	<!-- 软件详情 -->
	<div id="softwareVerDiv"  style="display: none;" >
		<input type="text" style="display: none" id="verSoftwareId" name="verSoftwareId" />
		<form action="" method="post" id="softwareVerForm">
		<div  id="updateVerTab"  class="pageFormContent">	
			<input type="hidden" id="softwareVerId" name="softwareVerId" />
			<input type="hidden" id="verIsActive" name="verIsActive" />
			<input type="text" style="display: none" id="verMethod" name="verMethod" />
			
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_softVerName"></icms-i18n:label>:</i><input type="text" id="verName" name="verName" class="textInput" />
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_softVerDesc"></icms-i18n:label>:</i><input type="text" id="verRemark" name="verRemark"class="textInput" />
			</p>
			<div class="nowrap right"></div> 
		</div>
		<div class="winbtnbar btnbar1" style="width:308px; padding-left:0;" ><label id="sp_error_tip"></label>
		<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeVer()" style="margin-right: 5px; margin-left:0;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveVerBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
		</div>
		</form>
	</div>
</body>
</html>
