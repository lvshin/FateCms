<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>邮箱绑定</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="${userSession.user.nickName}的个人资料">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description"
	content="${userSession.user.nickName}的个人资料,${setting.appName}">
<link rel="stylesheet" type="text/css"
	href="css/Validform/datePicker-min.css">
<link rel="stylesheet" type="text/css" href="css/Validform/style.css">
<link rel="stylesheet" type="text/css" href="css/Validform.css">
<c:import url="/op/head"></c:import>
<style>
.security {
	width: 70%;
	min-height: 200px;
	margin: 20px auto;
}

.row {
	margin-bottom: 15px;
}

.row #email {
	width: 60%;
	display: inline-block;
}

.row #sendEmail {
	margin-top: -5px;
}

.btns {
	width: 250px;
	margin: 0 auto;
}
</style>
</head>

<body>
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		<div class="profile">
			<div class="row">
				<c:import url="navi.jsp">
					<c:param name="index" value="3" />
				</c:import>
				<div class=" col-md-10 col-xs-12">
					<div class="title">
						<span><strong>邮箱绑定</strong></span>
					</div>
					<div class="security">
						<form id="emailForm">
							<div class="row">
								<div class="col-md-2">
									<span><span class="neccess">*</span>邮箱：</span>
								</div>
								<div class="col-md-6">
									<input name="email" id="email" class="form-control"
										placeholder="邮箱" datatype="e" ajaxurl="op/register/check"
										sucmsg="该邮箱可以使用！" nullmsg="请输入您的邮箱！" errormsg="请填写正确的邮箱！"
										value="${email}" />
									<button class="btn btn-success" type="submit" id="sendEmail">发送验证码</button>
								</div>
								<div class="col-md-4">
									<span class="Validform_checktip help"></span>
								</div>
							</div>
					</div>
				</div>

			</div>
		</div>
		<c:import url="/op/footer"></c:import>
	</div>
	<script type="text/javascript" src="js/Validform/Validform_v5.3.2.js"></script>
	<script type="text/javascript">
		/*验证码倒计时*/

		function countDown() {
			var $send = $("#sendEmail");
			var i = 120;
			$send.addClass("gray").css({
				cursor : "default"
			}).attr("disabled", "disabled");
			var interval = setInterval(function() {
				$send.html("剩余" + i + "秒");
				i--;
				if (i < 0) {
					$send.removeClass("gray").css({
						cursor : "pointer"
					}).removeAttr("disabled");
					$send.html("重新获取");
					clearInterval(interval);
				}
			}, 1000);
		}
		$(function() {

			var form = $("#emailForm")
					.Validform(
							{
								btnReset : "",
								tiptype : function(msg, o, cssctl) {
									switch (o.type) {
									case 1:
										$(o.obj).parent().next().children(
												".Validform_checktip")
												.removeClass("Validform_right")
												.removeClass("Validform_wrong")
												.html(msg);
										$(o.obj).parent().parent().removeClass(
												"has-error").removeClass(
												"has-success");
										break;
									case 2:
										$(o.obj).parent().next().children(
												".Validform_checktip")
												.addClass("Validform_right")
												.html(msg);
										$(o.obj).parent().parent().removeClass(
												"has-error").addClass(
												"has-success");
										break;
									case 3:
										$(o.obj).parent().next().children(
												".Validform_checktip")
												.addClass("Validform_wrong")
												.html(msg);
										$(o.obj).parent().parent().removeClass(
												"has-success").addClass(
												"has-error");
										break;

									default:
										break;
									}

								},
								ignoreHidden : true,
								dragonfly : false,
								tipSweep : false,
								label : ".label",
								showAllError : true,
								postonce : true,
								datatype : {
									"code" : /^\d{6}$/
								},
								usePlugin : {},
								beforeCheck : function(curform) {
									//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
									//这里明确return false的话将不会继续执行验证操作;	
								},
								beforeSubmit : function(curform) {
									//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
									//这里明确return false的话表单将不会提交;	
								},
								callback : function(data) {
									//返回数据data是json对象，{"info":"demo info","status":"y"}
									//info: 输出提示信息;
									//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
									//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
									//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；

									//这里执行回调操作;
									//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
									email();
									return false;
								}
							});

		});
		function email() {
			var options = {
				url : "profile/sendEmail",
				type : 'post',
				dataType : 'json',
				success : function(data) {

					if (data.success) {
						dialog.alert("邮件已发送");
					} else {
						var d = dialog({
							content : data.message
						});
						d.showModal();
						setTimeout(function() {
							d.close().remove();
						}, 1500);
					}
				},
				error : function() {
					dialog.alert("通信异常");
				}
			};
			if ($("#email").val() != "" && $("#code").val() != "") {
				countDown();
				$("#emailForm").ajaxSubmit(options);
			}

			else
				return false;
		}
	</script>
</body>
</html>
