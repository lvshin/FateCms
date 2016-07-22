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

<title>公告列表--${setting.siteName}</title>
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
				<c:param name="index" value="4" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;"  rel="nofollow">公告管理</a> > 公告列表
						</h5>
					</div>
				</div>
				<div class="page">
					<a class="btn btn-primary pull-right mg-t-10" href="admin/announcement/new">
						<span class="glyphicon glyphicon-plus"> </span> 添加新公告
					</a>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>显示顺序</th>
								<th>标题</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>发布人</th>
								<th align="right">操作&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="l" varStatus="i">
								<tr>
								<td class="text-center" width="73">${l.displayOrder}<input type="hidden" name="id" value="${l.id}" /></td>
								<td><a href="admin/announcement/new?id=${l.id}">${l.title}</a></td>
								<td>${l.startTime}</td>
								<td>${l.endTime}</td>
								<td>${l.anthor.nickName}</td>
								<td align="right">
									<div class="btn-group btn-group-xs" role="group">
										<button class="btn btn-danger del" onclick="del('${l.id}')">删除</button>
									</div>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page-num">
						<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/select.js"></script>
	<script type="text/javascript">
		//删除公告，真的删了o.o
		function del(id) {
			var d = dialog({
				content : "确认删除该公告？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						type : "post",
						url : "admin/announcement/del",
						dataType : 'json',
						data : {
							id : id
						},
						error : function(data) {
							if (data.status == 404)
								dialog.alert("请求地址不存在");
							else if (data.status == 500)
								dialog.alert("系统内部错误");
							else if (data.status == 200) {
								location.href = $("base").attr("href");
							} else
								dialog.alert("通信异常");
						},
						success : function(data) {
							if (data.success)
								location.reload();
							else {
								dialog.alert(data.msg);
							}

						}
					});
					return true;
				},
				cancelValue : "取消",
				cancel : function() {
				}
			});
			d.showModal();
		}
	</script>
</body>
</html>
