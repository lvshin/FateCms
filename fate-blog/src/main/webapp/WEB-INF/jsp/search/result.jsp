<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>搜索--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="搜索:${keyword}" />
<meta name="description" content="搜索:${keyword},搜索用时：${searchTime}秒，共搜索到<em>${count}</em>个结果" />
<c:import url="/op/head"></c:import>
</head>
<body>
	<div class="container">
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="2" />
		</c:import>
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span></a></li>
			<li><a href="/">首页</a></li>
			<li class="active">搜索</li>
		</ol>
		
		<div class="main row">
			<div class=" col-md-8">
				<div class="panel panel-default">
					<div class="panel-body result">
						<div class="help-block">搜索用时：${searchTime}秒，共搜索到<em>${count}</em>个结果</div>
						<div class=" row">
						<c:forEach items="${themes}" var="theme">
							<div class="col-xs-12">
								<div class="page-header">
									<h4>
										<a href="${theme.url}" target="_blank">${theme.title}</a><br> <small>
											<span class="glyphicon glyphicon-user"></span>${theme.author}
											<span class="glyphicon glyphicon-calendar"></span>
										<fmt:formatDate value="${theme.publishDate}" type="date"
												pattern="yyyy年MM月dd日" /> <span
											class="glyphicon glyphicon-comment"></span>${theme.replies} <span
											class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览
											<span class="glyphicon glyphicon-tags"></span>&nbsp;${theme.tags}
										</small>
									</h4>
								</div>
								<dvi class="content">
									${theme.content}
								</dvi>
							</div>
						</c:forEach>
						</div>
					</div>
				</div>
				<div class="page pull-right">
					<c:import
						url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
				</div>
			</div>
			<div class="col-md-4">
				<c:import url="/op/rightNavi?fid=1"></c:import>
			</div>

		</div>

		<c:import url="/op/footer"></c:import>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#keyword").val("${keyword}");
		});
	</script>
</body>
</html>
