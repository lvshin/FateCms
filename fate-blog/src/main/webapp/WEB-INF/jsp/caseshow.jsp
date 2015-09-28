<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>案例展示--${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="案例展示--${setting.appName}" />
<meta name="description" content="案例展示--${setting.appName}" />
<c:import url="/op/head"></c:import>
</head>
<body>

	<!-- 头部导航条 -->
	<c:import url="/op/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;首页</a></li>
			<li class="active">案例展示</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="page-header">
					<h1>
						作品展示 
					</h1>
				</div>
				<div class="row">
					<div class="col-md-2 col-xs-6">
						<a href="http://www.fanhan.com.cn/" target="_blank"><img src="images/cases/fanhan.png" width="150" alt="泛涵"/></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
</body>
</html>
