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

<title>百度一键分享--${setting.siteName}</title>
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
				<c:param name="index" value="9" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">全局设置</a> > 百度一键分享
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<div class="page-header">
						<h1>
							百度一键分享 <small></small>
						</h1>
					</div>
					<form id="baiduShareForm" onsubmit="return false;">
						<!-- 
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label>是否开启QQ登录</label><br>
									<label><input type="radio" name="on" value="false" ${!setting.qq?"checked":""}>&nbsp;&nbsp;否</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" name="on" value="true" ${setting.qq?"checked":""}>&nbsp;&nbsp;是</label>
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">是否开启QQ登录功能</label>
							</div>
						</div>
						 -->
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="duoshuoKey">新浪AppKey</label> <p class="form-control-static">${weibo==null?"无":weibo}</p>
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">新浪的AppKey，同登录用的key</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="duoshuoSecret">腾讯微博AppKey</label> <input type="text" class="form-control" id="txAppKey" name="txAppKey" value="${setting.txAppKey}" >
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">腾讯微博的AppKey</label>
							</div>
						</div>
						<button class="btn btn-default" onclick="baiduShareSubmit()">提交</button>
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
