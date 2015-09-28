<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>${setting.siteName}-邮件设置</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<c:import url="/op/base/admin/head"></c:import>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main">
			<c:import url="navi.jsp">
				<c:param name="index" value="4" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">站点设置</a> > 邮件设置
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<div class="page-header">
						<h1>
							邮件设置 <small></small>
						</h1>
					</div>
					<form id="mailForm" onsubmit="return false;">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="host">SMTP 服务器</label> <input type="text" class="form-control" id="host" name="host" value="${host}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">SMTP 服务器</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="from">发信人邮件地址</label> <input type="text" class="form-control" id="from" name="from" value="${from}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">发信人邮件地址</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="username">SMTP 身份验证用户名</label> <input type="text" class="form-control" id="username" name="username" value="${username}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">SMTP 身份验证用户名</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="password">SMTP 身份验证密码</label> <input type="password" class="form-control" id="password" name="password" value="${password}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">SMTP 身份验证密码</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="timeout">超时时间</label> <input type="text" class="form-control" id="timeout" name="timeout" value="${timeout}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">超时时间</label>
							</div>
						</div>
						<button class="btn btn-warning" onclick="sendTestEmail()">发送测试邮件</button>
						<button class="btn btn-default" onclick="mailSubmit()">提交</button>
						<label class="help-block">注：需要先提交才能发送测试邮件</label>
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/admin/siteSet.js"></script>
</body>
</html>
