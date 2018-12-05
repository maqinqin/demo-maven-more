<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmDeviceVMServerList.js"></script>
<script type="text/javascript">
	$(function() {
		$("#platformNameIdInput").change(function(){
			changePlatform();
		});	
		initCmDeviceList();
	});
	function clearData(){
		$("#serverName").val("");
		$("#platformNameId").val("");
		$("#vmTypeNameId").val("");
		$("#manageIp").val("");
	}
</script>
<style type="text/css">
#manageProjectName-error{padding-left: 148px !important;}
#manageProjectDOMAIN-error{padding-left: 148px !important;}
#VMServerUpdateiv i {text-align:left; padding-right:5px;}
#VMServerUpdateiv p{width:620px; margin-bottom:5px; margin-left:18px;  }
#VMServerUpdateiv p .updateDiv_list{overflow:hidden; display:inline-block; width:310px; float:left;}
#VMServerUpdateiv label{display:block; padding-left:108px; width:190px;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_vmServe"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">	
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
					<td class="tabBt" style="width:68px;"><icms-i18n:label name="com_l_serveName"></icms-i18n:label>：</td>
					<td class="tabCon"><input name="serverName" id="serverName" type="text" class="textInput"/> </td>
					<td class="tabBt"><icms-i18n:label name="com_l_platType"></icms-i18n:label>：</td>
					<td align="left">
					<icms-ui:dic id="platformNameId" name="platformName" showType="select" attr="class='selInput'" sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'"/>
				</td>
				<td class="tabBt"><icms-i18n:label name="bim_l_VirType"></icms-i18n:label>：</td>
				<td class="tabCon">
					<icms-ui:dic id="vmTypeNameId" name="vmTypeName" showType="select" attr="class='selInput'"/>
				</td>
			</tr>
			<tr>
				<td class="tabBt" style="width:68px;" ><icms-i18n:label name="bim_l_hostIP"></icms-i18n:label>：</td>
					<td class="tabCon"><input name="manageIp" id="manageIp" type="text" class="textInput"/></td>
				<td></td>
				<td></td>
				
			</tr>
		 </table>
	 </div>
  </div>
  <div class="searchBtn_right" style="width:28%; " >
					<table height="12%" width="100%" >
						<tr style=" height:52px;">
							<td>
								<a href="javascript:search()" type="button" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;" class="btn">
									<span class="icon iconfont icon-icon-search"></span>
									<span>查询</span>
								</a>
				      			<button type="button" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick='clearData()'>
				      				<span class="icon iconfont icon-icon-clear1"></span>
									<span>清空</span>
				      			</button>
								<shiro:hasPermission name="xjglfwq_sava">
									<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createVMServer();" class="btn">
										<span class="icon iconfont icon-icon-add"></span>
									<span>添加</span>
									</a>
								
								</shiro:hasPermission>
								<shiro:hasPermission name="xjglfwq_delete">
								<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
								</shiro:hasPermission>
			    				<shiro:hasPermission name="xjglfwq_update">
								<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
				</shiro:hasPermission> 
							</td>
						</tr>
					</table>
 </div>
</div>
		</form>
		</div>
		
		<!-- 显示虚机管理服务器的信息用列表 -->
		<div class="pageTableBg">
			<div class="panel clear" id="gridTable_div">
			<table id="gridTable"></table>
			<table id="gridPager"></table>
		</div>
		</div>
		
	</div>
	
	<div id="VMServerUpdateiv" style="display: none;">
		<form action="" method="post" id="updateForm">
		<div id="deviceVMServer"  class="pageFormContent">			
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_blogsDataCent"></icms-i18n:label>:</i>					
						<icms-ui:dic id="dataCenterIdInput" name="dataCenterIdInput" showType="select" attr="class='selInput'" sql="SELECT ID AS value, DATACENTER_CNAME AS name FROM RM_DATACENTER WHERE IS_ACTIVE = 'Y'"/>
				</span>
				
			   <span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_serveName"></icms-i18n:label>:</i>
					<input type="text" id="serverNameInput" name="serverNameInput" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_platType"></icms-i18n:label>:</i>
					<icms-ui:dic id="platformNameIdInput" name="platformNameIdInput" showType="select" attr="class='selInput'" sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'"/>
				</span>
				<span class="updateDiv_list">				
					<i><font color="red">*</font><icms-i18n:label name="bim_l_VirType"></icms-i18n:label>:</i>
						<icms-ui:dic id="vmTypeNameIdInput" name="vmTypeNameIdInput" showType="select" attr="class='selInput'"/>
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_hostIP"></icms-i18n:label>:</i>
					<input type="text" id="manageIpInput" name="manageIpInput" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_username"></icms-i18n:label>:</i>
					<input type="text" id="userNameInput" name="userNameInput"  class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_password"></icms-i18n:label>:</i>
					<input type="password" id="passwordInput" name="passwordInput"  class="textInput" />
				</span>
				<span id="span1" class="updateDiv_list" style="display: none">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_domainName"></icms-i18n:label>:</i>
					<input type="text" id="domainName" name="domainName"  class="textInput" />
				</span>
				<span id="span_version" class="updateDiv_list" style="display: none">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_version"></icms-i18n:label>:</i>
					<icms-ui:dic id="version" name="version" kind="OPENSTACK_VERSION" showType="select" attr=""  />
				</span>
				<span id="span_manageOneIp" class="updateDiv_list" style="display: none">
					<i><font color="red">*</font>SC Api Ip:</i>
					<input type="text" id="manageOneIp" name="manageOneIp"  class="textInput" />
				</span>
				<span id="span_manageOneOcIp" class="updateDiv_list" style="display: none">
					<i><font color="red">*</font>OC Api Ip:</i>
					<input type="text" id="manageOneOcIp" name="manageOneOcIp"  class="textInput" />
				</span>
					<span id="span_manageProjectId" class="updateDiv_list" style="display: none">
					<i><font color="red">*</font>管理ProjectID:</i>
					<input type="text" id="manageProjectId" name="manageProjectId"  class="textInput" />
				</span>
				</p>
			<input type="hidden" id="serverId" value="NULL" />
			<input type="hidden" id="cmPasswordid" />
			<div class="nowrap right"></div>   
		</div>
	  	<p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; padding-right:10px; width:572px;">
		  	<input type="button" class="btn btn_dd2 btn_dd3"  title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('VMServerUpdateiv')" style="margin-right: 5px; margin-left:0;"  />
		  	<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateBtn()" style="margin-right: 5px; margin-left:0;" />
	  	</p>
        </form>
	</div>
	
	<div id="VMServerShowDiv" style="display: none;">
		<form action="" method="post" id="showForm">
		<div id="deviceVMServerShow" class="pageFormContent" >
			   <p>
					<i><icms-i18n:label name="com_l_serveName"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="serverNameShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_hostIP"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="manageIpShow" />
				</p>				
				<p>
					<i><icms-i18n:label name="com_l_platType"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="platformNameIdShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_VirType"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="vmTypeNameIdShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_creatPeo"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="createUserShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_creatDay"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="createDatetimeShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_updatPeo"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="updateUserShow" />
				</p>
				<p>
					<i><icms-i18n:label name="bim_l_updatDay"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="updateDatetimeShow" />
				</p>
				<p>
					<i><icms-i18n:label name="com_l_blogsDataCent"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="dataCenterShow" />
				</p>
				<p>
					<i><icms-i18n:label name="com_l_username"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="userNameShow" size="50" />
				</p>
				<p id="version_show" style="display: none">
					<i><icms-i18n:label name="com_l_version"></icms-i18n:label>：</i>
					<input type="text" class="readonly" readonly id="versionShow" size="50" />
				</p>
				<p id="manageOneIp_show" style="display: none">
					<i>SC Api Ip：</i>
					<input type="text" class="readonly" readonly id="manageOneIpShow" size="50" />
				</p>
				<p id="manageOneOcIp_show" style="display: none">
					<i>OC Api Ip：</i>
					<input type="text" class="readonly" readonly id="manageOneOcIpShow" size="50" />
				</p>
			<p id="manageProjectId_show" style="display: none">
				<i>管理ProjectID：</i>
				<input type="text" class="readonly" readonly id="manageProjectIdShow" size="50" />
			</p>
			<div class="nowrap right"></div>   
		</div>
	  	<p class="winbtnbar btnbar1"><input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_close"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('VMServerShowDiv')" style="margin-right: 5px;" /></p>
        </form>
	</div>	
</body>
</html>
