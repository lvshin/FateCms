<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>${tag}--${forum.title==null||forum.title==""?setting.siteName:forum.title}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="${forum.keywords==null||forum.keywords==''?forum.forumName:forum.keywords}" />
<meta name="description" content="${forum.description==null||forum.description==''?forum.forumName:forum.description}" />
<c:import url="/op/head"></c:import>
</head>
<body>
	
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="2" />
		</c:import>
		<div class="container">
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span></a></li>
			<li><a href="/">首页</a></li>
			<li><a href="javascript:;">标签</a></li>
			<li class="active">${tag}</li>
		</ol>
		<div class="main row">
		<div class=" col-md-8">
			<div class="panel panel-default">
				<div class="panel-body">
				<div class="visible-md-block visible-lg-block">
					<table class="table table-hover table-striped table-forum">
						<thead>
							<tr>
								<th>标题</th>
								<th>作者</th>
								<th>评论/查看</th>
								<!-- 
								<th>最后评论</th>
								 -->
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${themes}" var="theme">
								<tr>
									<td style="vertical-align:middle;">
										<c:if test="${theme.priority!=0}">
											<span class="label label-danger">置顶</span>
										</c:if>
										<a href="${theme.url}">${theme.title}${theme.state==1?"&nbsp;<span class='badge'>草</span>":""}</a>
										<c:if test="${userSession!=null}">
											<c:if test="${theme.priority==0}">
												<button class="btn btn-warning btn-xs pull-right btn-top" style="display: none;" data-guid="${theme.guid}">置顶</button>
											</c:if>
											<c:if test="${theme.priority!=0}">
												<button class="btn btn-warning btn-xs pull-right btn-top-cancel" style="display: none;" data-guid="${theme.guid}">取消置顶</button>
											</c:if>
										</c:if>
									</td>
									<td style="font-size: 12px;">${theme.author}<span  class="gray" style="font-size: 10px;display: block;"><fmt:formatDate value="${theme.publishDate}" pattern="yyyy-MM-dd"/></span></td>
									<td style="vertical-align:middle;">${theme.replies}/${theme.views}</td>
									<!-- 
									<td style="vertical-align:middle;font-size: 12px;">
										<c:if test="${fn:length(theme.comments)==0}">
											<span class="gray" style="font-size: 14px;">暂无</span>
										</c:if>
										<c:if test="${fn:length(theme.comments)>0}">
											${theme.comments[0].user.nickName}
											<span class="gray" style='display: block;'><fmt:formatDate value="${theme.comments[0].commentDate}" pattern="yyyy-MM-dd HH:mm"/></span>
										</c:if>
									</td>
									 -->
								</tr>
							</c:forEach>
						</tbody>
					</table>	
					</div>
					<div class="visible-xs-block visible-sm-block">
					<table class="table table-hover table-striped">
						<thead>
							<tr>
								<th>标题</th>
								<th>作者</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${themes}" var="theme">
								<tr>
									<td style="vertical-align:middle;"><a href="${theme.url}">${theme.title}</a></td>
									<td style="font-size: 12px;">${theme.author}<span class="gray" style="display: block;"><fmt:formatDate value="${theme.publishDate}" pattern="yyyy-MM-dd"/></span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
					</div>
				</div>
				
				</div>
				<div class="page pull-right">
					<c:import url="/op/staticPage/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
				</div>
			</div>
			<div class="col-md-4">
				<c:import url="/op/rightNavi?fid=${forum==null?1:forum.parentForum.fid}"></c:import>
			</div>
			
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
</body>
</html>
