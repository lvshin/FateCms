<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>${title==null||title==""?setting.siteName:title}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="${keywords}" />
<meta name="description" content="${description}" />
<c:import url="/op/head"></c:import>
</head>
<body>

	<!-- 头部导航条 -->
	<c:import url="/op/header">
		<c:param name="header_index" value="2" />
	</c:import>
	<div class="padding20"></div>
	<div class="container">
		<div class="main row">
			<div class=" col-md-8">
				<div class="panel panel-default">
					<div class="panel-body">
						<h3 class="page-header panel-archive-title">最新主题</h3>
						<c:forEach items="${themes}" var="theme" varStatus="i">
							<div class="archive animated fadeIn duration1" style="animation-delay:${i.count*0.1}s;-webkit-animation-delay:${i.count*0.1}s;">
							<div class="header page-header">
								<h4 class="h4">
									<a href="${theme.url}">【${theme.forum.forumName}】${theme.title}</a>
									<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
										<span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
											value="${theme.publishDate}" type="date"
											pattern="yyyy年MM月dd日" /> <span
										class="glyphicon glyphicon-comment"></span>${theme.replies} <span
										class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览
										<span class="glyphicon glyphicon-tags"></span>${theme.tags}

									</small>
								</h4>
							</div>
							<div class="desc">
								${theme.content}
							</div>
							</div>
						</c:forEach>
						<!-- 
						<div class="page visible-xs-block">
							<c:import url="/op/page/2/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
						</div>
						 -->
						<div class="page hidden-xs">
							<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<c:import url="/op/rightNavi?fid=${forum==null?1:forum.parentForum.fid}"></c:import>
			</div>
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">友情链接</h3>
					</div>
					<div class="panel-body">
						<c:forEach items="${friendLinks}" var="f">
							<c:if test="${fn:startsWith(f.url,'http://')}">
								<a href="${f.url}" title="${f.siteName}" class="friend-link">${f.siteName}</a>
							</c:if>
							<c:if test="${!fn:startsWith(f.url,'http://')}">
								<a href="http://${f.url}" title="${f.siteName}" class="friend-link">${f.siteName}</a>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
</body>
</html>
