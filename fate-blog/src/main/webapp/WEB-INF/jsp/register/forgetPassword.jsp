<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>帐号注册--${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="css/Validform/style.css">
<link rel="stylesheet" type="text/css" href="css/Validform.css">
<c:import url="/op/head"></c:import>
</head>

<body>
	<div class="container">
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<script type="text/javascript" src="js/Validform/Validform_v5.3.2.js"></script>
		<script type="text/javascript" src="js/Validform/passwordStrength-min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.md5.js"></script>
		<div class="register">
			<div class="title">
				<span><strong>忘记密码</strong></span>
			</div>
			<div class="form">
				<form action="" id="registerForm" method="post">
				<input type="hidden" name="type" id="type" value="1" />
				<div class="row">
					<div class="col-md-2">
						<span><span class="neccess">*</span>验证方式：</span> 
					</div>
					<div class="col-md-5">
						<select name="type" id="type" value="" class="form-control" >
							<option value="0">邮箱</option>
							<option value="1">手机</option>
						</select>
					</div>
					<div class="col-md-5">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<span><span class="neccess">*</span>邮箱/手机号：</span> 
					</div>
					<div class="col-md-5">
						<input name="username" id="username" class="form-control" placeholder="E-mail/手机号" datatype="username" ajaxurl="op/register/check" sucmsg="该邮箱可以使用！" nullmsg="请输入您的用户名！" errormsg="请用正确的邮箱地址/手机号注册！" />
					</div>
					<div class="col-md-5">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="row" id="mcode">
					<div class="col-md-2">
						<span><span class="neccess">*</span>验证码：</span> 
					</div>
					<div class="col-md-5">
						<input name="code" id="code" class="form-control" placeholder="6位验证码" datatype="code" sucmsg="&nbsp;" nullmsg="请输入您收到的验证码！" errormsg="验证码为6位数字" maxlength="6"/>
        				<button class="btn btn-success" type="button" id="sendCode">发送验证码</button>
					</div>
					<div class="col-md-5">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<span><span class="neccess">*</span>密码：</span> 
					</div>
					<div class="col-md-5">
						<input type="password" name="password1" id="password" value="" class="form-control" datatype="*6-16" errormsg="密码范围在6~16位之间！" nullmsg="请输入密码！" sucmsg="&nbsp;" maxlength="16"  plugin="passwordStrength" />
					</div>
					<div class="col-md-5">
						<div class="passwordStrength" style="display:none;"><b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span></div>
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<span><span class="neccess">*</span>确认密码：</span> 
					</div>
					<div class="col-md-5">
						<input type="password" name="pwdcfm" id="pwdcfm" value="" class="form-control" datatype="*" recheck="password1" errormsg="您两次输入的账号密码不一致！" nullmsg="请再次输入密码！" sucmsg="&nbsp;" maxlength="16"/>
					</div>
					<div class="col-md-5">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="btns">
					<button type="submit" class="btn btn-info" id="registerSubmit" >立即注册</button>
					<button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
				</div>
				</form>
				
			</div>
		</div>
		<c:import url="/op/base/footer"></c:import>
	</div>
</body>
<script type="text/javascript">

/*验证码倒计时*/
			
	function countDown() {
		var $send = $("#sendCode");
		var i = 120;
		$("#sendCode").addClass("gray").css({
			cursor : "default"
		}).attr("disabled", "disabled");
		var interval = setInterval(function() {
			$send.html("剩余" + i + "秒");
			i--;
			if (i < 0) {
				$("#sendCode").removeClass("gray").css({
					cursor : "pointer"
				}).removeAttr("disabled");
				$send.html("重新获取");
				clearInterval(interval);
			}
		}, 1000);
	}

	$(function() {

		$("#sendCode").click(
				function() {
					countDown();
					if ($("#username").parent().next().children(
							".Validform_wrong").length == 0) {
						$.ajax({
							url : "op/sendVerificationCode",
							type : 'post',
							dataType : 'json',
							data : {
								mobile : $("#username").val()
							},
							success : function(data) {
								if (data.code != 0) {
									var d2 = dialog({
										content : data.msg
									});
									d2.show();
									setTimeout(function() {
										d2.close().remove();
									}, 2000);
								}

							},
							error : function(data) {
								alert('缃戠粶杩炴帴寮傚父');
							}
						});
					} else
						dialog.alert($("#username").parent().next().children(
								".Validform_wrong").html());
				});

		var form = $("#registerForm")
				.Validform(
						{
							btnReset : "",
							tiptype : function(msg, o, cssctl) {
								switch (o.type) {
								case 1:
									$(o.obj).parent().next().children(
											".Validform_checktip").removeClass(
											"Validform_right").removeClass(
											"Validform_wrong").html(msg);
									$(o.obj).parent().parent().removeClass(
											"has-error").removeClass(
											"has-success");
									break;
								case 2:
									$(o.obj).parent().next().children(
											".Validform_checktip").addClass(
											"Validform_right").html(msg);
									$(o.obj).parent().parent().removeClass(
											"has-error")
											.addClass("has-success");
									break;
								case 3:
									$(o.obj).parent().next().children(
											".Validform_checktip").addClass(
											"Validform_wrong").html(msg);
									$(o.obj).parent().parent().removeClass(
											"has-success")
											.addClass("has-error");
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
								"*6-20" : /^[^\s]{6,20}$/,
								"z2-16" : /^[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AF\w]{2,16}$/,
								"username" : function(gets, obj, curform, regxp) {
									//参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
									var mobile = /^1[3|4|5|8][0-9]\d{8}$/;
									var email = /^(\w)+[(\.\w+)-]*@(\w)+((\.\w{2,3}){1,3})$/;

									if (gets == null || gets == '')
										return "请输入您的用户名！";
									if (mobile.test(gets)) {
										$("#mcode").show();
										$("#type").val("2");
										return true;
									} else {
										$("#mcode").hide();
										if (email.test(gets)) {
											$("#type").val("1");
											return true;
										}
									}
									return false;

									//注意return可以返回true 或 false 或 字符串文字，true表示验证通过，返回字符串表示验证失败，字符串作为错误提示显示，返回false则用errmsg或默认的错误提示;
								},
								"code" : /^\d{6}$/
							},
							usePlugin : {
								passwordstrength : {
									minLen : 6,//设置密码长度最小值，默认为0;
									maxLen : 16,//设置密码长度最大值，默认为30;
									trigger : function(obj, error) {
										//该表单元素的keyup和blur事件会触发该函数的执行;
										//obj:当前表单元素jquery对象;
										//error:所设密码是否符合验证要求，验证不能通过error为true，验证通过则为false;

										//console.log(error);
										if (error) {
											obj.parent().next().find(
													".Validform_checktip")
													.show();
											obj.parent().next().find(
													".passwordStrength").hide();
										} else {
											obj.parent().next().find(
													".Validform_checktip")
													.hide();
											obj.parent().next().find(
													".passwordStrength").show();
										}
									}

								}
							},
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
								register();
								return false;
							}
						});
	});
	function register() {
		var options = {
			url : "op/register/submit",
			type : 'post',
			dataType : 'json',
			data : {
				password : $.md5($("#password").val())
			},
			success : function(data) {
				var d = dialog({
					content : data.message
				});
				d.showModal();
				if (data.success) {
					setTimeout(function() {
						location.href = data.url;
					}, 1500);
				} else {
					setTimeout(function() {
						d.close().remove();
					}, 1500);
				}
			},
			error : function() {
				dialog.alert("通信异常");
			}
		};
		if ($("#nickName").val() != "" && $("#username").val() != ""
				&& $("#password").val() != "" && $("#pwdcfm").val() != "")
			$("#registerForm").ajaxSubmit(options);
		else
			return false;
	}
</script>
</html>