<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>第三方帐号注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<c:import url="/op/head"></c:import>
<link rel="stylesheet" type="text/css" href="css/login.css" />

</head>

<body>
	<div class="login_logo">
		<div class="logo_left"></div>
		<div class="logo_right"></div>

	</div>
	<div class="login">
		<div class="login_left"></div>
		<div class="login_right">
			<div class="form">
				<div class="item">
					<span>为了您的帐号安全，请先做短信验证</span>
				</div>
				<div class="item" style="margin-top: 20px;">
					<span>手机号</span>
					<div class="item-info">
						<input name="mobile" id="mobile" value="${mobile }"
							style="width:100px;" class="loginInput" disabled/> <input type="button"
							class="btn-img btn-entry" id="sendSms" value="发送验证短信"
							tabindex="8" onclick="sendSms()"
							style="width:100px;height:28px;font-size: 14px;margin-left: 5px;" />
					</div>
				</div>
				<div class="item" style="margin-top: 20px;">
					<span>验证码</span>
					<div class="item-info">
						<input name="code" id="code" class="loginInput"
							style="width:100px;" maxlength="6" />
					</div>
				</div>
				<div class="item login-btn">
					<span>&nbsp;</span> <input type="button" class="btn-img btn-entry"
						id="smsSubmit" value="立即验证" tabindex="8" onclick="verifySms()" />
				</div>
			</div>
			<div class="decoration_reinforce"></div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<script type="text/javascript">
	function sendSms() {
		var i = 60;
		if($("#mobile").val()!=""){
		$("#sendSms").addClass("disable");
		$("#sendSms").attr("disabled", true);
		$.ajax({
			type : "post",
			url : "op/security/verification/sendSms",
			dataType : 'json',
			data : {
				mobile : "${mobile}",
				userGuid : "${userGuid}"
			},
			error : function() {
				var d = dialog({
					content : "发送系统内部错误"
				});
				d.show();
				setTimeout(function() {
					d.close();
				}, 1000);
			},
			success : function(data) {
				if (data.res.code == 0) {
					var d = dialog({
						content : "短信发送成功"
					});
					d.show();
					setTimeout(function() {
						d.close();
					}, 1000);
				} else {
					var d = dialog({
						content : "短信发送失败，请稍后再试"
					});
					d.show();
					setTimeout(function() {
						d.close();
					}, 1000);
				}

			}
		});
		var timer = setInterval(function() {
			$("#sendSms").val("重新发送(" + i + ")");
			i--;
			if (i == -1) {
				clearInterval(timer);
				$("#sendSms").val("重新发送");
				$("#sendSms").removeClass("disable");
		        $("#sendSms").attr("disabled", false);
			}
		}, 1000);
        }
	}

	function verifySms() {
		$.ajax({
			type : "post",
			url : "op/security/verification/verifySms",
			dataType : 'json',
			data : {
				code : $("#code").val(),
				userGuid : "${userGuid}"
			},
			error : function() {
				alert("通信错误");
			},
			success : function(data) {
				if (data.success) {
					var d = dialog({
						content : "短信验证成功"
					});
					d.show();
					setTimeout(function() {
						d.close();
						window.location.href = "op/login/callback";
					}, 1000);
				} else {
					if (data.error_code == 1) {
						var d = dialog({
							content : "短信验证码超时"
						});
						d.show();
						setTimeout(function() {
							d.close();
						}, 1000);
					} else if (data.error_code == 2) {
						var d = dialog({
							content : "短信验证码错误"
						});
						d.show();
						setTimeout(function() {
							d.close();
						}, 1000);
					} else if (data.error_code == 3) {
						var d = dialog({
							content : "请先发送验证短信"
						});
						d.show();
						setTimeout(function() {
							d.close();
						}, 1000);
					}
				}
			}
		});

	}
</script>
</body>
</html>