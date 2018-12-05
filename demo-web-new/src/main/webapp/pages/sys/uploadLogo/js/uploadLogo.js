/**
 * 提交验证，验证成功后进行保存操作
 */
function updateOrSaveForParam() {
	submitValidate();
}

/**
 * 验证是否为空
 */
function submitValidate() {
	var paramValue = $("#paramValueImg").val().trim().length;
	if (paramValue <= 0) {
		showTip("图片不能为空!");
		return false;
	}else{
		save();
	}	
}

/**
 * 执行保存方法
 */
function save() {
	var paramName = $('input[name="paramName"]:checked').val();
	var paramImg = '';
	if (paramName == 'CloudTubeLogo' || paramName == 'TenantLogo') {
		paramImg = image;
	}
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/uploadLogo/uploadLogo.action",
		async : false,
		data : {// 获得表单数据
			"paramName" : paramName,
			"paramImg" : paramImg,

		},
		success : function(data) {
			 if(data.result=='success'){
				 showTip("保存成功", null, "green");
				 document.getElementById("addParameterForm").reset();
				 document.getElementById('img').src = "";
             }else{
            	 showTip("发生错误", null, "red");
            	 document.getElementById("addParameterForm").reset();
             }
		},
		error : function() {
			showTip("发生错误", null, "red");
		}
		
	});
}

/**
 * 图片转为Base64
 */
var image = '';
function selectImage(file) {
    if (!file.files || !file.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
		image = evt.target.result;
		document.getElementById('img').src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}