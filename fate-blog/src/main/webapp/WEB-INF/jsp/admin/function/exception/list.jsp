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

<title>消息列表--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="8" />
			</c:import>
			<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;"  rel="nofollow">管理中心</a> ><a href="javascript:;">全局设置</a> > 异常记录
					</h5>
				</div>
				<div class="site_right">
				</div>
			</div>
			<div class="page">
			<table class="table table-hover">
				<thead>
					<tr>
						<td>被访页面</td>
						<td>状态码</td>
						<td>来源</td>
						<td>Agent</td>
						<td>访问时间</td>
						<td align="right">操作&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${exceptions}" var="e" varStatus="i">
						<tr>
							<td title="${e.url}"><div class="widthLimit">${e.url}</div></td>
							<td>${e.status}</td>
							<td>${e.source}</td>
							<td title="${e.agent}"><div class="widthLimit">${e.agent}</div></td>
							<td title='<fmt:formatDate value="${e.visitTime}" pattern="yyyy年MM月dd日  HH:mm:ss"/>'><fmt:formatDate value="${e.visitTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
							<td align="right">
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
