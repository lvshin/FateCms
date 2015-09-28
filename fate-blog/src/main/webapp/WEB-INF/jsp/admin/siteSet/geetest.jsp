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

<title>${setting.siteName}-站点信息</title>
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
			<c:param name="index" value="6" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">全局设置</a> > 极验验证
					</h5>
				</div>
				<div class="site_right"></div>
			</div>
			<div class="page">
				<div class="page-header">
					<h1>
						极验验证 <small></small>
					</h1>
				</div>
				<form id="geetestForm" onsubmit="return false;">
					<div class="geetest">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="geetestId">ID</label> <input type="text" class="form-control" id="geetestId" name="geetestId" value="${setting.geetestId}" >
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">极验验证的ID</label>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="geetestKey">KEY</label> <input type="text" class="form-control" id="geetestKey" name="geetestKey" value="${setting.geetestKey}" >
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">极验验证的Key</label>
						</div>
					</div>
					</div>
					<button class="btn btn-default" onclick="geetestSubmit()">提交</button>
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
