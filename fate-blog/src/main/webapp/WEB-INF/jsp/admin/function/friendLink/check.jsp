<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>友链检测--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td{font-weight: bold;}
.del{height:22px!important;}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="6" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;"  rel="nofollow">友链管理</a> > 友链列表
						</h5>
					</div>
				</div>
				<div class="page">
					<button class="btn btn-primary pull-right" data-toggle="modal" data-target="#addLink">
						<span class="glyphicon glyphicon-plus"> </span> 添加友链
					</button>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>网站名称</th>
								<th>网站url</th>
								<th>是否反链</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="l" varStatus="i">
								<tr>
									<td>${l.siteName}</td>
									<td><div class="length-limit">${l.siteUrl}</div></td>
									<td>${l.has?"有":"无"}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<!-- 友链审核框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">审批友链申请</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="linkForm" autocomplete="off">
						<div class="form-group">
							<label for="linkName" class="col-sm-2 control-label">是否同意</label>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="agree" value="true"> 同意
								</label>
								<label class="radio-inline">
									<input type="radio" name="agree" value="false"> 拒绝
								</label>
							</div>
						</div>
						<div class="form-group">
							<label for="linkUrl" class="col-sm-2 control-label">审核意见</label>
							<div class="col-sm-10">
								<textarea rows="6" class="form-control" name="reason"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateFriendLink();">提交</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- 友链添加框 -->
	<div class="modal fade" id="addLink" tabindex="-1" role="dialog">
  		<div class="modal-dialog">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title">添加友链</h4>
      			</div>
      			<div class="modal-body">
					<form class="form-horizontal" id="linkAddForm" autocomplete="off">
						<div class="form-group">
							<label for="linkName" class="col-sm-2 control-label">网站名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="siteName" name="siteName" datatype="*" placeholder="要显示的友链名称,不超过10个字" maxlength="10">
							</div>
						</div>
						<div class="form-group">
							<label for="linkUrl" class="col-sm-2 control-label">友链地址</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="url" name="url" datatype="url" placeholder="友链地址">
							</div>
						</div>
					</form>
				</div>
		      	<div class="modal-footer">
		        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        	<button type="button" class="btn btn-primary" onclick="addFriendLink();">提交</button>
		      	</div>
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<c:import url="/op/base/foot"></c:import>
<script type="text/javascript" src="js/select.js"></script>
<script type="text/javascript">
	var fid = 0;//当前选中的fid
	$(function(){
		$(document).on("click","button[data-fid]",function(){
			fid=$(this)[0].dataset.fid;
			$('#myModal').modal();
		});
	});
	
	function updateFriendLink(){
		var options = {
				url : "admin/friendLink/update",
				type : 'post',
				dataType : 'json',
				data : {
					fid : fid
				},
				success : function(data) {
					if (data.success) {
						location.reload();
					} else
						dialog.alert(data.msg);
				},
				error : function(data) {
					if(data.status==404)
							dialog.alert("请求地址不存在");
						else if(data.status==500)
							dialog.alert("系统内部错误");
						else if(data.status==200){
							dialog.alert("登录超时，为保证您填写的内容不丢失，请勿刷新页面，并在新开页面中重新登录");
						}
						else dialog.alert("通信异常");
				}

			};
			$("#linkForm").ajaxSubmit(options);
	}
	
	function addFriendLink(){
		var options = {
				url : "admin/friendLink/add",
				type : 'post',
				dataType : 'json',
				data : {
				},
				success : function(data) {
					if (data.success) {
						location.reload();
					} else
						dialog.alert(data.msg);
				},
				error : function(data) {
					if(data.status==404)
							dialog.alert("请求地址不存在");
						else if(data.status==500)
							dialog.alert("系统内部错误");
						else if(data.status==200){
							dialog.alert("登录超时，为保证您填写的内容不丢失，请勿刷新页面，并在新开页面中重新登录");
						}
						else dialog.alert("通信异常");
				}

			};
			$("#linkAddForm").ajaxSubmit(options);
	}
	
</script>
</body>
</html>
