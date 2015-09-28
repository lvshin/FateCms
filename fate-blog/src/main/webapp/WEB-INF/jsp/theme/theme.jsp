<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>${theme.title}- ${theme.forum.forumName} -
	${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
    <link type="text/css" rel="stylesheet" href="js/syntaxhighlighter/styles/shCore.css"/>
    <link type="text/css" rel="stylesheet" href="js/syntaxhighlighter/styles/shThemeDefault.css"/>
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
			<li><a href="op/forum-${theme.forum.fid}-1.html">${theme.forum.forumName}</a></li>
			<li class="active">${theme.title}</li>
		</ol>
		<div class="row">
			<div class="col-md-8">
				<div class=" theme animated fadeIn" id="theme" >
					<div class="header page-header">
						<h3 class="h3 visible-md-block visible-lg-block">
							${theme.title}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span>
							<fmt:formatDate value="${theme.publishDate}" type="date"
									pattern="yyyy年MM月dd日" /> <span
								class="glyphicon glyphicon-comment"></span>${theme.replies} <span
								class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览 <span
								class="glyphicon glyphicon-tags"></span>${theme.tags}

							</small>
						</h3>
						<h4 class="h4 visible-xs-block visible-sm-block">
							${theme.title}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span>
							<fmt:formatDate value="${theme.publishDate}" type="date"
									pattern="yyyy年MM月dd日" /> <span
								class="glyphicon glyphicon-comment"></span>${theme.replies} <span
								class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览 <span
								class="glyphicon glyphicon-tags"></span>${theme.tags}

							</small>
						</h4>
						<div class="toolbar">
							<div class="vote" data-toggle="tooltip" data-placement="top"
								data-original-title="★ ${theme.up} ☆ ${theme.down}"
								guid="${theme.guid}">
								<div class="up" data-type="1">
									<span class="glyphicon glyphicon-thumbs-up"></span><span
										class="votes">${theme.up}</span>
								</div>
								<div class="down" data-type="1">
									<span class="glyphicon glyphicon-thumbs-down"></span>
								</div>
							</div>
							<div class="bdsharebuttonbox">
								<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
									class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a
									href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a
									href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a
									href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a
									href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="content col-md-12">
						${theme.content}</div>
						<div class="col-md-12">
							<div class="alert alert-success" role="alert" style="margin: 20px 0;border-left: 0;border-right: 0;border-radius:0;">
								<span class="glyphicon glyphicon-play"></span>&nbsp;本文链接：<a href="${theme.url}" rel="author">${theme.title}</a>
								，${userSession.user.nickName}花了好多脑细胞写的，转载请注明链接喔~~
							</div>
						</div>
					</div>
				</div>
				<div class="comment">
					<!-- 多说评论框 start -->
					<div class="ds-thread" data-thread-key="${theme.guid}"
						data-title="${theme.title}" data-url="${theme.url}"></div>
					<!-- 多说评论框 end -->

				</div>
				
				<div class="comment hidden" id="respond" >
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
				<c:import url="/op/rightNavi?fid=${theme.forum.parentForum.fid}"></c:import>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<!-- 图+广告位 
	<script type="text/javascript">
		/*为了保证用户体验及收益，图片大小不得小于：200*180*/
		/*图+推广*/
		var cpro_id = "u2079355";
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/i.js"
		type="text/javascript"></script>
	-->
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
	<script src="js/theme/comment.min.js"></script>
	<script type="text/javascript" src="js/syntaxhighlighter/scripts/shCore.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushBash.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushCpp.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushCSharp.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushCss.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushDelphi.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushDiff.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushGroovy.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushJava.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushJScript.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushPhp.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushPlain.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushPython.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushRuby.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushScala.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushSql.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushVb.js"></script>
    <script type="text/javascript" src="js/syntaxhighlighter/scripts/shBrushXml.js"></script>
	<script type="text/javascript">
	$(function(){
		SyntaxHighlighter.config.clipboardSwf = 'js/syntaxhighlighter/scripts/clipboard.swf';
            SyntaxHighlighter.config.strings.expandSource = 'show source';
            SyntaxHighlighter.config.strings.viewSource = '查看源代码';
            SyntaxHighlighter.config.strings.copyToClipboard = '复制到剪贴板';
            SyntaxHighlighter.config.strings.copyToClipboardConfirmation = '代码现在在你的剪贴板';
            SyntaxHighlighter.config.strings.print = '打印';
            SyntaxHighlighter.config.strings.help = '帮助';
            SyntaxHighlighter.config.strings.alert = 'SyntaxHighlighter\n\n';
            SyntaxHighlighter.config.strings.noBrush = '无法找到Brush：';
            SyntaxHighlighter.config.strings.brushNotHtmlScript = 'Brush不能设置 html-script选项';
            SyntaxHighlighter.defaults['pad-line-numbers'] = false;
            SyntaxHighlighter.all();
	});
		function respond(num) {
			var options = {
				url : "theme/addComment",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					var d = dialog({
						content : data.message
					});
					d.showModal();
					if (data.success) {
						setTimeout(function() {
							location.reload();
						}, 1500);
					} else {
						setTimeout(function() {
							d.close().remove();
						}, 1500);
					}
				},
				error : function(data) {
					if(data.status==404)
						dialog.alert("请求地址不存在");
					else if(data.status==500)
						dialog.alert("系统内部错误");
					else if(data.status==200){
						location.reload();
					}
					else dialog.alert("通信异常");
				}
			};
			if ($("#respondForm" + num).children("[name=commentContent]").val() != "")
				$("#respondForm" + num).ajaxSubmit(options);
			else
				dialog.alert("请填写评论内容");
		}
		$(function() {
			$('[data-toggle="tooltip"]').tooltip({
				html : true
			});
			$('.time-picker').datetimepicker({
    			format: 'yyyy-mm-dd hh:ii:ss',
    			autoclose : true,
    			todayBtn : true,
    			todayHighlight : true,
    			language : 'zh-CN'
			});
		});
		function toLogin() {
			location.href = "op/login/goLogin?redirect_to=${theme.url}%23respond";
		}
	</script>
	
	<!-- 百度一件分享 -->
	<script>
		window._bd_share_config = {
			
		"common" : {
				"bdSnsKey" : {
					"tsina" : "${weibo}",
					"tqq" : "${setting.txAppKey}"
				},
				"bdText" : "",
				"bdMini" : "2",
				"bdMiniList" : false,
				"bdPic" : "",
				"bdStyle" : "1",
				"bdSize" : "16"
			},
			"share" : {},
			"image" : {
				"viewList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ],
				"viewText" : "分享到：",
				"viewSize" : "16"
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	</script>
</body>
</html>
