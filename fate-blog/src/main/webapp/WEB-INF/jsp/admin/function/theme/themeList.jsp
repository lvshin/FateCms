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

<title>主题列表--${setting.siteName}</title>
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
							<a href="javascript:;"  rel="nofollow">管理中心</a> ><a href="javascript:;">主题管理</a> > ${forum.forumName}
						</h5>
					</div>
					<!-- 
					<div class="site_right">
						<button class="btn btn-primary" onclick="location.href='admin/theme/theme'">
							<span class="glyphicon glyphicon-plus"> </span> 添加新主题
						</button>
					</div>
					 -->
				</div>
				<div class="page">
					<div class="form-horizontal">
						<div class="form-group">
						    <label for="" class="col-sm-1 control-label">版块选择</label>
						    <div class="col-sm-11">
						      	<select class="form-control" style="width: auto;" id="forum-list-select">
									<c:forEach items="${forums}" var="f">
	            						<option value="${f.fid}" ${f.fid==forum.fid?"selected":""}>${f.forumName}</option>
	           						</c:forEach>
								</select>
						    </div>
						</div>
					</div>
					<input type="hidden" id="selectedFiles" >
					<table class="table table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="selectAll" value="" style="margin-left:20%"></th>
								<th>主题名称</th>
								<th>作者</th>
								<th>类型</th>
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
									<td><div class="widthLimit"><a href="${theme.url}" title="${theme.title }" target="_blank" rel="nofollow">${theme.title}${theme.state==1?"&nbsp;<span class='badge'>草</span>":""}</a></div></td>
									<td>${theme.author}</td>
									<td>${theme.forum.forumName}</td>
									<td title='<fmt:formatDate value="${theme.publishDate}" pattern="yyyy年MM月dd日  HH:mm:ss"/>'><fmt:formatDate value="${theme.publishDate}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
									<td align="right">
										<div class="btn-group btn-group-xs" role="group" aria-label="...">
											<button class="btn btn-danger del" onclick="deleteTheme('${theme.guid}')">删除</button>	
										</div>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page-num">
						<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
					</div>
				</div>
				<div class="clear"></div>
				<div class="div-fixed">
					<div class="pull-left">
						<button onclick="multiDeleteTheme()" class="btn btn-default" disabled="disabled">批量删除</button>
						<button onclick="unSelectAll()" class="btn btn-default" disabled="disabled">取消选择</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/select.js"></script>
	<script type="text/javascript">
	
		$(function(){
			$("#forum-list-select").change(function(){
				location.href=$("base").attr("href")+"admin/theme/themeList?fid="+$(this).val();
			});
		});
		//删除主题
		function deleteTheme(id) {
			var d = dialog({
				content : "确认删除该主题？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						url : "admin/theme/deleteTheme",
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
		
		//批量删除主题
		function multiDeleteTheme() {
			var d = dialog({
				content : "确认删除选中的主题？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						url : "admin/theme/multiDeleteTheme",
						type : 'post',
						dataType : 'json',
						data : {
							guids : $("#selectedFiles").val()
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
