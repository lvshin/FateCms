<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>蜘蛛记录 - ${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<c:import url="/op/base/admin/head"></c:import>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="7" />
			</c:import>
			<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> > 蜘蛛记录
					</h5>
				</div>
			</div>
			<div class="page">
			<table class="table table-hover">
				<thead>
					<tr>
						<td>抓取地址</td>
						<td>蜘蛛名称</td>
						<td>抓取时间</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${lists}" var="list">
						<tr>
							<td>${list.spiderUrl}</td>
							<td>${list.spiderName}</td>
							<td>${list.spiderTime}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page-num">
				<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
			</div>
			</div>
			<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
</body>
</html>
