<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
	String path = request.getContextPath();

	ResourceBundle resource = ResourceBundle.getBundle("style");//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可
	String logoStyle = resource.getString("logoStyle");
	String loginLogoImage = resource.getString(logoStyle + "LoginLogoImage");
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/jquery.base64.js"></script>
<script type="text/javascript" src="${ctx}/common/sha1.js"></script>

<title><icms-i18n:label name="show_title"/></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<link rel="stylesheet" type="text/css"
	href="<%=context%>/jquery/css/jbox.css"></link>
<link rel="stylesheet" href="${ctx}/css/login.css" type="text/css"></link>

</head>
<script>
	// 获取logo
	$(function () {
		$.ajax({
            type : "POST",
            url : ctx + "/sys/uploadLogo/getLogo.action",
            data : {},
            async : true,
            cache : false,
            success : function(data) {
            	for(var i = 0 ; i < data.length; i ++){
            		if(data[i].paramName=="CloudTubeLogo"){
            			document.getElementById('logoImage').src = data[i].paramLogo;   
            			break;
            		}
            	}
            },
            error : function(e) {
            }
        });
	});

  $(document).ready(function(){ 
    
    //如果登录页在frame窗口中打开，则跳转到主窗口中打开
    if( window.top != window.self ){
       window.top.location='<%=path%>/shiro/logout.action';
    }
    
    //设置光标位置
  document.getElementById("username").blur();
  window.setTimeout( function(){ document.getElementById("username").focus(); }, 0);
      
	  var uploadResult = '${uploadResult}';//从action类中获得相应的值
	  if(uploadResult!="") {
		  showTip(uploadResult);
		  //login();
	  }
	  
    	//输入提示;
		$("#username").blur(function(){
			if($("#username").val() == ""){
				$(this).attr("value",i18nShow('login_name'));
				$("#username").css("color","#484e5e");
			}
		});
    	
		$("#username").focus(function(){
			$("#username").select();
			if($("#username").val() == i18nShow('login_name')){
				$(this).attr("value","");
				$("#username").css("color","#484e5e");
			}
		});
		
		 $("#userid").blur(function(){
			if($("#userid").val() == ""){
				$("#userid").css("color","#484e5e");
			}
		});
    	
		$("#userid").focus(function(){
			$("#userid").select();
		}); 
		
    }); 
  function encodeBase64(mingwen, times) {
		var code = "";
		var num = 1;
		if (typeof times == 'undefined' || times == null || times == "") {
			num = 1;
		} else {
			var vt = times + "";
			num = parseInt(vt);
		}
		if (typeof mingwen == 'undefined' || mingwen == null || mingwen == "") {
		} else {
			$.base64.utf8encode = true;
			code = mingwen;
			for (var i = 0; i < num; i++) {
				code = $.base64.btoa(code);
			}
		}
		return code;
	}
    //平台用户登录;
	function login() {
		var un = $("#username").val();
		var userid = $("#userid").val();
		console.log(userid);
		if(un == i18nShow('login_name')){
			un = "";
		}
		if(userid == "111111"){
			userid = "";
		}
		userid = hex_sha1(userid);
		showTip("load");
		$.ajax({
			type : "POST",
			url : "<%=context%>/shiro/login.action",
			data : {
				"username" : un,
				"userid" : userid
			},
			async : true,
			cache : false,
			success : function(data) {
				if(data.success){
					//成功;
					location.href = "<%=context%>/pages/index.jsp";
				} else {
					if(data.msg == "1" || data.msg == "2") { // 1、没找到证书 2、证书过期
						var msg = (data.msg == "1") ? "是否导入证书？" : "证书过期，是否重新导入证书？";
						showTip(msg, function() {
							$("#certificateDiv").dialog({
								width : 750,
								autoOpen : true,
								modal : true,
								height : 245,
								title : '证书导入'
							});
						});//是否导入证书
					} else {
						showTip(data.msg);
						refresh();
					}
				}
			},
			error : function(e) {
				showTip(exception_info);
			}
		});
		closeTip();
	}

	//重置;
	function reset() {
		$("#username").attr("value", i18nShow('login_name'));
		$("#userid").attr("value", "111111");
	}

	//回车事件;
	
		var isShow=false;
		//回车事件;
		document.onkeydown=function(e)
		{
			if(!e)
				//火狐中是 window.event
				e=window.event;
			if((e.keyCode||e.which)==13)
			{
				if(isShow)
				{
					isShow=false;
					closeTip();
				}
				else
				{
					isShow=true;
					login();
				}
			}
	}
	function fileLoad() {
		var fileValues = document.getElementById("myFile").value.split("\\");
		var frontName = fileValues[fileValues.length - 1].split(".");
		if (frontName[1] == "lic" || frontName[1] == "LIC") {
			$('#importform').submit();
		} else {
			alert("请上传正确的证书文件！");
		}
  }
 </script>
<style>
        *{
            margin:0;
            padding:0;
        }
        .logo img{
            position:absolute;
            left:2%;
           width: 12%;
           top: 3px;
          background-size: 100%; 
        }
        .form{
            margin-top:10%;
            margin-right:11%;
            background: #fff;
	      box-shadow: 0 2px 15px 4px rgba(85, 85, 85, 0.12);
			border-radius: 5px;
			padding: 3%;
			width:28%;
			float:right;
			box-sizing: border-box;
        }
        .form ul{
            margin:5% auto 0 auto;
        }
        .form ul li{
            position:relative;
            list-style: none;
            border: 1px solid #e5e6e7;
            border-radius: 4px;
            padding:5px 8px;
            /* line-height: 25px; */
            margin-bottom:5%;
            background:#fff;
            height:25px;
        }
        .form ul li img{
            position:absolute;
            top:9px;
            left:10px;
        }
        .form ul li:nth-child(1) input{
            width: 250px;
            background:none;
            border:none;
            margin-left:40px;
            color:#484e5e;
            display:inline-block;
            height:25px;
            line-height:25px;
        }
        .form ul li:nth-child(2) input{
            width: 250px;
            background:none;
            border:none;
            margin-left:40px;
            color:#484e5e;
            display:inline-block;
            height:25px;
            line-height:25px;
        }
        .form ul li:nth-child(3) input{
            width: 100px;
            background:none;
            border:none;
            margin-left:40px;
            color:#484e5e;
            display:inline-block;
            height:25px;
            line-height:25px;
        }
        .Btn_Login{	
        font-size:1.2em;
		cursor: pointer;
		 float:left;
		padding:10px 15px;
		color:#fff;
		letter-spacing:2px;
		border-radius: 2px;
		background: #A01830;
		color:#fff;
		width:100%;
		box-shadow: 0px 2px 1px #841727;
		margin-top:35px;
}

.Btn_Login:hover{box-shadow: none; background:#841727;}
h2.loginText{
	color: #484e5e;
	font-size: 24px;
	text-align: center;
	line-height: 50px;
	font-weight: 400;
	}
       
    </style>
<body style="width: 100%; height: 100%; background:#f6f6f6  url(<%=context%>/images/login-background1.jpg) no-repeat; background-size: 100%;">
    <div class="body">
    <div class="logo">
	       <img id="logoImage" src="" class="logo">   
	    </div>
    <form class="form">
    <h2 class="loginText">云管平台管理控制台</h2>
        <ul>
            <li>
                <img src="<%=context%>/images/username.png">
               <input type="text" class="UserPassIdCode" id="username" name="username"  placeholder='<icms-i18n:label name="l_login_login_name"/>'/>
            </li>
            <li>
                <img src="<%=context%>/images/password.png">
                <input type="password" class="UserPassIdCode" id="userid" name="userid"  placeholder='<icms-i18n:label name="com_l_password"/>'/>
            </li>
            	<a href="javascript:login();" class="loginIcon">
            		<input type="button" class="Btn_Login"  title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="l_login_login_action"></icms-i18n:label>' style="vertical-align:middle"/>
            	</a>
        </ul>
    </form>
    
     <div class="logoIn_footer">
		    <span style="font-style: italic;font-weight: bold;"><img src="<%=context%>/images/footImag.png" width="11" height="11"/>Copyright:&nbsp;&nbsp;</span>
		    <span style="text-decoration: underline;">2017 Global InfoTech All rights reserved</span>
		    <span style="font-style: italic;font-weight: bold;">&nbsp;&nbsp;www.git.com.cn</span>
	    </div>
</div>
    
    
    
    
	<div id="certificateDiv"
		style="display: none; background-color: white;">
		<form action="<%=context%>/shiro/fileUpload.action" method="post" enctype="multipart/form-data" name="importform" id="importform">
			<table align="center" width="50%">
				<tr>
				  
					<td>请选择要上传的文件：</td>
					<td><input type="file" id="myFile" name="myFile"
						style="width: 100%" /></td>
				</tr>
				
			</table>
			<br/>
		 <p align="center">	<input type="button" class="btn"
					src="${ctx}/images/btnBg.jpg" value="上传"
					onclick="fileLoad()"></p>
			
		</form>
	</div>
	<script>
        window.onload = function () {
            document.getElementById("userid").addEventListener('input', function () {
                var _this = this;
                var newPassword = _this.value;
                var oldPassword = _this.getAttribute("password");
                var deta = newPassword.length-oldPassword.length;

                var truePassword = "";
                var p = _this.selectionEnd;//光标结束时的位置

                for(var i=0; i<newPassword.length; i++){
                    var c = newPassword.charAt(i);
                    if(i<p && c!='●'){
                        truePassword += c;
                    }else if(i<p && c=='●'){
                        truePassword +=  oldPassword.charAt(i);
                    }else {
                        truePassword += oldPassword.substr(oldPassword.length-newPassword.length+p,newPassword.length-p);
                        break;
                    }

                }
                newPassword = truePassword.replace(/\S/g, '●');

                _this.setAttribute('password', truePassword);
                _this.value = newPassword;

		// 解决在win8中光标总是到input框的最后	
                _this.selectionEnd = p;
                _this.selectionStart = p;

                //console.log(truePassword);
            },false);
        }
    </script>
</body>
</html>