/**
 * 初始化
 */
$(function() {
	$.ajax({
		type : "POST",
		url : ctx + "/sys/user/findUserByCurrent.action",
		async : false,
		cache : false,
		success : function(data) {
			$("#loginName_labl").val(data.loginName);
			$("#userId").val(data.userId);
			$("#oldPwd").val(data.loginPassword);
		},
		error : function(e) {
			showTip(exception_info);
		}
	});

});

function saveUserBtn(){
	var passwordOld = $("#oldPwd").val();
	var passwordOldInput = $("#sys_l_loginPwd").val();
	var userId = $("#userId").val();
	var passWord = $("#loginNewPwd_labl").val();
	var pwd1=document.getElementById("loginNewPwd_labl").value;
	var pwd2=document.getElementById("loginNewPwd2_labl").value;
	if(passwordOld != hex_sha1(passwordOldInput)){
		return;
	}if(pwd1 != pwd2){
		return;
	}
	
	$.ajax({ 
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/user/modifyUserByOldPassword.action",
		async : true,
		data : {
			"userId" : userId,
			"passWord" : hex_sha1(passWord)
		},
		success : (function(data) {
			showTip(i18nShow('sys_l_ModifySaveSuccess'));
			formReset();
			return;
		}),
		error : function() {
			showError(i18nShow('tip_error'));
		}
	});
	
}

function contrast(){
	mdiv = document.getElementById("userloginpwd");
	var passwordOld = $("#oldPwd").val();
	var passwordOldInput = $("#sys_l_loginPwd").val();
	if(passwordOld != hex_sha1(passwordOldInput)){
		mdiv.innerHTML="<font color='red'>"+i18nShow('sys_l_ModifyOldPwdWrong')+"</font>";
		return false;
	}
	mdiv.innerHTML="<font color='red'></font>";
	return true;
}

function formReset(){
	$("#demo tr td input[type=password]").val('')
}
function checkPass(){
	mdiv = document.getElementById("usermsg");
	var pwd1=document.getElementById("loginNewPwd_labl").value;
	var pwd2=document.getElementById("loginNewPwd2_labl").value;
	if(pwd1 == null || pwd2 == null){
		mdiv.innerHTML="<font color='red'>"+i18nShow('sys_l_ModifyPasswordEnpty')+"</font>";
	    return false;
	 }
	if(pwd1!=pwd2){
		mdiv.innerHTML="<font color='red'>"+i18nShow('sys_l_ModifyPasswordIncorrect')+"</font>";
	    return false;
	 }
	mdiv.innerHTML="<font color='red'></font>";
	return true;
	}
