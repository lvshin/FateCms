<%@ page language="java"  import="java.util.*,fate.webapp.blog.model.GlobalSetting" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>手机绑定</title>
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
	margin: 50px auto;
}

.row {
	margin-bottom: 15px;
}

</style>
</head>

<body>
	<!-- 头部导航条 -->
	<c:import url="/op/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="padding20"></div>
	<div class="container">
		<div class="profile row">
				<c:import url="navi.jsp">
					<c:param name="index" value="3" />
				</c:import>
				<div class=" col-md-10 col-xs-12">
					<div class="title">
						<span><strong>手机绑定</strong></span>
					</div>
					<div class="security">
						<form id="mobileForm" class=" form-horizontal">
							<div class="form-group">
								<label for="username" class="col-sm-3 control-label"><span
									class="neccess">*</span>手机号：</label>
								<div class="col-sm-5">
									<input name="mobile" id="mobile" class="form-control"
										placeholder="手机号" datatype="m" ajaxurl="op/register/check"
										sucmsg="该手机号可以使用！" nullmsg="请输入您的手机号！" errormsg="请填写正确的手机号！"
										maxlength="11" value="${mobile}" />
								</div>
								<div class="col-sm-4">
									<span class="Validform_checktip help"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-3 control-label"> <span
									class="neccess">*</span>滑动验证码：
								</label>
								<div class="col-md-5">
									<jsp:useBean id="geetestSdk"
										class="fate.webapp.blog.api.open.GeetestLib"
										scope="request" />
									<%
										GlobalSetting globalSetting = GlobalSetting.getInstance();
										String captcha_id = globalSetting.getGeetestId();//It's a capthca whihc needs to be register
										geetestSdk.setCaptchaId(captcha_id);
									%>
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
							<div class="form-group" id="mcode">
								<label for="code" class="col-sm-3 control-label">
									<span class="neccess">*</span>手机验证码：
								</label>
								<div class="col-md-4">
									<input name="code" id="code" class="form-control" placeholder="6位验证码" datatype="code" sucmsg="&nbsp;" nullmsg="请输入您收到的验证码！" errormsg="验证码为6位数字" maxlength="6" />
        						</div>
        						<div class="col-md-5">
        							<button class="btn btn-success btn-sm" type="button" id="sendCode">发送验证码</button>
								</div>
							</div>
							<div class="form-group btns">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-info" id="mobileSubmit">提交</button>
									<button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<script type="text/javascript"
		src="js/Validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript" src="js/base/sms.min.js"></script>
</body>
</html>
