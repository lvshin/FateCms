var $btn;

function login() {
	window.location.href = "op/login/goLogin";
}

function logout() {
	$.ajax({
		type : "post",
		url : "op/login/logout",
		dataType : 'json',
		error : function() {
			alert("通信错误");
		},
		success : function(data) {
			if (data.success) {
				location.href="/";
			} else {
				var d = dialog({
					content : "登出失败"
				});
				d.showModal();
				setTimeout(function() {
					d.close().remove();
				}, 1000);
			}
		}
	});

}

$(function() {
	if ($.cookie("chkRememberMe") == "true") {
		$("#chkRememberMe").attr("checked", true);
		$("#loginName").val($.cookie("reinforceLoginName"));
		$("#loginPwd").val($.cookie("reinforceLoginPwd"));
	}
	
	$("#loginsubmit").click(function () {
	    $btn = $(this).button('loading');
	    login();
	  });
	
	// 如果记住密码后用户名有更改，则去除记住密码
	$(document).on("keyup change", "#loginName", function() {
		$("#chkRememberMe").removeAttr("checked");
		$("#loginPwd").val("");
		saveUserInfo();
	});

	$(document).on("keyup change", "#loginPwd", function() {
		$("#chkRememberMe").removeAttr("checked");
		saveUserInfo();
	});
});
function login() {
	var pwd;
	if ($.cookie("chkRememberMe") == "true"
			&& $("#chkRememberMe:checked").length > 0 && $.cookie("reinforceLoginName")==$("#loginName").val())
		pwd = $("#loginPwd").val();
	else
		pwd = $.md5($("#loginPwd").val());
	$.ajax({
		type : "post",
		url : "op/login/submit",
		dataType : 'json',
		data : {
			loginName : $("#loginName").val(),
			pwd : pwd,
			geetest_challenge : $(".geetest_challenge").val(),
			geetest_validate : $(".geetest_validate").val(),
			geetest_seccode : $(".geetest_seccode").val()
		},
		error : function() {
			dialog.alert("通信错误");
		},
		success : function(data) {
			if (data.success) {
				saveUserInfo();
				window.location.href = data.callback;
			}

			else {
				$btn.button('reset');
				var d = dialog({
					content : data.message,
					title : '提示',
					width : '200px',
					okValue : '确定',
					ok : function() {
						return true;
					}
				});
				d.showModal();
			}
		}
	});
}

function saveUserInfo() {
	if ($("#chkRememberMe:checked").length > 0) {
		if ($.cookie("chkRememberMe") != "true"||$("#loginName").val()!=$.cookie("reinforceLoginName")) {
			var loginName = $("#loginName").val();
			var loginPwd = $("#loginPwd").val();
			$.cookie("chkRememberMe", "true", {
				expires : 7
			}); // 存储一个带7天期限的 cookie
			$.cookie("reinforceLoginName", loginName, {
				expires : 7
			}); // 存储一个带7天期限的 cookie
			$.cookie("reinforceLoginPwd", $.md5(loginPwd), {
				expires : 7
			}); // 存储一个带7天期限的 cookie
		}
	} else {
		$.cookie("chkRememberMe", "false", {
			expires : -1
		});
		$.cookie("reinforceLoginName", '', {
			expires : -1
		});
		$.cookie("reinforceLoginPwd", '', {
			expires : -1
		});
	}
}

function keyDown(event) {
	if (event.keyCode == 13) {
		$("#loginsubmit").click();
	}
}
$(function() {
	$(".navbar-main").children("li")
			.each(
					function() {
						if ($(this).children("a").attr("url") == "/"
								&& window.location.pathname == "/")
							$(this).addClass("am-active");
						else if ($(this).children("a").attr("url") != "/"
								&& window.location.pathname != "/") {
							var path = window.location.pathname;
							path = path.substring(1);
							console.log(path.indexOf($(this).children("a")
									.attr("url")));
							if ($(this).children("a").attr("url").indexOf(
									path) != -1)
								$(this).addClass("active");
						}
					});

});

function opensearch(){
	if($("#keyword").val()!="")
		location.href="op/search?keyword="+$("#keyword").val();
}


dialog.alert = function(content){
	var d = dialog({
		title : '提示',
		content : content,
		width : '200px',
		okValue : '确定',
		ok : function(){}
	});
	d.showModal();
}