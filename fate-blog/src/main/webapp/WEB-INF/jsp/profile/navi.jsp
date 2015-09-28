<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	window.onload=function(){
		$(".navi").children(".nav").children("li:eq(${param.index-1})").addClass("active").siblings().removeClass("active");
		console.log($(document).width());
		if($(document).width()>992)
			$(".navi").css("height",$(".navi").next().css("height"));
	};
</script>
<div class="navi col-md-2 visible-md-block visible-lg-block">
	<ul class="nav nav-pills nav-stacked">
		<li role="presentation" class="active"><a
			href="profile/basicInfo"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;个人资料</a></li>
		<li role="presentation"><a href="profile/headIcon"><span class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;本地头像</a></li>
		<li role="presentation"><a href="profile/security"><span class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;安全设置</a></li>
		<li role="presentation"><a href="javascript:;"><span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;修改密码</a></li>
		<li role="presentation"><a href="profile/thirdParty"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;&nbsp;合作帐号</a></li>
	</ul>
</div>
