<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>水树奈奈专区--${title==null||title==""?setting.siteName:title}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="水树奈奈专区,水树奈奈博客,水树奈奈专辑" />
<meta name="description" content="水树奈奈专区,水树奈奈博客,水树奈奈专辑等内容" />
<c:import url="/op/head"></c:import>
</head>
<body>

	<!-- 头部导航条 -->
	<c:import url="/op/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="banner hidden-xs">
		<c:import url="/op/loading?type=3"></c:import>
		<ul  style="display: none;">
			<li style="position: relative;">
				<img src="images/nana/nana_bg_1.png" style="width:100%;position: absolute;left:0;top:0;opacity:0.3;">
				<div class="container" style="position: relative;">
					
					<div style="position: absolute;top:50%;left:130px;">
						<p>NANA MIZUKI LIVE THEATER -ACOUSTIC-</p>
						<p>収録内容：2015.1.18　SAITAMA SUPER ARENA</p>
					</div>
					<img class="img-thumbnail"
						src="images/nana/img37.jpg"
						style="position: absolute;right: 30px;top:30%;">
				</div>
			</li>
			<li style="position: relative;" onclick="location.href='http://www.reinforce.cn/2015/06/28/Exterminate.html'" >
				<img src="images/nana/nana_bg_2.png" style="width:100%;position: absolute;left:0;top:0;opacity:0.3;">
				<div class="container" style="position: relative;">
					
					<div style="position: absolute;top:50%;left:130px;">
						<p>水树奈奈33rd单曲 Exterminate</p>
						<p>01. Exterminate</p>
						<p>02. ブランブル</p>
						<p>03.It's Only Brave</p>
					</div>
					<img class="img-thumbnail"
						src="images/nana/img32.jpg"
						style="position: absolute;right: 30px;top:30%;">
				</div>
			</li>
			<li style="position: relative;" onclick="location.href='${forums[0].children[0].url}'" >
				<img src="images/nana/nana_bg_3.png" style="width:100%;position: absolute;left:0;top:0;opacity:0.3;">
				<div class="container" style="position: relative;">
					
					<div style="position: absolute;top:50%;left:130px;">
						<p>[奈奈博客]${forums[0].children[1].title}</p>
						<p>发布日期：<fmt:formatDate value="${forums[0].children[1].publishDate}" pattern="yyyy-MM-dd"/></p>
					</div>
					<img class="img-thumbnail"
						src="${forums[0].children[1].img}"
						style="position: absolute;right: -10px;top:30%;">
				</div>
			</li>
		</ul>
	</div>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;首页</a></li>
			<li><a href="nana">水树奈奈专区</a></li>
		</ol>
		<div class="main">
			<c:forEach items="${forums}" var="forum">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong>${forum.forumName}</strong>
					</div>
					<div class="panel-body">
						<c:forEach items="${forum.children}" var="f" varStatus="i">
							<c:if test="${i.index%2==0}">
								<div class="row forum-group">
							</c:if>

							<div class="col-md-6">
								<div class="media">
									<div class="media-left">
										<a href="op/forum-${f.fid}-1.html"> 
											<img class="media-object hidden-xs" src="${f.forumIcon}" width="${f.iconWidth}" alt="${f.forumName}">
											<img class="media-object visible-xs-block" src="${f.forumIcon}" width="120" alt="${f.forumName}">
										</a>
									</div>
									<div class="media-body">
										<h5 class="media-heading">
											<a href="op/forum-${f.fid}-1.html"><strong>${f.forumName}</strong></a>
										</h5>
										<div>主题：${f.theme}</div>
										<div class="visible-md-block visible-lg-block">
											<div
												style="width:150px;height:20px;display:inline-block;overflow: hidden;text-overflow:ellipsis;white-space:nowrap;">
												<a href="${f.url}">${f.title}</a>
											</div>
											&nbsp;&nbsp;${f.author}
										</div>
									</div>
								</div>
							</div>
							<c:if test="${i.index%2==1}">
					</div>
					</c:if>
			</c:forEach>
			
		</div>
	</div>
	</div>
	</c:forEach>

	</div>

	</div>
	<c:import url="/op/footer"></c:import>
	<script src="js/unslider.min.js"></script>
	<script>
		$(function() {
			$('.banner').unslider({
				speed: 500,               
				delay: 5000, 
				complete: function() {},
				arrows: true,
				fluid: true,
				dots: true
			});
		});
		window.onload=function(){
			NProgress.done();
			$(".banner ul").show();
			$(".l-wrapper").hide();
		};
	</script>
</body>
</html>
