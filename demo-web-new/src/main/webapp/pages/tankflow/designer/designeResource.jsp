<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link href="${ctx}/css/new.css" type="text/css" rel="stylesheet">
<%-- <link href="${ctx}/css/bootstrap.css" type="text/css" rel="stylesheet"> --%>
<link href="${ctx}/pages/tankflow/designer/css/common.css?t=${sfx}" type="text/css" rel="stylesheet">
<link href="${ctx}/pages/tankflow/designer/css/explorer.css?t=${sfx}" type="text/css" rel="stylesheet">
<link href="${ctx}/pages/tankflow/designer/css/TankFlow.css?t=${sfx}" type="text/css" rel="stylesheet">
<link href="${ctx}/pages/tankflow/designer/css/topMenu.css?t=${sfx}" type="text/css" rel="stylesheet">
<link href="${ctx}/pages/tankflow/designer/icon/allicon.css?t=${sfx}" type="text/css" rel="stylesheet">
<style type="text/css" media="screen">
object:focus {
	outline: none;
}
.ztree li{margin-top:0;}
 	#contextmenu{
        background:#fff;
        border:1px solid red;
        box-shadow: 10px 10px 5px #888888;
    }
    #contextmenu button:hover{
        color:red;
    }
</style>
<script type="text/javascript" src="${ctx}/pages/tankflow/plugin/canvg.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/plugin/html2canvas.min.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/plugin/promise.min.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/plugin/printThis.js?t=${sfx}"></script>

<script type="text/javascript" src="${ctx}/pages/tankflow/js/NewTankFlow.js?t=${sfx}"></script>

<script type="text/javascript" src="${ctx}/pages/tankflow/designer/js/TankFlowFunc.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/designer/js/json2.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/designer/js/designer.js?t=${sfx}"></script>	

<script type="text/javascript" src="${ctx}/pages/tankflow/designer/icon/iconfont.js?t=${sfx}"></script>

