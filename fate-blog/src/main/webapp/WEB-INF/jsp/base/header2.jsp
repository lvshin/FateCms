<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<nav class="navbar  navbar-inverse navbar-fixed-top" id="header">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="http://${setting.appUrl}/">
				<img src="images/logo.png" width="40" height="40" alt="${setting.appName}" >
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-main">
				<c:forEach items="${navis}" var="navi">
					<c:if test="${fn:length(navi.children)==0}">
						<li><a href="${navi.url}" url="${navi.url}">${navi.name}</a></li>
					</c:if>
					<c:if test="${fn:length(navi.children)>0}">
						<li class="dropdown"><a href="javascript:;"
							class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-expanded="false" url="${navi.childUrl}"> ${navi.name} <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<c:forEach items="${navi.children}" var="n">
									<li><a href="${n.url}">${n.name}</a></li>
								</c:forEach>
							</ul></li>
					</c:if>
				</c:forEach>
			</ul>
			<form class="navbar-form navbar-left" role="search" action="javascript:opensearch()">
				<div class="form-group has-feedback">
					<input type="text" class="form-control" placeholder="Search" id="keyword" />
					<span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
				</div>
			</form>

			<ul class="nav navbar-nav navbar-right">
				<c:if test="${userSession==null}">
					<li><a href="op/login/toLogin" class="login-icon"><img src="images/nana.png" width="18" height="22" alt="登录"/></a></li>
					<c:if test="${setting.qq}">
						<li><a href="op/login/toQQ" class="login-icon"><img src="images/qq/qq.png" width="25" height="25" alt="QQ登录"/></a></li>
					</c:if>
					<c:if test="${setting.weibo}">
						<li><a href="op/login/toWeibo" class="login-icon"><img src="images/weibo/weibo.png" width="25" height="25" alt="新浪登录" /></a></li>
					</c:if>
				</c:if>
				<c:if test="${userSession!=null}">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle user-info" data-toggle="dropdown" role="button" aria-expanded="false" rel="nofollow">
							<img src="${userSession.user.headIconBig==null?" images/default_headicon.png":userSession.user.headIconBig}" class="img-circle" width=28 alt="${userSession.user.nickName}" />
							${userSession.user.nickName}<span class="caret"></span>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="profile/basicInfo"><span
									class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;个人资料</a></li>
							<li><a href="profile/headIcon"><span
									class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;修改头像</a></li>
							<li><a href="#" rel="nofollow"><span
									class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;修改密码</a></li>
							<li class="divider"></li>
							<li><a href="javascript:logout()"><span
									class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;退出</a></li>
						</ul></li>
					<c:if test="${userSession!=null&&userSession.user.userType==1}">
						<li><a href="admin/siteSet/siteInfo" rel="nofollow">管理中心</a></li>
					</c:if>
				</c:if>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

