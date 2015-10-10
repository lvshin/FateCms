<%@ page language="java" import="java.util.*,fate.webapp.blog.model.GlobalSetting" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>帐号注册--${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="帐号注册--${setting.appName}">
<meta name="description" content="帐号注册--${setting.appName}">
<link rel="stylesheet" type="text/css" href="css/Validform/style.css">
<link rel="stylesheet" type="text/css" href="css/Validform.css">
<c:import url="/op/head"></c:import>
</head>

<body>
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		
		<div class="padding20"></div>
		<div class="register center-block">
			<div class="title">
				<span><strong>用户注册</strong></span>
			</div>
			<form class="form form-horizontal" action="" id="registerForm" method="post" autocomplete="off">
				<input type="hidden" name="type" id="type" value="1" />
				<div class="form-group">
    				<label for="nickName" class="col-sm-3 control-label"><span class="neccess">*</span>昵称：</label>
    				<div class="col-sm-5">
      					<input name="nickName" id="nickName" value="" class="form-control" placeholder="昵称，2~16个字符" ajaxurl="op/register/check" datatype="z2-16" errormsg="昵称范围在2~16个字符之间！" sucmsg="该昵称可以使用！" nullmsg="请输入您的昵称！" maxlength="16"/>
    				</div>
    				<div class="col-sm-4">
    					<span class="Validform_checktip help"></span>
    				</div>
  				</div>
  				<div class="form-group">
    				<label for="username" class="col-sm-3 control-label"><span class="neccess">*</span>邮箱/手机号：</label>
    				<div class="col-sm-5">
      					<input name="username" id="username" class="form-control" placeholder="E-mail/手机号" datatype="username" ajaxurl="op/register/check" sucmsg="该邮箱可以使用！" nullmsg="请输入您的用户名！" errormsg="请用正确的邮箱地址/手机号注册！" />
    				</div>
    				<div class="col-sm-4">
    					<span class="Validform_checktip help"></span>
    				</div>
  				</div>
				<div class="form-group">
					<label for="password" class="col-sm-3 control-label">
						<span class="neccess">*</span>密码：
					</label>
					<div class="col-md-5">
						<input type="password" name="password1" id="password" value="" class="form-control" datatype="*6-16" errormsg="密码范围在6~16位之间！" nullmsg="请输入密码！" sucmsg="&nbsp;" maxlength="16"  plugin="passwordStrength" />
					</div>
					<div class="col-md-4">
						<div class="passwordStrength" style="display:none;"><b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span></div>
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="pwdcfm" class="col-sm-3 control-label">
						<span class="neccess">*</span>确认密码：
					</label>
					<div class="col-md-5">
						<input type="password" name="pwdcfm" id="pwdcfm" value="" class="form-control" datatype="*" recheck="password1" errormsg="您两次输入的账号密码不一致！" nullmsg="请再次输入密码！" sucmsg="&nbsp;" maxlength="16"/>
					</div>
					<div class="col-md-4">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<jsp:useBean id="geetestSdk" class="fate.webapp.blog.api.open.GeetestLib" scope="request" />
				<%
					GlobalSetting globalSetting = GlobalSetting.getInstance();
					String captcha_id = globalSetting.getGeetestId();//It's a capthca whihc needs to be register
					if(captcha_id!=null){
						geetestSdk.setCaptchaId(captcha_id);
				%>
				<div class="form-group">
						<label for="" class="col-sm-3 control-label">
							<span class="neccess">*</span>滑动验证码：
						</label>
						<div class="col-md-5">
							<%
								if (geetestSdk.preProcess() != 1) {
							%>
							<h1>Geetest Server is down,Please use your own captcha system
								in your web page</h1>
							<%
								} else {
							%>
							<%=geetestSdk.getGtFrontSource()%>
							<%
								}
							%>
						</div>
					</div>
				<%} %>
				<div class="form-group" id="mcode">
					<label for="code" class="col-sm-3 control-label">
						<span class="neccess">*</span>手机验证码：
					</label>
					<div class="col-md-3">
						<input name="code" id="code" class="form-control" placeholder="6位验证码" datatype="code" sucmsg="&nbsp;" nullmsg="请输入您收到的验证码！" errormsg="验证码为6位数字" maxlength="6"/>
        			</div>
        			<div class="col-md-2">
        				<button class="btn btn-success btn-sm" type="button" id="sendCode">发送验证码</button>
					</div>
					<div class="col-md-4">
						<span class="Validform_checktip help"></span>
					</div>
				</div>
				<div class="form-group btns">
					<div class="col-sm-offset-2 col-sm-8">
						<button type="submit" class="btn btn-info" id="registerSubmit" >立即注册</button>
						<button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
					</div>
				</div>
				<div class="form-group btns">
					<div class="col-sm-12">
						<hr>
					</div>
					<div class="col-sm-offset-3 col-sm-9">
						<div class="help-block">注册太麻烦？那就试试第三方登录吧</div>
						<button class="qq_login_btn" onclick="location.href='op/login/toQQ'"></button>
						<button class="wb_connect_btn" onclick="location.href='op/login/toWeibo'"></button>
					</div>
				</div>
			</form>
				
		</div>

	</div>
			<c:import url="/op/footer"></c:import>
</body>

		<script type="text/javascript" src="js/Validform/passwordStrength-min.js"></script>
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
								mobile : $("#username").val(),
								geetest_challenge : $(".geetest_challenge").val(),
								geetest_validate : $(".geetest_validate").val(),
								geetest_seccode : $(".geetest_seccode").val()
							},
							success : function(data) {
								if (data.success) {
									var d2 = dialog({
										content : data.msg
									});
									d2.show();
									setTimeout(function() {
										d2.close().remove();
									}, 2000);
								}else{
									dialog.alert(data.msg);
								}

							},
							error : function(data) {
								dialog.alert("请求发送失败，请稍后再试");
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
							postonce : false,
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
					content : data.msg
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