<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/tankflow/instance/css/TankFlow.css?t=${sfx}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/tankflow/instance/css/default.css?t=${sfx}"/>
<link href="${ctx}/pages/tankflow/designer/font/iconfont.css?t=${sfx}" type="text/css" rel="stylesheet">
<link href="${ctx}/pages/tankflow/designer/icon/allicon.css?t=${sfx}" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/TankFlowFunc.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/json2.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/js/NewTankFlow.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/js/constants.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/processInstance.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/designer/icon/iconfont.js?t=${sfx}"></script>
<%-- <script type="text/javascript" src="${ctx}/pages/ui/processInstance.js?t=${sfx}"></script> --%>

<style type="text/css">
	.instanceTable tr td { 
	}
	.instanceTable tr td input{font-size:12px; padding:6px 4px;}
	.descript {
		padding-left: 10px;
	}
	#menuContainer{
        background:#fff;
        border:1px solid red;
        box-shadow: 10px 10px 5px #888888;
    }
    #menuContainer button:hover{
        color:red;
       	transform:scale(1,1.15);
       	background:#c1dcfc;
       	opacity:0.5;
   	 }
</style>
