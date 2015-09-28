<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	window.onload=function(){
		$(".panel-title").click(function() {
			if (!$(this).hasClass("panel-last"))
				$(this).siblings().slideToggle();
			$(this).next().addClass("in");
			$(this).parent().siblings().children(".panel-collapse").slideUp();
		});
		//滚轮下翻页面时，如果导航条到达浏览器窗口顶部，则固定导航条
		var h2 = $('.panel-right:last').offset().top;
		$(window).scroll(
				function() {
					if ($(window).scrollTop() > h2) {
						$('.panel-right:last').css('position', 'fixed').css(
								'top', '27px').css('width', "353px");
					} else {
						$('.panel-right:last').removeAttr("style");
					}
				});
	};
</script>

<div class="panel panel-default panel-right animated fadeInRight">
	<!-- Default panel contents -->
	<div class="panel-heading panel-title">
		<i class="glyphicon glyphicon-volume-up"></i>&nbsp;&nbsp;${announcement!=null?announcement.title:"公告"}
	</div>
	<div class="panel-body">
		<c:if test="${announcement!=null}">
  			${announcement.content}
  		</c:if>
		<c:if test="${announcement==null}">
			<span class="gray">暂无公告</span>
		</c:if>
	</div>

</div>

<div class="panel panel-danger panel-list panel-right animated fadeInRight delay1">
	<!-- Default panel contents -->
	<div class="panel-heading panel-title">
		<i class="glyphicon glyphicon-fire"></i>&nbsp;&nbsp;最热主题Top5
	</div>

	<!-- List group -->
	<div class="panel-collapse collapse in" style="display:block;">
		<c:forEach items="${hot}" var="h">
			<li class="list-group-item"><span class="badge">${h.views}</span><a
				href="${h.url}">${h.title}</a></li>
		</c:forEach>
	</div>
</div>
<div class="panel panel-info panel-list panel-right animated fadeInRight delay2"
	style="margin-bottom: 20px;">
	<!-- Default panel contents -->
	<div class="panel-heading panel-title">
		<i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;搜索排行Top5
	</div>

	<!-- List group -->
	<div class="panel-collapse collapse">
		<c:forEach items="${searchHot}" var="s">
			<li class="list-group-item"><span class="badge">${s.search}</span><a
				href="${s.url}">${s.title}</a></li>
		</c:forEach>
	</div>
</div>
 <div class="panel panel-default panel-right animated fadeInRight delay3">
	<div class="panel-heading panel-title">
		<i class="glyphicon glyphicon-tags"></i>&nbsp;&nbsp;标签
	</div>
	<div class="panel-body">
		<c:forEach items="${tags}" var="tag" varStatus="i">
			<c:if test="${i.count%5==0}">
				<a class="label label-info" href="tag/${tag.tagName}">${tag.tagName}</a>
			</c:if>
			<c:if test="${i.count%5==1}">
				<a class="label label-primary" href="tag/${tag.tagName}">${tag.tagName}</a>
			</c:if>
			<c:if test="${i.count%5==2}">
				<a class="label label-success" href="tag/${tag.tagName}">${tag.tagName}</a>
			</c:if>
			<c:if test="${i.count%5==3}">
				<a class="label label-warning" href="tag/${tag.tagName}">${tag.tagName}</a>
			</c:if>
			<c:if test="${i.count%5==4}">
				<a class="label label-danger" href="tag/${tag.tagName}">${tag.tagName}</a>
			</c:if>
		</c:forEach>
  	</div>
</div>
<div class="panel panel-default panel-right animated fadeInRight delay3">
	<!-- Default panel contents -->
	<div class="panel-heading panel-title panel-last">
		<i class="glyphicon glyphicon-stats"></i>&nbsp;&nbsp;站点统计
	</div>

	<!-- List group -->
	<ul class="list-group">
		<li class="list-group-item">统计时间 : <fmt:formatDate
				value="${updateTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></li>
		<li class="list-group-item">主题 : ${theme}</li>
		<!-- <li class="list-group-item">用户 : ${user}</li> -->
		<li class="list-group-item">评论 : ${comment}</li>
		<li class="list-group-item">浏览总数 : ${views}</li>
		<li class="list-group-item">搜索次数 : ${search}</li>
		<li class="list-group-item">在线人数 : ${online}</li>
		<li class="list-group-item">历史最高在线人数 : ${history_online}</li>
	</ul>
</div>
<div class="sidebar" style="position: fixed;right: 0;bottom: 20%;">
	<div class="btn-group-vertical" role="group">
	<button class="btn btn-default" onclick="toTop()" title="去顶部"><span class="glyphicon glyphicon-arrow-up"></span></button>
	<button class="btn btn-default" title="二维码" style="padding: 6px 1px;"><img class="QR" src="" width="25" height="25" /></button>
	<button class="btn btn-default" onclick="toBottom()" title="去底部"><span class="glyphicon glyphicon-arrow-down"></span></button>
	</div>
	<div class="showQR animated fadeInRight"><img class="QR" src="" width="250" height="250"/></div>
</div>
