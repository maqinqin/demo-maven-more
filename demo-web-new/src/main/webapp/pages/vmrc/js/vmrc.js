/**
 * VMware 远程控制台
 * wangjingxin
 * @date 2016/06/24
 */

function initialize() {
    // 初始化
    init();
    // 获取连接信息
    var url = ctx + '/vmrc/main/showConsole.action';
    var vmId = WebUtil.getQueryVar('vmId','');
    var data = {"vmrcVo.vmId":vmId};
    $.ajax({
        type:'get',
        datatype : "json",
        url:url,
        async : false,
        data:data,
        success:(function(data){
            var r_data = data;
            
            VMRC_GRO.modeMask = r_data.modeMask;
            VMRC_GRO.msgMode = r_data.msgMode;
            VMRC_GRO.advancedConfig = r_data.advancedConfig;
            VMRC_GRO.connect_host = r_data.connectHost;
            VMRC_GRO.connect_thumbprint = r_data.connectThumbprint;
            VMRC_GRO.connect_allow_ssl_errors = r_data.connectAllowSslErrors;
            VMRC_GRO.connect_ticket = r_data.connectTicket;
            VMRC_GRO.connect_username = r_data.connectUsername;
            VMRC_GRO.connect_password = r_data.connectPassword;
            VMRC_GRO.connect_vmid = r_data.connectVmid;
            VMRC_GRO.connect_datacenter = r_data.connectDatacenter;
            VMRC_GRO.connect_vmpath = r_data.connectVmpath;
            
            document.title = 'VMware 远程控制台: ' + r_data.devName;
        }),
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert('Error: ' + XMLHttpRequest.responseText);
        } 
    });
    // startup
    startup();
    // connect
    connect();

}

WebUtil = {};
// Read a query string variable
WebUtil.getQueryVar = function (name, defVal) {
    "use strict";
    var re = new RegExp('.*[?&]' + name + '=([^&#]*)'),
        match = document.location.href.match(re);
    if (typeof defVal === 'undefined') { defVal = null; }
    if (match) {
        return decodeURIComponent(match[1]);
    } else {
        return defVal;
    }
};
