<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>版块管理--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td {
	font-weight: bold;
}

.del {
	height: 22px !important;
}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main">
			<c:import url="../navi.jsp">
				<c:param name="index" value="1" />
			</c:import>
			<div class="reinforce-content forum">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a
								href="admin/forum/forumList">版块管理</a> > 版块管理
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<table class="table table-hover">
						<thead>
							<tr>
								<th style="min-width:80px;">显示顺序</th>
								<th style="width:70%;">版块名称（默认地址：op/forum-{fid}-1.html）</th>
								<th style="min-width:80px;">版块fid</th>
								<th style="min-width:150px;" align="right">操作&nbsp;</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<c:import url="/op/base/loading?size=10"></c:import>
					<br>
					<a href="javascript:newRegion();" class="add"><span
						class="glyphicon glyphicon-plus"></span>&nbsp;添加新分区</a>
					<div class="clear"></div>
					<br>
					<button class="btn btn-success" onclick="forumSubmit()">提交</button>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/select.js"></script>
	<script type="text/javascript">
		$(function() {
			getForumRoot();
		});
		/* 版块相关开始 */
		/**
		 * 获取版块树
		 */
		function getForumRoot() {
			$.ajax({
				type : "post",
				url : "admin/forum/getRoot",
				dataType : 'json',
				success : function(data) {
					$(".forum").find(".table").children("tbody").html(
							decodeForum(data));
					$(".spinner").hide();
				}
			});
		}

		/**
		 * 解析版块树
		 * 
		 * @param data
		 *            json数据
		 * @returns {String} 解析后的html代码
		 */
		function decodeForum(data) {
			var content = "";
			for (var i = 0; i < data.length; i++) {
				content += "<tr class='forum'>"
						+ "<td><input type='text' class='form-control order_of_show' name='forumOrder' value='"
				+ data[i].forumOrder
				+ "' /><input type='hidden' name='forumType' value='"
				+ data[i].type + "' /></td>"
						+ "<td";
				if (data[i].type == 2)
					content += " class='td-forum'";
				else if (data[i].type == 3) {
					if (i == data.length - 1)
						content += " class='td-subforum-last'";
					else
						content += " class='td-subforum'";
				}
				content += "><input class='form-control forum_name pull-left' type='text' name='forumName' value='"
				+ data[i].forumName
				+ "' /><input type='hidden' name='fid' value='"
				+ data[i].fid
				+ "' />";
				if (data[i].type == 2) {
					content += "<a href='javascript:;' class='newSub' fid='"
					+ data[i].fid + "' rel='nofollow' >"
							+ "<span class='glyphicon glyphicon-plus'></span></a>";
				}

				content += "</td>" + "<td>";
				if (data[i].type == 2) {
					content += "<a href='javascript:;' rel='nofollow'>"
							+ data[i].fid + "</a>";
				}
				content += "</td>" + "<td align='right'>";
				if (data[i].type == 2) {
					content += "<div class='btn-group btn-group-xs'>"
							+ "<button class='btn btn-info del' onclick=location.href='admin/forum/"
							+ (data[i].type == 1 ? "region" : "forum")
							+ "?fid=" + data[i].fid + "'>编辑</button>"
							+ "</div>";
				}
				content += "&nbsp;&nbsp;<div class='btn-group btn-group-xs'>"
						+ "<button class='btn btn-danger del' onclick=delForm('"
						+ data[i].fid + "')>删除</button>" + "</div>" + "</td>"
						+ "</tr>" + decodeForum(data[i].children);
				if (data[i].type == 1) {
					content += "<tr>"
							+ "<td></td>"
							+ "<td colspan='3'>"
							+ "<a href='javascript:;' class='add newForum' fid='"
					+ data[i].fid + "' rel='nofollow'>"
							+ "<span class='glyphicon glyphicon-plus'></span>"
							+ "&nbsp;添加新版块" + "</a>" + "</td>" + "</tr>";
				}
			}

			return content;

		}

		/**
		 * 新建分区
		 */
		function newRegion() {
			var content = "<tr class='forum'>"
					+ "<td><input class='form-control order_of_show' type='text' name='forumOrder' value='0' /><input type='hidden' name='forumType' value='1' /></td>"
					+ "<td><input class='form-control forum_name pull-left' type='text' name='forumName' /><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='fid' value='0' /></td>"
					+ "<td></td>" + "<td align='right'>" + "</td>" + "</tr>";
			$(".forum").find(".table").children("tbody").append(content);
		}

		/**
		 * 新建版块
		 */
		$(document)
				.on(
						"click",
						".newForum",
						function() {
							var content = "<tr class='forum'>"
									+ "<td><input class='form-control order_of_show' type='text' name='forumOrder' value='0' /><input type='hidden' name='forumType' value='2' /></td>"
									+ "<td class='td-forum'><input class='form-control forum_name pull-left' type='text' name='forumName'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='fid' value='0' /><input type='hidden' name='parentFid' value='"
									+ $(this).attr("fid") + "' /></td>"
									+ "<td></td>" + "<td align='right'>"
									+ "</td>" + "</tr>";
							$(this).parent().parent().before(content);
						});
		/**
		 * 新建子版块
		 */
		$(document)
				.on(
						"click",
						".newSub",
						function() {
							var content = "<tr class='forum'>"
									+ "<td><input class='form-control order_of_show' type='text' name='forumOrder' value='0' /><input type='hidden' name='forumType' value='3' /></td>"
									+ "<td class='td-subforum'><input class='form-control forum_name pull-left' type='text' name='forumName'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='fid' value='0' /><input type='hidden' name='parentFid' value='"
									+ $(this).attr("fid") + "' /></td>"
									+ "<td></td>" + "<td align='right'>"
									+ "</td>" + "</tr>";
							$(this).parent().parent().after(content);
						});

		/**
		 * 删除一行
		 */
		$(document).on("click", ".deleterow", function() {
			$(this).parent().parent().remove();
		});

		/**
		 * 版块更新
		 */
		function forumSubmit() {
			var result = "";
			$(".reinforce-content")
					.find(".table")
					.children("tbody")
					.children(".forum")
					.each(
							function() {
								result += "{forumType:"
										+ $(this).find("input[name=forumType]")
												.val()
										+ ",forumOrder:"
										+ $(this)
												.find("input[name=forumOrder]")
												.val()
										+ ",forumName:"
										+ $(this).find("input[name=forumName]")
												.val()
										+ ",fid:"
										+ $(this).find("input[name=fid]").val()
										+ ",parentFid:"
										+ ($(this)
												.find("input[name=parentFid]").length == 0 ? 0
												: $(this)
														.find(
																"input[name=parentFid]")
														.val()) + "},";

							});
			if (result.length > 0) {
				result = result.substring(0, result.length - 1);
				result = "[" + result + "]";
				$.ajax({
					type : "post",
					url : "admin/forum/addForum",
					dataType : 'json',
					data : {
						result : result
					},
					error : function() {
						alert("通信错误");
					},
					success : function(data) {
						if (data.success) {
							toastr.success("更新成功");
							setTimeout(function() {
								location.reload()
							}, 1500);
						} else {
							dialog.alert(data.message);
						}

					}
				});
			}
		}

		/**
		 * 删除版块
		 * @param fid 版块id
		 */
		function delForm(fid) {
			var d = dialog({
				content : "确认删除该主题？",
				title : '提示',
				width : '200px',
				okValue : '确定',
				ok : function() {
					$.ajax({
						type : "post",
						url : "admin/forum/delForum",
						dataType : 'json',
						data : {
							fid : fid
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
								dialog.alert(data.message);
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

		/*版块相关结束*/
	</script>
</body>
</html>
