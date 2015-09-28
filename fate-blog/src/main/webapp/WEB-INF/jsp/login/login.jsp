<%@ page language="java" import="java.util.*,fate.webapp.blog.model.GlobalSetting" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>用户登录--${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
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
	<div class="padding20"></div>
	<div class="container">
		
		<div class="login2 center-block">
			<div class="title">
				<span><strong>用户登录</strong></span>
			</div>
				<div class="form form-horizontal">
					<div class="form-group">
    					<label for="loginName" class="col-sm-3 col-sm-offset-1 control-label">邮箱/手机号：</label>
    					<div class="col-sm-6">
      						<input type="text" class="form-control" id="loginName" placeholder="E-mail/手机号" name="loginName">
    					</div>
    					<div class="col-sm-2"></div>
  					</div>
  					
  					<div class="form-group">
    					<label for="loginPwd" class="col-sm-3 col-sm-offset-1 control-label">密码：</label>
    					<div class="col-sm-6">
      						<input type="password" class="form-control" id="loginPwd" placeholder="密码" name="loginPwd" maxlength="16">
    					</div>
    					<div class="col-sm-2"></div>
  					</div>
					<jsp:useBean id="geetestSdk"
							class="fate.webapp.blog.api.open.GeetestLib" scope="request" />
					<%
							//TODO： replace your own ID here  after create a Captcha App in 'my.geetest.com'
							GlobalSetting globalSetting = GlobalSetting.getInstance();
							String captcha_id = globalSetting.getGeetestId();//It's a capthca whihc needs to be register
							if(captcha_id!=null){
							geetestSdk.setCaptchaId(captcha_id);
							//geetestSdk.setIsHttps(true);
							//geetestSdk.setProductType("popup");
							//geetestSdk.setSubmitBtnId("submit-button");
							//geetestSdk.setIsHttps(true);
					%>
					<div class="form-group">
    					<label for="" class="col-sm-3 col-sm-offset-1 control-label">验证码：</label>
    					<div class="col-sm-8">
      						<%
								if (geetestSdk.preProcess() != 1) {
							%>
							<h3>验证码加载失败，请刷新网页</h3>
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
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-8">
						<div class="checkbox">
							
							<label> <input id="chkRememberMe" type="checkbox" > 记住密码
							</label>&nbsp;&nbsp;&nbsp;
							<!-- 
							<a href="javascript:;">忘记密码？</a>&nbsp;&nbsp;&nbsp;
							 -->
							<c:if test="${setting.regAllow}">
								<a href="op/register/goRegister"><strong>立即注册</strong></a>
							</c:if>
							<c:if test="${!setting.regAllow}">
								<a href="javascript:dialog.alert('注册未开放');"><strong>立即注册</strong></a>
							</c:if>
						</div>
						
					</div>
				</div>
				<div class="form-group btns">
					<div class="col-sm-offset-4 col-sm-8">
						<button type="button" class="btn btn-info" data-loading-text="登录中..." autocomplete="off" id="loginsubmit">登录</button>
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
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<script type="text/javascript" src="js/Validform/Validform_v5.3.2.js"></script>
		<script type="text/javascript"
			src="js/Validform/passwordStrength-min.js"></script>
</body>
<script type="text/javascript">
	
</script>
</html>