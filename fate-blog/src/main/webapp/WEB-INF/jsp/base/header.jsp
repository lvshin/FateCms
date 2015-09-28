<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="logo hidden-xs hidden-sm">
		<div class="left pull-left">
			<div class="header-logo pull-left"><a href="/"><img src="images/logo.png" width=100 alt="${setting.appName}"></a></div>
			<div class="header-text pull-left"><div class="name"><a href="http://${setting.appUrl}/" rel="home" id="name">${setting.appName}</a></div><div class="help-block" id="desc">${setting.appEnName}</div></div>
		</div>
		<div class="right pull-right">
			<c:if test="${userSession==null}">
				<div class="regist">
					<div>
						<a href="javascript:;">找回密码</a>
					</div>
					<c:if test="${setting.regAllow}">
						<div>
							<a href="op/register/goRegister"><strong>立即注册</strong></a>
						</div>
					</c:if>
				</div>
				<div class="login">
					<div class="loginL">
						<div class="row">
							<div class="col-xs-3">
								<span>用户名:</span>
							</div>
							<div class="col-xs-9">
								<input class="form-control" id="loginName" name="loginName"
									placeholder="Email/手机号">
							</div>
						</div>
						<div class="row">
							<div class="col-xs-3">
								<span>密码:</span>
							</div>
							<div class="col-xs-9">
								<input type="password" class="form-control" id="loginPwd"
									name="loginPwd">
							</div>
						</div>
					</div>
					<div class="loginR">
						<div>
							<input type="checkbox" id="chkRememberMe" /><label
								for="chkRememberMe">记住密码</label>
						</div>
						<div>
							<button class="btn btn-default" data-loading-text="登录中..."
								autocomplete="off" id="loginsubmit">登录</button>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${userSession==null}">
				<div class="qq_login">
					<div class="qq_login_btn" onclick="location.href='op/login/toQQ'"></div>
					<div class="wb_connect_btn" onclick="location.href='op/login/toWeibo'"></div>
				</div>
			</c:if>

			<c:if test="${userSession!=null}">
				<div class="user">
					<div class=" info dropdown">
						<div href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"
							rel="nofollow">${userSession.user.nickName}${!(userSession.user.emailStatus||userSession.user.mobileStatus)&&userSession.type==0?"(未验证)":""}<span
								class="caret"></span>
						</div>
						<ul class="dropdown-menu" role="menu" style="margin-top: 7px;">
							<li><a href="profile/basicInfo"><span
									class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;个人资料</a></li>
							<li><a href="profile/headIcon"><span
									class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;修改头像</a></li>
							<li><a href="#" rel="nofollow"><span
									class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;修改密码</a></li>
							<li class="divider"></li>
							<li><a href="javascript:logout()" rel="nofollow"><span
									class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;退出</a></li>
						</ul>
					</div>
					|<a href="javasctipt:;">消息<span id="msgCount"></span></a>
					<c:if test="${userSession!=null&&userSession.user.userType==1}">
						|<a href="admin/siteSet/siteInfo" rel="nofollow">管理中心</a>
					</c:if>
					<div class="headIcon">
						<img src="${userSession.user.headIconBig==null?"
							images/default_headicon.png":userSession.user.headIconBig}" class="img-thumbnail"
							width=80 alt="${userSession.user.nickName}" />
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<nav class="navbar navbar-default ">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header visible-xs-block  visible-sm-block">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="http://${setting.appUrl}/"><img src="images/logo.png"
				width=40 style="display: inline-block;" alt="${setting.appName}" ><label style="margin-left: 5px;">${setting.appName}</label></a>
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
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search"
						id="keyword" />
				</div>
				<button type="button" class="btn btn-default" onclick="opensearch()" >
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</form>
			<ul
				class="nav navbar-nav navbar-right visible-xs-block  visible-sm-block">
				<c:if test="${userSession==null}">
					<li><a href="op/login/goLogin">登陆</a></li>
					<li><a href="op/register/goRegister">注册</a></li>
				</c:if>
				<c:if test="${userSession!=null}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"
						rel="nofollow">${userSession.user.nickName}<span class="caret"></span></a>
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

