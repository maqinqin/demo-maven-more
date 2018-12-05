<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>

<title>云服务定义</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/honeySwitch.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/cloud/service/jquery-ui.min.css"></link>
<script type="text/javascript" src="${ctx }/pages/cloud/service/js/cloudServiceCommon.js"></script>
<script type="text/javascript" src="${ctx }/pages/cloud/service/js/cloudService.js"></script>
<script type="text/javascript" src="${ctx }/pages/cloud/service/js/cloudServiceSet.js"></script>
<script type="text/javascript" src="${ctx }/pages/cloud/service/js/cloudServiceModel.js"></script>
<script type="text/javascript" src="${ctx }/common/javascript/honeySwitch2.js"></script>
<script type="text/javascript" src="${ctx }/common/javascript/num-alignment.js"></script>


<style type="text/css">
	#updateDiv .pageFormContent {padding-left:0; padding-right:0; padding-bottom:0;}
	#updateDiv i {text-align:right; padding-right:3px;width:110px}
	#updateDiv p{width:600px; margin-bottom:5px;}
	#updateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px;}
	.ui-widget-content a:link, .ui-widget-content a:visited{color:#fff; text-decoration:none; }
	.ui-widget-content a:hover{color:#fff; text-decoration:none;}
	#updateDiv .btnbar{padding-top:0;}
	#updateDiv textarea{width:466px; text-indent:4px;border-radius: 3px; padding:4px 0 ;border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5;padding: 1px 4px 0px 5px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
	#updateDiv label{display:block; padding-left:116px;}
	
	#viewDiv p{margin-bottom:0; width:380px; margin-left:30px; padding:3px 0;}
	#viewDiv p i{width:110px;text-align: right;}
	#viewDiv p input{width:220px;}
	#vmSpan div{display:inline-block;}
</style>

</head>

<body onload="init();" class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
			<div class="WorkBtBg"><icms-i18n:label name="cs_title_def"/></div>
			<div class="WorkSmallBtBg">
				<small>
					<icms-i18n:label name="cs_title_describe"/>
					</small>
			</div>	
					
			</h1>
		</div>
			<div id="topDiv" class="pageFormContent searchBg">
			<form action="">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:69%; float:left;">
					<div class="searchInOne" >
						<table height="12%" width="100%" align="center">
							<tr style=" height:55px; ">
								<td class="tabBt" style="width:70px;"><icms-i18n:label name="com_l_serviceName"/>：</td>
								<td  class="tabCon"><input  name="queryServiceName" class="textInput" id="queryServiceName"></td>
								<td class="tabBt"><icms-i18n:label name="com_l_status"/>：</td>
								<td class="tabCon" >
									<icms-ui:dic id="queryServiceStatic" name="queryServiceStatic" kind="CLOUD_SRV_STS"
										showType="select" attr="class='selInput'" />
								</td>
								<td class="tabBt"><icms-i18n:label name="com_l_haType"/>：</td>
								<td class="tabCon" >
									<icms-ui:dic id="queryhaType" name="queryhaType" kind="HA_TYPE" 
										showType="select" attr="class='selInput'" />
								</td>
							</tr>
							<tr>
								<td class="tabBt" style="width:66px;"><icms-i18n:label name="com_l_platType"/>：</td>
								<td class="tabCon">
									<icms-ui:dic name="queryplatformType" id="queryplatformType"
										sql="SELECT '1' as id, PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name,'1' AS kind  FROM RM_PLATFORM PLAY WHERE PLAY.IS_ACTIVE='Y'"
										showType="select" attr="class='selInput'" />
									</td>
								<td class="tabBt"><icms-i18n:label name="com_l_vmType"/>：</td>
								<td class="tabCon">
									<icms-ui:dic name="queryvmType" id="queryvmType"
										sql="SELECT '1' as id,  VM.VIRTUAL_TYPE_ID AS VALUE,VM.VIRTUAL_TYPE_NAME AS NAME ,'1' AS KIND FROM RM_VIRTUAL_TYPE VM WHERE VM.IS_ACTIVE='Y'"
										showType="select" attr="class='selInput'" />
								</td>
								<td class="tabBt" ></td>
								<td class="tabCon"></td>
								
							</tr>
						</table>
					</div>
				</div>
				<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
					
						<tr style=" height:50px; ">
							<td>
								<%-- <a href="javascript:void(0)" type="button" class="btn" onclick="writeToTxt();" title='<icms-i18n:label name="com_btn_wirte"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_wirte"></icms-i18n:label>' style="vertical-align:middle;">
									<span class="icon iconfont icon-icon-add"></span>
	  								<span><icms-i18n:label name="com_btn_wirte"></icms-i18n:label></span>
								</a> --%>
								 <%-- <form name="uploadForm" id="uploadForm" action="<%=basePath%>excel/create/uploadAction.action" method="post" enctype="multipart/form-data" >
					           	 	<input type="file" name="file">
					            	<input type="submit" value="提交">
					        	</form> --%>
							</td>
						</tr>
				
						<tr style=" height:52px;">
							<td>
							<a href="javascript:search()"  type="button" class="btn" onclick=""search();return false;"" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' style="vertical-align:middle">
								<span class="icon iconfont icon-icon-search"></span>
	  							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
								<span class="icon iconfont icon-icon-clear1"></span>
	  							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<shiro:hasPermission name="service:add">
								<a href="javascript:void(0)" type="button" class="btn" onclick="viewService()" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' style="vertical-align:middle;">
									<span class="icon iconfont icon-icon-add"></span>
	  								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:update">
								<input type="hidden" id="updateFlag" name="updateFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:delete">
								<input type="hidden" id="deleteFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:start">
								<input type="hidden" id="startFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:stop">
								<input type="hidden" id="stopFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:suit">
								<input type="hidden" id="viwSuitFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:model">
								<input type="hidden" id="viewModelFlag" value="1"/>
							</shiro:hasPermission>
							<shiro:hasPermission name="service:attr">
								<input type="hidden" id="viewAttrFlag" value="1"/>
							</shiro:hasPermission>
						</td>	
						</tr>
						
					</table>
				</div>
			</div>
		
			</form>	
		</div>
		<!-- pageFormContent完 -->
		<div class="pageTableBg">
		<div class="panel clear" id="gridTableDiv">
			<div>
				<table id="gridTable" ></table>
					<div id="gridPager"></div>
			</div>
		</div>
		</div>		
	</div>
	
	
	
	<div id="updateDiv" style="display: none;" >
		<form action="" method="post" id="serviceForm">
		<div  id="serviceTab"  class="pageFormContent">		
			<div id="baseDiv">
				<input type="hidden" id="serviceId" name="serviceId" /> 
				<input type="hidden" id="serviceMethod" name="serviceMethod" />
				<p >	
				<span>
						<i><font color="red">*</font><icms-i18n:label name="cs_l_csName"/>：</i>
						<input id="serviceName" class="textInput" name="serviceName" style="width:458px;"/>
				</span>
				<%-- <p>
					<span class="updateDiv_list" style="height:26px;">
						<i><icms-i18n:label name="cs_l_csType"/>：</i>
						<select id="cloud_type" name="cloudType" style='width: 178px;'class='selInput'>
							<option value="">请选择</option>
							<option value="IaaS">IaaS</option>
							<option value="PaaS">PaaS</option>
							<option value="SaaS">SaaS</option>
						</select>
					</span>
					<span class="updateDiv_list" style="height:26px;">
						<i><icms-i18n:label name="cs_l_csTypeCode"/>：</i>
						<select id="cloud_type_code" name="cloudTypeCode" style='width: 178px;'class='selInput'>
							<option value="">请选择</option>
							<option value="DB">数据库</option>
							<option value="MQ">消息中间件</option>
							<option value="WINDOWS">WINDOWS</option>
							<option value="RHEL">红帽</option>
							<option value="SUSE">SUSE</option>
							<option value="AIX">AIX</option>
						</select>
					</span>
				</p> --%>
					<span class="updateDiv_list" style="width:500px;">
						<i style="padding-right:0;"><font color="red">*</font><icms-i18n:label name="cs_l_csType"/>：</i>
						<icms-ui:dic id="serviceType" name="serviceType" kind="CLOUD_SRV_TYPE" showType="flatSelect" attr=""  click="initServiceType(this.getAttribute('value'))"/>
						
					</span>			
				
					<!-- <span class="updateDiv_list" style="height:26px;"> -->
						<i><icms-i18n:label name="cs_l_csTypeCode"/>：</i>
						<input id="cloud_type_code" name="cloudTypeCode" class="textInput" style="width:166px;"/>
					<!-- </span> -->
			
					<span class="updateDiv_list" style="height:26px; position:relative;overflow:visible;" id="switch_span">
						<i style="padding-right:0;"><font color="red">*</font><icms-i18n:label name="cs_l_csStatus"/>：</i>
						<input type="hidden" id="serviceStatus" name="serviceStatus" />
					</span>
				
				
				<%-- <p>
					<i><icms-i18n:label name="cs_l_busFunc"/>：</i>
						<textarea id="funcRemark" name="funcRemark"  style="resize: none; height:40px;"></textarea>
				</p>
				<p>
					<i><icms-i18n:label name="cs_l_remark"/>：</i>
						<textarea id="unfuncRemark" name="unfuncRemark" style="resize: none; height:40px;"></textarea>
				</p> --%>
					<span class="updateDiv_list" style="margin-top:10px;">
						<i><font color="red">*</font><icms-i18n:label name="com_l_platType"/>：</i>
						<icms-ui:dic name="platformType" id="platformType"
									sql="SELECT '1' as id, PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name,'1' AS kind  FROM RM_PLATFORM PLAY WHERE PLAY.IS_ACTIVE='Y'"
									showType="select" attr="'class='selInput'" />
					</span>
					<span class="updateDiv_list" id="hostType_span">
						<i><font color="red">*</font><icms-i18n:label name="com_l_hostType"/>：</i>
						<icms-ui:dic name="vmType" id="vmType"
									sql="SELECT '1' as id,  VM.VIRTUAL_TYPE_ID AS VALUE,VM.VIRTUAL_TYPE_NAME AS NAME ,'1' AS KIND FROM RM_VIRTUAL_TYPE VM WHERE VM.IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
						<input name="hostType" id="hostType" type="hidden" />
					</span>
				</p>
			</div>
			<div id="hostInfoDiv" style="display: none;" >
					<p class="updateDiv_list" id="haTypeSpan" style="display: inline-block;float: left;width: 296px;">
						<i><font color="red">*</font><icms-i18n:label name="com_l_haType"/>：</i>
						<icms-ui:dic id="haType" name="haType" kind="HA_TYPE" showType="select" attr="class='selInput'" />
					</p>
					<p id="vmSpan" class="updateDiv_list" style="display: inline-block;width: 296px;">
						<i><font color="red">*</font><icms-i18n:label name="cs_l_vmBase"/>：</i>
						<input id="vmBase" name="vmBase" class="textInput alignment" style="width: 80px;height:24px;"  data-step="1" data-min="0" data-digit="0" />
						<!-- <input id="vmBase" name="vmBase" class="textInput" style="width: 168px;" /> -->
					</p>
				
					<p class="updateDiv_list" id="imageSpan" style="width: 650px;">
						<i><font color="red">*</font><icms-i18n:label name="cs_l_sysMir"/>：</i>
						<icms-ui:dic name="imageId" id="imageId"
									sql="SELECT '1' as id, img.IMAGE_NAME AS name,img.IMAGE_ID AS value ,'1' AS kind  FROM CLOUD_IMAGE img WHERE img.IS_ACTIVE='Y' AND img.usage_scenarios='osImage' "
									showType="select" attr="style='width: 470px;' class='selInput'" />
					</p>
				<%-- <p style="width:294px; float:left;margin-left: 6px;">
					<span class="updateDiv_list" id="hostSpan">
						<i><font color="red">*</font><icms-i18n:label name="com_l_hostType"/>：</i>
						<icms-ui:dic name="hostType" id="hostType"
									sql="SELECT '1' as id, ht.HOST_TYPE_NAME AS name,ht.HOST_TYPE_ID AS value ,'1' AS kind  FROM RM_HOST_TYPE ht WHERE ht.IS_ACTIVE='Y'"
									showType="select" attr="style='width: 178px;' class='selInput'" />
					</span>
				</p> --%>
				<!-- <p style="height: 30px; width:294px; float:left;">
					<i>&nbsp;&nbsp;是否虚机 ：</i>
					<input id="isVm" name="isVm" type="checkbox" value="Y" style="margin-top:5px;"/>
				</p>  -->
				
				<div class="nowrap right"></div>  
				<div>
					<p>
						<i><icms-i18n:label name="cs_l_busFunc"/>：</i>
							<textarea id="funcRemark" name="funcRemark"  style="resize: none; height:40px;"></textarea>
					</p>
					<p>
						<i><icms-i18n:label name="cs_l_remark"/>：</i>
							<textarea id="unfuncRemark" name="unfuncRemark" style="resize: none; height:40px;"></textarea>
					</p>
				</div>
				
			</div>
			
			<div id="storeInfoDiv" style="display: none;" >
				<p>
					<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="service_cloud_operate_System"/>：</i>
						<icms-ui:dic id="systemType" name="systemType" kind="SYSTEM_TYPE" showType="select" attr="style='width: 175px;' class='selInput'" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="service_cloud_data_use"/>：</i>
					<icms-ui:dic id="storageDataType" name="storageDataType" kind="STORAGE_DATA_TYPE" showType="select" attr="style='width: 175px;' class='selInput'" />
				</span>
				</p>
				<div class="nowrap right"></div>  
			</div>
		</div>	
		</form>
		<div style="margin-bottom:20px; text-align:right; padding-left:0; margin-right:80px;float:right" class="btnbar1">
			<label id="sp_error_tip"></label>
			<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'  class="btn btn_dd2 btn_dd3" onclick="closeView()" style="margin-right: 5px; margin-left:0;" />
			<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="updateOrSave()" title='<icms-i18n:label name=""></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style=" margin-right: 5px;margin-left: 5px;" />
		</div>
	</div>
	<div id="viewDiv" style="display: none;"  class="pageFormContent">
		<div id="viewBaseDiv">
			<p>
				<i><icms-i18n:label name="cs_l_csName"/>：</i>
				<input id="serviceName2" class="readonly"   readonly="readonly"/>
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_csType"/>：</i>
				<input id="cloudType2" class="readonly"   readonly="readonly"/>
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_csTypeCode"/>：</i>
				<input id="cloudTypeCode2" class="readonly"   readonly="readonly"/>
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_csStatus"/>：</i>
				<input id="serviceStatus2" class="readonly" readonly="readonly" >
			</p>
			<p>
				<i><icms-i18n:label name="info_vm_platform"/>：</i>
				<input id="platformType3" class="readonly" readonly="readonly" >
			</p>
			<p>
				<i><icms-i18n:label name="com_l_hostType"/>：</i>
				<input id="hostType2" class="readonly" readonly="readonly">
			</p>
			<p>
				<i><icms-i18n:label name="com_l_haType"/>：</i>
				<input id="haType2" class="readonly" readonly="readonly">
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_vmBase"/>：</i>
				<input id="vmBase3" class="readonly" readonly="readonly" >
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_sysMir"/>：</i>
				<input id="systemImage2" class="readonly" readonly="readonly" >
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_busFunc"/>：</i>
				<input id="funcRemark2" class="readonly" readonly="readonly">
			</p>
			<p>
				<i><icms-i18n:label name="cs_l_remark"/>：</i>
				<input id="unfuncRemark2" class="readonly" readonly="readonly">
			</p>
		</div>
		<div id="viewHostInfoDiv" >
				<p>
					<i><icms-i18n:label name="info_vm_platform"/>：</i>
					<input id="platformType2" class="readonly" readonly="readonly" >
				</p>
				<p>
					<i><icms-i18n:label name="com_l_vmType"/>：</i>
					<input id="vmType2" class="readonly" readonly="readonly" >
				</p>
				<p id="isVmP" style="display: none;">
					<i><icms-i18n:label name="cs_l_isvm"/>：</i>
					<input id="isVm2" name="isVm2" type="checkbox" value="Y" disabled="disabled" class="readonly" style="width: 20px;"/>
				</p>
				<p id="vmSpan2" style="display: none;">
					<i><icms-i18n:label name="cs_l_vmBase"/>：</i>
					<input id="vmBase2" class="readonly" readonly="readonly" >
				</p>
				<p id="imageP" style="display: none;">
					<i><icms-i18n:label name="cs_l_sysMir"/>：</i>
					<input id="imageId2" class="readonly" readonly="readonly">
				</p>				
		</div>	
		<div id="viewStoreInfoDiv" >
				<p>
					<i><icms-i18n:label name="service_cloud_data_use"/>：</i>
						<input id="storageDataType2" class="readonly" readonly="readonly">
				</p>
		</div>
	</div>
	<!-- 云服务套餐 -->
	<div id="serviceSuit" style="display: none;" class="pageFormContent">
		<table width="97%">
			<tr>
				<td align="right">
					<shiro:hasPermission name="suit:add">
						<input class="Btn_Add" type="button" onclick="loadSet('')" title="新建" />
					</shiro:hasPermission>
					<shiro:hasPermission name="suit:update">
						<input type="hidden" id="updateSuitFlag" value="1"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="suit:delete">
						<input type="hidden" id="deleteSuitFlag"  value="1"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="suit:start">
						<input type="hidden" id="startSuitFlag" value="1"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="suit:stop">
						<input type="hidden" id="stopSuitFlag" value="1"/>
					</shiro:hasPermission>
				</td>
			</tr>
			<tr height="10px"><td></td></tr>
		</table>

		<div class="gridMain" id="serviceSuitGridDiv">
			<table id="serviceSuitGridTable"></table>
			<div id="serviceSuitGridPager"></div>
		</div>
	</div>

	<div id="serviceSetLoad" style="display: none;">
		<form action="" id=serviceSetLoadForm>
			<input type="hidden" id=serviceIds name="serviceIds" />
			<input type="hidden" id="serviceIsAvtice" name="serviceIsAvtice">
			<div  id="serviceSetTab"  class="pageFormContent">	
<!-- 				<input type="hidden" id="serviceSetId" name="serviceSetId" /> -->
				<p >
					<i><font color="red">*</font>套餐名称：</i><input id=serviceSetName name="serviceSetName"  class="textInput"/>
				</p>
				<p >
					<i><font color="red">*</font>套餐状态：</i><icms-ui:dic id="serviceSetStatus" name="serviceSetStatus" kind="CLOUD_SRV_STS" showType="flatSelect"
							attr="style='width: 100px;'" />
				</p>
				<p style="width: 600px">
					<i><font color="red">*</font>CPU：</i>
					<icms-ui:dic name="cpu" id="cpu" sql="SELECT CPU_MEM_REF_ID as id, PARAM_NAME AS name,PARAM_VALUE AS value ,'1' AS kind  FROM CLOUD_CPU_MEM_REF  WHERE PARAM_TYPE='CPU' AND IS_ACTIVE='Y' ORDER BY  PARAM_VALUE ASC"
								showType="flatSelect" attr="style='width: 70px;'" click="initMemSelect(this.getAttribute('value'));"/>
				</p>
				<p >
					<i><font color="red">*</font>内存：</i>
					<span id='mem_span' class='uc-radio'></span>
					<input type="hidden" id="mem" name="mem"/>
				</p>
				<p style="width: 600px">
                    <i><font color="red">*</font>系统盘大小：</i>
				    <span>
				        <span id="uc-duration" class="uc-slider" style="width: 400px;">
				            <span class="range">
				                        <span class="sliderBlock" value="250" style="width: 100px;">
				                               <span>250GB</span>
				                        </span><span class="sliderBlock"  value="500" style="width: 100px;">
				                                <span>500GB</span>
				                        </span><span class="sliderBlock"  value="1000" style="width: 200px;">
				                               <span>1000GB</span>
				                        </span>  
				                <span class="sliderContainer">
				                    <span class="sliderCurrent">
				                        <span class="sliderUnit">
				                               <span>250GB</span>
				                        </span><span class="sliderUnit">
				                                <span>500GB</span>
				                        </span><span class="sliderUnit">
				                               <span>1000GB</span>
				                        </span>
				                    </span>
				                </span>
				                 
				                <span class="bar"></span>                                             
				                <a href="javascript:;" target="_self" rel="nofollow" class="sliderDrag" hidefocus>
				                    <b></b>
				                    <b></b>
				                    <b></b>
				                </a>
				            </span>
				           
				        </span>
				    </span>
				    <input class="uc-input" id="sysDisk" name="sysDisk" type="text" value="5" />
				</p>
				<p >
					<i>是否可编辑：</i><input id="isEdit" type="checkbox" name="isEdit" value="Y" /> 
				</p>
				<p style="width: 600px">
					<i>备注：</i><textarea id="remark" name="remark" rows="" cols="" style="width: 465px;height:40px;resize: none;"></textarea>
				</p>
				<div class="nowrap right"></div>
			</div>
			<div class="winbtnbar">
				<input type="button"  class="btn_gray" onclick="saveServiceSet()" title="保存" value="保存" style="margin-right: 5px;margin-left: 5px;" />
				<input type="button"   title="取消" value="取消" class="btn_gray" onclick="closeViews('serviceSetLoad')" style="margin-right: 5px; margin-left:0;" />
			</div>
		</form>
	</div>
	
	<!-- 查看云服务套餐 -->
	<div  id="showServiceSetDiv"  class="pageFormContent" style="display: none;">	
		<p>
			<i>套餐名称:</i><input name="serviceSetNameLab" id="serviceSetNameLab" type="text" class="readonly"  size="30" readonly="readonly">
		</p>
		<p>
			<i>套餐状态:</i><input name="serviceSetStatusLab" id="serviceSetStatusLab" type="text" class="readonly"  size="30" readonly="readonly">
		</p>
		<p>
			<i>CPU(核):</i><input name="cpuLab" id="cpuLab" type="text" class="readonly"  size="30" readonly="readonly"> 
		</p>
		<p>
			<i>内存(M):</i><input name="memLab" id="memLab" type="text" class="readonly"  size="30" readonly="readonly">
		</p>
		<p>
			<i>系统盘大小(G):</i><input name="sysDiskLab" id="sysDiskLab" type="text" class="readonly"   size="30" readonly="readonly">
		</p>
		<p>
			<i>是否可编辑:</i><input name="isEditLab" id="isEditLab" type="checkbox" value="Y" disabled="disabled" class="readonly"> 
		</p>
		<p>
			<i>备注:</i><input name="remarkLab" id="remarkLab" type="text" class="readonly" style="width: 400px;height:40px;resize: none;"  size="30" readonly="readonly">
		</p>
	</div>
	<script type="text/javascript">
		$(function(){
			alignmentFns.initialize();
		})
	</script>
	<jsp:include page="serverModel.jsp"></jsp:include>
	<jsp:include page="serverAttr.jsp" ></jsp:include>
	
</body>
</html>
