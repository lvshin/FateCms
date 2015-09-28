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

<title>版块管理--${setting.siteName}</title>
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
				<c:param name="index" value="3" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a
								href="admin/forum/forumList">版块管理</a> > 版块管理
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<table class="table table-hover">
						<thead>
							<tr>
								<td style="min-width:80px;">显示顺序</td>
								<td style="width:70%;">导航名称</td>
								<td style="min-width:80px;">地址</td>
								<td style="min-width:150px;" align="reinforce-content">操作&nbsp;</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<c:import url="/op/base/loading?size=10"></c:import>
					<br>
					<a href="javascript:newNavi();" class="add">
						<span class="glyphicon glyphicon-plus"></span>
						&nbsp;添加新导航
					</a>
					<div class="clear"></div>
					<br>
					<button class="btn btn-success" onclick="naviSubmit()">提交</button>
					<div class="help-block">注：导航名称中支持Bootstrap和Font Awesome中的图标</div>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/select.js"></script>
	<script type="text/javascript" src="js/admin/siteSet.js"></script>
</body>
</html>
