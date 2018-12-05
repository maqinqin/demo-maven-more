<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-slider.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/slider.css"></link>
<script type="text/javascript" src="${ctx}/pages/service/js/imageParamList.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
#updateDiv i {text-align:left; padding-right:3px;}
#updateDiv p{width:600px; margin-bottom:5px; margin-left:18px;  }
.updateDiv_list{overflow:hidden; display:inline-block; width:280px; float:left;}
#updateDiv label{display:block; padding-left:102px;}
#softWareDiv i {text-align:left; padding-right:3px;}
#softWareDiv p{width:300px; margin-bottom:5px; margin-left:18px;  }
#softWareDiv label{display:block; padding-left:103px;}
.modo-pageFormContent p{padding-left:20px;}
.modo-pageFormContent i {width: 100px;}
#passwordDiv{width:165px; height:30px; display:display: initial;position:relative; }
#passwordDiv img{position:absolute; right:8px; top:7px; cursor:pointer;}
#passwordDiv input{padding-right:30px; width:124px; overflow:hidden;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
		
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_mir"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
						<tr style=" height:55px; ">
					<td class="tabBt" style="width:55px;" ><icms-i18n:label name="bim_l_mirName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="selectImageName" name="selectImageName"  class="textInput"/>
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
					</tr>
			</table>
			</div>
		</div>
		<div class="searchBtn_right" style=" float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td >
	           <a href="javascript:search()"  type="button" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' class="btn" onclick=""search();return false;";">
							<span class="icon iconfont icon-icon-search"></span>
	  						<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
						</a>
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  onclick="clearAll()">
							<span  class="icon iconfont icon-icon-clear1"></span>
	  						<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
						<shiro:hasPermission name="image:create">
							<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' class="btn" onclick="showCreateDiv()" >
								<span class="icon iconfont icon-icon-add"></span>
	  							<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="image:update"> 
				            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
				        </shiro:hasPermission>
				        <shiro:hasPermission name="image:delete"> 
				            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
				        </shiro:hasPermission>
				        <shiro:hasPermission name="image:softWare"> 
				            <input type="hidden" id="softWareFlag" name="softWareFlag" value="1" />
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
	<div id="updateDiv"style="display: none;" >
		<form action="" method="post" id="updateForm">
		<div  id="updateTab"  class="pageFormContent">
			<input type="hidden" id="imageId" name="cloudImage.imageId" />
			<input type="hidden" id="isActive" name="cloudImage.isActive" />
			<input type="text" style="display: none" id="method" name="method" />
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_mirName"></icms-i18n:label>:</i><input class="textInput"  type="text" id="imageName" name="cloudImage.imageName" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_mirSavePath"></icms-i18n:label>:</i><input class="textInput"  type="text" id="imagePath" name="cloudImage.imagePath" />
			</span>
			<span class="updateDiv_list" style="width: auto;">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_mirSaveURL"></icms-i18n:label>:</i><textarea class="textInput"  id="imageUrl" name="cloudImage.imageUrl" style="width: 435px;"></textarea>
			</span>		
			
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_mirSize"></icms-i18n:label>:</i><input class="textInput" type="text" id="imageSize" name="cloudImage.imageSize" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_diskSize"></icms-i18n:label>:</i><input class="textInput" type="text" id="udiskCapacity" name="cloudImage.diskCapacity" />
			</span>
			
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_username"></icms-i18n:label>:</i><input class="textInput" type="text" id="manager" name="cloudImage.manager" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_password"></icms-i18n:label>:</i><span id="passwordDiv"><input class="textInput" type="password" id="password" name="cloudImage.password" /><img src="${ctx}/images/eyeClose.jpg" onclick="changePassword();" style="width:20px; height:12px;"></span>
			</span>
			
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_manageType"></icms-i18n:label>:</i><icms-ui:dic id="manageType" name="cloudImage.manageType" kind="MANAGE_TYPE"  showType="select"  attr="class='selInput'"/>
			</span>
			<span id="spanImageType" class="updateDiv_list" style="display: none">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_mirType"></icms-i18n:label>:</i><icms-ui:dic id="imageType" name="cloudImage.imageType" kind="IMAGE_TYPE"  showType="select"  attr="class='selInput'"/>
			</span>
			
			<span class="updateDiv_list" style="width: auto;">
				<i><icms-i18n:label name="bim_l_mirDescribe"></icms-i18n:label>:</i><textarea class="textInput" type="text" id="remark" name="cloudImage.remark" style="width: 435px;"></textarea>
			</span>
			</p>
			<div class="nowrap right"></div>   
		</div>
		<div class="winbtnbar btnbar1" style="width:589px; margin-bottom:20px; padding-left:0;">
		<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-left:0; margin-right:5px;" class="btn btn_dd2 btn_dd3" onclick="closeView()" style="margin-right:5px;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
		</div>
		</form>
	</div>
	<!-- 新增软件 -->
	<div id="softWareDiv"   style="display: none; height: 100px ">
		<form action="" method="post" id="softWareForm">
		<div  id="softWareTab"  class="pageFormContent">
			<input type="hidden" id="softWareImageId" name="softWareImageId" /> 
			<input type="hidden" id="softWareIsActive" name="softWareIsActive" />
			<input type="hidden" id="softWareMethod" name="softWareMethod" />
			<input type="hidden" id="softWareRowId" name="softWareRowId" />
			<input type="hidden" id="softWareRowId" name="softWareRowId" />
			<input type="hidden" id="imageSoftwareId" name="imageSoftwareId" />
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_software"></icms-i18n:label>:</i><select  class="selInput" style="width: 140px;" id="softWareSelectId" name="softWareSelectId" ></select> 
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_version"></icms-i18n:label>:</i><select  class="selInput" style="width: 140px;" id="softWareVerSelectId" name="softWareVerName"></select>
			</p>
			<div class="nowrap right"></div>  
			</div>
			<div class="winbtnbar btnbar1" style="width:282px; padding-left:0;"><label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeSoftVer()" style="margin-right: 5px; margin-left:0;" />
				<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="addSoftVerBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
			</div>
		</form>
	</div>
	<!-- 软件信息 -->
	<div id="softWareVerDiv"  style="display: none; padding-top:0;" class="content-main clear">
		<div class="pageFormContent" style="padding-top:0; padding-bottom:0;">
		<table width="100%" >
			<tr>
				<td width="100%" align="right" class="btnbar1" style="padding-left:0;">
				<shiro:hasPermission name="image:createsoftWare">
					<input type="button" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="createSoftWare()" style="margin-left: 505px;"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="image:updateSoftWare"> 
		            <input type="hidden" id="updateSoftWareFlag" name="updateSoftWareFlag" value="1" />
		        </shiro:hasPermission>
				<shiro:hasPermission name="image:deleteSoftWare"> 
		            <input type="hidden" id="deleteSoftWareFlag" name="deleteSoftWareFlag" value="1" />
		        </shiro:hasPermission>
				&emsp;
				</td>
			</tr>
		</table>
		</div>
		<div  class="panel clear" id="softWareTableDiv">
			<table id="softWareTable"></table>
		</div>
	</div>
	<!-- 查看镜像 -->
	<div id="showDiv"style="display: none;" class="ui-dialog-content-np" > 
		<div  id="showTab"  class="pageFormContent modo-pageFormContent">	
			<p style="width:870px">
				<i><icms-i18n:label name="bim_l_mirName"></icms-i18n:label>:</i><input name="imageNameLab" id="imageNameLab" type="text" class="readonly"  size="30" readonly="readonly" style="width:400px">
			</p>
			<p>
				<i><icms-i18n:label name="bim_l_mirSize"></icms-i18n:label>:</i><input name="imageSizeLab" id="imageSizeLab" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p>
				<i><icms-i18n:label name="bim_l_diskSize"></icms-i18n:label>:</i><input name="diskCapacity" id="diskCapacity" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p>
				<i><icms-i18n:label name="com_l_username"></icms-i18n:label>:</i><input name="managerLab" id="managerLab" type="text" class="readonly"  size="30" readonly="readonly"> 
			</p>
			<p>
				<i><icms-i18n:label name="bim_l_mirSavePath"></icms-i18n:label>:</i><input name="imagePathLab" id="imagePathLab" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p style="width: 870px">
				<i><icms-i18n:label name="bim_l_mirSaveURL"></icms-i18n:label>:</i><input name="imageUrlLab" id="imageUrlLab" type="text" class="readonly" style="width: 700px; "   size="30" readonly="readonly">
			</p>
			<p  style="width: 870px">
				<i><icms-i18n:label name="bim_l_mirDescribe"></icms-i18n:label>:</i><input name="remarkLab" id="remarkLab" type="text" class="readonly"  size="30"style="width: 695px;"  readonly="readonly"> 
			</p>
			<p>
				<i><icms-i18n:label name="bim_l_softInfo"></icms-i18n:label>:</i>
				<div class="interval"></div>
				<div class="panel clear mode_table" id="showSoftWareDiv" >
					<table id="showSoftWareTable" ></table>
							<div id="showSoftWareTable"></div>
				</div>
			</p>
		</div>
	</div>
</body>
</html>