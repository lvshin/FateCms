<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>分区设置--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td {
	font-weight: bold;
}

.del {
	height: 22px !important;
}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main">
			<c:import url="navi.jsp">
				<c:param name="index" value="2" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="admin/globalSetting/siteInfo">站点设置</a> > 站点SEO
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<div class="page-header">
						<h3>
							站点SEO设置 <small></small>
						</h3>
					</div>
					<form id="seoForm" >
						<div class="row">
							<div class="form-group col-md-4 col-xs-6">
								<label for="title">title</label> 
								<input type="text" class="form-control" id="title" placeholder="" name="title" value="${title}">
							</div>
							<div class="col-md-8 col-xs-6 help-block">
								显示在浏览器标题处的title
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-4 col-xs-6">
								<label for="keywords">keywords</label> 
								<input type="text" class="form-control" id="keywords" placeholder="" name="keywords" value="${keywords}">
							</div>
							<div class="col-md-8 col-xs-6 help-block">
								keywords用于搜索引擎优化，放在 meta 的 keyword 标签中，多个关键字间请用半角逗号 "," 隔开
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-4 col-xs-6">
								<label for="description">description</label> 
								<textarea class="form-control" id="description" name="description" rows="7">${description}</textarea>
							</div>
							<div class="col-md-8 col-xs-6 help-block">
								description用于搜索引擎优化，放在 meta 的 description 标签中
							</div>
						</div>
						
						<button type="button" class="btn btn-success" onclick="seoSubmit()">提交</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/admin/siteSet.js"></script>
</body>
</html>
