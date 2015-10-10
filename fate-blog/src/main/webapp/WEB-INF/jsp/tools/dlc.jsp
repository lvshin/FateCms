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

<title>下载链接转换工具 -
	${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="${theme.title},${theme.tags}">
<meta name="description"
	content="${fn:substring(desc,0,100)}...,${theme.title},${theme.forum.forumName}">
<style type="text/css">
.adv {
	margin-top: 10px;
}
</style>

<c:import url="/op/head"></c:import>
<link rel="stylesheet" href="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.css">
    <link type="text/css" rel="stylesheet" href="../js/syntaxhighlighter/styles/shCore.css"/>
    <link type="text/css" rel="stylesheet" href="../js/syntaxhighlighter/styles/shThemeDefault.css"/>
</head>

<body>
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		<ol class="breadcrumb">
			<li><a href="/" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span>首页</a></li>
			<li><a href="javascript:;">实用工具</a></li>
			<li class="active">下载链接转换</li>
		</ol>
		<div class="row">
			<div class="col-md-8">
				<div class=" theme" id="theme">
					<div class="header page-header">
						<h3 class="h3 visible-md-block visible-lg-block">
							下载链接转换工具
							<small> 
							</small>
						</h3>
						<h4 class="h4 visible-xs-block visible-sm-block">
							下载链接转换工具
							<small>
							</small>
						</h4>
						<div class="toolbar">
							<div class="bdsharebuttonbox">
								<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
									class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a
									href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a
									href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a
									href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a
									href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
							</div>
							<script>
								window._bd_share_config = {
									"common" : {
										"bdSnsKey" : {
											"tsina" : "1049226049",
											"tqq" : "801565736"
										},
										"bdText" : "",
										"bdMini" : "2",
										"bdMiniList" : false,
										"bdPic" : "",
										"bdStyle" : "1",
										"bdSize" : "16"
									},
									"share" : {},
									"selectShare" : {
										"bdContainerClass" : null,
										"bdSelectMiniList" : [ "qzone",
												"tsina", "tqq", "renren",
												"weixin" ]
									}
								};
								with (document)
									0[(getElementsByTagName('head')[0] || body)
											.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
											+ ~(-new Date() / 36e5)];
							</script>
						</div>
					</div>
					<div class="row center-block">
						<div class="content col-md-12">
							<div class="input-group">
								<span class="input-group-addon">普通地址</span> 
									<input type="text" class="form-control" placeholder="普通地址" > 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">转换</button>
								</span>
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon">迅雷地址</span> 
									<input type="text" class="form-control" placeholder="迅雷地址" > 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">转换</button>
								</span>
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon">快车地址</span> 
									<input type="text" class="form-control" placeholder="快车地址" > 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">转换</button>
								</span>
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon">QQ旋风地址</span> 
									<input type="text" class="form-control" placeholder="QQ旋风地址" > 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">转换</button>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<script type="text/javascript">
								var cpro_id = "u2194845";
								(window["cproStyleApi"] = window["cproStyleApi"]
										|| {})[cpro_id] = {
									at : "3",
									rsi0 : "700",
									rsi1 : "250",
									pat : "6",
									tn : "baiduCustNativeAD",
									rss1 : "#FFFFFF",
									conBW : "0",
									adp : "1",
									ptt : "0",
									titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
									titFS : "14",
									rss2 : "#000000",
									titSU : "0",
									ptbg : "90",
									piw : "130",
									pih : "80",
									ptp : "1"
								}
							</script>
							<script src="http://cpro.baidustatic.com/cpro/ui/c.js"
								type="text/javascript"></script>
						</div>
					</div>
				</div>
				<div class="comment" id="respond">
					<h4>发表评论</h4>
					<c:if test="${userSession!=null}">
						<p class="login-help">
							当前登陆用户为：<a href="profile/basicInfo">${userSession.user.nickName}</a>。&nbsp;&nbsp;<a
								href="javascript:logout()" title="切换账号" rel="nofollow">登出&raquo;</a>
						</p>
						<form action="" method="post" id="respondForm">
							<input type="hidden" name="themeGuid" value="${theme.guid}">
							<textarea name="commentContent" class="form-control"
								autocomplete="off"></textarea>
						</form>
						<button class="btn btn-default" onclick="respond('')">评论</button>
					</c:if>
					<c:if test="${userSession==null}">
						要发表评论，请先<a
							href="op/login/goLogin?redirect_to=${theme.url}%23respond"
							rel="nofollow">登陆</a>。
					</c:if>
					<c:if test="${fn:length(theme.comments)>0}">
						<ul class="comments list-group">
							<li class="list-group-item">评论列表</li>
							<c:forEach items="${theme.comments}" var="comment">
								<li class="list-group-item">
									<div class="comment-votes" data-toggle="tooltip"
										data-placement="top"
										data-original-title="★ ${comment.up} ☆ ${comment.down}"
										guid="${comment.guid}">
										<a href="javascript:;" class="up" data-type="2" rel="nofollow"><span
											class="glyphicon glyphicon-chevron-up"></span> <span
											class="votes">${comment.up}</span></a> <a href="javascript:;"
											class="down" data-type="2" rel="nofollow"><span
											class="glyphicon glyphicon-chevron-down"></span></a>
									</div>
									<div class="comment-body">
										<div class="fn">
											<a href="javascript:dialog.alert('个人中心开发中...');"
												rel="nofollow" target="_blank">${comment.user.nickName}</a>
										</div>
										<div class="comment-content">
											<div>${comment.commentContent}</div>
										</div>
										<div class="comment-meta">
											<time datetime=""
												title="<fmt:formatDate value="${comment.commentDate}" type="date" pattern="yyyy年MM月dd日 HH:mm"/>">
												<fmt:formatDate value="${comment.commentDate}" type="date"
													pattern="yyyy年MM月dd日 HH:mm" />
											</time>
											<c:if test="${userSession.user.uid!=comment.user.uid}">
												<a rel="nofollow"
													class="comment-reply-login ${userSession!=null?"
													reply":""}" href="javascript:;" reply="${comment.guid}"
													to="${comment.user.uid}"
													${userSession==null?"onclick='toLogin()'":""}> <span
													class="glyphicon glyphicon-transfer"></span>${userSession==null?"登录以":""}回复</a>
												<a class="pm" href="javascript:dialog.alert('开发中....')"
													title="私信" target="_blank" style="display: none;"
													rel="nofollow"> <span
													class="glyphicon glyphicon-share-alt"></span>私信
												</a>
											</c:if>
										</div>

									</div> <c:if test="${fn:length(comment.comments)>0}">
										<ul class="list-group sub-comment">
											<c:forEach items="${comment.comments}" var="c">
												<li class="list-group-item sub-comment-item">
													<div class="comment-body">
														<div class="fn">
															<a href="javascript:;" rel="nofollow" target="_blank">${c.user.nickName}</a>
														</div>
														<div class="sub-comment-content">
															<div>回复&nbsp;${c.to.nickName}：${c.commentContent}</div>
														</div>
														<div class="comment-meta">
															<time datetime=""
																title="<fmt:formatDate value="${c.commentDate}" type="date" pattern="yyyy年MM月dd日 HH:mm"/>">
																<fmt:formatDate value="${c.commentDate}" type="date"
																	pattern="yyyy年MM月dd日 HH:mm" />
															</time>
															<a rel="nofollow"
																class="comment-reply-login ${userSession!=null?"
																reply":""}"
																	href="javascript:;"
																reply="${c.commentParent.guid}" to="${c.user.uid}"
																${userSession==null?"onclick='toLogin()'":""}> <span
																class="glyphicon glyphicon-share-alt"></span>${userSession==null?"登录以":""}回复</a>
															<a class="pm" href="javascript:dialog.alert('开发中....')"
																title="私信" target="_blank" style="display: none;"
																rel="nofollow"> <span
																class="glyphicon glyphicon-share-alt"></span>私信
															</a>
														</div>
													</div>
												</li>
											</c:forEach>
										</ul>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
			<div class="col-md-4">
				<c:import url="/op/rightNavi?fid=1"></c:import>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<!-- 图+广告位 -->
	<script type="text/javascript">
		/*为了保证用户体验及收益，图片大小不得小于：200*180*/
		/*图+推广*/
		var cpro_id = "u2079355";
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/i.js"
		type="text/javascript"></script>
	<script src="js/theme/comment.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="tooltip"]').tooltip({
				html : true
			});
		});
		function toLogin() {
			location.href = "op/login/goLogin?redirect_to=${theme.url}%23respond";
		}
	</script>
</body>
</html>
