<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>ECS实例列表</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<c:import url="/op/base/admin/head"></c:import>

<script type="text/javascript" src="js/cloud/upload.js"></script>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="../navi.jsp">
			<c:param name="index" value="2" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="admin/aliyun/filelist">阿里云</a> > ECS实例列表
					</h5>
					
				</div>
			</div>
			<div class="page" style="background: transparent;">
				<div class="ecs-list">
						<div class="ecs" data-id="${instance.instanceId}" style="background: #fff;">
							主机ID：${instance==null?"未使用ECS":instance.instanceId}<br>
							备注：<a href="javascript:;">${instance.instanceName}</a><br>
							<div class="circle cpu">
							    <div class="pie_left"><div class="left animated"></div></div>
							    <div class="pie_right"><div class="right"></div></div>
							    <div class="mask"><span>0</span>%</div>
							    <div class="desc">CPU使用率</div>
							</div>
							<div class="circle net">
							    <div class="pie_left"><div class="left"></div></div>
							    <div class="pie_right"><div class="right"></div></div>
							    <div class="mask"><span>0</span>%</div>
							    <div class="desc">带宽使用率</div>
							</div>
							<div class="circle memory">
							    <div class="pie_left"><div class="left"></div></div>
							    <div class="pie_right"><div class="right"></div></div>
							    <div class="mask"><span>0</span>%</div>
							    <div class="desc">内存使用率</div>
							</div>
							
							<!-- <span class="memory"></span> -->
							<div>入网/出网：<span class="net-in"></span>/<span class="net-out"></span></div>
							<!-- 
							<div class="progress disk">
  								<div class="progress-bar" role="progressbar">
  								</div>
							</div>
							 -->
						</div>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript">
		window.onload = function(){
			refreshEcs();
			setInterval(function(){
				refreshEcs();
			},5000);
			
		};
		$(function(){
			
		});
		
		function refreshEcs(){
			$(".ecs").each(function(){
				var $this = $(this);
				$.ajax({
					type : "post",
					url : "admin/aliyun/ecs/getEcsMonitorData",
					dataType : 'json',
					data : {
					},
					success : function(data) {
						console.log(data);
						console.log(data.cpuRatio);
						//$this.children(".disk").children(".progress-bar").html(data.disk+"%").css("width", data.disk+"%");
						if(data.cpuRatio!=null)
							$this.children(".cpu").children(".mask").children("span").html(parseFloat(data.cpuRatio).toFixed(1));
						if(data.net!=null)
							$this.children(".net").children(".mask").children("span").html(data.net);
						if(data.memoryRatio!="")
							$this.children(".memory").children(".mask").children("span").html(parseInt(data.memoryRatio));
						refreshCircle();
						
						$this.find(".net-in").html(parseFloat(data.net_in).toFixed(1)+"kb/s");
						$this.find(".net-out").html(parseFloat(data.net_out).toFixed(1)+"kb/s");
					},
				});
			});
		}
		
		function refreshCircle(){
			$('.circle').each(function(index, el) {
		        var num = $(this).find('span').text() * 3.6;
		        if (num<=180) {
		            $(this).find('.right').css('transform', "rotate(" + num + "deg)");
		            $(this).find('.left').css('transform', "rotate(0deg)");
		        } else {
		            $(this).find('.right').css('transform', "rotate(180deg)");
		            $(this).find('.left').css('transform', "rotate(" + (num - 180) + "deg)");
		        };
		    });

		}
	</script>
</body>
</html>
