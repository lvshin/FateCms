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
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<c:import url="/op/base/admin/head"></c:import>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="1" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">站点设置</a> > 站点信息
					</h5>
				</div>
				<div class="site_right"></div>
			</div>
			<div class="page">
				<div class="page-header">
					<h1>
						站点信息 <small></small>
					</h1>
				</div>
				<form id="infoForm" onsubmit="return false;">
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="siteName">站点名称</label>
								<input type="text" class="form-control" id="siteName" placeholder="站点名称" name="siteName" value="${setting.siteName}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">站点名称，将显示在浏览器窗口标题等位置</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="appName">网站名称</label> <input type="text" class="form-control" id="appName" placeholder="网站名称" name="appName" value="${setting.appName}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">网站名称，将显示在页面底部的联系方式处</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="appEnName">网站英文名称</label> <input type="text" class="form-control" id="appEnName" placeholder="网站名称" name="appEnName" value="${setting.appEnName}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">网站英文名称，作为副标题显示</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="appUrl">网站URL</label> <input type="text" class="form-control" id="appUrl" placeholder="网站URL" name="appUrl" value="${setting.appUrl}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">网站 URL，将作为链接显示在页面底部.非此URL的域名指向本网站的话，会做301永久重定向。</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="adminEmail">管理员邮箱</label> <input type="text" class="form-control" id="adminEmail" placeholder="管理员邮箱" name="adminEmail" value="${setting.adminEmail}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">管理员 E-mail，用于接收系统信息</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="icp">网站备案信息代码</label> <input type="text" class="form-control" id="icp" placeholder="网站备案信息代码" name="icp" value="${setting.icp}" >
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">页面底部可以显示 ICP 备案信息.</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="statisticsHead">网站第三方统计代码(head)</label>
								<textarea type="text" class="form-control" id="statisticsHead" placeholder="网站第三方统计代码" rows="10" name="statisticsHead">${setting.statisticsHead}</textarea>
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">适用于放在head里的第三方统计</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label for="statistics">网站第三方统计代码（body）</label>
								<textarea type="text" class="form-control" id="statistics" placeholder="网站第三方统计代码" rows="10" name="statistics">${setting.statistics}</textarea>
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">页面底部可以显示第三方统计</label>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<label>redis缓存设置</label> <br>
								<input type="radio" id="redis1" name="redisOpen" ${!setting.redisOpen?"checked":""} value="false" /><label for="redis1">否</label>
								<input type="radio" id="redis2" name="redisOpen" ${setting.redisOpen?"checked":""} value="true" /><label for="redis2">是</label>
							</div>
						</div>
						<div class="col-lg-6">
							<label class="help-block">是否开启redis缓存，请确保redis配置完成后再开启此项</label>
						</div>
					</div>
					<button class="btn btn-default" onclick="infoSubmit()">提交</button>
				</form>
				<label class="help-block">注：站点名称请勿经常修改，否则将影响搜索引擎排名</label>
			</div>
			<div class="clear"></div>
			
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/admin/siteSet.js"></script>
</body>
</html>
