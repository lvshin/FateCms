<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>广告列表--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td{font-weight: bold;}
.del{height:22px!important;}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="2" />
	</c:import>
	<div class="main row">
		<c:import url="navi.jsp">
			<c:param name="index" value="1" />
		</c:import>
		<div class="right col-md-10">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;"  rel="nofollow">广告管理</a> > 广告列表
					</h5>
				</div>
				<div class="site_right">
					<button class="btn btn-primary" onclick="location.href='admin/advertisement/new'">
						<span class="glyphicon glyphicon-plus"> </span> 添加新广告
					</button>
				</div>
			</div>
			
			<table class="table table-hover">
				<thead>
					<tr>
						<td>序号</td>
						<td>标题</td>
						<td>类型</td>
						<td>状态</td>
						<td align="right">操作&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="l" varStatus="i">
						<tr>
						<td>${i.index}</td>
						<td><a href="admin/advertisement/new?id=${l.id}">${l.name}</a></td>
						<td>${advNames[l.type-1]}</td>
						<td>${l.active?"有效":"无效"}</td>
						<td align="right">
							<div class="btn-group btn-group-xs" role="group">
								<button class="btn btn-danger del">删除</button>
							</div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page">
				<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
			</div>
			<div class="clear"></div>
		</div>
		</div>
		<c:import url="/op/base/footer"></c:import>
<script type="text/javascript" src="js/select.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
