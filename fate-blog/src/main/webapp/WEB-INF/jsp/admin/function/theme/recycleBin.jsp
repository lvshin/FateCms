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

<title>主题回收站--${setting.siteName}</title>
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
				<c:param name="index" value="2" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="timeline/element/index">主题管理</a> > 主题回收站
						</h5>
					</div>
				</div>
				<div class="page">
					<table class="table table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="selectedAll" value="" style="margin-left:20%"></th>
								<th>主题名称</th>
								<th>作者</th>
								<th>所属版块</th>
								<th>发布时间</th>
								<th align="right">操作&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${themes}" var="theme" varStatus="i">
								<tr>
									<td>
										<input type="checkbox" value="${theme.guid}" style="margin-left:20%">
									</td>
									<td><div class="widthLimit"><a href="javascript:;" title="${theme.title }" rel="nofollow">${theme.title}</a></div></td>
									<td>${theme.author}</td>
									<td>${theme.forum.forumName}</td>
									<td title='<fmt:formatDate value="${theme.publishDate}" pattern="yyyy年MM月dd日  HH:mm:ss"/>'><fmt:formatDate value="${theme.publishDate}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
									<td align="right">
										<div class="btn-group btn-group-xs" role="group" aria-label="...">
											<button class="btn btn-info del" onclick="restoreTheme('${theme.guid}')">恢复</button>	
										</div>
										<div class="btn-group btn-group-xs" role="group" aria-label="...">
											<button class="btn btn-danger del" onclick="crushTheme('${theme.guid}')">永久删除</button>	
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
	<script type="text/javascript">
		//彻底删除主题
		function crushTheme(id) {
			var d = dialog({
				content : "确认删除该主题？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						url : "admin/theme/crushTheme",
						type : 'post',
						dataType : 'json',
						data : {
							guid : id
						},
						success : function(data) {
							d.close().remove();
							if (data.success) {
								toastr.success(data.msg);
								location.reload();
							} else {
								toastr.error(data.msg);
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

		//恢复主题
		function restoreTheme(id) {
			var d = dialog({
				content : "确认恢复该主题吗？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						url : "admin/theme/restoreTheme",
						type : 'post',
						dataType : 'json',
						data : {
							guid : id
						},
						success : function(data) {
							d.close().remove();
							if (data.success) {
								toastr.success(data.msg);
								location.reload();
							} else {
								toastr.error(data.msg);
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
