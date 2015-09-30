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

<title>${theme.title}-${theme.forum.forumName}-
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
</head>

<body>
	<!-- 头部导航条 -->
	<c:import url="/op/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span></a></li>
			<li><a href="/">首页</a></li>
			<li><a href="op/forum-${theme.forum.fid}-1.html">${theme.forum.forumName}</a></li>
			<li class="active">${theme.title}</li>
		</ol>
		<div class="row">
			<div class="col-md-8">
				<div class=" theme" id="theme">
					<div class="header page-header">
						<h3 class="h3 visible-md-block visible-lg-block">
							${theme.title}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
									value="${theme.publishDate}" type="date" pattern="yyyy年MM月dd日" />
								<span class="glyphicon glyphicon-comment"></span>${theme.replies}
								<span class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览
								<span class="glyphicon glyphicon-tags"></span>${theme.tags}

							</small>
						</h3>
						<h4 class="h4 visible-xs-block visible-sm-block">
							${theme.title}
							<c:if test="${theme.authorId==userSession.user.uid}">
								<label><a href="theme/addTheme?tid=${theme.guid}">编辑</a></label>
							</c:if>
							<small> <span class="glyphicon glyphicon-user"></span>${theme.author}
								<span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
									value="${theme.publishDate}" type="date" pattern="yyyy年MM月dd日" />
								<span class="glyphicon glyphicon-comment"></span>${theme.replies}
								<span class="glyphicon glyphicon-eye-open"></span>${theme.views}次浏览
								<span class="glyphicon glyphicon-tags"></span>${theme.tags}

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
						<div class="content col-md-12">${theme.content}</div>
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
		
		<div class="player-panel">
			<audio  src="" controls="controls" preload="none"></audio>
			<div class="player-main">
				<div class="music_info" id="divsonginfo">
					<a target="contentFrame" class="album_pic" title="">
						<img src="http://imgcache.qq.com/mediastyle/y/img/cover_mine_130.jpg" alt="" onerror="this.src='http://imgcache.qq.com/mediastyle/y/img/cover_mine_130.jpg'"></a>
					<div class="music_info_main">
						<p class="music_name" title="奈奈音乐">
							<span>奈奈音乐</span>
						</p>
						<p class="singer_name" title="奈奈音乐">奈奈音乐</p>
						<p class="play_date" id="ptime"></p>
					</div>
				</div>
					<div class="bar_op">
						<strong title="上一首( [ )" class="prev_bt" onclick="prev();">
							<span class="glyphicon glyphicon-step-backward"></span>
						</strong> 
						<strong title="播放(P)" class="play_bt" id="btnplay">
							<span class="glyphicon glyphicon-play"></span>
						</strong> 
						<strong title="下一首( ] )" class="next_bt" onclick="next();">
							<span class="glyphicon glyphicon-step-forward"></span>
						</strong>
						<!-- 
						<strong title="列表循环" class="cycle_bt" id="btnPlayway" onclick="g_topPlayer.setPlayWay();">
							<span class="glyphicon glyphicon-repeat"></span>
						</strong>
						 -->
						<p class="volume" title="音量调节">
							<span class="volume_icon" id="spanmute" title="点击设为静音(M)"><span class=" glyphicon glyphicon-volume-down"></span></span>
							<span class="volume_regulate" id="spanvolume">
								<span class="volume_bar" style="width:0%;" id="spanvolumebar"></span>
								<span class="volume_op" style="left:0%;" id="spanvolumeop"></span>
							</span>
						</p>
					</div>
					<div class="playbar_cp_select" id="divselect" >
						<strong title="单曲循环" class="cycle_single_bt" onclick="setPlayWay(1);"><span class="glyphicon glyphicon-repeat"></span><div class="cycle_single_pic">1</div></strong>
						<strong title="顺序播放" class="ordered_bt" onclick="setPlayWay(2);"><span class="glyphicon glyphicon-menu-hamburger"></span></strong>
						<strong title="列表循环" class="cycle_bt" onclick="setPlayWay(3);"><span class="glyphicon glyphicon-repeat"></span></strong>
						<strong title="随机播放" class="unordered_bt" onclick="setPlayWay(4);"><span class="glyphicon glyphicon-random"></span></strong>
					</div>
				<p class="player_bar">
					<span class="player_bg_bar" id="spanplayer_bgbar"></span> 
					<span class="download_bar" id="downloadbar" style="width: 0%;"></span> 
					<span class="play_current_bar" style="width: 0%;" id="spanplaybar"></span>
					<span class="progress_op" style="left: 0%;" id="spanprogress_op"></span>
				</p>
			</div>
			<span title="展开播放列表" class="open_list" id="spansongnum" ><span class="glyphicon glyphicon-th-list"></span><span class="label label-info">${fn:length(theme.medias)}</span></span>
			<span title="显示歌词(L)" class="btn_lyrics_disabled" id="btnlrc">词</span>
			<button type="button" class="folded_bt" title="点击收起" id="btnfold"><span class="glyphicon glyphicon-chevron-left"></span></button>
			<div class="play_list_frame" id="divplayframe" style="display: none; opacity: 1;">
		<div class="play_list_title">
			<ul id="tab_container" style="width:270px;">
                <li id="playlist_tab" class="current"><a href="javascript:;">播放列表</a><i></i></li>
            </ul>
			<strong title="收起播放列表" class="close_list" id="btnclose"><span class="glyphicon glyphicon-chevron-down"></span></strong>
		</div>
		<div class="play_list" id="divlistmain">
			<div class="play_list_main" id="divplaylist">
				<!-- 播放列表_S-->
				<input type="hidden" id="play-index" value="0"/>
				<div class="single_list" id="divsonglist" dirid="0">
					<ul>
						<c:forEach items="${theme.medias}" var="audio">
						<li src="${audio.url}" class="">
							<span class="music_name" title="${audio.title}">${audio.title}</span>
							<span class="singer_name" title="${audio.singer}">${audio.singer}</span>
							<span class="play_time">${audio.lastTime}</span>
						</li>
						</c:forEach>
					</ul>
				<div id="divalbumlist" style="display:none;">
				</div>
			</div>
		</div>
	</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<script src="js/theme/comment.min.js"></script>
	<script src="js/base/myScroll.js"></script>
	<script type="text/javascript">
		var $audio = $("audio")[0];
		var $this;
		$(function() {
			$(".folded_bt").click(function(){
				if($(".player-panel").css("left")=="0px"){
					$(".player-panel").animate({left:"-540px"},1000);
					$(this).attr("title","点击展开").children("span").removeClass("glyphicon-chevron-left").addClass("glyphicon-chevron-right");
				}else{
					$(".player-panel").animate({left:"0"},1000);
					$(this).attr("title","点击收起").children("span").removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-left");
				}
			});
			
			$("#btnplay").click(function(){
				if($(this).hasClass("play_bt")){
					if($("audio").attr("src")==""){
						$("#play-index").val(0);
						$("audio").attr("src",$("#divsonglist").find("li:eq(0)").attr("src"));
						$audio.load();
						$(".music_name").children("span").html($("#divsonglist").find("li:eq(0)").children(".music_name").html());
						$(".singer_name").html($("#divsonglist").find("li:eq(0)").children(".singer_name").html());
					}
					$audio.play();
					$audio.addEventListener('timeupdate',updateProgress,false);
					$audio.addEventListener('ended',audioEnded,false);
    				$audio.addEventListener('play',audioPlay,false);
    				$audio.addEventListener('pause',audioPause,false);
				}
				else{
					$audio.pause();
				}
			});
			
			$("#spanmute").click(function(){
				if(!$audio.muted){
					$audio.muted  = true;
					$(this).attr("title","点击开启声音(M)").children("span").removeClass("glyphicon-volume-down").addClass("glyphicon-volume-off");
				}else{
					$audio.muted  = false;
					$(this).attr("title","点击设为静音(M)").children("span").removeClass("glyphicon-volume-off").addClass("glyphicon-volume-down");
				}
			});
			
			$volume = $audio.volume*100;
			$("#spanvolumebar").css("width",$volume+"%");
			$("#spanvolumeop").css("left",$volume*0.9+"%");
			
			$("#spanvolume").click(function(e){
				if(e.target.id!="spanvolumeop"){
					var per = (e.offsetX/$("#spanvolume").css("width").substring(0,$("#spanvolume").css("width").length-2)).toFixed(2);
					console.log(e.offsetX);
					console.log(per);
					$audio.volume = per;
					$("#spanvolumebar").css("width",per*100+"%");
					$("#spanvolumeop").css("left",per*90+"%");
				}
			});
			$(document).delegate("#spanvolumeop","mousedown",function(e){
				left = e.pageX, $this = $(this);
   		 		this.setCapture ? (
    			this.setCapture(),
    			this.onmousemove = function (ev) { mouseMove(ev || event); },
    			this.onmouseup = mouseUp
    			) : $(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp);

			});
			
			$(".player_bar").click(function(e){
				$audio.currentTime = $audio.duration * (e.offsetX / $(".player_bar").css("width").replace(/px$/,''));
			});
			
			$("#spansongnum").click(function(){
				$("#divplayframe").toggle();
				new addScroll('divlistmain', 'divplaylist', 'play_list_scrolling');
				$(".play_list_scroll").css("width","10px");
			});
			
			$("#btnclose").click(function(){
				$("#divplayframe").hide();
			});
			$("#divsonglist").find("li").hover(function(){
				$(this).addClass("play_hover");
			},function(){
				$(this).removeClass("play_hover");
			});
			
			$("#divsonglist li").click(function(){
				$("audio").attr("src",$(this).attr("src"));
				$audio.load();
				$(".music_name").children("span").html($(this).children(".music_name").html());
				$(".singer_name").html($(this).children(".singer_name").html());
				$audio.addEventListener('timeupdate',updateProgress,false);
				$audio.addEventListener('ended',audioEnded,false);
    			$audio.addEventListener('play',audioPlay,false);
    			$audio.addEventListener('pause',audioPause,false);
				$audio.play();
			});
		});
		
		function audioPlay(){
			$("#btnplay").removeClass("play_bt").addClass("pause_bt").attr("title","暂停(P)").children("span").removeClass("glyphicon-play").addClass("glyphicon-pause");
		}
		function audioPause(){
			$("#btnplay").removeClass("pause_bt").addClass("play_bt").attr("title","播放(P)").children("span").removeClass("glyphicon-pause").addClass("glyphicon-play");
		}
		
		function mouseMove(e) {
    		var target = $this.prev();
    		var l = e.pageX - left;
    		left = e.pageX;
    		var per;
    		per = (Number($("#spanvolumebar").css("width").replace(/px$/,''))+l)/($("#spanvolume").css("width").replace(/px$/,''))*100;
    		if(per<=0)
    			per = 0;
    		else if(per>=100)
    			per = 100;
    		console.log(per)
    		$this.css({ 'left': per*0.9+"%"});
    		$("#spanvolumebar").css({'width':per+"%"});
    		$audio.volume = per/100;
		}
		function mouseUp(e) {
    		var el = $this.get(0);
    		el.releaseCapture ?
    		(
        		el.releaseCapture(),
        		el.onmousemove = el.onmouseup = null
    		) : $(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp);
		}
		
		function next(){
			$audio.pause();
			var i = Number($("#play-index").val())+1;
			if(i>$("#divsonglist").find("li").length-1)
				i=0;
			$("#play-index").val(i)
			$("audio").attr("src",$("#divsonglist").find("li:eq("+i+")").attr("src"));
			$(".music_name").children("span").html($("#divsonglist").find("li:eq("+i+")").children(".music_name").html());
			$(".singer_name").html($("#divsonglist").find("li:eq("+i+")").children(".singer_name").html());
			$audio.load();
			$audio.play();
		}
		
		function prev(){
			$audio.pause();
			var i = $("#play-index").val()-1;
			if(i<0)
				i=$("#divsonglist").find("li").length-1;
			$("#play-index").val(i)
			$("audio").attr("src",$("#divsonglist").find("li:eq("+i+")").attr("src"));
			$(".music_name").children("span").html($("#divsonglist").find("li:eq("+i+")").children(".music_name").html());
			$(".singer_name").html($("#divsonglist").find("li:eq("+i+")").children(".singer_name").html());
			$audio.load();
			$audio.play();
		}
		
		function audioEnded(ev)
		{
    		next();
		}
		
		function updateProgress() 
		{
    		var value=($audio.currentTime/$audio.duration).toFixed(2)*99;
   	 		$("#spanprogress_op").css({"left":value+"%"});
   	 		$("#spanplaybar").css({"width":value+"%"});
    		$("#ptime").html(calcTime(Math.floor($audio.currentTime)));
		}
		
		function calcTime(time)
		{
   	 		var hour;
    		var minute;
    		var second;
    		hour=String(parseInt(time/3600,10));
    		if (hour.length == 1)   hour   = '0' + hour;
    			minute=String(parseInt((time%3600)/60,10));
    		if (minute.length == 1)   minute   = '0' + minute;
    			second=String(parseInt(time%60,10));
    		if (second.length == 1)   second   = '0' + second;
    			return hour+":"+minute+":"+second;
		}
		
		function play(){
			$audio.play();
		}
		
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
				error : function() {
					dialog.alert("通信异常");
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
