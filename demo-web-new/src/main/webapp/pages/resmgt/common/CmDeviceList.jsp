<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript"
	src="${ctx}/pages/resmgt/common/js/CmDeviceList.js"></script>
<script type="text/javascript">
	$(function() {
		initCmDeviceList();
	});
	function clearDate(){
		$("#deviceName").val("");
		$("#sn").val("");
		$("#resPoolId").val("");
		$("#deviceModelId").val("");
	}
</script>
<style type="text/css">
.Inputborder{display:inline-block; width:154px;/*  border:1px solid #d5d5d5; */ height:22px; line-height:22px; color:#615d70; /* background:#f5f5f5; */ /* padding:1px 4px 0 5px; */ box-shadow:1px 1px 2px #ededed;}
.pageFormContent i {width:90px; padding-right:0; text-align:right;}
#OaForm input{height:20px; line-height:20px; margin-left:4px; background-color: #f5f5f5;padding: 1px 4px 0px 5px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
#OaForm select{margin-left:4px;}
.searchBg input{background:none;}
label.error {position: absolute; font-size:11px;display:block}
.pageFormContent p label.error{left: 99px;}
#lab_cmHost {
    padding-right: 28px;
    padding-left: 0px; 
}
</style>
</head>
<body class="main1">
	<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="res_title_deviceManage"/></div>
 			<div class="WorkSmallBtBg"><small> <icms-i18n:label name="res_desc_deviceManage"/></small></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
					<tr style=" height:55px; ">
						<td class="tabBt" style="width:70px;"><icms-i18n:label name="res_l_device_name_short"/>：</td>
						<td class="tabCon"><input name="deviceName"
							id="deviceName" type="text" class="textInput readonly" /></td>
						<td class="tabBt">SN：</td>
						<td class="tabCon"><input name="sn" id="sn"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt"><icms-i18n:label name="res_l_device_pool"/>：</td>
						<td class="tabCon"><span class="Inputborder" style="background-color: #f5f5f5; border: 1px solid #d5d5d5;"><icms-ui:dic id="resPoolId" name="resPoolId"
								showType="select" attr="class='selInput'"
								sql="SELECT p.ID AS value,  CONCAT (dc.DATACENTER_CNAME, '/' ,p.POOL_NAME ) AS name FROM RM_RES_POOL p LEFT JOIN
			 RM_DATACENTER dc ON dc.ID=p.DATACENTER_ID WHERE p.IS_ACTIVE = 'Y' AND p.POOL_TYPE IN ('COMPUTE','NAS','SAN','ISCSI')"/>
						</span>
						</td>
						
					</tr>
					<tr>
						<td class="tabBt"><icms-i18n:label name="res_l_device_model"/>：</td>
						<td class="tabCon"><icms-ui:dic id="deviceModelId"
								name="deviceModelId" showType="select"
								attr="class='selInput'"
								sql="SELECT ID AS value, DEVICE_MODEL AS name FROM CM_DEVICE_MODEL WHERE IS_ACTIVE = 'Y'" />
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
							<td>
							<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="common_btn_query"/>' onclick="search();return false;" value='<icms-i18n:label name="common_btn_query"/>'>
								<span class="icon iconfont icon-icon-search"></span>
	  							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
							<button class="btnDel" title='<icms-i18n:label name="common_btn_clear"/>' value='<icms-i18n:label name="common_btn_clear"/>' type="button" onclick='clearDate()'>
								<span class="icon iconfont icon-icon-clear1"></span>
	  							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<shiro:hasPermission name="sbxxgl_sava">
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="common_btn_add"/>' onclick="createData()" value='<icms-i18n:label name="common_btn_add"/>'>
								<span  class="icon iconfont icon-icon-add"></span>
	  							<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_match">
	                        <input type="hidden" id="matchFlag" name="matchFlag" value="1" />
	                        </shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="sbxxgl_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission> 
						</td>
						</tr>
				</table>
			</div>
		</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="deviceGridTable_div">
				<table id="deviceGridTable"></table>
				<table id="gridPager"></table>
			</div>
		</div>
	</div>
	<div id="updateDiv" class="div_center" style="display: none">
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="res_title_deviceManage2"/></div>
				<div class="WorkSmallBtBg"><small id="s_updateTitle"><icms-i18n:label name="res_desc_deviceManage2"/> </small></div>
			</h1>
		</div>
		<form action="" method="post" id="updateForm">
			<div id="device" class="pageFormContent searchBg"
				style="align: center; padding-bottom: 5px; margin-bottom:10px;">
				<!-- 	<font>基本信息</font> -->
				<table style="width: 100%; margin: auto;">
					<tr style="margin-bottom: 5px">
						<th align="right" width="3%" height="45px"><i><font
								color="red">*</font><icms-i18n:label name="res_l_device_type"/>：</i></th>
						<td width="20%" height="45px" style="padding-right:3%;"><icms-ui:dic id="deviceType"
								name="deviceType" kind="DEVICE_TYPE" showType="select"
								attr="style='width: 150px;'class='selInput'" /></td>
						<th width="3%" height="55px"><i><font color="red">*</font><icms-i18n:label name="res_l_device_manufacturer"/>：</i></th>
						<td width="20%" height="55px" style="padding-right:3%;"><icms-ui:dic id="manufacturer"
								name="manufacturer" kind="DEVICE_MANUFACTURER" showType="select"
								attr="style='width:150px;'class='selInput'" /></td>
						<th width="3%" height="55px"><i><font color="red">*</font><icms-i18n:label name="res_l_device_model"/>：</i></th>
						<td width="20%" height="55px" style="padding-right:3%;"><select id="deviceModelId2"
							name="deviceModelId2" style="width: 150px;" class="selInput"
							onchange="selectDevModelByManuf(this.value)"><option
									value="0">----------<icms-i18n:label name="l_common_select"/>----------</option></select></td>
						<th width="3%" height="55px"><i><font color="red">*</font>SN：</i></th>
						<td width="20%" height="55px" style="padding-right:3%;"><input type="text" id="sn2"
							name="sn2" style="width: 140px;" class="textInput" /></td>
					</tr>
					<tr>
						<th><i><font id="deviceCss" style="display:none;color:red">*</font><icms-i18n:label name="res_pool_device_name"/>：</i></th>
						<td height="55px"><input type="text" id="deviceName2" name="deviceName2"
							style="width: 140px;" class="textInput" /></td>
						<th><i><font color="red">*</font><icms-i18n:label name="res_l_seat_code"/>：</i></th>
						<td><input type="text" name="seatId" id="seatId"
							style="width: 140px;" class="textInput" /></td>
						<th><i><icms-i18n:label name="res_l_desc_info"/>：</i></th>
						<td><textarea rows="1" name="description" id="description"
								style="width: 148px; border: 1px solid #d5d5d5; background-color: #f5f5f5; box-shadow: 1px 1px 2px #ededed;height: 25px;" ></textarea></td>
						<th></th>
						<td></td>
					</tr>
				</table>

			</div>


			<div id="cmHost" class="pageFormContent pageFormContent searchBg"
				style="align: center; display: none; padding-bottom: 5px; margin-bottom:10px;">
				<!-- 		<font>服务器设备信息</font> -->
				<table style="width: 100%; margin: auto;">
					<tr>
						<th align="right" width="5%" height="45px"><i><font
								color="red">*</font>CPU：</i></th>
						<td width="20%"><input type="text" name="cpu" id="cpu"
							style="width: 140px;" class="textInput" /></td>
						<th width="3%"><i><font color="red">*</font><icms-i18n:label name="res_l_mem_total"/>(G)：</i></th>
						<td width="20%"><input type="text" name="mem" id="mem"
							style="width: 140px;" class="textInput" /></td>
						<td width="3%"><i><font color="red">*</font><icms-i18n:label name="res_l_disk_size"/>(G)：</i></td>
						<td width="20%"><input type="text" name="disk" id="disk"
							style="width: 140px;" class="textInput" /></td>
						<td width="3%"><i><font color="red">*</font><icms-i18n:label name="res_l_disk_name"/>：</i></td>
						<td width="20%"><input type="text" name="localDisk"
							id="localDisk" style="width: 140px;" class="textInput" /></td>
					</tr>
					<tr>
						<th><i><icms-i18n:label name="res_l_device_capacity"/>(G)：</i></th>
						<td><input type="text" name="localsize" id="localsize"
							style="width: 140px;" class="textInput" /></td>
						<%-- <th width="3%"><i><font color="red">*</font><icms-i18n:label name="res_l_device_isBare"/>：</i></th>
						<td>
						<icms-ui:dic id="isBare" name="isBare" kind="IS_BARE" showType="select" attr="style='width:150px;'class='selInput'"/>
						</td> --%>
						
					</tr>
					<tr>
						<th width="3%"  height="45px"><i><icms-i18n:label name="res_l_ipmi_user"/>：</i></th>
						<td width="20%"  height="45px"><input type="text" name="IPMI_USER" id="IPMI_USER"
							style="width: 140px;" class="textInput" /></td>
							<th width="3%"  height="45px"><i><icms-i18n:label name="res_l_ipmi_pwd"/>：</i></th>
						<td width="20%"  height="45px"><input type="password" name="IPMI_PWD" id="IPMI_PWD"
							style="width: 140px;" class="textInput" /></td>
						<th width="3%"  height="45px"><i><icms-i18n:label name="res_l_ipmi_address"/>：</i></th>
						<td width="20%"  height="45px"><input type="text" name="IPMI_URL" id="IPMI_URL"
							style="width: 140px;" class="textInput" /></td>
						<th width="3%"  height="45px"><i><icms-i18n:label name="res_l_ipmi_ver"/>：</i></th>
						<td width="20%"  height="45px">
						<select type="text" name="IPMI_VER" id="IPMI_VER" class="selInput" >
						   <option value=""><icms-i18n:label name="res_l_comput_select"/></option>							
				  	       <option value="1.5">1.5</option>						
				  	       <option value="2.0">2.0</option>						
						</select>
						</td>
						<th></th>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>

			<div id="cMstorge" class="pageFormContent searchBg" style="display: none;padding-bottom: 20px; margin-bottom:10px;">
				<!-- 		<font>存储设备信息</font> -->
				<table style="width: 100%;margin:auto;">
					<tr>
						<th align="right" width="5%" height="45px"><i><font color="red">*</font><icms-i18n:label name="res_l_device_storageType"/>：</i></th>
						<td width="20%"><icms-ui:dic id="storageType" name="storageType" kind="STORAGE_TYPE" showType="select" attr="style='width: 150px;'class='selInput'" /></td>
						<th width="3%"><i><icms-i18n:label name="res_l_device_diskRpm"/>：</i></th>
						<td width="20%"><input type="text" name="diskRpm" id="diskRpm" style="width: 140px;" class="textInput"/></td>
                        <td width="3%"><i><icms-i18n:label name="res_l_device_portCount"/>：</i></td>
                        <td width="20%"><input type="text" name="portCount" id="portCount" style="width: 140px;" class="textInput"/></td>
                        <td width="3%"><i><icms-i18n:label name="res_l_device_microCode"/>：</i></td>
                        <td width="20%"><input type="text" name="microCode" id="microCode" style="width: 140px;" class="textInput" /></td>
						<input type="hidden" id="deviceId" name="deviceId" />
						<input type="hidden" id="validateSN" name="validateSN" />
						<input type="hidden" id="validateSeatId" name="validateSeatId" />
						<input type="hidden" id="hostId" name="hostId" />
						<input type="hidden" id="storageId" name="storageId" />
						<input type="hidden" id="datastoreId" name="datastoreId" />
						<input type="hidden" id="method" name="method" />
						<input type="hidden" id="LocalDeviceId" name="LocalDeviceId" />
						<input type="hidden" id="localDiskId" name="localDiskId" />
						<input type="hidden" id="validatedeviceName" name="validatedeviceName" />
						<input type="hidden" id="validateIpV4Name" name="validateIpV4Name" /><!-- wmy验证管理存储设备的管理Ip -->
					</tr>
					<tr height="45px">
						<th><i><icms-i18n:label name="res_l_disk_size"/>(G)：</i></th>
						<td><input type="text" name="diskSize" id="diskSize" style="width: 140px;" class="textInput" /></td>
						<th><i><icms-i18n:label name="res_l_device_mgrIp"/>：</i></th>
						<td><input type="text" name="mgrIp" id="mgrIp" style="width: 140px;" class="textInput" /></td>
						<th><i><icms-i18n:label name="res_l_device_cacheCapacity"/>：</i></th>
						<td><input type="text" name="cacheCapacity" id="cacheCapacity" style="width: 140px;" class="textInput" /></td>
						<th><i><icms-i18n:label name="res_l_device_diskCapacity"/>：</i></th>
						<td><input type="text" name="diskCapacity" id="diskCapacity" style="width: 140px;" class="textInput" /></td>
					</tr>
					<tr>
						<th><i><icms-i18n:label name="res_l_device_diskNumber"/>：</i></th>
						<td><input type="text" name="diskNumber" id="diskNumber" style="width: 140px;" class="textInput"/></td>
						<th><i><icms-i18n:label name="res_l_device_portSpeed"/>：</i></th>
						<td><input type="text" name="portSpeed" id="portSpeed" style="width: 140px;" class="textInput"/></td>
						<th></th>
						<td></td>
					</tr>

				</table>

			</div>
			<div id="cMdatastore" style="display: none;padding-bottom: 0px; margin-bottom:10px;">
				<font>&nbsp;</font>
				<div id="topDiv" style="width: 100%;">
					<div style="text-align:right; margin-bottom:20px; overflow:hidden; padding-top:0;" class="btnbar1" >
						
							<!--        <label id="device_error_tip"></label> -->
							
							<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_add"/>' onclick="createDatastoreData()" style="margin-right:5px; margin-left:0;">
							<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_delete"/>' onclick="deletDatastore()" style="margin-left:0; margin-right:5px;">
			
					</div>
					<div class="pageTableBg pageTableBg2">
						<div class="panel clear" id="grid_div">
							<table id="datastoreGridTable"></table>
							<table id="datastoregridPager"></table>
						</div>
					</div>
					
				</div>
			</div>
			<p class="btnbar btnbar1"  style="text-align:right; width:100%;padding:0px 0px;">
				<label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3 " title='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeView()" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-right: 5px; margin-left:0;" />
				<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3 " onclick="saveOrUpdateBtn()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px; margin-left: 5px;" />
			</p>
		</form>
	</div>
	<div id="updataCmDatastore" style="display:none;background-color:white; margin-bottom:10px;"  class="pageFormContent pageTableBg">
		<form action="" method="post" id="updateDatastoreForm">
				<input type="hidden" id="datastoreMethod" name="datastoreMethod" />
				<input type="hidden" id="datastoreId" name="datastoreId" />
				<input type="hidden" id="datastoreCheckName" name="datastoreCheckName"/><!-- 为了验证datastore名称，添加的 -->
				<input type="hidden" id="datastoreCheckPath" name="datastoreCheckPath"/><!-- 为了验证datastore路径，添加的 -->
				<p>
					<i><font color="red">*</font><icms-i18n:label name="res_l_device_orderNum"/>：</i>
					<input type="text" name="orderNum" id="orderNum"  class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="res_l_datastore_name"/>：</i>
					<input type="text" name="name" id="name" class="textInput"  />
				</p>
				<p id="pathFont">
					<i><font color="red" >*</font><icms-i18n:label name="res_l_datastore_path"/>：</i>
					<input type="text" name="path" id="path"  class="textInput" />
				</p>
				<p id="identifierFont">
					<i><font color="red" >*</font><icms-i18n:label name="res_l_detastore_identifier"/>：</i>
					<input type="text" name="identifier" id="identifier"  class="textInput" />
				</p>
			<p  style="text-align:right;width:240px;margin-top:8px;" class="btnbar1">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeDatastoreView()" style="margin-left: 5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' onclick="saveOrUpdateDatastoreBtn()" style="margin-right: 10px">
			</p>
		</form>
	</div>
	<div id="lookDiv" class="div_center" style="display: none">
	<div class="page-header">
			<h1>
				 <div class="WorkBtBg"><icms-i18n:label name="res_title_deviceManage2"/></div>
				<div class="WorkSmallBtBg"><small id="s_updateTitle"><icms-i18n:label name="res_desc_deviceManage3"/></small></div>
			</h1>
		</div>
		<div id="lab_device" class="pageFormContent searchBg" style="margin-bottom:14px;padding-right:0px;padding-left:0px;">
<!-- 			<font>基本信息</font> -->
			<table style="margin-left: 30px;">
				<tr>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_host_nmae"/>：</i></th>
					<td style="width: 12%"><input id="lab_deviceName2" type="text" class="readonly"   readonly="readonly" /></input></td>
					<th style="width: 5%"><i>SN：</i></th>
					<td style="width: 12%"><input id="lab_sn2" type="text" class="readonly"   readonly="readonly" /></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_device_pool"/>：</i></th>
					<td style="width: 12%"><input id="lab_resPoolId2" type="text" class="readonly"   readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_seat_code"/>：</i></th>
					<td style="width: 12%"><input id="lab_seatId" type="text" class="readonly"   readonly="readonly"></input></td>
				</tr>
				<tr>
					<th><i><icms-i18n:label name="res_l_device_type"/>：</i></th>
					<td><input id="lab_deviceType" type="text" class="readonly"  readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_manufacturer"/>：</i></th>
					<td><input id="lab_manufacturer" type="text" class="readonly"   readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_model"/>：</i></th>
					<td><input id="lab_deviceModelId2" type="text" class="readonly"   readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_desc_info"/>：</i></th>
					<td><input id="lab_description" type="text" class="readonly"   readonly="readonly"></input></td>
				</tr>
			</table>

		</div>


		<div id="lab_cmHost" class="pageFormContent searchBg">
<!-- 			<font>服务器设备信息</font> -->
			<table style="margin-left: 30px; width: 100%;padding-right:0px;padding-left:0px;">
				<tr>
					<th style="width: 5%"><i>CPU：</i></th>
					<td style="width: 12%"><input id="lab_cpu" class="readonly"   readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_mem_total"/>：</i></th>
					<td style="width: 12%"><input id="lab_mem" class="readonly"   readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_used_cpu"/>：</i></th>
					<td style="width: 12%"><input id="lab_cpuUsed" class="readonly"  readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_used_mem"/>：</i></th>
					<td style="width: 12%"><input id="lab_memUsed" class="readonly"   readonly="readonly"></input></td>
				</tr>
				<tr>
					<th><i><icms-i18n:label name="res_l_device_capacity"/>：</i></th>
					<td><input id="lab_localsize" class="readonly"  readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_disk_name"/>：</i></th>
					<td><input id="lab_localDisk" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_diskSize"/>：</i></th>
					<td><input id="lab_disk" class="readonly"  readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_is_invc"/>：</i></th>
					<td><label id="lab_nanotubes" ></label></td>
				</tr>
				<tr>
				    <th><i><icms-i18n:label name="res_l_device_isBare"/>：</i></th>
					<td><label id="lab_isBare" ></label></td>
				</tr>
			</table>
		</div>

		<div id="lab_cMstorge" class="pageFormContent pageTableBg searchBg" style="width: 100%;padding-right:0px;padding-left:0px;">
<!-- 			<font>存储设备信息</font> -->
			<table style="margin-left: 30px;">
				<tr>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_device_storageType"/>：</i></th>
					<td style="width: 12%"><input id="lab_storageType" type="text" class="readonly" readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_device_diskRpm"/>：</i></th>
					<td style="width: 12%"><input id="lab_diskRpm" type="text" class="readonly" readonly="readonly"></input></td>
                    <th style="width: 5%"><i><icms-i18n:label name="res_l_device_portCount"/>：</i></th>
					<td style="width: 12%"><input id="lab_portCount" type="text" class="readonly" readonly="readonly"></input></td>
					<th style="width: 5%"><i><icms-i18n:label name="res_l_device_microCode"/>：</i></th>
					<td style="width: 12%"><input id="lab_microCode" type="text" class="readonly" readonly="readonly"></input></td>

				</tr>
				<tr>
					<th><i><icms-i18n:label name="res_l_device_diskSize"/>：</i></th>
					<td><input id="lab_diskSize" type="text" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_mgrIp"/>：</i></th>
					<td><input id="lab_mgrIp" type="text" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_cacheCapacity"/>：</i></th>
					<td><input id="lab_cacheCapacity" type="text" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_diskCapacity"/>：</i></th>
					<td><input id="lab_diskCapacity" type="text" class="readonly"  readonly="readonly"></input></td>
				</tr>
				<tr>
					<th><i><icms-i18n:label name="res_l_device_diskNumber"/>：</i></th>
					<td><input id="lab_diskNumber" type="text" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_device_portSpeed"/>：</i></th>
					<td><input id="lab_portSpeed" type="text" class="readonly" readonly="readonly"></input></td>
					<th><i><icms-i18n:label name="res_l_is_invc"/>：</i></th>
					<td><label id="lab_nanotubes2"></label></td>
					<td></td>
					<td></td>
				</tr>

			</table>

		</div>
		<div id="lab_cMdatastore">
			<!-- 	<div class="" style="margin:0 auto; height:50px; width:100%;border:solid 1px #abc6d1;"> -->
			<!-- 	<table id="lab_datastoreGridTable"></table> -->
			<!-- 	<table id="lab_datastoregridPager"></table> -->
			<!-- 	</div> -->
			<div id="topDiv" style="width: 100%;">
				<table align="center" style="margin-bottom: 10px;">
					<tr>
						<!--        <label id="device_error_tip"></label> -->
						<td style="width: 40%"></td>
						<td style="width: 40%"></td>
						<td style="width: 40%"></td>
						<td style="width: 40%"></td>
					</tr>
				</table>
				<div class="panel clear" id="labGrid_div" style="width: 100%" >
					<table id="lab_datastoreGridTable"></table>
					<table id="lab_datastoregridPager"></table>
				</div>
			</div>
		</div>
             <p class="btnbar btnbar1" style="text-align:right; width:100%;">
				<label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="res_btn_returnList"/>' onclick="closeLabView()" style="margin-right: 5px;" />
			 </p>
	</div>

</body>
</html>
